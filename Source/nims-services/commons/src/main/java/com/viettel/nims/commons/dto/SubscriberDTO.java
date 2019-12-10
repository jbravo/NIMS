package com.viettel.nims.commons.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "SubscriberDTO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SubscriberDTO",
    propOrder = {
        "custName",
        "accountNo",
        "shopCode",
        "shopStaffName",
        "shopTelFax",
        "deployAddress",
        "lstSubInfrastructureDTO",
        "contactMobile",
        "promotionCode",
        "promotionName",
        "lstSubIpDTO",
        "isdn",
        "password",
        "accountPPPOE",
        "passwordPPPOE",
        "domesticSpeed",
        "speed",
        "shopName",
        "actStatusText",
        "lstInfoTelForSub",
        "subInfrastructureDTO",
        "latitude",
        "longitude",
        "telecomServiceName"
    }
)
public class SubscriberDTO {

  private String custName;
  private String accountNo;
  private String shopCode;
  private String shopStaffName;
  private String shopTelFax;
  private String deployAddress;
  private List<SubInfrastructureDTO> lstSubInfrastructureDTO;
  private String contactMobile;
  private String promotionCode;
  private String promotionName;
  private List<SubIpDTO> lstSubIpDTO;
  private String isdn;
  private String password;
  private String accountPPPOE;
  private String passwordPPPOE;
  private String domesticSpeed;
  private String speed;
  private String shopName;
  private String actStatusText;
  private List<InfoTelForSub> lstInfoTelForSub;
  private SubInfrastructureDTO subInfrastructureDTO;
  private String latitude;
  private String longitude;
  private String telecomServiceName;
}
