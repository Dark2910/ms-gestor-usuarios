package com.eespindola.cafeteria.gestor.usuarios.interceptors;

import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.ErrorValidation;
import com.eespindola.cafeteria.gestor.usuarios.model.EntryRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
  private void aroundMapperAop() {

  }

//  @Pointcut("@annotation(com.eespindola.cafeteria.gestor.usuarios.annotations.AfterAop)")
//  private void after() {
//  }

//  @Before("before()")
//  public void beforeHandler(JoinPoint joinPoint){
//    LOG.info("aspect: {}",joinPoint.getSignature().getName());
//  }

  @Around("aroundMapperAop()")
  public Object aroundHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    mapData(proceedingJoinPoint.getArgs());
    return proceedingJoinPoint.proceed();
  }

//  @After("after()")
//  public void afterHandler(JoinPoint joinPoint){
//    LOG.info("aspect: {}",joinPoint.getSignature().getName());
//  }

  private void mapData(Object[] list) throws JsonProcessingException {
    LOG.info("*** Mapeando Request");
    String jsonRequest = list[0].toString();
    EntryRequest<T> entryRequest = (EntryRequest<T>) list[1];

    T request = (T) objectMapper.readValue(jsonRequest, entryRequest.getRequest().getClass());
    entryRequest.setRequest(request);

    validaRequest(request);
  }

  private void validaRequest(T request) {
    Set<ConstraintViolation<T>> violations = validator.validate(request);
    if (!violations.isEmpty()) {
      LOG.error("*** Errores de validación detectados en el Interceptor");

      List<String> errorList = violations.stream().map(
                      ConstraintViolation::getMessage)
              .collect(Collectors.toCollection(ArrayList::new));

      throw new ErrorValidation(errorList, ErrorEnum.ERROR_400);
    }
  }

}
