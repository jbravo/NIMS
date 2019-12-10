package com.viettel.nims.commons.util;

import java.io.File;

public interface Constant {

  String FORWARD_GRID_DATA = "gridData";
  String FORWARD_PREPARE = "preparePage";
  String FORWARD_TREE_DATA = "treeData";
  String FORWARD_CHILDREN_DATA = "childrenData";
  String ATTR_AUTHEN = "authenticator";
  String CSV_EXTENSION = ".csv";
  String LOG_DIRECTORY = "log";
  int BUF_SIZE = 1024;
  String SESSION_USERBO = "userBO";
  String SESSION_UNITBO = "unitBO";
  String LIST_USER_UNDER_SESSION = "list_user_under_session";
  String HSDT_STATION_RECORD = "station_record";
  String HSDT_RIGHT_STATION_CODE = "[station_record]_Station_Code";
  int MAX_RESULT = 10000;
  long ONE_MINUTE = 60000;
  long ONE_HOUR = 3600000;
  long ONE_DAY = 86400000;
  long ONE_WEEK = 604800000;
  int JOB_CREATE_HOUR_START = 3;
  int JOB_SMS_HOUR_START = 7;
  int MAX_RESULT_EXCEL = 65000;
  String SCHEMAL_NIMS = "NIMS_FCN.";
  String WORKS_REQUIRED = "WORKS_REQUIRED";
  String FILE_UPLOAD = "../../../FileManager/";
  String CAT_WORK_ARISING = "CAT_WORK_ARISING";

  String FILE_REPORT = "../../../QLCTKT_REPORT/";
  String FILE_LOG_CLIENT = "../../logs/QLCTKT_LOGCLIENT/";
  String FILE_IMPORT = "../../../FILE_IMPORT/";
  String FILE_IMAGE = "/QLCTKT_IMAGES/";
  String FILE_DOWNLOAD = "../../../QLCTKT_DOWNLOAD/";
  Long UPDATE_POSITION = 1L;
  Long UPDATE_STATION = 2L;
  Long UPDATE_CABLE = 3L;
  String SESSION_VSA = "sessionForVSA";
  String FUNCTION_CHANNEL = "I";
  Long VALUE_SELECT = -1L;
  Long DECLARED = 1L;
  long TIME_OUT = 15;
  String USER_TOKEN = "vsaUserToken";
  String SESSION_TIMEOUT = "sessionTimeout";
  String USER_ASSIGN_ID = "USER_ASSIGN_ID";
  String FILE_EXTENSION_ALLOW = "jsp,pdf";
  int PORT_FTP = 21;
  String WELD_ODF_TYPE_0 = "welding.type";
  String WELD_ODF_TYPE_1 = "jointing.type";

  interface LEVEL_USER {

    Long COMPANY = 1L;
    Long AREA = 2L;
    Long PROVINCE = 3L;
    Long DISTRICT = 4L;
    Long VILLAGE = 5L;
  }

  interface ROW_STATUS {

    Integer ACTIVE = 1;
    Integer INACTIVE = 2;
  }
  interface ROLE_USER {

    Long GIAM_DOC = 1L;
    Long NHAN_VIEN = 0L;
  }

  interface EXPORT {

    String TEMPLATE_FILE_EXTENSION = ".xls";
    String TEMPLATE_FILE_EXTENSION_2007 = ".xlsx";
    String TEMPLATE_SOURCE_LOCATION = "/WEB-INF/jsp/template/";
    String TEMPLATE_SOURCE_LOCATION_EXPORT = "/WEB-INF/jsp/template/export/";
    String TEMPLATE_SOURCE_LOCATION_EXPORT_SCORING = "/WEB-INF/jsp/template/scoring/";
    String TEMPLATE_TOTAL_SCORE_REPORT = "scoring/Ban TONG DIEM CHI NHANH";
    String TEMPLATE_TOTAL_SCORE_REPORT_FILE_NAME = "Ban TONG DIEM CHI NHANH";
    String TEMPLATE_DIEMNHOM = "/WEB-INF/jsp/template/scoring/IMPORT_DIEMNHOM.xls";
    String TEMPLATE_DIEMTIEUCHI = "/WEB-INF/jsp/template/scoring/IMPORT_DIEMTIEUCHI_TKTU.xls";
    String TEMPALATE_DIEMTRU_BGD = "/WEB-INF/jsp/template/scoring/minusPoint.xls";
    String TEMPALATE_IMPORT_WORK_TO_DO = "/WEB-INF/jsp/template/workInfo/";
    String TEMPLATE_COMMON = "COMMON_EXPORT.xlsx";
    String REPORT_OUT = File.separator + ".." + File.separator + ".."
        + File.separator + ".." + File.separator + "report_out" + File.separator;
    String FILE_EXTENTION_07 = ".xlsx";
    String SHEET = "Sheet1";
    String TARGETMEASURE_REPORTFILE = "reportTargetMeasure_vi";
    String TARGETMEASURE_REPORTFILE_INFO = "reportTargetMeasureInfo_vi";
  }

  interface INOC {

    interface ADMIN {

      Long COLUMN_DISPLAY = 1L;
      Long COLUMN_HIDDEN = 0L;
      String DATE_TYPE = "Date";
      String STRING_TYPE = "Text";
      String NUMBER_TYPE = "Number";
      String DATETIME_TYPE = "Datetime";
      Long COMMON_CONDITION = 0L;
    }
  }

  interface COMPARE {

    Long GE = 1L;
    Long LE = 2L;
    Long GT = 3L;
    Long LT = 4L;
    Long EQ = 5L;
  }

  interface NATION {

    String VNM = "VNM";
    String VN = "1000";
  }

  interface WS_CODE {

    String SMS_GATEWAY = "sms_gateWay";
  }

  interface CAT_CRT_CODE {

    String PTHT_BTS_2G = "BTS_2G";
    String PTHT_BTS_3G = "BTS_3G";
    String PTHT_BTS_ACCEPTANCE = "BTS_ACCEPTANCE";
    String PTHT_CABLE_LANE = "CABLE_LANE";
    String PTHT_PILLAR_CABLE_ACCEPTANCE = "PILLAR_CABLE_ACCEPTANCE";
    String PTHT_PILLAR_NUM = "PILLAR_NUM";
    String PTHT_UNDER_GROUND_CABLE = "UNDER_GROUND_CABLE";
    String PTHT_MAINTENANCE = "MAINTENANCE";
    String TKTU_CSSR_2G_PEAK = "TC011";
    String TKTU_HOSR_2G_PEAK = "TC013";
    String TKTU_CS_CSSR_3G_NORMAL = "TC014";
    String TKTU_CDR_2G_PEAK = "TC012";
    String TKTU_CDR_3G_NORMAL = "TC015";
    String TKTU_CELL_TOI_CDR = "TC016";
    String TKTU_CELL_TOI_SCR = "TC017";
    String TKTU_CELL_TOI_CSSR = "TC018";
    String TKTU_CELL_TOI_TCR = "TC019";
    String TKTU_CELL_TOI_CS_CDR_3G = "TC020";
    String TKTU_CELL_TOI_KPI_MT_20 = "TC021";
    String TKTU_CELL_TOI_KPI_MT_30 = "TC022";
    String TKTU_CELL_TOI_VUNG_LOM = "TC024";
    String TKTU_DB_MANAGER = "TC025";
    String TKTU_KSTK_PS_TH = "TC026";
    String VHKT_CTTD = "TC027";
    String VHKT_DHVT = "TC028";
    String TKTU_DIEM_NGANH_DOC = "TC041";
    String VHKT_CD = "TC042";
    String TKTU_CELL_TOI_TCR_2G = "TC055";
    String TKTU_CS_CSSR_3G_PEAK = "TC052";
    String TKTU_PS_CSSR_3G_PEAK = "TC053";
    String TKTU_CS_CDR_3G_PEAK = "TC054";
    String TKTU_CELL_TOI_CS_CSSR_3G = "TC046";
    String TKTU_CELL_TOI_PS_CSSR_3G = "TC047";
    String TKTU_CELL_TOI_CS_RAB_CR_3G = "TC050";
    String TKTU_CELL_TOI_PS_RAB_CR_3G = "TC051";
    String TKTU_QUAN_LY_DATABASE = "TC025";
    String TKTU_PAKH_QUA_HAN = "TC057";
    String TKTU_SO_LUONG_PAKH = "TC058";
    String TKTU_UCTT_CSSR = "TC059";
    String TKTU_UCTT_CDR = "TC060";
    String TKTU_UCTT_CS_CSSR = "TC061";
    String TKTU_UCTT_PS_CSSR = "TC062";
    String TKTU_UCTT_CS_CSR = "TC063";
  }

  interface CAT_CRT_PLUS_POINT {

    Double PLUS_BTS_LEVEL1 = 0.3;
    Double PLUS_BTS_LEVEL2 = 0.45;
    Double PTHT_PILLAR_CABLE_LEVEL1 = 0.2;
    Double PTHT_PILLAR_CABLE_LEVEL2 = 0.3;
  }

  interface CAT_CRT_FIX_RANK {

