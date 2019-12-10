package com.viettel.nims.commons.dto;

import java.util.Date;
import lombok.Data;

@Data
public class SubDevice {

  private Long subDeviceId;
  private Long subId;
  private String account;
  private String tvmsCadId;
  private String tvmsMacId;
  private Long stockModelId;
  private Long status;
  private String imSerial;
  private String type;
  private Date createDate;
  private String stockModelCode;
  private String stockModelName;
}
