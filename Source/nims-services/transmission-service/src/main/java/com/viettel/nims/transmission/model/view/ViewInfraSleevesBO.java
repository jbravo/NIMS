package com.viettel.nims.transmission.model.view;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "view_infra_sleeves")
public class ViewInfraSleevesBO {

  private Long sleeveId;
  private String sleeveCode;
  private Long sleeveTypeId;
  private String sleeveTypeCode;
  private String pillarCode;
  private String poolCode;
  private Long holderId;
  private Long type;
  private Long deptId;
  private String deptName;
  private String deptPath;
  private String location;
  private String LaneCode;
  private String ownerName;
  private String vendorName;
  private Long purpose;
  private Double longitude;
  private Double latitude;
  private String serial;
  private String note;
  private Date installationDate;
  private Date updateTime;
  private Long status;
  private String statusName;
  private Long sleeveIndex;
  private String modifyDate;
  private String installation;
  private Long ownerId;
  private Long vendorId;
  private String purposeName;
  private String localPathPillarPool;

  @Id
  @Column(name = "SLEEVE_ID")
  public Long getSleeveId() {
    return sleeveId;
  }

  public void setSleeveId(Long sleeveId) {
    this.sleeveId = sleeveId;
  }

  @Basic
  @Column(name = "SLEEVE_CODE")
  public String getSleeveCode() {
    return sleeveCode;
  }

  public void setSleeveCode(String sleeveCode) {
    this.sleeveCode = sleeveCode;
  }

  @Basic
  @Column(name = "SLEEVE_TYPE_ID")
  public Long getSleeveTypeId() {
    return sleeveTypeId;
  }

  public void setSleeveTypeId(Long sleeveTypeId) {
    this.sleeveTypeId = sleeveTypeId;
  }

  @Basic
  @Column(name = "SLEEVE_TYPE_CODE")
  public String getSleeveTypeCode() {
    return sleeveTypeCode;
  }

  public void setSleeveTypeCode(String sleeveTypeCode) {
    this.sleeveTypeCode = sleeveTypeCode;
  }

  @Basic
  @Column(name = "PILLAR_CODE")
  public String getPillarCode() {
    return pillarCode;
  }

  public void setPillarCode(String pillarCode) {
    this.pillarCode = pillarCode;
  }

  @Basic
  @Column(name = "POOL_CODE")
  public String getPoolCode() {
    return poolCode;
  }

  public void setPoolCode(String poolCode) {
    this.poolCode = poolCode;
  }

  @Basic
  @Column(name = "HOLDER_ID")
  public Long getHolderId() {
    return holderId;
  }

  public void setHolderId(Long holderId) {
    this.holderId = holderId;
  }

  @Basic
  @Column(name = "TYPE")
  public Long getType() {
    return type;
  }

  public void setType(Long type) {
    this.type = type;
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
  @Column(name = "DEPT_NAME")
  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  @Basic
  @Column(name = "LOCATION_NAME")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Basic
  @Column(name = "DEPT_PATH")
  public String getDeptPath() {
    return deptPath;
  }

  public void setDeptPath(String deptPath) {
    this.deptPath = deptPath;
  }

  @Basic
  @Column(name = "LANE_CODE")
  public String getLaneCode() {
    return LaneCode;
  }

  public void setLaneCode(String laneCode) {
    LaneCode = laneCode;
  }

  @Basic
  @Column(name = "OWNER_NAME")
  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  @Basic
  @Column(name = "VENDOR_NAME")
  public String getVendorName() {
    return vendorName;
  }

  public void setVendorName(String vendorName) {
    this.vendorName = vendorName;
  }

  @Basic
  @Column(name = "PURPOSE")
  public Long getPurpose() {
    return purpose;
  }

  public void setPurpose(Long purpose) {
    this.purpose = purpose;
  }

  @Basic
  @Column(name = "SERIAL")
  public String getSerial() {
    return serial;
  }

  public void setSerial(String serial) {
    this.serial = serial;
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
  @Column(name = "INSTALLATION_DATE")
  @Temporal(TemporalType.DATE)
  public Date getInstallationDate() {
    return installationDate;
  }

  public void setInstallationDate(Date installationDate) {
    this.installationDate = installationDate;
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
  @Column(name = "STATUS")
  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  @Basic
  @Column(name = "SLEEVE_INDEX")
  public Long getSleeveIndex() {
    return sleeveIndex;
  }

  public void setSleeveIndex(Long sleeveIndex) {
    this.sleeveIndex = sleeveIndex;
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
  @Column(name = "VENDOR_ID")
  public Long getVendorId() {
    return vendorId;
  }

  public void setVendorId(Long vendorId) {
    this.vendorId = vendorId;
  }

  @Basic
  @Column(name = "LONGTITUDE")
  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  @Basic
  @Column(name = "LATITUDE")
  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  @Basic
  @Column(name = "PATH_LOCAL_NAME_PILLAR_POOL")
  public String getLocalPathPillarPool() {
    return localPathPillarPool;
  }

  public void setLocalPathPillarPool(String localPathPillarPool) {
    this.localPathPillarPool = localPathPillarPool;
  }

  @Transient
  public String getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(String modifyDate) {
    this.modifyDate = modifyDate;
  }

  @Transient
  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  @Transient
  public String getInstallation() {
    return installation;
  }

  public void setInstallation(String installation) {
    this.installation = installation;
  }

  @Transient
  public String getPurposeName() {
    return purposeName;
  }

  public void setPurposeName(String purposeName) {
    this.purposeName = purposeName;
  }
}

