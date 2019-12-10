package com.viettel.nims.transmission.model;


import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.viettel.nims.commons.util.PaginationDTO;

/**
 * ViewInfraOdfBO
 * Version 1.0
 * Date: 08-23-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 08-23-2019				HungNV				Create
 * 08-30-2019				HungNV				Update: add vendorId
 */
@Entity
@Table(name = "INFRA_ODFS")
public class InfraOdfBO extends PaginationDTO {

	private Long odfId;

	private String odfCode;

	private Long stationId;

	private Long deptId;

	private Long odfTypeId;

	private Date installationDate;

	private Long ownerId;

	private String note;

	private Date createTime;

	private Date updateTime;

	private Integer rowStatus;

	private Long vendorId;

	// ----------------------
	private String basicInfo;

	private String stationCode;

	private String deptName;

	private String odfTypeCode;

	private String ownerCode;

	private String odfIndex;

	private String vendorCode;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ODF_ID")
	public Long getOdfId() {
		return odfId;
	}

	public void setOdfId(Long odfId) {
		this.odfId = odfId;
	}

	@Basic
	@Column(name = "ODF_CODE")
	public String getOdfCode() {
		return odfCode;
	}

	public void setOdfCode(String odfCode) {
		this.odfCode = odfCode;
	}

	@Basic
	@Column(name = "STATION_ID")
	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	@Basic
	@Column(name = "DEPT_ID")
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Basic
	@Column(name = "ODF_TYPE_ID")
	public Long getOdfTypeId() {
		return odfTypeId;
	}

	public void setOdfTypeId(Long odfTypeId) {
		this.odfTypeId = odfTypeId;
	}

	@Basic
	@Column(name = "INSTALLATION_DATE")
	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	@Basic
	@Column(name = "OWNER_ID")
	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
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
  @Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "CREATE_TIME", updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic
  @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
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

	@Basic
	@Column(name = "VENDOR_ID")
	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	//----------------------------------------
	@Transient
	public String getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(String basicInfo) {
		this.basicInfo = basicInfo;
	}

	@Transient
	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	@Transient
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Transient
	public String getOdfTypeCode() {
		return odfTypeCode;
	}

	public void setOdfTypeCode(String odfTypeCode) {
		this.odfTypeCode = odfTypeCode;
	}

	@Transient
	public String getOwnerCode() { return ownerCode; }

	public void setOwnerCode(String ownerCode) { this.ownerCode = ownerCode; }

	@Transient
	public String getOdfIndex() { return odfIndex; }

	public void setOdfIndex(String odfIndex) { this.odfIndex = odfIndex; }

	@Transient
	public String getVendorCode() { return vendorCode; }

	public void setVendorCode(String vendorCode) { this.vendorCode = vendorCode; }
}
