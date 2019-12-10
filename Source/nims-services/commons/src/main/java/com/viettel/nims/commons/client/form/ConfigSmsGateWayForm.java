package com.viettel.nims.commons.client.form;

import com.viettel.nims.commons.entity.CfgWsBO;
import lombok.Data;

@Data
public class ConfigSmsGateWayForm {

  private String sms_service_xmlns;
  private String sms_service_username;
  private String sms_service_password;
  private String sms_service_url;
  private String sms_service_name;
  private String sms_service_sender;
  private String contentType;
  private String status;

  public ConfigSmsGateWayForm(CfgWsBO cfgWsBO) {
    this.sms_service_name = cfgWsBO.getWsServiceName();
    this.sms_service_password = cfgWsBO.getWsPassword();
    this.sms_service_sender = cfgWsBO.getWsDescription();
    this.sms_service_url = cfgWsBO.getWsUrl();
    this.sms_service_username = cfgWsBO.getWsUsername();
    this.sms_service_xmlns = cfgWsBO.getWsUrlName();
    this.status = "1";
    this.contentType = "1";
  }

  public ConfigSmsGateWayForm(String sms_service_xmlns, String sms_service_username,
                              String sms_service_password, String sms_service_url, String sms_service_name,
                              String sms_service_sender, String contentType, String status) {
    this.sms_service_xmlns = sms_service_xmlns;
    this.sms_service_username = sms_service_username;
    this.sms_service_password = sms_service_password;
    this.sms_service_url = sms_service_url;
    this.sms_service_name = sms_service_name;
    this.sms_service_sender = sms_service_sender;
    this.contentType = contentType;
    this.status = status;
  }
}
