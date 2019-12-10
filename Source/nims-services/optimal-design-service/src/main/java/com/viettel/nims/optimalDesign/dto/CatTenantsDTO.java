/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viettel.nims.optimalDesign.entity.CatDepartment;
import com.viettel.nims.optimalDesign.entity.CatLocation;
import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Admin
 */
@Data
public class CatTenantsDTO implements Serializable {

  private Long tenantId;
  private String tenantCode;
  private String tenantName;
  @JsonIgnore
  private List<CatDepartmentDTO> catDepartmentList;
  private List<CatLocationDTO> catLocationList;

  public Long getTenantId() {
    return tenantId;
  }

  public void setTenantId(Long tenantId) {
    this.tenantId = tenantId;
  }

  public String getTenantCode() {
    return tenantCode;
  }

  public void setTenantCode(String tenantCode) {
    this.tenantCode = tenantCode;
  }

  public String getTenantName() {
    return tenantName;
  }

  public void setTenantName(String tenantName) {
    this.tenantName = tenantName;
  }

  public List<CatDepartmentDTO> getCatDepartmentList() {
    return catDepartmentList;
  }

  public void setCatDepartmentList(List<CatDepartmentDTO> catDepartmentList) {
    this.catDepartmentList = catDepartmentList;
  }

  public List<CatLocationDTO> getCatLocationList() {
    return catLocationList;
  }

  public void setCatLocationList(List<CatLocationDTO> catLocationList) {
    this.catLocationList = catLocationList;
  }
}
