/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Admin
 */
@Data
public class SuggestionCallOffTransDeviceDTO implements Serializable {

  private Long suggestionCallOffId;
  private String deviceCode;
  private String networkClass;
  private String networkType;
  private Date createTime;
  private Date updateTime;
  private Integer rowStatus;

  private Long deviceTypeId;

  public Long getSuggestionCallOffId() {
    return suggestionCallOffId;
  }

  public void setSuggestionCallOffId(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

  public String getDeviceCode() {
    return deviceCode;
  }

  public void setDeviceCode(String deviceCode) {
    this.deviceCode = deviceCode;
  }

  public String getNetworkClass() {
    return networkClass;
  }

  public void setNetworkClass(String networkClass) {
    this.networkClass = networkClass;
  }

  public String getNetworkType() {
    return networkType;
  }

  public void setNetworkType(String networkType) {
    this.networkType = networkType;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getRowStatus() {
    return rowStatus;
  }

  public void setRowStatus(Integer rowStatus) {
    this.rowStatus = rowStatus;
  }

  public Long getDeviceTypeId() {
    return deviceTypeId;
  }

  public void setDeviceTypeId(Long deviceTypeId) {
    this.deviceTypeId = deviceTypeId;
  }
}
