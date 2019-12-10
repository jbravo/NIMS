package com.viettel.nims.transmission.model.view;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "view_cable_lines_in_station")
public class ViewCableLinesInStationBO {
  @Id
  @Column(name = "CABLE_CODE")
  private String cableCode;

  @Column(name = "CAPACITY")
  private Long capacity;

  @Column(name = "SOURCE_ID")
  private Long sourceOdfId;

  @Column(name = "DEST_ID")
  private Long destOdfId;
}
