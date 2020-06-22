package wg.application.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/*************************************************************
 * @Package wg.application.filter
 * @author wg
 * @date 2020/6/22 15:22
 * @version
 * @Copyright
 *************************************************************/
@Component
public class WgCharsetFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("WgCharsetFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        //servletRequest.
        servletResponse.setCharacterEncoding("utf-8");

        System.out.println("servletRequest.getCharacterEncoding() -> "+servletRequest.getCharacterEncoding());
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        System.out.println("WgCharsetFilter 销毁");
    }
}
