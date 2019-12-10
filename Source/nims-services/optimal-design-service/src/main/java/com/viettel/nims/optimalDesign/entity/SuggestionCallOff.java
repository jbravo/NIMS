/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import javax.persistence.*;


/**
 * @author Admin
 */
@Entity
@Table(name = "SUGGESTION_CALL_OFF")
public class SuggestionCallOff implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @Column(name = "SUGGESTION_CALL_OFF_ID")
  @SequenceGenerator(name = "suggestionCallOffsGenerator", sequenceName = "SUGGESTION_CALL_OFF_SEQ")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suggestionCallOffsGenerator")
  private Long suggestionCallOffId;
  @Column(name = "CALL_OFF_TYPE")
  private Integer callOffType;
  @Column(name = "CALL_OFF_STATUS")
  private Integer callOffStatus;
  @Column(name = "TYPE")
  private Integer type;
  @Column(name = "CREATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Column(name = "UPDATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateTime;
  @Column(name = "ROW_STATUS")
  private Integer rowStatus;

  @Column(name = "SUGGEST_ID")
  private Long suggestId;

  public SuggestionCallOff() {
  }

  public SuggestionCallOff(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (suggestionCallOffId != null ? suggestionCallOffId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SuggestionCallOff)) {
      return false;
    }
    SuggestionCallOff other = (SuggestionCallOff) object;
    if ((this.suggestionCallOffId == null && other.suggestionCallOffId != null) || (this.suggestionCallOffId != null && !this.suggestionCallOffId.equals(other.suggestionCallOffId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Model.SuggestionCallOff[ suggestionCallOffId=" + suggestionCallOffId + " ]";
  }

}
