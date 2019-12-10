package com.viettel.nims.commons.client.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateForm {

  private double lat;
  private double lng;
  private double[] coordinate = new double[2];

  public CoordinateForm(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
  }
}
