package com.dd.admin.common.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.security.jwt.JwtTokenUtil;
import com.dd.admin.common.security.jwt.service.JwtUserDetailsService;
import com.dd.admin.common.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Component
public class ApiInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthorService authorService;

    @Autowired
    HttpServletRequest httpServletRequest;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       // 预请求过滤
        if(RequestMethod.OPTIONS.name().equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            throw new ApiException(700,"请登录后访问");
        }
        //解析token
        System.out.println(token);
        String authorId = "";
        try {
             authorId = jwtTokenUtil.getUsernameFromToken(token);
        }catch (Exception e) {
            throw new ApiException(700,"token已失效");
        }

        request.setAttribute("authorId",authorId);

        Author author = authorService.getById(authorId);
        if(author==null){
            throw new ApiException(700,"该用户已删除~");
        }
        request.setAttribute("author",author);
        if(author.getAuthorStatus()!=null&&author.getAuthorStatus().equals(1)){
            throw new ApiException(700,"当前用户已冻结~");
        }
        return true;
    }
}
