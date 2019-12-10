/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;


/**
 * @author Admin
 */
@Data
public class SuggestionCallOffTransDTO implements Serializable {

  private Long suggestId;
  private Long suggestionCallOffId;
  private Integer transInterface;
  @Length(max = 100, message = "suggestionCallOffTrans.designer.length.max.100")
  private String designer;
  private String note;

  @Valid
  private SuggestionCallOffTransDeviceDTO suggestionCallOffTransDeviceDTO;

  @Valid
  private SuggestionCallOffTransCableLaneNewDTO suggestionCallOffTransCableLaneNewDTO;

  @Valid
  private SuggestionCallOffTransOldCableLaneDTO suggestionCallOffTransOldCableLaneDTO;

  public Long getSuggestId() {
    return suggestId;
  }

  public void setSuggestId(Long suggestId) {
    this.suggestId = suggestId;
  }

  public Long getSuggestionCallOffId() {
    return suggestionCallOffId;
  }

  public void setSuggestionCallOffId(Long suggestionCallOffId) {
    this.suggestionCallOffId = suggestionCallOffId;
  }

  public Integer getTransInterface() {
    return transInterface;
  }

  public void setTransInterface(Integer transInterface) {
    this.transInterface = transInterface;
  }

  public String getDesigner() {
    return designer;
  }

  public void setDesigner(String designer) {
    this.designer = designer;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public SuggestionCallOffTransDeviceDTO getSuggestionCallOffTransDeviceDTO() {
    return suggestionCallOffTransDeviceDTO;
  }

  public void setSuggestionCallOffTransDeviceDTO(SuggestionCallOffTransDeviceDTO suggestionCallOffTransDeviceDTO) {
    this.suggestionCallOffTransDeviceDTO = suggestionCallOffTransDeviceDTO;
  }

  public SuggestionCallOffTransCableLaneNewDTO getSuggestionCallOffTransCableLaneNewDTO() {
    return suggestionCallOffTransCableLaneNewDTO;
  }

  public void setSuggestionCallOffTransCableLaneNewDTO(SuggestionCallOffTransCableLaneNewDTO suggestionCallOffTransCableLaneNewDTO) {
    this.suggestionCallOffTransCableLaneNewDTO = suggestionCallOffTransCableLaneNewDTO;
  }

  public SuggestionCallOffTransOldCableLaneDTO getSuggestionCallOffTransOldCableLaneDTO() {
    return suggestionCallOffTransOldCableLaneDTO;
  }

  public void setSuggestionCallOffTransOldCableLaneDTO(SuggestionCallOffTransOldCableLaneDTO suggestionCallOffTransOldCableLaneDTO) {
    this.suggestionCallOffTransOldCableLaneDTO = suggestionCallOffTransOldCableLaneDTO;
  }
}
