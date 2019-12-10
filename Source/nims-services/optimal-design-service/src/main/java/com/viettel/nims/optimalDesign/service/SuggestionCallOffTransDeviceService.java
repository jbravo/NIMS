package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOffTransDeviceDTO;

/**
 * @author rabbit on 9/8/2019.
 */
public interface SuggestionCallOffTransDeviceService {
  SuggestionCallOffTransDeviceDTO save(SuggestionCallOffTransDeviceDTO suggestionCallOffTransDeviceDTO) throws Exception;

  SuggestionCallOffTransDeviceDTO get(Long suggestCallOffId);
}
