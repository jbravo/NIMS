package com.viettel.nims.commons.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActionLogPr {

  private Long id;
  private String request;
  private Date createDate;
  private String userName;
  private String shopCode;
  private String isdn;
  private String response;
  private String responseCode;
  private String exception;
}
