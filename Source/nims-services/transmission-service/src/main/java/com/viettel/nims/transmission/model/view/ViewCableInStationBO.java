package com.viettel.nims.transmission.model.view;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Created by HieuDT on 8/26/2019.
 */
@Entity
@Table(name = "view_infra_cable_in_station")
public class ViewCableInStationBO {
	private Long cableId;
	private String cableCode;
	private Long sourceId;
	private String sourceCode;
	private Long destId;
	private String destCode;
	private Long stationId;
	private String stationCode;
	private Long deptId;
	private String deptName;
	private String pathName;
	private Long cableTypeId;
	private String cableTypeCode;
	private Integer status;
	private BigDecimal length;
	private Date installationDate;
	private String note;
	private Long rowStatus;
	private Date createTime;
	private int cableIndex;
	private String statusName;

	@Id
	@Column(name = "CABLE_ID")
	public Long getCableId() {
		return cableId;
	}

	public void setCableId(Long cableId) {
		this.cableId = cableId;
	}

	@Basic
	@Column(name = "CABLE_CODE")
	public String getCableCode() {
		return cableCode;
	}

	public void setCableCode(String cableCode) {
		this.cableCode = cableCode;
	}

	@Basic
	@Column(name = "SOURCE_ID")
	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	@Basic
	@Column(name = "SOURCE_CODE")
	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Basic
	@Column(name = "DEST_CODE")
	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	@Basic
	@Column(name = "DEST_ID")
	public Long getDestId() {
		return destId;
	}

	public void setDestId(Long destId) {
		this.destId = destId;
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
	@Column(name = "STATION_CODE")
	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
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
	@Column(name = "DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Basic
	@Column(name = "CABLE_TYPE_ID")
	public Long getCableTypeId() {
		return cableTypeId;
	}

	public void setCableTypeId(Long cableTypeId) {
		this.cableTypeId = cableTypeId;
	}

	@Basic
	@Column(name = "CABLE_TYPE_CODE")
	public String getCableTypeCode() {
		return cableTypeCode;
	}

	public void setCableTypeCode(String cableTypeCode) {
		this.cableTypeCode = cableTypeCode;
	}

	@Basic
	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Basic
	@Column(name = "LENGTH")
	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
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
	@Column(name = "NOTE")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Basic
	@Column(name = "ROW_STATUS")
	public Long getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(Long rowStatus) {
		this.rowStatus = rowStatus;
	}

	@Basic
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic
	@Column(name = "CABLE_INDEX")
	public int getCableIndex() {
		return cableIndex;
	}

	public void setCableIndex(int cableIndex) {
		this.cableIndex = cableIndex;
	}

	@Transient
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Basic
	@Column(name = "PATH_NAME")
	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

}
