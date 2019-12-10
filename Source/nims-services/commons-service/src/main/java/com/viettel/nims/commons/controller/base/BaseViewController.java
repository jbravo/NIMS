package com.viettel.nims.commons.controller.base;

import com.google.gson.Gson;
import com.viettel.nims.commons.service.base.GenericDaoImpl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
public abstract class BaseViewController<T, PK extends Serializable> {

  protected Gson gson = new Gson();
  protected JSONObject jsonObject = new JSONObject();

  protected GenericDaoImpl<T, PK> genericDao;

  @Autowired
  public BaseViewController(GenericDaoImpl<T, PK> genericDao){
    this.genericDao =  genericDao;
  }

  @GetMapping(value = "/findAll")
  public String getFindAll() {
    jsonObject = buildResultJson(1, "success", gson.toJson(this.genericDao.findList()));
    return jsonObject.toString();
  }

  @GetMapping(value = "/countAll")
  public String countAll() {
    jsonObject = buildResultJson(1, "success", this.genericDao.count());
    return jsonObject.toString();
  }

  @PostMapping(value = "/search")
  public String search(@RequestParam (name = "first", defaultValue = "0") int first, @RequestParam (name = "pageSize", defaultValue = "1") int pageSize,
      @RequestBody Map<String, Object> filters) {
    List<T> list;
    Map<String, Object> filter = null;
    Map<String, Object> sqlRes = null;
    LinkedHashMap<String, String> order = null;
    if (filters.get("filters") != null) {
      filter = (Map<String, Object>) filters.get("filters");
    }
    if (filters.get("sqlRes") != null) {
      sqlRes = (Map<String, Object>) filters.get("sqlRes");
    }
    if (filters.get("orders") != null) {
      order = (LinkedHashMap<String, String>) filters.get("orders");
    }
    try {
      list = genericDao.findList(first, pageSize, filter, sqlRes, order);
      jsonObject = buildResultJson(1, "success", gson.toJson(list));
    } catch (Exception e) {
      jsonObject = buildResultJson(0, "Error: " + e, null);
      log.error("Exception", e);
    }
    return jsonObject.toString();
  }

  @PostMapping(value = "/count")
  public String count(@RequestBody Map<String, Object> filters) {
    Map<String, Object> filter = null;
    Map<String, Object> sqlRes = null;
    if (filters.get("filters") != null) {
      filter = (Map<String, Object>) filters.get("filters");
    }
    if (filters.get("sqlRes") != null) {
      sqlRes = (Map<String, Object>) filters.get("sqlRes");
    }
    try {
      int count = genericDao.count(filter, sqlRes);
      jsonObject = buildResultJson(1, "success", count);
    } catch (Exception e) {
      jsonObject = buildResultJson(0, "Error: " + e, null);
      log.error("Exception", e);
    }
    return jsonObject.toString();
  }

  @GetMapping(value = "/{id}")
  public String findById(@PathVariable PK id) {
    try {
      jsonObject = buildResultJson(1, "success", gson.toJson(this.genericDao.findById(id)));
    } catch (Exception e) {
      jsonObject = buildResultJson(0, "Error: " + e, null);
      log.error("Exception", e);
    }
    return jsonObject.toString();
  }


  protected JSONObject buildResultJson(Object status, Object message, Object data){
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", status);
    jsonObject.put("message", message);
    jsonObject.put("data", data);
    return jsonObject;
  }
}
