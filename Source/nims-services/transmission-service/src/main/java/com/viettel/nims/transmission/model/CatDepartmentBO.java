package com.viettel.nims.transmission.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatDepartmentBO implements Serializable {

  private Long deptId;
  private String deptCode;
  private String deptName;
  private Long parentId;
  private Long deptType;

}
