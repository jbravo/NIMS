package com.viettel.nims.transmission.model.dto;


import com.viettel.nims.transmission.commom.annotation.Element;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;

@SheetSerializable(sheetDataIndex = 0, startRow = 4, totalCols = 14, resultCols = 14, flagRow = 3)
public class InfraPillarDTO {
  @Element(type = "String", index = 0, header = "common.label.stt")
  private String stt;

  @Element(type = "String", index = 1, header = "pillar.header.laneCode")
  private String laneCode;

  @Element(type = "String", index = 2, header = "pillar.header.pillarIndex", isGenerateValue = true)
  private String pillarIndex;

  @Element(type = "String", index = 3, header = "pillar.header.pillarCode", isGenerateValue = true)
  private String pillarCode;

  @Element(type = "String", index = 4, header = "pillar.header.pillarTypeCode")
  private String pillarTypeCode;

  @Element(type = "String", index = 5, header = "pillar.header.deptCode")
  private String deptCode;

  @Element(type = "String", index = 6, header = "pillar.header.locationCode")
  private String locationCode;

  @Element(type = "String", index = 7, header = "pillar.header.ownerCode")
  private String ownerCode;

  @Element(type = "String", index = 8, header = "pillar.header.address")
  private String address;

  @Element(type = "String", index = 9, header = "pillar.header.constructionDateStr")
  private String constructionDateStr;

  @Element(type = "String", index = 10, header = "pillar.header.statusName")
  private String statusName;

  @Element(type = "String", index = 11, header = "pillar.header.longitudeStr")
  private String longitudeStr;

  @Element(type = "String", index = 12, header = "pillar.header.latitudeStr")
  private String latitudeStr;

  @Element(type = "String", index = 13, header = "pillar.header.note")
  private String note;

  //-------------------------------Getter,Setter-----------------------------------------

  public String getStt() {
    return stt;
  }

  public void setStt(String stt) {
    this.stt = stt;
  }

  public String getLaneCode() {
    return laneCode;
  }

  public void setLaneCode(String laneCode) {
    this.laneCode = laneCode;
  }

  public String getPillarIndex() {
    return pillarIndex;
  }

  public void setPillarIndex(String pillarIndex) {
    this.pillarIndex = pillarIndex;
  }

  public String getPillarCode() {
    return pillarCode;
  }

  public void setPillarCode(String pillarCode) {
    this.pillarCode = pillarCode;
  }

  public String getPillarTypeCode() {
    return pillarTypeCode;
  }

  public void setPillarTypeCode(String pillarTypeCode) {
    this.pillarTypeCode = pillarTypeCode;
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

  public String getOwnerCode() {
    return ownerCode;
  }

  public void setOwnerCode(String ownerCode) {
    this.ownerCode = ownerCode;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
