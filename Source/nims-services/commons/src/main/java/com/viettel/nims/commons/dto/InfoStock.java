package com.viettel.nims.commons.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "lstInfo")
public class InfoStock {

  private String accountingCode;
  private String accountingModelCode;
  private String accountingModelName;
  private String accountingName;
  private Long checkDeposit;
  private Long checkDial;
  private Long checkSerial;
  private Long discountGroupId;
  private String name;
  private String notes;
  private Long profileId;
  private Long sourcePrice;
  private Long status;
  private String stockModelCode;
  private Long stockModelId;
  private Long stockModelType;
  private Long stockTypeId;
  private String supplyGroupName;
  private Long telecomServiceId;
  private String unit;
  private Long vat;

  @XmlElement(name = "ACCOUNTING_CODE")
  public String getAccountingCode() {
    return accountingCode;
  }

  public void setAccountingCode(String accountingCode) {
    this.accountingCode = accountingCode;
  }

  @XmlElement(name = "ACCOUNTING_MODEL_CODE")
  public String getAccountingModelCode() {
    return accountingModelCode;
  }

  public void setAccountingModelCode(String accountingModelCode) {
    this.accountingModelCode = accountingModelCode;
  }

  @XmlElement(name = "ACCOUNTING_MODEL_NAME")
  public String getAccountingModelName() {
    return accountingModelName;
  }

  public void setAccountingModelName(String accountingModelName) {
    this.accountingModelName = accountingModelName;
  }

  @XmlElement(name = "ACCOUNTING_NAME")
  public String getAccountingName() {
    return accountingName;
  }

  public void setAccountingName(String accountingName) {
    this.accountingName = accountingName;
  }

  @XmlElement(name = "CHECK_DEPOSIT")
  public Long getCheckDeposit() {
    return checkDeposit;
  }

  public void setCheckDeposit(Long checkDeposit) {
    this.checkDeposit = checkDeposit;
  }

  @XmlElement(name = "CHECK_DIAL")
  public Long getCheckDial() {
    return checkDial;
  }

  public void setCheckDial(Long checkDial) {
    this.checkDial = checkDial;
  }

  @XmlElement(name = "CHECK_SERIAL")
  public Long getCheckSerial() {
    return checkSerial;
  }

  public void setCheckSerial(Long checkSerial) {
    this.checkSerial = checkSerial;
  }

  @XmlElement(name = "DISCOUNT_GROUP_ID")
  public Long getDiscountGroupId() {
    return discountGroupId;
  }

  public void setDiscountGroupId(Long discountGroupId) {
    this.discountGroupId = discountGroupId;
  }

  @XmlElement(name = "NAME")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @XmlElement(name = "NOTES")
  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @XmlElement(name = "PROFILE_ID")
  public Long getProfileId() {
    return profileId;
  }

  public void setProfileId(Long profileId) {
    this.profileId = profileId;
  }

  @XmlElement(name = "SOURCE_PRICE")
  public Long getSourcePrice() {
    return sourcePrice;
  }

  public void setSourcePrice(Long sourcePrice) {
    this.sourcePrice = sourcePrice;
  }

  @XmlElement(name = "STATUS")
  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  @XmlElement(name = "STOCK_MODEL_CODE")
  public String getStockModelCode() {
    return stockModelCode;
  }

  public void setStockModelCode(String stockModelCode) {
    this.stockModelCode = stockModelCode;
  }

  @XmlElement(name = "STOCK_MODEL_ID")
  public Long getStockModelId() {
    return stockModelId;
  }

  public void setStockModelId(Long stockModelId) {
    this.stockModelId = stockModelId;
  }

  @XmlElement(name = "STOCK_MODEL_TYPE")
  public Long getStockModelType() {
    return stockModelType;
  }

  public void setStockModelType(Long stockModelType) {
    this.stockModelType = stockModelType;
  }

  @XmlElement(name = "STOCK_TYPE_ID")
  public Long getStockTypeId() {
    return stockTypeId;
  }

  public void setStockTypeId(Long stockTypeId) {
    this.stockTypeId = stockTypeId;
  }

  @XmlElement(name = "SUPPLY_GROUP_NAME")
  public String getSupplyGroupName() {
    return supplyGroupName;
  }

  public void setSupplyGroupName(String supplyGroupName) {
    this.supplyGroupName = supplyGroupName;
  }

  @XmlElement(name = "TELECOM_SERVICE_ID")
  public Long getTelecomServiceId() {
    return telecomServiceId;
  }

  public void setTelecomServiceId(Long telecomServiceId) {
    this.telecomServiceId = telecomServiceId;
  }

  @XmlElement(name = "UNIT")
  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  @XmlElement(name = "VAT")
  public Long getVat() {
    return vat;
  }

  public void setVat(Long vat) {
    this.vat = vat;
  }
}
