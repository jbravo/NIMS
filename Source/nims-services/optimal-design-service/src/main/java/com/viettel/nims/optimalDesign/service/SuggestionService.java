package com.viettel.nims.optimalDesign.service;

import com.viettel.nims.optimalDesign.dto.DeleteSuggestionDTO;
import com.viettel.nims.optimalDesign.dto.SuggestStatusDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionSearchDTO;
import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;
import com.viettel.nims.optimalDesign.entity.Suggestion;

import java.util.Date;
import java.util.List;


public interface SuggestionService {
  List<SuggestionSearchDTO> getAllSSuggestions(SuggestionSearchDTO suggestionSearchDTO);

  List<SuggestionSearchDTO> getBySuggestionCode(String suggestionCode);

  String createNewSuggestion(SuggestionDTO suggestionDTO, String ip) throws Exception;

  void saveNewSuggestion(SuggestionDTO suggestionDTO, boolean isCreate) throws Exception;

  String updateNewSuggestion(SuggestionDTO suggestionDTO, String ip) throws Exception;

  public void changeSuggestStatus(Suggestion suggestion, int status) throws  Exception;

  ResponseDTO deleteListSuggesstion(DeleteSuggestionDTO deleteSuggestionDTO);

  Object get(Long suggestId);

  Long findNextIndexByStationCodeAndInCreateDate(String stationCode, Date createDate);

  String getSuggestCode(Integer suggestTypeId, String stationCode);

  public void approveDesign(Suggestion suggestion, SuggestStatusDTO suggestStatusDTO) throws Exception;

  public void changeStatusAfterUpdate(Suggestion suggestion) throws Exception;
}

