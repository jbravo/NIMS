  /*
  * To change this license header, choose License Headers in Project Properties.
  * To change this template file, choose Tools | Templates
  * and open the template in the editor.
  */
  package com.viettel.nims.optimalDesign.dto;

  import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

  /**
  * @author Admin
  */
  @Data
  public class SuggestionNewSiteDTO implements Serializable {

    @Id
    @Column(name="SUGGEST_ID")
    private Long suggestNewSiteId;

    @NotNull(message = "suggestionNewSite.stationCode.required")
    @Pattern(regexp = "^(?=.*\\S).*", message = "suggestionNewSite.stationCode.required")
    @Length(max = 30, message = "suggestionNewSite.stationCode.length.max.30")
    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "suggestionNewSite.stationCode.value.number")
    private String stationCode;

    @NotNull(message = "suggestionNewSite.lng.required")
    @Pattern(regexp = "^(?=.*\\S).*", message = "suggestionNewSite.lng.required")
    @Min(value = -180, message = "suggestionNewSite.lng.format.invalid")
    @Max(value = 180, message = "suggestionNewSite.lng.format.invalid")
    @Pattern(regexp = "^(-)?[0-9]{0,3}$|^(-)?[0-9]{0,3}.(0)+$|^(-)?[0-9]{0,3}.[1-9]{5,}$", message = "suggestionNewSite.lng.format.invalid")
    private String lng;

    @NotNull(message = "suggestionNewSite.lat.required")
    @Pattern(regexp = "^(?=.*\\S).*", message = "suggestionNewSite.lat.required")
    @Min(value = -90, message = "suggestionNewSite.lat.format.invalid")
    @Max(value = 90, message = "suggestionNewSite.lat.format.invalid")
    @Pattern(regexp = "^(-)?[0-9]{0,3}$|^(-)?[0-9]{0,3}.(0)+$|^(-)?[0-9]{0,3}.[1-9]{5,}$", message = "suggestionNewSite.lat.format.invalid")
    private String lat;

    @NotNull(message = "suggestionNewSite.locationId.required")
    private Long locationId;

    @Length(max = 200, message = "suggestionNewSite.address.length.max.200")
    private String address;

    @NotNull(message = "suggestionNewSite.auditType.required")
    private Integer auditType;
    private String note;
    private Date createTime;
    private Date updateTime;
    private Integer rowStatus;
    private String stationCodeApproved;

    @Valid
    private SuggestionRadioDTO suggestionRadioDTO;

    public Long getSuggestNewSiteId() {
      return suggestNewSiteId;
    }

    public void setSuggestNewSiteId(Long suggestNewSiteId) {
      this.suggestNewSiteId = suggestNewSiteId;
    }

    public String getStationCode() {
      return stationCode;
    }

    public void setStationCode(String stationCode) {
      this.stationCode = stationCode;
    }

    public String getLng() {
      return lng;
    }

    public void setLng(String lng) {
      this.lng = lng;
    }

    public String getLat() {
      return lat;
    }

    public void setLat(String lat) {
      this.lat = lat;
    }

    public Long getLocationId() {
      return locationId;
    }

    public void setLocationId(Long locationId) {
      this.locationId = locationId;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public Integer getAuditType() {
      return auditType;
    }

    public void setAuditType(Integer auditType) {
      this.auditType = auditType;
    }

    public String getNote() {
      return note;
    }

    public void setNote(String note) {
      this.note = note;
    }

    public Date getCreateTime() {
      return createTime;
    }

    public void setCreateTime(Date createTime) {
      this.createTime = createTime;
    }

    public Date getUpdateTime() {
      return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
      this.updateTime = updateTime;
    }

    public Integer getRowStatus() {
      return rowStatus;
    }

    public void setRowStatus(Integer rowStatus) {
      this.rowStatus = rowStatus;
    }

    public SuggestionRadioDTO getSuggestionRadioDTO() {
      return suggestionRadioDTO;
    }

    public void setSuggestionRadioDTO(SuggestionRadioDTO suggestionRadioDTO) {
      this.suggestionRadioDTO = suggestionRadioDTO;
    }
  }
