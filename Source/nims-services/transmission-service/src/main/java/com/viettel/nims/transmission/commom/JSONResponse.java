package com.viettel.nims.transmission.commom;

import net.sf.json.JSONObject;

public class JSONResponse {

  public static JSONObject buildResultJson(Object status, Object message, Object data) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", status);
    jsonObject.put("message", message);
    jsonObject.put("data", data);
    return jsonObject;
  }

}
