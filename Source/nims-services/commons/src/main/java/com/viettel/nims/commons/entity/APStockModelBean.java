package com.viettel.nims.commons.entity;

import com.viettel.nims.commons.client.form.DeviceSerialForm;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlType(name = "APStockModelBean")
public class APStockModelBean {

  private List<DeviceSerialForm> lstDeviceSerial;
  private Long bonusLimit;
  private Long catTaskReasonId;
  private Long price;
  private Long quantity;
  private Long sourceId;
  private Long stockModelId;
  private Long stockModelType;
  private Long stockTypeId;
  private Long subStockModelRelId;
  private Long taskId;
  private Long warningLimit;
  private String cableLengthEnd;
  private String cableLengthFirst;
  private String chiphi;
  private String contractCode;
  private String cost;
  private String groupCode;
  private String groupName;
  private String merchandiseId;
  private String quantityFee;
  private String saleServicesDetailId;
  private String serial;
  private String serialWarranty;
  private String stockModelCode;
  private String stockModelName;
  private String stockTypeForService;
  private String stockTypeName;
  private String stockTypeTempCode;
  private String totalFeeBonus;
  private String unitName;

  public APStockModelBean(Long stockTypeId, Long stockModelId) {
    this.stockTypeId = stockTypeId;
    this.stockModelId = stockModelId;
  }

  public APStockModelBean(Long stockModelId, String stockModelName, String serial, Long quantity,
      Long subStockModelRelId, String subStockCode, Long catTaskReasonId, String groupCode,
      String groupName,
      String cableLengthFirst, String cableLengthEnd) {
    this.stockModelId = stockModelId;
    this.stockModelName = stockModelName;
    this.serial = serial;
    this.quantity = quantity;
    this.subStockModelRelId = subStockModelRelId;
    this.stockModelCode = subStockCode;
    this.catTaskReasonId = catTaskReasonId;
    this.groupCode = groupCode;
    this.groupName = groupName;
    this.cableLengthFirst = cableLengthFirst;
    this.cableLengthEnd = cableLengthEnd;
  }

  public APStockModelBean(Long stockModelId, String stockModelName, String serial, Long quantity,
      Long subStockModelRelId) {
    this.stockModelId = stockModelId;
    this.stockModelName = stockModelName;
    this.serial = serial;
    this.quantity = quantity;
    this.subStockModelRelId = subStockModelRelId;
  }

  public APStockModelBean(SubStockModelRel tmp) {
    this.stockModelId = tmp.getStockModelId();
    this.serial = tmp.getSerial();
    this.subStockModelRelId = tmp.getSubStockModelRelId();
    this.stockModelName = tmp.getStockModelName();
    this.stockTypeName = tmp.getStockModelName();
  }

  public APStockModelBean(Long stockTypeId, Long stockModelId, String _saleServicesDetailId) {
    this.stockTypeId = stockTypeId;
    this.stockModelId = stockModelId;
    this.saleServicesDetailId = _saleServicesDetailId;
  }
}
