package com.dd.admin.business.api.service;


import com.dd.admin.business.api.domain.PhoneLoginDto;

public interface LoginService {
    public String sendSmsCode(String phoneNumber);
    public void checkCode(PhoneLoginDto phoneLoginDto);
}
