package com.viettel.nims.transmission.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CntCouplerToCouplerBO
 * Version 1.0
 * Date: 09-03-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 09-03-2019				HungNV				Create
 */

@Entity
@Table(name = "CNT_COUPLER_TO_COUPLER")
public class CntCouplerToCouplerBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long sourceOdfId;

	private Integer sourceCouplerNo;

	private Long destOdfId;

	private Integer destCouplerNo;

	private BigDecimal attenuation;

	private Date createDate;

	private String createUser;

	private String note;


	// ----------------------
	@Id
	@Column(name = "SOURCE_ODF_ID")
	public Long getSourceOdfId() {
		return sourceOdfId;
	}

	public void setSourceOdfId(Long sourceOdfId) {
		this.sourceOdfId = sourceOdfId;
	}

	// ----------------------
	@Id
	@Column(name = "SOURCE_COUPLER_NO")
	public Integer getSourceCouplerNo() {
		return sourceCouplerNo;
	}

	public void setSourceCouplerNo(Integer sourceCouplerNo) {
		this.sourceCouplerNo = sourceCouplerNo;
	}

	// ----------------------
	@Id
	@Column(name = "DEST_ODF_ID")
	public Long getDestOdfId() {
		return destOdfId;
	}

	public void setDestOdfId(Long destOdfId) {
		this.destOdfId = destOdfId;
	}

	// ----------------------
	@Id
	@Column(name = "DEST_COUPLER_NO")
	public Integer getDestCouplerNo() {
		return destCouplerNo;
	}

	public void setDestCouplerNo(Integer destCouplerNo) {
		this.destCouplerNo = destCouplerNo;
	}

	// ----------------------
	@Basic
	@Column(name = "ATTENUATION")
	public BigDecimal getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(BigDecimal attenuation) {
		this.attenuation = attenuation;
	}

	// ----------------------
	@Basic
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	// ----------------------
	@Basic
	@Column(name = "CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	// ----------------------
	@Basic
	@Column(name = "NOTE")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
