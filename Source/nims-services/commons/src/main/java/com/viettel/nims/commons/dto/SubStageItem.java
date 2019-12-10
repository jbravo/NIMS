package com.viettel.nims.commons.dto;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlType(name = "SubStageItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubStageItem {

  private String actionCode;
  private Date createDate;
  private Long id;
  private Long jsgId;
  private String jsgName;
  private Long quantity;
  private Long status;
  private Long subId;
}
