package wg.application.proto.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 16:57 2022/7/19
 * @updateTime: 16:57 2022/7/19
 ************************************************************************/
@Configuration
@ConfigurationProperties(prefix = "grpc.assessment")
public class GrpcProperty {

    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
