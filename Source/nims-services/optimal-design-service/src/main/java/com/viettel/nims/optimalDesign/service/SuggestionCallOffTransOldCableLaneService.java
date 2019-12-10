package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOffTransOldCableLaneDTO;

/**
 * @author rabbit on 9/8/2019.
 */
public interface SuggestionCallOffTransOldCableLaneService {
  SuggestionCallOffTransOldCableLaneDTO save(SuggestionCallOffTransOldCableLaneDTO suggestionCallOffTransOldCableLaneDTO) throws Exception;

  SuggestionCallOffTransOldCableLaneDTO get(Long suggestionCallOffId);
}
