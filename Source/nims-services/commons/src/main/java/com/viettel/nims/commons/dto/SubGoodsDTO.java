package com.viettel.nims.commons.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "subGoodsDTO",
    propOrder = {
        "actionCode",
        "createDatetime",
        "createUser",
        "description",
        "disableSerial",
        "imRequest",
        "partnerId",
        "partnerName",
        "price",
        "quantity",
        "reclaimAmount",
        "reclaimCommitmentCode",
        "reclaimCommitmentName",
        "reclaimCommitmentTime",
        "reclaimDatetime",
        "reclaimPayMethod",
        "reclaimPayMethodName",
        "reclaimProCode",
        "reclaimProCodeName",
        "saleServiceId",
        "saleTransId",
        "serial",
        "serialToRetrieve",
        "showSubgoodsDetail",
        "smartCode1",
        "smartCode2",
        "smartCode3",
        "sourceId",
        "status",
        "stockModelCode",
        "stockModelId",
        "stockModelName",
        "stockTypeId",
        "stockTypeName",
        "subGoodsId",
        "subId",
        "type",
        "updateDatetime",
        "updateUser",
        "vasCode",
        "gponSerial",
        "deviceType"
    }
)
public class SubGoodsDTO {

  protected String actionCode;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar createDatetime;
  protected String createUser;
  protected String description;
  protected boolean disableSerial;
  protected Long imRequest;
  protected Long partnerId;
  protected String partnerName;
  protected Long price;
  protected Long quantity;
  protected String reclaimAmount;
  protected String reclaimCommitmentCode;
  protected String reclaimCommitmentName;
  protected String reclaimCommitmentTime;
  protected String reclaimDatetime;
  protected String reclaimPayMethod;
  protected String reclaimPayMethodName;
  protected String reclaimProCode;
  protected String reclaimProCodeName;
  protected Long saleServiceId;
  protected Long saleTransId;
  protected String serial;
  protected String serialToRetrieve;
  protected boolean showSubgoodsDetail;
  protected String smartCode1;
  protected String smartCode2;
  protected String smartCode3;
  protected Long sourceId;
  protected String status;
  protected String stockModelCode;
  protected Long stockModelId;
  protected String stockModelName;
  protected Long stockTypeId;
  protected String stockTypeName;
  protected Long subGoodsId;
  protected Long subId;
  protected String type;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar updateDatetime;
  protected String updateUser;
  protected String vasCode;
  protected String gponSerial;
  protected String deviceType;
}
