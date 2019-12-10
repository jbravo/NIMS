package com.viettel.nims.transmission.service;

import com.viettel.nims.transmission.model.CfgMapUserBO;
import org.springframework.http.ResponseEntity;

/**
 * Created by VTN-PTPM-NV64 on 9/16/2019.
 */
public interface ConfigMapService {

  //save cau hinh ban do chung
  public ResponseEntity<?> saveConfigCommon(CfgMapUserBO cfgMapUserBO);

  // lay thong thin cau hinh ban do chung
  ResponseEntity<?> getInfoConfigMap(Long id);
}
