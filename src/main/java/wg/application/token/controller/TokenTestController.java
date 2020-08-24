package wg.application.token.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wg.application.token.util.JwtTokenUtil;
import wg.application.vo.Result;

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
public class TokenTestController {


    @RequestMapping(value = "/jwtTokenTest1")
    public Result tokenTest1() {
        String jwtToken = JwtTokenUtil.generateJwtToken();
        //eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ   // header
        // .eyJpYXQiOjE1OTc5OTE1MzUsImlzcyI6IndnIiwidXNlcm5hbWUiOiJ3d3ciLCJhdXRob3JpdHlDb2RlcyI6ImF1dGhvcml0eUNvZGVzIn0  // payload
        // .uqMvSGWaSMvXnyPQ6FogKsp-XqBviu1489oEL4KYAdUCrzquuB4SW1zFB71zoEHSIRzHA61XIfGNJFqy5BoTvg  // signature

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("jwtToken",jwtToken);

        JwtTokenUtil.analyseJwtToken(jwtToken);

        System.out.println("jwtToken:  "+jwtToken);
        return Result.ok(hashMap);
    }


    /***************************************************
     * 验证是否过期
     * @author: wg
     * @time: 2020/8/22 22:46
     ***************************************************/
    @RequestMapping(value = "/isExpiration")
    public Result isExpiration(){
        String jwtToken = JwtTokenUtil.generateJwtToken();

        try {
            TimeUnit.SECONDS.sleep(11);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean expired = JwtTokenUtil.isTokenExpired(jwtToken);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("expired",expired);

        return Result.ok(hashMap);


    }





}
