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
public class SuggestionCallOffTransCableLaneNewDTO implements Serializable {

  private Long suggestionCallOffId;
  private String laneCode;
  private String sourceCode;
  private String destCode;
  private Long lengthHang;
  private Long lengthBury;
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

  public String getSourceCode() {
    return sourceCode;
  }

  public void setSourceCode(String sourceCode) {
    this.sourceCode = sourceCode;
  }

  public String getDestCode() {
    return destCode;
  }

  public void setDestCode(String destCode) {
    this.destCode = destCode;
  }

  public Long getLengthHang() {
    return lengthHang;
  }

  public void setLengthHang(Long lengthHang) {
    this.lengthHang = lengthHang;
  }

  public Long getLengthBury() {
    return lengthBury;
  }

  public void setLengthBury(Long lengthBury) {
    this.lengthBury = lengthBury;
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
