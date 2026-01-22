package com.dd.admin.system.login;

import cn.hutool.core.bean.BeanUtil;
import com.dd.admin.common.aop.operationLog.aop.OperLog;
import com.dd.admin.common.aop.operationLog.aop.OperType;
import com.dd.admin.common.exception.ApiException;
import com.dd.admin.common.model.result.ResultBean;
import com.dd.admin.common.security.SecurityUtil;
import com.dd.admin.common.security.jwt.JwtTokenUtil;
import com.dd.admin.common.security.model.JwtUser;
import com.dd.admin.common.service.CommonService;
import com.dd.admin.common.utils.ColumnUtil;
import com.dd.admin.system.login.domain.LoginDto;
import com.dd.admin.system.login.domain.LoginVo;
import com.dd.admin.system.menu.domain.MenuVo;
import com.dd.admin.system.menu.domain.Meta;
import com.dd.admin.system.menu.domain.RouteVo;
import com.dd.admin.system.menu.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dd.admin.common.consts.SystemConst.AUTH_MENU;
import static com.dd.admin.common.consts.SystemConst.MENU_CACHE;
import static com.dd.admin.common.exception.enums.ApiExceptionEnum.USERNAME_OR_PASSWORD_ERROR;

@Api(tags = "登陆")
@RestController
public class LoginController {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    MenuService menuService;
    @Autowired
    CommonService commonService;

    @RequestMapping("/admin/login")
    @OperLog(operModule = "登陆",operType = OperType.OTHER,operDesc = "后台人员登陆操作")
    public ResultBean login(@RequestBody LoginDto loginDto){
        final JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(loginDto.getUsername());
        String password =  loginDto.getPassword();
        Boolean b = encoder.matches(password,userDetails.getPassword());
        if(!b){
            throw new ApiException(USERNAME_OR_PASSWORD_ERROR);
        }
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResultBean.success(new LoginVo().setToken(token));
    }

    @RequestMapping("/admin/user/info")
    public ResultBean info(){
        JwtUser jwtUser = SecurityUtil.getLoginUser();
        return ResultBean.success(jwtUser);
    }

    @RequestMapping("/admin/user/logout")
    public ResultBean logout(){
        return ResultBean.success();
    }

    @RequestMapping("/admin/user/getroutes")
    public ResultBean getroutes(){
        List<MenuVo> menuVoList = menuService.selectMenuByUserId(SecurityUtil.getLoginUser().getUserId());
        menuVoList = menuVoList.stream().filter(menuVo -> menuVo.getMenuType().equals(AUTH_MENU)).collect(Collectors.toList());
        menuVoList.add(new MenuVo().setMenuId("0").setMenuTitle("顶级"));
        List<RouteVo> list = menuVoList.stream().map(menuVo -> {
            RouteVo routeVo = BeanUtil.copyProperties(menuVo, RouteVo.class);
            routeVo.setName(menuVo.getMenuName());
            routeVo.setPath(menuVo.getMenuPath());
            Meta meta = new Meta(menuVo.getMenuTitle(),menuVo.getMenuIcon(),!MENU_CACHE.equals(menuVo.getMenuCache()));
            routeVo.setMeta(meta);
            return routeVo;
        }).collect(Collectors.toList());

        List<RouteVo> router = commonService.generateSubs(list, ColumnUtil.getName(RouteVo::getMenuId));
        if(router.get(0).getChildren()!=null){
            return ResultBean.success(router.get(0).getChildren());
        }
        return ResultBean.success(new ArrayList<>());
    }
}