    Double PLUS_BTS_2G_FROM = 1.0;
    Double PLUS_BTS_2G_TO = 3.0;
    Double PTHT_PILLAR_CABLE_FROM = 1.0;
    Double PTHT_PILLAR_CABLE_TO = 3.0;
    Double PTHT_UNDER_GROUND_CABLE_FROM = 1.0;
    Double PTHT_UNDER_GROUND_CABLE_TO = 300.0;
    Double PTHT_MAINTENANCE_FROM = 1.0;
    Double PTHT_MAINTENANCE_TO = 5.0;
  }

  interface CAT_CRT_GROUP {

    String PTHT = "G001";
    String VHKT_UCTT = "G002";
    String TKTU = "G003";
    String QLKT_PHTH = "G004";
    String CLMCD_ATTNCN = "G005";
    String CT_CNTT_CN = "G006";
    String CTDH_THCV = "G007";
    String KQKD = "G008";
    String GDCN_CTQL = "G009";
  }

  interface RESULT {

    String OK = "OK";
    String NOK = "NOK";
    String SUCCESS = "success";
    String FAIL = "fail";
    String FAIL_UPPER = "FAIL";
    int NUM_SUCCESS = 1;
    int NUM_FAIL = 0;
    int NUM_CONFLIC = 2;
    int NO_SCORE = 3;
    String TIME_OUT = "TIME_OUT";
    String NOT_LOGIN = "NOT_LOGIN";
    String SUCCESS_CAP = "SUCCESS";
  }

  interface TEMPLATE {

    String TEMPLATE_GIAOCHITIEU_PTHT = "/WEB-INF/jsp/template/scoring/IMPORT_GIAOCHITIEU_PTHT.xls";
    String TEMPLATE_GIAOCHITIEU_TKTU = "/WEB-INF/jsp/template/scoring/IMPORT_GIAOCHITIEU_TKTU.xls";
    String TEMPLATE_GIAOCHITIEUTUAN_TKTU = "/WEB-INF/jsp/template/scoring/IMPORT_GIAOCHITIEUTUAN_TKTU.xls";
    String TEMPLATE_GIAOCHITIEU_VHKT = "/WEB-INF/jsp/template/scoring/IMPORT_GIAOCHITIEU_VHKT.xls";
    String TEMPLATE_DIEMCHOT_PTHT = "/WEB-INF/jsp/template/scoring/IMPORT_DIEMCHOT_PTHT.xls";
    String TEMPLATE_DIEMCHOT_TKTU = "/WEB-INF/jsp/template/scoring/IMPORT_DIEMCHOT_TKTU.xls";
    String TEMPLATE_DIEMCHOT_VHKT = "/WEB-INF/jsp/template/scoring/IMPORT_DIEMCHOT_VHKT.xls";
    String TEMPLATE_CHITIEUDAT_PTHT = "/WEB-INF/jsp/template/scoring/IMPORT_CHITIEUDAT_PTHT.xls";
    String TEMPLATE_CHITIEUDAT_TKTU = "/WEB-INF/jsp/template/scoring/IMPORT_CHITIEUDAT_TKTU.xls";
    String TEMPLATE_CHITIEUDAT_VHKT = "/WEB-INF/jsp/template/scoring/IMPORT_CHITIEUDAT_VHKT.xls";
    String TEMPLATE_CHITIEUDAT_PTHT_DAILY = "/WEB-INF/jsp/template/scoring/IMPORT_CHITIEUDAT_PTHT_DAILY.xls";
    String TEMPLATE_CHITIEUDAT_TKTU_DAILY = "/WEB-INF/jsp/template/scoring/IMPORT_CHITIEUDAT_TKTU_DAILY.xls";
    String TEMPLATE_CHITIEUDAT_VHKT_DAILY = "/WEB-INF/jsp/template/scoring/IMPORT_CHITIEUDAT_VHKT_DAILY.xls";
    String TEMPLATE_CHAMDIEM_PTHT = "/WEB-INF/jsp/template/scoring/EXPORT_CHAM_DIEM_PTHT.xls";
    String TEMPLATE_CHAMDIEM_VHKT = "/WEB-INF/jsp/template/scoring/EXPORT_CHAM_DIEM_VHKT.xls";
    String TEMPLATE_CHAMDIEM_TKTU = "/WEB-INF/jsp/template/scoring/EXPORT_CHAM_DIEM_TKTU.xls";
    String TEMPLATE_CHAMDIEM_TUAN_TKTU = "/WEB-INF/jsp/template/scoring/EXPORT_CHAM_DIEM_TUAN_TKTU.xls";
    String TEMPLATE_ASSIGN_STATION_HISTORY = "/WEB-INF/jsp/template/assignStationHistory.xls";
    String TEMPLATE_ASSIGN_STATION_HISTORY_GROUP = "/WEB-INF/jsp/template/assignStationHistoryGroup.xls";
    String TEMPLATE_SLR_STATION_HISTORY = "/WEB-INF/jsp/template/exportSlrStationHistory.xls";
    String TEMPLATE_STATION_USER = "/WEB-INF/jsp/template/template_import_gan_go_tram_nhanvien.xls";
    String TEMPLATE_STATION_GROUP = "/WEB-INF/jsp/template/template_import_gan_go_tram_nhom.xls";
    String TEMPLATE_TOGGLE_ASSIGN_STATION = "/WEB-INF/jsp/template/station/import_toggle_assign_station.xls";
    String TEMPLATE_TOGGLE_ASSIGN_STATION_GROUP = "/WEB-INF/jsp/template/station/import_toggle_assign_station_group.xls";
    String TEMPLATE_TOGGLE_ASSIGN_LANE = "/WEB-INF/jsp/template/station/import_toggle_assign_lane.xls";
    String TEMPLATE_COORDINATE_POINT_PTHT = "/WEB-INF/jsp/template/scoring/import_coordinate_point_ptht.xls";
    String TEMPLATE_COORDINATE_POINT_TKTU = "/WEB-INF/jsp/template/scoring/import_coordinate_point_tktu.xls";
    String TEMPLATE_COORDINATE_POINT_VHKT_KTTD = "/WEB-INF/jsp/template/scoring/import_coordinate_point_vhkt_kttd.xls";
    String TEMPLATE_COORDINATE_POINT_VHKT_DHVT = "/WEB-INF/jsp/template/scoring/import_coordinate_point_vhkt_dhvt.xls";
    String TEMPLATE_COORDINATE_POINT_VHKT_CD = "/WEB-INF/jsp/template/scoring/import_coordinate_point_vhkt_cd.xls";
    String DEVICE_TEMPLATE_EXPORT_COREMOBILE_PATH = "/WEB-INF/jsp/template/export/";
    String DEVICE_EXPORT_COREMOBILE = "export_core_mobile_device_";
    String DEVICE_TEMPLATE_COREMOBILE_NAME = "DEVICE_Template_Export_CoreMobile.xls";
    String DEVICE_TEMPLATE_EXPORT_IP_PATH = "/WEB-INF/jsp/template/export/";
    String DEVICE_EXPORT_IP = "export_Ip_device_";
    String DEVICE_EXPORT_TD = "export_TD_device_";
    String DEVICE_TEMPLATE_Ip_NAME = "DEVICE_Template_Export_Ip.xls";
    String DEVICE_TEMPLATE_TD_NAME = "DEVICE_Template_Export_TD.xls";
    String FILE_EXTENTION = ".xls";
    String EXPORT_DAILT_REPORT = "EXPORT_DAILT_REPORT_Template.xls";
    String EXPORT_WEEK_REPORT = "EXPORT_WEEK_REPORT_Template.xls";
    String EXPORT_DAILT_REPORT_PROVINCE = "EXPORT_DAILT_REPORT_PROVINCE_Template.xls";
    String EXPORT_WEEKLY_REPORT_PROVINCE = "EXPORT_WEEKLY_REPORT_PROVINCE_Template.xls";
    String EXPORT_MONTH_REPORT_PROVINCE = "EXPORT_MONTH_REPORT_PROVINCE_Template.xls";
    String SLR_INTERUP_CELL_H = "/WEB-INF/jsp/template/slrInterup/slr_interup_cell_h.xlsx";
    String SLR_INTERUP_SUB_H = "/WEB-INF/jsp/template/slrInterup/slr_interup_sub_h.xlsx";
    String SLR_INTERUP_CLM24 = "/WEB-INF/jsp/template/slrInterup/slr_interup_clm24.xlsx";
    String SLR_INTERUP_FLASH_STATION = "/WEB-INF/jsp/template/slrInterup/slr_interup_flash_station.xlsx";
    String SLR_TROUBLE_SUB = "/WEB-INF/jsp/template/slrInterup/slr_trouble_sub.xlsx";
    String SLR_CONFIG_WARNING = "/WEB-INF/jsp/template/slrInterup/slr_config_warning.xlsx";
    String SLR_CONFIG_WARNING_IMPORT = "/WEB-INF/jsp/template/slrInterup/slr_config_warning_import.xlsx";
    String SLR_KPI_2G_NPMS = "/WEB-INF/jsp/template/slrInterup/slr_kpi_2g_npms.xlsx";
    String SLR_KPI_3G_NPMS = "/WEB-INF/jsp/template/slrInterup/slr_kpi_3g_npms.xlsx";
    String SLR_TROUBLE_STATION = "/WEB-INF/jsp/template/slrInterup/slr_trouble_station.xlsx";
    String SLR_ALARM_NOCPRO = "/WEB-INF/jsp/template/slrInterup/slr_alarm_nocpro.xlsx";
    String EXPORT_MTN_HISTORY_CHECK_STATION = "/WEB-INF/jsp/template/mtnwork/mtn_history_check_station.xlsx";
    String REPORT_TOTAL_TICKET_NAME = "report_ticket_trouble_";
    String REPORT_TOTAL_TICKET = "/WEB-INF/jsp/template/reportTicket/reportTotalTicket.xlsx";
    String REPORT_STOCK = "/WEB-INF/jsp/template/task/report_stock_suppress.xlsx";
    String IMPORT_REPORT_STOCK = "/WEB-INF/jsp/template/task/import_report_stock_suppress.xlsx";
  }

