package com.viettel.nims.transmission.model.view;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
/**
 * Created by VAN-BA on 25/09/2019.
 */
@Data
@Entity
@Table(name = "view_infra_cable_lane")
public class ViewInfraCableLaneBO {

  @Id
  @Column(name = "LANE_CODE")
  private String laneCode;

  @Basic
  @Column(name = "DEPT_NAME")
  private String deptName;

  @Basic
  @Column(name = "LOCATION_NAME")
  private String locationName;

  @Basic
  @Column(name = "LOCATION_ID")
  private Long locationId;

  @Basic
  @Column(name = "DEPT_ID")
  private Long deptId;

}

