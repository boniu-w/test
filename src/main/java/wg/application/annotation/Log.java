package wg.application.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

/**
 * @author wg
 * @Package wg.application.annotation
 * @date 2020/4/28 13:54
 * @Copyright
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    @AliasFor("name")
    String value() default "";

    String createTime() default "";

    @AliasFor("value")
    String name() default "";

}
