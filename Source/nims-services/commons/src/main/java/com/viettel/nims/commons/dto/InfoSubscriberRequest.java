package com.viettel.nims.commons.dto;

import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InfoSubscriberRequest {

  private Long custId;
  private String custName;
  private String idNo;
  private String idType;
  private String sex;
  private String custAddress;
  private String busType;
  private String busPermitNo;
  private String busName;
  private String subDeployAddress;
  private String subDeployAreaCode;
  private String linePhone;
  private String lineType;
  private String telFax;
  private Long status;
  private String progress;
  private String lata;
  private String center;
  private ArrayList<SubReqDeploymentBO> lstSubReqDeploymentBO;
}
