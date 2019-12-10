package com.viettel.nims.transmission.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by VTN-PTPM-NV64 on 8/14/2019.
 */
@Entity
@Table(name = "CAT_ITEM")
public class CatItemBO {
  private Long itemId;
  private String itemCode;
  private String itemName;
  private Long categoryId;
  private String note;
  private Timestamp createTime;
  private Timestamp updateTime;
  private Integer rowStatus;

  @Id
  @Column(name = "ITEM_ID")
  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  @Basic
  @Column(name = "ITEM_CODE")
  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  @Basic
  @Column(name = "ITEM_NAME")
  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  @Basic
  @Column(name = "CATEGORY_ID")
  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
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
