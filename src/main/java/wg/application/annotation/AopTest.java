package wg.application.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * author wg
 * description AopTest
 * createTime 16:07 2025/3/14
 * updateTime 16:07 2025/3/14
 */
@Aspect
@Component
public class AopTest {

    // @Pointcut(value = "execution(public * ceet.ufps.pl.pia.*.service.impl.*.update(..))")
    // public void beforeUpdate() {
    // }
    //
    // @Before(value = "beforeUpdate()")
    // public void setUpdateData(JoinPoint joinPoint) {
    //     Object[] args = joinPoint.getArgs();
    //     if (args != null && args.length > 0) {
    //         Object arg = args[0];
    //         String user = null;
    //         // SysUserDto curUser = SecurityUtils.getUser();
    //         // if (curUser != null) {
    //         //     user = curUser.getUserName();
    //         // }
    //         if (CommonUtils.hasField(arg, "updateTime")) {
    //             CommonUtils.setter(arg, "updateTime", LocalDateTime.now());
    //         }
    //         if (CommonUtils.hasField(arg, "updateUser")) {
    //             CommonUtils.setter(arg, "updateUser", user);
    //         }
    //     }
    // }
}
