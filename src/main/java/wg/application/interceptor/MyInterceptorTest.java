package wg.application.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import wg.application.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/************************************************************************
 * author: wg
 * description: 拦截器 拦截的是 controller
 * createTime: 11:02 2023/3/13
 * updateTime: 11:02 2023/3/13
 ************************************************************************/
@Component
public class MyInterceptorTest extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("更新拦截器");
        //判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.getName().equals("testUpdateInterceptor")) {
                Object arg = handlerMethod.getMethodParameters()[0];
                // arg 就是 updateTest() 方法的参数值
                // 处理参数值的逻辑
                if (CommonUtil.hasField(arg, "updateTime")) {
                    CommonUtil.setter(arg, "updateTime", LocalDateTime.now());
                }
            }
        }

        return true;
    }
}
