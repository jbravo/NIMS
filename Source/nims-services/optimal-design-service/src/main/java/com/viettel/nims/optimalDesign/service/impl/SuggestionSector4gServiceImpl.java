package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionSector4gDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionSector4g;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionSector4gRepository;
import com.viettel.nims.optimalDesign.service.SuggestionSector4gService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author rabbit on 9/7/2019.
 */
@Service
public class SuggestionSector4gServiceImpl implements SuggestionSector4gService {
  @Autowired
  private SuggestionSector4gRepository suggestionSector4gRepository;

  private BaseMapper<SuggestionSector4g, SuggestionSector4gDTO> suggestionSector4gMapper = new BaseMapper<>(SuggestionSector4g.class, SuggestionSector4gDTO.class);

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<SuggestionSector4gDTO> save(List<SuggestionSector4gDTO> suggestionSector4gDTOS) throws Exception{
    List<SuggestionSector4g> list = suggestionSector4gMapper.toPersistenceBean(suggestionSector4gDTOS);
    return suggestionSector4gMapper.toDtoBean(suggestionSector4gRepository.saveAll(list));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuggestionSector4gDTO save(SuggestionSector4gDTO suggestionSector4gDTO) throws Exception{
    SuggestionSector4g suggestionSector4g = suggestionSector4gMapper.toPersistenceBean(suggestionSector4gDTO);
    return suggestionSector4gMapper.toDtoBean(suggestionSector4gRepository.save(suggestionSector4g));
  }

  @Override
  @Transactional
  public List<SuggestionSector4gDTO> gets(Long suggestionCallOffId) {
    List<SuggestionSector4g> list = suggestionSector4gRepository.findAllBySuggestionCallOffId(suggestionCallOffId);
    return suggestionSector4gMapper.toDtoBean(list);
  }
}
