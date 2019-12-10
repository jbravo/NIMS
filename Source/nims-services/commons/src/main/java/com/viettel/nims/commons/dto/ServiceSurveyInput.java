package com.viettel.nims.commons.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceSurveyInput {

  private Long subOrderId;
  private Long parentReq;
  private String account;
  private String serviceCode;
  private String serviceName;
  private String description;
  private Long progress;
  private String teamCode;
  private String stationCode;
  private List<ServiceSurveyResultInput> lstResult;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ServiceSurveyResultInput {

    private Long Id;
    private String Value;
    private Long isSignal;
  }
}
