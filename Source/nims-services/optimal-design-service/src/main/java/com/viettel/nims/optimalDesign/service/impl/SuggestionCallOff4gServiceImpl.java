package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOff4gDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionSector4gDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOff4g;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionCallOff4gRepository;
import com.viettel.nims.optimalDesign.repository.custom.RepositoryCustomUtils;
import com.viettel.nims.optimalDesign.service.SuggestionCallOff4gService;
import com.viettel.nims.optimalDesign.service.SuggestionSector4gService;
import com.viettel.nims.optimalDesign.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author rabbit on 8/23/2019.
 */

@Service
public class SuggestionCallOff4gServiceImpl implements SuggestionCallOff4gService {
  @Autowired
  private SuggestionCallOff4gRepository suggestionCallOff4gRepository;

  private BaseMapper<SuggestionCallOff4g, SuggestionCallOff4gDTO> suggestionCallOff4gMapper = new BaseMapper<>(SuggestionCallOff4g.class, SuggestionCallOff4gDTO.class);

  @Autowired
  private SuggestionSector4gService suggestionSector4gService;

  @Autowired
  RepositoryCustomUtils repositoryCustomUtils;

  @Override
  public String validate(SuggestionCallOff4gDTO suggestionCallOff4gDTO, Boolean isCreate) {
    if (suggestionCallOff4gDTO != null && !isCreate) {
      Optional<SuggestionCallOff4g> callOff4g = suggestionCallOff4gRepository.findById(suggestionCallOff4gDTO.getSuggestionCallOffId());

      if (isCreate && callOff4g.isPresent()) {
        return "suggestionCallOff4g.exists";
      } else if (!isCreate && !callOff4g.isPresent()) {
        return "suggestionCallOff4g.notexists";
      }

      return null;
    }

    return null;
  }

