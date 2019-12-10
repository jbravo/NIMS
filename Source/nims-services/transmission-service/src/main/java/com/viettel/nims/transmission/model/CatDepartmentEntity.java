package com.viettel.nims.transmission.model;

import com.viettel.nims.commons.util.PaginationDTO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by VTN-PTPM-NV64 on 8/8/2019.
 */
@Entity
@Table(name = "CAT_DEPARTMENT")
public class CatDepartmentEntity extends PaginationDTO {
	private Long deptId;
	private Long tenantId;
	private String deptCode;
	private String deptName;
	private Long parentId;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer rowStatus;
	
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
	@Column(name = "TENANT_ID")
	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
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
	@Column(name = "DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	@Column(name = "CREATE_TIME")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Basic
	@Column(name = "UPDATE_TIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Basic
	@Column(name = "ROW_STATUS")
	public Integer getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(Integer rowStatus) {
		this.rowStatus = rowStatus;
	}

	@Transient
	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

}
