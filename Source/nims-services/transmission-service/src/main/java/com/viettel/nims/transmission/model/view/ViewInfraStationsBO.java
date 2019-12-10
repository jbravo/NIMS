package com.viettel.nims.transmission.model.view;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
@Entity
@Table(name = "view_infra_stations")
public class ViewInfraStationsBO {
	private Long stationId;
	private String stationCode;
	private Long deptId;
	private String deptName;
	private String deptCode;
	private String pathName;
	private Long locationId;
	private String locationName;
	private String locationCode;
	private String pathLocalName;
	private Long terrain;
	private String terrainName;
	private String houseOwnerName;
	private String houseOwnerPhone;
	private String address;
	private Long ownerId;
	private String ownerName;
	private Date constructionDate;
	private Long status;
	private String statusName;
	private Long houseStationTypeId;
	private String houseStationTypeName;
	private Long stationTypeId;
	private String stationTypeName;
	private Long stationFeatureId;
	private String stationFeatureName;
	private Long backupStatus;
	private String backupStatusName;
	private Long position;
	private String positionName;
	private BigDecimal length;
	private BigDecimal width;
	private BigDecimal height;
	private BigDecimal heightestBuilding;
	// private Point geometry;
	private Long auditType;
	private String auditTypeName;
	private Long auditStatus;
	private String auditStatusName;
	private String note;
	private Long rowStatus;
	private Date createTime;
	private Double longitude;
	private Double latitude;

	private String fileCheck;
	private String fileListed;

	@Id
	@Column(name = "STATION_ID")
	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	@Basic
	@Column(name = "STATION_CODE")
	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
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
	@Column(name = "DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Basic
	@Column(name = "PATH_NAME")
	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	@Basic
	@Column(name = "DEPT_CODE")

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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
	@Column(name = "LOCATION_NAME")
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Basic
	@Column(name = "PATH_LOCAL_NAME")
	public String getPathLocalName() {
		return pathLocalName;
	}

	public void setPathLocalName(String pathLocalName) {
		this.pathLocalName = pathLocalName;
	}

	@Basic
	@Column(name = "LOCATION_CODE")
	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	@Basic
	@Column(name = "TERRAIN")
	public Long getTerrain() {
		return terrain;
	}

	public void setTerrain(Long terrain) {
		this.terrain = terrain;
	}

	@Transient
	public String getTerrainName() {
		return terrainName;
	}

	public void setTerrainName(String terrainName) {
		this.terrainName = terrainName;
	}

	@Basic
	@Column(name = "HOUSE_OWNER_NAME")
	public String getHouseOwnerName() {
		return houseOwnerName;
	}

	public void setHouseOwnerName(String houseOwnerName) {
		this.houseOwnerName = houseOwnerName;
	}

	@Basic
	@Column(name = "HOUSE_OWNER_PHONE")
	public String getHouseOwnerPhone() {
		return houseOwnerPhone;
	}

	public void setHouseOwnerPhone(String houseOwnerPhone) {
		this.houseOwnerPhone = houseOwnerPhone;
	}

	@Basic
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	@Column(name = "OWNER_NAME")
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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
	@Column(name = "STATUS")
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Transient
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Basic
	@Column(name = "HOUSE_STATION_TYPE_ID")
	public Long getHouseStationTypeId() {
		return houseStationTypeId;
	}

	public void setHouseStationTypeId(Long houseStationTypeId) {
		this.houseStationTypeId = houseStationTypeId;
	}

	@Basic
	@Column(name = "HOUSE_STATION_TYPE_NAME")
	public String getHouseStationTypeName() {
		return houseStationTypeName;
	}

	public void setHouseStationTypeName(String houseStationTypeName) {
		this.houseStationTypeName = houseStationTypeName;
	}

	@Basic
	@Column(name = "STATION_TYPE_ID")
	public Long getStationTypeId() {
		return stationTypeId;
	}

	public void setStationTypeId(Long stationTypeId) {
		this.stationTypeId = stationTypeId;
	}

	@Basic
	@Column(name = "STATION_TYPE_NAME")
	public String getStationTypeName() {
		return stationTypeName;
	}

	public void setStationTypeName(String stationTypeName) {
		this.stationTypeName = stationTypeName;
	}

	@Basic
	@Column(name = "STATION_FEATURE_ID")
	public Long getStationFeatureId() {
		return stationFeatureId;
	}

	public void setStationFeatureId(Long stationFeatureId) {
		this.stationFeatureId = stationFeatureId;
	}

	@Basic
	@Column(name = "STATION_FEATURE_NAME")
	public String getStationFeatureName() {
		return stationFeatureName;
	}

	public void setStationFeatureName(String stationFeatureName) {
		this.stationFeatureName = stationFeatureName;
	}

	@Basic
	@Column(name = "BACKUP_STATUS")
	public Long getBackupStatus() {
		return backupStatus;
	}

	public void setBackupStatus(Long backupStatus) {
		this.backupStatus = backupStatus;
	}

	@Transient
	public String getBackupStatusName() {
		return backupStatusName;
	}

	public void setBackupStatusName(String backupStatusName) {
		this.backupStatusName = backupStatusName;
	}

	@Basic
	@Column(name = "POSITION")
	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	@Transient
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Basic
	@Column(name = "LENGTH")
	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	@Basic
	@Column(name = "WIDTH")
	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	@Basic
	@Column(name = "HEIGHT")
	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	@Basic
	@Column(name = "HEIGHTEST_BUILDING")
	public BigDecimal getHeightestBuilding() {
		return heightestBuilding;
	}

	public void setHeightestBuilding(BigDecimal heightestBuilding) {
		this.heightestBuilding = heightestBuilding;
	}

	// @Basic
	// @Column(name = "GEOMETRY", columnDefinition = "geometry(Point,4326)")
	// @JsonSerialize(using = GeometrySerializer.class)
	// @JsonDeserialize(contentUsing = GeometryDeserializer.class)
	// public Point getGeometry() {
	// return geometry;
	// }
	//
	// public void setGeometry(Point geometry) {
	// this.geometry = geometry;
	// }

	@Basic
	@Column(name = "AUDIT_TYPE")
	public Long getAuditType() {
		return auditType;
	}

	public void setAuditType(Long auditType) {
		this.auditType = auditType;
	}

	@Transient
	public String getAuditTypeName() {
		return auditTypeName;
	}

	public void setAuditTypeName(String auditTypeName) {
		this.auditTypeName = auditTypeName;
	}

	@Basic
	@Column(name = "AUDIT_STATUS")
	public Long getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Long auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Transient
	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

	@Basic
	@Column(name = "NOTE")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Basic
	@Column(name = "ROW_STATUS")
	public Long getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(Long rowStatus) {
		this.rowStatus = rowStatus;
	}

	@Basic
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	@Column(name = "FILE_KD")
	public String getFileCheck() {
		return fileCheck;
	}

	public void setFileCheck(String fileCheck) {
		this.fileCheck = fileCheck;
	}

	@Basic
	@Column(name = "FILE_NY")
	public String getFileListed() {
		return fileListed;
	}

	public void setFileListed(String fileListed) {
		this.fileListed = fileListed;
	}
}
