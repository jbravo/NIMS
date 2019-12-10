package com.viettel.nims.transmission.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CAT_OPTICAL_CABLE_TYPE")
public class CatOpticalCableTypeBO {
	@Id
	@Column(name = "CABLE_TYPE_ID")
	private Long cableTypeId;
	@Column(name = "CABLE_TYPE_CODE")
	private String cableTypeCode;
	@Column(name = "TYPE")
	private Integer type;
	@Column(name = "CAPACITY")
	private Integer capacity;
	@Column(name = "DIAMETER")
	private Float diameter;
	@Column(name = "NOTE")
	private String note;
	@Column(name = "CREATE_TIME")
	private Date createTime;
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	@Column(name = "ROW_STATUS")
	private Integer rowStatus;

	public Long getCableTypeId() {
		return cableTypeId;
	}

	public void setCableTypeId(Long cableTypeId) {
		this.cableTypeId = cableTypeId;
	}

	public String getCableTypeCode() {
		return cableTypeCode;
	}

	public void setCableTypeCode(String cableTypeCode) {
		this.cableTypeCode = cableTypeCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Float getDiameter() {
		return diameter;
	}

	public void setDiameter(Float diameter) {
		this.diameter = diameter;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public int getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(Integer rowStatus) {
		this.rowStatus = rowStatus;
	}
	
}
