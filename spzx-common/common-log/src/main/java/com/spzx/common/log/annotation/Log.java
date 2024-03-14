package com.spzx.common.log.annotation;

import com.spzx.model.globalConstant.OperatorTypeEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义日志记录注解
 * @create 2023-10-31-23:40
 */

@Target(ElementType.METHOD)   //指定注解使用的对象，此处表明注解可以用于方法
@Retention(RetentionPolicy.RUNTIME) //指定注解的生命周期
public @interface Log {

    String title(); // 模块名称
    OperatorTypeEnum operatorType() default OperatorTypeEnum.MANAGE;	// 操作人类别，默认为管理员
    int businessType(); // 业务类型（-1 登出 0 登录 1 新增 2 删除 3 修改）
    boolean isSaveRequestData() default true;   //是否保存请求的参数
    boolean isSaveResponseData() default true;  //是否保存响应的参数
}