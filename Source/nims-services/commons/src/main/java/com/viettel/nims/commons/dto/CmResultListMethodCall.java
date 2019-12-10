package com.viettel.nims.commons.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmResultListMethodCall {

  private String account;
  private String responCode;
  private String description;
}
