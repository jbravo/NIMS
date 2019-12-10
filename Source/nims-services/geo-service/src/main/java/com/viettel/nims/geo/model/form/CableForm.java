package com.viettel.nims.geo.model.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by SangNV1 on 5/25/2019.
 */
@Getter
@Setter
public class CableForm {
  Long cableId;
  String cableCode;
  List<CoordinateForm> lngLats;
  // test demo
  Long cableTypeId;
  Long deptId;
  String status;

  public CableForm() {
  }

  public CableForm(Long cableId, String cableCode, List<CoordinateForm> lngLats,Long cableTypeId, Long deptId,String status) {
    this.cableId = cableId;
    this.cableCode = cableCode;
    this.lngLats = lngLats;
    this.cableTypeId = cableTypeId;
    this.deptId = deptId;
    this.status = status;
  }
}
