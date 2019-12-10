package com.viettel.nims.commons.util;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "common-logUtils")
public class LogUtils {

  public static void logRequest(HttpServletRequest req, String userName, String action,
      Long responseTime,
      String... contentArr) {
    String requestId = req.getHeader("requestClientId");
    StringBuilder content = new StringBuilder();
    for (String str : contentArr) {
      content.append(str);
      content.append(" || ");
    }
    log.info("RequestId: " + requestId + " || Username: " + userName + " || Action: " + action
        + " || Time taken: " + responseTime + " ms || Content: " + content);
  }

  public static void logRequest(HttpServletRequest req, String action, String... contentArr) {
    String requestId = (String) req.getAttribute("requestClientId");
    StringBuilder content = new StringBuilder();
    for (String str : contentArr) {
      content.append(str);
      content.append(" || ");
    }
    log.info("RequestId: " + requestId + "|| Action: " + action + " || Content: " + content);
  }

}
