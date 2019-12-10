package com.viettel.nims.transmission.model.view;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "view_odf_cable")
public class ViewODFCableBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long cableId;

	private Long odfId;

	private String cableCode;

	private String odfCode;


	@Id
	@Column(name = "CABLE_ID")
	public Long getCableId() {
		return cableId;
	}

	public void setCableId(Long cableId) {
		this.cableId = cableId;
	}

	@Id
	@Column(name = "ODF_ID")
	public Long getOdfId() {
		return odfId;
	}

	public void setOdfId(Long odfId) {
		this.odfId = odfId;
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
	@Column(name = "ODF_CODE")
	public String getOdfCode() {
		return odfCode;
	}

	public void setOdfCode(String odfCode) {
		this.odfCode = odfCode;
	}

}
