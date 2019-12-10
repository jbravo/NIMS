package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOffTransOldCableLaneDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionCallOffTransOldCableLaneDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOffTransOldCableLane;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOffTransOldCableLane;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionCallOffTransOldCableLaneRepository;
import com.viettel.nims.optimalDesign.service.SuggestionCallOffTransOldCableLaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author rabbit on 9/8/2019.
 */
@Service
public class SuggestionCallOffTransOldCableLaneServiceImpl implements SuggestionCallOffTransOldCableLaneService {
  @Autowired
  private SuggestionCallOffTransOldCableLaneRepository suggestionCallOffTransOldCableLaneRepository;

  private BaseMapper<SuggestionCallOffTransOldCableLane, SuggestionCallOffTransOldCableLaneDTO> suggestionCallOffTransOldCableLaneMapper = new BaseMapper<>(SuggestionCallOffTransOldCableLane.class, SuggestionCallOffTransOldCableLaneDTO.class);

  @Override
  public SuggestionCallOffTransOldCableLaneDTO save(SuggestionCallOffTransOldCableLaneDTO SuggestionCallOffTransOldCableLaneDTO) throws Exception {
    SuggestionCallOffTransOldCableLane SuggestionCallOffTransOldCableLane = suggestionCallOffTransOldCableLaneMapper.toPersistenceBean(SuggestionCallOffTransOldCableLaneDTO);
    return suggestionCallOffTransOldCableLaneMapper.toDtoBean(suggestionCallOffTransOldCableLaneRepository.save(SuggestionCallOffTransOldCableLane));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuggestionCallOffTransOldCableLaneDTO get(Long suggestionCallOffId) {
    Optional<SuggestionCallOffTransOldCableLane> opt = suggestionCallOffTransOldCableLaneRepository.findById(suggestionCallOffId);
    if(opt.isPresent()){
      SuggestionCallOffTransOldCableLane SuggestionCallOffTransOldCableLane = opt.get();
      return suggestionCallOffTransOldCableLaneMapper.toDtoBean(SuggestionCallOffTransOldCableLane);
    }
    return null;
  }
}
