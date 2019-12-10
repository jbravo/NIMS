package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.CatDeviceTypeDTO;
import com.viettel.nims.optimalDesign.entity.CatDeviceType;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.CatDeviceTypeRepository;
import com.viettel.nims.optimalDesign.service.CatDeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rabbit on 9/3/2019.
 */
@Service
public class CatDeviceTypeServiceImpl implements CatDeviceTypeService {
  @Autowired
  private CatDeviceTypeRepository catDeviceTypeRepository;

  private BaseMapper<CatDeviceType, CatDeviceTypeDTO> catDeviceTypeMapper = new BaseMapper<>(CatDeviceType.class, CatDeviceTypeDTO.class);

  /**
   * @author quangdv
   * @since 03-09-2019
   * @param rowStatus trang thai ban ghi
   * @return danh sach theo trang thai ban ghi
   */
  @Override
  public List<CatDeviceTypeDTO> findAllByRowStatus(Integer rowStatus) {
    List<CatDeviceType> catDeviceTypes = catDeviceTypeRepository.findAllByRowStatus(rowStatus);
    return catDeviceTypeMapper.toDtoBean(catDeviceTypes);
  }
}
