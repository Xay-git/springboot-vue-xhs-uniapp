package com.dd.admin.common.utils;

import cn.hutool.core.bean.BeanUtil;
import com.dd.admin.business.dev.entity.Dev;
import com.dd.admin.business.dev.service.DevService;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.system.dept.domain.DeptDto;
import com.dd.admin.system.dept.entity.Dept;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.sun.net.httpserver.HttpsServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class DevController {

    @Autowired
    DevService devService;

    @Autowired
    HttpServletRequest request;

    @PostMapping("/dev/sendMessage")
    public ResultBean<Dept> add(@RequestBody Map map) {
        System.out.println(map);
        Dev dev = new Dev();
        dev.setServerName(String.valueOf(map.get("s")));
        dev.setIpAddress(IPUtils.getIpAddr(request)); // 请求IP
        dev.setIpRealAddress(AddressUtils.getRealAddress(dev.getIpAddress()));
        devService.save(dev);
        return ResultBean.success();
    }
}
