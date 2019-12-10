package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestionSector2gDTO;

import java.util.List;

/**
 * @author rabbit on 9/7/2019.
 */
public interface SuggestionSector2gService {
  SuggestionSector2gDTO save(SuggestionSector2gDTO suggestionSector2gDTO) throws Exception;

  List<SuggestionSector2gDTO> save(List<SuggestionSector2gDTO> suggestionSector2gDTO) throws Exception;

  List<SuggestionSector2gDTO> gets(Long suggestionCallOffId);
}
