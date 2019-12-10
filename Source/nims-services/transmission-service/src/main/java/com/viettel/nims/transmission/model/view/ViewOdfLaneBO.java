package com.viettel.nims.transmission.model.view;

import com.viettel.nims.commons.util.PaginationDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "view_odf_line")
public class ViewOdfLaneBO extends PaginationDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @Column(name = "ODF_ID")
  private Long odfId;

  @Column(name = "ODF_CODE")
  private String odfCode;

  @Column(name = "COUPLER_NO")
  private Long couplerNo;

  @Id
  @Column(name = "CABLE_ID")
  private Long cableId;

  @Column(name = "CABLE_CODE")
  private String cableCode;

  @Column(name = "LINE_NO")
  private Long lineNo;

  @Id
  @Column(name = "LANE_ID")
  private Long laneId;

  @Column(name = "LANE_CODE")
  private String laneCode;

  @Id
  @Column(name = "SOURCE_ID")
  private Long sourceId;

  @Id
  @Column(name = "DEST_ID")
  private Long destId;

  public Long getOdfId() {
    return odfId;
  }

  public void setOdfId(Long odfId) {
    this.odfId = odfId;
  }

  public String getOdfCode() {
    return odfCode;
  }

  public void setOdfCode(String odfCode) {
    this.odfCode = odfCode;
  }

  public Long getCouplerNo() {
    return couplerNo;
  }

  public void setCouplerNo(Long couplerNo) {
    this.couplerNo = couplerNo;
  }

  public Long getCableId() {
    return cableId;
  }

  public void setCableId(Long cableId) {
    this.cableId = cableId;
  }

  public String getCableCode() {
    return cableCode;
  }

  public void setCableCode(String cableCode) {
    this.cableCode = cableCode;
  }

  public Long getLineNo() {
    return lineNo;
  }

  public void setLineNo(Long lineNo) {
    this.lineNo = lineNo;
  }

  public Long getLaneId() {
    return laneId;
  }

  public void setLaneId(Long laneId) {
    this.laneId = laneId;
  }

  public String getLaneCode() {
    return laneCode;
  }

  public void setLaneCode(String laneCode) {
    this.laneCode = laneCode;
  }

  public Long getSourceId() {
    return sourceId;
  }

  public void setSourceId(Long sourceId) {
    this.sourceId = sourceId;
  }

  public Long getDestId() {
    return destId;
  }

  public void setDestId(Long destId) {
    this.destId = destId;
  }
}
