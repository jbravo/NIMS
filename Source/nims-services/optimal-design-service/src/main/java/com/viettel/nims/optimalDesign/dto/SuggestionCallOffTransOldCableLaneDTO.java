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

/**
 * @author Admin
 */
@Data
public class SuggestionCallOffTransOldCableLaneDTO implements Serializable {

  private Long suggestionCallOffId;
  private String laneCode;
  private Long reuseCableHangLength;
  private Long reuseCableBuryLength;
  private Date createTime;
  private Date updateTime;
  private Integer rowStatus;

  public Long getSuggestionCallOffId() {
    return suggestionCallOffId;
  }

  public void setSuggestionCallOffId(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

  public String getLaneCode() {
    return laneCode;
  }

  public void setLaneCode(String laneCode) {
    this.laneCode = laneCode;
  }

  public Long getReuseCableHangLength() {
    return reuseCableHangLength;
  }

  public void setReuseCableHangLength(Long reuseCableHangLength) {
    this.reuseCableHangLength = reuseCableHangLength;
  }

  public Long getReuseCableBuryLength() {
    return reuseCableBuryLength;
  }

  public void setReuseCableBuryLength(Long reuseCableBuryLength) {
    this.reuseCableBuryLength = reuseCableBuryLength;
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
