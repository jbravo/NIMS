package com.viettel.nims.commons.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockGoodsBccsImBO {

  private Long availableQuantity;
  private Long checkSerial;
  private String code;
  private Long currentQuantity;
  private String name;
  private Long ownerId;
  private Long ownerType;
  private Long prodOfferId;
  private Long prodOfferTypeId;
  private Long stateId;
  private Long status;
  private String unit;
  private ArrayList<StockGoodSerialBccsImBO> objSerials;
}
