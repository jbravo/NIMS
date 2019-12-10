/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.viettel.nims.optimalDesign.entity.CatDepartment;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

/**
 * @author Admin
 */
@Getter
@Setter
@NoArgsConstructor
public class CatDepartmentDTO implements Serializable {


  private Long deptId;
  private String deptCode;
  private String deptName;
  private Long parentId;
//  private Date createTime;
//  private Date updateTime;
//  private Integer rowStatus;
  @JsonIgnore
  private List<SysUsersDTO> sysUsersList;
//  @JsonIgnore
//  private CatTenantsDTO tenantId;
//  @JsonIgnore
//  private List<CntDepartmentLocationDTO> cntDepartmentLocationList;
  private List<CatDepartmentDTO> listChild;

  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  public String getDeptCode() {
    return deptCode;
  }

  public void setDeptCode(String deptCode) {
    this.deptCode = deptCode;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public List<CatDepartmentDTO> getListChild() {
    return listChild;
  }

  public void setListChild(List<CatDepartmentDTO> listChild) {
    this.listChild = listChild;
  }
}
