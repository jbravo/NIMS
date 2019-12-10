package com.viettel.nims.transmission.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by VTN-PTPM-NV64 on 9/13/2019.
 */
@Entity
@Table(name = "CFG_MAP_USER")
public class CfgMapUserBO {
  private Long cfgMapUserId;
  private Long userId;
  private Long zoom;
  private BigDecimal lng;
  private BigDecimal lat;
  private Integer mapType;

  private List<CfgMapUserObjectBO> mapConfig;

  @Id
  @Column(name = "CFG_MAP_USER_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long getCfgMapUserId() {
    return cfgMapUserId;
  }

  public void setCfgMapUserId(Long cfgMapUserId) {
    this.cfgMapUserId = cfgMapUserId;
  }

  @Basic
  @Column(name = "USER_ID")
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Basic
  @Column(name = "ZOOM")
  public Long getZoom() {
    return zoom;
  }

  public void setZoom(Long zoom) {
    this.zoom = zoom;
  }

  @Basic
  @Column(name = "LNG")
  public BigDecimal getLng() {
    return lng;
  }

  public void setLng(BigDecimal lng) {
    this.lng = lng;
  }

  @Basic
  @Column(name = "LAT")
  public BigDecimal getLat() {
    return lat;
  }

  public void setLat(BigDecimal lat) {
    this.lat = lat;
  }

  @Basic
  @Column(name = "MAP_TYPE")
  public Integer getMapType() {
    return mapType;
  }

  public void setMapType(Integer mapType) {
    this.mapType = mapType;
  }

  @Transient
  public List<CfgMapUserObjectBO> getMapConfig() {
    return mapConfig;
  }

  public void setMapConfig(List<CfgMapUserObjectBO> mapConfig) {
    this.mapConfig = mapConfig;
  }
}
