package wg.application.token.util;

import io.jsonwebtoken.*;
import wg.application.token.entity.AuthorizerDetail;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*************************************************************
 * @Package wg.application.token.util
 * @author wg
 * @date 2020/8/21 14:46
 * @version
 * @Copyright
 *************************************************************/
public class JwtTokenUtil {


    /****************************************************************
     * 生成token
     * @author: wg
     * @time: 2020/8/21 14:46
     ****************************************************************/
    public static String generateJwtToken() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        Date date = Date.from(Instant.from(zonedDateTime));

        LocalDateTime plus = now.plus(60, ChronoUnit.SECONDS);
        ZonedDateTime time = plus.atZone(ZoneId.systemDefault());
        Date expirationDate = Date.from(time.toInstant());

        String jwtToken = Jwts.builder()
          .setHeaderParam("type", "JWT")
          .setIssuedAt(date)
          .setExpiration(expirationDate)
          .setIssuer("wg")
          .claim("username", "www")
          .claim("authorizerCodeList", new ArrayList<String>())
          .signWith(SignatureAlgorithm.HS512, "SignatureAlgorithm.HS512")
          .compact();

        System.out.println("jwtToken:  " + jwtToken);
        return jwtToken;
    }

    /****************************************************************
     * token是否过期
     * @author: wg
     * @time: 2020/8/21 14:51
     ****************************************************************/
    public static boolean isTokenExpired(Date expirationDate) {
        return expirationDate.before(new Date());
    }


    /****************************************************************
     * 解析token
     * @author: wg
     * @time: 2020/8/21 14:57
     ****************************************************************/
    public static Jws<Claims> analyseJwtToken(String jwtToken) {

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("SignatureAlgorithm.HS512").parseClaimsJws(jwtToken);
        JwsHeader header = claimsJws.getHeader();
        Claims claims = claimsJws.getBody();

        System.out.println("header:  " + header);
        System.out.println("body:  " + claims);


        return claimsJws;
    }


}
