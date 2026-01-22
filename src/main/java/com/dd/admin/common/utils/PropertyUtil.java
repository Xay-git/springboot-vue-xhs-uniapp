package com.dd.admin.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyUtil {

    private static Logger logger = LoggerFactory.getLogger("PropertyUtil");

    private static final String SET_PREFIX = "set";
    private static final String IS_PREFIX = "is";
    private static final String GET_PREFIX = "get";

    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) {//根据需求，定制 自己的get和set方法
        Method setMethod = null;
        Method getMethod = null;
        PropertyDescriptor pd = null;
        try {
            Field field = clazz.getDeclaredField(propertyName);// 根据字段名来获取字段
            if (field != null) {
                // 构建方法的后缀
                String methodEnd = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                setMethod = clazz.getDeclaredMethod(SET_PREFIX + methodEnd, new Class[] { field.getType() });
                // 构建get 方法
                getMethod = clazz.getDeclaredMethod(GET_PREFIX + methodEnd, new Class[] {});
                // 构建一个属性描述器 把对应属性 propertyName 的 get 和 set 方法保存到属性描述器中
                pd = new PropertyDescriptor(propertyName, getMethod, setMethod);
            }
        } catch (Exception ex) {
            logger.error("getPropertyDescriptor error,msg:"+ex.getMessage());
        }

        return pd;
    }

    public static PropertyDescriptor getPropertyDescriptor2(Class<?> clazz, String propertyName) {//使用 PropertyDescriptor 提供的 get和set方法
        try {
            return new PropertyDescriptor(propertyName, clazz);
        } catch (IntrospectionException e) {
            logger.error(" getPropertyDescriptor2 error,msg:"+e.getMessage());
        }
        return null;
    }

    public static void setProperty(Object obj, String propertyName, Object value) {
        Class<?> clazz = obj.getClass();// 获取对象的类型
        propertyName = formatProperty(propertyName);//字段中的下划线处理
        PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);// 获取 clazz类型中的propertyName的属性描述器
        Method setMethod = pd.getWriteMethod();// 从属性描述器中获取 set 方法
        try {
            setMethod.invoke(obj, new Object[] { value });// 调用 set 方法将传入的value值保存属性中去
        } catch (Exception e) {
            logger.error(" setProperty error,msg:"+e.getMessage());
        }
    }

    public static Object getProperty(Object obj, String propertyName) {
        Class<?> clazz = obj.getClass();// 获取对象的类型
        propertyName = formatProperty(propertyName);//字段中的下划线处理
        PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);// 获取clazz类型中的propertyName的属性描述器
        Method getMethod = pd.getReadMethod();// 从属性描述器中获取 get 方法
        Object value = null;
        try {
            value = getMethod.invoke(obj, new Object[] {});// 调用方法获取方法的返回值
        } catch (Exception e) {
            logger.error(" getProperty error,msg:"+e.getMessage());
        }
        return value;// 返回值
    }

    public static String formatProperty(String propertyName) {
        if (propertyName.contains("_")) {
            String[] tmp = propertyName.split("_");
            StringBuilder sb = new StringBuilder();
            sb.append(tmp[0]);
            for (int i = 1; i < tmp.length; i++) {
                sb.append(tmp[i].substring(0, 1).toUpperCase()).append(tmp[i].substring(1));
            }
            return sb.toString();
        }else {
            return propertyName;
        }
    }


}
