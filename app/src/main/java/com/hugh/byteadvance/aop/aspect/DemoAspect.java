package com.hugh.byteadvance.aop.aspect;

import android.util.Log;

import com.hugh.byteadvance.aop.pointcut.CustomPointCut;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by chenyw on 2020/8/12.
 */
@Aspect
public class DemoAspect {
    @Pointcut("execution(@com.hugh.byteadvance.aop.pointcut.CustomPointCut * *..*.*(..))")  //这里需要设置全限定名
    public void customMethod() {
    }

    @Around("customMethod()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 类名
        String className = methodSignature.getDeclaringType().getSimpleName();
        // 方法名
        String methodName = methodSignature.getName();
        // 功能名
        CustomPointCut behaviorTrace = methodSignature.getMethod().getAnnotation(CustomPointCut.class);
        String[] value = behaviorTrace.permissionName();
        // value -- > phone,status
        long start = System.currentTimeMillis();

        // 也可以不执行joinPoint.proceed()，根据业务需求没有权限/登录不调用目标方法
        Object result = joinPoint.proceed();// result 为目标方法调用后的返回值

        long duration = System.currentTimeMillis() - start;//可以统计方法耗时.
        Log.e("aaa","--->"+duration+"毫秒");
        return result;//返回值,可以任性修改.
    }
}
