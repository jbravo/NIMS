package com.viettel.nims.commons.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlType(name = "CheckAccountResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class CheckAccountResult {

  @XmlElement(name = "responseCode")
  private String responseCode;

  @XmlElement(name = "center")
  private String center;

  @XmlElement(name = "productId")
  private Long productId;

  @XmlElement(name = "productCode")
  private String productCode;
}
