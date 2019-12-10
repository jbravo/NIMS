package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestionRadioDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionRadio;

/**
 * @author rabbit on 9/6/2019.
 */
public interface SuggestionRadioService {
  String  validate(SuggestionRadioDTO suggestionRadioDTO);

  void save(SuggestionRadioDTO suggestionRadioDTO) throws Exception;

  SuggestionRadioDTO get(Long id);
}
