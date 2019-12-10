package com.viettel.nims.transmission.model.view;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "view_coupler_used")
public class ViewCouplerUsedBO {
  @Id
  @Column(name = "ODF_ID", nullable = false)
  private Long odfId;

  @Column(name = "COUPLER_NO", nullable = false)
  private Long couplerNo;
}
