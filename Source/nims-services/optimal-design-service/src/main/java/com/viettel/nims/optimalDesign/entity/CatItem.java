/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import javax.persistence.*;



/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CAT_ITEM")
public class CatItem implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ITEM_ID")
    @SequenceGenerator(name = "catItemsGenerator", sequenceName = "CAT_ITEM_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catItemsGenerator")
    private Long itemId;
    @Basic(optional = false)
    @Column(name = "ITEM_CODE")
    private String itemCode;
    @Basic(optional = false)
    @Column(name = "ITEM_NAME")
    private String itemName;
    @Column(name = "NOTE")
    private String note;
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @Column(name = "ROW_STATUS")
    private Integer rowStatus;

    @Column(name = "CATEGORY_ID")
    private Long catCategoryId;

    public CatItem() {
    }

    public CatItem(Long itemId) {
        this.itemId = itemId;
    }

    public CatItem(Long itemId, String itemCode, String itemName) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

  @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatItem)) {
            return false;
        }
        CatItem other = (CatItem) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.CatItem[ itemId=" + itemId + " ]";
    }

}
