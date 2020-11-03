package wg.application.filter.config;

import javax.servlet.*;
import java.io.IOException;

/*************************************************************
 * @Package wg.application.cas.filter
 * @author wg
 * @date 2020/8/25 15:16
 * @version
 * @Copyright
 *************************************************************/
public class CasFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {



        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
