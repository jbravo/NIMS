package com.viettel.nims.transmission.model.view;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "view_cable_code")
public class ViewCableCodeBO {
  @Id
  @Column(name = "CABLE_ID", nullable = false)
  private BigInteger cableId;

  @Column(name = "CABLE_CODE", length = 100, nullable = false)
  private String cableCode;
}
