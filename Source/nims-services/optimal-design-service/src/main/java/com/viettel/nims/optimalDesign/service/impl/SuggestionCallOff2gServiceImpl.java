package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.SuggestionCallOff2gDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionSector2gDTO;
import com.viettel.nims.optimalDesign.entity.SuggestionCallOff2g;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.repository.SuggestionCallOff2gRepository;
import com.viettel.nims.optimalDesign.repository.custom.RepositoryCustomUtils;
import com.viettel.nims.optimalDesign.service.SuggestionCallOff2gService;
import com.viettel.nims.optimalDesign.service.SuggestionSector2gService;
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
public class SuggestionCallOff2gServiceImpl implements SuggestionCallOff2gService {
  @Autowired
  private SuggestionCallOff2gRepository suggestionCallOff2gRepository;

  private BaseMapper<SuggestionCallOff2g, SuggestionCallOff2gDTO> suggestionCallOff2gMapper = new BaseMapper<>(SuggestionCallOff2g.class, SuggestionCallOff2gDTO.class);

  @Autowired
  private SuggestionSector2gService suggestionSector2gService;

  @Autowired
  RepositoryCustomUtils repositoryCustomUtils;

  @Override
  public String validate(SuggestionCallOff2gDTO suggestionCallOff2gDTO, Boolean isCreate) {
    if (suggestionCallOff2gDTO != null && !isCreate) {
      Optional<SuggestionCallOff2g> callOff2g = suggestionCallOff2gRepository.findById(suggestionCallOff2gDTO.getSuggestionCallOffId());

      if (isCreate && callOff2g.isPresent()) {
        return "suggestionCallOff2g.exists";
      } else if (!isCreate && !callOff2g.isPresent()) {
        return "suggestionCallOff2g.notexists";
      }

      return null;
    }

    return null;
  }

