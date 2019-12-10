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
@Table(name = "SYS_GRID_VIEW_USER")
public class SysGridViewUserBO implements Serializable {

  @Id
  @Column(name = "USER_ID")
  private Long userId;
  @Id
  @Column(name = "COLUMN_ID")
  private Long columnId;

  @Column(name = "IS_SHOW")
  private Long isShow;

}
