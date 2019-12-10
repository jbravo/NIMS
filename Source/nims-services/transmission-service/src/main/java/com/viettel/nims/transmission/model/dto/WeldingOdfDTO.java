package com.viettel.nims.transmission.model.dto;

import java.math.BigDecimal;
import java.sql.Date;

import com.viettel.nims.transmission.commom.annotation.Element;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;

/**
 * WeldingOdfDTO
 * Version 1.0
 * Date: 10-01-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 10-01-2019				HungNV				Create
 */

@SheetSerializable(sheetDataIndex = 0, startRow = 4, totalCols = 9, resultCols = 9, flagRow = 3)
public class WeldingOdfDTO {

	@Element(type = "String", index = 0, header = "common.label.stt")
	private String stt;

	@Element(type = "String", index = 1, header = "odf.welding.header.odfCode")
	private String odfCode;

	@Element(type = "String", index = 2, header = "odf.welding.header.couplerNo")
	private String couplerNo;

	@Element(type = "String", index = 3, header = "odf.welding.header.cableCode")
	private String cableCode;

	@Element(type = "String", index = 4, header = "odf.welding.header.lineNo")
	private String lineNo;

	@Element(type = "String", index = 5, header = "odf.welding.header.createUser")
	private String createUser;

	@Element(type = "String", index = 6, header = "odf.welding.header.attenuation")
	private String attenuation;

	@Element(type = "String", index = 7, header = "odf.welding.header.createDate")
	private String createDate;

	@Element(type = "String", index = 8, header = "odf.welding.header.note")
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
	public String getCableCode() {
		return cableCode;
	}

	public void setCableCode(String cableCode) {
		this.cableCode = cableCode;
	}

	//===============================================
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
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

	public void setCreateDate(String create_Date) {
		this.createDate = create_Date;
	}

	//===============================================
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	//===============================================
	public String getCouplerNo() {
		return couplerNo;
	}

	public void setCouplerNo(String couplerNo) {
		this.couplerNo = couplerNo;
	}

	//===============================================

	
}
