/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import javax.persistence.*;
import java.io.Serializable;



/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CAT_CATEGORY")
public class CatCategory implements Serializable {
//    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CATEGORY_ID")
    @SequenceGenerator(name = "catCategoriesGenerator", sequenceName = "CAT_CATEGORY_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catCategoriesGenerator")
    private Long categoryId;
    @Basic(optional = false)
    @Column(name = "CATEGORY_CODE")
    private String categoryCode;
    @Basic(optional = false)
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Column(name = "NOTE")
    private String note;

    public CatCategory() {
    }

    public CatCategory(Long categoryId) {
        this.categoryId = categoryId;
    }

    public CatCategory(Long categoryId, String categoryCode, String categoryName) {
        this.categoryId = categoryId;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatCategory)) {
            return false;
        }
        CatCategory other = (CatCategory) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.CatCategory[ categoryId=" + categoryId + " ]";
    }
    
}
