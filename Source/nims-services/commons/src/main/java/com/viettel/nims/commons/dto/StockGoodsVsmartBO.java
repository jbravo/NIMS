package com.viettel.nims.commons.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockGoodsVsmartBO {

  private String stockModelCode;
  private String name;
  private Long quantity;
  private Long type;
  private String unit;
  private Long ownerId;
  private Long ownerType;
  private ArrayList<StockGoodSerialBccsImBO> objSerials;
}
