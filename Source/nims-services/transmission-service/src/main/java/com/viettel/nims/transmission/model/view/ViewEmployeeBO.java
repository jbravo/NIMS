package com.viettel.nims.transmission.model.view;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "view_employee")
public class ViewEmployeeBO {
  @Id
  @Column(name = "EMPLOYEE", length = 100, nullable = false)
  private String employee;
}
