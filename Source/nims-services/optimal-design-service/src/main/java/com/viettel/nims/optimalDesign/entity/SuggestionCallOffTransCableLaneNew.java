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
@Table(name = "SUGGESTION_CALL_OFF_TRANS_CABLE_LANE_NEW")
public class SuggestionCallOffTransCableLaneNew implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "SUGGESTION_CALL_OFF_ID")
    private Long suggestionCallOffId;
    @Column(name = "LANE_CODE")
    private String laneCode;
    @Column(name = "SOURCE_CODE")
    private String sourceCode;
    @Column(name = "DEST_CODE")
    private String destCode;
    @Column(name = "LENGTH_HANG")
    private Long lengthHang;
    @Column(name = "LENGTH_BURY")
    private Long lengthBury;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "ROW_STATUS")
    private Integer rowStatus;

    public SuggestionCallOffTransCableLaneNew() {
    }

    public SuggestionCallOffTransCableLaneNew(Long suggestionCallOffId) {
        this.suggestionCallOffId = suggestionCallOffId;
    }

    public Long getSuggestionCallOffId() {
        return suggestionCallOffId;
    }

    public void setSuggestionCallOffId(Long suggestionCallOffId) {
        this.suggestionCallOffId = suggestionCallOffId;
    }

    public String getLaneCode() {
        return laneCode;
    }

    public void setLaneCode(String laneCode) {
        this.laneCode = laneCode;
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

    public Long getLengthHang() {
        return lengthHang;
    }

    public void setLengthHang(Long lengthHang) {
        this.lengthHang = lengthHang;
    }

    public Long getLengthBury() {
        return lengthBury;
    }

    public void setLengthBury(Long lengthBury) {
        this.lengthBury = lengthBury;
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
        hash += (suggestionCallOffId != null ? suggestionCallOffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuggestionCallOffTransCableLaneNew)) {
            return false;
        }
        SuggestionCallOffTransCableLaneNew other = (SuggestionCallOffTransCableLaneNew) object;
        if ((this.suggestionCallOffId == null && other.suggestionCallOffId != null) || (this.suggestionCallOffId != null && !this.suggestionCallOffId.equals(other.suggestionCallOffId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.SuggestionCallOffTransCableLaneNew[ suggestionCallOffId=" + suggestionCallOffId + " ]";
    }
    
}
