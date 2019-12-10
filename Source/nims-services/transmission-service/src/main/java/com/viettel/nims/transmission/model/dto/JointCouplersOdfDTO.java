package com.viettel.nims.transmission.model.dto;

import java.math.BigDecimal;
import java.sql.Date;

import com.viettel.nims.transmission.commom.annotation.Element;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;

/**
 * JointCouplersOdfDTO
 * Version 1.0
 * Date: 10-01-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 10-01-2019				HungNV				Create
 */

@SheetSerializable(sheetDataIndex = 0, startRow = 4, totalCols = 9, resultCols = 9, flagRow = 3)
public class JointCouplersOdfDTO {
	@Element(type = "String", index = 0, header = "common.label.stt")
	private String stt;

	@Element(type = "String", index = 1, header = "odf.joint.header.odfCode")
	private String odfCode;

	@Element(type = "String", index = 2, header = "odf.joint.header.couplerNo")
	private String couplerNo;

	@Element(type = "String", index = 3, header = "odf.joint.header.destOdfCode")
	private String destOdfCode;

	@Element(type = "String", index = 4, header = "odf.joint.header.destCouplerNo")
	private String destCouplerNo;

	@Element(type = "String", index = 5, header = "odf.joint.header.createUser")
	private String createUser;

	@Element(type = "String", index = 6, header = "odf.joint.header.attenuation")
	private String attenuation;

	@Element(type = "String", index = 7, header = "odf.joint.header.createDate")
	private String createDate;

	@Element(type = "String", index = 8, header = "odf.joint.header.note")
	private String note;

	//===============================================
	public String getStt() {
		return stt;
	}

	public void setStt(String stt) {
		this.stt = stt;
	}

	//===============================================
	public String getOdfCode() {
		return odfCode;
	}

	public void setOdfCode(String odfCode) {
		this.odfCode = odfCode;
	}

	//===============================================
	public String getCouplerNo() {
		return couplerNo;
	}

	public void setCouplerNo(String couplerNo) {
		this.couplerNo = couplerNo;
	}

	//===============================================
	public String getDestOdfCode() {
		return destOdfCode;
	}

	public void setDestOdfCode(String destOdfCode) {
		this.destOdfCode = destOdfCode;
	}

	//===============================================
	public String getDestCouplerNo() {
		return destCouplerNo;
	}

	public void setDestCouplerNo(String destCouplerNo) {
		this.destCouplerNo = destCouplerNo;
	}

	//===============================================
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	//===============================================
	public String getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(String attenuation) {
		this.attenuation = attenuation;
	}

	//===============================================
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	//===============================================
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
