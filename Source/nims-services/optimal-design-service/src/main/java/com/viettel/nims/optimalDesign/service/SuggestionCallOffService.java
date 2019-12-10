package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestStatusDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionCallOffDTO;

/**
 * @author rabbit on 9/7/2019.
 */
public interface SuggestionCallOffService {
  SuggestionCallOffDTO save(SuggestionCallOffDTO suggestionCallOffDTO) throws Exception;

  SuggestionCallOffDTO get(Long suggestionCallOffId);

  SuggestionCallOffDTO findBySuggestId(Long suggestId);

  public void setStationCode(Long id, SuggestStatusDTO suggestStatusDTO) throws Exception;
}
