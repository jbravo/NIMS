package com.viettel.nims.transmission.model;

import com.viettel.nims.commons.util.PaginationDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "INFRA_CABLES")
public class InfraCablesBO extends PaginationDTO {

  private Long cableId;
  private String cableCode;
  private Long sourceId;
  private Long destId;
  private Long deptId;
  private String constructionCode;
  private Long cableTypeId;
  private Long status;
  private Long length;
  private Date installationDate;
  private String geometry;
  private String note;
  private Date createTime;
  private Date updateTime;
  private Long rowStatus;
  private String sourceCode;
  private String destCode;

  @Id
  @Column(name = "CABLE_ID")
  public Long getCableId() {
    return cableId;
  }

  public void setCableId(Long cableId) {
    this.cableId = cableId;
  }

  @Basic
  @Column(name = "CABLE_CODE")
  public String getCableCode() {
    return cableCode;
  }

  public void setCableCode(String cableCode) {
    this.cableCode = cableCode;
  }

  @Basic
  @Column(name = "SOURCE_ID")
  public Long getSourceId() {
    return sourceId;
  }

  public void setSourceId(Long sourceId) {
    this.sourceId = sourceId;
  }

  @Basic
  @Column(name = "DEST_ID")
  public Long getDestId() {
    return destId;
  }

  public void setDestId(Long destId) {
    this.destId = destId;
  }

  @Basic
  @Column(name = "DEPT_ID")
  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  @Basic
  @Column(name = "CONSTRUCTION_CODE")
  public String getConstructionCode() {
    return constructionCode;
  }

  public void setConstructionCode(String constructionCode) {
    this.constructionCode = constructionCode;
  }

  @Basic
  @Column(name = "CABLE_TYPE_ID")
  public Long getCableTypeId() {
    return cableTypeId;
  }

  public void setCableTypeId(Long cableTypeId) {
    this.cableTypeId = cableTypeId;
  }

  @Basic
  @Column(name = "STATUS")
  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  @Basic
  @Column(name = "LENGTH")
  public Long getLength() {
    return length;
  }

  public void setLength(Long length) {
    this.length = length;
  }

  @Basic
  @Column(name = "INSTALLATION_DATE")
  public Date getInstallationDate() {
    return installationDate;
  }

  public void setInstallationDate(Date installationDate) {
    this.installationDate = installationDate;
  }

  @Basic
  @Column(name = "GEOMETRY")
  public String getGeometry() {
    return geometry;
  }

  public void setGeometry(String geometry) {
    this.geometry = geometry;
  }

  @Basic
  @Column(name = "NOTE")
  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @Basic
  @Column(name = "CREATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @Basic
  @Column(name = "UPDATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  @Basic
  @Column(name = "ROW_STATUS")
  public Long getRowStatus() {
    return rowStatus;
  }

  public void setRowStatus(Long rowStatus) {
    this.rowStatus = rowStatus;
  }

  @Transient
  public String getSourceCode() {
    return sourceCode;
  }

  public void setSourceCode(String sourceCode) {
    this.sourceCode = sourceCode;
  }

  @Transient
  public String getDestCode() {
    return destCode;
  }

  public void setDestCode(String destCode) {
    this.destCode = destCode;
  }
}
