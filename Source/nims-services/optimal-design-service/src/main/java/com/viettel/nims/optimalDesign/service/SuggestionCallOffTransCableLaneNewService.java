package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOffTransCableLaneNewDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOffTransCableLaneNew;

/**
 * @author rabbit on 9/8/2019.
 */
public interface SuggestionCallOffTransCableLaneNewService {
  SuggestionCallOffTransCableLaneNewDTO save(SuggestionCallOffTransCableLaneNewDTO suggestionCallOffTransCableLaneNewDTO) throws Exception;

  SuggestionCallOffTransCableLaneNewDTO get(Long suggestionCallOffId);
}
