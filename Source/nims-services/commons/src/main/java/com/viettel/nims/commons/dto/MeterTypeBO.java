package com.viettel.nims.commons.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "meterType",
    propOrder = {
        "name",
        "optionSetId",
        "optionSetValueId",
        "value"
    }
)
public class MeterTypeBO {

  protected String name;
  protected String optionSetId;
  protected String optionSetValueId;
  protected String value;
}
