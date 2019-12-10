/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import com.viettel.nims.optimalDesign.entity.CatDepartment;
import com.viettel.nims.optimalDesign.entity.CatLocation;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Admin
 */
@Data
public class CntDepartmentLocationDTO implements Serializable {

  private Long id;
  private Date updateTime;
  private CatDepartment deptId;
  private CatLocation locationId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public CatDepartment getDeptId() {
    return deptId;
  }

  public void setDeptId(CatDepartment deptId) {
    this.deptId = deptId;
  }

  public CatLocation getLocationId() {
    return locationId;
  }

  public void setLocationId(CatLocation locationId) {
    this.locationId = locationId;
  }
}
