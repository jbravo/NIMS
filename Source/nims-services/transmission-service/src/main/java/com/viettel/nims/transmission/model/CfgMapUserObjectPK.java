package com.viettel.nims.transmission.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by VTN-PTPM-NV64 on 9/16/2019.
 */
@Embeddable
public class CfgMapUserObjectPK implements Serializable {
  private String objectCode;
  private Long  cfgMapUserId;

  @Column(name = "OBJECT_CODE")
  public String getObjectCode() {
    return objectCode;
  }

  public void setObjectCode(String objectCode) {
    this.objectCode = objectCode;
  }

  @Column(name = "CFG_MAP_USER_ID")
  public Long getCfgMapUserId() {
    return cfgMapUserId;
  }

  public void setCfgMapUserId(Long cfgMapUserId) {
    this.cfgMapUserId = cfgMapUserId;
  }
}
