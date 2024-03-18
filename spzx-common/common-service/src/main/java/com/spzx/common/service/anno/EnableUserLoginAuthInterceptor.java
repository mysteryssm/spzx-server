package com.spzx.common.service.anno;

import com.spzx.common.service.config.UserWebMvcConfiguration;
import com.spzx.common.service.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Import(value = { UserLoginAuthInterceptor.class, UserWebMvcConfiguration.class})
public @interface EnableUserLoginAuthInterceptor {

}