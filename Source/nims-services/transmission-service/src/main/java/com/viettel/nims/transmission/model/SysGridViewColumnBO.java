package com.viettel.nims.transmission.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SYS_GRID_VIEW_COLUMNS")
public class SysGridViewColumnBO implements Serializable {

  @Id
  @Column(name = "COLUMN_ID")
  private Long columnId;

  @Column(name = "GRID_ID")
  private Long gridId;

  @Column(name = "COLUMN_NAME")
  private String columnName;

}
