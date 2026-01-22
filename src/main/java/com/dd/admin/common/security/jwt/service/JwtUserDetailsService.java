package com.dd.admin.common.security.jwt.service;

import cn.hutool.core.bean.BeanUtil;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.security.model.JwtUser;
import com.dd.admin.common.utils.StringUtil;
import com.dd.admin.system.menu.service.MenuService;
import com.dd.admin.system.user.domain.UserVo;
import com.dd.admin.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.dd.admin.common.exception.enums.ApiExceptionEnum.USERNAME_IS_NOT_NULL;

/**
 * JwtUserDetailsService
 *	 	实现UserDetailsService,重写loadUserByUsername方法
 *  	如果你自己需要定制查询user的方法,请改造这里
 * @author zhengkai.blog.csdn.net
 */
@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService{
    @Autowired
    UserService userService;
    @Autowired
    MenuService menuService;
    @Override
    public UserDetails loadUserByUsername(String username) {
        if(StringUtil.isEmpty(username)){
          throw new ApiException(USERNAME_IS_NOT_NULL);
        }
        UserVo user = userService.selectOneByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名或者密码错误!");
        }
        JwtUser jwtUser = BeanUtil.copyProperties(user,JwtUser.class);
//        //用户拥有的权限和角色
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        //添加权限  从所拥有的菜单和权限中获取自定义 权限
        Set<String> perms = menuService.selectPermsByUserId(jwtUser.getUserId());
        perms.stream().forEach(perm->{
            authorities.add(new SimpleGrantedAuthority(perm));
        });
//        //添加角色 暂时用不到 后期从数据库获取
//        if(ADMIN_USER_NAME.equals(username)){
//            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }
        jwtUser.setAuthorities(authorities);
        return jwtUser;
    }
}