  /**
   * @param suggestionCallOff2gDTOS
   * @return ma thong bao loi-khong loi tra ve null
   */
  @Override
  public String validate(List<SuggestionCallOff2gDTO> suggestionCallOff2gDTOS) {

    if (suggestionCallOff2gDTOS == null) return null;

    for (SuggestionCallOff2gDTO item : suggestionCallOff2gDTOS) {
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
  public List<SuggestionCallOff2gDTO> save(List<SuggestionCallOff2gDTO> suggestionCallOff2gDTOS, Long suggestId) throws Exception{
    boolean isCreate = suggestId==null ? true:false;

    //Xoa cac SuggestionCallOff2g va SuggestionSector2g ton tai trong db
    if(!isCreate) {
        //#Lay cac SUGGESTION_CALL_OFF_ID cua SuggestionCallOff2g ton tai trong db theo suggestId
        //#SELECT SUGGESTION_CALL_OFF_ID FROM SUGGESTION_CALL_OFF_2G
        // WHERE SUGGEST_ID = :suggestId
        List<Long> suggestCallOffIds = Convert.convertListObjectsToListLong(
            repositoryCustomUtils.getAllValueOfColumnByParentId("SUGGESTION_CALL_OFF_2G", "SUGGESTION_CALL_OFF_ID", "SUGGEST_ID", suggestId)
        );

        //#Xoa cac SuggestionCallOff2g va SuggestionSector2g
        if (suggestCallOffIds.size() > 0) {
          //Delete all suggestCallOff2g by ids: suggestionCallOffIds
          //#DELETE FROM SUGGESTION_CALL_OFF_2G
          // WHERE SUGGESTION_CALL_OFF_ID IN (:suggestCallOffIds[0],...,:suggestCallOffIds[n-1])
          repositoryCustomUtils.deleteAllInListId("SUGGESTION_CALL_OFF_2G", "SUGGESTION_CALL_OFF_ID", suggestCallOffIds);
          //#DELETE FROM SUGGESTION_SECTOR_2G
          // WHERE SUGGESTION_CALL_OFF_ID IN (suggestCallOffIds[0],...,suggestCallOffIds[n-1])
          repositoryCustomUtils.deleteAllByListParentId("SUGGESTION_SECTOR_2G", "SUGGESTION_CALL_OFF_ID", suggestCallOffIds);
        }
    }

    List<SuggestionCallOff2g> suggestionCallOff2gs = suggestionCallOff2gMapper.toPersistenceBean(suggestionCallOff2gDTOS);
    List<SuggestionCallOff2gDTO> suggestionCallOff2gDTOSSaved = suggestionCallOff2gMapper.toDtoBean(suggestionCallOff2gRepository.saveAll(suggestionCallOff2gs));

    int size = suggestionCallOff2gDTOS.size();

    //save multi list suggestionSector2g
    for (int i = 0; i < size; ++i) {

      List<SuggestionSector2gDTO> suggestionSector2gDTOS = new ArrayList<>();

      Long suggestionCallOffId = suggestionCallOff2gDTOSSaved.get(i).getSuggestionCallOffId();

      List<SuggestionSector2gDTO> list = suggestionCallOff2gDTOS.get(i).getSuggestionSector2gDTOList();

      if(list!=null)
        for (SuggestionSector2gDTO item : list) {
          item.setSuggestionCallOffId(suggestionCallOffId);
          suggestionSector2gDTOS.add(item);
        }

      suggestionSector2gDTOS = suggestionSector2gService.save(suggestionSector2gDTOS);
      suggestionCallOff2gDTOSSaved.get(i).setSuggestionSector2gDTOList(suggestionSector2gDTOS);
    }

    return suggestionCallOff2gDTOSSaved;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SuggestionCallOff2gDTO save(SuggestionCallOff2gDTO suggestionCallOff2gDTO) throws Exception{
    SuggestionCallOff2g suggestionCallOff2g = suggestionCallOff2gMapper.toPersistenceBean(suggestionCallOff2gDTO);
    SuggestionCallOff2gDTO suggestionCallOff2gDTOSaved = suggestionCallOff2gMapper.toDtoBean(suggestionCallOff2gRepository.save(suggestionCallOff2g));

    //save list suggestionSector2g
    List<SuggestionSector2gDTO> suggestionSector2gDTOS = new ArrayList<>();

    Long suggestionCallOffId = suggestionCallOff2gDTOSaved.getSuggestionCallOffId();

    List<SuggestionSector2gDTO> list = suggestionCallOff2gDTO.getSuggestionSector2gDTOList();
    for (SuggestionSector2gDTO item : list) {
      item.setSuggestionCallOffId(suggestionCallOffId);
      suggestionSector2gDTOS.add(item);
    }

    suggestionSector2gDTOS = suggestionSector2gService.save(suggestionSector2gDTOS);
    suggestionCallOff2gDTOSaved.setSuggestionSector2gDTOList(suggestionSector2gDTOS);

    return suggestionCallOff2gDTOSaved;
  }

  @Override
  public List<SuggestionCallOff2gDTO> gets(Long suggestId) {
    List<SuggestionCallOff2g> list = suggestionCallOff2gRepository.findAllBySuggestId(suggestId);
    List<SuggestionCallOff2gDTO> suggestionCallOff2gDTOS = suggestionCallOff2gMapper.toDtoBean(list);

    for(SuggestionCallOff2gDTO suggestionCallOff2gDTO: suggestionCallOff2gDTOS){
      List<SuggestionSector2gDTO> suggestionSector2gDTOS = suggestionSector2gService.gets(suggestionCallOff2gDTO.getSuggestionCallOffId());

      suggestionCallOff2gDTO.setSuggestionSector2gDTOList(suggestionSector2gDTOS);
    }

    return suggestionCallOff2gDTOS;
  }

  @Override
  public void setCode(String code, Long id) throws Exception {
    SuggestionCallOff2g suggestionCallOff2g = suggestionCallOff2gRepository.findBySuggestId(id);
    if(suggestionCallOff2g == null)
      return;
    suggestionCallOff2g.setCabinetCode(code);
    suggestionCallOff2gRepository.save(suggestionCallOff2g);
  }
}
