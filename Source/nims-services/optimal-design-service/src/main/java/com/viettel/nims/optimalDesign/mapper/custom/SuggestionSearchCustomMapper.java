package com.viettel.nims.optimalDesign.mapper.custom;

import com.viettel.nims.optimalDesign.dto.SuggestionNewSiteDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionSearchDTO;
import com.viettel.nims.optimalDesign.entity.Suggestion;
import com.viettel.nims.optimalDesign.entity.SuggestionNewSite;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.ArrayList;
import java.util.List;

public class SuggestionSearchCustomMapper extends CustomMapper<Suggestion, SuggestionSearchDTO> {
  MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();

  public SuggestionSearchDTO toDtoBean(Suggestion suggestion) {
    mapperFactory.classMap(Suggestion.class, SuggestionSearchDTO.class)
        .field("suggestId", "suggestId")
        .field("suggestType", "suggestType")
        .field("suggestCode", "suggestCode")
        .field("deptId", "deptId")
        .field("suggestStatus", "suggestStatus")
        .field("userName", "userName")
        .field("createTime", "createTime")
        .field("updateTime", "updateTime")
        .field("rowStatus", "rowStatus")
        .register();
    MapperFacade mapper = mapperFactory.getMapperFacade();

    SuggestionSearchDTO searchDTO = mapper.map(suggestion, SuggestionSearchDTO.class);

    return searchDTO;
  }
  public List<SuggestionSearchDTO> toDtoBean(List<Suggestion> suggestionList){
    List<SuggestionSearchDTO> searchDTOList = new ArrayList<>();
    for(Suggestion suggestion: suggestionList){
      SuggestionSearchDTO searchDTO = toDtoBean(suggestion);
      searchDTO.setCatDepartmentDTO(null);
      searchDTOList.add(searchDTO);
    }
    return searchDTOList;
  }
}
