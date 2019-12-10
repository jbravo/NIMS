package com.viettel.nims.optimalDesign.dto.commons;

/**
 * @author rabbit on 8/29/2019.
 */
public class ItemComboboxDTO {
  private String itemCode;
  private String itemName;
  private Long itemId;

  public ItemComboboxDTO() {
  }

  public ItemComboboxDTO(String itemCode, String itemName, Long itemId) {
    this.itemCode = itemCode;
    this.itemName = itemName;
    this.itemId = itemId;
  }

  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
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
