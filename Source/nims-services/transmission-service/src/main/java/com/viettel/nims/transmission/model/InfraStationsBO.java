package com.viettel.nims.transmission.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.viettel.nims.commons.util.PaginationDTO;
import com.vividsolutions.jts.geom.Point;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
@Entity
@Table(name = "INFRA_STATIONS")
public class InfraStationsBO extends PaginationDTO {

  private Long stationId;
  private String stationCode;
  private Long deptId;
  private Long locationId;
  private Long houseStationTypeId;
  private Long stationTypeId;
  private Long ownerId;
  private Long stationFeatureId;
  private Date constructionDate;
  private Long status;
  private String address;
  private String houseOwnerName;
  private String houseOwnerPhone;
  private Long backupStatus;
  private Long position;
  private BigDecimal length;
  private BigDecimal width;
  private BigDecimal height;
  private Point geometry;
  private String note;
  private BigDecimal heightestBuilding;
  private Long auditType;
  private Long auditStatus;
  private String auditReason;
  private Date createTime;
  private Date updateTime;
  private Long rowStatus;
  private String basicInfo;
  private String deptName;
  private String locationName;
  private Long terrain;
  private Double longitude;
  private Double latitude;
  private String ownerName;
  private String fillerStationCode;
  private String terrainName;
  private String statusName;
  private String backupStatusName;
  private String positionName;
  private String auditTypeName;
  private String auditStatusName;
  private String longString;
  private String laString;
  private String lengthStr;
  private String widthStr;
  private String heightStr;
  private String heightestBuildingStr;
  private String fileCheck;
  private String fileListed;

  @Id
  @Column(name = "STATION_ID")
  public Long getStationId() {
    return stationId;
  }

  public void setStationId(Long stationId) {
    this.stationId = stationId;
  }

  @Basic
  @Column(name = "STATION_CODE")
  public String getStationCode() {
    return stationCode;
  }

