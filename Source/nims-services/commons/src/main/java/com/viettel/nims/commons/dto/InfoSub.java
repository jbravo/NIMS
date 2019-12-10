package com.viettel.nims.commons.dto;

import java.util.List;
import lombok.Data;

@Data
public class InfoSub {

  private String account;
  private String deployAddress;
  private String address;
  private Long contractId;
  private Long subId;
  private String userUsing;
  private String serviceType;
  private String actStauts;
  private String telMobile;
  private String telFax;
  private String password;
  private Long custId;
  private List<SubDevice> lstSubDevice;
  private String actStautsName;
  private String technology;
  private String productCode;
  private String custName;
  private List<InfoTelForSub> lstInfoTelForSub;
}
