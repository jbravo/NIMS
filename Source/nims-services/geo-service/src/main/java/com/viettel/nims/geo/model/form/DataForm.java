package com.viettel.nims.geo.model.form;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by SangNV1 on 5/25/2019.
 */
@Getter
@Setter
public class DataForm {
  String type;
  Long id;

  public DataForm(String type, Long id) {
    this.type = type;
    this.id = id;
  }

  public DataForm() {
  }
}
