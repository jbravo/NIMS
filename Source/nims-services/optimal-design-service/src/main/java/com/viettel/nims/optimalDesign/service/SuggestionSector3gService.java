package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestionSector3gDTO;

import java.util.List;

/**
 * @author rabbit on 9/7/2019.
 */
public interface SuggestionSector3gService {
  SuggestionSector3gDTO save(SuggestionSector3gDTO suggestionSector2gDTO) throws Exception;

  List<SuggestionSector3gDTO> save(List<SuggestionSector3gDTO> suggestionSector2gDTO) throws Exception;

  List<SuggestionSector3gDTO> gets(Long suggestionCallOffId);
}
