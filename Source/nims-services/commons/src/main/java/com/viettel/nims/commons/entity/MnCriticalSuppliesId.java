package com.viettel.nims.commons.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MnCriticalSuppliesId implements Serializable {

  private Long supplesId;
  private Long criticalPointId;
}
