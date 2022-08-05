package wg.application.util;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IP地址
 *
 * @Author scott
 * @email jeecgos@163.com
 * @Date 2019年01月14日
 */
public class IPUtil {
    private static Logger logger = LoggerFactory.getLogger(IPUtil.class);

    public static void main(String[] args) throws Exception {
        System.out.println(IPUtil.getInterIP1());
        System.out.println(IPUtil.getInterIP2());
        System.out.println(IPUtil.getOutIPV4());

    }

    /************************************************************************
     * @author: wg
     * @description: 本机ip
     * @params:
     * @return:
     * @createTime: 10:58  2022/5/5
     * @updateTime: 10:58  2022/5/5
     ************************************************************************/
    public static String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (!StringUtils.isEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }


    public static String getInterIP1() throws Exception {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static String getInterIP2() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        Enumeration<NetworkInterface> netInterfaces;
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

    public static String getOutIPV4() {
        String ip = "";
        String chinaz = "http://ip.chinaz.com";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            while ((read = in.readLine()) != null) {
                inputLine.append(read).append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            String ipstr = m.group(1);
            ip = ipstr;
        }
        return ip;
    }

    /************************************************************************
     * @author: wg
     * @description: 本机ip
     * @params:
     * @return:
     * @createTime: 10:58  2022/5/5
     * @updateTime: 10:58  2022/5/5
     ************************************************************************/
    public static String getIp(HttpServletRequest request) {
        return ServletUtil.getClientIP(request, null);
    }


    /**
     * 根据IP地址获取地理位置
     */
    public static String getAddressByIP(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return "";
        }
        if ("127.0.0.1".equals(ip)) {
            return "局域网，无法获取位置";
        }
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6006&format=json&query=" + ip;
        HttpResponse res = HttpRequest.get(url).execute();
        if (200 != res.getStatus()) {
            return "获取位置失败";
        } else {
            JSONObject resJson = new JSONObject(res.body());
            cn.hutool.json.JSONArray jsonArray = resJson.getJSONArray("data");
            resJson=  new JSONObject(""+jsonArray.get(0));
            return resJson.getStr("location");
        }
    }

    // public static void main(String[] args) {
    //     String ip="42.122.18.64";
    //     String addressByIP = getAddressByIP(ip);
    //     System.out.println(addressByIP);
    // }
}
