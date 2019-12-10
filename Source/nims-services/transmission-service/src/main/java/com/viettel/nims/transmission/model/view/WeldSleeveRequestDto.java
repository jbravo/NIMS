package com.viettel.nims.transmission.model.view;

import java.math.BigDecimal;

import com.viettel.nims.commons.util.PaginationDTO;

public class WeldSleeveRequestDto extends PaginationDTO {
	private Long cableSourceId;//Id cap den
	private Long sleeveId;//Id mang xong
	private Long cableDestId;//Id cap di
	private String typeWeld;//Kieu han
	private String weldAmount; //So luong han
	private String welddingBy;//Nguoi han
	private BigDecimal attenuation;// Suy hao
	//Han 1 cap va truc tiep
	private Integer quadSource; //Quad đến
	private Integer quadDest; //Quad đi
	//Nhieu cap va truc tiep
	private Integer quadSourceStart;//Quad đến từ
	private Integer quadSourceEnd; //Quad đến đến
	private Integer quadDestStart; //Quad đi từ
	private Integer quadDestEnd; //Quad đi đến
	//1 cap va thong thuong
	private Integer yarnSource;//Sợi đi
	private Integer yarnDest;//Sợi đến
	//Nhieu cap va thong thuong
	private Integer cableSourceYarnStart;//Sợi đến từ
	private Integer cableSourceYarnEnd; //Sợi đến đến
	private Integer cableDestYarnStart;//Sợi đến từ
	private Integer cableDestYarnEnd; //Sợi đến đến
	public Long getCableSourceId() {
		return cableSourceId;
	}
	public void setCableSourceId(Long cableSourceId) {
		this.cableSourceId = cableSourceId;
	}
	public Long getSleeveId() {
		return sleeveId;
	}
	public void setSleeveId(Long sleeveId) {
		this.sleeveId = sleeveId;
	}
	public Long getCableDestId() {
		return cableDestId;
	}
	public void setCableDestId(Long cableDestId) {
		this.cableDestId = cableDestId;
	}
	public String getTypeWeld() {
		return typeWeld;
	}
	public void setTypeWeld(String typeWeld) {
		this.typeWeld = typeWeld;
	}
	public String getWeldAmount() {
		return weldAmount;
	}
	public void setWeldAmount(String weldAmount) {
		this.weldAmount = weldAmount;
	}
	public String getWelddingBy() {
		return welddingBy;
	}
	public void setWelddingBy(String welddingBy) {
		this.welddingBy = welddingBy;
	}
	public BigDecimal getAttenuation() {
		return attenuation;
	}
	public void setAttenuation(BigDecimal attenuation) {
		this.attenuation = attenuation;
	}
	public Integer getQuadSource() {
		return quadSource;
	}
	public void setQuadSource(Integer quadSource) {
		this.quadSource = quadSource;
	}
	public Integer getQuadDest() {
		return quadDest;
	}
	public void setQuadDest(Integer quadDest) {
		this.quadDest = quadDest;
	}
	public Integer getQuadSourceStart() {
		return quadSourceStart;
	}
	public void setQuadSourceStart(Integer quadSourceStart) {
		this.quadSourceStart = quadSourceStart;
	}
	public Integer getQuadSourceEnd() {
		return quadSourceEnd;
	}
	public void setQuadSourceEnd(Integer quadSourceEnd) {
		this.quadSourceEnd = quadSourceEnd;
	}
	public Integer getQuadDestStart() {
		return quadDestStart;
	}
	public void setQuadDestStart(Integer quadDestStart) {
		this.quadDestStart = quadDestStart;
	}
	public Integer getQuadDestEnd() {
		return quadDestEnd;
	}
	public void setQuadDestEnd(Integer quadDestEnd) {
		this.quadDestEnd = quadDestEnd;
	}
	public Integer getYarnSource() {
		return yarnSource;
	}
	public void setYarnSource(Integer yarnSource) {
		this.yarnSource = yarnSource;
	}
	public Integer getYarnDest() {
		return yarnDest;
	}
	public void setYarnDest(Integer yarnDest) {
		this.yarnDest = yarnDest;
	}
	public Integer getCableSourceYarnStart() {
		return cableSourceYarnStart;
	}
	public void setCableSourceYarnStart(Integer cableSourceYarnStart) {
		this.cableSourceYarnStart = cableSourceYarnStart;
	}
	public Integer getCableSourceYarnEnd() {
		return cableSourceYarnEnd;
	}
	public void setCableSourceYarnEnd(Integer cableSourceYarnEnd) {
		this.cableSourceYarnEnd = cableSourceYarnEnd;
	}
	public Integer getCableDestYarnStart() {
		return cableDestYarnStart;
	}
	public void setCableDestYarnStart(Integer cableDestYarnStart) {
		this.cableDestYarnStart = cableDestYarnStart;
	}
	public Integer getCableDestYarnEnd() {
		return cableDestYarnEnd;
	}
	public void setCableDestYarnEnd(Integer cableDestYarnEnd) {
		this.cableDestYarnEnd = cableDestYarnEnd;
	}
	public WeldSleeveRequestDto(Long cableSourceId, Long sleeveId, Long cableDestId, String typeWeld, String weldAmount,
			String welddingBy, BigDecimal attenuation, Integer quadSource, Integer quadDest, Integer quadSourceStart,
			Integer quadSourceEnd, Integer quadDestStart, Integer quadDestEnd, Integer yarnSource, Integer yarnDest,
			Integer cableSourceYarnStart, Integer cableSourceYarnEnd, Integer cableDestYarnStart, Integer cableDestYarnEnd) {
		super();
		this.cableSourceId = cableSourceId;
		this.sleeveId = sleeveId;
		this.cableDestId = cableDestId;
		this.typeWeld = typeWeld;
		this.weldAmount = weldAmount;
		this.welddingBy = welddingBy;
		this.attenuation = attenuation;
		this.quadSource = quadSource;
		this.quadDest = quadDest;
		this.quadSourceStart = quadSourceStart;
		this.quadSourceEnd = quadSourceEnd;
		this.quadDestStart = quadDestStart;
		this.quadDestEnd = quadDestEnd;
		this.yarnSource = yarnSource;
		this.yarnDest = yarnDest;
		this.cableSourceYarnStart = cableSourceYarnStart;
		this.cableSourceYarnEnd = cableSourceYarnEnd;
		this.cableDestYarnStart = cableDestYarnStart;
		this.cableDestYarnEnd = cableDestYarnEnd;
	}
	public WeldSleeveRequestDto() {
		super();
	}
	@Override
	public String toString() {
		return "WeldSleeveRequestDto [cableSourceId=" + cableSourceId + ", sleeveId=" + sleeveId + ", cableDestId="
				+ cableDestId + ", typeWeld=" + typeWeld + ", weldAmount=" + weldAmount + ", welddingBy=" + welddingBy
				+ ", attenuation=" + attenuation + ", quadSource=" + quadSource + ", quadDest=" + quadDest
				+ ", quadSourceStart=" + quadSourceStart + ", quadSourceEnd=" + quadSourceEnd + ", quadDestStart="
				+ quadDestStart + ", quadDestEnd=" + quadDestEnd + ", yarnSource=" + yarnSource + ", yarnDest="
				+ yarnDest + ", cableSourceYarnStart=" + cableSourceYarnStart + ", cableSourceYarnEnd="
				+ cableSourceYarnEnd + ", cableDestYarnStart=" + cableDestYarnStart + ", cableDestYarnEnd="
				+ cableDestYarnEnd + "]";
	}


}
