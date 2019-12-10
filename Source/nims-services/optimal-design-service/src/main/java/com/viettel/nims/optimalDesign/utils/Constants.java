package com.viettel.nims.optimalDesign.utils;

public class Constants {
  public enum SUGGESTION_TYPE_NAME{
    NEW_SITE,COSSITE,MOVE_SITE,MODIFY_ANTEN,ADD_CELL,SWAP_DEVICE,DESTROY
  }
  public interface SUGGESTION_TYPE {
    public static final int NEW_SITE = 0;
    public static final int COSSITE = 1;
    public static final int MOVE_SITE = 2;
    public static final int MODIFY_ANTEN = 3;
    public static final int ADD_CELL = 4;
    public static final int SWAP_DEVICE = 5;
    public static final int DESTROY = 6;
  }

  public interface AUDIT_TYPE {
    public static final int DO_NOTHING = 3;
    public static final int ACCREDITATION = 1;
    public static final int ANNOUNCED = 2;
  }

  public interface STATION_TECH_TYPE {
    public static final String []NAMES = {"2G","3G", "4G", "5G", "2G+3G", "2G+4G", "2G+5G", "3G+4G", "3G+5G", "4G+5G", "2G+3G+4G", "3G+4G+5G", "2G+4G+5G", "2G+3G+4G+5G"};
    public static final Integer []CODES = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};
  }

  public interface RADIO_LOCATION_TYPE {
    public static final int TPT = 1;
    public static final int TTH = 2;
    public static final int NTDB = 3;
    public static final int NTMN = 4;
  }

  public interface RADIO_PILLAR_TYPE {
    public static final int COC = 1;
    public static final int DAY_CO = 2;
    public static final int TU_DUNG = 3;
    public static final int KHAC = 4;
    public static final int THAP = 5;
    public static final int NGUY_TRANG = 6;
    public static final int KHONG_CO = 7;
  }

  public interface OPTION {
    public static final int YES = 1;
    public static final int NO = 0;
  }
  public interface STATUS_CODE{
    public static final String INVALID = "INVALID_DATA";
    public static final String SUCCESS = "SUCCESS";
    public static  final String ERROR= "ERROR";

  }
  public  static int MAX_DATE_COMPARE=365;

  public interface OBJECT_TYPE{
    public static final int TRAM = 0;
    public static final int COT = 1;
    public static final int BE = 2;
    public static final int CAP = 3;
    public static final int TUYEN_CAP = 4;
    public static final int SOI_CAP = 5;
    public static final int CAT_ITEM = 6;
    public static final int LOAI_COT = 7;
    public static final int LOAI_BE = 8;
    public static final int LOAI_CAP = 9;
  }

  public interface ACTION{
    public static final short ADD = 1;
    public static final short EDIT = 2;
    public static final short DELETE = 3;
    public static final short APPROVE = 4;
    public static final short REJECT = 5;
  }
  public interface SEARCH{
    public static  final String SIMPLE ="SIMPLE";
  }

}