  interface CRT_GROUP_TEMPLATE_TYPE {

    String GROUP_PTHT = "G001";
    String GROUP_VHKT = "G002";
    String GROUP_TKTU = "G003";
  }

  interface CRT_POINT_TITLE {

    String KH = "KH";
    String KQ = "KQ";
    String TI_LE = "Tỉ lệ";
    String DIEM_CONG = "Điểm cộng";
    String DIEM_TRU = "Điểm trừ";
    String DIEM_GOC = "Điểm gốc";
    String DIEM_CHOT = "Điểm chốt";
    String NOTE = "Ghi chú";
    String DIEM_PHOI_HOP = "Điểm phối hợp";
    String TARGET = "Target";
    String THUC_HIEN = "Thực hiện";
    String DIEM_DAT = "Điểm đạt";
    String TONG_DIEM = "Tổng điểm chuẩn KPI";
    String TONG_DIEM_CONG = "Tổng điểm cộng chuẩn";
    String DIEM_DAT_KPI = "Điểm đạt KPI";
    String TONG_DIEM_DAT = "Tổng điểm đạt";
    String TONG_DIEM_THANG = "Tổng điểm";
    String TONG_DIEM_CHUAN = "Tổng điểm chuẩn";
    String PHAN_TRAM_DAT = "% Đạt";
  }

  interface CRT_POINT_TITLE_TYPE {

    String KH = "1";
    String KQ = "2";
    String TI_LE = "3";
    String DIEM_CONG = "4";
    String DIEM_TRU = "5";
    String DIEM_GOC = "6";
    String DIEM_CHOT = "7";
    String NOTE = "8";
  }

  interface ACTION_LOG {

    Long INSERT = 1L;
    Long UPDATE = 2L;
    Long DELETE = 3L;
    Long SEARCH = 4L;
    Long TO_MARK = 5L;
    Long TOTAL_MARK = 6L;
    Long DOWNLOAD = 7L;
    Long IMPORT = 8L;
    Long EXPORT = 9L;
    Long ASSIGN = 10L;
    Long UNASSIGN = 11L;
    Long UPDATEPROGRESS = 12L;
  }

  interface ACTION_TYPE {

    Long CRITERION_GROUP = 1L;
    Long CRITERION = 2L;
    Long CRT_KI = 3L;
    Long RANK_SCORE = 4L;
    Long POINT_METHOD = 5L;
    Long DOWNLOAD_MONTHLY = 6L;
    Long IMPORT_MONTHLY = 7L;
    Long DONE_VALUE_MANAGEMENT = 9L;
    Long SCORING_CRT_GROUP = 10L;
    Long TOTAL_SCORE = 11L;
    Long FIX_SCORE = 12L;
    Long OFFLINE_ERROR = 13L;
    Long CAT_CRT_TARGET = 14L;
    Long POINT_METHOD_PTHT = 15L;
    Long IMPORT_MUNIS_DIRECTOR = 16L;
    Long DONE_SCORE_IMPORT = 17L;
    Long COORDINATE_POINT_IMPORT = 18L;
    Long SYS_GROUP_MANAGER = 19L;
    Long GROUP_USER_ASSIGN = 20L;
    Long SLR_STATION = 21L;
    Long SLR_STATION_TKTU = 22L;
    Long SLR_SUBCRIPTION = 23L;
    Long SYS_USERS = 24L;
    Long SLR_CONFIG_KI_STATION = 37L;
    Long SLR_CONFIG_KI_SUB = 25L;
    Long SLR_CONFIG_KI_TKTU = 26L;
    Long SLR_CONFIG_COEFFICIENT_STATION = 27L;
    Long SLR_CONFIG_COEFFICIENT_SUB = 28L;
    Long SLR_CONFIG_COEFFICIENT_LOCATE = 29L;
    Long SLR_CONFIG_KI = 30L;
    Long SLR_CONFIG_CALC_SUB_100 = 31L;
    Long SLR_CONFIG_CALC_SUB_TROUBLE_INTERM = 32L;
    Long SLR_CONFIG_CALC_STATION_GENENATOR4H = 33L;
    Long SLR_CONFIG_CALC_STATION_TROUBLE = 34L;
    Long SLR_CONFIG_CALC_STATION_TEMPHIGH = 35L;
    Long SLR_CONFIG_CALC_SUB_DEPLOY_INTERM = 36L;
    Long INFRA_STATION = 38L;
    Long MTN_SYS_GROUP_MANAGER = 39L;
    Long MTN_GROUP_USER_ASSIGN = 40L;
    Long SLR_TROUBLE_SUB_1 = 41L;
    Long SLR_TROUBLE_SUB_2 = 42L;
    Long SLR_INTERUP_CELL_H = 43L;
    Long SLR_INTERUP_SUB_H = 44L;
    Long SLR_INTERUP_FLASH_STATION = 45L;
    Long SLR_INTERUP_CLM24 = 46L;
    Long SLR_CONFIG_THRESHOLD = 47L;
    Long SLR_CONFIG_WARNING = 48L;
    Long MTN_HIS_CHECK_STATION = 49L;
    Long MN_CAT_WORK = 50L;
    Long MN_CAT_SUPPLIES = 90L;
    Long MN_CAT_WORK_MAJOR = 91L;
    Long WORK_ARISING = 66L;
    Long CAT_STOCK_MODEL = 92L;
    Long GROUP_RECALL_STOCK = 93L;
  }

  interface LEVEL_LOCATION {

    Long COUNTRY = 1L;
    Long AREA = 2L;
    Long PROVINCE = 3L;
    Long DISTRICT = 4L;
    Long VILLAGE = 5L;
  }

  interface GROUP_CRT {

    String PHAT_TRIEN_HA_TANG = "G001";
    String VHKT_UCTT = "G002";
    String TKTU = "G003";
    String QLKT_PHTH = "G004";
    String CLCD_ATTT = "G005";
    String CNTT_CN = "G006";
    String THCV_CT_DV_CD = "G007";
    String KQKD_CN = "G008";
    String GDCNDG = "G009";
  }

  interface ROLE_TYPE {

    Long GD = 0l;
    Long PGD_CN = 1l;
  }

  interface SCORE_ADD {

    Long SCORE = 0l;
  }

  interface IMPORT_VALUE {

    Long NO_IMPORT = 0l;
    Long IMPORTED = 1l;
  }

  interface CRT_GROUP_POINT_TITLE {

    String TONG_DIEM_THUONG = "Tổng điểm thưởng";
    String TONG_DIEM_TRU = "Tổng điểm trừ";
    String TONG_DIEM_DAT = "Tổng điểm đạt";
  }

  interface STATION_USER_PERMISSION {

    Long ASSIGN_STATION = 1L;
    Long REMOVE_STATION = 0L;
  }

  interface CAT_MAJOR_TYPE {

    String NVDN = "NVDN";
    String NVKT = "NVKT";
  }

  interface SYS_PARAM {

    Long PRICE_STA_ON_TER_PLAIN = 0L;
    Long PRICE_STA_ON_TER_MIDLAND = 1L;
    Long PRICE_STA_ON_TER_UPLAND = 2L;
    Long PRICE_STA_ON_TER_RIVER = 3L;
    Long PRICE_STA_ON_TER_ISLAND = 4L;
    Long PRICE_STA_ON_TER_SEA = 4L;
    Long PRICE_ADDED_CABINET_COEFF = 6L;
    Long FAUL_CLOSING_DATE = 7L;
  }

  interface STATUS {

    Long SYS_USER_INACTIVE = 0L;
    Long SYS_USER_ACTIVE = 1L;
    Long ERROR_OFFLINE_REVEMO = 3L;
    Long ERROR_OFFLINE_IS_LATCH = 2L;
    Long ERROR_OFFLINE_NOT_LATCH = 1L;
  }

  interface NETWORK_TYPE {

    String METRO = "METRO";
    String PSTN = "PSTN";
    String DSLAM = "DSLAM";
  }

  interface CALC_TYPE {

    Long TRU_THEO_PHAN_TRAM = 1L;
    Long TRU_THEO_DON_GIA = 2L;
  }

  interface APPLY_TYPE {

    Long NVDN = 0L;
    Long NVKT = 1L;
    Long BOTH = 2L;
  }

  interface SYS_PARAM_NAME {

    String PRICE_STA_ON_TER_PLAIN = "PRICE_STA_ON_TER_PLAIN";
    String PRICE_STA_ON_TER_MIDLAND = "PRICE_STA_ON_TER_MIDLAND";
    String PRICE_STA_ON_TER_UPLAND = "PRICE_STA_ON_TER_UPLAND";
    String PRICE_ADDED_CABINET_COEFF = "PRICE_ADDED_CABINET_COEFF";
    String FAUL_CLOSING_DATE = "FAUL_CLOSING_DATE";
    String PRICE_STA_ON_TER_RIVER = "PRICE_STA_ON_TER_RIVER";
    String PRICE_STA_ON_TER_ISLAND = "PRICE_STA_ON_TER_ISLAND";
  }

