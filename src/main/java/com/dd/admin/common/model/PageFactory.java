package com.dd.admin.common.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.admin.common.utils.HttpContext;
import com.dd.admin.common.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 生成默认分页数据
 */
public class PageFactory {

    private static final int defaultLimit = 20;

    public static Page defaultPage() {
        HttpServletRequest request = HttpContext.getRequest();
        //第几页
        int page;
        String pg = request.getParameter("page");
        if(StringUtil.isEmpty(pg)){
            page = 1;
        }else{
            page= Integer.valueOf(pg);
        }
        //每页显示多少条数据
        int limit;
        String lim  = request.getParameter("limit");
        if(StringUtil.isEmpty(lim)){
            limit = defaultLimit;
        }else{
            limit = Integer.valueOf(lim);
        }
        return new Page(page, limit);
    }
}
