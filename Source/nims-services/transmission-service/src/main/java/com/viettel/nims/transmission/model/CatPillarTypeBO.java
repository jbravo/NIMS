package com.viettel.nims.transmission.model;

import com.viettel.nims.commons.util.PaginationDTO;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Van-Ba on 3/9/2019.
 */
@Data
@Entity
@Table(name = "CAT_PILLAR_TYPE")
public class CatPillarTypeBO{
  @Id
  @Column(name = "PILLAR_TYPE_ID", nullable = false)
  private Long pillarTypeId;

  @Column(name = "PILLAR_TYPE_CODE", length = 100, nullable = false)
  private String pillarTypeCode;

  @Column(name = "NOTE", length = 200, nullable = false)
  private String note;

  @Column(name = "CREATE_TIME")
  private Timestamp createTime;

  @Column(name = "UPDATE_TIME")
  private Timestamp updateTime;

  @Column(name = "ROW_STATUS")
  private int rowStatus;

  @Column(name = "HEIGHT")
  private Float height;

  public Long getPillarTypeId() {
    return pillarTypeId;
  }

  public void setPillarTypeId(Long pillarTypeId) {
    this.pillarTypeId = pillarTypeId;
  }

  public String getPillarTypeCode() {
    return pillarTypeCode;
  }

  public void setPillarTypeCode(String pillarTypeCode) {
    this.pillarTypeCode = pillarTypeCode;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  public int getRowStatus() {
    return rowStatus;
  }

  public void setRowStatus(int rowStatus) {
    this.rowStatus = rowStatus;
  }

  public Float getHeight() {
    return height;
  }

  public void setHeight(Float height) {
    this.height = height;
  }
}
