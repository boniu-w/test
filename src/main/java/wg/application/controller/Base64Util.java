package wg.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Base64;

/**
 * @author wg
 * @Package wg.application.config
 * @date 2020/4/28 11:44
 * @Copyright
 */
@Controller
public class Base64Util {

//    @RequestMapping(value = "/base64Util")
//    @ResponseBody
//    public String decipheringUserName(String userName) {
//        userName = "撒旦发射点";
//        try {
//            // 加密
//            BASE64Encoder encoder = new BASE64Encoder();
//            String encode = encoder.encode(userName.getBytes());
//            System.out.println(encode);
//
//            // 解密
//            BASE64Decoder decoder = new BASE64Decoder();
//
//            byte[] bytes = decoder.decodeBuffer(encode);
//            userName = new String(bytes);
//            System.out.println(userName);
//            return userName;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
