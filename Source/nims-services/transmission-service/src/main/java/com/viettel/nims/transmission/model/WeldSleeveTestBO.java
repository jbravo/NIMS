package com.viettel.nims.transmission.model;

import com.viettel.nims.commons.util.PaginationDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CNT_LINE_SLEEVE_LINE")
public class WeldSleeveTestBO extends PaginationDTO{

  private Long sleeveId;
  private Long sourceCableId;
  private Integer sourceLineNo;
  private Long destCableId;
  private Integer destLineNo;
  private Float attenuation;
  private Date createDate;
  private String createUser;
  private String note;
  private String basicInfo;

  @Id
  @Column(name = "SLEEVE_ID")
  public Long getSleeveId() {
    return sleeveId;
  }

  public void setSleeveId(Long sleeveId) {
    this.sleeveId = sleeveId;
  }

  @Column(name = "SOURCE_CABLE_ID")
  public Long getSourceCableId() {
    return sourceCableId;
  }

  public void setSourceCableId(Long sourceCableId) {
    this.sourceCableId = sourceCableId;
  }

  @Column(name = "SOURCE_LINE_NO")
  public Integer getSourceLineNo() {
    return sourceLineNo;
  }

  public void setSourceLineNo(Integer sourceLineNo) {
    this.sourceLineNo = sourceLineNo;
  }

  @Column(name = "DEST_CABLE_ID")
  public Long getDestCableId() {
    return destCableId;
  }

  public void setDestCableId(Long destCableId) {
    this.destCableId = destCableId;
  }

  @Column(name = "DEST_LINE_NO")
  public Integer getDestLineNo() {
    return destLineNo;
  }

  public void setDestLineNo(Integer destLineNo) {
    this.destLineNo = destLineNo;
  }

  @Column(name = "ATTENUATION")
  public Float getAttenuation() {
    return attenuation;
  }

  public void setAttenuation(Float attenuation) {
    this.attenuation = attenuation;
  }

  @Column(name = "CREATE_DATE")
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Column(name = "CREATE_USER")
  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  @Column(name = "NOTE")
  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @Transient
  public String getBasicInfo() {
    return basicInfo;
  }

  public void setBasicInfo(String basicInfo) {
    this.basicInfo = basicInfo;
  }
}
