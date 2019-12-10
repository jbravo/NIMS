package com.viettel.nims.transmission.model.view;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "view_odf_used")
public class ViewOdfUsedBO {
  @Id
  @Column(name = "ODF_ID", nullable = false)
  private BigInteger odfId;

  @Column(name = "COUPLER_NO", nullable = false)
  private BigDecimal couplerNo;
}
