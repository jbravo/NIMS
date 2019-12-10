/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Admin
 */
@Data
public class SuggestionDTO implements Serializable {

  private Long suggestId;
  @NotNull(message = "suggestion.suggestType.required")
  private Integer suggestType;
//  @NotNull(message = "suggestion.suggestCode.required")
  private String suggestCode;
  @NotNull(message = "suggestion.deptId.required")
  private Long deptId;
  private Integer suggestStatus;
  @NotNull(message = "suggestion.userName.required")
  @Pattern(regexp = "^(?=.*\\S).*", message = "suggestion.userName.required")
  private String userName;
  private Date createTime;
  private Date beforeDate;
  private Date afterDate;
  private Date updateTime;
  private Integer rowStatus;
  @Valid
  private SuggestionNewSiteDTO suggestionNewSiteDTO;

  @Valid
  @Nullable
  private List<SuggestionCallOff2gDTO> suggestionCallOff2gDTOList = new ArrayList<>();
  @Valid
  @Nullable
  private List<SuggestionCallOff4gDTO> suggestionCallOff4gDTOList = new ArrayList<>();;
  @Valid
  @Nullable
  private List<SuggestionCallOff3gDTO> suggestionCallOff3gDTOList = new ArrayList<>();;
  @Valid
  @Nullable
  private List<SuggestionVerifyDTO> suggestionVerifyDTOList = new ArrayList<>();;
  @Valid
  @Nullable
  private List<SuggestionCallOffTransDTO> suggestionCallOffTransDTOList = new ArrayList<>();;
  @Valid
  @Nullable
  private List<SuggestionFileDTO> suggestionFileDTOList = new ArrayList<>();;

  public Long getSuggestId() {
    return suggestId;
  }

  public void setSuggestId(Long suggestId) {
    this.suggestId = suggestId;
  }

  public Integer getSuggestType() {
    return suggestType;
  }

  public void setSuggestType(Integer suggestType) {
    this.suggestType = suggestType;
  }

  public String getSuggestCode() {
    return suggestCode;
  }

  public void setSuggestCode(String suggestCode) {
    this.suggestCode = suggestCode;
  }

  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  public Integer getSuggestStatus() {
    return suggestStatus;
  }

  public void setSuggestStatus(Integer suggestStatus) {
    this.suggestStatus = suggestStatus;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getBeforeDate() {
    return beforeDate;
  }

  public void setBeforeDate(Date beforeDate) {
    this.beforeDate = beforeDate;
  }

  public Date getAfterDate() {
    return afterDate;
  }

  public void setAfterDate(Date afterDate) {
    this.afterDate = afterDate;
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

  public SuggestionNewSiteDTO getSuggestionNewSiteDTO() {
    return suggestionNewSiteDTO;
  }

  public void setSuggestionNewSiteDTO(SuggestionNewSiteDTO suggestionNewSiteDTO) {
    this.suggestionNewSiteDTO = suggestionNewSiteDTO;
  }

  @Nullable
  public List<SuggestionCallOff2gDTO> getSuggestionCallOff2gDTOList() {
    return suggestionCallOff2gDTOList;
  }

  public void setSuggestionCallOff2gDTOList(@Nullable List<SuggestionCallOff2gDTO> suggestionCallOff2gDTOList) {
    this.suggestionCallOff2gDTOList = suggestionCallOff2gDTOList;
  }

  @Nullable
  public List<SuggestionCallOff4gDTO> getSuggestionCallOff4gDTOList() {
    return suggestionCallOff4gDTOList;
  }

  public void setSuggestionCallOff4gDTOList(@Nullable List<SuggestionCallOff4gDTO> suggestionCallOff4gDTOList) {
    this.suggestionCallOff4gDTOList = suggestionCallOff4gDTOList;
  }

  @Nullable
  public List<SuggestionCallOff3gDTO> getSuggestionCallOff3gDTOList() {
    return suggestionCallOff3gDTOList;
  }

  public void setSuggestionCallOff3gDTOList(@Nullable List<SuggestionCallOff3gDTO> suggestionCallOff3gDTOList) {
    this.suggestionCallOff3gDTOList = suggestionCallOff3gDTOList;
  }

  @Nullable
  public List<SuggestionVerifyDTO> getSuggestionVerifyDTOList() {
    return suggestionVerifyDTOList;
  }

  public void setSuggestionVerifyDTOList(@Nullable List<SuggestionVerifyDTO> suggestionVerifyDTOList) {
    this.suggestionVerifyDTOList = suggestionVerifyDTOList;
  }

  @Nullable
  public List<SuggestionCallOffTransDTO> getSuggestionCallOffTransDTOList() {
    return suggestionCallOffTransDTOList;
  }

  public void setSuggestionCallOffTransDTOList(@Nullable List<SuggestionCallOffTransDTO> suggestionCallOffTransDTOList) {
    this.suggestionCallOffTransDTOList = suggestionCallOffTransDTOList;
  }

  @Nullable
  public List<SuggestionFileDTO> getSuggestionFileDTOList() {
    return suggestionFileDTOList;
  }

  public void setSuggestionFileDTOList(@Nullable List<SuggestionFileDTO> suggestionFileDTOList) {
    this.suggestionFileDTOList = suggestionFileDTOList;
  }
}
