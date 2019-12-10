package com.viettel.nims.transmission.model;

import com.viettel.nims.commons.util.PaginationDTO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by VTN-PTPM-NV64 on 8/8/2019.
 */
@Entity
@Table(name = "CAT_LOCATION")
public class CatLocationBO extends PaginationDTO {
  private Long locationId;
  private Long tenantId;
  private String locationCode;
  private String locationName;
  private Long parentId;
  private Integer terrain;
  private Timestamp createTime;
  private Timestamp updateTime;
  private Integer rowStatus;

  @Id
  @Column(name = "LOCATION_ID")
  public Long getLocationId() {
    return locationId;
  }

  public void setLocationId(Long locationId) {
    this.locationId = locationId;
  }

  @Basic
  @Column(name = "TENANT_ID")
  public Long getTenantId() {
    return tenantId;
  }

  public void setTenantId(Long tenantId) {
    this.tenantId = tenantId;
  }

  @Basic
  @Column(name = "LOCATION_CODE")
  public String getLocationCode() {
    return locationCode;
  }

  public void setLocationCode(String locationCode) {
    this.locationCode = locationCode;
  }

  @Basic
  @Column(name = "LOCATION_NAME")
  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  @Basic
  @Column(name = "PARENT_ID")
  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  @Basic
  @Column(name = "TERRAIN")
  public Integer getTerrain() {
    return terrain;
  }

  public void setTerrain(Integer terrain) {
    this.terrain = terrain;
  }

  @Basic
  @Column(name = "CREATE_TIME")
  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  @Basic
  @Column(name = "UPDATE_TIME")
  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  @Basic
  @Column(name = "ROW_STATUS")
  public Integer getRowStatus() {
    return rowStatus;
  }

  public void setRowStatus(Integer rowStatus) {
    this.rowStatus = rowStatus;
  }
}
