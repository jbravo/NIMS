package com.viettel.nims.transmission.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "INFRA_CABLE_LINE")
public class InfraCableLineBO implements Serializable {
  @Id
  @Column(name = "CABLE_ID", nullable = false)
  private Long cableId;

  @Id
  @Column(name = "LINE_NO", nullable = false)
  private Long lineNo;

  @Column(name = "LINE_TYPE")
  private Long lineType;

  @Column(name = "STATUS")
  private Integer statuz;

  @Column(name = "PURPOSE")
  private String purpose;

  @Column(name = "ATTENUATION_TOTAL")
  private Long attenuationTotal;

  @Column(name = "ATTENUATION_AVG_KM")
  private Long attenuationAvgKm;

  @Column(name = "ATTENUATION_ABNORMAL")
  private Long attenuationAbnormal;

  @Column(name = "COMMENT")
  private String comment;

  @Column(name = "MEASURER_NAME")
  private String measurerName;

  @Column(name = "STAFF_CODE")
  private String staffCode;

  @Column(name = "MEASURE_DATE")
  private Date measureDate;

  @Column(name = "REASON_BAD_LINE")
  private String reasonBadLine;

  @Column(name = "NOTE")
  private String note;

  public InfraCableLineBO(Long cableId, Long lineNo, Integer statuz) {
    this.cableId = cableId;
    this.lineNo = lineNo;
    this.statuz = statuz;
  }

  public InfraCableLineBO() {
	super();
  }

  
}
