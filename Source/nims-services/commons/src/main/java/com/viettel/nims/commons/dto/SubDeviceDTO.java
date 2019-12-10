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
@XmlType(name = "subDeviceDTO", propOrder = {
    "account",
    "createDate",
    "imSerial",
    "status",
    "stockModelId",
    "subDeviceId",
    "subId",
    "tvmsCadId",
    "tvmsMacId",
    "type"
})
public class SubDeviceDTO {

  protected String account;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar createDate;
  protected String imSerial;
  protected Short status;
  protected Long stockModelId;
  protected Long subDeviceId;
  protected Long subId;
  protected String tvmsCadId;
  protected String tvmsMacId;
  protected String type;
}
