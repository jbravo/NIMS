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
@Table(name = "CNT_DEPARTMENT_LOCATION")
public class CntDepartmentLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @SequenceGenerator(name = "catCntDepartmentLocationGenerator", sequenceName = "CAT_CNT_DEPARTMENT_LOCATION_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catCntDepartmentLocationGenerator")
    private Long id;
    @Basic(optional = false)
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEPT_ID")
    @ManyToOne(optional = false)
    private CatDepartment deptId;
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID")
    @ManyToOne(optional = false)
    private CatLocation locationId;

    public CntDepartmentLocation() {
    }

    public CntDepartmentLocation(Long id) {
        this.id = id;
    }

    public CntDepartmentLocation(Long id, Date updateTime) {
        this.id = id;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public CatDepartment getDeptId() {
        return deptId;
    }

    public void setDeptId(CatDepartment deptId) {
        this.deptId = deptId;
    }

    public CatLocation getLocationId() {
        return locationId;
    }

    public void setLocationId(CatLocation locationId) {
        this.locationId = locationId;
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
        if (!(object instanceof CntDepartmentLocation)) {
            return false;
        }
        CntDepartmentLocation other = (CntDepartmentLocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.CntDepartmentLocation[ id=" + id + " ]";
    }
    
}
