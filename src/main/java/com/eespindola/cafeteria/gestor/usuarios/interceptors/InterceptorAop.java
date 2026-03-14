package com.eespindola.cafeteria.gestor.usuarios.interceptors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InterceptorAop {
  private static final Logger LOG = LoggerFactory.getLogger(InterceptorAop.class);

  @Pointcut("@annotation(com.eespindola.cafeteria.gestor.usuarios.annotations.BeforeAop)")
  private void before() {
  }

  @Pointcut("@annotation(com.eespindola.cafeteria.gestor.usuarios.annotations.AroundAop)")
  private void around() {

  }

  @Pointcut("@annotation(com.eespindola.cafeteria.gestor.usuarios.annotations.AfterAop)")
  private void after() {
  }

  @Before("before()")
  public void beforeHandler(JoinPoint joinPoint){
    LOG.info("aspect: {}",joinPoint.getSignature().getName());
  }

  @Around("around()")
  public Object aroundHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    LOG.info("Antes de ejecutar aspect");
    Object object = proceedingJoinPoint.proceed();
    LOG.info("Despues de ejecutar aspect");
    return object;
  }

  @After("after()")
  public void afterHandler(JoinPoint joinPoint){
    LOG.info("aspect: {}",joinPoint.getSignature().getName());
  }

}
