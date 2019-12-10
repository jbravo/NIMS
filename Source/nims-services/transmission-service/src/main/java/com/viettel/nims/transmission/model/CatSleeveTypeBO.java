package com.viettel.nims.transmission.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CAT_SLEEVE_TYPE")
public class CatSleeveTypeBO {
  private Long sleeveTypeId;
  private String sleeveTypeCode;
  private Long type;
  private Long capacity;
  private String note;
  private Timestamp createTime;
  private Timestamp updateTime;
  private Integer rowStatus;

  @Id
  @Column(name = "SLEEVE_TYPE_ID")
  public Long getSleeveTypeId() {
    return sleeveTypeId;
  }

  public void setSleeveTypeId(Long sleeveTypeId) {
    this.sleeveTypeId = sleeveTypeId;
  }

  @Basic
  @Column(name = "SLEEVE_TYPE_CODE")
  public String getSleeveTypeCode() {
    return sleeveTypeCode;
  }

  public void setSleeveTypeCode(String sleeveTypeCode) {
    this.sleeveTypeCode = sleeveTypeCode;
  }

  @Basic
  @Column(name = "TYPE")
  public Long getType() {
    return type;
  }

  public void setType(Long type) {
    this.type = type;
  }

  @Basic
  @Column(name = "CAPACITY")
  public Long getCapacity() {
    return capacity;
  }

  public void setCapacity(Long capacity) {
    this.capacity = capacity;
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
