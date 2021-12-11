// package com.java.eduservice.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
// import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
// /**
//  * @author 曹广福
//  * @date 2021/10/25 20:38
//  */
// @Configuration//托管spring
// @EnableSwagger2//开启swagger功能
// public class SwaggerConfig {
//     @Bean
//     public Docket createRestApi() {
//         //版本类型是swagger2
//         return new Docket(DocumentationType.SWAGGER_2)
//                 //通过调用自定义方法apiInfo，获得文档的主要信息
//                 .apiInfo(apiInfo())
//                 .select()
//                 .apis(RequestHandlerSelectors.basePackage("com.java.eduservice.controller"))//扫描该包下面的API注解
//                 .paths(PathSelectors.any())
//                 .build();
//     }
//
//     /**
//      * 创建该API的基本信息（这些基本信息会展现在文档页面中）
//      * 访问地址：http://项目实际地址/swagger-ui.html
//      *
//      * @return
//      */
//     private ApiInfo apiInfo() {
//         return new ApiInfoBuilder()
//                 .title("接口管理文档首页显示") //
//                 .description("API的描述")//
//                 .termsOfServiceUrl("www.baidu.com")//网站url等
//                 .version("1.0")
//                 .build();
//     }
//
//
// }