  /**
   * @param suggestionCallOff4gDTOS
   * @return ma thong bao loi-khong loi tra ve null
   */
  @Override
  public String validate(List<SuggestionCallOff4gDTO> suggestionCallOff4gDTOS) {

    if (suggestionCallOff4gDTOS == null) return null;

    for (SuggestionCallOff4gDTO item : suggestionCallOff4gDTOS) {
      if (item != null) {
        Boolean isCreate = item.getSuggestionCallOffId() == null ? true : false;
        String messageCode = validate(item, isCreate);
        if (messageCode != null) return messageCode;
      }
    }

    return null;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<SuggestionCallOff4gDTO> save(List<SuggestionCallOff4gDTO> suggestionCallOff4gDTOS, Long suggestId) throws Exception{
    boolean isCreate = suggestId==null ? true:false;

    //Xoa cac SuggestionCallOff4g va SuggestionSector4g ton tai trong db
    if(!isCreate) {
      //#Lay cac SUGGESTION_CALL_OFF_ID cua SuggestionCallOff4g ton tai trong db theo suggestId
      //#SELECT SUGGESTION_CALL_OFF_ID FROM SUGGESTION_CALL_OFF_4G
      // WHERE SUGGEST_ID = :suggestId
      List<Long> suggestCallOffIds = Convert.convertListObjectsToListLong(
          repositoryCustomUtils.getAllValueOfColumnByParentId("SUGGESTION_CALL_OFF_4G", "SUGGESTION_CALL_OFF_ID", "SUGGEST_ID", suggestId)
      );
      //#Xoa cac SuggestionCallOff4g va SuggestionSector4g
      if (suggestCallOffIds.size() > 0) {
        //Delete all suggestCallOff4g by ids: suggestionCallOffIds
        //#DELETE FROM SUGGESTION_CALL_OFF_4G
        // WHERE SUGGESTION_CALL_OFF_ID IN (:suggestCallOffIds[0],...,:suggestCallOffIds[n-1])
        repositoryCustomUtils.deleteAllInListId("SUGGESTION_CALL_OFF_4G", "SUGGESTION_CALL_OFF_ID", suggestCallOffIds);
        //#DELETE FROM SUGGESTION_SECTOR_4G
        // WHERE SUGGESTION_CALL_OFF_ID IN (suggestCallOffIds[0],...,suggestCallOffIds[n-1])
        repositoryCustomUtils.deleteAllByListParentId("SUGGESTION_SECTOR_4G", "SUGGESTION_CALL_OFF_ID", suggestCallOffIds);
      }
    }

    List<SuggestionCallOff4g> suggestionCallOff4gs = suggestionCallOff4gMapper.toPersistenceBean(suggestionCallOff4gDTOS);
    List<SuggestionCallOff4gDTO> suggestionCallOff4gDTOSSaved = suggestionCallOff4gMapper.toDtoBean(suggestionCallOff4gRepository.saveAll(suggestionCallOff4gs));

    int size = suggestionCallOff4gDTOS.size();

    //save multi list suggestionSector4g
    for (int i = 0; i < size; ++i) {
      List<SuggestionSector4gDTO> suggestionSector4gDTOS = new ArrayList<>();

      Long suggestionCallOffId = suggestionCallOff4gDTOSSaved.get(i).getSuggestionCallOffId();

      List<SuggestionSector4gDTO> list = suggestionCallOff4gDTOS.get(i).getSuggestionSector4gDTOList();

      if(list!=null)
      for (SuggestionSector4gDTO item : list) {
        item.setSuggestionCallOffId(suggestionCallOffId);
        suggestionSector4gDTOS.add(item);
      }

      suggestionSector4gDTOS = suggestionSector4gService.save(suggestionSector4gDTOS);
      suggestionCallOff4gDTOSSaved.get(i).setSuggestionSector4gDTOList(suggestionSector4gDTOS);
    }

    return suggestionCallOff4gDTOSSaved;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuggestionCallOff4gDTO save(SuggestionCallOff4gDTO suggestionCallOff4gDTO) throws Exception{
    SuggestionCallOff4g suggestionCallOff4g = suggestionCallOff4gMapper.toPersistenceBean(suggestionCallOff4gDTO);
    SuggestionCallOff4gDTO suggestionCallOff4gDTOSaved = suggestionCallOff4gMapper.toDtoBean(suggestionCallOff4gRepository.save(suggestionCallOff4g));

    //save list suggestionSector4g
    List<SuggestionSector4gDTO> suggestionSector4gDTOS = new ArrayList<>();

    Long suggestionCallOffId = suggestionCallOff4gDTOSaved.getSuggestionCallOffId();

    List<SuggestionSector4gDTO> list = suggestionCallOff4gDTO.getSuggestionSector4gDTOList();
    for (SuggestionSector4gDTO item : list) {
      item.setSuggestionCallOffId(suggestionCallOffId);
      suggestionSector4gDTOS.add(item);
    }

    suggestionSector4gDTOS = suggestionSector4gService.save(suggestionSector4gDTOS);
    suggestionCallOff4gDTOSaved.setSuggestionSector4gDTOList(suggestionSector4gDTOS);

    return suggestionCallOff4gDTO;
  }

  @Override
  public List<SuggestionCallOff4gDTO> gets(Long suggestId) {
//    List<SuggestionCallOff4g> list = new ArrayList<>();
    List<SuggestionCallOff4g> list = suggestionCallOff4gRepository.findAllBySuggestId(suggestId);
    List<SuggestionCallOff4gDTO> suggestionCallOff4DTOS = suggestionCallOff4gMapper.toDtoBean(list);

    for(SuggestionCallOff4gDTO suggestionCallOff4gDTO: suggestionCallOff4DTOS){
      List<SuggestionSector4gDTO> suggestionSector4gDTOS = suggestionSector4gService.gets(suggestionCallOff4gDTO.getSuggestionCallOffId());

      suggestionCallOff4gDTO.setSuggestionSector4gDTOList(suggestionSector4gDTOS);
    }
    return suggestionCallOff4DTOS;
  }

  @Override
  public void setCode(String code, Long id) throws Exception {
    Optional<SuggestionCallOff4g> suggestionCallOff4g = suggestionCallOff4gRepository.findBySuggestId(id);
    if(!suggestionCallOff4g.isPresent())
      return;
    suggestionCallOff4g.get().setCabinetCode(code);
    suggestionCallOff4gRepository.save(suggestionCallOff4g.get());
  }
}
