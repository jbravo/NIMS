package com.viettel.nims.transmission.model.view;

import javax.persistence.*;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 * Updated by HungNV on 9/9/2019
 */
@Entity
@Table(name = "view_cat_location")
public class ViewCatLocationBO {

	private Long locationId;

	private String locationCode;

	private String locationName;

	private Long parentId;

	private String pathLocalId;

	private String pathLocalName;

	private Long terrain;

	@Id
	@Column(name = "LOCATION_ID")
	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
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
	@Column(name = "LOCATION_NAME")
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Basic
	@Column(name = "PARENT_ID")
	public Long getParentId() {
	return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Basic
	@Column(name = "PATH_LOCAL_ID")
	public String getPathLocalId() {
	return pathLocalId;
	}

	public void setPathLocalId(String pathLocalId) {
	this.pathLocalId = pathLocalId;
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
  @Column(name = "TERRAIN")
  public Long getTerrain() {
    return terrain;
  }

  public void setTerrain(Long terrain) {
    this.terrain = terrain;
  }
}