  public void setStationCode(String stationCode) {
    this.stationCode = stationCode;
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
  @Column(name = "LOCATION_ID")
  public Long getLocationId() {
    return locationId;
  }

  public void setLocationId(Long locationId) {
    this.locationId = locationId;
  }

  @Basic
  @Column(name = "HOUSE_STATION_TYPE_ID")
  public Long getHouseStationTypeId() {
    return houseStationTypeId;
  }

  public void setHouseStationTypeId(Long houseStationTypeId) {
    this.houseStationTypeId = houseStationTypeId;
  }

  @Basic
  @Column(name = "STATION_TYPE_ID")
  public Long getStationTypeId() {
    return stationTypeId;
  }

  public void setStationTypeId(Long stationTypeId) {
    this.stationTypeId = stationTypeId;
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
  @Column(name = "STATION_FEATURE_ID")
  public Long getStationFeatureId() {
    return stationFeatureId;
  }

  public void setStationFeatureId(Long stationFeatureId) {
    this.stationFeatureId = stationFeatureId;
  }

  @Basic
  @Column(name = "CONSTRUCTION_DATE")
  public Date getConstructionDate() {
    return constructionDate;
  }

  public void setConstructionDate(Date constructionDate) {
    this.constructionDate = constructionDate;
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
  @Column(name = "ADDRESS")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Basic
  @Column(name = "HOUSE_OWNER_NAME")
  public String getHouseOwnerName() {
    return houseOwnerName;
  }

  public void setHouseOwnerName(String houseOwnerName) {
    this.houseOwnerName = houseOwnerName;
  }

  @Basic
  @Column(name = "HOUSE_OWNER_PHONE")
  public String getHouseOwnerPhone() {
    return houseOwnerPhone;
  }

  public void setHouseOwnerPhone(String houseOwnerPhone) {
    this.houseOwnerPhone = houseOwnerPhone;
  }

  @Basic
  @Column(name = "BACKUP_STATUS")
  public Long getBackupStatus() {
    return backupStatus;
  }

  public void setBackupStatus(Long backupStatus) {
    this.backupStatus = backupStatus;
  }

  @Basic
  @Column(name = "POSITION")
  public Long getPosition() {
    return position;
  }

  public void setPosition(Long position) {
    this.position = position;
  }

  @Basic
  @Column(name = "LENGTH")
  public BigDecimal getLength() {
    return length;
  }

  public void setLength(BigDecimal length) {
    this.length = length;
  }

  @Basic
  @Column(name = "WIDTH")
  public BigDecimal getWidth() {
    return width;
  }

  public void setWidth(BigDecimal width) {
    this.width = width;
  }

  @Basic
  @Column(name = "HEIGHT")
  public BigDecimal getHeight() {
    return height;
  }

  public void setHeight(BigDecimal height) {
    this.height = height;
  }

  @Basic
  @Column(name = "GEOMETRY", columnDefinition = "geometry(Point,4326)")
  @JsonSerialize(using = GeometrySerializer.class)
  @JsonDeserialize(contentUsing = GeometryDeserializer.class)
  public Point getGeometry() {
    return geometry;
  }

  public void setGeometry(Point geometry) {
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
  @Column(name = "HEIGHTEST_BUILDING")
  public BigDecimal getHeightestBuilding() {
    return heightestBuilding;
  }

  public void setHeightestBuilding(BigDecimal heightestBuilding) {
    this.heightestBuilding = heightestBuilding;
  }

  @Basic
  @Column(name = "AUDIT_TYPE")
  public Long getAuditType() {
    return auditType;
  }

  public void setAuditType(Long auditType) {
    this.auditType = auditType;
  }

  @Basic
  @Column(name = "AUDIT_STATUS")
  public Long getAuditStatus() {
    return auditStatus;
  }

  public void setAuditStatus(Long auditStatus) {
    this.auditStatus = auditStatus;
  }

  @Basic
  @Column(name = "AUDIT_REASON")
  public String getAuditReason() {
    return auditReason;
  }

  public void setAuditReason(String auditReason) {
    this.auditReason = auditReason;
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
  public String getBasicInfo() {
    return basicInfo;
  }

  public void setBasicInfo(String basicInfo) {
    this.basicInfo = basicInfo;
  }

  @Transient
  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  @Transient
  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  @Transient
  public Long getTerrain() {
    return terrain;
  }

  public void setTerrain(Long terrain) {
    this.terrain = terrain;
  }

  @Transient
  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  @Transient
  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  @Transient
  public String getFillerStationCode() {
    return fillerStationCode;
  }

  public void setFillerStationCode(String fillerStationCode) {
    this.fillerStationCode = fillerStationCode;
  }

  @Transient
  public String getTerrainName() {
    return terrainName;
  }

  public void setTerrainName(String terrainName) {
    this.terrainName = terrainName;
  }

  @Transient
  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  @Transient
  public String getBackupStatusName() {
    return backupStatusName;
  }

  public void setBackupStatusName(String backupStatusName) {
    this.backupStatusName = backupStatusName;
  }

  @Transient
  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }

  @Transient
  public String getAuditTypeName() {
    return auditTypeName;
  }

  public void setAuditTypeName(String auditTypeName) {
    this.auditTypeName = auditTypeName;
  }

  @Transient
  public String getAuditStatusName() {
    return auditStatusName;
  }

  public void setAuditStatusName(String auditStatusName) {
    this.auditStatusName = auditStatusName;
  }

  @Transient
  public String getLongString() {
    return longString;
  }

  public void setLongString(String longString) {
    this.longString = longString;
  }

  @Transient
  public String getLaString() {
    return laString;
  }

  public void setLaString(String laString) {
    this.laString = laString;
  }

  @Transient
  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  @Transient
  public String getLengthStr() {
    return lengthStr;
  }

  public void setLengthStr(String lengthStr) {
    this.lengthStr = lengthStr;
  }

  @Transient
  public String getWidthStr() {
    return widthStr;
  }

  public void setWidthStr(String widthStr) {
    this.widthStr = widthStr;
  }

  @Transient
  public String getHeightStr() {
    return heightStr;
  }

  public void setHeightStr(String heightStr) {
    this.heightStr = heightStr;
  }

  @Transient
  public String getHeightestBuildingStr() {
    return heightestBuildingStr;
  }

  public void setHeightestBuildingStr(String heightestBuildingStr) {
    this.heightestBuildingStr = heightestBuildingStr;
  }

  @Transient
  public String getFileCheck() {
    return fileCheck;
  }

  public void setFileCheck(String fileCheck) {
    this.fileCheck = fileCheck;
  }

  @Transient
  public String getFileListed() {
    return fileListed;
  }

  public void setFileListed(String fileListed) {
    this.fileListed = fileListed;
  }
}
