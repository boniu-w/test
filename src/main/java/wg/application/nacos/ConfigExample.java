// /*
// * Demo for Nacos
// * pom.xml
//     <dependency>
//         <groupId>com.alibaba.nacos</groupId>
//         <artifactId>nacos-client</artifactId>
//         <version>${version}</version>
//     </dependency>
// */
// package wg.application.nacos;
//
// import java.util.Properties;
// import java.util.concurrent.Executor;
//
// import com.alibaba.nacos.api.NacosFactory;
// import com.alibaba.nacos.api.PropertyKeyConst;
// import com.alibaba.nacos.api.config.ConfigService;
// import com.alibaba.nacos.api.config.listener.Listener;
// import com.alibaba.nacos.api.exception.NacosException;
//
// /**
//  * Config service example
//  *
//  * @author Nacos
//  */
// public class ConfigExample {
//
//     public static void main(String[] args) throws NacosException, InterruptedException {
//         String serverAddr = "10.12.12.210";
//         String dataId = "test";
//         String group = "DEFAULT_GROUP";
//         Properties properties = new Properties();
//         properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
//         ConfigService configService = NacosFactory.createConfigService(properties);
//         String content = configService.getConfig(dataId, group, 5000);
//         System.out.println(content);
//         configService.addListener(dataId, group, new Listener() {
//             @Override
//             public void receiveConfigInfo(String configInfo) {
//                 System.out.println("recieve:" + configInfo);
//             }
//
//             @Override
//             public Executor getExecutor() {
//                 return null;
//             }
//         });
//
//         boolean isPublishOk = configService.publishConfig(dataId, group, "java wg ");
//         System.out.println("isPublishOk : " + isPublishOk);
//
//         Thread.sleep(3000);
//         content = configService.getConfig(dataId, group, 5000);
//         System.out.println("content after publish : " + content);
//
//         // boolean isRemoveOk = configService.removeConfig(dataId, group);
//         // System.out.println("isRemoveOk : "+isRemoveOk);
//         // Thread.sleep(3000);
//         //
//         // content = configService.getConfig(dataId, group, 5000);
//         // System.out.println("content after remove : " + content);
//         // Thread.sleep(300000);
//     }
// }
