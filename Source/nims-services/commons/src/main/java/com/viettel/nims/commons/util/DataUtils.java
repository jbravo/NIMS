package com.viettel.nims.commons.util;

import com.viettel.nims.commons.client.form.ErrorLoginOutput;

import javax.servlet.http.HttpServletRequest;

public class DataUtils {

  public static String genCommonLoginErrorCodeResponse(HttpServletRequest req, String errorCode,
                                                       String errorKey) {
    ErrorLoginOutput errorResponse = new ErrorLoginOutput();
    errorResponse.setLogin(-1L);
    errorResponse.setErrorCode(errorCode);
    errorResponse.setErrorMsg(errorCode + " : " +
        LanguageBundleUtils.getStringVsmart(req, errorKey) + DateTimeUtils.getTimeError());
    return JsonUtils.convertObjectToString(errorResponse);
  }

}
