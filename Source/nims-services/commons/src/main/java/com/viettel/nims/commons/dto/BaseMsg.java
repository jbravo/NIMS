package com.viettel.nims.commons.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class BaseMsg {

  private String responseCode;
  private String description;
  private List outputLst;
  private String abc;
  private String isdn;
}
