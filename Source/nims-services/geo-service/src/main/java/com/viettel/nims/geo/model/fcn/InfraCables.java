package com.viettel.nims.geo.model.fcn;

import com.viettel.nims.geo.model.form.CoordinateForm;
import com.vividsolutions.jts.geom.LineString;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "infra_cables", schema = "nims")
public class InfraCables {
  @Id
  @Column(name = "cable_id")
  private Long cableId;
  @Column(name = "cable_code")
  private String cableCode;
  @Column(name = "location_id")
  private Long locationId;
  @Column(name = "cable_type_id")
  private Long cableTypeId;
  @Column(name = "geometry", columnDefinition = "geometry(LineString,4326)")
  private LineString geometry;

  @Transient
  private String geometryAsGeoJson;

  // test demo
  @Transient
  private String status;

  @Transient
  List<CoordinateForm> lngLats;
}
