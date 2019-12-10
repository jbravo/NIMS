package com.viettel.nims.transmission.model;

import javax.persistence.*;

/**
 * Created by VTN-PTPM-NV64 on 9/18/2019.
 */
@Entity
@Table(name = "INFRA_POLYLINES")
public class InfraPolylinesBO {
  private Long id;
  private int type;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Basic
  @Column(name = "TYPE")
  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
