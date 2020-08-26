package wg.application.cas.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/*************************************************************
 * @Package wg.application.cas.controller
 * @author wg
 * @date 2020/8/25 15:15
 * @version
 * @Copyright
 *************************************************************/
@RequestMapping(value = "/casController")
@RestController
public class CasController {


    @RequestMapping(value = "/test1")
    public void test1(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);  // /casController/test1

        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURL);  // http://localhost:33333/casController/test1


        String queryString = request.getQueryString();
        System.out.println(queryString);
    }

    /****************************************************************
     * 获取当前用户
     * @author: wg
     * @time: 2020/8/25 17:26
     ****************************************************************/
    @RequestMapping(value = "/getCurrentUser")
    public void getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();

        String toString = principal.toString();
        System.out.println(toString);

    }

}
