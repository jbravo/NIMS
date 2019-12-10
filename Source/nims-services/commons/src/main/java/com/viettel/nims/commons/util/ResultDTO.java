package com.viettel.nims.commons.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

  private String resultCode;
  private String resultByText;
  private String resultMessage;
  private String additionInfo;
  private String state;
  private String feature;
}
