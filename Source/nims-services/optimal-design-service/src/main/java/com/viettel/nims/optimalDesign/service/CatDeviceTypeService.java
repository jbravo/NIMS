package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.CatDeviceTypeDTO;

import java.util.List;

/**
 * @author rabbit on 9/3/2019.
 */
public interface CatDeviceTypeService {
  List<CatDeviceTypeDTO> findAllByRowStatus(Integer rowStatus);
}
