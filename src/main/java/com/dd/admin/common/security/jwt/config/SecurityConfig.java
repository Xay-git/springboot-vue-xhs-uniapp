package com.dd.admin.common.security.jwt.config;

import com.dd.admin.common.security.jwt.service.JwtUserDetailsService;
import com.dd.admin.common.security.jwt.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * SecurityConfig 配置类
 */
@Configuration
@EnableWebSecurity //开启注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;
    /**
     * 忽略接口
     */
    @Autowired
    IgnoreConfig ignoreConfig;

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 开启跨域
                .cors()
                .and()
                // 禁用 CSRF
                .csrf().disable()
//                //异常处理   注释掉使用自定义异常 方便统一管理
//                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .anyRequest().authenticated()

                // 添加自定义 JWT 过滤器
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        httpSecurity
                .headers()
                .frameOptions().sameOrigin()
                .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        WebSecurity and = web.ignoring().and();
        // 忽略 GET
        ignoreConfig.getGet().forEach(url -> and.ignoring().antMatchers(HttpMethod.GET, url));
        // 忽略 POST
        ignoreConfig.getPost().forEach(url -> and.ignoring().antMatchers(HttpMethod.POST, url));
        // 忽略 所有方法
        ignoreConfig.getPattern().forEach(url -> and.ignoring().antMatchers(url));
    }


}
