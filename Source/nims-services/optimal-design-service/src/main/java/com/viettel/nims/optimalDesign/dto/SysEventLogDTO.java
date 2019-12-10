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
public class SysEventLogDTO implements Serializable {

  private Long sysEventLogId;
  private short action;
  private String objectCode;
  private Long objectId;
  private int objectType;
  private String userName;
  private String userIp;
  private Long objectDeptId;
  private Long userDeptId;
  private String objectDeptName;
  private String userDeptName;
  private Date updateTime;
  private String note;
  private List<SysEventLogDetailDTO> sysEventLogDetailList;

  public Long getSysEventLogId() {
    return sysEventLogId;
  }

  public void setSysEventLogId(Long sysEventLogId) {
    this.sysEventLogId = sysEventLogId;
  }

  public short getAction() {
    return action;
  }

  public void setAction(short action) {
    this.action = action;
  }

  public String getObjectCode() {
    return objectCode;
  }

  public void setObjectCode(String objectCode) {
    this.objectCode = objectCode;
  }

  public Long getObjectId() {
    return objectId;
  }

  public void setObjectId(Long objectId) {
    this.objectId = objectId;
  }

  public int getObjectType() {
    return objectType;
  }

  public void setObjectType(int objectType) {
    this.objectType = objectType;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public Long getObjectDeptId() {
    return objectDeptId;
  }

  public void setObjectDeptId(Long objectDeptId) {
    this.objectDeptId = objectDeptId;
  }

  public Long getUserDeptId() {
    return userDeptId;
  }

  public void setUserDeptId(Long userDeptId) {
    this.userDeptId = userDeptId;
  }

  public String getObjectDeptName() {
    return objectDeptName;
  }

  public void setObjectDeptName(String objectDeptName) {
    this.objectDeptName = objectDeptName;
  }

  public String getUserDeptName() {
    return userDeptName;
  }

  public void setUserDeptName(String userDeptName) {
    this.userDeptName = userDeptName;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public List<SysEventLogDetailDTO> getSysEventLogDetailList() {
    return sysEventLogDetailList;
  }

  public void setSysEventLogDetailList(List<SysEventLogDetailDTO> sysEventLogDetailList) {
    this.sysEventLogDetailList = sysEventLogDetailList;
  }
}
