package com.viettel.nims.transmission.model.dto.enums;

public enum EnumTypeImport {

	IMPORT_FOLDER_TEMPLATE("template"), 

	IMPORT_ODF("importOdf"),

	IMPORT_COUPLE_TO_LINE_ODF("importCoupleToLineOdf"),

	IMPORT_COUPLE_TO_COUPLE_ODF("importCoupleToCoupleOdf");

	private String type;

	EnumTypeImport(String type) {
		this.type = type;
	}

	 public String type() {
		return type;
		}
}
