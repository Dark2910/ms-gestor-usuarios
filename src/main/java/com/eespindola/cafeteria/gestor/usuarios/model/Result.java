package com.eespindola.cafeteria.gestor.usuarios.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
@JsonInclude(
  JsonInclude.Include.NON_NULL
)
public class Result<T> {

  @JsonProperty(
    "success"
  )
  private Boolean success;

  @JsonProperty(
    "message"
  )
  private String message;

  @JsonProperty(
    "data"
  )
  private T data;

  @JsonProperty(
    "dataList"
  )
  private List<T> dataList;

  @JsonProperty(
    "errorCode"
  )
  private Integer errorCode;

  @JsonProperty(
    "errorDescription"
  )
  private List<String> errorDescription;

  // ==========================================
  // 2. CONSTRUCTORES
  // ==========================================

  public Result() {
  }

  private Result(ResultBuilder<T> builder) {
    this.success = builder.success;
    this.message = builder.message;
    this.data = builder.data;
    this.dataList = builder.dataList;
    this.errorCode = builder.errorCode;
    this.errorDescription = builder.errorDescription;
  }

  // ==========================================
  // 3. MÉTODOS ESTÁTICOS
  // ==========================================

  public static <T> ResultBuilder<T> builder() {
    return new ResultBuilder<>();
  }

  // ==========================================
  // 4. GETTERS Y SETTERS
  // ==========================================

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public List<T> getDataList() {
    return dataList;
  }

  public void setDataList(List<T> dataList) {
    this.dataList = dataList;
  }

  public Integer getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }

  public List<String> getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(List<String> errorDescription) {
    this.errorDescription = errorDescription;
  }

  // ==========================================
  // 5. CLASE BUILDER
  // ==========================================

  public static class ResultBuilder<T> {

    private Boolean success;
    private String message;
    private T data;
    private List<T> dataList;
    private Integer errorCode;
    private List<String> errorDescription;

    public ResultBuilder<T> success(Boolean success) {
      this.success = success;
      return this;
    }

    public ResultBuilder<T> message(String message) {
      this.message = message;
      return this;
    }

    public ResultBuilder<T> data(T data) {
      this.data = data;
      return this;
    }

    public ResultBuilder<T> dataList(List<T> dataList) {
      this.dataList = dataList;
      return this;
    }

    public ResultBuilder<T> errorCode(Integer errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public ResultBuilder<T> errorDescription(List<String> errorDescription) {
      this.errorDescription = errorDescription;
      return this;
    }

    public Result<T> build() {
      return new Result<>(this);
    }

  }
}
