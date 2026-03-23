package com.eespindola.cafeteria.gestor.usuarios.interceptors;

import com.eespindola.cafeteria.gestor.usuarios.model.EntryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Aspect
@Component
public class InterceptorAop<T> {
  private static final Logger LOG = LoggerFactory.getLogger(InterceptorAop.class);

  private final Validator validator;
  private final ObjectMapper objectMapper;

  @Autowired
  public InterceptorAop(
          Validator iValidator,
          ObjectMapper objMapper
  ) {
    this.validator = iValidator;
    this.objectMapper = objMapper;
  }

//  @Pointcut("@annotation(com.eespindola.cafeteria.gestor.usuarios.annotations.BeforeAop)")
//  private void before() {
//  }

  @Pointcut("@annotation(com.eespindola.cafeteria.gestor.usuarios.annotations.AroundMapperAop)")
  private void AroundMapperAop() {

  }

//  @Pointcut("@annotation(com.eespindola.cafeteria.gestor.usuarios.annotations.AfterAop)")
//  private void after() {
//  }

//  @Before("before()")
//  public void beforeHandler(JoinPoint joinPoint){
//    LOG.info("aspect: {}",joinPoint.getSignature().getName());
//  }

  @Around("AroundMapperAop()")
  public Object aroundHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    mapData(proceedingJoinPoint.getArgs());
    return proceedingJoinPoint.proceed();
  }

//  @After("after()")
//  public void afterHandler(JoinPoint joinPoint){
//    LOG.info("aspect: {}",joinPoint.getSignature().getName());
//  }

  private void mapData(Object[] list) throws Throwable {
    LOG.info("*** Mapeando Request");
    String jsonRequest = list[0].toString();
    EntryRequest<T> entryRequest = (EntryRequest<T>) list[1];

    T request = (T) objectMapper.readValue(jsonRequest, entryRequest.getRequest().getClass());
    entryRequest.setRequest(request);

    Set<ConstraintViolation<T>> violations = validator.validate(request);
    if (!violations.isEmpty()) {
      LOG.error("*** Errores de validación detectados en el Interceptor");
      throw new ConstraintViolationException(violations);
    }
  }

}
