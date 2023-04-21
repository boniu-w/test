package wg.application.proto.channel;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wg.application.proto.config.GrpcProperty;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/************************************************************************
 * @author: wg
 * @description:
 * @createTime: 16:20 2022/7/20
 * @updateTime: 16:20 2022/7/20
 ************************************************************************/
@Component
public class ChannelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ChannelUtil.class);

    @Resource
    GrpcProperty grpcProperty;

    private static ChannelUtil channelUtil;

    @PostConstruct
    private void initChannelUtil() {
        System.out.println(" >>>>>>>>>>>  channelutil init  <<<<<<<<<<<");
        channelUtil = this;
    }

    public static ManagedChannel buildChannel() {
        String target = channelUtil.grpcProperty.getHost();
        return ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
    }

    /************************************************************************
     * @author: wg
     * @description: 解决 Make sure to call shutdown()/shutdownNow() and wait until awaitTermination() returns true.
     * @params:
     * @return:
     * @createTime: 9:34  2022/7/20
     * @updateTime: 9:34  2022/7/20
     ************************************************************************/
    public static void shutdownManagedChannel(ManagedChannel managedChannel) {
        // Close the gRPC managed-channel if not shut down already.
        if (!managedChannel.isShutdown()) {
            try {
                managedChannel.shutdown();
                if (!managedChannel.awaitTermination(45, TimeUnit.SECONDS)) {
                    logger.warn("Timed out gracefully shutting down connection: {}. ", managedChannel);
                }
            } catch (Exception e) {
                logger.error("Unexpected exception while waiting for channel termination", e);
            }
        }

        // Forceful shut down if still not terminated.
        if (!managedChannel.isTerminated()) {
            try {
                managedChannel.shutdownNow();
                if (!managedChannel.awaitTermination(15, TimeUnit.SECONDS)) {
                    logger.warn("Timed out forcefully shutting down connection: {}. ", managedChannel);
                }
            } catch (Exception e) {
                logger.error("Unexpected exception while waiting for channel termination", e);
            }
        }
    }

}
