package com.viettel.nims.commons.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
@XmlType(name = "problemDTO", propOrder = {
    "agentNote",
    "areaCode",
    "arise",
    "asVip",
    "assignStatus",
    "callChannelId",
    "causeId",
    "causeLV1",
    "causeLV2",
    "causeLV3",
    "cellId",
    "changeCusFreeTimePeriod",
    "channelType",
    "close",
    "comQuantity",
    "complainerAddress",
    "complainerEmail",
    "complainerIdNo",
    "complainerName",
    "complainerPhone",
    "contactNumber",
    "cooperateStatus",
    "cooperateType",
    "cooperationEndDate",
    "cooperationExpDate",
    "createDate",
    "cusFreeTimePeriod",
    "custAppointDate",
    "custId",
    "custLimitDate",
    "custLimitDateBeforeCP",
    "custName",
    "customerText",
    "customerTimeDesireFrom",
    "customerTimeDesireTo",
    "delProblem",
    "district",
    "downloadSpeed",
    "duplicateId",
    "endDate",
    "endUser",
    "errorAlertDetail",
    "errorAlertId",
    "errorCode",
    "forMVT",
    "includeCall",
    "inputSerial",
    "isArise",
    "isCloseSpm",
    "isNotPassQuantity",
    "isOverdue",
    "isdn",
    "lac",
    "lastProcessTime",
    "lastShopId",
    "lastUser",
    "latPosition",
    "lockDate",
    "longPosition",
    "myViettelAcc",
    "networkType",
    "note",
    "numContactCust",
    "objectId",
    "oneSerial",
    "otherOperator",
    "parentGroupId",
    "parentGroupName",
    "parentProblemId",
    "preResult",
    "precinct",
    "probAcceptTypeId",
    "probChannelCode",
    "probChannelId",
    "probChannelName",
    "probGroupId",
    "probGroupName",
    "probPriorityId",
    "probTypeId",
    "probTypeName",
    "problemAttCode",
    "problemContent",
    "problemContentDuplicate",
    "problemId",
    "problemLevelId",
    "processResultContent",
    "processingNote",
    "processingUser",
    "province",
    "reCompNumber",
    "reasonNotPassQuantity",
    "responsiblePartyId",
    "resultContent",
    "resultId",
    "returnReason",
    "returnReasonText",
    "returnStatus",
    "returnStatusUi",
    "satLevelId",
    "satisfiedLevelId",
    "serial",
    "shopAcceptId",
    "shopProcessId",
    "signalStrength",
    "staffName",
    "staffPhoneNumber",
    "startProcessingDate",
    "status",
    "statusText",
    "subId",
    "subProductId",
    "suspendTime",
    "telecomServiceId",
    "uploadSpeed",
    "userAccept",
    "userOtherSystem",
    "userProcess"
})
public class ProblemDTO {

  protected String agentNote;
  protected String areaCode;
  protected Short arise;
  protected boolean asVip;
  protected Short assignStatus;
  protected Long callChannelId;
  protected Long causeId;
  protected Long causeLV1;
  protected Long causeLV2;
  protected Long causeLV3;
  protected String cellId;
  protected boolean changeCusFreeTimePeriod;
  protected Long channelType;
  protected boolean close;
  protected Long comQuantity;
  protected String complainerAddress;
  protected String complainerEmail;
  protected String complainerIdNo;
  protected String complainerName;
  protected String complainerPhone;
  protected String contactNumber;
  protected Long cooperateStatus;
  protected Long cooperateType;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar cooperationEndDate;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar cooperationExpDate;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar createDate;
  protected String cusFreeTimePeriod;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar custAppointDate;
  protected Long custId;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar custLimitDate;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar custLimitDateBeforeCP;
  protected String custName;
  protected String customerText;
  protected String customerTimeDesireFrom;
  protected String customerTimeDesireTo;
  protected boolean delProblem;
  protected String district;
  protected String downloadSpeed;
  protected Long duplicateId;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar endDate;
  protected String endUser;
  protected String errorAlertDetail;
  protected Long errorAlertId;
  protected String errorCode;
  protected boolean forMVT;
  protected boolean includeCall;
  protected boolean inputSerial;
  protected boolean isArise;
  protected boolean isCloseSpm;
  protected Boolean isNotPassQuantity;
  protected Integer isOverdue;
  protected String isdn;
  protected String lac;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar lastProcessTime;
  protected Long lastShopId;
  protected String lastUser;
  protected String latPosition;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar lockDate;
  protected String longPosition;
  protected String myViettelAcc;
  protected String networkType;
  protected String note;
  protected Integer numContactCust;
  protected Long objectId;
  protected boolean oneSerial;
  protected boolean otherOperator;
  protected Long parentGroupId;
  protected String parentGroupName;
  protected Long parentProblemId;
  protected String preResult;
  protected String precinct;
  protected Long probAcceptTypeId;
  protected String probChannelCode;
  protected Long probChannelId;
  protected String probChannelName;
  protected Long probGroupId;
  protected String probGroupName;
  protected Long probPriorityId;
  protected Long probTypeId;
  protected String probTypeName;
  protected String problemAttCode;
  protected String problemContent;
  protected String problemContentDuplicate;
  protected Long problemId;
  protected Long problemLevelId;
  @XmlElement(nillable = true)
  protected String processResultContent;
  protected String processingNote;
  protected String processingUser;
  protected String province;
  protected Integer reCompNumber;
  protected String reasonNotPassQuantity;
  protected Long responsiblePartyId;
  protected String resultContent;
  protected Long resultId;
  protected Short returnReason;
  protected String returnReasonText;
  protected Short returnStatus;
  protected boolean returnStatusUi;
  protected Long satLevelId;
  protected Long satisfiedLevelId;
  protected String serial;
  protected Long shopAcceptId;
  protected Long shopProcessId;
  protected String signalStrength;
  protected String staffName;
  protected String staffPhoneNumber;
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar startProcessingDate;
  protected short status;
  protected String statusText;
  protected Long subId;
  protected Long subProductId;
  protected Double suspendTime;
  protected String telecomServiceId;
  protected String uploadSpeed;
  protected String userAccept;
  protected String userOtherSystem;
  protected String userProcess;
}
