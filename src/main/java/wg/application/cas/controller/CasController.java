package wg.application.cas.controller;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.context.SecurityContext;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

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


    /****************************************************************
     * request
     * @author: wg
     * @time: 2020/8/28 15:11
     ****************************************************************/
    @RequestMapping(value = "/test1")
    public void test1(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);  // /casController/test1

        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURL);  // http://localhost:33333/casController/test1


        String queryString = request.getQueryString();
        System.out.println(queryString);

        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key, value) -> {
            System.out.println(key + ": " + Arrays.toString(value));
        });

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            System.out.println(s);
        }

        String header = request.getHeader("Access-Control-Request-Headers");
        System.out.println("header: " + header);


        String pathInfo = request.getPathInfo();
        System.out.println("pathInfo: " + pathInfo);


    }

    /****************************************************************
     * 获取当前用户
     * @author: wg
     * @time: 2020/8/25 17:26
     ****************************************************************/
    // @RequestMapping(value = "/getCurrentUser")
    // public void getCurrentUser(HttpServletRequest request) {
    //     String name = request.getParameter("username");
    //     String password = request.getParameter("password");
    //     String id = request.getParameter("id");
    //
    //     User user1 = new User();
    //     user1.setName(name);
    //     user1.setId(Long.valueOf(id));
    //
    //     UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
    //             = new UsernamePasswordAuthenticationToken(user1, password);
    //
    //     Object credentials = usernamePasswordAuthenticationToken.getCredentials();
    //     String name1 = usernamePasswordAuthenticationToken.getName();
    //     Object principal1 = usernamePasswordAuthenticationToken.getPrincipal();
    //     Collection<GrantedAuthority> authorities = usernamePasswordAuthenticationToken.getAuthorities();
    //
    //     System.out.println("credentials: " + credentials);
    //     System.out.println("name1: " + name1);
    //     System.out.println("principal1: " + principal1);
    //     System.out.println("authorities: " + authorities);
    //
    //     SecurityContext context = SecurityContextHolder.getContext();
    //     context.setAuthentication(usernamePasswordAuthenticationToken);
    //
    //     Authentication authentication = context.getAuthentication();
    //     Object principal = authentication.getPrincipal();
    //
    //     String toString = principal.toString();
    //     System.out.println(toString);
    //
    //     User user = (User) principal;
    //     System.out.println(user);
    // }
}
