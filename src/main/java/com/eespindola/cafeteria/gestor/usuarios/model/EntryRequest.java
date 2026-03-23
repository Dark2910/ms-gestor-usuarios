package com.eespindola.cafeteria.gestor.usuarios.model;

import jakarta.validation.Valid;

public class EntryRequest<T> {

  @Valid
  private T request;

  public T getRequest() {
    return request;
  }

  public void setRequest(T request) {
    this.request = request;
  }

  public EntryRequest(T request){
    this.request = request;
  }

}
