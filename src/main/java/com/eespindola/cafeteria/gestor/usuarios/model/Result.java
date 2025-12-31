package com.eespindola.cafeteria.gestor.usuarios.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("message")
  private String message;

  @JsonProperty("data")
  private T data;

  @JsonProperty("dataList")
  private List<T> dataList;

  @JsonProperty("errorCode")
  private Integer errorCode;

  @JsonProperty("errorDescription")
  private List<String> errorDescription;

}

