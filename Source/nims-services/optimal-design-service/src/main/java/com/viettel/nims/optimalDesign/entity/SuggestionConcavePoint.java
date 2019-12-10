/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.*;


/**
 *
 * @author Admin
 */
@Entity
@Table(name = "SUGGESTION_CONCAVE_POINT")
public class SuggestionConcavePoint implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "SUGGEST_ID")
    private Long suggestId;
    @Column(name = "CONCAVE_POINT_CODE")
    private String concavePointCode;
    @Column(name = "CONCAVE_POINT_TYPE")
    private Integer concavePointType;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "ROW_STATUS")
    private Integer rowStatus;

    public SuggestionConcavePoint() {
    }

    public SuggestionConcavePoint(Long suggestId) {
        this.suggestId = suggestId;
    }

    public Long getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(Long suggestId) {
        this.suggestId = suggestId;
    }

    public String getConcavePointCode() {
        return concavePointCode;
    }

    public void setConcavePointCode(String concavePointCode) {
        this.concavePointCode = concavePointCode;
    }

    public Integer getConcavePointType() {
        return concavePointType;
    }

    public void setConcavePointType(Integer concavePointType) {
        this.concavePointType = concavePointType;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (suggestId != null ? suggestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuggestionConcavePoint)) {
            return false;
        }
        SuggestionConcavePoint other = (SuggestionConcavePoint) object;
        if ((this.suggestId == null && other.suggestId != null) || (this.suggestId != null && !this.suggestId.equals(other.suggestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.SuggestionConcavePoint[ suggestId=" + suggestId + " ]";
    }
    
}
