/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import java.util.List;
import javax.persistence.*;



/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CAT_TENANTS")
public class CatTenants implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TENANT_ID")
    @SequenceGenerator(name = "catCatTenantsGenerator", sequenceName = "CAT_TENANTS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catCatTenantsGenerator")
    private Long tenantId;
    @Basic(optional = false)
    @Column(name = "TENANT_CODE")
    private String tenantCode;
    @Basic(optional = false)
    @Column(name = "TENANT_NAME")
    private String tenantName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tenantId")
    private List<CatDepartment> catDepartmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tenantId")
    private List<CatLocation> catLocationList;

    public CatTenants() {
    }

    public CatTenants(Long tenantId) {
        this.tenantId = tenantId;
    }

    public CatTenants(Long tenantId, String tenantCode, String tenantName) {
        this.tenantId = tenantId;
        this.tenantCode = tenantCode;
        this.tenantName = tenantName;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

   
    public List<CatDepartment> getCatDepartmentList() {
        return catDepartmentList;
    }

    public void setCatDepartmentList(List<CatDepartment> catDepartmentList) {
        this.catDepartmentList = catDepartmentList;
    }

   
    public List<CatLocation> getCatLocationList() {
        return catLocationList;
    }

    public void setCatLocationList(List<CatLocation> catLocationList) {
        this.catLocationList = catLocationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tenantId != null ? tenantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatTenants)) {
            return false;
        }
        CatTenants other = (CatTenants) object;
        if ((this.tenantId == null && other.tenantId != null) || (this.tenantId != null && !this.tenantId.equals(other.tenantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.CatTenants[ tenantId=" + tenantId + " ]";
    }
    
}
