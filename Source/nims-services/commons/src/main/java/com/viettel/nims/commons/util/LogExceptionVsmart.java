package com.viettel.nims.commons.util;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogExceptionVsmart {

  public static void log(HttpServletRequest req, String action, Date startTime, Date endTime,
      Exception ex) {
    try {
      StringBuilder logStr = new StringBuilder();
      if (req != null) {
        String userName = req.getHeader("username");
        logStr.append(userName).append("|");
      } else {
        logStr.append("null|");
      }
      logStr.append(action).append("|");
      if (startTime != null) {
        logStr.append(DateTimeUtils.convertDateTimeToString(startTime, "dd/MM/yyyy HH:mm:ss:SSS"));
      }
      logStr.append("|");
      if (endTime != null) {
        logStr.append(DateTimeUtils.convertDateTimeToString(endTime, "dd/MM/yyyy HH:mm:ss:SSS"));
      }
      logStr.append("|");
      if (endTime != null && startTime != null) {
        logStr.append(endTime.getTime() - startTime.getTime());
      }
      logStr.append("|");
      if (req != null) {
        String input = getParameter(req).replace("\n", "")
            .replace("\r", "")
            .replace("[", "&1")
            .replace("]", "&2")
            .replace(",", "&3")
            .replace("|", "&4");
        logStr.append(input);
      }
      logStr.append("|");
      if (req != null) {
        logStr.append(req.getLocalAddr()).append(":").append(req.getLocalPort());
      }
      logStr.append("|");
      StackTraceElement[] lstParam = ex.getStackTrace();
      int length;
      if (lstParam.length > 0) {
        length = lstParam.length;
        if (length > 25) {
          length = 25;
        }
        String lineException =
            lstParam[0].getClassName() + ":" + lstParam[0].getMethodName() + "(" + lstParam[0]
                .getFileName() + ":" + lstParam[0].getLineNumber() + ")";
        logStr.append(lineException).append("|");
        StringBuilder contentEx = new StringBuilder(ex.toString() + " ");
        for (int i = 0; i < length; i++) {
          String exceptElement =
              lstParam[i].getClassName() + ":" + lstParam[i].getMethodName() + "(" + lstParam[i]
                  .getFileName() + ":" + lstParam[i].getLineNumber() + ") ";
          contentEx.append(exceptElement);
        }
        String exceptionContent = contentEx.toString().replace("\n", "")
            .replace("\r", "")
            .replace("[", "&1")
            .replace("]", "&2")
            .replace(",", "&3")
            .replace("|", "&4");
        logStr.append(exceptionContent);
      } else {
        logStr.append("|");
      }
      log.info(logStr.toString());
    } catch (Exception exx) {
      log.error("ERROR", exx);
    }
  }

  private static String getParameter(HttpServletRequest req) {
    Map<String, String[]> mapParameters;
    try {
      mapParameters = req.getParameterMap();
      if (mapParameters != null && !mapParameters.isEmpty()) {
        Object[] keyArr = mapParameters.keySet().toArray();
        StringBuilder result = new StringBuilder();
        for (Object str : keyArr) {
          result.append(str).append(":").append(mapParameters.get(str.toString())[0]).append(";");
        }
        if (result.length() > 1) {
          result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
      } else {
        return "";
      }
    } catch (Exception ex) {
      log.error("Error: ", ex);
      return "";
    }
  }
}
