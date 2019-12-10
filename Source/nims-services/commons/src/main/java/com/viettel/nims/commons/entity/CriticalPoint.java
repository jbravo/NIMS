package com.viettel.nims.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriticalPoint {

  private String longtitude;
  private String latitude;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CriticalPoint1 {

    private String lng;
    private String lat;
    private Long cableId;

    public CriticalPoint1(Long cableId, String lat, String lng) {
      this.lng = lng;
      this.lat = lat;
      this.cableId = cableId;
    }
  }
}
