/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.*;


/**
 * @author Admin
 */
@Entity
@Table(name = "SUGGESTION_VERIFY")
public class SuggestionVerify implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @Column(name = "SUGGESTION_VERIFY_ID")
  @SequenceGenerator(name = "suggestionVerifyGenerator", sequenceName = "SUGGESTION_VERIFY_SEQ")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suggestionVerifyGenerator")
  private Long suggestionVerifyId;
  @Column(name = "SUGGESTION_VERIFY_TYPE")
  private Integer suggestionVerifyType;
  @Column(name = "STATUS")
  private Integer status;
  @Column(name = "VERIFY_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date verifyTime;
  @Column(name = "VERIFY_USER")
  private String verifyUser;
  @Column(name = "NOTE")
  private String note;

  @Column(name = "SUGGEST_ID")
  private Long suggestId;

  public SuggestionVerify() {
  }

  public SuggestionVerify(Long suggestionVerifyId) {
    this.suggestionVerifyId = suggestionVerifyId;
  }

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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (suggestionVerifyId != null ? suggestionVerifyId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SuggestionVerify)) {
      return false;
    }
    SuggestionVerify other = (SuggestionVerify) object;
    if ((this.suggestionVerifyId == null && other.suggestionVerifyId != null) || (this.suggestionVerifyId != null && !this.suggestionVerifyId.equals(other.suggestionVerifyId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Model.SuggestionVerify[ suggestionVerifyId=" + suggestionVerifyId + " ]";
  }

}
