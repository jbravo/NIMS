package com.viettel.nims.geo.controller;

import com.viettel.nims.geo.model.common.BboxForm;
import com.viettel.nims.geo.service.InfraPoolServiceImpl;
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
 * Created by vtn-ptpm-nv78 on 10/1/2019.
 */
@Slf4j
@RestController
@RequestMapping("${app.prefix}/infraPools")
public class InfraPoolsController {

  @Autowired
  private InfraPoolServiceImpl poolService;

  @PostMapping("/find-by-bbox")
  public String findByBbox(@RequestBody BboxForm bboxForm) {
    long startTime = Calendar.getInstance().getTimeInMillis();
    String jsonString = null;
    try {
      Map<String, List<String>> mapData = new HashMap<>();
      List<String> dataCable = poolService.findByBbox(bboxForm);
      mapData.put("pools", dataCable);
      jsonString = StringUtils.convertDataToJson(jsonString, mapData);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    System.out.println("find by bbox is success");
    System.out.println("Server time : " + (Calendar.getInstance().getTimeInMillis() - startTime));
    return jsonString;
  }
}
