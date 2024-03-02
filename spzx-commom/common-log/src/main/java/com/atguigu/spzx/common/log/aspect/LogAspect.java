package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.common.log.util.LogUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect //定义切面类
@Component
@Slf4j
public class LogAspect {            // 环绕通知切面类定义

    @Autowired
    private AsyncOperLogService asyncOperLogService;

    @Around(value = "@annotation(log)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log log) {

        SysOperLog sysOperLog = new SysOperLog();   // 构建前置参数
        LogUtil.beforeHandleLog(log, joinPoint, sysOperLog);
        Object proceed = null;

        try {
            proceed = joinPoint.proceed();  // 执行业务方法
            LogUtil.afterHandlLog(log, proceed, sysOperLog, 0, null);    // 构建响应结果参数
        } catch (Throwable e) {                                 // 代码执行进入到catch中，
            // 业务方法执行产生异常
            e.printStackTrace();                                // 打印异常信息
            LogUtil.afterHandlLog(log, proceed, sysOperLog, 1, e.getMessage());
            throw new RuntimeException();
        }

        asyncOperLogService.saveSysOperLog(sysOperLog); // 保存日志数据

        return proceed;    // 返回执行结果
    }
}