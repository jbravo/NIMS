package com.viettel.nims.geo.model.form;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by SangNV1 on 5/25/2019.
 */
@Getter
@Setter
public class StationForm {
  Long stationId;
  String stationCode;
  String address;
  Long status;
  CoordinateForm lngLat;
  Long deptId;
  Long locationId;
  String action;

}
