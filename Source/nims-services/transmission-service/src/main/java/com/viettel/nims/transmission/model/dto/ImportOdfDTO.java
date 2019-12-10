package com.viettel.nims.transmission.model.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.viettel.nims.transmission.model.view.ViewInfraOdfBO;

/**
 * ImportOdfDTO
 * Version 1.0
 * Date: 09-26-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 09-26-2019				HungNV				Create
 */
public class ImportOdfDTO {

	private List<ViewInfraOdfBO> result;

	private String type;

	private MultipartFile file;

	public ImportOdfDTO(List<ViewInfraOdfBO> result, String type) {
		super();
		this.result = result;
		this.type = type;
	}


	public ImportOdfDTO(String type, MultipartFile file) {
		super();
		this.type = type;
		this.file = file;
	}


	public ImportOdfDTO() {
		super();
	}

	public List<ViewInfraOdfBO> getResult() {
		return result;
	}

	public void setResult(List<ViewInfraOdfBO> result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	
	
}
