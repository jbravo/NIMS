package com.viettel.nims.radio.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.viettel.nims.radio.utils.CustomerDateAndTimeDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionLogBO implements Serializable {

  private Long id;
  private String userName;
  private String ipAddress;
  private Long stationId;
  private String stationCode;
  private String provinceCode;
  private String provinceName;
  private String areaCode;
  private String areaName;
  private String category;
  private Long actionType;
  private Long methodType;
  private String contentOld;
  private String contentNew;
  private String description;
  @JsonDeserialize(using= CustomerDateAndTimeDeserialize.class)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
  private Long categoryType;
  @Transient
  private String provinceNameReplace;

  public ActionLogBO(String userName, String ipAddress, Long stationId, String category, Long actionType, Long methodType, String contentOld, String contentNew, Long categoryType) {
    this.userName = userName;
    this.ipAddress = ipAddress;
    this.stationId = stationId;
    this.category = category;
    this.actionType = actionType;
    this.methodType = methodType;
    this.contentOld = contentOld;
    this.contentNew = contentNew;
    this.categoryType = categoryType;
  }

  //<editor-fold desc="Constant for ActionLog">
  public static final Long ACTION_INSERT = 1L;          // Action Thêm
  public static final Long ACTION_UPDATE = 2L;          // Action Sửa
  public static final Long ACTION_DELETE = 3L;          // Action Xóa
  public static final Long ACTION_IMPORT = 4L;          // Action Import

  public static final Long METHOD_WEB = 1L;             // Lấy từ Web
  public static final Long METHOD_VSMART = 2L;          // Lấy từ VS Mart

  public static final Long IMPORT_ELECTRIC_NETWORK = 1L;
  public static final Long IMPORT_DCPOWER = 2L;
  public static final Long IMPORT_ACCU = 3L;
  public static final Long IMPORT_VOL = 4L;
  public static final Long IMPORT_VENTILATION = 5L;
  public static final Long IMPORT_SURGEARRESTER = 6L;
  public static final Long IMPORT_METER = 7L;
  public static final Long IMPORT_SOLARPANEL = 8L;
  public static final Long IMPORT_ALARMSYS = 9L;
  public static final Long IMPORT_GENERATORHOUSE = 10L;
  public static final Long IMPORT_EARTHINGLEADSYS = 11L;
  public static final Long IMPORT_CONDITIONER = 12L;
  public static final Long IMPORT_CAMERA = 13L;
  public static final Long IMPORT_ATS = 14L;
  public static final Long IMPORT_ACPOWER = 15L;
  public static final Long IMPORT_FE = 16L;

  public static final Long PROVINCE_MECHANICAL_EEE_ELECTRICNETWORK = 100L;
  public static final Long PROVINCE_MECHANICAL_EEE_ACPOWER = 101L;
  public static final Long PROVINCE_MECHANICAL_EEE_DCPOWER = 102L;
  public static final Long PROVINCE_MECHANICAL_EEE_CONDITIONER = 103L;
  public static final Long PROVINCE_MECHANICAL_EEE_GENERATOR = 104L;
  public static final Long PROVINCE_MECHANICAL_EEE_ACCU = 105L;
  public static final Long PROVINCE_MECHANICAL_EEE_GROUPACCU = 106L;
  public static final Long PROVINCE_MECHANICAL_EEE_VOLTAGE = 107L;
  public static final Long PROVINCE_MECHANICAL_EEE_FIREEXTINGUISHER = 108L;
  public static final Long PROVINCE_MECHANICAL_EEE_ALARMSYSTEM = 109L;
  public static final Long PROVINCE_MECHANICAL_EEE_ATSTIMERGSDK = 110L;
  public static final Long PROVINCE_MECHANICAL_EEE_GENERATORHOUSE = 111L;
  public static final Long PROVINCE_MECHANICAL_EEE_SURGEARRESTER = 112L;
  public static final Long PROVINCE_MECHANICAL_EEE_BATTERYSUN = 113L;
  public static final Long PROVINCE_MECHANICAL_EEE_EARTHINGLEADSYSTEM = 114L;
  public static final Long PROVINCE_MECHANICAL_EEE_GENMETER = 115L;
  public static final Long PROVINCE_MECHANICAL_EEE_CAMERA = 116L;
  public static final Long PROVINCE_MECHANICAL_EEE_VENTILATIONDEVICE = 117L;
  public static final Long PROVINCE_MECHANICAL_EEE_DEVICEIP = 118L;
  public static final Long PROVINCE_MECHANICAL_EEE_DEVICEPSTN = 119L;
  public static final Long PROVINCE_MECHANICAL_EEE_DEVICECDBR = 120L;
  public static final Long PROVINCE_MECHANICAL_EEE_TVDEVICE = 121L;
  public static final Long PROVINCE_MECHANICAL_EEE_DEVICETRANS = 122L;
  public static final Long PROVINCE_MECHANICAL_EEE_DEVICEMAP = 123L;

  public static final Long GENERATOR_MERCHANDISE_GENERATOR = 200L;
  public static final Long GENERATOR_GENERATOR = 201L;
  public static final Long GENERATOR_MAINTAIN_GENERATOR = 202L;

  public static final Long MANAGECATEGORY_STATION = 300L;
  public static final Long MANAGECATEGORY_USER = 301L;
  public static final Long MANAGECATEGORY_UNIT = 302L;
  public static final Long MANAGECATEGORY_KTTS = 303L;
  public static final Long MANAGECATEGORY_NIMS = 304L;
  public static final Long MANAGECATEGORY_VOLTAGE = 305L;
  public static final Long MANAGECATEGORY_BATTERY = 306L;
  public static final Long MANAGECATEGORY_ATS = 307L;
  public static final Long MANAGECATEGORY_CONDITIONER = 308L;
  public static final Long MANAGECATEGORY_DCPOWER = 309L;
  public static final Long MANAGECATEGORY_ACCU = 310L;
  public static final Long MANAGECATEGORY_MANAGEDOCUMENT = 311L;
  public static final Long MANAGECATEGORY_MANAGEUNIT = 312L;
  public static final Long MANAGECATEGORY_IP = 313L;
  public static final Long MANAGECATEGORY_PSTN = 314L;
  public static final Long MANAGECATEGORY_GPON = 315L;
  public static final Long MANAGECATEGORY_TV = 316L;
  public static final Long MANAGECATEGORY_TRANS_DEVICE = 317L;
  public static final Long MANAGECATEGORY_2G = 318L;
  public static final Long MANAGECATEGORY_3G = 319L;
  public static final Long MANAGECATEGORY_4G = 320L;
  public static final Long MANAGECATEGORY_VENTILATION_CONDITIONERDC = 321L;
  public static final Long MANAGECATEGORY_NOTICE = 322L;
  public static final Long MANAGECATEGORY_RRU = 323L;
  public static final Long MANAGECATEGORY_IPTV_MULTISCREEN = 324L;
  public static final Long MANAGECATEGORY_VIBA = 325L;
  public static final Long MANAGECATEGORY_VSAT = 326L;

  public static final Long COMPLEXSTATION_SUBSTATION = 400L;
  public static final Long COMPLEXSTATION_GENERATOR = 401L;
  public static final Long COMPLEXSTATION_ATS = 402L;
  public static final Long COMPLEXSTATION_AC = 403L;
  public static final Long COMPLEXSTATION_EARTHINGLEADSYS = 404L;
  public static final Long COMPLEXSTATION_DC = 405L;
  public static final Long COMPLEXSTATION_CONDITIONER = 406L;
  public static final Long COMPLEXSTATION_UPS = 407L;
  public static final Long COMPLEXSTATION_POWER = 408L;
  public static final Long COMPLEXSTATION_WARING = 409L;
  public static final Long COMPLEXSTATION_FAKEFLOOR = 410L;
  public static final Long COMPLEXSTATION_FUEL = 411L;
  public static final Long COMPLEXSTATION_RESERVERSUPPLIES = 412L;
  public static final Long COMPLEXSTATION_FIREWARINGSYS = 413L;
  public static final Long COMPLEXSTATION_SOURCEFOREDEVICE = 414L;

  public static final Long REPAIR_CONFIG_DEVICE_TYPE = 500L;
  public static final Long REPAIR_REASON_FAILTURE = 501L;
  public static final Long REPAIR_HISTORY = 502L;

  public static final Long CONFIGSYSTEM_CONTENT_MAINTAIN = 600L;
  public static final Long CONFIGSYSTEM_POSITION_PUT = 601L;

  public static final Long SYNC_SERIAL_KTTS_DC_CONDITIONER_ACCU = 701L;

  //</editor-fold>
}
