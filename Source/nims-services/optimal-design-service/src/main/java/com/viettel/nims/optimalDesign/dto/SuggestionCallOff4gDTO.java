/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Admin
 */
@Data
public class SuggestionCallOff4gDTO implements Serializable {

  private Long suggestionCallOffId;
  private String cabinetCode;

  @NotNull(message = "suggestionCallOff2g.cabinetCode.required")
  @Length(max = 100, message = "suggestionCallOff2g.cabinetCode.length.max.100")

  private String cabinetCodeSuggest;
  private Date expectedBroadcastDate;
  private Integer btsStationType;

  private Long bandwidth;

  @NotNull(message = "suggestionCallOff4g.purposeType.required")
  private Integer purposeType;
  private Integer transType;
  private Integer transInterface;
  private Long transCapacity;

  @NotNull(message = "suggestionCallOff4g.cfgSoftware.required")
  @Min(value = 0, message = "suggestionCallOff4g.cfgSoftware.value.invalid")
  @Max(value = 1000000000, message = "suggestionCallOff4g.cfgSoftware.value.invalid")
  private Integer cfgSoftware;

  @NotNull(message = "suggestionCallOff4g.cfgHardwave.required")
  @Min(value = 0, message = "suggestionCallOff4g.cfgHardwave.value.invalid")
  @Max(value = 1000000000, message = "suggestionCallOff4g.cfgHardwave.value.invalid")
  private Integer cfgHardwave;

  @NotNull(message = "suggestionCallOff4g.cfg.required")
  @Min(value = 0, message = "suggestionCallOff4g.cfg.value.invalid")
  @Max(value = 1000000000, message = "suggestionCallOff4g.cfg.value.invalid")
  private Integer cfg;

  @NotNull(message = "suggestionCallOff4g.isBuildingEdge.required")
  private Integer isBuildingEdge;

//  @NotNull(message = "suggestionCallOff4g.numberSector.required")
//  @Min(value = 0, message = "suggestionCallOff4g.numberSector.value.invalid")
//  @Max(value = 1000000000, message = "suggestionCallOff4g.numberSector.value.invalid")
  private Integer numberSector;

  @Length(max = 100, message = "suggestionCallOff2g.designer.length.max.100")
  private String designer;

  @NotNull(message = "suggestionCallOff4g.deviceTypeId.required")
  private Long deviceTypeId;
  @NotNull(message = "suggestionCallOff4g.itemId.required")
  private Long itemId;
  private Long suggestId;

  @Valid
  private List<SuggestionSector4gDTO> suggestionSector4gDTOList;

  public Long getSuggestionCallOffId() {
    return suggestionCallOffId;
  }

  public void setSuggestionCallOffId(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

  public String getCabinetCode() {
    return cabinetCode;
  }

  public void setCabinetCode(String cabinetCode) {
    this.cabinetCode = cabinetCode;
  }

  public String getCabinetCodeSuggest() {
    return cabinetCodeSuggest;
  }

  public void setCabinetCodeSuggest(String cabinetCodeSuggest) {
    this.cabinetCodeSuggest = cabinetCodeSuggest;
  }

  public Date getExpectedBroadcastDate() {
    return expectedBroadcastDate;
  }

  public void setExpectedBroadcastDate(Date expectedBroadcastDate) {
    this.expectedBroadcastDate = expectedBroadcastDate;
  }

  public Integer getBtsStationType() {
    return btsStationType;
  }

  public void setBtsStationType(Integer btsStationType) {
    this.btsStationType = btsStationType;
  }

  public Long getBandwidth() {
    return bandwidth;
  }

  public void setBandwidth(Long bandwidth) {
    this.bandwidth = bandwidth;
  }

  public Integer getPurposeType() {
    return purposeType;
  }

  public void setPurposeType(Integer purposeType) {
    this.purposeType = purposeType;
  }

  public Integer getTransType() {
    return transType;
  }

  public void setTransType(Integer transType) {
    this.transType = transType;
  }

  public Integer getTransInterface() {
    return transInterface;
  }

  public void setTransInterface(Integer transInterface) {
    this.transInterface = transInterface;
  }

  public Long getTransCapacity() {
    return transCapacity;
  }

  public void setTransCapacity(Long transCapacity) {
    this.transCapacity = transCapacity;
  }

  public Integer getCfgSoftware() {
    return cfgSoftware;
  }

  public void setCfgSoftware(Integer cfgSoftware) {
    this.cfgSoftware = cfgSoftware;
  }

  public Integer getCfgHardwave() {
    return cfgHardwave;
  }

  public void setCfgHardwave(Integer cfgHardwave) {
    this.cfgHardwave = cfgHardwave;
  }

  public Integer getCfg() {
    return cfg;
  }

  public void setCfg(Integer cfg) {
    this.cfg = cfg;
  }

  public Integer getIsBuildingEdge() {
    return isBuildingEdge;
  }

  public void setIsBuildingEdge(Integer isBuildingEdge) {
    this.isBuildingEdge = isBuildingEdge;
  }

  public Integer getNumberSector() {
    return numberSector;
  }

  public void setNumberSector(Integer numberSector) {
    this.numberSector = numberSector;
  }

  public String getDesigner() {
    return designer;
  }

  public void setDesigner(String designer) {
    this.designer = designer;
  }

  public Long getDeviceTypeId() {
    return deviceTypeId;
  }

  public void setDeviceTypeId(Long deviceTypeId) {
    this.deviceTypeId = deviceTypeId;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Long getSuggestId() {
    return suggestId;
  }

  public void setSuggestId(Long suggestId) {
    this.suggestId = suggestId;
  }

  public List<SuggestionSector4gDTO> getSuggestionSector4gDTOList() {
    return suggestionSector4gDTOList;
  }

  public void setSuggestionSector4gDTOList(List<SuggestionSector4gDTO> suggestionSector4gDTOList) {
    this.suggestionSector4gDTOList = suggestionSector4gDTOList;
  }
}
