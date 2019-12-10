/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author Admin
 */
@Entity
@Table(name = "SUGGESTION_CALL_OFF_TRANS_DEVICE")
public class SuggestionCallOffTransDevice implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @Column(name = "SUGGESTION_CALL_OFF_ID")
  private Long suggestionCallOffId;
  @Column(name = "DEVICE_CODE")
  private String deviceCode;
  @Column(name = "NETWORK_CLASS")
  private String networkClass;
  @Column(name = "NETWORK_TYPE")
  private String networkType;
  @Column(name = "CREATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Column(name = "UPDATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateTime;
  @Column(name = "ROW_STATUS")
  private Integer rowStatus;
  @Column(name = "DEVICE_TYPE_ID")
  private Long deviceTypeId;

  public SuggestionCallOffTransDevice() {
  }

  public SuggestionCallOffTransDevice(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (suggestionCallOffId != null ? suggestionCallOffId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SuggestionCallOffTransDevice)) {
      return false;
    }
    SuggestionCallOffTransDevice other = (SuggestionCallOffTransDevice) object;
    if ((this.suggestionCallOffId == null && other.suggestionCallOffId != null) || (this.suggestionCallOffId != null && !this.suggestionCallOffId.equals(other.suggestionCallOffId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Model.SuggestionCallOffTransDevice[ suggestionCallOffId=" + suggestionCallOffId + " ]";
  }

}
