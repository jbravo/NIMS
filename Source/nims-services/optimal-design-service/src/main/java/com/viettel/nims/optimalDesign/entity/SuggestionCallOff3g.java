/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import java.io.Serializable;


import java.util.Date;
import javax.persistence.*;


/**
 * @author Admin
 */
@Entity
@Table(name = "SUGGESTION_CALL_OFF_3G")
public class SuggestionCallOff3g implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @Column(name = "SUGGESTION_CALL_OFF_ID")
  private Long suggestionCallOffId;
  @Column(name = "CABINET_CODE")
  private String cabinetCode;
  @Column(name = "CABINET_CODE_SUGGEST")
  private String cabinetCodeSuggest;
  @Column(name = "EXPECTED_BROADCAST_DATE")
  @Temporal(TemporalType.DATE)
  private Date expectedBroadcastDate;
  @Column(name = "BTS_STATION_TYPE")
  private Integer btsStationType;
  @Column(name = "BANDWIDTH")
  private Long bandwidth;
  @Column(name = "PURPOSE_TYPE")
  private Integer purposeType;
  @Column(name = "TRANS_TYPE")
  private Integer transType;
  @Column(name = "TRANS_INTERFACE")
  private Integer transInterface;
  @Column(name = "TRANS_CAPACITY")
  private Long transCapacity;
  @Column(name = "CFG")
  private Integer cfg;
  @Column(name = "IS_BUILDING_EDGE")
  private Integer isBuildingEdge;
  @Column(name = "NUMBER_SECTOR")
  private Integer numberSector;
  @Column(name = "DESIGNER")
  private String designer;
  @Column(name = "DEVICE_TYPE_ID")
  private Long deviceTypeId;
  @Column(name = "CABINET_SOLUTION_TYPE")
  private Long itemId;

  @Column(name = "SUGGEST_ID")
  private Long suggestId;

  public SuggestionCallOff3g() {
  }

  public SuggestionCallOff3g(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

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

  public Long getCatDeviceId() {
    return deviceTypeId;
  }

  public void setCatDeviceId(Long catDeviceId) {
    this.deviceTypeId = catDeviceId;
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

  public Long getDeviceTypeId() {
    return deviceTypeId;
  }

  public void setDeviceTypeId(Long deviceTypeId) {
    this.deviceTypeId = deviceTypeId;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (suggestionCallOffId != null ? suggestionCallOffId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SuggestionCallOff3g)) {
      return false;
    }
    SuggestionCallOff3g other = (SuggestionCallOff3g) object;
    if ((this.suggestionCallOffId == null && other.suggestionCallOffId != null) || (this.suggestionCallOffId != null && !this.suggestionCallOffId.equals(other.suggestionCallOffId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Model.SuggestionCallOff3g[ suggestionCallOffId=" + suggestionCallOffId + " ]";
  }

}
