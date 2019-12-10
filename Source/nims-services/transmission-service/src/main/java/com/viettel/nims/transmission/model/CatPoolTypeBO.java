package com.viettel.nims.transmission.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ThieuNV on 08/26/2019.
 */
@Entity
@Table(name = "CAT_POOL_TYPE")
public class CatPoolTypeBO {
	private Long poolTypeId;
	private String poolTypeCode;
	private String note;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer rowStatus;

	@Id
	@Basic
	@Column(name = "POOL_TYPE_ID")
	public Long getPoolTypeId() {
		return poolTypeId;
	}

	public void setPoolTypeId(Long poolTypeId) {
		this.poolTypeId = poolTypeId;
	}

	@Basic
	@Column(name = "POOL_TYPE_CODE")
	public String getPoolTypeCode() {
		return poolTypeCode;
	}

	public void setPoolTypeCode(String poolTypeCode) {
		this.poolTypeCode = poolTypeCode;
	}

	@Basic
	@Column(name = "NOTE")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

}
