package com.viettel.nims.transmission.model.view;

import com.viettel.nims.commons.util.PaginationDTO;

import javax.persistence.*;

@Entity
@Table(name = "view_tree_cat_location")
public class ViewTreeCatLocationBO extends PaginationDTO {

  private Long locationId;

  private String locationCode;

  private String locationName;

  private Long parentId;

  private String pathLocalId;

  private String pathLocalName;

  private Long deptId;

  private Integer isTree;

  @Id
  @Column(name = "LOCATION_ID")
  public Long getLocationId() {
    return locationId;
  }

  public void setLocationId(Long locationId) {
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
  @Column(name = "DEPT_ID")
  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  @Transient
  public Integer getIsTree() {
    return isTree;
  }

  public void setIsTree(Integer isTree) {
    this.isTree = isTree;
  }
}
