package com.viettel.nims.transmission.model.view;

import javax.persistence.*;

/**
 * Created by VTN-PTPM-NV64 on 8/20/2019.
 */
@Entity
@Table(name = "view_cat_tenants")
public class ViewCatTenantsBO {
  private long stationId;
  private String stationCode;
  private String tenantCode;
  private Long deptId;
  private Long tenantId;

  @Id
  @Column(name = "STATION_ID")
  public long getStationId() {
    return stationId;
  }

  public void setStationId(long stationId) {
    this.stationId = stationId;
  }

  @Basic
  @Column(name = "STATION_CODE")
  public String getStationCode() {
    return stationCode;
  }

  public void setStationCode(String stationCode) {
    this.stationCode = stationCode;
  }

  @Basic
  @Column(name = "TENANT_CODE")
  public String getTenantCode() {
    return tenantCode;
  }

  public void setTenantCode(String tenantCode) {
    this.tenantCode = tenantCode;
  }

  @Basic
  @Column(name = "DEPT_ID")
  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }
  @Basic
  @Column(name = "TENANT_ID")
  public Long getTenantId() {
    return tenantId;
  }

  public void setTenantId(Long tenantId) {
    this.tenantId = tenantId;
  }
}
