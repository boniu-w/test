// package wg.application.config;
//
// import io.swagger.annotations.Api;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.oas.annotations.EnableOpenApi;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
//
// /************************************************************************
//  * author: wg
//  * description: SwaggerConfig
//  * createTime: 11:14 2022/12/29
//  * updateTime: 11:14 2022/12/29
//  ************************************************************************/
// @Configuration
// @EnableOpenApi
// public class SwaggerConfig {
//
//     @Bean
//     public Docket createDocket(){
//         return new Docket(DocumentationType.OAS_30)
//                 .select()
//                 .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                 .paths(PathSelectors.any())
//                 .build();
//     }
// }
