package com.viettel.nims.commons.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "CustomerDTO")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerDTO", propOrder = {"custId", "name"})
public class CustomerDTO {

  private Long custId;
  private String name;
}
