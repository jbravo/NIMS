package com.viettel.nims.geo.model.fcn;

import com.vividsolutions.jts.geom.LineString;
import lombok.*;

import javax.persistence.*;

/**
 * Created by VTN-PTPM-NV55 on 9/26/2019.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "infra_pillars", schema = "nims")

public class InfraPillar {
  @Id
  @Column(name = "pillar_id")
  private Long pillarId;
  @Column(name = "pillar_code")
  private String pillarCode;
  @Column(name = "location_id")
  private Long locationId;
  @Column(name = "geometry", columnDefinition = "geometry(LineString,4326)")
  private LineString geometry;

  @Transient
  private String geometryAsGeoJson;
}
