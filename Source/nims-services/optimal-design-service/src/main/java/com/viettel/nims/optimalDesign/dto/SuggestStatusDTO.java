package com.viettel.nims.optimalDesign.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author rabbit on 9/8/2019.
 */
public class SuggestStatusDTO {
  private Long suggestId;
  private Integer suggestStatus;
  private String suggestStationCode;
  private String calloff2gCode;
  private String calloff3gCode;
  private String calloff4gCode;
  private String userName;
  @NotBlank(message = "require.field")
  private String note;


  public Long getSuggestId() {
    return suggestId;
  }

  public void setSuggestId(Long suggestId) {
    this.suggestId = suggestId;
  }

  public Integer getSuggestStatus() {
    return suggestStatus;
  }

  public void setSuggestStatus(Integer suggestStatus) {
    this.suggestStatus = suggestStatus;
  }

  public String getSuggestStationCode() {
    return suggestStationCode;
  }

  public void setSuggestStationCode(String suggestStationCode) {
    this.suggestStationCode = suggestStationCode;
  }

  public String getCalloff2gCode() {
    return calloff2gCode;
  }

  public void setCalloff2gCode(String calloff2gCode) {
    this.calloff2gCode = calloff2gCode;
  }

  public String getCalloff3gCode() {
    return calloff3gCode;
  }

  public void setCalloff3gCode(String calloff3gCode) {
    this.calloff3gCode = calloff3gCode;
  }

  public String getCalloff4gCode() {
    return calloff4gCode;
  }

  public void setCalloff4gCode(String calloff4gCode) {
    this.calloff4gCode = calloff4gCode;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
