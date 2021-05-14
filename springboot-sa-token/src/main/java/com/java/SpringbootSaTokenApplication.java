package com.java;

import cn.dev33.satoken.SaManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassNameSpringbootSaToken
 * @Description
 * @Author liufei
 * @Date2021/5/14 12:00
 * @Version V1.0
 **/
@SpringBootApplication
@Slf4j
public class SpringbootSaTokenApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootSaTokenApplication.class,args);
        log.info("启动成功：sa-token配置如下：{}" + SaManager.getConfig());
    }
}
