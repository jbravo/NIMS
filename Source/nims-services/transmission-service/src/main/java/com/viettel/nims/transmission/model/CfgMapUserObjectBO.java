package com.viettel.nims.transmission.model;

import javax.persistence.*;

/**
 * Created by VTN-PTPM-NV64 on 9/13/2019.
 */
@Entity
@Table(name = "CFG_MAP_USER_OBJECT")
public class CfgMapUserObjectBO {
  private CfgMapUserObjectPK id;
  private String property;
  private Integer isShow;

  @EmbeddedId
  public CfgMapUserObjectPK getId() {
    return id;
  }

  public void setId(CfgMapUserObjectPK id) {
    this.id = id;
  }

  @Transient
  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  @Basic
  @Column(name = "IS_SHOW")
  public Integer getIsShow() {
    return isShow;
  }

  public void setIsShow(Integer isShow) {
    this.isShow = isShow;
  }

}

