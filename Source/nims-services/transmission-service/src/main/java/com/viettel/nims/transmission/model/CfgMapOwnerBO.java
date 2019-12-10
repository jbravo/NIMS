package com.viettel.nims.transmission.model;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "CFG_MAP_OWNER")
public class CfgMapOwnerBO {
  private Long ownerId;
  private String opticalCableColor;
  private String stationIcon;
  private String pillarIcon;
  private String pillarSleeveIcon;
  private String poolIcon;
  private String poolSleeveIcon;

  @Id
  @Column(name = "OWNER_ID")
  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }

  @Basic
  @Column(name = "OPTICAL_CABLE_COLOR")
  public String getOpticalCableColor() {
    return opticalCableColor;
  }

  public void setOpticalCableColor(String opticalCableColor) {
    this.opticalCableColor = opticalCableColor;
  }


  @Basic
  @Column(name = "STATION_ICON")
  public String getStationIcon() {
    return stationIcon;
  }

  public void setStationIcon(String stationIcon) {
    this.stationIcon = stationIcon;
  }


  @Basic
  @Column(name = "PILLAR_ICON")
  public String getPillarIcon() {
    return pillarIcon;
  }

  public void setPillarIcon(String pillarIcon) {
    this.pillarIcon = pillarIcon;
  }

  @Basic
  @Column(name = "PILLAR_SLEEVE_ICON")
  public String getPillarSleeveIcon() {
    return pillarSleeveIcon;
  }

  public void setPillarSleeveIcon(String pillarSleeveIcon) {
    this.pillarSleeveIcon = pillarSleeveIcon;
  }


  @Basic
  @Column(name = "POOL_ICON")
  public String getPoolIcon() {
    return poolIcon;
  }

  public void setPoolIcon(String poolIcon) {
    this.poolIcon = poolIcon;
  }

  @Basic
  @Column(name = "POOL_SLEEVE_ICON")
  public String getPoolSleeveIcon() {
    return poolSleeveIcon;
  }

  public void setPoolSleeveIcon(String poolSleeveIcon) {
    this.poolSleeveIcon = poolSleeveIcon;
  }


}
