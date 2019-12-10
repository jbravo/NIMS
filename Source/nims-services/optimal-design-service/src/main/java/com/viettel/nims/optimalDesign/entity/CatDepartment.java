/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author Admin
 */
@Entity
@Table(name = "CAT_DEPARTMENT")
public class CatDepartment implements Serializable {

  //    private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @Column(name = "DEPT_ID")
  @SequenceGenerator(name = "catDepartmentsGenerator", sequenceName = "CAT_DEPARTMENT_SEQ")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catDepartmentsGenerator")
  private Long deptId;
  @Basic(optional = false)
  @Column(name = "DEPT_CODE")

  private String deptCode;

  @Basic(optional = false)
  @Column(name = "DEPT_NAME")
  private String deptName;

  @Column(name = "PARENT_ID")
  private Long parentId;
  @Column(name = "CREATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)

  private Date createTime;
  @Column(name = "UPDATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)

  private Date updateTime;
  @Column(name = "ROW_STATUS")

  private Integer rowStatus;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "deptId", fetch = FetchType.LAZY)
  private List<SysUsers> sysUsersList;
  @JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private CatTenants tenantId;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "deptId", fetch = FetchType.LAZY)
  private List<CntDepartmentLocation> cntDepartmentLocationList;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "catDepartment", fetch = FetchType.LAZY)
  private List<Suggestion> suggestions;

  public CatDepartment() {
  }

  public CatDepartment(Long deptId) {
    this.deptId = deptId;
  }

  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  public String getDeptCode() {
    return deptCode;
  }

  public void setDeptCode(String deptCode) {
    this.deptCode = deptCode;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getRowStatus() {
    return rowStatus;
  }

  public void setRowStatus(Integer rowStatus) {
    this.rowStatus = rowStatus;
  }


  public List<SysUsers> getSysUsersList() {
    return sysUsersList;
  }

  public void setSysUsersList(List<SysUsers> sysUsersList) {
    this.sysUsersList = sysUsersList;
  }

  public CatTenants getTenantId() {
    return tenantId;
  }

  public void setTenantId(CatTenants tenantId) {
    this.tenantId = tenantId;
  }

  public List<CntDepartmentLocation> getCntDepartmentLocationList() {
    return cntDepartmentLocationList;
  }

  public void setCntDepartmentLocationList(List<CntDepartmentLocation> cntDepartmentLocationList) {
    this.cntDepartmentLocationList = cntDepartmentLocationList;
  }

  public List<Suggestion> getSuggestions() {
    return suggestions;
  }

  public void setSuggestions(List<Suggestion> suggestions) {
    this.suggestions = suggestions;
  }
}
