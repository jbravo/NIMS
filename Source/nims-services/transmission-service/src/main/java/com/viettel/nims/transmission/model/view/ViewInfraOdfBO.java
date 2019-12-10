package com.viettel.nims.transmission.model.view;

import com.viettel.nims.commons.util.PaginationDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * ViewInfraOdfBO
 * Version 1.0
 * Date: 08-23-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 08-23-2019				HungNV				Create
 * 08-28-2019				DungPH				Add odfIndex
 */
@Entity
@Table(name = "view_infra_odfs")
public class ViewInfraOdfBO extends PaginationDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String odfCode;

	private String stationCode;

	private Long stationId;

	private Long odfId;

	private Long deptId;

	private String deptCode;

	private String deptName;

	private String deptPath;

	private String note;

	private Long odfTypeId;

	private String odfTypeCode;

	private Long vendorId;

	private String vendorName;

	private Integer rowStatus;

	private Date installationDate;

	private Long ownerId;

	private String ownerName;

  // ----------------------
  private Integer usedCoupler;

  private Integer unusedCoupler;

	//===============================================
	@Id
	@Column(name = "ODF_ID")
	public Long getOdfId() {
		return odfId;
	}

	public void setOdfId(Long odfId) {
		this.odfId = odfId;
	}

	//===============================================
	@Basic
	@Column(name = "ODF_CODE")
	public String getOdfCode() {
		return odfCode;
	}

	public void setOdfCode(String odfCode) {
		this.odfCode = odfCode;
	}

	//===============================================
	@Basic
	@Column(name = "STATION_ID")
	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	//===============================================
	@Basic
	@Column(name = "STATION_CODE")
	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	//===============================================
	@Basic
	@Column(name = "DEPT_ID")
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	//===============================================
	@Basic
	@Column(name = "DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	//===============================================
	@Basic
	@Column(name = "NOTE")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	//===============================================
	@Basic
	@Column(name = "ODF_TYPE_ID")
	public Long getOdfTypeId() {
		return odfTypeId;
	}


	public void setOdfTypeId(Long odfTypeId) {
		this.odfTypeId = odfTypeId;
	}

	//===============================================
	@Basic
	@Column(name = "ODF_TYPE_CODE")
	public String getOdfTypeCode() {
		return odfTypeCode;
	}

	public void setOdfTypeCode(String odfTypeCode) {
		this.odfTypeCode = odfTypeCode;
	}

	//===============================================
	@Basic
	@Column(name = "VENDOR_ID")
	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	//===============================================
	@Basic
	@Column(name = "VENDOR_NAME")
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	//===============================================
	@Basic
	@Column(name = "ROW_STATUS")
	public Integer getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(Integer rowStatus) {
		this.rowStatus = rowStatus;
	}

	//===============================================

	@Basic
	@Column(name = "DEPT_PATH")
	public String getDeptPath() {
		return deptPath;
	}

	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}

	//===============================================

	@Basic
	@Column(name = "INSTALLATION_DATE")
	public Date getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(Date installationDate) {
		this.installationDate = installationDate;
	}

	//===============================================
	@Basic
	@Column(name = "OWNER_ID")
	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	//===============================================
	@Basic
	@Column(name = "OWNER_NAME")
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	//===============================================
	@Basic
	@Column(name = "DEPT_CODE")
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

  //===============================================
  @Transient
  public Integer getUsedCoupler() {
    return usedCoupler;
  }

  public void setUsedCoupler(Integer usedCoupler) {
    this.usedCoupler = usedCoupler;
  }

  //===============================================
  @Transient
  public Integer getUnusedCoupler() {
    return unusedCoupler;
  }

  public void setUnusedCoupler(Integer unusedCoupler) {
    this.unusedCoupler = unusedCoupler;
  }
}

