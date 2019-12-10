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
@Table(name = "CAT_LOCATION")
public class CatLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "LOCATION_ID")
    @SequenceGenerator(name = "catLocationsGenerator", sequenceName = "CAT_LOCATION_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catLocationsGenerator")
    private Long locationId;
    @Basic(optional = false)
    @Column(name = "LOCATION_CODE")
    private String locationCode;
    @Basic(optional = false)
    @Column(name = "LOCATION_NAME")
    private String locationName;
    @Column(name = "PARENT_ID")
    private Long parentId;
    @Column(name = "TERRAIN")
    private Short terrain;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "ROW_STATUS")
    private Integer rowStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
    private List<CntDepartmentLocation> cntDepartmentLocationList;
    @JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID")
    @ManyToOne(optional = false)
    private CatTenants tenantId;

    public CatLocation() {
    }

    public CatLocation(Long locationId) {
        this.locationId = locationId;
    }

    public CatLocation(Long locationId, String locationCode, String locationName) {
        this.locationId = locationId;
        this.locationCode = locationCode;
        this.locationName = locationName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Short getTerrain() {
        return terrain;
    }

    public void setTerrain(Short terrain) {
        this.terrain = terrain;
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

   
    public List<CntDepartmentLocation> getCntDepartmentLocationList() {
        return cntDepartmentLocationList;
    }

    public void setCntDepartmentLocationList(List<CntDepartmentLocation> cntDepartmentLocationList) {
        this.cntDepartmentLocationList = cntDepartmentLocationList;
    }

    public CatTenants getTenantId() {
        return tenantId;
    }

    public void setTenantId(CatTenants tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatLocation)) {
            return false;
        }
        CatLocation other = (CatLocation) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.CatLocation[ locationId=" + locationId + " ]";
    }
    
}
