package com.dd.admin.business.api;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dd.admin.business.api.domain.PhoneLoginDto;
import com.dd.admin.business.api.service.LoginService;
import com.dd.admin.business.author.entity.Author;
import com.dd.admin.business.author.service.AuthorService;
import com.dd.admin.business.note.domain.NoteDto;
import com.dd.admin.business.note.domain.NoteVo;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.security.jwt.JwtTokenUtil;
import com.dd.admin.common.utils.RedisUtil;
import com.dd.admin.common.utils.StringUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginApi {

    @Autowired
    LoginService loginService;
    @Autowired
    AuthorService authorService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    HttpServletRequest request;

    @ApiOperation(value = "获取验证码")
    @ApiOperationSupport(order = 1)
    @GetMapping("/api/getCode")
    @OperLog(operModule = "获取验证码",operType = OperType.QUERY,operDesc = "获取验证码")
    public ResultBean<String> getCode(String phoneNumber) {
        String code = loginService.sendSmsCode(phoneNumber);
        if (code == null) {
            // 真实发送短信时，返回成功提示信息
            return ResultBean.success("验证码已发送，请注意查收");
        }
        // 本地测试时，返回验证码
        return ResultBean.success(code);
    }

    @ApiOperation(value = "验证码登陆")
    @ApiOperationSupport(order = 1)
    @PostMapping("/api/checkCode")
    @Transactional
    @OperLog(operModule = "获取验证码",operType = OperType.OTHER,operDesc = "验证码登陆")
    public ResultBean<Author> checkCode(@RequestBody  PhoneLoginDto phoneLoginDto) {
//        loginService.checkCode(phoneLoginDto);
       //验证后登陆
        Author author = authorService.selectAuthorByPhoneNumber(phoneLoginDto.getPhoneNumber());
        if(author==null){
            author = authorService.createNewAuthor(phoneLoginDto.getPhoneNumber());
        }else if(author.getDeleted().equals(1)){
            throw new ApiException("当前用户状态异常~");
        }
//        else if(author.getAuthorStatus().equals(1)){
//            throw new ApiException("当前用户已冻结~");
//        }
        //根据用户id生成token
        final String token = jwtTokenUtil.generateTokenByUserId(author.getAuthorId());
        author.setToken(token);
        return ResultBean.success(author);
    }


    @GetMapping(value = "/api/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @OperLog(operModule = "流式数据", operType = OperType.QUERY, operDesc = "获取流式数据")
    public SseEmitter streamData() {
        // 创建 SSE 发射器，设置超时时间（例如 1 分钟）
        SseEmitter emitter = new SseEmitter(60_000L);
        // 创建新线程，防止主程序阻塞
        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(1000); // 模拟延迟
                    // 发送数据
                    emitter.send("time=" + System.currentTimeMillis());
                }
                // 发送完毕
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }

}
