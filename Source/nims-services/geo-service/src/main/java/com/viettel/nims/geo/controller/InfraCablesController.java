package com.viettel.nims.geo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.viettel.nims.geo.model.common.BboxForm;
import com.viettel.nims.geo.model.form.AjaxResponseBody;
import com.viettel.nims.geo.model.form.CableForm;
import com.viettel.nims.geo.service.InfraCableServiceImpl;
import com.viettel.nims.geo.utils.Constains;
import com.viettel.nims.geo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("${app.prefix}/infraCables")
public class InfraCablesController {

  protected Gson gson = new Gson();
  protected JSONObject jsonObject = new JSONObject();
  private JdbcOperations jdbcTemplate;

  @Autowired
  private InfraCableServiceImpl infraCableService;


  @PostMapping("/findByBbox")
  public String findByBbox(@RequestBody BboxForm bboxForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    String jsonString = null;
    try {
      Map<String, List<String>> mapData = new HashMap<>();
      List<String> dataCable = infraCableService.findByBbox(bboxForm);
      mapData.put("cables", dataCable);
      jsonString = StringUtils.convertDataToJson(jsonString, mapData);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    System.out.println("Server time : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    return jsonString;
  }

  @PostMapping("/update")
  public String update(@RequestBody CableForm cableForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    String jsonString = null;
    Map<String, String> mapData = new HashMap<>();
    try {
      int a = infraCableService.updateCable(cableForm);
      if (a > 0) {
        mapData.put("result", Constains.RESULT.OK);
      } else {
        mapData.put("result", Constains.RESULT.NOK);
      }
    } catch (Exception e) {
      e.printStackTrace();
      mapData.put("result", Constains.RESULT.NOK);
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
      jsonString = mapper.writeValueAsString(mapData);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Server time : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    return jsonString;
  }

  @PostMapping("/delete")
  public String delete(@RequestBody Long id) {
    long startTime = Calendar.getInstance().getTimeInMillis();
    AjaxResponseBody result = new AjaxResponseBody();
    String jsonString = null;
    Map<String, String> mapData = new HashMap<>();
    int a = infraCableService.delete(id);
    if (a > 0) {
      mapData.put("result", Constains.RESULT.OK);
    } else {
      mapData.put("result", Constains.RESULT.NOK);
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
      jsonString = mapper.writeValueAsString(mapData);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Server time : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    return jsonString;
  }

  @PostMapping("/addNew")
  public String addNew(@RequestBody CableForm cableForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    AjaxResponseBody result = new AjaxResponseBody();
    String jsonString = null;
    Map<String, String> mapData = new HashMap<>();
    int a = infraCableService.addNewCable(cableForm);
    if (a > 0) {
      mapData.put("result", Constains.RESULT.OK);
    } else {
      mapData.put("result", Constains.RESULT.NOK);
    }
    ObjectMapper mapper = new ObjectMapper();
    try {

      jsonString = mapper.writeValueAsString(mapData);
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("Server time : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    return jsonString;
  }


  @PostMapping("/findByData")
  public String findByData(@RequestBody CableForm cableForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    String jsonString = null;
    try {
      Map<String, List<String>> mapData = new HashMap<>();
      List<String> dataCable = infraCableService.findByData(cableForm);
      mapData.put("cables", dataCable);
      jsonString = StringUtils.convertDataToJson(jsonString, mapData);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    System.out.println("Server time : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    return jsonString;
  }

  @PostMapping("/saveOrUpdate")
  public void saveOrUpdate(@RequestBody CableForm cableForm){
    if(cableForm.getStatus().equals("add")){
      infraCableService.addNewCable(cableForm);
    }else{
      infraCableService.editCable(cableForm);
    }
  }

}
