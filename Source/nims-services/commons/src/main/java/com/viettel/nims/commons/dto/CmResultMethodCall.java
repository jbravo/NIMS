package com.viettel.nims.commons.dto;

import com.viettel.nims.commons.entity.SubStockModelRel;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmResultMethodCall {

  private String code;
  private String value;
  private Long valueV2;
  private String message;
  private List lstResult;
  private List<SubStockModelRel> lstSubStockModelRel;
  private InfoSubscriberRequest infoSubscriberRequest;
  private List<SubStageItem> lstSubStageItem;
  private List<ActionLogPrBO> lstActionLogPrBO;
  private List<ActionLogPr> lstActionLogPrLog = new ArrayList<>();
  private String allowSendToProvisioning;
  private String gponSerial;
  private String profile;
  private String rfState;
  private String onuType;
  private String passWord;
  private String account;
  private GponInfoBO gponInfoBO;
  private InfoSub infoSub;
  private List<InfoSub> lstInfoSub;
  private List<CmResultListMethodCall> lstCmResultListMethodCall;
}