  interface OBJECT_TYPE {

    Long STATIONS = 0L;
    Long CABLE = 1L;
  }

  interface WORK_TYPE {

    Long CYCLE = 1L;
    Long NO_CYCLE = 1L;

    interface SCORE_STANDART_MINUS_POINT {

      Double GD = 2d;
      Double PGD = 7d;
    }

    interface STANDARD_POINT_TITLE {

      String GD = "Tổng điểm trừ không nhĐ hơn -2 điểm";
      String PGD = "Tổng điểm trừ không nhĐ hơn -7 điểm";
      String TITLE_GD = "PHỤ LỤC 3: TỔNG HỢP CHẤM ĐIỂM TT ĐHKT CÔNG VĂN CHỈ THỊ CHẤM GĐCN";
      String TITLE_PGD = "PHỤ LỤC 3: TỔNG HỢP CHẤM ĐIỂM TT ĐHKT CÔNG VĂN CHỈ THỊ CHẤM P.GĐ";
    }

    interface CACULATE_UNIT {

      Long STATION = 1L;
      Long CABLE_LANE = 2L;
      Long CABINET_BOX = 3L;
    }

    interface ReportPlan_Export {

      String REPORTPLAN_EXPORT_AUTO_IN = "ReportPlan_Template";
      String REPORTPLAN_EXPORT_AUTO_OUT = "ReportPlan_Template_";
      String TEMPLATE_SOURCE_LOCATION = "/WEB-INF/jsp/template/reportPlan/";
      String FILE_EXTENTION = ".xls";
    }

    interface ReportDevice_Export {

      String QLPM_NSS_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_NSS";
      String QLPM_NSS_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_NSS";
      String QLPM_BSS_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_BSS";
      String QLPM_BSS_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_BSS";
      String QLPM_VASIN_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_VASIN";
      String QLPM_VASIN_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_VASIN";
      String QLPM_TRANSMISSION_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_TRUYENDAN";
      String QLPM_TRANSMISSION_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_TRUYENDAN";
      String QLPM_IP_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_IP";
      String QLPM_IP_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_IP";
      String QLPM_NGUON_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_NGUON";
      String QLPM_NGUON_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_NGUON";
      String TEMPLATE_SOURCE_LOCATION = "/WEB-INF/jsp/template/reportDevice/";
      String FILE_EXTENTION = ".xls";
    }
  }

  interface TRANS_DEVICE_TYPE {

    Long STM1 = 1L;
    Long STM4 = 2L;
    Long STM16 = 3L;
    Long STM64 = 4L;
    Long DWDM = 5L;
  }

  interface METRO_DEVICE_TYPE {

    Long BRAS = 1L;
    Long CORE_AREA = 2L;
    Long CORE_PROVINCE = 3L;
    Long AGG_SWITCH = 4L;
    Long SRT = 5L;
    Long DSLAM = 6L;
    Long SWITCH = 7L;
  }

  interface APPROVE_STATUS {

    Long NOK = 1L;
    Long OK = 2L;
  }

  interface SCORE_PLUS_POINT {

    Double GD = 7d;
    Double PGD = 21d;
  }

  interface SCORE_STANDART_MINUS_POINT {

    Double GD = 2d;
    Double PGD = 7d;
  }

  interface STANDARD_POINT_TITLE {

    String GD = "Tổng điểm trừ không nhĐ hơn -2 điểm";
    String PGD = "Tổng điểm trừ không nhĐ hơn -7 điểm";
    String TITLE_GD = "PHỤ LỤC 3: TỔNG HỢP CHẤM ĐIỂM TT ĐHKT CÔNG VĂN CHỈ THỊ CHẤM GĐCN";
    String TITLE_PGD = "PHỤ LỤC 3: TỔNG HỢP CHẤM ĐIỂM TT ĐHKT CÔNG VĂN CHỈ THỊ CHẤM P.GĐ";
  }

  interface CACULATE_UNIT {

    Long STATION = 1L;
    Long CABLE_LANE = 2L;
    Long CABINET_BOX = 3L;
  }

  interface ReportPlan_Export {

    String REPORTPLAN_EXPORT_AUTO_IN = "ReportPlan_Template";
    String REPORTPLAN_EXPORT_AUTO_OUT = "ReportPlan_Template_";
    String TEMPLATE_SOURCE_LOCATION = "/WEB-INF/jsp/template/reportPlan/";
    String FILE_EXTENTION = ".xls";
  }

  interface Power_Export {

    String POWER_EXPORT_AUTO_IN = "Power_Template";
    String POWER_EXPORT_AUTO_OUT = "Power_Template_";
    String TEMPLATE_SOURCE_LOCATION = "/WEB-INF/jsp/template/reportPlan/";
    String FILE_EXTENTION = ".xls";
  }

  interface ReportWorkDevice_Export {

    String REPORTWORKDEVICE_EXPORT_AUTO_IN = "report_cong_viec_bao_duong";
    String REPORTWORKDEVICE_AUTO_OUT = "report_cong_viec_bao_duong";
    String TEMPLATE_REPORTWORKDEVICE_SOURCE_LOCATION = "/WEB-INF/jsp/template/reportWorkDevice/";
    String FILE_EXTENTION = ".xls";
  }

  interface ReportDevice_Export {

    String QLPM_NSS_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_NSS";
    String QLPM_NSS_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_NSS_";
    String QLPM_BSS_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_BSS";
    String QLPM_BSS_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_BSS_";
    String QLPM_VASIN_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_VASIN";
    String QLPM_VASIN_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_VASIN_";
    String QLPM_TRANSMISSION_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_TRUYENDAN";
    String QLPM_TRANSMISSION_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_TRUYENDAN_";
    String QLPM_IP_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_IP";
    String QLPM_IP_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_IP_";
    String QLPM_NGUON_IN = "DS_NHAN_SU_QUAN_LY_THIET_BI_NGUON";
    String QLPM_NGUON_OUT = "DS_NHAN_SU_QUAN_LY_THIET_BI_NGUON_";
    String TEMPLATE_SOURCE_LOCATION = "/WEB-INF/jsp/template/reportDevice/";
    String FILE_EXTENTION = ".xls";
  }

  interface Report_Assign {

    String Report_AssignWork = "QLCTKT_IMPORT_GIAOVIEC";
    String Report_AssignWork_Temp = "QLCTKT_IMPORT_GIAOVIEC_TEMP";
    String Report_AssignWork_Re = "QLCTKT_IMPORT_GIAOVIEC_KETQUA";
    String Report_AssignDevice = "QLCTKT_IMPORT_GANTHIETBI";
    String Report_AssignDevice_Temp = "QLCTKT_IMPORT_GANTHIETBI_TEMP";
    String Report_AssignDevice_Re = "QLCTKT_IMPORT_GANTHIETBI_KETQUA";
    String FILE_EXTENTION = ".xls";
  }

  interface DevicesType_R3110 {

    String NUMBER1 = "AC";
    String NUMBER2 = "DC";
    String NUMBER3 = "ATS";
    String NUMBER4 = "UPS";
    String NUMBER5 = "Máy phát điện";
    String NUMBER6 = "Hệ thống làm mát";
    String NUMBER7 = "Hệ thống tiếp địa";
    String NUMBER8 = "Accu_DC";
    String NUMBER9 = "Accu_UPS";
  }

  interface APPLICATION_CODE {

    String QLCTKT = "QLCTKT";
  }

  interface PERMISSION {

    String INSERT_SCORING_PTHT = "INSERT_SCORING_PTHT";
    String SEARCH_SCORING_PTHT = "SEARCH_SCORING_PTHT";
    String INSERT_SCORING_VHKT = "INSERT_SCORING_VHKT";
    String SEARCH_SCORING_VHKT = "SEARCH_SCORING_VHKT";
    String INSERT_SCORING_TKTU = "INSERT_SCORING_TKTU";
    String SEARCH_SCORING_TKTU = "SEARCH_SCORING_TKTU";
  }

  interface SALARY {

    Long TYPE1 = 1L;
    Long TYPE2 = 2L;
    Long TYPE3 = 3L;
    Long TYPE4 = 4L;
    Long TYPE5 = 5L;
    Long TYPE6 = 6L;
    Long TYPE7 = 7L;
    Long STATUS_EFFECTIVE = 1L;
    Long STATUS_EXPIRE = 2L;
    Long QUALITATIVE = 1L;
    Long QUANTITATIVE = 2L;
  }

  interface ASSSIGN_TASK {

    String ADMIN_TTDHKT = "admin_ttdhkt";
    String TTDHKT = "ttdhkt";
    String ADMIN_KV = "admin_kv";
    String KV = "kv";
    String ADMIN_PROVINCE = "admin_province";
    String LEADER = "leader";
    String STAFF = "staff";
    String STAFF_ID = "userId";
    String GROUP_ID = "groupId";
    String ROLE = "roleLogin";
    String ROLE_TYPE = "roleType";
    String ROLE_TTDHKT_KV = "roleTtdhktKv";
    String ROLE_POPUP = "role";
  }

  interface SLR_TROUBLE_SUB {

    interface IS_TROUBLE {

      Long SU_CO = 1L;
      Long NEW = 2L;
    }
  }

  interface TASK {

