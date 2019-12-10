/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

/**
 * @author Admin
 */
@Data
public class CatDeviceTypeDTO implements Serializable {

  private Long deviceTypeId;
  private String deviceTypeCode;
  private Long vendorId;
  private String note;
  private Date createTime;
  private Date updateTime;
  private Integer rowStatus;

  public Long getDeviceTypeId() {
    return deviceTypeId;
  }

  public void setDeviceTypeId(Long deviceTypeId) {
    this.deviceTypeId = deviceTypeId;
  }

  public String getDeviceTypeCode() {
    return deviceTypeCode;
  }

  public void setDeviceTypeCode(String deviceTypeCode) {
    this.deviceTypeCode = deviceTypeCode;
  }

  public Long getVendorId() {
    return vendorId;
  }

  public void setVendorId(Long vendorId) {
    this.vendorId = vendorId;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
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
}
