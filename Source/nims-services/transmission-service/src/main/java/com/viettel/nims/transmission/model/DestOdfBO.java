package com.viettel.nims.transmission.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
public class DestOdfBO {
  @Transient
  private Long odfId;

  @Transient
  private String odfCode;
}
