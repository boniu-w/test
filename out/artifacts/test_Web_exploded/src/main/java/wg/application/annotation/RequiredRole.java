package wg.application.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*************************************************************
 * @Package wg.application.annotation
 * @author wg
 * @date 2020/8/7 10:47
 * @version
 * @Copyright
 *************************************************************/
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredRole {

    String[] value();

}
