package wg.application.util;

import io.jsonwebtoken.*;
import wg.application.entity.AuthorizerDetail;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

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

        LocalDateTime plus = now.plus(10, ChronoUnit.SECONDS);
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

        return jwtToken;
    }


    /***************************************************
     * 根据用户信息 生成token
     * @author: wg
     * @time: 2020/8/22 23:11
     ***************************************************/
    public static String generateJwtToken(AuthorizerDetail authorizerDetail) {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        Date date = Date.from(Instant.from(zonedDateTime));

        LocalDateTime plus = now.plus(10, ChronoUnit.SECONDS);
        ZonedDateTime time = plus.atZone(ZoneId.systemDefault());
        Date expirationDate = Date.from(time.toInstant());

        String jwtToken = Jwts.builder()
          .setHeaderParam("type", "JWT")
          .setIssuedAt(date)
          .setExpiration(expirationDate)
          .setIssuer(authorizerDetail.getUserId())
          .claim(authorizerDetail.userName, authorizerDetail.getUserName())
          .claim("authorizerDetail.authorityCodeList", new ArrayList<String>())
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
    public static boolean isTokenExpired(String token) {
        try {
            analyseJwtToken(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
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

        //System.out.println("header:  " + header);
        //System.out.println("body:  " + claims);

        return claimsJws;
    }

    /****************************************************************
     * 过期 重新生成
     * @author: wg
     * @time: 2020/8/24 14:49
     ****************************************************************/
    public static Jws<Claims> refurbishJwtToken(String jwtToken) {

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("SignatureAlgorithm.HS512").parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();

        Date expiration = claims.getExpiration();

        // 如果不过期 刷新 时间
        if (expiration.after(new Date())) {
            LocalDateTime now = LocalDateTime.now();
            ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
            Date date = Date.from(Instant.from(zonedDateTime));

            LocalDateTime plus = now.plus(60, ChronoUnit.SECONDS);
            ZonedDateTime time = plus.atZone(ZoneId.systemDefault());
            Date expirationDate = Date.from(time.toInstant());
            claims.setExpiration(expirationDate);

        }

        return claimsJws;
    }

}
