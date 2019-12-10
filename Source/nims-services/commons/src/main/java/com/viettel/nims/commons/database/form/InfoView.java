package com.viettel.nims.commons.database.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoView {

  private String infoName;
  private String infoValue;
  private String infoValue1;

  public InfoView(String infoName, String infoValue) {
    this.infoName = infoName;
    this.infoValue = infoValue;
  }
}
