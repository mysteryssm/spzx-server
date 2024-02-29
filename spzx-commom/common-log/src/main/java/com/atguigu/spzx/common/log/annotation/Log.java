package com.atguigu.spzx.common.log.annotation;

import com.atguigu.spzx.model.globalEnum.OperatorType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ljl
 * @create 2023-10-31-23:40
 */

@Target(ElementType.METHOD)   //指定注解使用的对象，此处表明注解可以用于方法
@Retention(RetentionPolicy.RUNTIME) //指定注解的生命周期
public @interface Log {		// 自定义操作日志记录注解

    String title(); // 模块名称
    OperatorType operatorType() default OperatorType.MANAGE;	// 操作人类别
    int businessType(); // 业务类型（0其它 1新增 2修改 3删除）
    boolean isSaveRequestData() default true;   //是否保存请求的参数
    boolean isSaveResponseData() default true;  //是否保存响应的参数

}