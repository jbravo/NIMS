package com.viettel.nims.geo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public class StringUtils {
  public static JSONObject buildResultJson(Object status, Object message, Object data) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", status);
    jsonObject.put("message", message);
    jsonObject.put("data", data);
    return jsonObject;
  }

  public static String convertDataToJson(String jsonString, Map<String, List<String>> mapData) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      jsonString = mapper.writeValueAsString(mapData);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return jsonString;
  }
}
