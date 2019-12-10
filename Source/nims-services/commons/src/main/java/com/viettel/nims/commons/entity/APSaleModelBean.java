package com.viettel.nims.commons.entity;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlType(name = "APSaleModelBean")
public class APSaleModelBean {

  private Long stockTypeId;
  private String stockTypeName;
  private Long saleServicesModelId;
  private ArrayList<APStockModelBean> lstStockModel;

  public APSaleModelBean(ArrayList<APStockModelBean> tmp) {
    this.lstStockModel = tmp;
    if (!tmp.isEmpty()) {
      this.stockTypeName = tmp.get(0).getStockTypeName();
    }
  }

}
