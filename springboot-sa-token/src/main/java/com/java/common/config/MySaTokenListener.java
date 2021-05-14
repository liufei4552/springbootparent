package com.java.common.config;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassNameMySaTokenListener
 * @Description
 * @Author liufei
 * @Date2021/5/14 16:22
 * @Version V1.0
 **/
@Component
@Slf4j
public class MySaTokenListener implements SaTokenListener {
    /**
     * 每次登录时触发
     *
     * @param loginKey   账号类别
     * @param loginId    账号id
     * @param loginModel 登录参数
     */
    @Override
    public void doLogin(String loginKey, Object loginId, SaLoginModel loginModel) {
        log.info("登录log={},{},{}",loginKey,loginId,loginModel);
    }

    /**
     * 每次注销时触发
     *
     * @param loginKey   账号类别
     * @param loginId    账号id
     * @param tokenValue token值
     */
    @Override
    public void doLogout(String loginKey, Object loginId, String tokenValue) {
        log.info("退出log={},{},{}",loginKey,loginId,tokenValue);
    }

    /**
     * 每次被踢下线时触发
     *
     * @param loginKey   账号类别
     * @param loginId    账号id
     * @param tokenValue token值
     * @param device     设备标识
     */
    @Override
    public void doLogoutByLoginId(String loginKey, Object loginId, String tokenValue, String device) {
        log.info("被踢下线log={},{},{},{}",loginKey,loginId,tokenValue,device);
    }

    /**
     * 每次被顶下线时触发
     *
     * @param loginKey   账号类别
     * @param loginId    账号id
     * @param tokenValue token值
     * @param device     设备标识
     */
    @Override
    public void doReplaced(String loginKey, Object loginId, String tokenValue, String device) {
        log.info("被顶下线log={},{},{},{}",loginKey,loginId,tokenValue,device);
    }

    /**
     * 每次被封禁时触发
     *
     * @param loginKey    账号类别
     * @param loginId     账号id
     * @param disableTime 封禁时长，单位: 秒
     */
    @Override
    public void doDisable(String loginKey, Object loginId, long disableTime) {
        log.info("被封禁log={},{},{}",loginKey,loginId,disableTime);
    }

    /**
     * 每次被解封时触发
     *
     * @param loginKey 账号类别
     * @param loginId  账号id
     */
    @Override
    public void doUntieDisable(String loginKey, Object loginId) {
        log.info("被解封log={},{}",loginKey,loginId);
    }

    /**
     * 每次创建Session时触发
     *
     * @param id SessionId
     */
    @Override
    public void doCreateSession(String id) {
        log.info("创建Sessionlog={}",id);
    }

    /**
     * 每次注销Session时触发
     *
     * @param id SessionId
     */
    @Override
    public void doLogoutSession(String id) {
        log.info("注销Sessionlog={}",id);
    }
}
