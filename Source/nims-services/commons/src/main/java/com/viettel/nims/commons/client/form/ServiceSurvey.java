package com.viettel.nims.commons.client.form;

import com.viettel.nims.commons.entity.APStockModelBean;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
public class ServiceSurvey {

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
}
