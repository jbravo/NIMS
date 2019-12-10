package com.viettel.nims.transmission.model.view;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "view_weld_list")
public class ViewWeldListBO {
  private Integer sourceLineNo;
  private Integer destLineNo;
  private String createUser;
  private BigDecimal attenuation;
  private Long sourceCableId;
  private Long destCableId;
  private Long sleeveId;

  @Column(name = "SOURCE_LINE_NO")
  public Integer getSourceLineNo() {
    return sourceLineNo;
  }

  public void setSourceLineNo(Integer sourceLineNo) {
    this.sourceLineNo = sourceLineNo;
  }

  @Column(name = "DEST_LINE_NO")
  public Integer getDestLineNo() {
    return destLineNo;
  }

  public void setDestLineNo(Integer destLineNo) {
    this.destLineNo = destLineNo;
  }

  @Column(name = "CREATE_USER")
  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  @Column(name = "ATTENUATION")
  public BigDecimal getAttenuation() {
    return attenuation;
  }


  public void setAttenuation(BigDecimal attenuation) {
    this.attenuation = attenuation;
  }

  @Column(name = "SOURCE_CABLE_ID")
  public Long getSourceCableID() {
    return sourceCableId;
  }

  public void setSourceCableID(Long sourceCableID) {
    this.sourceCableId = sourceCableID;
  }

  @Column(name = "DEST_CABLE_ID")
  public Long getDestCableId() {
    return destCableId;
  }

  public void setDestCableId(Long destCableId) {
    this.destCableId = destCableId;
  }

  @Id
  @Column(name = "SLEEVE_ID")
  public Long getSleeveId() {
    return sleeveId;
  }

  public void setSleeveId(Long sleeveId) {
    this.sleeveId = sleeveId;
  }
}
