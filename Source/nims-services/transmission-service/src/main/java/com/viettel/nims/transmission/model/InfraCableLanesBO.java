package com.viettel.nims.transmission.model;

import com.viettel.nims.commons.util.PaginationDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "INFRA_CABLE_LANES")
@Data
public class InfraCableLanesBO extends PaginationDTO {
  private Long laneId;
  private String laneCode;
  private Long sourceId;
  private Long destId;
  private Long deptId;
  private Long cableLaneLevelId;
  private Long ownerId;
  private Long length;
  private Long lengthHang;
  private Long lengthBury;
  private Long lengthInPipe;
  private Long attenuation;
  private Long status;
  private String note;
  private Date createTime;
  private Date updateTime;
  private Long rowStatus;

  @Id
  @Column(name = "LANE_ID")
  public Long getLaneId() {
    return laneId;
  }

  public void setLaneId(Long laneId) {
    this.laneId = laneId;
  }

  @Basic
  @Column(name = "LANE_CODE")
  public String getLaneCode() {
    return laneCode;
  }

  public void setLaneCode(String laneCode) {
    this.laneCode = laneCode;
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
  @Column(name = "CABLE_LANE_LEVEL_ID")
  public Long getCableLaneLevelId() {
    return cableLaneLevelId;
  }

  public void setCableLaneLevelId(Long cableLaneLevelId) {
    this.cableLaneLevelId = cableLaneLevelId;
  }

  @Basic
  @Column(name = "OWNER_ID")
  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
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
  @Column(name = "LENGTH_HANG")
  public Long getLengthHang() {
    return lengthHang;
  }

  public void setLengthHang(Long lengthHang) {
    this.lengthHang = lengthHang;
  }

  @Basic
  @Column(name = "LENGTH_BURY")
  public Long getLengthBury() {
    return lengthBury;
  }

  public void setLengthBury(Long lengthBury) {
    this.lengthBury = lengthBury;
  }

  @Basic
  @Column(name = "LENGTH_IN_PIPE")
  public Long getLengthInPipe() {
    return lengthInPipe;
  }

  public void setLengthInPipe(Long lengthInPipe) {
    this.lengthInPipe = lengthInPipe;
  }

  @Basic
  @Column(name = "ATTENUATION")
  public Long getAttenuation() {
    return attenuation;
  }

  public void setAttenuation(Long attenuation) {
    this.attenuation = attenuation;
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

  private String pathName;
  @Transient
  public String getPathName() {
    return pathName;
  }

  private String pathLocalName;
  @Transient
  public String getPathLocalName() {
    return pathLocalName;
  }

  private String laneCodeTemp;
  @Transient
  public String getLaneCodeTemp() {
    return laneCodeTemp;
  }

  private String deptName;
  @Transient
  public String getDeptName() {
    return deptName;
  }

  private String locationName;
  @Transient
  public String getLocationName() {
    return locationName;
  }

  private Long holderId;
  @Transient
  public Long getHolderId() {
    return holderId;
  }
}
