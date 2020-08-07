package wg.application.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wg.application.annotation.RequiredRole;
import wg.application.service.impl.RoleInterfaceImpl;
import wg.application.vo.Result;

import java.lang.reflect.Method;

/*************************************************************
 * @Package wg.application.aspect
 * @author wg
 * @date 2020/8/7 11:15
 * @version
 * @Copyright
 *************************************************************/
@Aspect
@Component
public class RoleAspect {


    @Autowired
    RoleInterfaceImpl roleInterface;

    @Pointcut("@annotation(wg.application.annotation.RequiredRole)")
    public void requiredRole() {
    }

    /****************************************************************
     * 如果不抛出异常, 无法阻止controller 执行方法内部的逻辑,也就是,这个注解的本意没有达成
     * 这个aop就没有意义
     * @author: wg
     * @time: 2020/8/7 15:32
     ****************************************************************/
    @Around("requiredRole()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean b = false;
        String value = "";
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        RequiredRole requiredRole = method.getAnnotation(RequiredRole.class);
        String[] values = requiredRole.value();
        for (int i = 0; i < values.length; i++) {

            // 注解的value 值
            value = values[i];
            // 根据value 查询数据库中角色
            String[] roleCodes = roleInterface.getRoleCode();
            for (int j = 0; j < roleCodes.length; j++) {
                if (roleCodes[j].equals(value)) {
                    b = true;
                }

            }

        }

        if (b == true) {
            System.out.println("????   " + joinPoint.proceed().toString());
            return joinPoint.proceed();
        } else {
            throw new Exception("required admin ");

            //Result result = (Result) joinPoint.proceed();
            //result.setMessage("required " + value);
            //result.setSuccess(false);
            //
            //return result;
        }

    }


    /****************************************************************
     * 前置通知 还是没啥用 改变不了任何东西
     * @author: wg
     * @time: 2020/8/7 15:01
     ****************************************************************/
    //@Before("requiredRole()")
    public Object doBeforeRequired(JoinPoint joinPoint) {
        boolean b = false;
        String value = "";
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        RequiredRole requiredRole = method.getAnnotation(RequiredRole.class);
        String[] values = requiredRole.value();
        for (int i = 0; i < values.length; i++) {

            // 注解的value 值
            value = values[i];
            // 根据value 查询数据库中角色
            String[] roleCodes = roleInterface.getRoleCode();
            for (int j = 0; j < roleCodes.length; j++) {
                if (roleCodes[j].equals(value)) {
                    b = true;

                }

            }

        }

        if (b == true) {
            System.out.println("true");
            return Result.ok("required " + value);
        } else {
            System.out.println("false");
            return Result.error("required " + value);
        }
    }

}
