package com.spzx.common.log.annotation;

import com.spzx.common.log.aspect.LogAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于启用 LogAspect 切面类
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = LogAspect.class)    //通过Import注解导入日志切面类到Spring容器中
public @interface EnableLogAspect {
    
}