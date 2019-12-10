package com.viettel.nims.geo.model.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by SangNV1 on 5/25/2019.
 */
@Getter
@Setter
public class PillarForm {
  Long pillarId;
  String pillarCode;
  List<CoordinateForm> lngLats;

  public PillarForm() {
  }

  public PillarForm(Long pillarId, String pillarCode, List<CoordinateForm> lngLats) {
    this.pillarId = pillarId;
    this.pillarCode = pillarCode;
    this.lngLats = lngLats;
  }
}
