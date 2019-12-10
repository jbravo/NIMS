package com.viettel.nims.commons.util;

import com.google.gson.Gson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("common-commonresponeentity")
@Slf4j
public class CommonResponeEntity {

  private JsonUtils jsonUtils;
  private Gson gson;

  @Autowired
  public CommonResponeEntity(JsonUtils jsonUtils, Gson gson) {
    this.jsonUtils = jsonUtils;
    this.gson = gson;
  }

  public ResponseEntity<String> getStringResponseEntityNoContent(HttpServletRequest req,
      String code) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders
        .set("message", LanguageBundleUtils.getStringVsmart(req, code));
    log.info(LanguageBundleUtils.getStringVsmart(req, code));
    return new ResponseEntity<String>("", responseHeaders,
        HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<String> commonListResponse(HttpServletRequest req,
      List result) {
    if (result != null && result.size() > 0) {
      return new ResponseEntity<String>(gson.toJson(result), new HttpHeaders(), HttpStatus.OK);
    } else {
      return getStringResponseEntityNoContent(req, "COMMON_NO_RESULT");
    }
  }

  public ResponseEntity<String> commonResponse(HttpServletRequest req,
      Object result) {
    if (result != null) {
      return new ResponseEntity<String>(gson.toJson(result), new HttpHeaders(), HttpStatus.OK);
    } else {
      return getStringResponseEntityNoContent(req, "COMMON_NO_RESULT");
    }
  }
}
