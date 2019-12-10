package com.viettel.nims.transmission.model.view;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "view_dest_odf_code")
public class ViewDestOdfCodeBO {
  @Id
  @Column(name = "DEST_ODF_ID", nullable = false)
  private BigInteger destOdfId;

  @Column(name = "DEST_ODF_CODE", length = 100, nullable = false)
  private String destOdfCode;
}
