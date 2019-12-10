package com.viettel.nims.transmission.model.dto;

import com.viettel.nims.transmission.commom.annotation.Element;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;

@SheetSerializable(sheetDataIndex = 0, startRow = 4, totalCols = 16, resultCols = 16, flagRow = 3)
public class InfraPoolDTO {
  @Element(type = "String", index = 0, header = "common.label.stt")
  private String index;
  @Element(type = "String", index = 1, header = "pillar.header.deptCode")
  private String deptCode;
  @Element(type = "number", index = 2, header = "pool.header.poolIndex", isGenerateValue = true)
  private String poolIndex;
  @Element(type = "String", index = 3, header = "pool.poolCode", isGenerateValue = true)
  private String poolCode;
  @Element(type = "String", index = 4, header = "pool.header.isAdditionalPool")
  private String isComplementaryPool;
  @Element(type = "String", index = 5, header = "pool.header.poolTypeCode")
  private String poolTypeCode;
  @Element(type = "String", index = 6, header = "pool.header.location")
  private String locationCode;
  @Element(type = "String", index = 7, header = "pillar.header.address")
  private String address;
  @Element(type = "String", index = 8, header = "pool.header.constructionDate")
  private String constructionDate;
  @Element(type = "String", index = 9, header = "pool.header.deliverDate")
  private String deliverDate;
  @Element(type = "String", index = 10, header = "pool.header.acceptanceDate")
  private String acceptanceDate;
  @Element(type = "String", index = 11, header = "odf.ownerName")
  private String ownerCode;
  @Element(type = "String", index = 12, header = "pillar.header.longitudeStr")
  private String longtitude;
  @Element(type = "String", index = 13, header = "pillar.header.latitudeStr")
  private String latitude;
  @Element(type = "String", index = 14, header = "cable.header.status")
  private String status;
  @Element(type = "String", index = 15, header = "station.note")
  private String note;

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getDeptCode() {
    return deptCode;
  }

  public void setDeptCode(String deptCode) {
    this.deptCode = deptCode;
  }

  public String getPoolIndex() {
    return poolIndex;
  }

  public void setPoolIndex(String poolIndex) {
    this.poolIndex = poolIndex;
  }

  public String getPoolCode() {
    return poolCode;
  }

  public void setPoolCode(String poolCode) {
    this.poolCode = poolCode;
  }

  public String getIsComplementaryPool() {
    return isComplementaryPool;
  }

  public void setIsComplementaryPool(String isComplementaryPool) {
    this.isComplementaryPool = isComplementaryPool;
  }

  public String getPoolTypeCode() {
    return poolTypeCode;
  }

  public void setPoolTypeCode(String poolTypeCode) {
    this.poolTypeCode = poolTypeCode;
  }

  public String getLocationCode() {
    return locationCode;
  }

  public void setLocationCode(String locationCode) {
    this.locationCode = locationCode;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getConstructionDate() {
    return constructionDate;
  }

  public void setConstructionDate(String constructionDate) {
    this.constructionDate = constructionDate;
  }

  public String getDeliverDate() {
    return deliverDate;
  }

  public void setDeliverDate(String deliverDate) {
    this.deliverDate = deliverDate;
  }

  public String getAcceptanceDate() {
    return acceptanceDate;
  }

  public void setAcceptanceDate(String acceptanceDate) {
    this.acceptanceDate = acceptanceDate;
  }

  public String getOwnerCode() {
    return ownerCode;
  }

  public void setOwnerCode(String ownerCode) {
    this.ownerCode = ownerCode;
  }

  public String getLongtitude() {
    return longtitude;
  }

  public void setLongtitude(String longtitude) {
    this.longtitude = longtitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
