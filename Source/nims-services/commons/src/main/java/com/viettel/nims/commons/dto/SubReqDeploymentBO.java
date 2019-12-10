package com.viettel.nims.commons.dto;

import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class SubReqDeploymentBO {

  private Long subReqDepId;
  private Long subId;
  private ArrayList<SubDeploymentDetail> lstSubDeploymentDetail;
  private Long cableBoxId;
  private String ip;
  private Long sourceType;
  private String progress;
}
