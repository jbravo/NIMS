package com.viettel.nims.optimalDesign.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

  public static String date2ddMMyyyyHHMMss(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    return dateFormat.format(date);
  }
}
