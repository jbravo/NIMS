/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.*;
import java.io.Serializable;

import java.util.Date;

/**
 * @author Admin
 */
@Data
public class SuggestionSector2gDTO implements Serializable {


  private Long suggestionSector2gId;

  private String sectorCode;

  private Integer sectorCfg;

  private Integer isJointSector;

  private Long numberSplitterPerSector;

  private String jointSectorCode;

  private Integer isJointRru;

  private Long numRruPerSector;

  private String increaseCapacitySectorCode;

  private Integer isJointAntena;

  private String jointAntenaSectorCode;

  private Long diplexerType;

  private Long numDiplexerPerSector;

  private Long antenaHeight;

  private Long antenaHeightGround;

  private Long antenaTypeId;

  private Long rruHeight;

  private Float lng;

  private Float lat;

  private Long azimuth;

  private Long tiltMechan;

  private Long tiltElec;

  private Date createTime;

  private Date updateTime;

  private Integer rowStatus;

  private Long suggestionCallOffId;

  public Long getSuggestionSector2gId() {
    return suggestionSector2gId;
  }

  public void setSuggestionSector2gId(Long suggestionSector2gId) {
    this.suggestionSector2gId = suggestionSector2gId;
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
}
