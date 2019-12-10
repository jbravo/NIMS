package com.viettel.nims.commons.dto;

import com.viettel.nims.commons.dto.ServiceSurveyInput.ServiceSurveyResultInput;
import com.viettel.nims.commons.entity.APStockModelBean;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "serviceSurvey")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "serviceSurvey",
    propOrder = {
        "account",
        "parentReq",
        "serviceCode",
        "subOrderId",
        "progress",
        "description",
        "serviceName",
        "lstAPStockModelBean",
        "teamCode",
        "stationCode",
        "lstStageItem"
    }
)
public class ServiceSurveyDTO {

  private Long subOrderId;
  private Long parentReq;
  private String account;
  private String serviceCode;
  private String serviceName;
  private String description;
  private Long progress;
  private ArrayList<APStockModelBean> lstAPStockModelBean;
  private ArrayList<APStockModelBean> lstStageItem;
  private String teamCode;
  private String stationCode;

  @XmlTransient
  private Long surveyResult;

  public ServiceSurveyDTO(ServiceSurveyInput serviceSurveyInput, Long connectorId) {
    this.subOrderId = serviceSurveyInput.getSubOrderId();
    this.parentReq = serviceSurveyInput.getParentReq();
    this.account = serviceSurveyInput.getAccount();
    this.serviceCode = serviceSurveyInput.getServiceCode();
    this.serviceName = serviceSurveyInput.getServiceName();
    this.description = serviceSurveyInput.getDescription();
    this.progress = serviceSurveyInput.getProgress();
    this.teamCode = serviceSurveyInput.getTeamCode();
    this.stationCode = serviceSurveyInput.getStationCode();
    List<ServiceSurveyResultInput> lstResult = serviceSurveyInput.getLstResult();
    if (!lstResult.isEmpty()) {
      ServiceSurveyResultInput resultInput = lstResult.get(0);
      if (connectorId == 0L || connectorId == -1L) {
        if (resultInput.getId() == 1L) {
          this.setSurveyResult(1L);
        } else if (resultInput.getId() == 0L) {
          this.setSurveyResult(2L);
        }
      } else if (connectorId > 0L) {
        if (resultInput.getId() == 1L) {
          this.setSurveyResult(3L);
        } else if (resultInput.getId() == 0L) {
          this.setSurveyResult(2L);
        }
      } else {
        log.error("connectorId must be 0, -1 or positive number");
      }
    }
  }
}
