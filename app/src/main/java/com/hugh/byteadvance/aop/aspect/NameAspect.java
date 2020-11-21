package com.hugh.byteadvance.aop.aspect;

import android.util.Log;

import com.hugh.byteadvance.aop.AopEntity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by chenyw on 2020/8/12.
 */
@Aspect
public class NameAspect {

    //PointCut: 切点
    @Pointcut("call(* setAopName(String))") //语法需要书写对,*号后面的空格不能少
    public void setName1() {

    }

    @Around("setName1()")
    public Object aroundsetName(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();

        Object proceed = joinPoint.proceed();
        if (target instanceof AopEntity.innerB) {
            Log.e("aaa", "拦截到了");
        }
        return proceed;
    }

    @Pointcut("call(* getAopName())")
    public void getName1() {

    }

    @Around("getName1()")
    public String aroundgetName() {
        return "hahaha";
    }


}
