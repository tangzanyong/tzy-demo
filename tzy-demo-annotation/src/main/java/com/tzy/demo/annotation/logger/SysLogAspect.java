package com.tzy.demo.annotation.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author tangzanyong
 * @description 加了@SysLog注解要执行的业务，也可以理解成SysLog注解处理器
 * @date 2020/5/16
 **/
@Aspect // 1.表明这是一个切面类
@Component
public class SysLogAspect {
    // 2. PointCut表示这是一个切点，@annotation表示这个切点切到一个注解上，后面带该注解的全类名
    // 切面最主要的就是切点，所有的故事都围绕切点发生
    // logPointCut()代表切点名称
    @Pointcut("@annotation(com.tzy.demo.annotation.logger.SysLog)")
    public void logPointCut(){};

    // 3. 环绕通知
    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        Object result = null;
        // 获取方法名称
        String methodName = joinPoint.getSignature().getName();
        // 获取入参
        Object[] param = joinPoint.getArgs();

        StringBuilder sb = new StringBuilder();
        for (Object o : param) {
            sb.append(o + "; ");
        }
        System.out.println("进入[" + methodName + "]方法,参数为:" + sb.toString());

        // 继续执行方法
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println(methodName + "方法执行结束");
        return result;
    }
}
