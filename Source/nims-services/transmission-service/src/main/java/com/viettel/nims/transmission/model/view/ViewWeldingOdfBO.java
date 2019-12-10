package com.viettel.nims.transmission.model.view;

import com.viettel.nims.commons.util.PaginationDTO;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.sql.Timestamp;

/**
 * Created by BinhNV on 22/9/2019.
 */
@Data
@Entity
@Table(name = "view_welding_odf")
public class ViewWeldingOdfBO implements Serializable {
  @Id
  @Column(name = "ODF_ID", nullable = false)
  private Long odfId;

  @Column(name = "ODF_CODE", length = 100, nullable = false)
  private String odfCode;

  @Id
  @Column(name = "COUPLER_NO", nullable = false)
  private Long couplerNo;

  @Column(name = "CABLE_CODE", length = 100, nullable = false)
  private String cableCode;

  @Column(name = "CABLE_ID", nullable = false)
  private Long cable_Id;

  @Column(name = "LINE_NO", nullable = false)
  private Long line_No;

  @Column(name = "DEST_ODF_ID", nullable = false)
  private Long dest_OdfId;

  @Column(name = "DEST_ODF_CODE", length = 100, nullable = false)
  private String destOdfCode;

  @Column(name = "DEST_COUPLER_NO", nullable = false)
  private Long dest_Coupler;

  @Column(name = "ATTENUATION")
  private BigDecimal real_attenuation;

  @Column(name = "NOTE", length = 500)
  private String note;

  @Column(name = "CREATE_DATE")
  private Date create_Date;

  @Column(name = "CREATE_USER", length = 100)
  private String createUser;

  @Transient
  private String odfConnectType;

  @Transient
  private String createDate;

  @Transient
  private String cableId;

  @Transient
  private String destOdfId;

  @Transient
  private String lineNo;

  @Transient
  private String destCouplerNo;

  @Transient
  private String attenuation;
}
