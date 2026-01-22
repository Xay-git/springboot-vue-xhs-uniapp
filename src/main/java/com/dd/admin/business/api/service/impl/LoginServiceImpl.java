package com.dd.admin.business.api.service.impl;

import com.dd.admin.business.api.domain.PhoneLoginDto;
import com.dd.admin.business.api.service.LoginService;
import com.dd.admin.common.config.SmsProperties;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.service.SmsService;
import com.dd.admin.common.utils.RandomCodeUtil;
import com.dd.admin.common.utils.RedisUtil;
import com.dd.admin.system.setting.service.SystemSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private SystemSettingService systemSettingService;

    @Override
    public String sendSmsCode(String phoneNumber) {
        String redisKey = "SMS_CODE:" + phoneNumber;
        Object smsCode = redisUtil.get(redisKey);
        if (smsCode != null) {
            throw new ApiException("验证码已发送，请稍后再试");
        }

        // 优先检查系统设置的短信模式
        String smsMode = systemSettingService.getSmsMode();
        
        // 如果系统设置有值，按系统设置执行
        if (smsMode != null && !smsMode.trim().isEmpty()) {
            // 生成验证码
            String code = RandomCodeUtil.generateRandomCode();
            
            try {
                if ("test".equals(smsMode)) {
                    // 测试模式：调用短信服务但不真实发送，返回验证码到前端
                    smsService.sendMessage(phoneNumber, code);
                    int expireSeconds = smsProperties.getExpireMinutes() * 60;
                    redisUtil.set(redisKey, code, expireSeconds);
                    log.info("测试模式验证码：{}", code);
                    return code;
                } else {
                    // 真实模式：真实发送短信，不返回验证码到前端
                    smsService.sendMessage(phoneNumber, code);
                    int expireSeconds = smsProperties.getExpireMinutes() * 60;
                    redisUtil.set(redisKey, code, expireSeconds);
                    return null;
                }
            } catch (Exception e) {
                log.error("发送短信失败", e);
                throw new ApiException(e.getMessage());
            }
        }
        
        // 系统设置为空，回退到配置文件设置
        String code = RandomCodeUtil.generateRandomCode();
        
        if (!smsProperties.isEnabled()) {
            // 短信功能未启用，本地测试模式，验证码存入redis，有效期2分钟
            log.info("短信功能未启用，本地测试验证码：{}", code);
            redisUtil.set(redisKey, code, 120);
            return code;
        } else {
            // 配置文件启用短信功能，默认真实发送
            try {
                smsService.sendMessage(phoneNumber, code);
                int expireSeconds = smsProperties.getExpireMinutes() * 60;
                redisUtil.set(redisKey, code, expireSeconds);
                return null;
            } catch (Exception e) {
                log.error("发送短信失败", e);
                throw new ApiException(e.getMessage());
            }
        }
    }

    @Override
    public void checkCode(PhoneLoginDto phoneLogin) {
        String code = phoneLogin.getCode();
        String phoneNumber = phoneLogin.getPhoneNumber();
        String redisKey = "SMS_CODE:" + phoneNumber;
        String smsCode = (String) redisUtil.get(redisKey);
        
        // 验证码是否存在
        if (smsCode == null) {
            throw new ApiException("验证码已过期或不存在");
        }
        
        // 验证码是否正确
        if (!code.equals(smsCode)) {
            throw new ApiException("验证码有误");
        }
        
        // 验证成功后，删除redis中的验证码
        redisUtil.del(redisKey);
    }
}
