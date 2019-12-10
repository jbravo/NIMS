package com.viettel.nims.commons.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class DateTimeUtils {

  public static String getPreviousMonthDateString() {
    Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -1);
    return formatter.format(cal.getTime());
  }

  public static String getTimeError() {
    String result = "";
    try {
      result = " Thời gian:" + convertDateToStringFull(new Date());
    } catch (Exception ex) {
      log.error("Error: ", ex);
    }
    return result;
  }

  public static String convertDateToStringFull(Date date) {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      return dateFormat.format(date);
    } catch (Exception e) {
      log.error("ERROR", e);
      return "";
    }
  }

  public static Date convertStringToTime(String date, String pattern) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    try {
      return dateFormat.parse(date);
    } catch (ParseException e) {
      log.debug("Date ParseException, string value:" + date);
    }
    return null;
  }

  public static String convertDateToString(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if (date == null) {
      return "";
    }
    try {
      return dateFormat.format(date);
    } catch (Exception e) {
      throw e;
    }
  }

  public static String convertDateTimeToString(Date date, String format) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    try {
      if (date != null) {
        return dateFormat.format(date);
      } else {
        return null;
      }
    } catch (Exception e) {
      throw e;
    }
  }

  public static String getSysDateTime() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    try {
      return dateFormat.format(calendar.getTime());
    } catch (Exception e) {
      throw e;
    }
  }

  public static Date convertStringToDateTime(String date) {
    String pattern = "dd/MM/yyyy HH:mm:ss";
    return convertStringToTime(date, pattern);
  }

  public static String convertDateToStringByPattern(Date date, String pattern) throws Exception {
    if (date == null) {
      return "";
    }
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      return dateFormat.format(date);
    } catch (Exception e) {
      log.error("ERROR", e);
      return "";
    }
  }

}
