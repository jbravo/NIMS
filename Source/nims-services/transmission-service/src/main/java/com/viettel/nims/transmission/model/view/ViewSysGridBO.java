package com.viettel.nims.transmission.model.view;

import javax.persistence.*;

@Entity
@Table(name = "view_sys_grid")
public class ViewSysGridBO {

  private Long gridId;
  private Long columnId;
  private String gridViewName;
  private String columnName;
  private Long userId;
  private Long isShow;
  private String note;

  @Basic
  @Column(name = "GRID_ID")
  public Long getGridId() {
    return gridId;
  }

  public void setGridId(Long gridId) {
    this.gridId = gridId;
  }

  @Id
  @Column(name = "COLUMN_ID")
  public Long getColumnId() {
    return columnId;
  }

  public void setColumnId(Long columnId) {
    this.columnId = columnId;
  }

  @Basic
  @Column(name = "GRID_VIEW_NAME")
  public String getGridViewName() {
    return gridViewName;
  }

  public void setGridViewName(String gridViewName) {
    this.gridViewName = gridViewName;
  }

  @Basic
  @Column(name = "COLUMN_NAME")
  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  @Basic
  @Column(name = "USER_ID")
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Basic
  @Column(name = "IS_SHOW")
  public Long getIsShow() {
    return isShow;
  }

  public void setIsShow(Long isShow) {
    this.isShow = isShow;
  }

  @Basic
  @Column(name = "NOTE")
  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
