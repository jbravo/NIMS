/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.Valid;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

/**
 * @author Admin
 */
@Data
public class CatLocationDTO implements Serializable {

  private Long locationId;
  private String locationCode;
  private String locationName;
  private Long parentId;
  private Short terrain;
  private Date createTime;
  private Date updateTime;
  private Integer rowStatus;
  @Valid
  @Nullable
  private List<CntDepartmentLocationDTO> cntDepartmentLocationList;

  @Valid
  @Nullable
  private CatTenantsDTO tenantId;

  public Long getLocationId() {
    return locationId;
  }

  public void setLocationId(Long locationId) {
    this.locationId = locationId;
  }

  public String getLocationCode() {
    return locationCode;
  }

  public void setLocationCode(String locationCode) {
    this.locationCode = locationCode;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Short getTerrain() {
    return terrain;
  }

  public void setTerrain(Short terrain) {
    this.terrain = terrain;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getRowStatus() {
    return rowStatus;
  }

  public void setRowStatus(Integer rowStatus) {
    this.rowStatus = rowStatus;
  }

  public List<CntDepartmentLocationDTO> getCntDepartmentLocationList() {
    return cntDepartmentLocationList;
  }

  public void setCntDepartmentLocationList(List<CntDepartmentLocationDTO> cntDepartmentLocationList) {
    this.cntDepartmentLocationList = cntDepartmentLocationList;
  }

  public CatTenantsDTO getTenantId() {
    return tenantId;
  }

  public void setTenantId(CatTenantsDTO tenantId) {
    this.tenantId = tenantId;
  }
}
