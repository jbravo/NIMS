package com.viettel.nims.transmission.model.view;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "view_odf_code")
public class ViewOdfCodeBO {
  @Id
  @Column(name = "ODF_ID", nullable = false)
  private BigInteger odfId;

  @Column(name = "ODF_CODE", length = 100, nullable = false)
  private String odfCode;
}
