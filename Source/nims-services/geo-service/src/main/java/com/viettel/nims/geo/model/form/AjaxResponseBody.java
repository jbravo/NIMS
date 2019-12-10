package com.viettel.nims.geo.model.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AjaxResponseBody {

  String msg;
  List<String> stationData;
  List<String> cableData;
}
