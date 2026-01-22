package com.dd.admin.common.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.dd.admin.common.security.SecurityUtil;
import com.dd.admin.common.security.model.JwtUser;
import com.dd.admin.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        log.info("start insert fill ....");
        // 移除无条件设置createTime，改为在后面检查时设置

        try {
            JwtUser jwtUser = SecurityUtil.getLoginUser();

            if(jwtUser!=null){
                String deptId = String.valueOf(getFieldValByName("deptId",metaObject));
                if(StringUtil.isEmpty(deptId)){
                    setFieldValByName("deptId",jwtUser.getDeptId(), metaObject);
                }

                String deptName = String.valueOf(getFieldValByName("deptName",metaObject));
                if(StringUtil.isEmpty(deptName)){
                    setFieldValByName("deptName",jwtUser.getDeptName(), metaObject);
                }

                String shopId = String.valueOf(getFieldValByName("shopId",metaObject));
                if(StringUtil.isEmpty(shopId)){
                    setFieldValByName("shopId",jwtUser.getDeptId(), metaObject);
                }

                String shopName = String.valueOf(getFieldValByName("shopName",metaObject));
                if(StringUtil.isEmpty(shopName)){
                    setFieldValByName("shopName",jwtUser.getDeptName(), metaObject);
                }

                String createId = String.valueOf(getFieldValByName("createId",metaObject));
                if(StringUtil.isEmpty(createId)){
                    setFieldValByName("createId",jwtUser.getUserId(), metaObject);
                }

                String createName = String.valueOf(getFieldValByName("createName",metaObject));
                if(StringUtil.isEmpty(createName)){
                    setFieldValByName("createName",jwtUser.getUsername(), metaObject);
                }

                try {
                    Date createTime = (Date) getFieldValByName("createTime",metaObject);
                    System.out.println("自动填充createTime" + createTime);

                    if(createTime == null){
                        setFieldValByName("createTime",new Date(), metaObject);
                    }else {
                        setFieldValByName("updateTime",new Date(), metaObject);
                    }
                }catch (Exception e){
                    System.out.println("获取createTime失败");
                    setFieldValByName("createTime",new Date(), metaObject);
                }

            }else{
                try {
                    Date createTime = (Date) getFieldValByName("createTime",metaObject);
                    System.out.println("自动填充createTime" + createTime);

                    if(createTime == null){
                        setFieldValByName("createTime",new Date(), metaObject);
                    }else {
                        setFieldValByName("updateTime",new Date(), metaObject);
                    }
                }catch (Exception e){
                    System.out.println("获取createTime失败");
                    setFieldValByName("createTime",new Date(), metaObject);
                }
            }
        }catch (Exception e){
            log.error("自动填充发生错误",e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        setFieldValByName("updateTime",new Date(), metaObject);

        JwtUser jwtUser = SecurityUtil.getLoginUser();

        try {
            String updateId = String.valueOf(getFieldValByName("updateId",metaObject));
            if(StringUtil.isEmpty(updateId)){
                setFieldValByName("updateId",jwtUser.getUserId(), metaObject);
            }

            String updateName = String.valueOf(getFieldValByName("updateName",metaObject));
            if(StringUtil.isEmpty(updateName)){
                setFieldValByName("updateName",jwtUser.getUsername(), metaObject);
            }
        }catch (Exception e){
            log.error("自动填充发生错误",e);
        }
    }
}
