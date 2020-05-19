package com.tzy.demo.swagger.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author tangzanyong
 * @description swagger2的配置文件
 * @date 2020/5/12
 **/
@Configuration
@EnableSwagger2
//是否开启swagger，正式环境一般是需要关闭的（避免不必要的漏洞暴露！），可根据springboot的多环境配置进行设置
//@Profile({"dev","test"}) 生产环境关闭Swagger2：表示在dev和test环境才能访问swagger-ui.html，prod环境下访问不了
//@ConditionalOnProperty(name = "swagger.enable", havingValue = "true") //开启与关闭
public class SwaggerConfig {
    // swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //.apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.tzy.demo.swagger.api"))
                .paths(PathSelectors.any())
                //.paths(PathSelectors.regex("^.*(?<!error)$"))
                //.paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
                .securitySchemes(securitySchemes()) //添加token
                .securityContexts(securityContexts())
                .apiInfo(apiInfo())
                ;
    }
    //swagger设置全局token,解决接口需要token验证的问题
    private List<ApiKey> securitySchemes() {
        return newArrayList(new ApiKey("token", "token", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        //.forPaths(PathSelectors.any())
                        .build()
        );
    }
    //设置全局token
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("token", authorizationScopes));
    }

    /**
     * swagger.title=标题
     * swagger.description=描述
     * swagger.version=版本
     * swagger.license=许可证
     * swagger.licenseUrl=许可证URL
     * swagger.termsOfServiceUrl=服务条款URL
     * swagger.contact.name=维护人
     * swagger.contact.url=维护人URL
     * swagger.contact.email=维护人email
     * swagger.base-package=swagger扫描的基础包，默认：全扫描
     * swagger.base-path=需要处理的基础URL规则，默认：/**
     * swagger.exclude-path=需要排除的URL规则，默认：空
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "swagger测试服务",
                "swagger测试服务REST-API.",
                "1.0.0",
                "Terms of service",
                new Contact("tangzanyong", "", "tangzanyong@126.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}
