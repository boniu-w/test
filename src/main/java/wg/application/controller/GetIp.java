package wg.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/*************************************************************
 * @Package wg.application.controller
 * @author wg
 * @date 2020/9/15 18:29
 * @version
 * @Copyright
 *************************************************************/
@RestController
@RequestMapping(value = "/getIp")
public class GetIp {


    /****************************************************************
     * get ip without port
     * @author: wg
     * @time: 2020/9/15 18:31
     ****************************************************************/
    @RequestMapping(value = "/getIpAddr")
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equals(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equals(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equals(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
