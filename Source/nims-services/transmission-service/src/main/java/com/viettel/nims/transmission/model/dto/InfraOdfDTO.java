package com.viettel.nims.transmission.model.dto;

import com.viettel.nims.transmission.commom.annotation.Element;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;

@SheetSerializable(sheetDataIndex = 0, startRow = 4, totalCols = 10, resultCols = 10,flagRow = 3)
public class InfraOdfDTO {
	@Element(type = "String", index = 0, header = "common.label.stt")
	private String stt;

	@Element(type = "String", index = 1, header = "odf.header.stationCode")
	private String stationCode;

	@Element(type = "number", index = 2, isGenerateValue = true, header ="odf.header.odfIndex")
	private String odfIndex;

	@Element(type = "String", index = 3, isGenerateValue = true, header ="odf.header.odfCode")
	private String odfCode;

	@Element(type = "String", index = 4, header = "odf.header.deptCode")
	private String deptCode;

	@Element(type = "String", index = 5, header = "odf.header.odfTypeCode")
	private String odfTypeCode;

	@Element(type = "String", index = 6, header = "odf.header.ownerName")
	private String ownerName;

	@Element(type = "String", index = 7, header = "odf.header.vendorName")
	private String vendorName;

	@Element(type = "Date", index = 8, header = "odf.header.installationDate")
	private String installationDate;

	@Element(type = "String", index = 9, header = "odf.header.note")
	private String note;

	//===============================================
	public String getStt() {
		return stt;
	}

	public void setStt(String stt) {
		this.stt = stt;
	}

	//===============================================
	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	//===============================================
	public String getOdfIndex() {
		return odfIndex;
	}

	public void setOdfIndex(String odfIndex) {
		this.odfIndex = odfIndex;
	}

	//===============================================
	public String getOdfCode() {
		return odfCode;
	}

	public void setOdfCode(String odfCode) {
		this.odfCode = odfCode;
	}

	//===============================================
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	//===============================================
	public String getOdfTypeCode() {
		return odfTypeCode;
	}

	public void setOdfTypeCode(String odfTypeCode) {
		this.odfTypeCode = odfTypeCode;
	}

	//===============================================
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	//===============================================
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	//===============================================
	public String getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}

	//===============================================
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
