package com.dd.admin.common.utils;


import java.util.List;

public class CommonUtil {

    /**
     * 获取list 中的id 通过,相连
     * @return
     */
    public static String getIds(List list,String idKey){
        StringBuffer stringBuffer = new StringBuffer();
        if(list==null||list.size()==0){
            return "''";
        }else{
            for(Object obj:list){
                String val = String.valueOf(PropertyUtil.getProperty(obj,idKey));
                stringBuffer.append("'"+val+"',");
            }
        }
        return stringBuffer.deleteCharAt(stringBuffer.length()-1).toString();
    }

    /**
     * 获取list 中的id 通过,相连
     * @return
     */
    public static String coverIds(List<String> list){
        StringBuffer stringBuffer = new StringBuffer();
        if(list==null||list.size()==0){
            return "''";
        }else{
            for(String val:list){
                stringBuffer.append("'"+val+"',");
            }
        }
        return stringBuffer.deleteCharAt(stringBuffer.length()-1).toString();
    }
}
