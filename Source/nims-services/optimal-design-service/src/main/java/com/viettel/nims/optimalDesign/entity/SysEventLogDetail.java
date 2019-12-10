/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 *
 * @author Admin
 */
@Entity
@Table(name = "SYS_EVENT_LOG_DETAIL")
public class SysEventLogDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @SequenceGenerator(name = "sysEventLogDetailGenerator", sequenceName = "SYS_EVENT_LOG_DETAIL_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sysEventLogDetailGenerator")
    private Long id;
    @Basic(optional = false)
    @Column(name = "FIELD_NAME")
    private String fieldName;
    @Column(name = "OLD_VALUE")
    private String oldValue;
    @Column(name = "NEW_VALUE")
    private String newValue;
    @JoinColumn(name = "SYS_EVENT_LOG_ID", referencedColumnName = "SYS_EVENT_LOG_ID", insertable = false, updatable = false)
    @ManyToOne
    private SysEventLog sysEventLogId;
    @Column(name = "SYS_EVENT_LOG_ID")
    private Long idSysEventLog;

    public SysEventLogDetail() {
    }

    public SysEventLogDetail(Long id) {
        this.id = id;
    }

    public SysEventLogDetail(Long id, String fieldName) {
        this.id = id;
        this.fieldName = fieldName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public SysEventLog getSysEventLogId() {
        return sysEventLogId;
    }

    public void setSysEventLogId(SysEventLog sysEventLogId) {
        this.sysEventLogId = sysEventLogId;
    }

  public Long getIdSysEventLog() {
    return idSysEventLog;
  }

  public void setIdSysEventLog(Long idSysEventLog) {
    this.idSysEventLog = idSysEventLog;
  }

  @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysEventLogDetail)) {
            return false;
        }
        SysEventLogDetail other = (SysEventLogDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.SysEventLogDetail[ id=" + id + " ]";
    }
    
}
