package wg.application.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author wg
 * @Package wg.application.aspect
 * @date 2020/4/28 13:11
 * @Copyright
 */
@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(public * wg.application.service.AspectService.add(..))")
    public void deciphering() {
    }


    /*************************************************************
     * 加密
     * @author: wg
     * @time: 2020/4/28 13:16
     *************************************************************/
    //@Before("deciphering()")
//    public Object encryptUserName(ProceedingJoinPoint joinPoint) {
//        BASE64Encoder encoder = new BASE64Encoder();
//        String encode = "";
//        try {
//            Object arg = joinPoint.getArgs()[0];
//            Object proceed = joinPoint.proceed();
//
//            if (arg instanceof String) {
//                encode = encoder.encode(((String) arg).getBytes());
//                System.out.println("encode: " + encode);
//                arg = encode;
//            }
//            return arg;
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        return null;
//    }

    //@After("deciphering()")
//    public void dobefore(JoinPoint joinPoint) {
//        BASE64Encoder encoder = new BASE64Encoder();
//
//
//        String arg = (String) joinPoint.getArgs()[0];
//
//        arg = encoder.encode(arg.getBytes());
//        System.out.println("encode: " + arg);
//
//
//    }

    /*************************************************************
     *  解密
     * @author: wg
     * @time: 2020/4/28 13:09
     *************************************************************/
//    @After("deciphering()")
//    public void decipheringUserName(JoinPoint joinPoint) {
//        BASE64Decoder decoder = new BASE64Decoder();
//        Object arg = joinPoint.getArgs()[0];
//        System.out.println("decipheringUserName: " + arg);
//        try {
//            if (arg instanceof String) {
//                byte[] bytes = decoder.decodeBuffer((String) arg);
//                String s = new String(bytes);
//                System.out.println("s: " + s);
//                arg = s;
//            }
//        } catch (IOException io) {
//            io.printStackTrace();
//        }
//    }

    @Pointcut("execution(public * wg.application.controller.AopTestController.add(..))")
    public void executeAdd() {
    }

    /****************************************************************
     * before 暂时改变不了参数
     * @author: wg
     * @time: 2020/7/29 17:29
     ****************************************************************/
    @Before(value = "executeAdd()")
    public Object execute(JoinPoint joinPoint) {
        Object arg = joinPoint.getArgs()[0];

        String name = "xiaohei";
        arg = name;
        System.out.println("[][][][][][][]");
        return arg;
    }

    /****************************************************************
     * around 可以改变参数, 且必须有 返回值
     * @author: wg
     * @time: 2020/7/29 18:59
     ****************************************************************/
    @Around(value = "executeAdd()")
    public Object executeAdd(ProceedingJoinPoint point) throws Throwable {
        Object[] arg = point.getArgs();

        arg[0] = "-----";
        arg[1] = "=====";


        return point.proceed(arg);

    }


}
