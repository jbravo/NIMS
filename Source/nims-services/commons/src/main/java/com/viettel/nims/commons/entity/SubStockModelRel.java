package com.viettel.nims.commons.entity;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlType(name = "SubStockModelRel")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubStockModelRel {

  private String stockModelName;
  private Long stockModelId;
  private String serial;
  private Date createdDate;
  private Long imRequest;
  private Long reclaimAmount;
  private String reclaimCommitmentCode;
  private Long reclaimDatetime;
  private String reclaimProCode;
  private Long status;
  private Long subId;
  private Long subStockModelRelId;
}
