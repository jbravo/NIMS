/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Admin
 */
@Data
public class CatCategoryDTO implements Serializable {
  private Long categoryId;
  private String categoryCode;
  private String categoryName;
  private String note;
  private List<CatItemDTO> catItemList;

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public List<CatItemDTO> getCatItemList() {
    return catItemList;
  }

  public void setCatItemList(List<CatItemDTO> catItemList) {
    this.catItemList = catItemList;
  }
}
