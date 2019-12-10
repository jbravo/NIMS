package com.viettel.nims.transmission.model.view;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
/**
 * Created by VAN-BA on 19/09/2019.
 */
@Data
@Entity
@Table(name = "view_hang_cable")
public class ViewHangCableBO {

  @Id
  @Column(name = "ID")
  private Long id;

  @Basic
  @Column(name = "HOLDER_ID")
  private Long holderId;

  @Basic
  @Column(name = "CABLE_ID")
  private Long cableId;

  @Basic
  @Column(name = "CABLE_CODE")
  private String cableCode;
}
