package com.kerwin.paysdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动支付注解
 * Created by zhangke on 2018/4/23.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface PayMark {

}
