package com.viettel.nims.transmission.model.dto;

import lombok.Data;

/**
 * Created by VTN-PTPM-NV68 on 10/9/2019.
 */


@Data
public class PGInfraCablesDto {

  private Long cable_id;
  private String cable_code;
  private Long dept_id;
  private Long location_id;
  private Long cable_type_id;
  private Long status;
  private Long owner_id;
  private Double longitude;
  private Double latitude;
  private boolean isUpdate;
}
