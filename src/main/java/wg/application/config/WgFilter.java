package wg.application.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wg.application.util.JwtTokenUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*************************************************************
 * @Package wg.application.filter
 * @author wg
 * @date 2020/6/22 15:22
 * @version
 * @Copyright
 *************************************************************/
@Component
public class WgFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(WgFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("WgCharsetFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // System.out.println("request.getHeader(\"Origin\") :  " + request.getHeader("Origin"));
        // System.out.println("Access-Control-Request-Headers :  " + request.getHeader("Access-Control-Request-Headers"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        // response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        // response.setHeader("Access-Control-Allow-Origin", "https://datav.aliyun.com");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));

        String token = "";

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                String name = cookies[i].getName();
                if ("ssoTicket".equals(name)) {

                    // 获取token
                    token = cookies[i].getValue();

                    // 解析 验证token
                    // 先验证是否过期,如果过期 重新生成
                    boolean tokenExpired = JwtTokenUtil.isTokenExpired(token);
                    System.out.println("tokenExpired :  " + tokenExpired);
                    if (tokenExpired) {
                        token = JwtTokenUtil.generateJwtToken();
                    }

                    Jws<Claims> claimsJws = JwtTokenUtil.analyseJwtToken(token);
                    Claims claims = claimsJws.getBody();

                    logger.info("{}{}", "第一个大括号参数: " + claims.toString(), "\n 第二个大括号参数: ");

                }
            }
        } else {

//            response.sendRedirect("");
//            request.getRequestDispatcher("").forward(request,response);
        }
        System.out.println("servletRequest.getCharacterEncoding() -> " + servletRequest.getCharacterEncoding());
        System.out.println();
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        System.out.println("WgCharsetFilter 销毁");
    }
}
