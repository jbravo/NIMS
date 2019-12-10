package com.viettel.nims.transmission.commom;

import java.util.Date;
import java.util.List;

public class TreeNodeDTO {

  private Long nodeId;
  private Long parentId;
  private Long locationId;
  private Long deptId;
  private String deptName;
  private String path;
  private String pathName;
  private String pathcode;
  private String locationName;
  private String pathLocalName;
  private String pathLocalId;
  private String data;
  private String label;
  private Date expiredDate;
  private String icon = "fa fa-folder-o";
  private String expandedIcon = "fa fa-folder-open";
  private String collapsedIcon = "fa fa-folder";
  private Long referenceNum;
  private boolean leaf = true;
  private boolean expanded = false;
  private List<TreeNodeDTO> children;

  /**
   * @return the nodeId
   */
  public Long getNodeId() {
    return nodeId;
  }

  /**
   * @param nodeId the nodeId to set
   */
  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  /**
   * @return the data
   */
  public String getData() {
    return data;
  }

  /**
   * @param data the data to set
   */
  public void setData(String data) {
    this.data = data;
  }

  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @return the icon
   */
  public String getIcon() {
    return icon;
  }

  /**
   * @param icon the icon to set
   */
  public void setIcon(String icon) {
    this.icon = icon;
  }

  /**
   * @return the expandedIcon
   */
  public String getExpandedIcon() {
    return expandedIcon;
  }

  /**
   * @param expandedIcon the expandedIcon to set
   */
  public void setExpandedIcon(String expandedIcon) {
    this.expandedIcon = expandedIcon;
  }

  /**
   * @return the collapsedIcon
   */
  public String getCollapsedIcon() {
    return collapsedIcon;
  }

  /**
   * @param collapsedIcon the collapsedIcon to set
   */
  public void setCollapsedIcon(String collapsedIcon) {
    this.collapsedIcon = collapsedIcon;
  }

  /**
   * @return the referenceNum
   */
  public Long getReferenceNum() {
    return referenceNum;
  }

  /**
   * @param referenceNum the referenceNum to set
   */
  public void setReferenceNum(Long referenceNum) {
    this.referenceNum = referenceNum;
    if (CommonUtil.NVL(this.referenceNum) > 0L) {
      this.leaf = false;
    }
  }

  /**
   * @return the children
   */
  public List<TreeNodeDTO> getChildren() {
    return children;
  }

  /**
   * @param children the children to set
   */
  public void setChildren(List<TreeNodeDTO> children) {
    this.children = children;
  }

  /**
   * @return the expiredDate
   */
  public Date getExpiredDate() {
    return expiredDate;
  }

  /**
   * @param expiredDate the expiredDate to set
   */
  public void setExpiredDate(Date expiredDate) {
    this.expiredDate = expiredDate;
  }


  /**
   * @return the leaf
   */
  public boolean isLeaf() {
    return leaf;
  }

  /**
   * @param leaf the leaf to set
   */
  public void setLeaf(boolean leaf) {
    this.leaf = leaf;
  }


  /**
   * @return the expanded
   */
  public boolean isExpanded() {
    return expanded;
  }


  /**
   * @param expanded the expanded to set
   */
  public void setExpanded(boolean expanded) {
    this.expanded = expanded;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Long getLocationId() {
    return locationId;
  }

  public void setLocationId(Long locationId) {
    this.locationId = locationId;
  }

  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getPathName() {
    return pathName;
  }

  public void setPathName(String pathName) {
    this.pathName = pathName;
  }

  public String getPathcode() {
    return pathcode;
  }

  public void setPathcode(String pathcode) {
    this.pathcode = pathcode;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public String getPathLocalName() {
    return pathLocalName;
  }

  public void setPathLocalName(String pathLocalName) {
    this.pathLocalName = pathLocalName;
  }

  public String getPathLocalId() {
    return pathLocalId;
  }

  public void setPathLocalId(String pathLocalId) {
    this.pathLocalId = pathLocalId;
  }
}
