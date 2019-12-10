package com.viettel.nims.transmission.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CNT_CABLE_LANE")
public class CntCableLaneBO {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long cableId;

  private Long laneId;

  private Date createTime;

  private Date updateTime;

  private Long rowStatus;

  @Id
  @Column(name = "ID")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name = "CABLE_ID")
  public Long getCableId() {
    return cableId;
  }

  public void setCableId(Long cableId) {
    this.cableId = cableId;
  }

  @Column(name = "LANE_ID")
  public Long getLaneId() {
    return laneId;
  }

  public void setLaneId(Long laneId) {
    this.laneId = laneId;
  }

  @Column(name = "CREATE_TIME")
  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  @Column(name = "UPDATE_TIME")
  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  @Column(name = "ROW_STATUS")
  public Long getRowStatus() {
    return rowStatus;
  }

  public void setRowStatus(Long rowStatus) {
    this.rowStatus = rowStatus;
  }


}
