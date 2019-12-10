package com.viettel.nims.transmission.model.dto;

import com.viettel.nims.transmission.commom.annotation.Element;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;

@SheetSerializable(sheetDataIndex = 0, startRow = 4, totalCols = 16, resultCols = 16, flagRow = 3)
public class InfraSleeveDTO {
  @Element(type = "String", index = 0, header = "common.label.stt")
  private String index;
  @Element(type = "String", index = 1, header = "sleeve.header.pillarCode")
  private String pillarCode;
  @Element(type = "String", index = 2, header = "sleeve.header.poolCode")
  private String poolCode;
  @Element(type = "String", index = 3, header = "sleeve.header.cableLaneCode")
  private String cableLaneCode;
  @Element(type = "String", index = 4, header = "sleeve.header.sleeveCode", isGenerateValue = true)
  private String sleeveIndex;
  @Element(type = "String", index = 5, header = "sleeves.code", isGenerateValue = true)
  private String sleeveCode;
  @Element(type = "String", index = 6, header = "sleeve.header.sleeveTypeCode")
  private String sleeveTypeCode;
  @Element(type = "String", index = 7, header = "sleeve.header.dept")
  private String deptCode;
  @Element(type = "String", index = 8, header = "odf.vendorName")
  private String vendorCode;
  @Element(type = "String", index = 9, header = "sleeves.owner")
  private String ownerCode;
  @Element(type = "String", index = 10, header = "sleeve.header.purpose")
  private String purpose;
  @Element(type = "String", index = 11, header = "sleeve.header.status")
  private String status;
  @Element(type = "String", index = 12, header = "sleeve.header.installationDate")
  private String instalationDate;
  @Element(type = "String", index = 13, header = "sleeve.header.modifyDate")
  private String modifiedDate;
  @Element(type = "String", index = 14, header = "sleeves.label.serial")
  private String serialCode;
  @Element(type = "String", index = 15, header = "sleeves.cause.error")
  private String note;

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getPillarCode() {
    return pillarCode;
  }

  public void setPillarCode(String pillarCode) {
    this.pillarCode = pillarCode;
  }

  public String getPoolCode() {
    return poolCode;
  }

  public void setPoolCode(String poolCode) {
    this.poolCode = poolCode;
  }

  public String getCableLaneCode() {
    return cableLaneCode;
  }

  public void setCableLaneCode(String cableLaneCode) {
    this.cableLaneCode = cableLaneCode;
  }

  public String getSleeveIndex() {
    return sleeveIndex;
  }

  public void setSleeveIndex(String sleeveIndex) {
    this.sleeveIndex = sleeveIndex;
  }

  public String getSleeveCode() {
    return sleeveCode;
  }

  public void setSleeveCode(String sleeveCode) {
    this.sleeveCode = sleeveCode;
  }

  public String getSleeveTypeCode() {
    return sleeveTypeCode;
  }

  public void setSleeveTypeCode(String sleeveTypeCode) {
    this.sleeveTypeCode = sleeveTypeCode;
  }

  public String getDeptCode() {
    return deptCode;
  }

  public void setDeptCode(String deptCode) {
    this.deptCode = deptCode;
  }

  public String getVendorCode() {
    return vendorCode;
  }

  public void setVendorCode(String vendorCode) {
    this.vendorCode = vendorCode;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getInstalationDate() {
    return instalationDate;
  }

  public void setInstalationDate(String instalationDate) {
    this.instalationDate = instalationDate;
  }

  public String getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(String modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  public String getSerialCode() {
    return serialCode;
  }

  public void setSerialCode(String serialCode) {
    this.serialCode = serialCode;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getOwnerCode() {
    return ownerCode;
  }

  public void setOwnerCode(String ownerCode) {
    this.ownerCode = ownerCode;
  }
}
