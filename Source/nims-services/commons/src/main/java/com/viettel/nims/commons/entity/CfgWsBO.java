package com.viettel.nims.commons.entity;

import lombok.Data;

@Data
public class CfgWsBO {
  private String wsServiceName;
  private String wsPassword;
  private String wsDescription;
  private String wsUrl;
  private String wsUsername;
  private String wsUrlName;
}
