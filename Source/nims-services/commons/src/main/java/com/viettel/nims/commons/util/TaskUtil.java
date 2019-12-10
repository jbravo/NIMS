package com.viettel.nims.commons.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class TaskUtil {

  public static String getStringFromObj(Object escapeObject) {
    return getStringFromObj(escapeObject, true);
  }

  public static String getStringFromObj(Object escapeObject, boolean limit) {
    String sb = "";
    try {
      if (escapeObject != null) {
        Gson gson = new Gson();
        sb = gson.toJson(escapeObject);
        if (limit && sb.length() > 2000) {
          sb = sb.substring(0, 2000);
        }
      }
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return sb;
  }

  public static String getIpLocal(HttpServletRequest req) {
    String ip = "0.0.0.0";
    try {
      ip = req.getLocalAddr();
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return ip;
  }
}
