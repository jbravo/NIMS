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
public class SuggestionVerifyDTO implements Serializable {

  private Long suggestionVerifyId;
  private Integer suggestionVerifyType;
  private Integer status;
  private Date verifyTime;
  private String verifyUser;
  private String note;

  private Long suggestId;

  public Long getSuggestionVerifyId() {
    return suggestionVerifyId;
  }

  public void setSuggestionVerifyId(Long suggestionVerifyId) {
    this.suggestionVerifyId = suggestionVerifyId;
  }

  public Integer getSuggestionVerifyType() {
    return suggestionVerifyType;
  }

  public void setSuggestionVerifyType(Integer suggestionVerifyType) {
    this.suggestionVerifyType = suggestionVerifyType;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Date getVerifyTime() {
    return verifyTime;
  }

  public void setVerifyTime(Date verifyTime) {
    this.verifyTime = verifyTime;
  }

  public String getVerifyUser() {
    return verifyUser;
  }

  public void setVerifyUser(String verifyUser) {
    this.verifyUser = verifyUser;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Long getSuggestId() {
    return suggestId;
  }

  public void setSuggestId(Long suggestId) {
    this.suggestId = suggestId;
  }
}
