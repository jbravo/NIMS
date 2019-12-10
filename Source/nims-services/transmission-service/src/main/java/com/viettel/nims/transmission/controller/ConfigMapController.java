package com.viettel.nims.transmission.controller;

import com.viettel.nims.transmission.model.CfgMapUserBO;
import com.viettel.nims.transmission.service.ConfigMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by VTN-PTPM-NV64 on 9/13/2019.
 */
@RestController
//@RequestMapping(value = "${app.prefix}/configMap")
@RequestMapping(value = "/configMap")
public class ConfigMapController {

  @Autowired
  ConfigMapService configMapService;

  @PostMapping("/saveCommon")
  public ResponseEntity<?> saveConfigCommon(@RequestBody CfgMapUserBO cfgMapUserBO){
    return configMapService.saveConfigCommon(cfgMapUserBO);
  }

  @GetMapping("/getInfo/{id}")
  public ResponseEntity<?> getInfoConfigMap(@PathVariable("id") Long id){
    return configMapService.getInfoConfigMap(id);
  }
}
