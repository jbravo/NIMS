package com.viettel.nims.commons.dto;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "SubInfrastructureDTO")
public class SubInfrastructureDTO {

  private Long sourceId;
  private String address;
}
