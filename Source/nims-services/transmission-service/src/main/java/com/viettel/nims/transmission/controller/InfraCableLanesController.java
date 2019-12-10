package com.viettel.nims.transmission.controller;

import com.viettel.nims.transmission.model.InfraCableLanesBO;
import com.viettel.nims.transmission.service.InfraCableLanesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by VTN-PTPM-NV64 on 8/27/2019.
 */
@RestController
@RequestMapping(value = "/cableLanes")
public class InfraCableLanesController {

  @Autowired
  InfraCableLanesService infraCableLanesService;

  @PostMapping("/add")
  public ResponseEntity<?> addCableLanes(@RequestBody InfraCableLanesBO infraCableLanesBO){
    return infraCableLanesService.addCableLanes(infraCableLanesBO);
  }

}
