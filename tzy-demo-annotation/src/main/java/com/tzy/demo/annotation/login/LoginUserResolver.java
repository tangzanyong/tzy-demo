package com.tzy.demo.annotation.login;

import com.alibaba.fastjson.JSON;
import com.tzy.demo.annotation.model.AuthToken;
import com.tzy.demo.annotation.model.User;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author tangzanyong
 * @description 加了@LoginUserArg注解 要执行的业务，也可以理解成LoginUserArg注解处理器
 * @date 2020/5/15
 **/
@Component
public class LoginUserResolver implements HandlerMethodArgumentResolver {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhYaOV384ofdajBHoagPBuRpwc+joGz9hFN0Y3Jfj57LTzOFVXhEfNUQvWRig9+x+KCKxDxwdq3LMjd3gvtRQkw/YgLCfZFPsVQL30znBPEidpzOpJRQTQKgvUtZMZ0ZpWx4xzZP3F9wO3jRWow+png6n5jiENR56ANhg2e/XsVwC0rkSqtc2StIZK12AhBGNDcGsSYC9WePjNRFF5Pf3E8yOJsf7FUWqAdFQxjreVslPscKLg/gumRDtbOxQc6hUbz/7a8xe/3XvKm+HT2V+PHdJII5B6AFJDuew+57BfFB2XoBbqQsRpFxFSjlFLvhGFKktXckwVShAKIazbQoYuwIDAQAB-----END PUBLIC KEY-----";

    //只要方法参数加了LoginUserArg注解,此方法会被调用
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //建立LoginUserArg注解与方法参数关联
        return parameter.hasParameterAnnotation(LoginUserArg.class);//通过注解方式
    }

    /**
     * 加了@LoginUserArg注解的参数，会调用这个方法，返回值赋值到这个参数中
     * 1.从请求头或是cookkie中取token (测试token:06ed280e-e8b4-4c96-9285-d79d6aa01072)
     * 2.根据token从缓存中取jwtToken  (测试jwtToken:eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpblRpbWUiOiIyMDIwLTA1LTE0IDE1OjU4OjQ3IiwibG9naW5JcCI6IjEwLjAuMTAzLjYiLCJzY29wZSI6WyJhcHAiXSwibmFtZSI6InR6eSIsInRlbmFudElkIjoxLCJpZCI6MSwiZXhwIjoxNTg5NDg5NjUxLCJvcmdJZCI6NiwianRpIjoiMDZlZDI4MGUtZThiNC00Yzk2LTkyODUtZDc5ZDZhYTAxMDcyIiwiY2xpZW50X2lkIjoidmVzX2NsaWVudCIsInVzZXJuYW1lIjoiMTM0MTY0ODExODAifQ.JkDnDKxqSmp_yxpUyO71CPOpDO7PxLwt6_3r7FBvda7Veo7f1Tze-Mgb0eR4VOnpvBpzj2zzCEqaWTRY6ihGXDYfFlchtJFcd0vxyTfGP8XswSaPWLxybdwh6CfW4ppmjeRjr2GfT7eWJVKCHX1dqRS8C_S9i9cuJ05AWuI6ERETLg_Bm1Qo37kPneI9YH6NwKfiJEdxAo9gGXkqxG6uPVLLFagak2-EEzYxYyeAIxfrJwX8BbS6s1ReCxazLs__UA_uXfy1hRX0w29RnLZKWcZA4yfArZTOfHc-2iqpC438A3Q8cuurmvU94SBa3_B9_QF1Bx3dOSKVdWtChT3-1Q)
     * 3.校验解析jwt令牌，并把自定义内容转成User对象
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        //1.从请求头或是cookkie中取token
        //方式1：从请求头中取token
        String token = servletRequest.getHeader("token");
        /*
        //方式2：从cookkie中取token
        Map<String, String> cookieMap = CookieUtil.getCookie(servletRequest, "token");
        if(cookieMap !=null && cookieMap.get("token")!=null){
            token = cookieMap.get("token");
        }
        */
        //2.根据token从缓存中取jwtToken
        String tokenObjectString = stringRedisTemplate.opsForValue().get("user_token:" + token);
        if (StringUtils.isEmpty(tokenObjectString)){
            //throw new SecurityException("用户凭证过期,请重新登录");
            return null;
        }
        //转成对象
        AuthToken authToken = JSON.parseObject(tokenObjectString, AuthToken.class);
        if (authToken == null){
            //throw new SecurityException("用户凭证过期,请重新登录");
            return null;
        }
        //3.校验解析jwt令牌，并把内容转成User对象
        Jwt jwt = JwtHelper.decodeAndVerify(authToken.getJwtToken(), new RsaVerifier(PUBLIC_KEY));
        //拿到jwt令牌中自定义的内容
        String claims = jwt.getClaims();
        User user = JSON.parseObject(claims, User.class);
        return user;
    }
}