    Long TASK_TYPE_CHANGE_DEVICE_UCTT = 70L;
    Long TASK_REASON_ACTIVE = 1L;
    Long TASK_REASON_NOT_ACTIVE = 0L;
    Long TASK_ID_CHANGE_SPEED_MULTISCREEN = 51L;
    Long IP_VIETTEL_QUY_HOACH = 1L;
    Long PROGRESS_ASSIGN_STAFF_TO_CM = 3L;
    Long PROGRESS_FINISH_SETUP_TO_CM = 8L;
    Long IS_NEW_TASK = 1L;
    Long STATUS_WAITTING = 3L;
    Long STATUS_NOT_UPDATE = 2L;
    Long STATUS_ACTIVE = 1L;
    Long STATUS_DEACTIVE = 0L;
    Long PROGRESS_BEGIN = 0L;
    Long PROGRESS_ASSIGN_GROUP = 1L;
    Long PROGRESS_CHANGE_GROUP = 2L;
    Long PROGRESS_ASSIGN_STAFF = 3L;
    Long PROGRESS_CHANGE_STAFF = 4L;
    Long PROGRESS_FINISH_SETUP = 5L;
    Long PROGRESS_FINISH_FILE = 6L;
    Long PROGRESS_FINISH = 7L;
    Long PROGRESS_FINISH_REVOKE = 77L;
    Long PROGRESS_RUNNING = 12L;
    Long PROGRESS_WAITTING_ACTIVESUB_FINISH = 13L;
    Long PROGRESS_ACTIVE_SUB_FAILSE = 14L;
    Long PROGRESS_AUTO_ACTIVE_SUB_SUCCESS = 15L;
    Long PROGRESS_DELAY_FINISH = 16L;
    Long PROGRESS_ACTIVE_DEVICE = 17L;
    Long PROGRESS_UPDATE_FINISH = 21L;
    Long PROGRESS_CHILD_RUNNING = 22L;
    Long PROGRESS_CHECK_CAPACITY = 23L;
    Long STATE_BEGIN = 0L;
    Long STATE_ASSIGN_GROUP = 1L;
    Long STATE_ASSIGN_STAFF = 2L;
    Long RESULT_SUCCESS_WITH_OTHER_CONNECTOR = 3L;
    Long RESULT_CHECK_CAPACITY = 4L;
    Long RESULT_SUCCESS = 1L;
    Long RESULT_FAIL = 0L;
    Long RESULT_CHANGE_REQUEST = 2L;
    Long RESULT_CHANGE_GROUP = 3L;
    Long PROVINCES_LEVEL = 3L;
    Long DISTRICT_LEVEL = 4L;
    String HOUSE = "HOUSE";
    String CONNECTOR = "CONNECTOR";
    String PATH = "PATH";
    String SERVICCE_TYPE_ADSL = "A";
    String SERVICCE_TYPE_PSTN = "P";
    String SERVICCE_TYPE_FTTH = "F";
    String SERVICCE_TYPE_IPTV = "I";
    String SERVICCE_TYPE_NGN = "N";
    String SERVICCE_TYPE_LEADLINE = "L";
    String SERVICCE_TYPE_THC = "T";
    String SERVICCE_TYPE_EOC = "E";
    String SERVICCE_TYPE_WHITE_CHANNEL = "W";
    String SERVICCE_TYPE_PRIVATE_INTERNATIONAL_CHANNEL = "Q";
    String SERVICCE_TYPE_PCM = "K";
    String SERVICCE_TYPE_OW = "O";
    String SERVICCE_TYPE_MW = "Z";
    String SERVICCE_TYPE_PPPoE = "J";
    String SERVICCE_TYPE_MULTISCREEN_2 = "2";
    String SERVICCE_TYPE_AMI_ONE = "V";
    String SERVICCE_TYPE_CAMERA = "G";
    String SERVICCE_TYPE_MULTI_SCREEN = "U";
    Long SERVICCE_TYPE_LONG_MULTI_SCREEN = 18L;
    Long TASK_TYPE_SURVEY = 1L;
    Long TASK_TYPE_SETUP = 2L;
    Long TASK_TYPE_CHANGE_ADD_AFTER_INSTALLING = 3L;
    Long TASK_TYPE_SETUP_CONTACT_INSTALLING = 50L;
    Long TASK_TYPE_SETUP_INTERGRATE_INSTALLING = 53L;
    Long KHAI_KENH = 4L;
    Long KHAI_LUONG = 5L;
    Long TASK_TYPE_ASSIGN_PSTN = 8L;
    Long TASK_TYPE_SUPPRESS = 9L;
    Long TASK_TYPE_PLUGIN = 36L;
    Long TASK_TYPE_MOVE_PSTN = 10L;
    Long CANCEL_FLOW_TRANSMISSION = 12L;
    Long BLOCK_FLOW_TRANSMISSION = 13L;
    Long OPEN_FLOW_TRANSMISSION = 14L;
    Long TASK_TYPE_CHANNEL_REMOVE = 15L;
    Long TASK_TYPE_CHANNEL_CLOSE = 16L;
    Long TASK_TYPE_CHANNEL_OPEN = 17L;
    Long TASK_TYPE_CHANGE_CHANNEL = 18L;
    Long TASK_TYPE_ADD_NUMBER_CENTREX = 20L;
    Long TASK_TYPE_DELETE_NUMBER_CENTREX = 21L;
    Long TASK_TYPE_ADD_CENTREX = 22L;
    Long TASK_TYPE_REMOVE_CENTREX = 23L;
    Long TASK_TYPE_CREATE_VOID = 24L;
    Long TASK_TYPE_DELETE_VOID = 25L;
    Long TASK_TYPE_CHANGE_REQUEST_SURVEY = 29L;
    Long TASK_TYPE_CHANGE_ADMIN_CENTREX = 30L;
    Long TASK_TYPE_ACCEPTANCE_CHANGE = 31L;
    Long TASK_TYPE_SURVEY_CHANGE_SPEED = 32L;
    Long TASK_TYPE_DEPLOY_CHANGE_SPEED = 33L;
    Long TASK_TYPE_SURVEY_FLOW = 34L;
    Long TASK_TYPE_CHANGE_BANDWIDTH_FLOW = 11L;
    Long TASK_TYPE_DELOY_VAS_xMIO = 42L;
    Long SERVEY_E1_TRUNK = 46L;
    Long DEPLOY_E1_TRUNK = 43L;
    Long SERVEY_PSTN_No_TRUNK = 45L;
    Long DEPLOY_PSTN_No_TRUNK = 44L;
    Long DELOY_VAS_TRUNK = 47L;
    Long SERVEY_PSTN_IN_TRUNK = 49L;
    Long CHANGE_PORT_A_P = 48L;
    Long SERVEY_TAMDEN_ADD_PSTN = 54L;
    Long ASSIGN_TAMDEN_ADD_PSTN = 55L;
    Long REMOVE_TAMDEN_ADD_PSTN = 56L;
    Long TASK_TYPE_PULL_CABLE = 51L;
    Long TASK_TYPE_ACTIVE_SUB = 50L;
    Long TASK_TYPE_WELDING = 52L;
    Long TASK_TYPE_ACCEPTANCE = 53L;
    Long TASK_TYPE_SURVEY_HOTLINE = 60L;
    Long TASK_TYPE_CHANGE_MODEL_GPON = 61L;
    Long TASK_TYPE_COMPLETE_CHANNEL_DATA = 63L;
    Long TASK_TYPE_CHANGE_PACKET = 64L;
    Long WITHDRAW_DEVICE = 66L;
    Long WITHDRAW_DEVICE_WIRE = 67L;
    Long WITHDRAW_WIRE = 68L;
    Long TASK_TYPE_DEPLOY_CHANGE_DEVICE = 57L;
    Long TASK_TYPE_SURVEY_CHANGE_DEVICE = 58L;
    Long TASK_TYPE_KCS_DEVICE = 59L;
    Long TASK_IS_ACTIVE_SUB = 1L;
    Long TASK_ID_ADD_STOCK_MODEL = 48L;
    Long TASK_ID_CHANGE_STOCK_MODEL = 49L;
    Long TASK_ID_RETAKE_VENDOR = 52L;
    Long TYPE_CHANGE_STOCK_MODEL = 4L;
    Long TYPE_RECALL_STOCK_MODEL = 3L;
    Long TASK_TYPE_LAST_CHECK = 40L;
    Long TASK_TYPE_UNPLUG_PORT = 41L;
    Long TASK_TYPE_RUTDAY = 41L;
    Long TASK_TYPE_CLOSE_18001900 = 37L;
    Long TASK_TYPE_OPEN_18001900 = 38L;
    Long TASK_TYPE_REMOVE_18001900 = 39L;
    String TASK_TYPE_CM_ADD_NUMBER_CENTREX = "7";
    String TASK_TYPE_CM_DELETE_NUMBER_CENTREX = "8";
    String TASK_TYPE_CM_ADD_CENTREX = "5";
    String TASK_TYPE_CM_REMOVE_CENTREX = "6";
    String TASK_TYPE_CM_CHANGE_ADMIN_CENTREX = "15";
    String TASK_TYPE_CM_ACCEPTANCE_CHANGE = "0";
    String TASK_TYPE_CM_REMOVE_SERVICE = "4";
    String ERROR_MSG = "errorMsg";
    String INFO_MSG = "infoMsg";
    String RELOAD_MSG = "reLoadMsg";
    String MSG_COMPLAINT = "msgComplaint";
    Long TASK_TYPE_CHANGE_REQUEST_SETUP = 3L;
    String WORK_TEAM = "G";
    String WORK_FLOW = "T";
    String WORK_CHANNEL = "I";
    String WORK_CORE_NETWORK = "C";
    String WEB_SERVICE_QLCTKT = "QLCTKT";
    String WEB_SERVICE_NIMS = "NIMS";
    String WEB_SERVICE_BCCS_CM = "BCCS_CM";
    String WEB_SERVICE_BCCS_IM = "BCCS_IM";
    String WEB_SERVICE_NTMS = "NTMS";
    String TASK_TYPE_CM_CHANNEL_REMOVE = "4";
    String TASK_TYPE_CM_CHANNEL_CLOSE = "1";
    String TASK_TYPE_CM_CHANNEL_OPEN = "2";
    String TASK_TYPE_CM_CHANNEL_CHANGE = "0";
    Long CHANGE_BANDWITH = 11L;
    Long OPEN_FLOW_TRANSMISSION_BCCS = 2L;
    Long BLOCK_FLOW_TRANSMISSION_BCCS = 1L;
    Long CANCEL_FLOW_TRANSMISSION_BCCS = 4L;
    Long CHANGE_BANDWITH_BCCS = 0L;
    Long TASK_TYPE_CHANGE_STATION = 10L;
    String TASK_TYPE_CM_CREATE_VOID = "9";
    String TASK_TYPE_CM_DELETE_VOID = "10";
    String TASK_TYPE_CM_CHANGE_REQUEST = "20";
    String TASK_TYPE_CM_CHANGE_SPEED = "0";
    String CM_TASK_TYPE_REVOKE = "3";
    String CM_TASK_THC = "9";
    String UPDATE_FININSH_TASK = "";
    Long TASK_RESULT_SUCCESS = 1l;
    Long TASK_RESULT_FAIL = 0l;
    Long TASK_RESULT_CHANGE = 2l;
    Long CM_CATV_TYPE_BLOCK = 1l;
    Long CM_CATV_TYPE_UP = 2l;
    Long CM_CATV_TYPE_DOWN = 4l;
    String TASK_RESULT_LASTCHECK_CATV = "1";
    Long TASK_REASON_SaiQuyCachTrienKhai = 1L;
    Long TASK_REASON_SaiVatTuTrienKhai = 2L;
    Long TASK_REASON_TYPE_CoPhatSinhVatTu = 1L;
    Long TASK_REASON_TYPE_KhongPhatSinhVatTu = 0L;
    String TASK_RESULT_HauKiemOK = "0";
    String TASK_RESULT_HauKiemNOK_KhongPhatSinhVatTu = "1";
    String TASK_RESULT_HauKiemNOK_CoPhatSinhVatTu = "3";
    String TASK_RESULT_HauKiemNOK_SaiVatTuTrienKhai = "2";
    String TASK_RESULT_HauKiemNOK_SaiNhieuTruongHop = "4";
    Long USE_CONNECTOR = 0L;
    Long NO_USE_CONNECTOR = 1L;
    Long AUTO_INFRA = 2L;
    Long SOURCE_TYPE_CM = 0L;
    Long SOURCE_TYPE_QLCTKT = 1L;
    Long SOURCE_TYPE_NIMS = 2L;
    String modificationType = "10";
    String modificationType_getInfoPPPoe = "126";
    String WEB_SERVICE_QOS = "QOS";
    String WEB_SERVICE_BCCS_CC = "BCCS_CC";
    String WEB_SERVICE_HOTLINE = "HOTLINE";
    String QLCTKT = "QLCTKT";
    String NIMS = "NIMS";
    String BCCS_IM = "BCCS_IM";
    String BCCS_CM = "BCCS_CM";
    String CM = "CM";
    String QLCTKT_AUTO = "QLCTKT_AUTO";
    String PAYMENT = "PAYMENT";
    String CM_PAYMENT = "CM_PAYMENT";
    String GATE_PRO = "GATE_PRO";
    String NPMS = "NPMS";
    String TKTU = "TKTU";
    String QOS = "QOS";
    String SOC = "SOC";
    Long KV1 = 338L;
    Long KV2 = 340L;
    Long KV3 = 342L;
    String CM_CHANGE_DEVICE = "CM_CHANGE_DEVICE";
    String CM_VENDOR = "CM_VENDOR";
    Long PROGRESS_FINISH_REOPEN_OTHER_TASK = 33L;
    String RESPONSE_CM_RC_SUCCESS = "RC_SUCCESS";
    String RESPONSE_CM_RC_SUCCESS_AFTER_SALE = "1";
    String RESPONSE_CM_NOT_FIND_REQUEST_AFTER_SALE = "WS_RESPONSE_CODE_NO_FIND_REQUEST";
    String RESPONSE_CM_NOT_FIND_REQUEST = "LIST_SUB_REQUEST_NULL";
    String RESPONSE_CM_RC_NOT_EXIST_ACCOUNT = "LIST_SUB_REQUEST_NULL";
    String RESPONSE_CM_VIEW_ACCOUNT_SUCCESS = "0";
    Long CM_STATUS_ACTIVESUB_SUCCESS = 3L;
    String CM_PROGRESS_ACTIVESUB_SUCCESS = "6";
    Long CM_STATUS_FINISH_TASK_SUCCESS = 4L;
    String CM_PROGRESS_FINISH_TASK_SUCCESS = "8";
    Long CM_STATUS_FINISH_TASK_SUCCESS_CHANNEL = 3L;
    String CM_PROGRESS_FINISH_TASK_SUCCESS_CHANNEL = "8";
    Long CM_STATUS_CHANGE_PORT_SUCCESS = 4L;
    Long CM_STATUS_FINISH_SUCCESS = 2L;
    String UPLOAD_FOLDER = "../../../QLCTKT_REPORT/";
    String PRO_PROCESS_CODE_VIEW_PPPOE = "040040";
    String PRO_PROCESS_CODE_VIEW_NGN = "800006";
    String PRO_MESSAGE_TYPE_VIEW_PPPOE = "1900";
    String PRO_PROCESS_CODE_VIEW_XMIO = "041004";
    String PRO_MESSAGE_TYPE_VIEW_XMIO = "1900";
    String PRO_PROCESS_CODE_CHANGE_DEVICE_CODE_XMIO = "041005";
    String PRO_MESSAGE_TYPE_CHANGE_DEVICE_CODE_XMIO = "1900";
    Long UPDATE_TASK_FROM_WEB = 1L;
    Long UPDATE_TASK_FROM_MOBILE = 2L;
    String xMIO = "xMIO";
    Integer GATE_PRO_DECLARE_FTTH = 1;
    Integer GATE_PRO_DECLARE_IPTV = 5;
    Integer GATE_PRO_DECLARE_MULTIN_SCREEN = 20;
    Integer GATE_PRO_DECLARE_Voip = 26;
    Integer GATE_PRO_DECLARE_FTTH_IPTV = 6;
    Integer GATE_PRO_PAUSE_FTTH = 2;
    Integer GATE_PRO_CONTINUE_FTTH = 3;
    Integer GATE_PRO_REMOVE_FTTH = 4;
    Integer GATE_PRO_REMOVE_IPTV = 7;
    Integer GATE_PRO_REMOVE_FTTH_IPTV = 8;
    Long ACTION_CHANGE_PORT = 2L;
    Long ACTION_CHANGE_DELOY_ADD = 1L;
    Long CHANGE_PORT_SUCCESS = 1L;
    Long CHANGE_PORT_FAILSE = 2L;
    Long CHANGE_PORT_REMOVE_OLD = 3L;
    Long CHANGE_PORT_WAITTING_DEVICE = 4L;
    Long CHANGE_PORT_ROLLBACK_NIMS = 5L;
    Long CHANGE_PORT_SENDSUB_NIMS = 6L;
    Long CHANGE_PORT_TIMEOUT_CM = 7L;
    Long CHANGE_ONU_UCTT_SUCCESS = 8L;
    Long CHANGE_ONU_UCTT_FAIL = 9L;
    Long CHANGE_ONU_SUCCESS = 10L;
    Long CHANGE_ONU_FAIL = 11L;
    Long CHANGE_STB_UCTT_SUCCESS = 12L;
    Long CHANGE_STB_UCTT_FAIL = 13L;
    Long CHANGE_STB_SUCCESS = 14L;
    Long CHANGE_STB_FAIL = 15L;
    Long CHANGE_STB_ROLLBACK_NIMS = 16L;
    Long CHANGE_PORT_TYPE_CHANGE_PORT = 1L;
    Long CHANGE_PORT_TYPE_RESET_PORT = 2L;
    Long CHANGE_PORT_TYPE_CHANGE_DEVICE = 3L;
    Long CHANGE_PORT_TYPE_CHANGE_ONU_UCTT = 4L;
    Long CHANGE_PORT_TYPE_CHANGE_STB_UCTT = 5L;
    Long CHANGE_PORT_TYPE_CHANGE_INFRA_NIMS = 6L;
    Long CHANGE_PORT_TYPE_CHANGE_STB = 7L;
    Long CHANGE_ONU_GUAR_SUCCESS = 16L;
    Long CHANGE_ONU_GUAR_FAIL = 17L;
    Long CHANGE_STB_GUAR_SUCCESS = 18L;
    Long CHANGE_STB_GUAR_FAIL = 19L;
    Long CHANGE_PORT_TYPE_CHANGE_ONU_GUAR = 20L;
    Long CHANGE_PORT_TYPE_CHANGE_STB_GUAR = 21L;
    String TEMPLATE_IMPORT_CHANGE_PORT = "/WEB-INF/jsp/template/ImportChangePortSub.xls";
    String TEMPLATE_IMPORT_RESET_PORT = "/WEB-INF/jsp/template/ImportResetPortSub.xls";
    Long CHANGE_PORT = 1L;
    Long RESET_PORT = 2L;
    Long SINGLE_SUB = 1L;
    Long MULTI_SUB = 2L;
    Long CHANGE_INFRA_NIMS = 6L;
    String UPDATE_APPOINTMENT_DAY = "UPDATE_APPOINTMENT_DAY";
    Long APPOINTMENT_CANCEL = -1L;
    Long APPOINTMENT_WAITING = 0L;
    Long APPOINTMENT_OK = 1L;
    Long APPOINTMENT_RESULT_CC_OK = 1L;
    Long APPOINTMENT_RESULT_CC_NOK = 0L;
    Long APPOINTMENT_RESULT_CC_NOKTG = 2L;
    Long INFRA_TYPE_CCN = 1L;
    Long INFRA_TYPE_FCN = 3L;
    Long INFRA_TYPE_CCN_1 = 2L;
    Long INFRA_TYPE_GPON = 4L;
    String INFRA_TYPE_GPON_STR = "GPON";
    String INFRA_TYPE_AON_STR = "AON";
    String INFRA_TYPE_FCN_STR = "FCN";
    String INFRA_TYPE_CCN_STR = "CCN";
    String INFRA_TYPE_CATV_STR = "CATV";
    Long GLINE_EXIST = 1L;
    Long GLINE_NO_EXIST = 0L;
    Long IS_RETAKE = 1L;
    Long IS_NOT_RETAKE = 0L;
    Long GPON_INTERNET = 2L;
    Long GPON_TV = 1L;
    Long GROUP_INFRA_CONNECTOR = 1L;
    String STAFF_NVKT = "NVL2";
    String STAFF_NVCD = "NVL3";
    String STAFF_MANAGEMENT = "NVL1";
    String STAFF_OUTSIDER = "CT";
    String MAJOR_STAFF_NVKD = "NVKD";
    Long WORK_TYPE_DEPLOY = 1L;
    Long WORK_TYPE_MAINTAIN = 2L;
    Long WORK_TYPE_TROUBLE = 3L;
    Long WORK_TYPE_CR = 4L;
    Long WORK_TYPE_TROUBLE_VT = 5L;
    Long WORK_TYPE_NEW_STATION = 6L;
    Long FINISH_WORK = 1L;
    Long UPDATE_REASON = 1L;
    Long UPDATE_REASON_DELAY = 1L;
    Long UPDATE_CM = 2L;
    Long RESEASON_RE_UPDATE_INFRA = 3L;
    Long RESEASON_RE_UPDATE_CABLE = 4L;
    Long RESEASON_TASK_DELAY = 1L;
    Long RESEASON_SERVEY_FASLE = 2L;
    Long RESEASON_PROGRESS_UPDATE_FINISH = 13L;
    Long RESEASON_UN_IMPOSSIBLE_DEPLOY = 15L;
    Long RESEASON_SERVEY_K_SERVICE_FAIL = 9L;
    Long SERVIVE_TYPE_TRUNG_KE = 9L;
    String MAJOR_STAFF_NVL2 = "NVL2";
    String MAJOR_STAFF_NVL3 = "NVL3";
    Long TASK_TYPE_UPDATE_COMPLETE = 3L;
    Long TASK_TYPE_UPDATE_PROCESS = 2L;
    Long TASK_TYPE_UPDATE_TEMP = 1L;
    Long TASK_TYPE_UPDATE_OPEN_TEMP = 4L;
    Long TASK_TYPE_UPDATE_CHECK_TROUBLE = 5L;
    String MULTI_SCREEN_1_CHIEU = "MST_1C";
    String MULTI_SCREEN_2_CHIEU = "MST_2C";
    String MULTI_SCREEN_NoRF = "MST_NoRF";
    Long DEVICE_1_PORT = 1L;
    Long DEVICE_2_PORT = 2L;
    String CHANGE_DEVICE_ONU = "CHANGE_DEVICE_ONU";
    Long CM_VERSION_BCCS_SHOP = 2L;
    Long CC_VERSION2_BCCS_SHOP = 2L;
    Long DANG_TRIEN_KHAI = 0L;
    Long DA_TRIEN_KHAI = 1L;
    Long RESEASON_SERVEY_HOTLINE_SERVICE_FAIL = 11L;
    Long RESEASON_SERVEY_UN_IMPOSSILE_DEPLOY = 15L;
    Long TYPE_RETAKE_VENDOR = 3L;
    Long TYPE_CHANGE_ONT = 1L;
    Long TYPE_CHANGE_SFU = 2L;
    Long TASK_ID_CHANGE_MODEL = 53L;
    String PASS_PORT = "PASSPORT";
    Long TASK_E1_IN_TRUNK = 1L;
    Long IS_UPLINK = 1L;
    Long SUB_TYPE_E1_IN_TRUNK = 1L;
    Long SUB_TYPE_PSTN_IN_TRUNK = 2L;
    Long CHANGE_ONU = 97L;
    Long CHANGE_STB = 98L;
    Long UN_IMPOSSIBLE_DEPLOY = 65L;
    Long CONFIG = 1L;
    Long SURVEY_HOTLINE_SUCCESS = 43L;
    Long SURVEY_HOTLINE_FALSE = 44L;
    String IM_RETURN_SUCCESS = "FIND_SUCCESS";
    String IM_RETURN_SUCCESS_NOT_FOUND = "NOT_FOUND";
    String IM_RETURN_FAIL = "EXCEPTION";
    Long CHANGE_PORT_GPON = 1000L;
    Long CHANGE_DEVICE = 1001L;
    Long CHANGE_DEVICE_UCTT = 1002L;
    Long CHANGE_DEVICE_BH = 1003L;
    Long PROGRESS_CHANGE_UCTT = 24L;
    Long TASK_TYPE_ROUTTING = 62L;
    String PORT_GARBAGE = "portGarbage";
    Long LONG_SERVICCE_TYPE_FTTH = 6L;
    Long LONG_SERVICCE_TYPE_NEXTTV = 5L;
    Long LONG_SERVICCE_TYPE_IPPHONE = 7L;
    Long LONG_MULTISCREEN_2 = 20L;
    Long LONG_SERVICCE_TYPE_THC = 13L;
    String SERVICCE_TYPE_AP = "AP";
  }

