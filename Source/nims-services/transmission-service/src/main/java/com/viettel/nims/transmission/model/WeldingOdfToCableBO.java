package com.viettel.nims.transmission.model;

import com.viettel.nims.transmission.utils.Constains;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "CNT_COUPLER_TO_LINE")
public class WeldingOdfToCableBO implements Serializable {
  @Id
  @Column(name = "ODF_ID", nullable = false)
  private Long odfId;

  @Id
  @Column(name = "COUPLER_NO", nullable = false)
  private Long couplerNo;

  @Column(name = "CABLE_ID", nullable = false)
  private Long cableId;

  @Column(name = "LINE_NO", nullable = false)
  private Long lineNo;

  @Column(name = "ATTENUATION")
  private BigDecimal attenuation;

  @Column(name = "NOTE", length = 500)
  private String note;

  @Column(name = "CREATE_DATE")
  private Date createDate;

  @Column(name = "CREATE_USER", length = 100)
  private String createUser;

  @Transient
  private Integer odfConnectType = 1;

  public WeldingOdfToCableBO(Long odfId, Long couplerNo, Long cableId, Long lineNo, BigDecimal attenuation, String note, Date createDate, String createUser) {
    this.odfId = odfId;
    this.couplerNo = couplerNo;
    this.cableId = cableId;
    this.lineNo = lineNo;
    this.attenuation = attenuation;
    this.note = note;
    this.createDate = createDate;
    this.createUser = createUser;
  }
}
