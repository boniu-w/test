package wg.application.filter.config;

import javax.servlet.*;
import java.io.IOException;

/*************************************************************
 * @Package wg.application.filter.config
 * @author wg
 * @date 2020/8/24 16:43
 * @version
 * @Copyright
 *************************************************************/
public class WgFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("=========");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
