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
@Table(name = "VOFFICE_SIGN_LOG")
public class VofficeSignLog implements Serializable {

  private static final long serialVersionUID = 1L;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Id
  @Basic(optional = false)
  @Column(name = "ID")
  @SequenceGenerator(name = "vofficeSignLogGenerator", sequenceName = "VOFFICE_SIGN_LOG_SEQ")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vofficeSignLogGenerator")
  private Long id;
  @Column(name = "USER_CREATE")
  private String userCreate;
  @Column(name = "ACTION")
  private Integer action;
  @Column(name = "NOTE")
  private String note;
  @JoinColumn(name = "VOFFICE_SIGN_ID", referencedColumnName = "VOFFICE_SIGN_ID")
  @ManyToOne(optional = false)
  private VofficeSign vofficeSignId;

  public VofficeSignLog() {
  }

  public VofficeSignLog(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserCreate() {
    return userCreate;
  }

  public void setUserCreate(String userCreate) {
    this.userCreate = userCreate;
  }

  public Integer getAction() {
    return action;
  }

  public void setAction(Integer action) {
    this.action = action;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public VofficeSign getVofficeSignId() {
    return vofficeSignId;
  }

  public void setVofficeSignId(VofficeSign vofficeSignId) {
    this.vofficeSignId = vofficeSignId;
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
    if (!(object instanceof VofficeSignLog)) {
      return false;
    }
    VofficeSignLog other = (VofficeSignLog) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Model.VofficeSignLog[ id=" + id + " ]";
  }

}
