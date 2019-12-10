package com.viettel.nims.geo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viettel.nims.geo.model.common.BboxForm;
import com.viettel.nims.geo.model.form.AjaxResponseBody;
import com.viettel.nims.geo.model.form.PillarForm;
import com.viettel.nims.geo.service.InfraPillarServiceImpl;
import com.viettel.nims.geo.utils.Constains;
import com.viettel.nims.geo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VTN-PTPM-NV55 on 9/26/2019.
 */

@Slf4j
@RestController
@RequestMapping("${app.prefix}/infraPillar")

public class InfraPillarController {
  @Autowired
  private InfraPillarServiceImpl infraPillarService;


  @PostMapping("/findByBbox")
  public String findByBbox(@RequestBody BboxForm bboxForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    String jsonString = null;
    try {
      Map<String, List<String>> mapData = new HashMap<>();
      List<String> dataPillar = infraPillarService.findByBbox(bboxForm);
      mapData.put("pillars", dataPillar);
      jsonString = StringUtils.convertDataToJson(jsonString, mapData);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    System.out.println("Server time : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    return jsonString;
  }

  @PostMapping("/update")
  public String update(@RequestBody PillarForm pillarForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    String jsonString = null;
    Map<String, String> mapData = new HashMap<>();
    try {
      int a = infraPillarService.updatePillar(pillarForm);
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
    int a = infraPillarService.delete(id);
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
  public String addNew(@RequestBody PillarForm pillarForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    AjaxResponseBody result = new AjaxResponseBody();
    String jsonString = null;
    Map<String, String> mapData = new HashMap<>();
    int a = infraPillarService.addNewPillar(pillarForm);
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
  public String findByData(@RequestBody PillarForm pillarForm) {

    long startTime = Calendar.getInstance().getTimeInMillis();
    String jsonString = null;
    try {
      Map<String, List<String>> mapData = new HashMap<>();
      List<String> dataPillar = infraPillarService.findByData(pillarForm);
      mapData.put("pillars", dataPillar);
      jsonString = StringUtils.convertDataToJson(jsonString, mapData);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    System.out.println("Server time : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    return jsonString;
  }
}
