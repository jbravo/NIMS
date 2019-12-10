/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * @author Admin
 */
@Entity
@Table(name = "SUGGESTION_CALL_OFF_TRANS")
public class SuggestionCallOffTrans implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @Column(name = "SUGGESTION_CALL_OFF_ID")
  private Long suggestionCallOffId;
  @Column(name = "TRANS_INTERFACE")
  private Integer transInterface;
  @Column(name = "DESIGNER")
  private String designer;
  @Column(name = "NOTE")
  private String note;

  @Column(name = "SUGGEST_ID")
  private Long suggestId;

  public SuggestionCallOffTrans() {
  }

  public SuggestionCallOffTrans(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

  public Long getSuggestionCallOffId() {
    return suggestionCallOffId;
  }

  public void setSuggestionCallOffId(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

  public Integer getTransInterface() {
    return transInterface;
  }

  public void setTransInterface(Integer transInterface) {
    this.transInterface = transInterface;
  }

  public String getDesigner() {
    return designer;
  }

  public void setDesigner(String designer) {
    this.designer = designer;
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
    hash += (suggestionCallOffId != null ? suggestionCallOffId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SuggestionCallOffTrans)) {
      return false;
    }
    SuggestionCallOffTrans other = (SuggestionCallOffTrans) object;
    if ((this.suggestionCallOffId == null && other.suggestionCallOffId != null) || (this.suggestionCallOffId != null && !this.suggestionCallOffId.equals(other.suggestionCallOffId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Model.SuggestionCallOffTrans[ suggestionCallOffId=" + suggestionCallOffId + " ]";
  }

}
