package com.java.common.annotationImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.common.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
* 功能描述: 拦截自定义注解并解析参数
 * @param
* @return:
* @Author: liufei
* @Date: 2021/4/20 12:57
*/
@Slf4j
public class ParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    //自定义key
    private static final String KEY = "TEST_JSON_BODY_KEY";
    private static ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Param.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Param jsonFmt = parameter.getParameterAnnotation(Param.class);
        JSONObject jsonObject = getJsonObject(webRequest);

        String value = getParamName(parameter,jsonFmt);
        boolean require = jsonFmt.require();
        Object paramValue = getParamValue(jsonObject,value);

        if (paramValue == null && require) {
            throw new Exception("parameter[" + value + "]不能为空。");
        }
        if (paramValue == null) {
            return null;
        }

        Class<?> classType = parameter.getParameterType();

        if (paramValue.getClass().equals(JSONObject.class)){
            paramValue = objectMapper.readValue(paramValue.toString(),classType);
        }

        return paramValue;
    }

    private String getParamName(MethodParameter parameter, Param jsonFmt) {
        String value = jsonFmt.value();
        if (StringUtils.isEmpty(value)) {
            value = parameter.getParameterName();
        }
        return value;
    }

    private Object getParamValue(JSONObject jsonObject,String value) {
        for (String key: jsonObject.keySet()) {
            if(key.equalsIgnoreCase(value)){
                return jsonObject.get(key);
            }
        }
        return null;
    }

    private JSONObject getJsonObject(NativeWebRequest webRequest) throws Exception {
        String jsonBody = (String) webRequest.getAttribute(KEY, NativeWebRequest.SCOPE_REQUEST);
        if(StringUtils.isEmpty(jsonBody)){
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024];
            int rd;
            while ((rd = reader.read(buf)) != -1) {
                sb.append(buf, 0, rd);
            }

            jsonBody = sb.toString();

            if(StringUtils.isEmpty(jsonBody)){
                Map<String,String[]> params = request.getParameterMap();

                Map tmp = new HashMap();
                for (Map.Entry<String,String[]> param:params.entrySet()) {
                    if(param.getValue().length == 1){
                        tmp.put(param.getKey(),param.getValue()[0]);
                    }else{
                        tmp.put(param.getKey(),param.getValue());
                    }

                }
                jsonBody = JSON.toJSONString(tmp);
            }

            webRequest.setAttribute(KEY, jsonBody, NativeWebRequest.SCOPE_REQUEST);
        }

        return JSONObject.parseObject(jsonBody);
    }
}