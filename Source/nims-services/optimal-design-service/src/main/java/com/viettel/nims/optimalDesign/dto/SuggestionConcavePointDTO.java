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
public class SuggestionConcavePointDTO implements Serializable {

  private Long suggestId;
  private String concavePointCode;
  private Integer concavePointType;
  private Date createTime;
  private Date updateTime;
  private Integer rowStatus;

  public Long getSuggestId() {
    return suggestId;
  }

  public void setSuggestId(Long suggestId) {
    this.suggestId = suggestId;
  }

  public String getConcavePointCode() {
    return concavePointCode;
  }

  public void setConcavePointCode(String concavePointCode) {
    this.concavePointCode = concavePointCode;
  }

  public Integer getConcavePointType() {
    return concavePointType;
  }

  public void setConcavePointType(Integer concavePointType) {
    this.concavePointType = concavePointType;
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
