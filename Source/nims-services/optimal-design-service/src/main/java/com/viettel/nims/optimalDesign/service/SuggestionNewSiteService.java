package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.SuggestionNewSiteDTO;

/**
 * @author rabbit on 8/23/2019.
 */
public interface SuggestionNewSiteService {

  String validate(SuggestionNewSiteDTO suggestionNewSiteDTO);

  void save(SuggestionNewSiteDTO suggestionNewSiteDTO) throws Exception;

  SuggestionNewSiteDTO get(Long id);

  public void setStationCode(String code, Long id) throws Exception;
}
