/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


import java.util.Date;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "SUGGESTION_NEW_SITE")
public class SuggestionNewSite implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "SUGGEST_ID")
    private Long suggestNewSiteId;
    @Column(name = "STATION_CODE")
    private String stationCode;
    @Column(name = "LNG")
    private Float lng;
    @Column(name = "LAT")
    private Float lat;
    @Column(name = "LOCATION_ID")
    private Long locationId;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "AUDIT_TYPE")
    private Integer auditType;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "ROW_STATUS")
    private Integer rowStatus;
    @Column(name = "STATION_CODE_APPROVED")
    private String stationCodeApproved;

    public SuggestionNewSite() {
    }

    public SuggestionNewSite(Long suggestNewSiteId) {
        this.suggestNewSiteId = suggestNewSiteId;
    }

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

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
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

  public String getStationCodeApproved() {
    return stationCodeApproved;
  }

  public void setStationCodeApproved(String stationCodeApproved) {
    this.stationCodeApproved = stationCodeApproved;
  }

  @Override
    public int hashCode() {
        int hash = 0;
        hash += (suggestNewSiteId != null ? suggestNewSiteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuggestionNewSite)) {
            return false;
        }
        SuggestionNewSite other = (SuggestionNewSite) object;
        if ((this.suggestNewSiteId == null && other.suggestNewSiteId != null) || (this.suggestNewSiteId != null && !this.suggestNewSiteId.equals(other.suggestNewSiteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.SuggestionNewSite[ suggestId=" + suggestNewSiteId + " ]";
    }

}
