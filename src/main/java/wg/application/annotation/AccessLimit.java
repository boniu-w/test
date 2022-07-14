package wg.application.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/************************************************************************
 * @author: wg
 * @description: 解决 接口恶意刷新和暴力请求
 * @params:
 * @return:
 * @createTime: 11:07  2022/7/11
 * @updateTime: 11:07  2022/7/11
 ************************************************************************/
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {

    int seconds();

    int maxCount();

    boolean needLogin() default true;
}