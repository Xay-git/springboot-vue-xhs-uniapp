package com.dd.admin.common.security;


import com.dd.admin.common.security.model.JwtUser;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {
     public static JwtUser getLoginUser(){
       JwtUser jwtUser = null;
        try {
             jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return jwtUser;
    }
}
