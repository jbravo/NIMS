package com.viettel.nims.commons.dto;

import com.viettel.nims.commons.entity.APStockModelBean;
import com.viettel.nims.commons.entity.SubStockModelRel;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "ParamInputBO")
public class ParamInputBO {

  private ArrayList<APStockModelBean> lstAPStockModelBean;
  private ArrayList<APStockModelBean> lstStageItem;
  private ArrayList<SubGoodsDTO> listSubGood;
  private ArrayList<SubGoodsDTO> lstSubGoodsDTO;
  private ArrayList<SubStageItem> lstActualSubStageItem;
  private ArrayList<SubStageItem> lstSubStageItem;
  private ArrayList<SubStockModelRel> lstSubStockModelRel;
  private List<ActionLogPrBO> lstActionLogPrBO;
  private List<ServiceSurveyDTO> moreService;
  private Long custId;
  private Long custOrderId;
  private Long requestId;
  private Long retakeOnu;
  private Long sourceId;
  private Long stockModelId;
  private Long stockStaffId;
  private Long subOrderId;
  private Long task;
  private Long taskId;
  private Long taskType;
  private String account;
  private String accountOrIsdn;
  private String areaCode;
  private String connectorCode;
  private String connectorId;
  private String customerOrderId;
  private String deleteInfoFax;
  private String description;
  private String deviceCode;
  private String deviceType;
  private String glineAccount;
  private String gponSerial;
  private String imSerial;
  private String imStbSerial;
  private String ip;
  private String ipGateway;
  private String ipLan;
  private String ipRouter;
  private String ipWan;
  private String isGetCust;
  private String isRestricted;
  private String latitude;
  private String logicalPort;
  private String loginName;
  private String longitude;
  private String measPointCode;
  private String measPointName;
  private String memberIsdns;
  private String meterSerial;
  private String meterType;
  private String nameContact;
  private String noteCode;
  private String oldTelFax;
  private String physicalPort;
  private String pppoe;
  private String privateIp;
  private String progress;
  private String projectCode;
  private String projectName;
  private String reasonName;
  private String reqDatetime;
  private String reqNote;
  private String serviceType;
  private String shopCode;
  private String staffIdNo;
  private String staffIdNoEnd;
  private String stationCode;
  private String teamCode;
  private String teamCodeEnd;
  private String telFax;
  private String trunkId;
  private String vasCode;
  private String vendorCode;

  public ParamInputBO(String accountOrIsdn, String serviceType,
      ArrayList<APStockModelBean> lstAPStockModelBean) {
    this.accountOrIsdn = accountOrIsdn;
    this.serviceType = serviceType;
    this.lstAPStockModelBean = lstAPStockModelBean;
  }

  public ParamInputBO(String accountOrIsdn, String serviceType, String progress, String description,
      String teamCode, String loginName, String shopCode,
      ArrayList<APStockModelBean> lstAPStockModelBean, ArrayList<APStockModelBean> lstStageItem) {
    this.accountOrIsdn = accountOrIsdn;
    this.serviceType = serviceType;
    this.progress = progress;
    this.description = description;
    this.teamCode = teamCode;
    this.loginName = loginName;
    this.shopCode = shopCode;
    this.lstAPStockModelBean = lstAPStockModelBean;
    this.lstStageItem = lstStageItem;
  }

  public ParamInputBO(String accountOrIsdn, String serviceType, Long taskType, Long task,
      String teamCode, String loginName, ArrayList<APStockModelBean> lstAPStockModelBean,
      ArrayList<APStockModelBean> lstStageItem) {
    this.accountOrIsdn = accountOrIsdn;
    this.serviceType = serviceType;
    this.teamCode = teamCode;
    this.loginName = loginName;
    this.taskType = taskType;
    this.task = task;
    this.lstAPStockModelBean = lstAPStockModelBean;
    this.lstStageItem = lstStageItem;
  }
}
