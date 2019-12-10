package com.viettel.nims.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertErrorDTO {

  protected String description;
  protected String errorCode;
  protected Long errorId;
  protected String errorName;
  protected Short status;
  protected String supportMobile;
  protected String supportUser;
  protected String systemCode;
  protected Long updateTime;
  protected Long updateUser;
}
