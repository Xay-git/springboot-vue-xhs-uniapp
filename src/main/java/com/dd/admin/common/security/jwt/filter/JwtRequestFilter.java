package com.dd.admin.common.security.jwt.filter;


import cn.hutool.core.collection.CollUtil;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.security.jwt.JwtTokenUtil;
import com.dd.admin.common.security.jwt.service.JwtUserDetailsService;
import com.dd.admin.common.security.jwt.config.IgnoreConfig;
import com.dd.admin.common.utils.StringUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.dd.admin.common.exception.enums.ApiExceptionEnum.TOKEN_ERROR;


@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    IgnoreConfig ignoreConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException , ApiException {
        //放开过滤url
        if(checkIgnores(ignoreConfig,request)){
            chain.doFilter(request, response);
             return;
        }

        SecurityContext context = SecurityContextHolder.getContext();
        //从请求头拿到token
        final String requestTokenHeader = request.getHeader(tokenHeader);

        String username = null;
        String jwtToken = null;

        // JWT报文表头的格式是"Bearer token". 去除"Bearer ",直接获取token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                //根据token获取用户名
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                throw new ApiException(TOKEN_ERROR);
            } catch (ExpiredJwtException e) {
                throw new ApiException(TOKEN_ERROR);
            }
        } else {
            throw new ApiException(TOKEN_ERROR);
        }


        if(context.getAuthentication() == null){
            //查看是否是后台
            if(StringUtil.isNotEmpty(username)){
                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
                // 查看token是否过期
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    setAuthentication(request,context,userDetails);
                    chain.doFilter(request, response);
                    return;
                }else{
                    throw new ApiException(TOKEN_ERROR);
                }
            }
        }

        chain.doFilter(request, response);
    }


   //将自己需要的Authentication对象放入Spring Security中
    private void setAuthentication(HttpServletRequest request, SecurityContext context, UserDetails userDetails){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(usernamePasswordAuthenticationToken);
    }


    public static boolean checkIgnores(IgnoreConfig ignoreConfig, HttpServletRequest request) {
        String method = request.getMethod();
        Set<String> ignores = new HashSet<>();
        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (httpMethod == null) {
            httpMethod = HttpMethod.GET;
        }
        switch (httpMethod) {
            case GET:
                ignores.addAll(ignoreConfig.getGet());
                break;
            case POST:
                ignores.addAll(ignoreConfig.getPost());
                break;
            default:
                break;
        }
        ignores.addAll(ignoreConfig.getPattern());
        if (CollUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }
}
