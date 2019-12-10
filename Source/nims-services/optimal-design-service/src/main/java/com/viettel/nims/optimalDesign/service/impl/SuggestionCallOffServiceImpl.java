package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestStatusDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionCallOffDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOff;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionCallOffRepository;
import com.viettel.nims.optimalDesign.service.SuggestionCallOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author rabbit on 9/7/2019.
 */
@Service
public class SuggestionCallOffServiceImpl implements SuggestionCallOffService {
  @Autowired
  private SuggestionCallOffRepository suggestionCallOffRepository;

  private BaseMapper<SuggestionCallOff, SuggestionCallOffDTO> suggestionCallOffMapper = new BaseMapper<>(SuggestionCallOff.class, SuggestionCallOffDTO.class);

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuggestionCallOffDTO save(SuggestionCallOffDTO suggestionCallOffDTO) throws Exception{

    SuggestionCallOff suggestionCallOff = suggestionCallOffMapper.toPersistenceBean(suggestionCallOffDTO);
    suggestionCallOff = suggestionCallOffRepository.save(suggestionCallOff);
    return suggestionCallOffMapper.toDtoBean(suggestionCallOff);
  }

  @Override
  public SuggestionCallOffDTO get(Long suggestionCallOffId) {
    Optional<SuggestionCallOff> opt = suggestionCallOffRepository.findById(suggestionCallOffId);
    if(opt.isPresent()){
      SuggestionCallOff suggestionCallOff = opt.get();
      return suggestionCallOffMapper.toDtoBean(suggestionCallOff);
    }
    return null;
  }

  @Override
  public SuggestionCallOffDTO findBySuggestId(Long suggestId) {
    SuggestionCallOff suggestionCallOff = suggestionCallOffRepository.findBySuggestId(suggestId);
    return  suggestionCallOffMapper.toDtoBean(suggestionCallOff);
  }

  @Override
  public void setStationCode(Long id, SuggestStatusDTO suggestStatusDTO) throws Exception {
    return;
  }


}
