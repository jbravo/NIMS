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
 *
 * @author Admin
 */
@Entity
@Table(name = "VOFFICE_SIGN")
public class VofficeSign implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @Column(name = "VOFFICE_SIGN_ID")
  @SequenceGenerator(name = "vofficeSignGenerator", sequenceName = "VOFFICE_SIGN_SEQ")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vofficeSignGenerator")
  private Long vofficeSignId;
  @Column(name = "TRANS_CODE")
  private String transCode;
  @Column(name = "SIGN_STATUS")
  private Integer signStatus;
  @Column(name = "SIGN_SOURCE")
  private String signSource;
  @Column(name = "DOC_TITLE")
  private String docTitle;
  @Column(name = "PUBLISH_OGANIZATION_CODE")
  private String publishOganizationCode;
  @Column(name = "LAST_SIGN_EMAIL")
  private String lastSignEmail;
  @Column(name = "SIGN_COMMENT")
  private String signComment;
  @Column(name = "CREATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Column(name = "UPDATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateTime;
  @Column(name = "ROW_STATUS")
  private Integer rowStatus;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "vofficeSignId")
  private List<VofficeSignLog> vofficeSignLogList;

  public VofficeSign() {
  }

  public VofficeSign(Long vofficeSignId) {
    this.vofficeSignId = vofficeSignId;
  }

  public Long getVofficeSignId() {
    return vofficeSignId;
  }

  public void setVofficeSignId(Long vofficeSignId) {
    this.vofficeSignId = vofficeSignId;
  }

  public String getTransCode() {
    return transCode;
  }

  public void setTransCode(String transCode) {
    this.transCode = transCode;
  }

  public Integer getSignStatus() {
    return signStatus;
  }

  public void setSignStatus(Integer signStatus) {
    this.signStatus = signStatus;
  }

  public String getSignSource() {
    return signSource;
  }

  public void setSignSource(String signSource) {
    this.signSource = signSource;
  }

  public String getDocTitle() {
    return docTitle;
  }

  public void setDocTitle(String docTitle) {
    this.docTitle = docTitle;
  }

  public String getPublishOganizationCode() {
    return publishOganizationCode;
  }

  public void setPublishOganizationCode(String publishOganizationCode) {
    this.publishOganizationCode = publishOganizationCode;
  }

  public String getLastSignEmail() {
    return lastSignEmail;
  }

  public void setLastSignEmail(String lastSignEmail) {
    this.lastSignEmail = lastSignEmail;
  }

  public String getSignComment() {
    return signComment;
  }

  public void setSignComment(String signComment) {
    this.signComment = signComment;
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

 
  public List<VofficeSignLog> getVofficeSignLogList() {
    return vofficeSignLogList;
  }

  public void setVofficeSignLogList(List<VofficeSignLog> vofficeSignLogList) {
    this.vofficeSignLogList = vofficeSignLogList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (vofficeSignId != null ? vofficeSignId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof VofficeSign)) {
      return false;
    }
    VofficeSign other = (VofficeSign) object;
    if ((this.vofficeSignId == null && other.vofficeSignId != null) || (this.vofficeSignId != null && !this.vofficeSignId.equals(other.vofficeSignId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Model.VofficeSign[ vofficeSignId=" + vofficeSignId + " ]";
  }

}

