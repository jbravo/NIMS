package com.viettel.nims.transmission.model.view;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by VAN-BA on 19/09/2019.
 */
@Data
@Entity
@Table(name = "view_hang_cable_lane")
public class ViewHangCableLaneBO implements Serializable {
  @Basic
  @Column(name = "PILLAR_ID")
  private Long pillarId;

  @Basic
  @Column(name = "POOL_ID")
  private Long poolId;

  @Id
  @Column(name = "CABLE_ID")
  private Long cableId;

  @Basic
  @Column(name = "CABLE_CODE")
  private String cableCode;

  @Id
  @Column(name = "PILLAR_CODE")
  private String pillarCode;

  @Id
  @Column(name = "POOL_CODE")
  private String poolCode;

  @Id
  @Column(name = "LANE_CODE")
  private String laneCode;

  @Basic
  @Column(name = "DEPT_ID")
  private Long deptId;

  @Basic
  @Column(name = "PATH_NAME")
  private String pathName;

  @Basic
  @Column(name = "LOCATION_ID")
  private Long locationId;

  @Basic
  @Column(name = "PATH_LOCAL_NAME")
  private String pathLocalName;

}
