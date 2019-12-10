package com.viettel.nims.transmission.controller;

import com.viettel.nims.transmission.model.InfraCablesBO;
import com.viettel.nims.transmission.service.InfraCablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by VTN-PTPM-NV64 on 8/30/2019.
 */
@RestController
@RequestMapping("/infraCables")
public class InfraCablesController {

  @Autowired
  InfraCablesService infraCablesService;

  @PostMapping("/save")
  public ResponseEntity<?> saveOrUpdateInfraCables(@RequestBody InfraCablesBO infraCablesBO){
    return infraCablesService.saveOrUpdateCable(infraCablesBO);
  }

  @GetMapping("/find/cables/{id}")
  public ResponseEntity<?> findCableById(@PathVariable("id") Long id){
     return infraCablesService.findCableById(id);
  }

}
