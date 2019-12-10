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
public class ViewOdfBO {
  @Id
  @Column(name = "ODF_ID", nullable = false)
  private BigInteger odfId;

  @Column(name = "ODF_CODE", nullable = false)
  private String odfCode;

  @Column(name = "STATION_ID", nullable = false)
  private Long stationId;

  @Column(name = "COUPLER_SIZE", nullable = false)
  private Integer couplerSize;
}
