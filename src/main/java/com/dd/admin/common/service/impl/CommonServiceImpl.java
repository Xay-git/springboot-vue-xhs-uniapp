package com.dd.admin.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dd.admin.common.service.CommonService;
import com.dd.admin.common.utils.PropertyUtil;
import com.dd.admin.common.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonServiceImpl implements CommonService {
    private final static String PARENTID = "parentId";
    private final static String CHILDREN = "children";



    @Override
    public <T> List<T> generateSubs(List<T> list ,String idkey) {
        //JDK8的stream处理,把根分类区分出来
        List<T> roots = list.stream()
                .filter(root ->{
                    String parentId = String.valueOf(PropertyUtil.getProperty(root,PARENTID));
//                    ||parentId.equals("0")
                    return StringUtil.isEmpty(parentId);
                })
                .collect(Collectors.toList());
        //把非根分类区分出来
        List<T> subs =  list.stream()
                .filter(sub -> {
                    String parentId = String.valueOf(PropertyUtil.getProperty(sub,PARENTID));
                    return StringUtil.isNotEmpty(parentId);
                })
                .collect(Collectors.toList());

        roots.forEach(root -> {
            buildSubs(root,subs, idkey);
        });

        return roots;
    }

    @Override
    public  <T> void buildSubs( T parent, List<T> subs, String idkey) {
        List<T> children =  subs.stream()
                .filter(sub -> {
                    String parentId = String.valueOf(PropertyUtil.getProperty(sub,PARENTID));
                    String id = String.valueOf(PropertyUtil.getProperty(parent,idkey));
                    return parentId.equals(id);
                }).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(children)) {//有子分类的情况
            PropertyUtil.setProperty(parent,CHILDREN,children);
            children.forEach(child -> buildSubs(child, subs, idkey));//再次递归构建
        }
    }
}
