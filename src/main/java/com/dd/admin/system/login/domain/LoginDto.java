package com.dd.admin.system.login.domain;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class LoginDto {
    private String username;
    private String password;
}
