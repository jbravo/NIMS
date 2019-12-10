package com.viettel.nims.commons.client.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorLoginOutput {
  private Long login;
  private String errorCode;
  private String errorMsg;
}
