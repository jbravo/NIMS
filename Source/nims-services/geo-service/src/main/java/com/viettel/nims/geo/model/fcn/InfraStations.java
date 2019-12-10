package com.viettel.nims.geo.model.fcn;

import com.vividsolutions.jts.geom.Point;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "infra_stations", schema = "nims")
public class InfraStations {
  @Id
  @Column(name = "station_id")
  private Long stationId;
  @Column(name = "station_code")
  private String stationCode;
  @Column(name = "location_id")
  private Long locationId;
  @Column(name = "geometry", columnDefinition = "geometry(Point,4326)")
  private Point geometry;

  @Transient
  private String geometryAsGeoJson;
}
