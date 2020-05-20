package com.tzy.demo.seed.config;

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
 * @description Swagger参数配置
 * @date 2020/5/20
 **/

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                //.apis(RequestHandlerSelectors.basePackage("com.tzy.demo.seed.api"))
                //.paths(PathSelectors.any())
                .paths(PathSelectors.regex("^.*(?<!error)$"))
                //.paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
                .securitySchemes(securitySchemes())//添加token
                .securityContexts(securityContexts())
                .apiInfo(apiInfo())
                ;

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "服务REST-API",
                "服务REST-API.",
                "1.0.0",
                "Terms of service",
                new Contact("tangzanyong", "", "tangzanyong@126.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("token", "token", "header"));
    }
    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
//                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .forPaths(PathSelectors.any())
                        .build()
        );
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("token", authorizationScopes));
    }
}
