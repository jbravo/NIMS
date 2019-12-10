package com.viettel.nims.transmission.model.view;

import javax.persistence.*;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
@Entity
@Table(name = "view_cat_department")
public class ViewCatDepartmentBO {
	private Long deptId;
	private String deptName;
	private String deptCode;
	private String pathcode;
	private Long parentId;
	private String path;
	private String pathName;

	@Id
	@Column(name = "DEPT_ID")
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Basic
	@Column(name = "DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

  @Basic
  @Column(name = "DEPT_CODE")
  public String getDeptCode() {
    return deptCode;
  }

  public void setDeptCode(String deptCode) {
    this.deptCode = deptCode;
  }

  @Basic
  @Column(name = "PARENT_ID")
  public Long getParentId() {
    return parentId;
  }

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Basic
	@Column(name = "PATH")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Basic
	@Column(name = "PATH_NAME")
	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	@Basic
	@Column(name = "PATH_CODE")
  public String getPathcode() {
    return pathcode;
  }

  public void setPathcode(String pathcode) {
    this.pathcode = pathcode;
  }
}
