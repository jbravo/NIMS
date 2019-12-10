package com.viettel.nims.transmission.model.dto;

import com.viettel.nims.transmission.commom.annotation.Element;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;
import com.vividsolutions.jts.geom.Point;

import java.math.BigDecimal;
import java.util.Date;


@SheetSerializable(sheetDataIndex = 0, startRow = 4, totalCols = 24, resultCols = 24, flagRow = 3)
public class InfraStationDTO {
  @Element(type = "String", index = 0, header = "common.label.stt")
  private String stt;

  @Element(type = "String", index = 1, header = "station.header.code")
  private String stationCode;

  @Element(type = "String", index = 2, header = "station.header.dept")
  private String deptCode;

  @Element(type = "String", index = 3, header = "station.header.location")
  private String locationCode;

  @Element(type = "String", index = 4, header = "station.houseOwnerName")
  private String houseOwnerName;

  @Element(type = "String", index = 5, header = "station.header.houseownerPhone")
  private String houseOwnerPhone;

  @Element(type = "String", index = 6, header = "station.address")
  private String address;

  @Element(type = "String", index = 7, header = "station.header.owner")
  private String ownerCode;

  @Element(type = "Date", index = 8, header = "station.header.constructionDate")
  private String constructionDateStr;

  @Element(type = "String", index = 9, header = "station.status")
  private String statusName;

  @Element(type = "String", index = 10, header = "station.houseStationType")
  private String houseStationTypeName;

  @Element(type = "String", index = 11, header = "station.header.stationType")
  private String stationTypeName;

  @Element(type = "String", index = 12, header = "station.header.stationFeature")
  private String stationFeatureName;

  @Element(type = "String", index = 13, header = "station.backupStatus")
  private String backupStatusName;

  @Element(type = "String", index = 14, header = "station.position")
  private String positionName;

  @Element(type = "String", index = 15, header = "station.length")
  private String lengthStr;

  @Element(type = "String", index = 16, header = "station.width")
  private String widthStr;

  @Element(type = "String", index = 17, header = "station.height")
  private String heightStr;

  @Element(type = "String", index = 18, header = "station.heightestBuilding")
  private String heightestBuildingStr;

  @Element(type = "String", index = 19, header = "station.header.longitude")
  private String longitudeStr;

  @Element(type = "String", index = 20, header = "station.header.latitude")
  private String latitudeStr;

  @Element(type = "String", index = 21, header = "station.auditType")
  private String auditTypeName;

  @Element(type = "String", index = 22, header = "station.auditStatus", isGenerateValue = true)
  private String auditStatusName;

  @Element(type = "String", index = 23, header = "station.note")
  private String note;

  public String getStt() {
    return stt;
  }

  public void setStt(String stt) {
    this.stt = stt;
  }

  public String getStationCode() {
    return stationCode;
  }

  public void setStationCode(String stationCode) {
    this.stationCode = stationCode;
  }

  public String getDeptCode() {
    return deptCode;
  }

  public void setDeptCode(String deptCode) {
    this.deptCode = deptCode;
  }

  public String getLocationCode() {
    return locationCode;
  }

  public void setLocationCode(String locationCode) {
    this.locationCode = locationCode;
  }

  public String getHouseOwnerName() {
    return houseOwnerName;
  }

  public void setHouseOwnerName(String houseOwnerName) {
    this.houseOwnerName = houseOwnerName;
  }

  public String getHouseOwnerPhone() {
    return houseOwnerPhone;
  }

  public void setHouseOwnerPhone(String houseOwnerPhone) {
    this.houseOwnerPhone = houseOwnerPhone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getOwnerCode() {
    return ownerCode;
  }

  public void setOwnerCode(String ownerCode) {
    this.ownerCode = ownerCode;
  }

  public String getConstructionDateStr() {
    return constructionDateStr;
  }

  public void setConstructionDateStr(String constructionDateStr) {
    this.constructionDateStr = constructionDateStr;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public String getHouseStationTypeName() {
    return houseStationTypeName;
  }

  public void setHouseStationTypeName(String houseStationTypeName) {
    this.houseStationTypeName = houseStationTypeName;
  }

  public String getStationTypeName() {
    return stationTypeName;
  }

  public void setStationTypeName(String stationTypeName) {
    this.stationTypeName = stationTypeName;
  }

  public String getStationFeatureName() {
    return stationFeatureName;
  }

  public void setStationFeatureName(String stationFeatureName) {
    this.stationFeatureName = stationFeatureName;
  }

  public String getBackupStatusName() {
    return backupStatusName;
  }

  public void setBackupStatusName(String backupStatusName) {
    this.backupStatusName = backupStatusName;
  }

  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }

  public String getLengthStr() {
    return lengthStr;
  }

  public void setLengthStr(String lengthStr) {
    this.lengthStr = lengthStr;
  }

  public String getWidthStr() {
    return widthStr;
  }

  public void setWidthStr(String widthStr) {
    this.widthStr = widthStr;
  }

  public String getHeightStr() {
    return heightStr;
  }

  public void setHeightStr(String heightStr) {
    this.heightStr = heightStr;
  }

  public String getHeightestBuildingStr() {
    return heightestBuildingStr;
  }

  public void setHeightestBuildingStr(String heightestBuildingStr) {
    this.heightestBuildingStr = heightestBuildingStr;
  }

  public String getLongitudeStr() {
    return longitudeStr;
  }

  public void setLongitudeStr(String longitudeStr) {
    this.longitudeStr = longitudeStr;
  }

  public String getLatitudeStr() {
    return latitudeStr;
  }

  public void setLatitudeStr(String latitudeStr) {
    this.latitudeStr = latitudeStr;
  }

  public String getAuditTypeName() {
    return auditTypeName;
  }

  public void setAuditTypeName(String auditTypeName) {
    this.auditTypeName = auditTypeName;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getAuditStatusName() {
    return auditStatusName;
  }

  public void setAuditStatusName(String auditStatusName) {
    this.auditStatusName = auditStatusName;
  }
}
