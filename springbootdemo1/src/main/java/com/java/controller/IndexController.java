package com.java.controller;

import com.java.globalexception.BizException;
import com.java.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: springbootdemo
 * @Package: com.java.controller
 * @ClassName: IndesController
 * @Author: Administrator
 * @Description: ${description} 页面类
 * @Date: 2018/12/21 16:49
 * @Version: 1.0
 */
@Controller
public class IndexController {
	@RequestMapping(value = "login")
	public String login(){
		String s="";
		if(StringUtil.isEmptyOrNull(s)){
			throw new BizException("-1","参数不能为空!");
		}
        System.out.println(2/0);
		return "login";
	}
}
