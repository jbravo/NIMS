package com.viettel.nims.transmission.model.dto;

import com.vividsolutions.jts.geom.Point;
import lombok.Data;

/**
 * Created by VTN-PTPM-NV68 on 10/3/2019.
 */
@Data
public class PGInfraStationDto {

  private Long station_id;
  private String station_code;
  private Long dept_id;
  private Long location_id;
  private Long owner_id;
  private Long status;
  private Double longitude;
  private Double latitude;
  private String address;
  private boolean isUpdate;
}
