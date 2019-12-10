package com.viettel.nims.transmission.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CNT_DEPARTMENT_LOCATION")
public class CntDepartmentLocationBO {

  @Id
  @Column(name = "ID")
  private Long id;

  @Basic
  @Column(name = "DEPT_ID")
  private Long deptId;

  @Basic
  @Column(name = "LOCATION_ID")
  private Long locationId;

  @Basic
  @Column(name = "UPDATE_TIME")
  private Timestamp updateTime;
}
