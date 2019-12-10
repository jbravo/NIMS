package com.viettel.nims.transmission.model.view;

import com.viettel.nims.commons.util.PaginationDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "view_weld_sleeve")
public class ViewWeldSleeveBO extends PaginationDTO implements Serializable {
  private Integer sourceLineNo;
  private Integer destLineNo;
  private String createUser;
  private BigDecimal attenuation;
  private String sourceCableCode;
  private String destCableCode;
  private String sleeveCode;
  private Long sleeveId;
  private Long sourceCableId;
  private Long destCableId;
  private Long sourceCableCableLandId;
  private Long destCableCableLandId;
  private Long laneId;
  private String laneCode;


  @Id
  @Column(name = "SOURCE_CABLE_ID")
  public Long getSourceCableId() {
    return sourceCableId;
  }

  public void setSourceCableId(Long sourceCableId) {
    this.sourceCableId = sourceCableId;
  }

  @Id
  @Column(name = "DEST_CABLE_ID")
  public Long getDestCableId() {
    return destCableId;
  }

  public void setDestCableId(Long destCableId) {
    this.destCableId = destCableId;
  }

  @Column(name = "SOURCE_CABLE_CABLE_LANE_ID")
  public Long getSourceCableCableLandId() {
    return sourceCableCableLandId;
  }

  public void setSourceCableCableLandId(Long sourceCableCableLandId) {
    this.sourceCableCableLandId = sourceCableCableLandId;
  }
  @Column(name = "DEST_CABLE_CABLE_LANE_ID")
  public Long getDestCableCableLandId() {
    return destCableCableLandId;
  }

  public void setDestCableCableLandId(Long destCableCableLandId) {
    this.destCableCableLandId = destCableCableLandId;
  }

  @Id
  @Column(name = "SOURCE_LINE_NO")
  public Integer getSourceLineNo() {
    return sourceLineNo;
  }

  public void setSourceLineNo(Integer sourceLineNo) {
    this.sourceLineNo = sourceLineNo;
  }

  @Id
  @Column(name = "DEST_LINE_NO")
  public Integer getDestLineNo() {
    return destLineNo;
  }

  public void setDestLineNo(Integer destLineNo) {
    this.destLineNo = destLineNo;
  }

  @Column(name = "CREATE_USER")
  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  @Column(name = "ATTENUATION")
  public BigDecimal getAttenuation() {
    return attenuation;
  }

  public void setAttenuation(BigDecimal attenuation) {
    this.attenuation = attenuation;
  }

  @Column(name = "SOURCE_CABLE_CODE")
  public String getSourceCableCode() {
    return sourceCableCode;
  }

  public void setSourceCableCode(String sourceCableCode) {
    this.sourceCableCode = sourceCableCode;
  }

  @Column(name = "DEST_CABLE_CODE")
  public String getDestCableCode() {
    return destCableCode;
  }

  public void setDestCableCode(String destCableCode) {
    this.destCableCode = destCableCode;
  }

  @Column(name = "SLEEVE_CODE")
  public String getSleeveCode() {
    return sleeveCode;
  }

  public void setSleeveCode(String sleeveCode) {
    this.sleeveCode = sleeveCode;
  }

  @Id
  @Column(name = "SLEEVE_ID")
  public Long getSleeveId() {
    return sleeveId;
  }

  public void setSleeveId(Long sleeveId) {
    this.sleeveId = sleeveId;
  }

  @Column(name = "LANE_ID")
  public Long getLaneId() {
    return laneId;
  }

  public void setLaneId(Long laneId) {
    this.laneId = laneId;
  }

  @Column(name = "LANE_CODE")
  public String getLaneCode() {
    return laneCode;
  }

  public void setLaneCode(String laneCode) {
    this.laneCode = laneCode;
  }

  public void setTotalRecordsLaneId(Query queryCountLaneId) {
  }

//  @Transient
//  private Long totalRecordsLaneId;
//
//  public Long getTotalRecordsLaneId() {
//    return totalRecordsLaneId;
//  }
//
//  public void setTotalRecordsLaneId(Long totalRecordsLaneId) {
//    this.totalRecordsLaneId = totalRecordsLaneId;
//  }
}
