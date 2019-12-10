package com.viettel.nims.transmission.model.view;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

/**
 * Created by ThieuNV on 08/24/2019.
 */
@Entity
@Table(name = "view_infra_pools")
public class ViewInfraPoolsBO {
	private Long poolId;
	private String poolCode;
//  private String poolTTT;
//  private Long poolYYYY;
//  private String poolZ;
	private Long deptId;
	private String deptName;
	private String deptCode;
	private Long locationId;
	private String locationName;
	private Long poolTypeId;
	private String poolTypeCode;
	private Long ownerId;
	private String ownerName;
	private Date constructionDate;
	private Date deliveryDate;
	private Date acceptanceDate;
	private Integer status;
	private String address;
//  private Point geometry;
	private String note;
	private Date createTime;
	private Date updateTime;
	private Integer rowStatus;
	private Double longitude;
	private Double latitude;
	private String pathName;
	private String statusName;
	private String pathLocationName;

	@Id
	@Basic
	@Column(name = "POOL_ID")
	public Long getPoolId() {
		return poolId;
	}

	public void setPoolId(Long poolId) {
		this.poolId = poolId;
	}

	@Basic
	@Column(name = "POOL_CODE")
	public String getPoolCode() {
		return poolCode;
	}

	public void setPoolCode(String poolCode) {
		this.poolCode = poolCode;
	}

	@Basic
	@Column(name = "DEPT_ID")
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Basic
	@Column(name = "LOCATION_ID")
	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	@Basic
	@Column(name = "POOL_TYPE_ID")
	public Long getPoolTypeId() {
		return poolTypeId;
	}

	public void setPoolTypeId(Long poolTypeId) {
		this.poolTypeId = poolTypeId;
	}

	@Basic
	@Column(name = "OWNER_ID")
	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	@Basic
	@Column(name = "CONSTRUCTION_DATE")
	public Date getConstructionDate() {
		return constructionDate;
	}

	public void setConstructionDate(Date constructionDate) {
		this.constructionDate = constructionDate;
	}

	@Basic
	@Column(name = "DELIVERY_DATE")
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Basic
	@Column(name = "ACCEPTANCE_DATE")
	public Date getAcceptanceDate() {
		return acceptanceDate;
	}

	public void setAcceptanceDate(Date acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}

	@Basic
	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Basic
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

//  @Basic
//  @Column(name = "GEOMETRY", columnDefinition = "geometry(Point,4326)")
//  @JsonSerialize(using = GeometrySerializer.class)
//  @JsonDeserialize(contentUsing = GeometryDeserializer.class)
//  public Point getGeometry() {
//    return geometry;
//  }
//
//  public void setGeometry(Point geometry) {
//    this.geometry = geometry;
//  }

	@Basic
	@Column(name = "NOTE")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Basic
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Basic
	@Column(name = "ROW_STATUS")
	public Integer getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(Integer rowStatus) {
		this.rowStatus = rowStatus;
	}

	@Basic
	@Column(name = "DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Basic
	@Column(name = "LOCATION_NAME")
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Basic
	@Column(name = "POOL_TYPE_CODE")
	public String getPoolTypeCode() {
		return poolTypeCode;
	}

	public void setPoolTypeCode(String poolTypeCode) {
		this.poolTypeCode = poolTypeCode;
	}

	@Basic
	@Column(name = "OWNER_NAME")
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Basic
	@Column(name = "LONGITUDE")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Basic
	@Column(name = "LATITUDE")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Basic
	@Column(name = "DEPT_CODE")
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

//  @Basic
//  @Column(name = "POOL_YYYY")
//  public Long getPoolYYYY() {
//    return poolYYYY;
//  }
//
//  public void setPoolYYYY(Long poolYYYY) {
//    this.poolYYYY = poolYYYY;
//  }
//
//  @Basic
//  @Column(name = "POOL_Z")
//  public String getPoolZ() {
//    return poolZ;
//  }
//
//  public void setPoolZ(String poolZ) {
//    this.poolZ = poolZ;
//  }
//
//  @Basic
//  @Column(name = "POOL_TTT")
//  public String getPoolTTT() {
//    return poolTTT;
//  }
//
//  public void setPoolTTT(String poolTTT) {
//    this.poolTTT = poolTTT;
//  }

	@Basic
	@Column(name = "PATH_NAME")
	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	@Transient
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Basic
	@Column(name = "PATH_LOCAL_NAME")
	public String getPathLocationName() {
		return pathLocationName;
	}

	public void setPathLocationName(String pathLocationName) {
		this.pathLocationName = pathLocationName;
	}
	
	



}
