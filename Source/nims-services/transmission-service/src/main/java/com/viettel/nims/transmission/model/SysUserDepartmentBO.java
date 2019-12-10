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
@IdClass(SysUserDepartmentBO.class)
@Table(name = "SYS_USER_DEPARTMENT")
public class SysUserDepartmentBO implements Serializable {
  //  @EmbeddedId
//  @AttributeOverrides({
//      @AttributeOverride(name = "sourceCableId", column = @Column(name = "USER_ID")),
//      @AttributeOverride(name = "sourceLineNo", column = @Column(name = "DEPT_ID")),})
  @Id
  @Column(name = "USER_ID")
  private Long userId;
  @Id
  @Column(name = "DEPT_ID")
  private Long deptId;

//  @Embeddable
//  public static class PK implements Serializable {
//    private static final long serialVersionUID = 1L;
//    @Column(name = "SOURCE_CABLE_ID")
//    private Long sourceCableId;
//    @Column(name = "SOURCE_LINE_NO")
//    private Long userId
//  }
}

