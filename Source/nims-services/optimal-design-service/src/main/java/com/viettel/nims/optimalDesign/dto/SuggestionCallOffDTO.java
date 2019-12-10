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
public class SuggestionCallOffDTO implements Serializable {

  private Long suggestionCallOffId;
  private Integer callOffType;
  private Integer callOffStatus;
  private Integer type;
  private Date createTime;
  private Date updateTime;
  private Integer rowStatus;
  private Long suggestId;

  public Long getSuggestionCallOffId() {
    return suggestionCallOffId;
  }

  public void setSuggestionCallOffId(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

  public Integer getCallOffType() {
    return callOffType;
  }

  public void setCallOffType(Integer callOffType) {
    this.callOffType = callOffType;
  }

  public Integer getCallOffStatus() {
    return callOffStatus;
  }

  public void setCallOffStatus(Integer callOffStatus) {
    this.callOffStatus = callOffStatus;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
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

  public Long getSuggestId() {
    return suggestId;
  }

  public void setSuggestId(Long suggestId) {
    this.suggestId = suggestId;
  }
}
