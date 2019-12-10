package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionSector2gDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionSector2g;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionSector2gRepository;
import com.viettel.nims.optimalDesign.service.SuggestionSector2gService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author rabbit on 9/7/2019.
 */
@Service
public class SuggestionSector2gServiceImpl implements SuggestionSector2gService {
  @Autowired
  private SuggestionSector2gRepository suggestionSector2gRepository;

  private BaseMapper<SuggestionSector2g, SuggestionSector2gDTO> suggestionSector2gMapper = new BaseMapper<>(SuggestionSector2g.class, SuggestionSector2gDTO.class);

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<SuggestionSector2gDTO> save(List<SuggestionSector2gDTO> suggestionSector2gDTOS) throws Exception{
    List<SuggestionSector2g> list = suggestionSector2gMapper.toPersistenceBean(suggestionSector2gDTOS);
    return suggestionSector2gMapper.toDtoBean(suggestionSector2gRepository.saveAll(list));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuggestionSector2gDTO save(SuggestionSector2gDTO suggestionSector2gDTO) throws Exception{
    SuggestionSector2g suggestionSector2g = suggestionSector2gMapper.toPersistenceBean(suggestionSector2gDTO);
    return suggestionSector2gMapper.toDtoBean(suggestionSector2gRepository.save(suggestionSector2g));
  }

  @Override
  public List<SuggestionSector2gDTO> gets(Long suggestionCallOffId) {
    List<SuggestionSector2g> list = suggestionSector2gRepository.findAllBySuggestionCallOffId(suggestionCallOffId);
    return suggestionSector2gMapper.toDtoBean(list);
  }
}
