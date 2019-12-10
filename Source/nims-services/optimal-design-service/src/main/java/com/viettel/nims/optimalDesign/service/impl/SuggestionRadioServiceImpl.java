package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionRadioDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionRadio;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionRadioRepository;
import com.viettel.nims.optimalDesign.service.SuggestionRadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author rabbit on 9/6/2019.
 */
@Service
public class SuggestionRadioServiceImpl implements SuggestionRadioService {
  @Autowired
  private SuggestionRadioRepository suggestionRadioRepository;

  private BaseMapper<SuggestionRadio, SuggestionRadioDTO> suggestionRadioMapper = new BaseMapper<>(SuggestionRadio.class, SuggestionRadioDTO.class);

  @Override
  public String validate(SuggestionRadioDTO suggestionRadioDTO) {
    if(suggestionRadioDTO!=null){
      //Có xử lý vùng lõm không ? 0-Không xử lý, 1-Có xử lý
      if(suggestionRadioDTO.getHasConcavePoint()==null || suggestionRadioDTO.getHasConcavePoint()!=1)
        return null;

      if(suggestionRadioDTO.getHeightWithConcavePoint()>=1000000){
        return "suggestionRadio.heightWithConcavePoint.format.invalid";
      }

      if(suggestionRadioDTO.getConcavePointPopulation2g()<=0){
        return "suggestionRadio.concavePointPopulation2g.required";
      }

      if(suggestionRadioDTO.getConcavePointPopulation3gData()<=0){
        return "suggestionRadio.concavePointPopulation3gData.required";
      }

      if(suggestionRadioDTO.getConcavePointPopulation3gVoice()<=0){
        return "suggestionRadio.concavePointPopulation3gVoice.required";
      }

      if(suggestionRadioDTO.getConcavePointPopulation4g()<=0){
        return "suggestionRadio.concavePointPopulation4g.required";
      }
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void save(SuggestionRadioDTO suggestionRadioDTO) throws Exception{
    if(suggestionRadioDTO!=null) {
      SuggestionRadio suggestionRadio = suggestionRadioMapper.toPersistenceBean(suggestionRadioDTO);
      suggestionRadioRepository.save(suggestionRadio);
    }
  }

  @Override
  public SuggestionRadioDTO get(Long id) {
    if(id!=null) {
      Optional<SuggestionRadio> opt = suggestionRadioRepository.findById(id);
      if (opt.isPresent()) {
        SuggestionRadio suggestionRadio = opt.get();
        return suggestionRadioMapper.toDtoBean(suggestionRadio);
      }
    }
    return null;
  }
}
