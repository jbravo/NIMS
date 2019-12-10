/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import java.io.Serializable;


import java.util.Date;
import java.util.List;
import javax.persistence.*;



/**
 *
 * @author Admin
 */
@Entity
@Table(name = "SYS_EVENT_LOG")
public class SysEventLog implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "SYS_EVENT_LOG_ID")
    @SequenceGenerator(name = "sysEventLogGenerator", sequenceName = "SYS_EVENT_LOG_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sysEventLogGenerator")
    private Long sysEventLogId;
    @Basic(optional = false)
    @Column(name = "ACTION")
    private short action;
    @Basic(optional = false)
    @Column(name = "OBJECT_CODE")
    private String objectCode;
    @Basic(optional = false)
    @Column(name = "OBJECT_ID")
    private Long objectId;
    @Basic(optional = false)
    @Column(name = "OBJECT_TYPE")
    private int objectType;
    @Basic(optional = false)
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "USER_IP")
    private String userIp;
    @Column(name = "OBJECT_DEPT_ID")
    private Long objectDeptId;
    @Column(name = "USER_DEPT_ID")
    private Long userDeptId;
    @Column(name = "OBJECT_DEPT_NAME")
    private String objectDeptName;
    @Column(name = "USER_DEPT_NAME")
    private String userDeptName;
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "NOTE")
    private String note;
    @OneToMany(mappedBy = "sysEventLogId")
    private List<SysEventLogDetail> sysEventLogDetailList;

    public SysEventLog() {
    }

    public SysEventLog(Long sysEventLogId) {
        this.sysEventLogId = sysEventLogId;
    }

    public SysEventLog(Long sysEventLogId, short action, String objectCode, Long objectId, int objectType, String userName) {
        this.sysEventLogId = sysEventLogId;
        this.action = action;
        this.objectCode = objectCode;
        this.objectId = objectId;
        this.objectType = objectType;
        this.userName = userName;
    }

    public Long getSysEventLogId() {
        return sysEventLogId;
    }

    public void setSysEventLogId(Long sysEventLogId) {
        this.sysEventLogId = sysEventLogId;
    }

    public short getAction() {
        return action;
    }

    public void setAction(short action) {
        this.action = action;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Long getObjectDeptId() {
        return objectDeptId;
    }

    public void setObjectDeptId(Long objectDeptId) {
        this.objectDeptId = objectDeptId;
    }

    public Long getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(Long userDeptId) {
        this.userDeptId = userDeptId;
    }

    public String getObjectDeptName() {
        return objectDeptName;
    }

    public void setObjectDeptName(String objectDeptName) {
        this.objectDeptName = objectDeptName;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

   
    public List<SysEventLogDetail> getSysEventLogDetailList() {
        return sysEventLogDetailList;
    }

    public void setSysEventLogDetailList(List<SysEventLogDetail> sysEventLogDetailList) {
        this.sysEventLogDetailList = sysEventLogDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sysEventLogId != null ? sysEventLogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysEventLog)) {
            return false;
        }
        SysEventLog other = (SysEventLog) object;
        if ((this.sysEventLogId == null && other.sysEventLogId != null) || (this.sysEventLogId != null && !this.sysEventLogId.equals(other.sysEventLogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.SysEventLog[ sysEventLogId=" + sysEventLogId + " ]";
    }
    
}
