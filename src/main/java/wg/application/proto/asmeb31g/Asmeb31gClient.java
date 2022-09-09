// package wg.application.proto.asmeb31g;
//
// import com.sevenme.assessment.defect.corrosion.Asmeb31gGrpc;
// import com.sevenme.assessment.defect.corrosion.Asmeb31gProto;
// import io.grpc.Channel;
// import io.grpc.ManagedChannel;
// import io.grpc.ManagedChannelBuilder;
// import io.grpc.StatusRuntimeException;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;
//
// import java.util.logging.Level;
//
// /************************************************************************
//  * @author: wg
//  * @description:
//  * @createTime: 18:03 2022/7/15
//  * @updateTime: 18:03 2022/7/15
//  ************************************************************************/
// @Component
// public class Asmeb31gClient {
//
//     private static final Logger logger = LoggerFactory.getLogger(Asmeb31gClient.class.getName());
//
//     private Asmeb31gGrpc.Asmeb31gBlockingStub blockingStub;
//
//     public Asmeb31gClient(Channel channel) {
//         blockingStub = Asmeb31gGrpc.newBlockingStub(channel);
//     }
//
//     public void test() {
//         System.out.println("------- test  ");
//         Asmeb31gProto.Asmeb31gLevel0Request level0Request = Asmeb31gProto.Asmeb31gLevel0Request
//                 .newBuilder()
//                 .setDepth(1F)
//                 .setWallThickness(1F)
//                 .setLength(1F)
//                 .setOutsideDiameter(1F)
//                 .build();
//         Asmeb31gProto.Asmeb31gLevel0Response level0Response;
//
//         Asmeb31gProto.Asmeb31gLevel1Request level1Request = Asmeb31gProto.Asmeb31gLevel1Request.newBuilder()
//                 .setDepth(1F)
//                 .setWallThickness(1F)
//                 .setLength(1F)
//                 .setOutsideDiameter(1F)
//                 .setSmys(1F)
//                 .setSafetyFactor(1F)
//                 .setMaop(1F)
//                 .build();
//         Asmeb31gProto.Asmeb31gLevel1Response level1Response;
//
//         try {
//             level0Response = blockingStub.level0AssessmentIn1991(level0Request);
//             String result = level0Response.getResult();
//             System.out.println("result  " + result);
//
//             level1Response = blockingStub.level1AssessmentIn1991(level1Request);
//             String result1 = level1Response.getResult();
//             double residualStrength = level1Response.getResidualStrength();
//             System.out.println("level1Response: " + result1 + "  " + residualStrength);
//
//         } catch (StatusRuntimeException e) {
//             logger.info(Level.WARNING.getName(), "RPC failed: {0}", e.getStatus());
//             return;
//         }
//     }
//
//     public static void main(String[] args) {
//         String user = "world";
//         String target = "localhost:50051";
//
//         if (args.length > 0) {
//             if ("--help".equals(args[0])) {
//                 System.err.println("Usage: [name [target]]");
//                 System.err.println("");
//                 System.err.println("  name    The name you wish to be greeted by. Defaults to " + user);
//                 System.err.println("  target  The server to connect to. Defaults to " + target);
//                 System.exit(1);
//             }
//             user = args[0];
//         }
//         if (args.length > 1) {
//             target = args[1];
//         }
//
//         ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
//                 .usePlaintext()
//                 .build();
//
//         Asmeb31gClient asmeb31gClient = new Asmeb31gClient(channel);
//         asmeb31gClient.test();
//     }
// }
