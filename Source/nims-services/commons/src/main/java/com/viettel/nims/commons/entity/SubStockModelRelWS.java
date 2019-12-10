package com.viettel.nims.commons.entity;

import com.viettel.nims.commons.client.form.ComboForm;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Data;

@Data
@XmlType(name = "SubStockModelRelWS")
public class SubStockModelRelWS {

  private String stockModelName;
  private Long stockModelId;
  private String serial;
  private List<ComboForm> lstResultStock;
  private Long subGoodsId;
  private String reason;
  private String result;

  @XmlElement(name = "stockModelName")
  public void setStockModelName(String stockModelName) {
    this.stockModelName = stockModelName;
  }

  @XmlElement(name = "stockModelId")
  public void setStockModelId(Long stockModelId) {
    this.stockModelId = stockModelId;
  }

  @XmlElement(name = "serial")
  public void setSerial(String serial) {
    this.serial = serial;
  }

  @XmlElement(name = "lstResultStock")
  public void setLstResultStock(List<ComboForm> lstResultStock) {
    this.lstResultStock = lstResultStock;
  }

  @XmlElement(name = "subGoodsId")
  public void setSubGoodsId(Long subGoodsId) {
    this.subGoodsId = subGoodsId;
  }

  @XmlElement(name = "reason")
  public void setReason(String reason) {
    this.reason = reason;
  }

  @XmlElement(name = "result")
  public void setResult(String result) {
    this.result = result;
  }
}

