package com.dd.admin.common.aop.operationLog.aop;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dd.admin.business.operationLog.entity.OperationLog;
import com.dd.admin.business.operationLog.service.OperationLogService;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.security.SecurityUtil;
import com.dd.admin.common.security.model.JwtUser;
import com.dd.admin.common.utils.AddressUtils;
import com.dd.admin.common.utils.IPUtils;
import com.dd.admin.common.utils.StringUtil;
import com.dd.admin.system.user.domain.UserVo;
import com.dd.admin.system.user.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * 切面处理类，操作日志异常日志记录处理
 *
 * @author wu
 * @date 2019/03/21
 */
@Aspect
@Component
public class OperLogAspect {

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private UserService userService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.dd.admin.common.aop.operationLog.aop.OperLog)")
    public void operLogPoinCut() {
    }

    /**
     * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.dd.admin.business.*.controller..*.*(..))")
    public void operExceptionLogPoinCut() {
    }
    @Pointcut("execution(* com.dd.admin.system.*.controller..*.*(..))")
    public void operExceptionLogPoinCut1() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) throws IOException {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        OperationLog operlog = new OperationLog();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OperLog opLog = method.getAnnotation(OperLog.class);
            if (opLog != null) {
                String operModule = opLog.operModule();
                OperType operType = opLog.operType();
                String operDesc = opLog.operDesc();
                operlog.setOperModule(operModule); // 操作模块
                operlog.setOperType(String.valueOf(operType)); // 操作类型
                operlog.setOperDesc(operDesc); // 操作描述
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;

            operlog.setOperMethod(methodName); // 请求方法


            // 请求的参数
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            String rtnJson = JSON.toJSONString(rtnMap);

            String json =  JSON.toJSONString(joinPoint.getArgs());
            JSONArray jsonArray = JSONUtil.parseArray(json);
            if(StringUtil.isNotEmpty(rtnJson)&&!rtnMap.isEmpty()){
                operlog.setOprrRequestParam(rtnJson); // 请求参数
            }else if(StringUtil.isNotEmpty(json)&& !CollectionUtil.isEmpty(jsonArray)){
                operlog.setOprrRequestParam(json); // 请求参数
            }



            //设置会员信息
            ResultBean resultBean = (ResultBean) keys;

            operlog.setOperResponseParam(JSON.toJSONString(keys)); // 返回结果
            operlog.setOperIp(IPUtils.getIpAddr(request)); // 请求IP
            operlog.setOperIpAddress(AddressUtils.getRealAddress(operlog.getOperIp()));
            operlog.setOperUrl(request.getRequestURI()); // 请求URI

            try {
                JwtUser user = SecurityUtil.getLoginUser();
                if(user!=null){
                    operlog.setOperUserId(user.getUserId()); // 请求用户ID
                    operlog.setOperUserName(user.getUsername()); // 请求用户名称
                    operlog.setOperDeptId(user.getDeptId());
                    operlog.setOperDeptName(user.getDeptName());
                }
            }catch (Exception e){
                Object obj = jsonArray.get(0);
                Map map = BeanUtil.beanToMap(obj);
                String username = String.valueOf(map.get("username"));
                UserVo user = userService.selectOneByUserName(username);
                if(user!=null){
                    operlog.setOperUserId(user.getUserId()); // 请求用户ID
                    operlog.setOperUserName(user.getUserName()); // 请求用户名称
                    operlog.setOperDeptId(user.getDeptId());
                    operlog.setOperDeptName(user.getDeptName());
                    //这里是登陆操作 参数只保存 用户名
                    operlog.setOprrRequestParam(user.getUserName()); // 请求参数
                }
            }
            operationLogService.save(operlog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }
}
