package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.CfgMapUserBO;

/**
 * Created by VTN-PTPM-NV64 on 9/13/2019.
 */
public interface ConfigMapDao {

  void saveConfigCommon(CfgMapUserBO cfgMapUserBO);

  CfgMapUserBO getInfoConfigMap(Long id);
}
