package com.tzy.demo.annotation.api;

import com.tzy.demo.annotation.logger.SysLog;
import com.tzy.demo.annotation.login.LoginRequired;
import com.tzy.demo.annotation.login.LoginUserArg;
import com.tzy.demo.annotation.model.Dog;
import com.tzy.demo.annotation.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author tangzanyong
 * @description 测试
 * @date 2020/5/15
 */
@Api(tags="测试 登录用户参数注解、必须登录注解、日志注解")
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/loginArg")
    @ApiOperation(value = "测试 登录用户参数注解 给参数赋值; 请求头header需添加已登录有效token")
    public String test(@ApiIgnore @LoginUserArg User user){ //加了登录用户参数注解
        //user有值是登录状态，没值就是未登录
        if(user != null){
            return "当前登录用户：" + user.getName();
        }else {
            return "未登录";
        }
    }

    @GetMapping("/sourceA")
    @ApiOperation(value = "测试不用登录可以访问资源")
    public String sourceA(){
        Dog d = new Dog();
        System.out.println(d);
        return "你正在访问sourceA资源";
    }

    @LoginRequired //必须登录注解，需要登录才能访问该方法
    @GetMapping("/sourceB")
    @ApiOperation(value = "测试 必须登录注解，需要登录才能访问的资源")
    public String sourceB(){
        return "你正在访问sourceB资源";
    }

    @SysLog //给方法添加 日志注解
    @GetMapping("/sourceC/{source_name}")
    @ApiOperation(value = "测试 日志注解，调用该接口，会打出日志")
    public String sourceC(@PathVariable("source_name") String sourceName){
        System.out.println("========");
        return "你正在访问sourceC资源";
    }
}
