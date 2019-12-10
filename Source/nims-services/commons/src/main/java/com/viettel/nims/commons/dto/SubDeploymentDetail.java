package com.viettel.nims.commons.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubDeploymentDetail {

  private Long subDepDetailId;
  private String locationCode;
  private String orderLocaltion;
  private String xLocation;
  private String yLocation;
  private Long subReqDepId;
}
