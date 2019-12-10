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
 * @author Admin
 */
@Entity
@Table(name = "SUGGESTION_SECTOR_3G")
public class SuggestionSector3g implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @Column(name = "SUGGESTION_SECTOR_3G_ID")
  @SequenceGenerator(name = "suggestionSector3gGenerator", sequenceName = "SUGGESTION_SECTOR_3G_SEQ")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suggestionSector3gGenerator")
  private Long suggestionSector3gId;
  @Column(name = "SECTOR_CODE")
  private String sectorCode;
  @Column(name = "SECTOR_CFG")
  private Integer sectorCfg;
  @Column(name = "IS_JOINT_SECTOR")
  private Integer isJointSector;
  @Column(name = "NUMBER_SPLITTER_PER_SECTOR")
  private Long numberSplitterPerSector;
  @Column(name = "JOINT_SECTOR_CODE")
  private String jointSectorCode;
  @Column(name = "IS_JOINT_RRU")
  private Integer isJointRru;
  @Column(name = "NUM_RRU_PER_SECTOR")
  private Long numRruPerSector;
  @Column(name = "INCREASE_CAPACITY_SECTOR_CODE")
  private String increaseCapacitySectorCode;
  @Column(name = "IS_JOINT_ANTENA")
  private Integer isJointAntena;
  @Column(name = "JOINT_ANTENA_SECTOR_CODE")
  private String jointAntenaSectorCode;
  @Column(name = "DIPLEXER_TYPE")
  private Long diplexerType;
  @Column(name = "NUM_DIPLEXER_PER_SECTOR")
  private Long numDiplexerPerSector;
  @Column(name = "ANTENA_HEIGHT")
  private Long antenaHeight;
  @Column(name = "ANTENA_HEIGHT_GROUND")
  private Long antenaHeightGround;
  @Column(name = "ANTENA_TYPE_ID")
  private Long antenaTypeId;
  @Column(name = "RRU_HEIGHT")
  private Long rruHeight;
  @Column(name = "LNG")
  private Float lng;
  @Column(name = "LAT")
  private Float lat;
  @Column(name = "AZIMUTH")
  private Long azimuth;
  @Column(name = "TILT_MECHAN")
  private Long tiltMechan;
  @Column(name = "TILT_ELEC")
  private Long tiltElec;
  @Column(name = "CREATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Column(name = "UPDATE_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateTime;
  @Column(name = "ROW_STATUS")
  private Integer rowStatus;

  @Column(name = "SUGGESTION_CALL_OFF_ID")
  private Long suggestionCallOffId;

  public SuggestionSector3g() {
  }

  public SuggestionSector3g(Long suggestionSector3gId) {
    this.suggestionSector3gId = suggestionSector3gId;
  }

  public Long getSuggestionSector3gId() {
    return suggestionSector3gId;
  }

  public void setSuggestionSector3gId(Long suggestionSector3gId) {
    this.suggestionSector3gId = suggestionSector3gId;
  }

  public String getSectorCode() {
    return sectorCode;
  }

  public void setSectorCode(String sectorCode) {
    this.sectorCode = sectorCode;
  }

  public Integer getSectorCfg() {
    return sectorCfg;
  }

  public void setSectorCfg(Integer sectorCfg) {
    this.sectorCfg = sectorCfg;
  }

  public Integer getIsJointSector() {
    return isJointSector;
  }

  public void setIsJointSector(Integer isJointSector) {
    this.isJointSector = isJointSector;
  }

  public Long getNumberSplitterPerSector() {
    return numberSplitterPerSector;
  }

  public void setNumberSplitterPerSector(Long numberSplitterPerSector) {
    this.numberSplitterPerSector = numberSplitterPerSector;
  }

  public String getJointSectorCode() {
    return jointSectorCode;
  }

  public void setJointSectorCode(String jointSectorCode) {
    this.jointSectorCode = jointSectorCode;
  }

  public Integer getIsJointRru() {
    return isJointRru;
  }

  public void setIsJointRru(Integer isJointRru) {
    this.isJointRru = isJointRru;
  }

  public Long getNumRruPerSector() {
    return numRruPerSector;
  }

  public void setNumRruPerSector(Long numRruPerSector) {
    this.numRruPerSector = numRruPerSector;
  }

  public String getIncreaseCapacitySectorCode() {
    return increaseCapacitySectorCode;
  }

  public void setIncreaseCapacitySectorCode(String increaseCapacitySectorCode) {
    this.increaseCapacitySectorCode = increaseCapacitySectorCode;
  }

  public Integer getIsJointAntena() {
    return isJointAntena;
  }

  public void setIsJointAntena(Integer isJointAntena) {
    this.isJointAntena = isJointAntena;
  }

  public String getJointAntenaSectorCode() {
    return jointAntenaSectorCode;
  }

  public void setJointAntenaSectorCode(String jointAntenaSectorCode) {
    this.jointAntenaSectorCode = jointAntenaSectorCode;
  }

  public Long getDiplexerType() {
    return diplexerType;
  }

  public void setDiplexerType(Long diplexerType) {
    this.diplexerType = diplexerType;
  }

  public Long getNumDiplexerPerSector() {
    return numDiplexerPerSector;
  }

  public void setNumDiplexerPerSector(Long numDiplexerPerSector) {
    this.numDiplexerPerSector = numDiplexerPerSector;
  }

  public Long getAntenaHeight() {
    return antenaHeight;
  }

  public void setAntenaHeight(Long antenaHeight) {
    this.antenaHeight = antenaHeight;
  }

  public Long getAntenaHeightGround() {
    return antenaHeightGround;
  }

  public void setAntenaHeightGround(Long antenaHeightGround) {
    this.antenaHeightGround = antenaHeightGround;
  }

  public Long getAntenaTypeId() {
    return antenaTypeId;
  }

  public void setAntenaTypeId(Long antenaTypeId) {
    this.antenaTypeId = antenaTypeId;
  }

  public Long getRruHeight() {
    return rruHeight;
  }

  public void setRruHeight(Long rruHeight) {
    this.rruHeight = rruHeight;
  }

  public Float getLng() {
    return lng;
  }

  public void setLng(Float lng) {
    this.lng = lng;
  }

  public Float getLat() {
    return lat;
  }

  public void setLat(Float lat) {
    this.lat = lat;
  }

  public Long getAzimuth() {
    return azimuth;
  }

  public void setAzimuth(Long azimuth) {
    this.azimuth = azimuth;
  }

  public Long getTiltMechan() {
    return tiltMechan;
  }

  public void setTiltMechan(Long tiltMechan) {
    this.tiltMechan = tiltMechan;
  }

  public Long getTiltElec() {
    return tiltElec;
  }

  public void setTiltElec(Long tiltElec) {
    this.tiltElec = tiltElec;
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

  public Long getSuggestionCallOffId() {
    return suggestionCallOffId;
  }

  public void setSuggestionCallOffId(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (suggestionSector3gId != null ? suggestionSector3gId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SuggestionSector3g)) {
      return false;
    }
    SuggestionSector3g other = (SuggestionSector3g) object;
    if ((this.suggestionSector3gId == null && other.suggestionSector3gId != null) || (this.suggestionSector3gId != null && !this.suggestionSector3gId.equals(other.suggestionSector3gId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Model.SuggestionSector3g[ suggestionSector3gId=" + suggestionSector3gId + " ]";
  }

}
