package com.java.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.java.common.annotation.Param;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.acl.LastOwnerException;
import java.util.List;

/**
 * @ClassNameUserController
 * @Description
 * @Author liufei
 * @Date2021/5/14 13:22
 * @Version V1.0
 **/
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private StpInterface stpInterface;

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public String doLogin(@Param("username")String username,@Param("password")String password){
        log.info("username={},password={}",username,password);
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.setLoginId(10001);
            String loginIdByToken = StpUtil.getTokenValue();
            List<String> permissionList = stpInterface.getPermissionList(10001, loginIdByToken);
            log.info("我的权限:{}",permissionList);
            List<String> roleList = stpInterface.getRoleList(10001, loginIdByToken);
            log.info("我的角色:{}",roleList);
            return loginIdByToken;
        }
        return "登录失败";
    }
    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping(value = "/isLogin",method = RequestMethod.POST)
    @SaCheckLogin
    public String isLogin(@Param("uername")String username,@Param("password")String password) {
        log.info("判断是否登录...................");
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @SaCheckLogin
    public String logout(){
        StpUtil.logout();
        return "退出成功";
    }
    @RequestMapping(value = "/hasPermission",method = RequestMethod.GET)
    @SaCheckLogin
    public boolean hasPermission(){
        // 当前账号是否含有指定权限, 返回true或false
        boolean b = StpUtil.hasPermission("user-update");
        return b;
    }
}
