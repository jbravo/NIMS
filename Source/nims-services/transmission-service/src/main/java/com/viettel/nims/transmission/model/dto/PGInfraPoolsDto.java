package com.viettel.nims.transmission.model.dto;

import lombok.Data;

/**
 * Created by VTN-PTPM-NV68 on 10/9/2019.
 */
@Data
public class PGInfraPoolsDto {
  private Long pool_id;
  private String pool_code;
  private Long dept_id;
  private Long location_id;
  private Long pool_type_id;
  private Integer status;
  private Long owner_id;
  private String address;
  private Double longitude;
  private Double latitude;
  private boolean isUpdate;
}
