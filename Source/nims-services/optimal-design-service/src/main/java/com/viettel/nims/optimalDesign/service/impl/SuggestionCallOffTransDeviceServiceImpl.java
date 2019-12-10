package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOffTransDeviceDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOffTransDevice;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionCallOffTransDeviceRepository;
import com.viettel.nims.optimalDesign.service.SuggestionCallOffTransDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author rabbit on 9/8/2019.
 */
@Service
public class SuggestionCallOffTransDeviceServiceImpl implements SuggestionCallOffTransDeviceService {
  @Autowired
  private SuggestionCallOffTransDeviceRepository suggestionCallOffTransDeviceRepository;

  private BaseMapper<SuggestionCallOffTransDevice, SuggestionCallOffTransDeviceDTO> SuggestionCallOffTransDeviceMapper = new BaseMapper<>(SuggestionCallOffTransDevice.class, SuggestionCallOffTransDeviceDTO.class);

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuggestionCallOffTransDeviceDTO save(SuggestionCallOffTransDeviceDTO SuggestionCallOffTransDeviceDTO) throws Exception {
    SuggestionCallOffTransDevice SuggestionCallOffTransDevice = SuggestionCallOffTransDeviceMapper.toPersistenceBean(SuggestionCallOffTransDeviceDTO);
    return SuggestionCallOffTransDeviceMapper.toDtoBean(suggestionCallOffTransDeviceRepository.save(SuggestionCallOffTransDevice));
  }

  @Override
  public SuggestionCallOffTransDeviceDTO get(Long suggestionCallOffId) {
    Optional<SuggestionCallOffTransDevice> opt = suggestionCallOffTransDeviceRepository.findById(suggestionCallOffId);
    if(opt.isPresent()){
      SuggestionCallOffTransDevice SuggestionCallOffTransDevice = opt.get();
      return SuggestionCallOffTransDeviceMapper.toDtoBean(SuggestionCallOffTransDevice);
    }
    return null;
  }
}