  interface TASK_FUNCTION {

    String EXTRA_TIME = "1";
  }

  interface TASK_REASON {

    Long OTHER_REASON = 18l;
    Long MERCHANDISE_ARIS = 1L;
    Long MERCHANDIS_NO_ARIS = 0L;
    Long REASON_ACTIVESUB = 98L;
    Long REASON_ACTIVESUB_FALSE = 99L;
  }

  interface INFRA_TYPE {

    Long CCN = 1l;
    Long FCN = 3l;
    Long CATV = 2l;
    Long GPON = 4l;
    Long DEVICE = -1l;
    Long DEVICE_ADSL = -2l;
    Long DEVICE_PSTN = -3l;
  }

  interface PATH {

    String ATTACH_VERSION_FILE_PATH = "versionMobile";
    String ATTACH_GS_FILE_PATH = "googleService";
  }

  interface TYPE_SEARCH {

    String TYPE_ACCOUNT = "1";
    String TYPE_STATIONCODE = "2";
    String TYPE_TEL = "3";
    String TYPE_SERVICETYPE = "4";
  }

  interface TASK_UPDATE_TYPE {

    Long ACTIVE = 1L;
    Long FINISHTASK = 2L;
    Long UPDATE_PERIPHERAL = 3L;
    Long RECALLMERCHANDISE = 4L;
    Long UPDATE_SERVEY = 5L;
    Long UPDATE_REASON = 6L;
    Long UPDATE_DOCUMENT = 7L;
  }

