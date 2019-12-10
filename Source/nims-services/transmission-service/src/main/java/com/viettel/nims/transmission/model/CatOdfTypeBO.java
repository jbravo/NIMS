package com.viettel.nims.transmission.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

/**
 * CatOdfTypeBO
 * Version 1.0
 * Date: 08-30-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 08-30-2019				DungPH				Create
 */
@Entity
@Table(name = "CAT_ODF_TYPE")
public class CatOdfTypeBO {
  private Long odfTypeId;
  private String odfTypeCode;
  private Integer capacity;
  private Long vendorId;
  private String note;
  private Date createTime;
  private Date updateTime;
  private Integer rowStatus;

  @Id
  @Column(name = "ODF_TYPE_ID")
  public Long getOdfTypeId() {
    return odfTypeId;
  }

  public void setOdfTypeId(Long odfTypeId) {
    this.odfTypeId = odfTypeId;
  }

  @Basic
  @Column(name = "ODF_TYPE_CODE")
  public String getOdfTypeCode() {
    return odfTypeCode;
  }

  public void setOdfTypeCode(String odfTypeCode) {
    this.odfTypeCode = odfTypeCode;
  }

  @Basic
  @Column(name = "CAPACITY")
  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  @Basic
  @Column(name = "VENDOR_ID")
  public Long getVendorId() {
    return vendorId;
  }

  public void setVendorId(Long vendorId) {
    this.vendorId = vendorId;
  }

  @Basic
  @Column(name = "NOTE")
  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @Basic
  @CreationTimestamp
  @Column(name = "CREATE_TIME")
  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @Basic
  @UpdateTimestamp
  @Column(name = "UPDATE_TIME")
  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
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
