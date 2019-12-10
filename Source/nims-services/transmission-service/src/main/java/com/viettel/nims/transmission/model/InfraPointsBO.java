package com.viettel.nims.transmission.model;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
@Entity
@Table(name = "INFRA_POINTS")
public class InfraPointsBO {
  private Long id;
  private BigInteger type;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @SequenceGenerator(name = "points_generator",sequenceName = "INFRA_POINTS_SEQ",initialValue = 1)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Basic
  @Column(name = "TYPE")
  public BigInteger getType() {
    return type;
  }

  public void setType(BigInteger type) {
    this.type = type;
  }

}
