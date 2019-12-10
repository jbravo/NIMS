package com.viettel.nims.geo.controller.base;

import com.viettel.nims.geo.service.base.GenericDaoImpl;

import java.io.Serializable;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
public abstract class BaseController<T, PK extends Serializable> extends BaseViewController<T, PK>{

  public BaseController(GenericDaoImpl<T, PK> genericDao) {
    super(genericDao);
  }

  @PostMapping(value = "/saveOrUpdate")
  public String saveOrUpdate(@RequestBody T object) {
    try {
      object = this.genericDao.saveOrUpdate(object);
      jsonObject = buildResultJson(1, "success", gson.toJson(object));
    } catch (Exception e) {
      jsonObject = buildResultJson(0, "Error: " + e, null);
      log.error("Exception", e);
    }
    return jsonObject.toString();
  }

  @PostMapping(value = "/saveOrUpdateList")
  public String saveOrUpdateList(@RequestBody List<T> object) {
    try {
      this.genericDao.saveOrUpdate(object);
      jsonObject = buildResultJson(1, "success", gson.toJson(object));
    } catch (Exception e) {
      jsonObject = buildResultJson(0, "Error: " + e, null);
      log.error("Exception", e);
    }
    return jsonObject.toString();
  }


  @DeleteMapping(value = "/{id}")
  public String deleteById(@PathVariable PK id) {
    try {
      this.genericDao.delete(genericDao.findById( id));
      jsonObject = buildResultJson(1, "success", "Delete data success with id = " + id);
    } catch (Exception e) {
      jsonObject = buildResultJson(0, "Error: " + e, null);
      log.error("Exception", e);
    }
    return jsonObject.toString();
  }

  @PostMapping(value = "/delete")
  public String delete(@RequestBody T object) {
    try {
      this.genericDao.delete(object);
      jsonObject = buildResultJson(1, "success", "Delete data success: " + gson.toJson(object));
    } catch (Exception e) {
      jsonObject = buildResultJson(0, "Error: " + e, null);
      log.error("Exception", e);
    }
    return jsonObject.toString();
  }
}