  interface QUERY {

    interface QUERY_DATA_LEVEL {

      Long DEFAULT = 0L;
      Long HOURLY = 1L;
      Long DAILY = 2L;
    }

    interface QUERY_STATUS {

      Long ACTIVE = 1L;
      Long INACTIVE = 0L;
    }

    interface QUERY_KEEP_CUR_MAP {

      Integer YES = 0;
      Integer NO = 1;
    }
  }

  interface ORDER_ID {

    Long NONE = -1L;
    Long ALL = -2L;
  }

  interface ORDER_PROGRESS {

    Long ALL = -2L;
    Long WAIT = 1L;
    Long CANCEL = 2L;
    Long APPROVED = 3L;
    Long RECEIVED = 4L;
  }

  interface ORDER_STATUS {

    Long ACTIVE = 1L;
    Long CANCEL = 0L;
  }

  interface CAPACITY {

    Long QLCTKT = 1L;
    Long RESULT_OK = 1L;
    Long RESULT_NOK = 0L;
    Long RESULT_CHECK_NTMS = 2L;
    Long RESULT_PENDING = 3L;
    Long RESULT_NOT_CONNECT_QOS = 4L;
    Long CAPACITY_CONNECT_QOS = 1L;
    Long CAPACITY_NOTCONNECT_QOS = 0L;
    Long STATUS_ACTIVE = 1L;
    Long STATUS_NOT_ACTIVE = 0L;
    Long SUCCESS = 1L;
    Long FAIL = 0L;
    String ERROR_CODE_FGPON = "F_GPON_ChuaXacDinhLoi";
    String ERROR_CODE_AON = "AON-CXĐNN";
    String RESULT_FAIL = "1";
    String RESULT_ERROR = "2";
    String ERROR_CODE_OK = "ChuaXacDinhLoi";
  }

