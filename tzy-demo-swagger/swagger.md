# swagger(api文档工具)
官网地址：https://swagger.io/
> 1、是一款让你更好的书写API文档的规范且完整框架。  
> 2、提供描述、生产、消费和可视化RESTful Web Service。  
> 3、是由庞大工具集合支撑的形式化规范。这个集合涵盖了从终端用户接口、底层代码库到商业API管理的方方面面。  

参考资料  
* [swagger使用参考](https://blog.csdn.net/weixin_37509652/article/details/80094370)
* [swagger使用参考２](https://blog.csdn.net/weixin_42165041/article/details/81077100)
* [swagger-ui自定义封装](https://www.cnblogs.com/myhappylife/p/9403563.html#autoid-0-0-0)
* [swagger-ui自定义封装github代码](https://github.com/tangzanyong/huan-swagger)

## 一、swagger两种使用方法
### 方法1：使用第三方依赖（最简单的方法）
1、在pom.xml文件中添加第三方swagger依赖
```xml
        <dependency>
            <groupId>com.spring4all</groupId>
            <artifactId>swagger-spring-boot-starter</artifactId>
            <version>1.9.0.RELEASE</version>
        </dependency>
```
2、在Spring Boot项目的启动类上添加@EnableSwagger2或@EnableSwagger2Doc注解，就可以直接使用啦。    
3、swagger依赖实现的项目源码与详细的讲解  
    https://github.com/SpringForAll/spring-boot-starter-swagger  

### 方法2：使用官方依赖
1、在pom.xml文件中添加swagger相关依赖
```xml
        <!--引入swagger2需要的jar-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```
第一个是API获取的包，第二是官方给出的一个ui界面。这个界面可以自定义，默认是官方的，对于安全问题，以及ui路由设置需要着重思考。  

2、swagger的configuration
需要特别注意的是swagger scan base package,这是扫描注解的配置，即你的API接口位置。
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tzy.demo.swagger.api"))
                .paths(PathSelectors.any())
                .build();
    }
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
```
## 二、swagger具体使用
1、api标记  
Api 用在类上，说明该类的作用。可以标记一个Controller类做为swagger文档资源，使用方式：
```java
    @RestController
    @Api(tags = "测试接口",description = "测试接口 Rest Api")
    @RequestMapping("/test")
    public class TestController {
```
2、ApiOperation标记   
ApiOperation：用在方法上，说明方法的作用，每一个url资源的定义,使用方式：
```java
    @GetMapping("/hello")
    @ApiOperation(value = "测试", httpMethod = "GET", notes = "测试接口")
    public String test(String userName){
```
4、ApiResponse  
ApiResponse：响应配置，使用方式：  
@ApiResponse(code = 400, message = "Invalid user supplied")

5、ApiResponses  
ApiResponses：响应集配置，使用方式：    
@ApiResponses({ @ApiResponse(code = 400, message = "Invalid Order") })

6、ResponseHeader    
响应头设置，使用方法    
@ResponseHeader(name="head1",description="response head conf")

7、生产环境关闭Swagger2  
Swagger用于开发期间前端和后端API上的交流使用，在生产环境中我们应该关掉Swagger，如果生产环境不关掉swagger将是一件非常危险的事情。关闭Swagger有两种方式：
方式一：在SwaggerConfig上使用@Profile注解标识，@Profile({"dev","test"})表示在dev和test环境才能访问swagger-ui.html，prod环境下访问不了
方式二：在SwaggerConfig上使用@ConditionalOnProperty注解，@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")表示配置文件中如果swagger.enable =true表示开启。所以只需要在开发环境的配置文件配置为true，生产环境配置为false即可。
```java
        @Configuration
        @EnableSwagger2
        //@Profile({"dev","test"})  表示在dev和test环境才能访问swagger-ui.html，prod环境下访问不了
        //@ConditionalOnProperty(name = "swagger.enable", havingValue = "true") //开发环境的配置文件配置为true，生产环境配置为false即可。
        public class SwaggerConfig {
```

## 三、swagger设置全局token,解决接口需要token验证的问题
1、在SwaggerConfig增加
```
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
```
2、在SwaggerConfig的public Docket createRestApi()方法new Docket()中增加  
　　.securitySchemes(securitySchemes())   
　　.securityContexts(securityContexts())  
```
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
```

## 四、如果项目中存在继承WebMvcConfigurationSupport的配置类，需要对swagger访问资料注册放行,否则不能访问swagger资源
1、存在继承WebMvcConfigurationSupport的WebMvcConfig配置类
```java
    @Configuration
    public class WebMvcConfig extends WebMvcConfigurationSupport {
        ......
    }
```
2、需要对swagger访问资料注册放行,否则不能访问swagger资源
```java
    @Configuration
    public class WebMvcConfig extends WebMvcConfigurationSupport {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
```

## 五、其它api文档工具
* [mock官网](https://mock.yonyoucloud.com/)
* [mockgithub](https://github.com/gomcarter/developer)
* [RAP](https://github.com/thx/RAP)
