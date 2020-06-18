package wg.application.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
    public Object encryptUserName(ProceedingJoinPoint joinPoint) {
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = "";
        try {
            Object arg = joinPoint.getArgs()[0];
            Object proceed = joinPoint.proceed();

            if (arg instanceof String) {
                encode = encoder.encode(((String) arg).getBytes());
                System.out.println("encode: " + encode);
                arg = encode;
            }
            return arg;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    //@After("deciphering()")
    public void dobefore(JoinPoint joinPoint) {
        BASE64Encoder encoder = new BASE64Encoder();


        String arg = (String) joinPoint.getArgs()[0];

        arg = encoder.encode(arg.getBytes());
        System.out.println("encode: " + arg);


    }

    /*************************************************************
     *  解密
     * @author: wg
     * @time: 2020/4/28 13:09
     *************************************************************/
    @After("deciphering()")
    public void decipheringUserName(JoinPoint joinPoint) {
        BASE64Decoder decoder = new BASE64Decoder();
        Object arg = joinPoint.getArgs()[0];
        System.out.println("decipheringUserName: " + arg);
        try {
            if (arg instanceof String) {
                byte[] bytes = decoder.decodeBuffer((String) arg);
                String s = new String(bytes);
                System.out.println("s: " + s);
                arg = s;
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }



}
