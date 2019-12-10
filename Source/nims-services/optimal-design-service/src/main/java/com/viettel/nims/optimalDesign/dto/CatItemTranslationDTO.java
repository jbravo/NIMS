/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.dto;

import com.viettel.nims.optimalDesign.entity.CatItem;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Admin
 */
@Data
public class CatItemTranslationDTO implements Serializable {
  private Long id;
  private String languageCode;
  private String itemName;
  private Long itemId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }
}
