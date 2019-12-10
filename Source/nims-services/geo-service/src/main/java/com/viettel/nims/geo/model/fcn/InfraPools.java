package com.viettel.nims.geo.model.fcn;

import com.vividsolutions.jts.geom.Point;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "infra_pools", schema = "nims")
public class InfraPools {

  @Id
  @Column(name = "pool_id")
  private Long    poolId;

  @Column(name = "pool_code")
  private String  poolCode;

  @Column(name = "dept_id")
  private Long    deptId;

  @Column(name = "location_id")
  private Long    locationId;

  @Column(name = "pool_type_id")
  private Long    poolTypeId;

  @Column(name = "status")
  private Integer status;

  @Column(name = "owner_id")
  private Integer ownerId;

  @Column(name = "address")
  private String  address;

  @Column(name = "geometry", columnDefinition = "geometry(Point,4326)")
  private Point   geometry;
}
