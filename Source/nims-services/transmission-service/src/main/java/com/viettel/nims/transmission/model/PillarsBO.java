package com.viettel.nims.transmission.model;

import com.viettel.nims.commons.util.PaginationDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.ArrayList;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.vividsolutions.jts.geom.Point;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * Created by VAN-BA on 8/26/2019.
 */
@Data
@Entity
@Table(name = "INFRA_PILLARS")
public class PillarsBO extends PaginationDTO {
  @Basic
  @Column(name = "PILLAR_CODE")
  private String pillarCode;
//  public String getPillarCode(){return pillarCode;}
//  public void setPillarCode(String pillarCode) {this.pillarCode = pillarCode;}

  @Id
  @Column(name = "PILLAR_ID")
  private Long pillarId;

  @Basic
  @Column(name = "LANE_CODE")
  private String laneCode;

  @Basic
  @Column(name = "DEPT_ID")
  private Long deptId;

  @Basic
  @Column(name = "LOCATION_ID")
  private Long locationId;

  @Basic
  @Column(name = "PILLAR_TYPE_ID")
  private Long pillarTypeId;
  public void setPillarTypeId(Long pillarTypeId){ this.pillarTypeId = pillarTypeId;}

  @Basic
  @Column(name = "OWNER_ID")
  private Long ownerId;

  @Basic
  @Column(name = "CONSTRUCTION_DATE")
  private Date constructionDate;

  @Basic
  @Column(name = "STATUS")
  private Long status;

  @Basic
  @Column(name = "ADDRESS")
  private String address;

  @Basic
  @Column(name = "NOTE")
  private String note;

  @Basic
  @Column(name = "GEOMETRY", columnDefinition = "geometry(Point,4326)")
  @JsonSerialize(using = GeometrySerializer.class)
  @JsonDeserialize(contentUsing = GeometryDeserializer.class)
  private Point geometry;
  public Point getGeometry() {
    return geometry;
  }
  public void setGeometry(Point geometry) {
    this.geometry = geometry;
  }

  @Basic
  @Column(name = "CREATE_TIME")
  private Date createTime;

  @Basic
  @Column(name = "UPDATE_TIME")
  private Date updateTime;

  @Basic
  @Column(name = "ROW_STATUS")
  private int rowStatus;

  @Transient
  private String pillarTypeCode;
  public String getPillarTypeCode() {
    return pillarTypeCode;
  }

  @Transient
  private String basicInfo;
  public String getBasicInfo() {
    return basicInfo;
  }

  public void setBasicInfo(String basicInfo) {this.basicInfo = basicInfo; }


  @Transient
  private String deptName;
  public String getDeptName() {
    return deptName;
  }

  @Transient
  private String pathName;
  public String getPathName() {
    return pathName;
  }

  @Transient
  private String pathLocalName;
  public String getPathLocalName() {
    return pathLocalName;
  }

  @Transient
  private String locationName;
  public String getLocationName() {
    return locationName;
  }

  @Transient
  private String ownerName;
  public String getOwnerName() {
    return ownerName;
  }

  @Transient
  private Long pillarStatusCable;
  public Long getPillarStatusCable() {
    return pillarStatusCable;
  }

  @Transient
  private Double longitude;
  public Double getLongitude() {
    return longitude;
  }


  @Transient
  private Double latitude;
  public Double getLatitude() { return latitude; }

  @Transient
  private String laString;
  public String getLaString() { return laString; }

  @Transient
  private String longString;
  public String getLongString() { return longString; }

  @Transient
  private ArrayList<Object> filter;
  public ArrayList<Object> getFilter() { return filter; }


  @Transient
  private String pillarIndex;
  public String getPillarIndex() { return pillarIndex; }

  @Transient
  private String cableCode;
  public String getCableCode() { return cableCode; }

  @Transient
  private String pillarCodeTemp;
  public String getPillarCodeTemp() { return pillarCodeTemp; }

  @Transient
  private String poolCode;
  public String getPoolCode() { return poolCode; }

  @Transient
  private Long pillarTypeIdTemp;
  public Long getPillarTypeIdTemp() { return pillarTypeIdTemp; }

  @Transient
  private Long ownerIdTemp;
  public Long getOwnerIdTemp() { return ownerIdTemp; }

  @Transient
  private Long poolId;
  public Long getPoolId() { return poolId; }

  @Transient
  private String laneCodeTemp;
  public String getLaneCodeTemp() { return laneCodeTemp; }

}
