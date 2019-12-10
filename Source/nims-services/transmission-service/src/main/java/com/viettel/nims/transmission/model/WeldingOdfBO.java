package com.viettel.nims.transmission.model;

import com.viettel.nims.commons.util.PaginationDTO;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by BinhNV on 22/9/2019.
 */
@Data
public class WeldingOdfBO{
  @Id
  private Long odfId;

  @Id
  private Long couplerNo;

  @Transient
  private Long cableId;

  @Transient
  private Long lineNo;

  @Transient
  private Long destOdfId;

  @Transient
  private Long destCouplerNo;

  @Transient
  private BigDecimal attenuation;

  @Transient
  private Integer odfConnectType;

  @Transient
  private String note;

  @Transient
  private String createDate;

  @Transient
  private Date create_Date;

  @Transient
  private String createUser;

  @Transient
  private Long couplerNoFrom;

  @Transient
  private Long couplerNoTo;

  @Transient
  private Long lineNoFrom;

  @Transient
  private Long lineNoTo;

  @Transient
  private Long[] sourceCouplerNos;

  @Transient
  private Long[] destCouplerNos;

}
