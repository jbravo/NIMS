package com.viettel.nims.transmission.model.view;

public class CableInSleeveResponDto {
	private Long cableId;//Id cable
	private String cableCode;//ma cable
	private Integer quad;//quad
	private Integer yarn;//Sợi
	public Long getCableId() {
		return cableId;
	}
	public void setCableId(Long cableId) {
		this.cableId = cableId;
	}
	public String getCableCode() {
		return cableCode;
	}
	public void setCableCode(String cableCode) {
		this.cableCode = cableCode;
	}
	public Integer getQuad() {
		return quad;
	}
	public void setQuad(Integer quad) {
		this.quad = quad;
	}
	public Integer getYarn() {
		return yarn;
	}
	public void setYarn(Integer yarn) {
		this.yarn = yarn;
	}
	
	@Override
	public String toString() {
		return "CableInSleeveResponDto [cableId=" + cableId + ", cableCode=" + cableCode + ", quad=" + quad + ", yarn="
				+ yarn + "]";
	}
	
}
