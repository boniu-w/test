package wg.application.token.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.util.JwtTokenUtil;
import wg.application.vo.Result;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/*************************************************************
 * @Package wg.application.token.controller
 * @author wg
 * @date 2020/8/21 11:44
 * @version
 * @Copyright
 *************************************************************/
@RequestMapping(value = "/tokenTestController")
@RestController
@Slf4j
public class TokenTestController {

    /****************************************************************
     * 模拟登录,生成token,发送cookie
     * token有效期是10秒
     * @author: wg
     * @time: 2020/8/26 15:00
     ****************************************************************/
    @RequestMapping(value = "/generateToken")
    public Result tokenTest1(HttpServletRequest request, HttpServletResponse response) {
        String token = JwtTokenUtil.generateJwtToken();
        log.info(token);
        Cookie cookie = new Cookie("ssoTicket", token);
        response.addCookie(cookie);

        return Result.ok();
    }


    /***************************************************
     * 验证是否过期
     * @author: wg
     * @time: 2020/8/22 22:46
     ***************************************************/
    @RequestMapping(value = "/isExpiration")
    public Result isExpiration() {
        String jwtToken = JwtTokenUtil.generateJwtToken();
        JwtTokenUtil.analyseJwtToken(jwtToken);

        String currentToken = "";

        try {
            TimeUnit.SECONDS.sleep(3);
            boolean expired = JwtTokenUtil.isTokenExpired(jwtToken);
            if (expired == true) {
                currentToken = JwtTokenUtil.generateJwtToken();
                System.out.println("if");
            }

        } catch (ExpiredJwtException expiredJwtException) {
            currentToken = JwtTokenUtil.generateJwtToken();
            System.out.println("exception");
            Jws<Claims> claimsJws = JwtTokenUtil.analyseJwtToken(currentToken);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return Result.ok(currentToken);


    }


    /****************************************************************
     *
     * @author: wg
     * @time: 2020/8/26 14:43
     ****************************************************************/
    @RequestMapping(value = "/ticketTest")
    public Result ticketTest(HttpServletRequest request, HttpServletResponse response) {


        return Result.ok();
    }

}
