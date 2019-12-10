package com.viettel.nims.transmission.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.viettel.nims.commons.util.PaginationDTO;

/**
 * Created by HieuDT on 8/26/2019.
 */
@Entity
@Table(name = "INFRA_CABLES_IN_STATION")
public class CableInStationBO extends PaginationDTO {
	private Long cableId;
	private String cableCode;
	private Long sourceId;
	private Long destId;
	private Long stationId;
	private Long deptId;
	private Long cableTypeId;
	private Integer status;
	private BigDecimal length;
	private Date installationDate;
	private String note;
	private Date createTime;
	private Date updateTime;
	private Long rowStatus;
	private String basicInfo;
	private String deptName;
	private String sourceCode;
	private String destCode;
	private String cableTypeCode;
	private String stationCode;
	private Integer cableIndex;
	private String lengthStr;

	private String cableCodeTable;
	private Long sourceIdTable;
	private Long destIdTable;
	private Long stationIdTable;
	private Long deptIdTable;
	private Long cableTypeIdTable;
	private String deptNameTable;
	private String sourceCodeTable;
	private String destCodeTable;
	private String cableTypeCodeTable;
	private String stationCodeTable;

	@Id
	@Column(name = "CABLE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Column(name = "DEPT_ID")
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
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
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Basic
	@Column(name = "ROW_STATUS")
	public Long getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(Long rowStatus) {
		this.rowStatus = rowStatus;
	}

	@Transient
	public String getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(String basicInfo) {
		this.basicInfo = basicInfo;
	}

	@Transient
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Transient
	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Transient
	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	@Transient
	public String getCableTypeCode() {
		return cableTypeCode;
	}

	public void setCableTypeCode(String cableTypeCode) {
		this.cableTypeCode = cableTypeCode;
	}

	@Transient
	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	@Transient
	public Integer getCableIndex() {
		return cableIndex;
	}

	public void setCableIndex(Integer cableIndex) {
		this.cableIndex = cableIndex;
	}

	@Transient
	public String getLengthStr() {
		return lengthStr;
	}

	public void setLengthStr(String lengthStr) {
		this.lengthStr = lengthStr;
	}

	@Transient
	public String getCableCodeTable() {
		return cableCodeTable;
	}

	public void setCableCodeTable(String cableCodeTable) {
		this.cableCodeTable = cableCodeTable;
	}

	@Transient
	public Long getSourceIdTable() {
		return sourceIdTable;
	}

	public void setSourceIdTable(Long sourceIdTable) {
		this.sourceIdTable = sourceIdTable;
	}

	@Transient
	public Long getDestIdTable() {
		return destIdTable;
	}

	public void setDestIdTable(Long destIdTable) {
		this.destIdTable = destIdTable;
	}

	@Transient
	public Long getStationIdTable() {
		return stationIdTable;
	}

	public void setStationIdTable(Long stationIdTable) {
		this.stationIdTable = stationIdTable;
	}

	@Transient
	public Long getDeptIdTable() {
		return deptIdTable;
	}

	public void setDeptIdTable(Long deptIdTable) {
		this.deptIdTable = deptIdTable;
	}

	@Transient
	public Long getCableTypeIdTable() {
		return cableTypeIdTable;
	}

	public void setCableTypeIdTable(Long cableTypeIdTable) {
		this.cableTypeIdTable = cableTypeIdTable;
	}

	@Transient
	public String getDeptNameTable() {
		return deptNameTable;
	}

	public void setDeptNameTable(String deptNameTable) {
		this.deptNameTable = deptNameTable;
	}

	@Transient
	public String getSourceCodeTable() {
		return sourceCodeTable;
	}

	public void setSourceCodeTable(String sourceCodeTable) {
		this.sourceCodeTable = sourceCodeTable;
	}

	@Transient
	public String getDestCodeTable() {
		return destCodeTable;
	}

	public void setDestCodeTable(String destCodeTable) {
		this.destCodeTable = destCodeTable;
	}

	@Transient
	public String getCableTypeCodeTable() {
		return cableTypeCodeTable;
	}

	public void setCableTypeCodeTable(String cableTypeCodeTable) {
		this.cableTypeCodeTable = cableTypeCodeTable;
	}

	@Transient
	public String getStationCodeTable() {
		return stationCodeTable;
	}

	public void setStationCodeTable(String stationCodeTable) {
		this.stationCodeTable = stationCodeTable;
	}
	

}
