package com.viettel.nims.commons.client.form;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationForm {

  private String stationCode;
  private String unitId;
  private String employeeId;
  private String employeeFullList;
  private String employeeList;
  private Long stationId;
  private String locationId;
  private String assignType;
  private String listStationId;
  private Date fromDate;
  private Date toDate;
  private String stateDetail;
  private Date issueDate;
  private Long stationStateId;
  private String cabinet;
  private Long elecmachenicalAc;
  private Long elecmachenicalAccqui;
  private Long elecmachenicalPlantExp;
  private Long backupLane;
  private Long isPstn;
  private Long isAdsl;
  private Long transDeviceTypeId;
  private Long transQuantity;
  private Long terrainId;
  private Long metroDeviceTypeId;
  private Long metroQuantity;
  private Long approveStatus;
  private Long checkManager;
  private Long groupId;
  private String groupCode;
  private String groupName;
  private Long userId;
  private String email;
  private String deployCode;
  private String username;
  private Long haveGroup;
  private Long statusUser;
  private Long statusGroup;

  public StationForm(String stationCode) {
    this.stationCode = stationCode;
  }
}
