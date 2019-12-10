package com.viettel.nims.commons.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GponInfoBO {

  private List<SubDevice> lstSubDevice;
  private String rfState;
  private String accountGline;
  private String onuType;
  private String gponSerial;
  private String profile;
  private String deployModelIpphone;
}
