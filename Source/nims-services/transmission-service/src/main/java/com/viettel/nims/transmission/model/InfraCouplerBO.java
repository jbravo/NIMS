package com.viettel.nims.transmission.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by BinhNV on 22/8/2019.
 */
@Data
@Entity
@Table(name = "INFRA_COUPLERS")
public class InfraCouplerBO implements Serializable {
  /**
	 *
	 */
	private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ODF_ID", nullable = false)
  private Long odfId;

  @Id
  @Column(name = "COUPLER_NO", nullable = false)
  private Long couplerNo;

  @Column(name = "STATUS")
  private Integer statuz;

  public InfraCouplerBO() {
    super();
  }

  public InfraCouplerBO(Long odfId, Long couplerNo, Integer statuz) {
    this.odfId = odfId;
    this.couplerNo = couplerNo;
    this.statuz = statuz;
  }
}
