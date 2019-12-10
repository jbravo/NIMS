package com.viettel.nims.commons.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockGoodSerialBccsImBO {

  private String fromSerial;
  private String quantity;
  private String toSerial;
}
