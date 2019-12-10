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
    name = "stockHandset",
    propOrder = {
        "createUser",
        "id",
        "ownerId",
        "ownerType",
        "prodOfferId",
        "serial",
        "serialGpon",
        "stateId",
        "status"
    }
)
public class StockHandset {

  private String createUser;
  private String id;
  private String ownerId;
  private String ownerType;
  private String prodOfferId;
  private String serial;
  private String serialGpon;
  private String stateId;
  private String status;
}
