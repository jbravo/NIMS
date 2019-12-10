package com.viettel.nims.commons.client.form;

import lombok.Data;

@Data
public class DepartmentForm {

  private Long departmentId;
  private String deptCode;
  private String deptName;
  private int deptLevel;
  private String foreignCode;
}

