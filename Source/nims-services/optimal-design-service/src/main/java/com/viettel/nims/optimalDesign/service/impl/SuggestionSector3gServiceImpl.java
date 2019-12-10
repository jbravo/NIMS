package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionSector3gDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionSector3g;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionSector3gRepository;
import com.viettel.nims.optimalDesign.service.SuggestionSector3gService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author rabbit on 9/7/2019.
 */
@Service
public class SuggestionSector3gServiceImpl implements SuggestionSector3gService {
  @Autowired
  private SuggestionSector3gRepository suggestionSector3gRepository;

  private BaseMapper<SuggestionSector3g, SuggestionSector3gDTO> suggestionSector3gMapper = new BaseMapper<>(SuggestionSector3g.class, SuggestionSector3gDTO.class);

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<SuggestionSector3gDTO> save(List<SuggestionSector3gDTO> suggestionSector3gDTOS) throws Exception{
    List<SuggestionSector3g> list = suggestionSector3gMapper.toPersistenceBean(suggestionSector3gDTOS);
    return suggestionSector3gMapper.toDtoBean(suggestionSector3gRepository.saveAll(list));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuggestionSector3gDTO save(SuggestionSector3gDTO suggestionSector3gDTO) throws Exception{
    SuggestionSector3g suggestionSector3g = suggestionSector3gMapper.toPersistenceBean(suggestionSector3gDTO);
    return suggestionSector3gMapper.toDtoBean(suggestionSector3gRepository.save(suggestionSector3g));
  }

  @Override
  public List<SuggestionSector3gDTO> gets(Long suggestionCallOffId) {
    List<SuggestionSector3g> list = suggestionSector3gRepository.findAllBySuggestionCallOffId(suggestionCallOffId);
    return suggestionSector3gMapper.toDtoBean(list);
  }
}
