package com.viettel.nims.optimalDesign.dto.commons;

import lombok.Data;

@Data
public class ResponseDTO {
  private String errorCode;
  private String message;
  private Object data;

  public ResponseDTO() {
  }

  public ResponseDTO(String errorCode, String message, Object data) {
    this.errorCode = errorCode;
    this.message = message;
    this.data = data;
  }
}
