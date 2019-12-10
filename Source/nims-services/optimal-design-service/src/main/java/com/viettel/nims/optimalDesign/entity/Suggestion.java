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
@Table(name = "SUGGESTION")
public class Suggestion implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @Column(name = "SUGGEST_ID")
  @SequenceGenerator(name = "suggestionGenerator", sequenceName = "SUGGESTION_SEQ")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suggestionGenerator")
  private Long suggestId;
  @Basic(optional = false)
  @Column(name = "SUGGEST_TYPE")
  private int suggestType;
  @Basic(optional = false)
  @Column(name = "SUGGEST_CODE")
  private String suggestCode;
  @Basic(optional = false)
  @Column(name = "DEPT_ID")
  private Long deptId;
  @Basic(optional = false)
  @Column(name = "SUGGEST_STATUS")
  private int suggestStatus;
  @Column(name = "USER_NAME")
  private String userName;
  @Column(name = "CREATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Column(name = "UPDATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateTime;
  @Column(name = "ROW_STATUS")
  private Integer rowStatus;

  @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEPT_ID", insertable = false, updatable = false)
  @ManyToOne(fetch =  FetchType.LAZY)
  private CatDepartment catDepartment;

  @Transient
  private String deptName;

  public Suggestion() {
  }

  public Suggestion(Long suggestId) {
    this.suggestId = suggestId;
  }

  public Suggestion(Long suggestId, int suggestType, String suggestCode, Long deptId, int suggestStatus) {
    this.suggestId = suggestId;
    this.suggestType = suggestType;
    this.suggestCode = suggestCode;
    this.deptId = deptId;
    this.suggestStatus = suggestStatus;
  }

  public Suggestion(Long suggestId, int suggestType, String suggestCode, String deptName, int suggestStatus, String userName, Date createTime){
    this.suggestId = suggestId;
    this.suggestType = suggestType;
    this.suggestCode = suggestCode;
    this.deptName = deptName;
    this.suggestStatus = suggestStatus;
    this.userName = userName;
    this.createTime = createTime;
  }

  public Long getSuggestId() {
    return suggestId;
  }

  public void setSuggestId(Long suggestId) {
    this.suggestId = suggestId;
  }

  public int getSuggestType() {
    return suggestType;
  }

  public void setSuggestType(int suggestType) {
    this.suggestType = suggestType;
  }

  public String getSuggestCode() {
    return suggestCode;
  }

  public void setSuggestCode(String suggestCode) {
    this.suggestCode = suggestCode;
  }

  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  public int getSuggestStatus() {
    return suggestStatus;
  }

  public void setSuggestStatus(int suggestStatus) {
    this.suggestStatus = suggestStatus;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public CatDepartment getCatDepartment() {
    return catDepartment;
  }

  public void setCatDepartment(CatDepartment catDepartment) {
    this.catDepartment = catDepartment;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (suggestId != null ? suggestId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Suggestion)) {
      return false;
    }
    Suggestion other = (Suggestion) object;
    if ((this.suggestId == null && other.suggestId != null) || (this.suggestId != null && !this.suggestId.equals(other.suggestId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Model.Suggestion[ suggestId=" + suggestId + " ]";
  }

}

