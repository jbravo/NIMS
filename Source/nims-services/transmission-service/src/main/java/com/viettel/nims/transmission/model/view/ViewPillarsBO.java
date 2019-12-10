package com.viettel.nims.transmission.model.view;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.sql.Date;
import javax.persistence.*;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.vividsolutions.jts.geom.Point;

/**
 * Created by VAN-BA on 8/26/2019.
 */
@Data
@Entity
@Table(name = "view_infra_pillars")
public class ViewPillarsBO {

  @Basic
  @Column(name = "PILLAR_TYPE_ID")
  private Long pillarTypeId;

  @Basic
  @Column(name = "PILLAR_CODE")
  private String pillarCode;

  @Basic
  @Column(name = "PILLAR_TYPE_CODE")
  private String pillarTypeCode;

  @Basic
  @Column(name = "LANE_CODE")
  private String laneCode;

  @Basic
  @Column(name = "CONSTRUCTION_DATE")
  private Date constructionDate;

  @Basic
  @Column(name = "ADDRESS")
  private String address;

  @Basic
  @Column(name = "STATUS")
  private Long status;

  @Basic
  @Column(name = "DEPT_ID")
  private Long deptId;

  @Basic
  @Column(name = "DEPT_CODE")
  private String deptCode;

  @Basic
  @Column(name = "DEPT_NAME")
  private String deptName;

  @Basic
  @Column(name = "LOCATION_ID")
  private Long locationId;

//  @Basic
//  @Column(name = "GEOMETRY", columnDefinition = "geometry(Point,4326)")
//  @JsonSerialize(using = GeometrySerializer.class)
//  @JsonDeserialize(contentUsing = GeometryDeserializer.class)
//  private Point geometry;
//  public Point getGeometry() {
//    return geometry;
//  }
//  public void setGeometry(Point geometry) {
//    this.geometry = geometry;
//  }

  @Basic
  @Column(name = "OWNER_ID")
  private Long ownerId;

  @Basic
  @Column(name = "ROW_STATUS")
  private int rowStatus;

  @Basic
  @Column(name = "LOCATION_CODE")
  private String locationCode;

  @Basic
  @Column(name = "LOCATION_NAME")
  private String locationName;

  @Basic
  @Column(name = "OWNER_NAME")
  private String ownerName;

  @Id
  @Column(name = "PILLAR_ID")
  private Long pillarId;

  @Basic
  @Column(name = "PILLAR_STATUS_CABLE")
  private Long pillarStatusCable;

  @Basic
  @Column(name = "NOTE")
  private String note;

  @Basic
  @Column(name = "PILLAR_INDEX")
  private int pillarIndex;

  @Basic
  @Column(name = "PATH_LOCAL_NAME")
  private String pathLocalName;

  @Basic
  @Column(name = "PATH_NAME")
  private String pathName;

  @Basic
  @Column(name = "LONGITUDE")
  private Double longitude;

  @Basic
  @Column(name = "LATITUDE")
  private Double latitude;

//  @Transient
//  private Double longitude;
//  public Double getLongitude() {
//    return longitude;
//  }
//  public void setLongitude(Double longitude) {
//    this.longitude = longitude;
//  }

//  @Transient
//  private Double latitude;
//  public Double getLatitude() {
//    return latitude;
//  }
//  public void setLatitude(Double latitude) {
//    this.latitude = latitude;
//  }

  @Transient
  private String statusName;
  public String getStatusName() { return statusName; }

}
