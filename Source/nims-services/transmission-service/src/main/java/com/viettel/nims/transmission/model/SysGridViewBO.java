package com.viettel.nims.transmission.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SYS_GRID_VIEW")
public class SysGridViewBO implements Serializable {

  @Id
  @Column(name = "GRID_ID")
  private Long gridId;

  @Column(name = "GRID_VIEW_NAME")
  private String gridViewName;

  @Column(name = "NOTE")
  private String note;
}
