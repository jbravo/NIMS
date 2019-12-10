package com.viettel.nims.geo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.viettel.nims.geo.model.common.BboxForm;
import com.viettel.nims.geo.model.form.AjaxResponseBody;
import com.viettel.nims.geo.model.form.StationForm;
import com.viettel.nims.geo.service.InfraStationServiceImpl;
import com.viettel.nims.geo.utils.Constains;
import com.viettel.nims.geo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("${app.prefix}/infraStations")
public class InfraStationsController {

  protected Gson gson = new Gson();
  protected JSONObject jsonObject = new JSONObject();
  private JdbcOperations jdbcTemplate;

  @Autowired
  private InfraStationServiceImpl infraStationService;

  @PostMapping("/findByBbox")
  public String findByBbox(@RequestBody BboxForm bboxForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    String jsonString = null;
    try {
      Map<String, List<String>> mapData = new HashMap<>();
      List<String> dataCable = infraStationService.findByBbox(bboxForm);
      mapData.put("stations", dataCable);
      jsonString = StringUtils.convertDataToJson(jsonString, mapData);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    System.out.println("Server time : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    return jsonString;
  }

  @PostMapping("/update")
  public String update(@Valid @RequestBody StationForm stationForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    String jsonString = null;
    Map<String, String> mapData = new HashMap<>();
    try {
      int a = infraStationService.updateStation(stationForm);
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
  public String delete(@Valid @RequestBody Long id) {
    long startTime = Calendar.getInstance().getTimeInMillis();
    AjaxResponseBody result = new AjaxResponseBody();
    String jsonString = null;
    Map<String, String> mapData = new HashMap<>();
    int a = infraStationService.delete(id);
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
  public String addNew(@Valid @RequestBody StationForm stationForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    AjaxResponseBody result = new AjaxResponseBody();
    String jsonString = null;
    Map<String, String> mapData = new HashMap<>();
    int a = infraStationService.addNewStation(stationForm);
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
  public String findByData(@RequestBody StationForm stationForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    String jsonString = null;
    try {
      Map<String, List<String>> mapData = new HashMap<>();
      List<String> dataCable = infraStationService.findByData(stationForm);
      mapData.put("stations", dataCable);
      jsonString = StringUtils.convertDataToJson(jsonString, mapData);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Server time : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    return jsonString;
  }

  @PostMapping("/saveOrUpdate")
  public void saveOrUpdate(@RequestBody StationForm stationForm){
    if(stationForm.getAction().equals("edit")){
      infraStationService.editStation(stationForm);
    }else{
      infraStationService.addNewStation(stationForm);
    }
  }

}
