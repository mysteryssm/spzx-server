package com.spzx.common.log.util;

import com.alibaba.fastjson.JSON;
import com.spzx.common.log.annotation.Log;
import com.spzx.model.entity.admin.LogEntity;
import com.spzx.common.utils.AuthContextUtil;
import io.netty.handler.codec.http.HttpMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 日志记录的工具类
 */

public class LogUtil {

    //操作执行之前调用
    public static void beforeHandleLog(Log log, ProceedingJoinPoint joinPoint, LogEntity logEntity) {

        logEntity.setTitle(log.title());   // 获取操作名称
        logEntity.setOperatorType(log.operatorType().name());  // 获取操作人员类别

        // 获取目标方法信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature() ;
        Method method = methodSignature.getMethod();
        logEntity.setMethod(method.getDeclaringClass().getName());

        // 获取请求相关参数
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        logEntity.setRequestMethod(request.getMethod());
        logEntity.setOperUrl(request.getRequestURI());
        logEntity.setOperIp(request.getRemoteAddr());

        // 设置请求参数
        if(log.isSaveRequestData()) {
            String requestMethod = logEntity.getRequestMethod();
            if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
                String params = Arrays.toString(joinPoint.getArgs());
                logEntity.setOperParam(params);
            }
        }
        logEntity.setOperName(AuthContextUtil.get().getUserName());
    }

    //操作执行之后调用
    public static void afterHandleLog(Log sysLog, Object proceed, LogEntity logEntity, int status, String errorMsg) {
        if(sysLog.isSaveResponseData()) {
            logEntity.setJsonResult(JSON.toJSONString(proceed));
        }
        logEntity.setStatus(status);
        logEntity.setErrorMsg(errorMsg);
    }
}