  interface CONFIG {

    String TIME_SLEEP_CHECK_CAPACITY_QOS = "TIME_SLEEP_CHECK_CAPACITY_QOS";
    String SEND_LOCATION_2_BCCS = "SEND_LOCATION_2_BCCS";
    String SEND_LOCATION_2_BCCS_TRUE = "1";
    String TIME_NOT_CONNECT_CAPACITY_QOS = "TIME_NOT_CONNECT_CAPACITY_QOS";
    String TIME_COMPLAINT_TO_CC = "TIME_COMPLAINT_TO_CC";
    String CONFIG_MERCHANDISE_LENGTH_CABLE = "CONFIG_MERCHANDISE_LENGTH_CABLE";
    String CONFIG_ACTIVE_SUB_TIMEOUT_TIME = "CONFIG_ACTIVE_SUB_TIMEOUT_TIME";
    String CONFIG_COMPLAIN_LEVEL_ID = "CONFIG_COMPLAIN_LEVEL_ID";
    String CONFIG_COMPLAIN_ACCEPT_TYPE_ID = "CONFIG_COMPLAIN_ACCEPT_TYPE_ID";
    String CONFIG_COMPLAIN_CHANNEL_ID = "CONFIG_COMPLAIN_CHANNEL_ID";
    String CONFIG_COMPLAIN_TYPE_ID = "CONFIG_COMPLAIN_TYPE_ID";
    String CC2_COMPLAIN_ParentGroupId = "CC2_COMPLAIN_ParentGroupId";
    String CC2_COMPLAIN_ProbGroupId = "CC2_COMPLAIN_ProbGroupId";
    String CC2_COMPLAIN_ProbTypeId_50 = "CC2_COMPLAIN_configProbTypeId_50";
    String CC2_COMPLAIN_ProbTypeId_2 = "CC2_COMPLAIN_configProbTypeId_2";
    String CC2_COMPLAIN_ProbTypeId_3 = "CC2_COMPLAIN_configProbTypeId_3";
    String CC2_COMPLAIN_ProbTypeId_1000 = "CC2_COMPLAIN_configProbTypeId_1000";
    String CC2_COMPLAIN_ProbTypeId_1001 = "CC2_COMPLAIN_configProbTypeId_1001";
    String CC2_COMPLAIN_ProbTypeId_1002 = "CC2_COMPLAIN_configProbTypeId_1002";
    String CC2_COMPLAIN_ProbTypeId_1003 = "CC2_COMPLAIN_configProbTypeId_1003";
    String CC2_COMPLAIN_ProbTypeId_Other = "CC2_COMPLAIN_configProbTypeId_Other";
    String DEFAULT_CRITICAL_POINT_EXTEND_TIME = "DEFAULT_CRITICAL_POINT_EXTEND_TIME";
    String DEFAULT_EXTEND_TIME = "DEFAULT_EXTEND_TIME";
    String MAX_DEPLOY_TIME = "MAX_DEPLOY_TIME";
    String DEFAULT_REASON_CHECK_RESULT = "DEFAULT_REASON_CHECK_RESULT";
    String CONFIG_CHANGE_DEVICE_UCTT_TIME = "CONFIG_CHANGE_DEVICE_UCTT_TIME";
    String portGarbage_distric = "portGarbage_distric";
  }

  interface STATUS_COMPLAINT {

    Long UPDATE = 0L;
    Long UPDATE_FINISH = 1L;
    Long UPDATE_TASK_FINISH = 2L;
  }

  interface DEVICE_ERORR_CODE {

    Long IS_DEVICE = 1L;
    Long NOT_IS_DEVICE = 2L;
    Long IS_UPDATE_TASK = 1L;
    Long NOT_IS_UPDATE_TASK = 2L;
    Long REQUEST_ACTIVE = 1L;
    Long REQUEST_CANCEL = 2L;
    Long REQUEST_PENDING = 3L;
    String MSG_ACITVE_UPDATE = "MSG_ACITVE_UPDATE";
    Long CANCEL = 3L;
  }

  interface TASK_WHILE_ACCOUNT {

    Long CREATE_COMPLAIN = 1L;
    Long IS_UPDATE_TASK = 1L;
    Long NOT_IS_UPDATE_TASK = 2L;
    Long CHECK_SOC = 2L;
  }

  interface GTMT_TYPE {

    String PHAT_SINH_CUOC = "SUBCRIBER_PSC_NUM";
    String ROI_MANG = "SUBCRIBER_RESIGN_NUM";
    String TB_QUAN_LY = "SUBCRIBER_MANAGEMENT_NUM";
    String CV_TRIEN_KHAI = "DEPLOY_EXIST_NUM";
    String XLSC_CO_DAY = "XLSC_CD_NUM";
  }

  interface LOCAL {

    String REQUEST_LOCALE = "request_locale";
    String CURRENT_LOCALE = "current_locale";
    String VT_LOCALE = "vt_locale";
  }

  interface HOTLINE {

    Long UPDATE_EXTEND_TIME = 1L;
    Long UPDATE_ASSIGN_TASK = 2L;
    Long UPDATE_PROGRESS_TASK = 3L;
    Long REQUEST_DETAIL = 4L;
    String EXTEND_TIME_APPOINTMENT = "SURVEY_HOTLINE_EXTRA_TIME";
    String EXTEND_TIME_APPROVE = "SURVEY_HOTLINE_COMFIRM_TASK_FALSE";
    String RESPONSE_HOTLINE_ERROR = "error";

  }

  interface LANGUAGE {

    String LANGUAGE_VI = "vi";
    String LANGUAGE_EN = "en";
    String COUNTRY_VN = "VN";
    String COUNTRY_US = "US";
    String FULL_VN_LOCALE = "vi_VN";
    String FULL_EN_LOCALE = "en_US";
  }

  interface SWAP_NGN {

    String TEMPLATE_LIST_SUB_NGN = "/WEB-INF/jsp/template/task/iphone.xlsx";
    String TEMPLATE_FILE_PLAN = "/WEB-INF/jsp/template/task/iphone_plan_swap.xlsx";
    String TEMPLATE_LIST_SUB_NGN_RESULT = "/WEB-INF/jsp/template/task/iphone_plan_swap_result.xlsx";
    String CREATE_PPPOE_SUCCESS = "1";
    String SWAP_SUCCESS = "1";
    String SWAP_FAILURE = "0";
    String SWAP_DONE = "789";
    Long PROGRESS_ASSIGNSTAFF = 2L;
    Long PROGRESS_FINISH = 3L;
    Long RESULT_FAILURE = 0L;
    Long RESULT_SUCCESS = 1L;
    Long RESULT_NO = 2L;
    Long RESULT_START = 2L;
  }

  interface CHANGE_DEVICE_SALE {

    Long CHANGE_ONU = 1L;
    Long CHANGE_STB = 2L;
    Long CHANGE_OTHER_DEVICE = 3L;
  }

  interface VALIDATE_TOKEN {

    Long RESULT_OK = 1L;
    Long RESULT_NOK = 0L;
  }

  interface STATION_POINT {

    Long STATION_QUALIFIED_MAX_POINT = 85L;
  }

  interface NPMS_SEARCH_TYPE {

    String GET_CELL_2G_VSMART = "GET_CELL_2G_VSMART";
    String GET_CELL_3G_VSMART = "GET_CELL_3G_VSMART";
    String GET_CELL_4G_VSMART = "GET_CELL_4G_VSMART";
    String GET_STATION_2G_VSMART = "GET_STATION_2G_VSMART";
    String GET_STATION_3G_VSMART = "GET_STATION_3G_VSMART";
    String GET_STATION_4G_VSMART = "GET_STATION_4G_VSMART";

  }

  interface IMAGE_STATUS {

    Long IMAGE_NEW = 1L;
    Long IMAGE_APPROVE = 2L;
    Long IMAGE_REJECT = 3L;
  }

  interface READ_STATUS {

    Long NEW = 1L;
    Long READED = 2L;
    Long DEACTIVE = 3L;
  }

  interface DEFAULT_TIME_OUT {

    Long CONNECT_TIME_OUT = 30L;
    Long READ_TIME_OUT = 30L;
    Long WRITE_TIME_OUT = 30L;

  }

  interface SIGNAL_TYPE_NIMS {

    Long TV = 1L;
    Long INTERNET = 2L;
    Long ALL = 3L;
  }

  interface RESPONSE_TYPE {
    String SUCCESS = "SUCCESS";
    String ERROR = "ERROR";
    String WARNING = "WARNING";
  }

  interface CHANGE_TYPE {
	String ADD = "ADD";
	String UPDATE = "UPDATE";
	String DELETE = "DELETE";
  }
}
