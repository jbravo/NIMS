package com.viettel.nims.transmission.service;

import com.viettel.nims.transmission.model.InfraCableLanesBO;
import org.springframework.http.ResponseEntity;

/**
 * Created by VTN-PTPM-NV64 on 8/27/2019.
 */
public interface InfraCableLanesService {

  // Them moi tuyen cap
  public ResponseEntity<?> addCableLanes(InfraCableLanesBO cableLanes);
}
