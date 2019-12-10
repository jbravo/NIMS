package com.viettel.nims.transmission.model;

import com.viettel.nims.commons.util.PaginationDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "INFRA_SLEEVES")

public class InfraSleevesBO extends PaginationDTO {
  private Long sleeveId;
  private String sleeveCode;
  private Long holderId;
  private Long deptId;
  private String laneCode;
  private String serial;
  private Long sleeveTypeId;
  private Long ownerId;
  private Long status;
  private Long purpose;
  private Date installationDate;
  private String note;
  private Date createTime;
  private Date updateTime;
  private Long rowStatus;
  private Long vendorId;

  private String pillarCode;
  private String poolCode;
  private String basicInfo;
  private String location;
  private String keySort;
  private String ownerName;
  private String longitude;
  private String latitude;
  private String sortName;

  private String sSleeveCode;
  private Long sSleeveTypeId;
  private Long sPurpose;
  private Long sStatus;
  private String sSerial;
  private String sDeptPath;
  private String vendorName;
  private String sPillarCode;
  private String sPoolCode;
  private String sLaneName;
  private String likePillarCode;
  private String likePoolCode;
  private String likeDeptPath;
  private String likeLaneCode;

  @Id
  @Column(name = "SLEEVE_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  @Column(name = "HOLDER_ID")
  public Long getHolderId() {
    return holderId;
  }

  public void setHolderId(Long holderId) {
    this.holderId = holderId;
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
  @Column(name = "LANE_CODE")
  public String getLaneCode() {
    return laneCode;
  }

  public void setLaneCode(String laneCode) {
    this.laneCode = laneCode;
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
  @Column(name = "SLEEVE_TYPE_ID")
  public Long getSleeveTypeId() {
    return sleeveTypeId;
  }

  public void setSleeveTypeId(Long sleeveTypeId) {
    this.sleeveTypeId = sleeveTypeId;
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
  @Column(name = "STATUS")
  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
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
  @Column(name = "INSTALLATION_DATE")
  public Date getInstallationDate() {
    return installationDate;
  }

  public void setInstallationDate(Date installationDate) {
    this.installationDate = installationDate;
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

  @Basic
  @Column(name = "VENDOR_ID")
  public Long getVendorId() {
    return vendorId;
  }

  public void setVendorId(Long vendorId) {
    this.vendorId = vendorId;
  }

  @Transient
  public String getBasicInfo() {
    return basicInfo;
  }

  public void setBasicInfo(String basicInfo) {
    this.basicInfo = basicInfo;
  }

  @Transient
  public String getLocation() {
    return location;
  }

  public void setLocation(String locationName) {
    this.location = locationName;
  }

  @Transient
  public String getPillarCode() {
    return pillarCode;
  }

  public void setPillarCode(String pillarCode) {
    this.pillarCode = pillarCode;
  }

  @Transient
  public String getPoolCode() {
    return poolCode;
  }

  public void setPoolCode(String poolCode) {
    this.poolCode = poolCode;
  }

  @Transient
  public String getKeySort() {
    return keySort;
  }

  public void setKeySort(String keySort) {
    this.keySort = keySort;
  }

  @Transient
  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  @Transient
  public String getLongitude() { return longitude; }

  public void setLongitude(String longitude) { this.longitude = longitude; }

  @Transient
  public String getLatitude() { return latitude; }

  public void setLatitude(String latitude) { this.latitude = latitude; }

  @Transient
  public String getSortName() {
    return sortName;
  }

  public void setSortName(String sortName) {
    this.sortName = sortName;
  }

  @Transient
  public Long getsPurpose() {
    return sPurpose;
  }

  public void setsPurpose(Long sPurpose) {
    this.sPurpose = sPurpose;
  }

  @Transient
  public Long getsStatus() {
    return sStatus;
  }

  public void setsStatus(Long sStatus) {
    this.sStatus = sStatus;
  }

  @Transient
  public Long getsSleeveTypeId() {
    return sSleeveTypeId;
  }

  public void setsSleeveTypeId(Long sSleeveTypeId) {
    this.sSleeveTypeId = sSleeveTypeId;
  }

  @Transient
  public String getsSleeveCode() {
    return sSleeveCode;
  }

  public void setsSleeveCode(String sSleeveCode) {
    this.sSleeveCode = sSleeveCode;
  }

  @Transient
  public String getsSerial() {
    return sSerial;
  }

  public void setsSerial(String sSerial) {
    this.sSerial = sSerial;
  }

  @Transient
  public String getVendorName() {
    return vendorName;
  }

  public void setVendorName(String vendorName) {
    this.vendorName = vendorName;
  }

  @Transient
  public String getsPillarCode() {
    return sPillarCode;
  }

  public void setsPillarCode(String sPillarCode) {
    this.sPillarCode = sPillarCode;
  }

  @Transient
  public String getsPoolCode() {
    return sPoolCode;
  }

  public void setsPoolCode(String sPoolCode) {
    this.sPoolCode = sPoolCode;
  }

  @Transient
  public String getsLaneName() {
    return sLaneName;
  }

  public void setsLaneName(String sLaneName) {
    this.sLaneName = sLaneName;
  }

  @Transient
  public String getsDeptPath() {
    return sDeptPath;
  }

  public void setsDeptPath(String sDeptPath) {
    this.sDeptPath = sDeptPath;
  }

  @Transient
  public String getLikePillarCode() {
    return likePillarCode;
  }

  public void setLikePillarCode(String likePillarCode) {
    this.likePillarCode = likePillarCode;
  }

  @Transient
  public String getLikePoolCode() {
    return likePoolCode;
  }

  public void setLikePoolCode(String likePoolCode) {
    this.likePoolCode = likePoolCode;
  }

  @Transient
  public String getLikeDeptPath() {
    return likeDeptPath;
  }

  public void setLikeDeptPath(String likeDeptPath) {
    this.likeDeptPath = likeDeptPath;
  }

  @Transient
  public String getLikeLaneCode() {
    return likeLaneCode;
  }

  public void setLikeLaneCode(String likeLaneCode) {
    this.likeLaneCode = likeLaneCode;
  }
}
