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
    name = "stockTotalFullDTO",
    propOrder = {
        "accountModelCode",
        "accountModelName",
        "availableQuantity",
        "checkSerial",
        "currentQuantity",
        "district",
        "idNo",
        "isStaff",
        "modifiedDate",
        "ownerId",
        "ownerType",
        "prodOfferCode",
        "prodOfferId",
        "prodOfferName",
        "productOfferTypeId",
        "province",
        "shop",
        "shopCode",
        "shopName",
        "staffCode",
        "staffCode1",
        "staffName",
        "staffOwnerId",
        "stateId",
        "stateName",
        "status",
        "tel",
        "unit"
    }
)
public class StockTotalFullDTO {

  protected String accountModelCode;
  protected String accountModelName;
  protected Long availableQuantity;
  protected Long checkSerial;
  protected Long currentQuantity;
  protected String district;
  protected String idNo;
  protected Long isStaff;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar modifiedDate;
  protected Long ownerId;
  protected Long ownerType;
  protected String prodOfferCode;
  protected Long prodOfferId;
  protected String prodOfferName;
  protected Long productOfferTypeId;
  protected String province;
  protected String shop;
  protected String shopCode;
  protected String shopName;
  protected String staffCode;
  protected String staffCode1;
  protected String staffName;
  protected Long staffOwnerId;
  protected Long stateId;
  protected String stateName;
  protected Long status;
  protected String tel;
  protected String unit;
}
