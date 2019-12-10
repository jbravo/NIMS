package com.viettel.nims.transmission.controller;

import com.viettel.nims.transmission.service.TransmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by VTN-PTPM-NV64 on 8/26/2019.
 */
@RestController
@RequestMapping(value = "/map")
public class MapController {

  @Autowired
  TransmissionService transmissionService;

  @GetMapping("/find/cables/{id}")
  public ResponseEntity<?> findCablesById(@PathVariable("id") Long id){
    return transmissionService.findCablesById(id);
  }
}
