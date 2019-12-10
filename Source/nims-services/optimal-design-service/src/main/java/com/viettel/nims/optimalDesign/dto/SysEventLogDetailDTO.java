/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Admin
 */
@Data
public class SysEventLogDetailDTO implements Serializable {

  private Long id;
  private String fieldName;
  private String oldValue;
  private String newValue;
  private SysEventLogDTO sysEventLogId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getOldValue() {
    return oldValue;
  }

  public void setOldValue(String oldValue) {
    this.oldValue = oldValue;
  }

  public String getNewValue() {
    return newValue;
  }

  public void setNewValue(String newValue) {
    this.newValue = newValue;
  }

  public SysEventLogDTO getSysEventLogId() {
    return sysEventLogId;
  }

  public void setSysEventLogId(SysEventLogDTO sysEventLogId) {
    this.sysEventLogId = sysEventLogId;
  }
}
