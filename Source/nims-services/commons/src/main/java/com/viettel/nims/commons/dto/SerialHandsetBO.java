package com.viettel.nims.commons.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SerialHandsetBO {

  private String serial;
  private String serialGpon;
  private String stateIdName;
  private String statusName;
}
