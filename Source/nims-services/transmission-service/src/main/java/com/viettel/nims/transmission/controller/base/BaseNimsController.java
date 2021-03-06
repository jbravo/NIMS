package com.viettel.nims.transmission.controller.base;

import net.sf.json.JSONObject;

public abstract class BaseNimsController {

  protected JSONObject buildResultJson(Object status, Object message, Object data) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", status);
    jsonObject.put("message", message);
    jsonObject.put("data", data);
    return jsonObject;
  }

}
