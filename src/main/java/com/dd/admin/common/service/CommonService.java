package com.dd.admin.common.service;

import java.util.List;

/**

 * Description:

 * date: 2021/6/15

 * @author: wxl

 */
public interface CommonService {

    public <T> List<T> generateSubs(List<T> list, String idkey);

    /**
     * 递归构建
     * @param parent
     * @param subs
     */
    public <T> void buildSubs(T parent, List<T> subs,String idkey);


}
