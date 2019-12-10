package com.viettel.nims.transmission.model.view;

import javax.persistence.*;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
@Entity
@Table(name = "view_cat_item")
public class ViewCatItemBO {
	private Long itemId;
	private String itemCode;
	private String itemName;
	private String categoryCode;
	private String categoryName;
	private String languageCode;

	@Id
	@Column(name = "ITEM_ID")
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@Basic
	@Column(name = "ITEM_NAME")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Basic
	@Column(name = "CATEGORY_CODE")
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Basic
	@Column(name = "LANGUAGE_CODE")
	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	@Basic
	@Column(name = "ITEM_CODE")
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Basic
	@Column(name = "CATEGORY_NAME")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
