package com.viettel.nims.transmission.model.dto;

import com.viettel.nims.transmission.commom.annotation.Element;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;

@SheetSerializable(sheetDataIndex = 0, startRow = 4, totalCols = 11, resultCols = 11,flagRow = 3)
public class InfraCableInStationDTO {
	 @Element(type = "String", index = 0, header = "common.label.stt")
	  private String stt;

	  @Element(type = "String", index = 1, header = "cable.header.stationCode")
	  private String stationCode;

	  @Element(type = "String", index = 2, header = "cable.header.sourceCode")
	  private String sourceCode;

	  @Element(type = "String", index = 3, header = "cable.header.destCode")
	  private String destCode;

	  @Element(type = "number", index = 4, header = "cable.cableIndex",isGenerateValue = true)
	  private String cableIndex;

	  @Element(type = "String", index = 5, header = "cable.cableCode" , isGenerateValue = true)
	  private String cableCode;

	  @Element(type = "String", index = 6, header = "cable.header.cableTypeCode")
	  private String cableTypeCode;

	  @Element(type = "String", index = 7, header = "cable.header.lenghth")
	  private String length;

	  @Element(type = "String", index = 8, header = "cable.header.status")
	  private String statusName;

	  @Element(type = "Date", index = 9, header = "cable.header.installationDate")
	  private String installationDate;

	  @Element(type = "String", index = 10, header = "station.note")
	  private String note;



	public String getStt() {
		return stt;
	}

	public void setStt(String stt) {
		this.stt = stt;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	public String getCableIndex() {
		return cableIndex;
	}

	public void setCableIndex(String cableIndex) {
		this.cableIndex = cableIndex;
	}

	public String getCableCode() {
		return cableCode;
	}

	public void setCableCode(String cableCode) {
		this.cableCode = cableCode;
	}

	public String getCableTypeCode() {
		return cableTypeCode;
	}

	public void setCableTypeCode(String cableTypeCode) {
		this.cableTypeCode = cableTypeCode;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


}
