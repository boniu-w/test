package wg.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 14:12 2022/9/6
 * @updateTime: 14:12 2022/9/6
 ************************************************************************/
@Component
public class WgProperty {

    @Value("${wg.jwt.secret}")
    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
