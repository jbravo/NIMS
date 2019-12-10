package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOffTransCableLaneNewDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOffTrans;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOffTransCableLaneNew;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionCallOffTransCableLaneNewRepository;
import com.viettel.nims.optimalDesign.service.SuggestionCallOffTransCableLaneNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author rabbit on 9/8/2019.
 */
@Service
public class SuggestionCallOffTransCableLaneNewServiceImpl implements SuggestionCallOffTransCableLaneNewService {
  @Autowired
  private SuggestionCallOffTransCableLaneNewRepository suggestionCallOffTransCableLaneNewRepository;

  private BaseMapper<SuggestionCallOffTransCableLaneNew, SuggestionCallOffTransCableLaneNewDTO> suggestionCallOffTransCableLaneNewMapper = new BaseMapper<>(SuggestionCallOffTransCableLaneNew.class, SuggestionCallOffTransCableLaneNewDTO.class);
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuggestionCallOffTransCableLaneNewDTO save(SuggestionCallOffTransCableLaneNewDTO suggestionCallOffTransCableLaneNewDTO) throws Exception {
    SuggestionCallOffTransCableLaneNew suggestionCallOffTransCableLaneNew = suggestionCallOffTransCableLaneNewMapper.toPersistenceBean(suggestionCallOffTransCableLaneNewDTO);
    return suggestionCallOffTransCableLaneNewMapper.toDtoBean(suggestionCallOffTransCableLaneNewRepository.save(suggestionCallOffTransCableLaneNew));
  }

  @Override
  public SuggestionCallOffTransCableLaneNewDTO get(Long suggestionCallOffId) {
    Optional<SuggestionCallOffTransCableLaneNew> opt = suggestionCallOffTransCableLaneNewRepository.findById(suggestionCallOffId);
    if(opt.isPresent()){
      SuggestionCallOffTransCableLaneNew suggestionCallOffTransCableLaneNew = opt.get();
      return suggestionCallOffTransCableLaneNewMapper.toDtoBean(suggestionCallOffTransCableLaneNew);
    }
    return null;
  }
}
