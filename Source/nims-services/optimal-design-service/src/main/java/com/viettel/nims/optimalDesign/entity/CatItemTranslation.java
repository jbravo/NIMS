/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.nims.optimalDesign.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CAT_ITEM_TRANSLATION")
public class CatItemTranslation implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @SequenceGenerator(name = "catItemTranslationsGenerator", sequenceName = "CAT_ITEM_TRANSLATION_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catItemTranslationsGenerator")
    private Long id;
    @Basic(optional = false)
    @Column(name = "LANGUAGE_CODE")
    private String languageCode;
    @Basic(optional = false)
    @Column(name = "ITEM_NAME")
    private String itemName;
    @Column(name = "ITEM_ID")
    private Long itemId;

    public CatItemTranslation() {
    }

    public CatItemTranslation(Long id) {
        this.id = id;
    }

    public CatItemTranslation(Long id, String languageCode, String itemName) {
        this.id = id;
        this.languageCode = languageCode;
        this.itemName = itemName;
    }

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

  @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatItemTranslation)) {
            return false;
        }
        CatItemTranslation other = (CatItemTranslation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.CatItemTranslation[ id=" + id + " ]";
    }
    
}
