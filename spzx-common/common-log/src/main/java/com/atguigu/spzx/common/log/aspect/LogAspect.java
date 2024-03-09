package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.service.LogService;
import com.atguigu.spzx.common.log.util.LogUtil;
import com.atguigu.spzx.model.entity.log.LogEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 声明切面类，自定义通知
 */

@Aspect //定义切面类
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private LogService logService;

    // 定义环绕通知，ProceedingJoinPoint 包含代理类和被代理类的信息
    @Around(value = "@annotation(log)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, com.atguigu.spzx.common.log.annotation.Log log) {

        LogEntity logEntity = new LogEntity();   //
        LogUtil.beforeHandleLog(log, joinPoint, logEntity);
        Object proceed = null;  // 储存业务方法的执行结果

        try {
            proceed = joinPoint.proceed();  // 执行业务方法
            LogUtil.afterHandleLog(log, proceed, logEntity, 0, null);    // 构建响应结果参数
        } catch (Throwable e) {
            e.printStackTrace();
            LogUtil.afterHandleLog(log, proceed, logEntity, 1, e.getMessage());
            throw new RuntimeException();   // 此处需要抛出异常，避免事务失效问题
        }

        logService.saveLog(logEntity); // 不论操作失败或成功都需要保存日志数据
        return proceed;    // 返回执行结果
    }
}