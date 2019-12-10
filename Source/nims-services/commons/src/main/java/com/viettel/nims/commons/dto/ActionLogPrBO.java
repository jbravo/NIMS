package com.viettel.nims.commons.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ActionLogPrBO", propOrder = {"actionLogPrId", "request", "response"})
public class ActionLogPrBO {

  @XmlElement(name = "actionLogPrId")
  private Long actionLogPrId;

  @XmlElement(name = "request")
  private String request;

  @XmlElement(name = "response")
  private String response;

  public Long getActionLogPrId() {
    return actionLogPrId;
  }

  public String getRequest() {
    if (request != null) {
      return request.replace("&quot;", "\"")
          .replace("&lt;", "<")
          .replace("&gt;", ">")
          .replace("\"", "\\\"")
          .replace("&#xD;", "\n")
          .replace("> <", ">\n<");
    }
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public String getResponse() {
    if (response != null) {
      return response.replace("&quot;", "\"")
          .replace("&lt;", "<")
          .replace("&gt;", ">")
          .replace("\"", "\\\"")
          .replace("&#xD;", "\n")
          .replace("> <", ">\n<");
    }
    return response;
  }
}
