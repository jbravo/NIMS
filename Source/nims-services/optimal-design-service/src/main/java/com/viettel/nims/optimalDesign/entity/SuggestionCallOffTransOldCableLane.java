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
@Table(name = "SUGGESTION_CALL_OFF_TRANS_OLD_CABLE_LANE")

public class SuggestionCallOffTransOldCableLane implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "SUGGESTION_CALL_OFF_ID")
    private Long suggestionCallOffId;
    @Column(name = "LANE_CODE")
    private String laneCode;
    @Column(name = "REUSE_CABLE_HANG_LENGTH")
    private Long reuseCableHangLength;
    @Column(name = "REUSE_CABLE_BURY_LENGTH")
    private Long reuseCableBuryLength;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "ROW_STATUS")
    private Integer rowStatus;

    public SuggestionCallOffTransOldCableLane() {
    }

    public SuggestionCallOffTransOldCableLane(Long suggestionCallOffId) {
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

    public Long getReuseCableHangLength() {
        return reuseCableHangLength;
    }

    public void setReuseCableHangLength(Long reuseCableHangLength) {
        this.reuseCableHangLength = reuseCableHangLength;
    }

    public Long getReuseCableBuryLength() {
        return reuseCableBuryLength;
    }

    public void setReuseCableBuryLength(Long reuseCableBuryLength) {
        this.reuseCableBuryLength = reuseCableBuryLength;
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
        if (!(object instanceof SuggestionCallOffTransOldCableLane)) {
            return false;
        }
        SuggestionCallOffTransOldCableLane other = (SuggestionCallOffTransOldCableLane) object;
        if ((this.suggestionCallOffId == null && other.suggestionCallOffId != null) || (this.suggestionCallOffId != null && !this.suggestionCallOffId.equals(other.suggestionCallOffId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.SuggestionCallOffTransOldCableLane[ suggestionCallOffId=" + suggestionCallOffId + " ]";
    }
    
}
