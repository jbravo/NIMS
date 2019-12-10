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
@Table(name = "CAT_DEVICE_TYPE")
public class CatDeviceType implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "DEVICE_TYPE_ID")
    @SequenceGenerator(name = "catDevicesGenerator", sequenceName = "CAT_DEVICE_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catDevicesGenerator")
    private Long deviceTypeId;
    @Basic(optional = false)
    @Column(name = "DEVICE_TYPE_CODE")
    private String deviceTypeCode;
    @Basic(optional = false)
    @Column(name = "VENDOR_ID")
    private Long vendorId;
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

    public CatDeviceType() {
    }

    public CatDeviceType(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public CatDeviceType(Long deviceTypeId, String deviceTypeCode, Long vendorId) {
        this.deviceTypeId = deviceTypeId;
        this.deviceTypeCode = deviceTypeCode;
        this.vendorId = vendorId;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeCode() {
        return deviceTypeCode;
    }

    public void setDeviceTypeCode(String deviceTypeCode) {
        this.deviceTypeCode = deviceTypeCode;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deviceTypeId != null ? deviceTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatDeviceType)) {
            return false;
        }
        CatDeviceType other = (CatDeviceType) object;
        if ((this.deviceTypeId == null && other.deviceTypeId != null) || (this.deviceTypeId != null && !this.deviceTypeId.equals(other.deviceTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.CatDeviceType[ deviceTypeId=" + deviceTypeId + " ]";
    }

}
