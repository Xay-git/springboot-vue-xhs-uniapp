package com.dd.admin.common.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String userName;
    @JsonIgnore
    private String password;
    private Integer userType;
    private String deptId;
    private String deptName;

    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;


    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //true 没有过期
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  //true 没有上锁
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //true 没有失效
    }
}
