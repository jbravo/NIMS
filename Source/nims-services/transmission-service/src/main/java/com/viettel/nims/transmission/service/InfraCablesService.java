package com.viettel.nims.transmission.service;

import com.viettel.nims.transmission.model.InfraCablesBO;
import org.springframework.http.ResponseEntity;

/**
 * Created by VTN-PTPM-NV64 on 8/30/2019.
 */
public interface InfraCablesService {

  public ResponseEntity<?> saveOrUpdateCable(InfraCablesBO infraCablesBO);

  ResponseEntity<?> findCableById(Long id);

}
