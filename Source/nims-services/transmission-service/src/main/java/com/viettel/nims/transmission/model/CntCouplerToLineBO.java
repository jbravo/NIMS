package com.viettel.nims.transmission.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

/**
 * CntCouplerToLineBO
 * Version 1.0
 * Date: 09-03-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 09-03-2019				HungNV				Create
 */

@Entity
@Table(name = "CNT_COUPLER_TO_LINE")
public class CntCouplerToLineBO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long odfId;

	private Integer couplerNo;

	private Long cableId;

	private Integer lineNo;

	private BigDecimal attenuation;

	private Date createDate;

	private String createUser;

	private String note;


	// ----------------------
	@Id
	@Column(name = "ODF_ID")
	public Long getOdfId() {
		return odfId;
	}

	public void setOdfId(Long odfId) {
		this.odfId = odfId;
	}

	// ----------------------
	@Id
	@Basic
	@Column(name = "COUPLER_NO")
	public Integer getCouplerNo() {
		return couplerNo;
	}

	public void setCouplerNo(Integer couplerNo) {
		this.couplerNo = couplerNo;
	}

	// ----------------------
	@Id
	@Basic
	@Column(name = "CABLE_ID")
	public Long getCableId() {
		return cableId;
	}

	public void setCableId(Long cableId) {
		this.cableId = cableId;
	}

	// ----------------------
	@Id
	@Basic
	@Column(name = "LINE_NO")
	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
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
