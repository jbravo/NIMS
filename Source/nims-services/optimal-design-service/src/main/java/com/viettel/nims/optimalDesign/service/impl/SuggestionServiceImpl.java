package com.viettel.nims.optimalDesign.service.impl;

import com.viettel.nims.optimalDesign.dto.*;
import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;
import com.viettel.nims.optimalDesign.entity.CatDepartment;
import com.viettel.nims.optimalDesign.entity.Suggestion;
import com.viettel.nims.optimalDesign.entity.SuggestionNewSite;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import com.viettel.nims.optimalDesign.mapper.custom.SuggestionSearchCustomMapper;
import com.viettel.nims.optimalDesign.repository.CatDepartmentRepository;
import com.viettel.nims.optimalDesign.repository.SuggestionCallOffRepository;
import com.viettel.nims.optimalDesign.repository.SuggestionNewSiteRepository;
import com.viettel.nims.optimalDesign.repository.SuggestionRepository;
import com.viettel.nims.optimalDesign.service.*;
import com.viettel.nims.optimalDesign.utils.Constants;
import com.viettel.nims.optimalDesign.utils.ResponseUtils;
import com.viettel.nims.optimalDesign.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author quangdv on 8/19/2019.
 */

@Service
public class SuggestionServiceImpl implements SuggestionService {

  @Autowired
  private SuggestionRepository suggestionRepository;

  private BaseMapper<Suggestion, SuggestionDTO> suggestionMapper = new BaseMapper<>(Suggestion.class, SuggestionDTO.class);

  @Autowired
  private CatDepartmentRepository catDepartmentRepository;

  private BaseMapper<Suggestion, SuggestionSearchDTO> suggestionSearchMapper = new BaseMapper<>(Suggestion.class, SuggestionSearchDTO.class);

  @Autowired
  private SuggestionNewSiteService suggestionNewSiteService;

  @Autowired
  private SuggestionRadioService suggestionRadioService;

  @Autowired
  private SuggestionCallOff2gService suggestionCallOff2gService;

  @Autowired
  private SuggestionCallOff3gService suggestionCallOff3gService;

  @Autowired
  private SuggestionCallOff4gService suggestionCallOff4gService;

  @Autowired
  ReloadableResourceBundleMessageSource messageSource;

  BaseMapper<CatDepartment, CatDepartmentDTO> catDepartmentBaseMapper = new BaseMapper<>(CatDepartment.class, CatDepartmentDTO.class);

  @Autowired
  private SuggestionCallOffTransService suggestionCallOffTransService;

  @Autowired
  private SuggestionFileService suggestionFileService;

  @Autowired
  private SuggestionCallOffService suggestionCallOffService;

  @Autowired
  private SuggestionNewSiteRepository suggestionNewSiteRepository;

  @Autowired
  private SuggestionCallOffRepository suggestionCallOffRepository;

  private SuggestionSearchCustomMapper suggestionSearchCustomMapper = new SuggestionSearchCustomMapper();
  private final Logger log = LoggerFactory.getLogger(Suggestion.class);

  private static final String SYSTEM_ERROR = "system.error";

  @Override
  public List<SuggestionSearchDTO> getAllSSuggestions(SuggestionSearchDTO suggestionSearchDTO) {

    return suggestionRepository.findAllSuggestionSearch(suggestionSearchDTO);
//
//    List<Suggestion> suggestionList = suggestionRepository.findAllSuggestion(suggestionSearchDTO);
//
//    List<SuggestionSearchDTO> datas = suggestionSearchMapper.toDtoBean(suggestionList);
//    if(datas != null && datas.size() > 0){
//      datas.forEach(v -> {
//        v.setDeptName(catDepartmentRepository.findDeptNameById(v.getDeptId()));
//      });
//    }
//    return datas;
  }

  @Override
  public List<SuggestionSearchDTO> getBySuggestionCode(String suggestionCode) {
    List<Suggestion> suggestionList = suggestionRepository.findBySuggestCode(suggestionCode);
    List<SuggestionSearchDTO> datas = suggestionSearchCustomMapper.toDtoBean(suggestionList);
    if(datas != null && datas.size() > 0){
      datas.forEach(v -> {
        v.setDeptName(catDepartmentRepository.findDeptNameById(v.getDeptId().longValue()));
      });
    }
    return datas;
  }

  /** Luu thong tin De Xuat Moi
   * @param suggestionDTO
   * @return thanh cong hay khong thanh cong
   * @author quangdv
   * @since 9/07/2019
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void saveNewSuggestion(SuggestionDTO suggestionDTO, boolean isCreate) throws Exception{
    SuggestionCallOffDTO suggestionCallOffDTO = null;

    if(isCreate) {
      /*---THIET LAP GIA TRI MAC DINH KHI TAO MOI---*/
      //#Status mac dinh: 0-Tao moi
      suggestionDTO.setSuggestStatus(0);
      //#RowStatus mac dinh: 1-active
      suggestionDTO.setRowStatus(1);
      //#Ngay tao mac dinh: Ngay hien tai
      suggestionDTO.setCreateTime(new Date());

      //#Mac dinh: Loại đề xuất_mã nhà trạm đề xuất_Ngày tháng năm đề xuất_STT
      if (suggestionDTO.getSuggestionNewSiteDTO() != null) {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        String strDate = formatter.format(new Date());
        Long nextIndex = findNextIndexByStationCodeAndInCreateDate(suggestionDTO.getSuggestionNewSiteDTO().getStationCode(), new Date());
        String suggestCode = Constants.SUGGESTION_TYPE_NAME.NEW_SITE + "_" + suggestionDTO.getSuggestionNewSiteDTO().getStationCode() + "_" + strDate + "_" + nextIndex;
        suggestionDTO.setSuggestCode(suggestCode);
      }

      if (suggestionDTO.getSuggestionNewSiteDTO() != null) {
        suggestionDTO.getSuggestionNewSiteDTO().setCreateTime(new Date());
      }

      suggestionCallOffDTO = new SuggestionCallOffDTO();
      suggestionCallOffDTO.setCreateTime(new Date());
      //#Trang thai call-off mac dinh: 1-Tao moi
      suggestionCallOffDTO.setCallOffStatus(1);
    }else {
      /*---THIET LAP GIA TRI MAC DINH KHI CHINH SUA---*/
      //#Ngay chinh sua mac dinh: Ngay hien tai
      suggestionDTO.setUpdateTime(new Date());
      //Lay thong tin call off
      suggestionCallOffDTO = suggestionCallOffService.findBySuggestId(suggestionDTO.getSuggestId());
    }


    /*---LUU THONG TIN---*/
    //Luu thong tin de xuat
    Suggestion suggestion = suggestionMapper.toPersistenceBean(suggestionDTO);
    suggestion = suggestionRepository.save(suggestion);
//    SuggestionDTO suggestionDTOSaved = suggestionMapper.toDtoBean(suggestion);
    Long suggestId = suggestion.getSuggestId();

    //Luu thong tin call off
    //#set default value suggestId for suggestionCallOff
    if(isCreate) {
      suggestionCallOffDTO.setSuggestId(suggestion.getSuggestId());
    }
    if(suggestionCallOffDTO.getSuggestionCallOffId() != null) {
      suggestionCallOffRepository.deleteById(suggestionCallOffDTO.getSuggestionCallOffId());
    }
    suggestionCallOffDTO.setSuggestionCallOffId(null);//all way new ids
    SuggestionCallOffDTO suggestionCallOffDTOSaved = suggestionCallOffService.save(suggestionCallOffDTO);

    //Luu thong tin vi tri-vo tuyen
    suggestionDTO.getSuggestionNewSiteDTO().setSuggestNewSiteId(suggestId);
    suggestionDTO.getSuggestionNewSiteDTO().setRowStatus(1);
    suggestionNewSiteService.save(suggestionDTO.getSuggestionNewSiteDTO());

    //Luu call-off 2g
    List<SuggestionCallOff2gDTO> suggestionCallOff2gDTOList = suggestionDTO.getSuggestionCallOff2gDTOList();
    if(suggestionCallOff2gDTOList!=null && suggestionCallOff2gDTOList.size() > 0) {
      for (SuggestionCallOff2gDTO suggestionCallOff2gDTO : suggestionCallOff2gDTOList) {
        if(suggestionCallOff2gDTO!=null){
          //#set default value suggestId, suggestCallOffId
          suggestionCallOff2gDTO.setSuggestId(suggestion.getSuggestId());
          suggestionCallOff2gDTO.setSuggestionCallOffId(suggestionCallOffDTOSaved.getSuggestionCallOffId());

          //#set default value numberSector
          if(suggestionCallOff2gDTO.getSuggestionSector2gDTOList()!=null){
            suggestionCallOff2gDTO.setNumberSector(suggestionCallOff2gDTO.getSuggestionSector2gDTOList().size());
          }
        }
      }

      suggestionCallOff2gService.save(suggestionCallOff2gDTOList, suggestId);
    }

    //Luu call-off 3g
    List<SuggestionCallOff3gDTO> suggestionCallOff3gDTOList = suggestionDTO.getSuggestionCallOff3gDTOList();
    if(suggestionCallOff3gDTOList != null && suggestionCallOff3gDTOList.size() > 0) {
      for (SuggestionCallOff3gDTO suggestionCallOff3gDTO : suggestionCallOff3gDTOList) {
        if(suggestionCallOff3gDTO!=null){
          //#set default value suggestId, suggestCallOffId
          suggestionCallOff3gDTO.setSuggestId(suggestion.getSuggestId());
          suggestionCallOff3gDTO.setSuggestionCallOffId(suggestionCallOffDTOSaved.getSuggestionCallOffId());

          //#set default value numberSector
          if(suggestionCallOff3gDTO.getSuggestionSector3gDTOList()!=null){
            suggestionCallOff3gDTO.setNumberSector(suggestionCallOff3gDTO.getSuggestionSector3gDTOList().size());
          }
        }
      }

      suggestionCallOff3gService.save(suggestionCallOff3gDTOList, suggestId);
    }

    //Luu call-off 4g
    List<SuggestionCallOff4gDTO> suggestionCallOff4gDTOList = suggestionDTO.getSuggestionCallOff4gDTOList();
    if(suggestionCallOff4gDTOList != null && suggestionCallOff4gDTOList.size() > 0) {
      for (SuggestionCallOff4gDTO suggestionCallOff4gDTO : suggestionCallOff4gDTOList) {
        if(suggestionCallOff4gDTO != null){
          //#set default value suggestId, suggestCallOffId
          suggestionCallOff4gDTO.setSuggestId(suggestion.getSuggestId());
          suggestionCallOff4gDTO.setSuggestionCallOffId(suggestionCallOffDTOSaved.getSuggestionCallOffId());

          //#set default value numberSector
          if(suggestionCallOff4gDTO.getSuggestionSector4gDTOList() != null){
            suggestionCallOff4gDTO.setNumberSector(suggestionCallOff4gDTO.getSuggestionSector4gDTOList().size());
          }
        }
      }

      suggestionCallOff4gService.save(suggestionCallOff4gDTOList, suggestId);
    }

    //Luu call-off trans
    List<SuggestionCallOffTransDTO> suggestionCallOffTransDTOList = suggestionDTO.getSuggestionCallOffTransDTOList();
    if(suggestionCallOffTransDTOList!=null && suggestionCallOffTransDTOList.size() > 0 ) {
      for (SuggestionCallOffTransDTO suggestionCallOffTransDTO : suggestionCallOffTransDTOList) {
        if(suggestionCallOffTransDTO!=null){
          //#set default value suggestId, suggestCallOffId
          suggestionCallOffTransDTO.setSuggestId(suggestion.getSuggestId());
          suggestionCallOffDTO.setSuggestionCallOffId(suggestionCallOffDTOSaved.getSuggestionCallOffId());
        }
      }

      suggestionCallOffTransService.save(suggestionCallOffTransDTOList, suggestId);
    }

    //Luu suggestion file
  }

  /**
   * Them thong tin De Xuat Moi
   * @param suggestionDTO
   * @return ma loi khi gap loi
   * @author quangdv
   * @since 8/19/2019
   * Them thong tin De Xuat Moi
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public String createNewSuggestion(SuggestionDTO suggestionDTO, String ip) throws Exception{

    String messageCode = null;

    if (suggestionDTO == null) {
      messageCode = "suggestion.null";
      return messageCode;
    }

    if(suggestionDTO.getSuggestionNewSiteDTO() == null) {
      messageCode = "suggestionNewSite.null";
      return messageCode;
    }

    if(suggestionDTO.getSuggestionNewSiteDTO().getSuggestionRadioDTO() == null) {
      messageCode = "suggestionRadioDTO.null";
      return messageCode;
    }

    if (suggestionDTO.getSuggestId() != null) {
      //#exception create not cotains id
      messageCode = "suggestion.id.exists";
      return messageCode;
    }

    //validate thong tin vi tri-vo tuyen
    messageCode = suggestionNewSiteService.validate(suggestionDTO.getSuggestionNewSiteDTO());
    if (messageCode != null) return messageCode;

    //validate call-off 2g
    messageCode = suggestionCallOff2gService.validate(suggestionDTO.getSuggestionCallOff2gDTOList());
    if (messageCode != null) return messageCode;

    //validate call-off 3g
    messageCode = suggestionCallOff3gService.validate(suggestionDTO.getSuggestionCallOff3gDTOList());
    if (messageCode != null) return messageCode;

    //validate call-off 4g
    messageCode = suggestionCallOff4gService.validate(suggestionDTO.getSuggestionCallOff4gDTOList());
    if (messageCode != null) return messageCode;

    //validate call-off trans
    messageCode = suggestionCallOffTransService.validate(suggestionDTO.getSuggestionCallOffTransDTOList());
    if (messageCode != null) return messageCode;

    //validate suggestion file
    messageCode = suggestionFileService.validate(suggestionDTO.getSuggestionFileDTOList());
    if (messageCode != null) return messageCode;

    //Luu thong tin
    saveNewSuggestion(suggestionDTO, true);

    return null;
  }

  /**
   * @param suggestionDTO
   * @return ma loi khi gap loi hoac null
   * @author quangdv
   * @since 8/20/2019
   * Chinh sua thong tin De Xuat Moi
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public String updateNewSuggestion(SuggestionDTO suggestionDTO, String ip) throws Exception{

    String messageCode = null;

    if (suggestionDTO == null) {
      messageCode = "suggestion.null";
      return messageCode;
    }

    if (suggestionDTO.getSuggestId() == null) {
      //#exception update must cotains id
      messageCode = "suggestion.id.notexists";
      return messageCode;
    } else {
      Optional<Suggestion> optionalSuggestion = suggestionRepository.findById(suggestionDTO.getSuggestId());
      if (!optionalSuggestion.isPresent()) {
        messageCode = "suggestion.notexists";
        return messageCode;
      }
    }

    //validate thong tin vi tri
    messageCode = suggestionNewSiteService.validate(suggestionDTO.getSuggestionNewSiteDTO());
    if (messageCode != null) return messageCode;

    //validate thong tin vi tri
    if(suggestionDTO.getSuggestionNewSiteDTO()!=null) {
      messageCode = suggestionRadioService.validate(suggestionDTO.getSuggestionNewSiteDTO().getSuggestionRadioDTO());
      if (messageCode != null) return messageCode;
    }

    //validate call-off 2g
    messageCode = suggestionCallOff2gService.validate(suggestionDTO.getSuggestionCallOff2gDTOList());
    if (messageCode != null) return messageCode;

    //validate call-off 3g
    messageCode = suggestionCallOff3gService.validate(suggestionDTO.getSuggestionCallOff3gDTOList());
    if (messageCode != null) return messageCode;

    //validate call-off 4g
    messageCode = suggestionCallOff4gService.validate(suggestionDTO.getSuggestionCallOff4gDTOList());
    if (messageCode != null) return messageCode;

    //validate call-off trans
    messageCode = suggestionCallOffTransService.validate(suggestionDTO.getSuggestionCallOffTransDTOList());
    if (messageCode != null) return messageCode;

    //validate suggestion file
    messageCode = suggestionFileService.validate(suggestionDTO.getSuggestionFileDTOList());
    if (messageCode != null) return messageCode;

    //Luu thong tin
    saveNewSuggestion(suggestionDTO, false);

    return null;
  }

  /**
   * Thay doi trang thai de xuat
   * @return ma thong bao loi-khong loi tra ve null
   * @author quangdv
   * @since 8/26/2019
   * Chinh sua trang thai de xuat
   */
  @Override
  public void changeSuggestStatus(Suggestion suggestion, int status) throws Exception{
    suggestion.setSuggestStatus(status);
    suggestionRepository.save(suggestion);
  }

  @Transactional(rollbackFor = Exception.class)
  public void approveDesign(Suggestion suggestion, SuggestStatusDTO suggestStatusDTO) throws Exception{
    int status = suggestStatusDTO.getSuggestStatus() == 1 ? 1 : 2;
    changeSuggestStatus(suggestion,status);
    if(suggestStatusDTO.getSuggestStatus() == 0)
      return;
    suggestionNewSiteService.setStationCode(suggestStatusDTO.getSuggestStationCode(),suggestion.getSuggestId());
    if(!Utils.isNullOrEmpty(suggestStatusDTO.getCalloff2gCode()))
      suggestionCallOff2gService.setCode(suggestStatusDTO.getCalloff2gCode(),suggestion.getSuggestId());
    if(!Utils.isNullOrEmpty(suggestStatusDTO.getCalloff3gCode()))
      suggestionCallOff3gService.setCode(suggestStatusDTO.getCalloff2gCode(),suggestion.getSuggestId());
    if(!Utils.isNullOrEmpty(suggestStatusDTO.getCalloff4gCode()))
      suggestionCallOff4gService.setCode(suggestStatusDTO.getCalloff2gCode(),suggestion.getSuggestId());
  }

  @Override
  public void changeStatusAfterUpdate(Suggestion suggestion) throws Exception {
    if(suggestion.getSuggestStatus() == 1){
      suggestion.setSuggestStatus(3);
      suggestionRepository.save(suggestion);
    }

  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseDTO deleteListSuggesstion(DeleteSuggestionDTO deleteSuggestionDTO) {
    if (!checkExistSuggestion(deleteSuggestionDTO)) {
      return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage("suggestion.id.notexist", null, Locale.forLanguageTag("vi")), null);
    } else {
      try {
        for (Long idSug : deleteSuggestionDTO.getIdSuggestions()) {
          Suggestion suggestion = suggestionRepository.findById(idSug).get();
          if (suggestion.getRowStatus() != 0) {
            suggestion.setRowStatus(0);
            suggestionRepository.save(suggestion);
          }
          Optional<SuggestionNewSite> newSite = suggestionNewSiteRepository.findById(suggestion.getSuggestId());
          if(newSite.isPresent()){
            newSite.get().setRowStatus(0);
            suggestionNewSiteRepository.save(newSite.get());
          }

        }
      } catch (Exception e) {
        log.error(e.getMessage());
        return ResponseUtils.responseByCon(Constants.STATUS_CODE.ERROR, messageSource.getMessage(SYSTEM_ERROR, null, Locale.forLanguageTag("vi")), null);
      }
      return ResponseUtils.responseByCon(Constants.STATUS_CODE.SUCCESS, messageSource.getMessage("success", null, Locale.forLanguageTag("vi")), null);
    }
  }

  private Boolean checkExistSuggestion(DeleteSuggestionDTO deleteSuggestionDTO) {
    for (Long idSug : deleteSuggestionDTO.getIdSuggestions()) {
      Optional<Suggestion> optionalSuggestion = suggestionRepository.findById(idSug);
      if (!optionalSuggestion.isPresent()) {
        return false;
      }
    }
    return true;
  }

  /**
   * @param suggestId ma de xuat
   * @return de xuat neu ton tai-ma loi neu khong ton tai trong csdl
   * @author quangdv
   * @since 8/26/2019
   * Lay de xuat theo id
   */
  @Override
  public Object get(Long suggestId) {
    Optional<Suggestion> opt = suggestionRepository.findById(suggestId);
    if (!opt.isPresent()) {
      return "suggestion.notexists";
    }

    /*---LUU THONG TIN---*/
    //Lay thong tin de xuat
    SuggestionDTO suggestionDTO = suggestionMapper.toDtoBean(opt.get());

    //Lay thong tin vi tri-vo tuyen
    SuggestionNewSiteDTO suggestionNewSiteDTO = suggestionNewSiteService.get(suggestId);
    suggestionDTO.setSuggestionNewSiteDTO(suggestionNewSiteDTO);

    //Lay call-off 2g
    List<SuggestionCallOff2gDTO> suggestionCallOff2gDTOS = suggestionCallOff2gService.gets(suggestId);
    suggestionDTO.setSuggestionCallOff2gDTOList(suggestionCallOff2gDTOS);

    //Lay call-off 3g
    List<SuggestionCallOff3gDTO> suggestionCallOff3gDTOS = suggestionCallOff3gService.gets(suggestId);
    suggestionDTO.setSuggestionCallOff3gDTOList(suggestionCallOff3gDTOS);

    //Lay call-off 4g
    List<SuggestionCallOff4gDTO> suggestionCallOff4gDTOS = suggestionCallOff4gService.gets(suggestId);
    suggestionDTO.setSuggestionCallOff4gDTOList(suggestionCallOff4gDTOS);

    //Lay call-off trans
    List<SuggestionCallOffTransDTO> suggestionCallOffTransDTO = suggestionCallOffTransService.gets(suggestId);
    suggestionDTO.setSuggestionCallOffTransDTOList(suggestionCallOffTransDTO);
    //Lay suggestion file

    return suggestionDTO;
  }

  /**
   * @return gia tri index tiep theo
   * @author quangdv
   * @since 09/04/2019
   * Lay gia tri index tiep theo khi tao moi de xuat trong ngay tao(STT trong ma de xuat)
   */
  @Override
  public Long findNextIndexByStationCodeAndInCreateDate(String stationCode, Date createDate) {
    //Bat dau tu 1
    Long nextIndex = suggestionRepository.findNextIndexByStationCodeAndInCreateDate(stationCode, createDate) + 1;
    return nextIndex;
  }

  @Override
  public String getSuggestCode(Integer suggestTypeId, String stationCode) {
      SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
      String strDate = formatter.format(new Date());
      Long nextIndex = findNextIndexByStationCodeAndInCreateDate(stationCode, new Date());

      String suggestTypeName = null;

      switch (suggestTypeId){
        case Constants.SUGGESTION_TYPE.NEW_SITE :{
          suggestTypeName = Constants.SUGGESTION_TYPE_NAME.NEW_SITE.toString();
          break;
        }
        case Constants.SUGGESTION_TYPE.COSSITE :{
          suggestTypeName = Constants.SUGGESTION_TYPE_NAME.COSSITE.toString();
          break;
        }
        case Constants.SUGGESTION_TYPE.MOVE_SITE :{
          suggestTypeName = Constants.SUGGESTION_TYPE_NAME.MOVE_SITE.toString();
          break;
        }
        case Constants.SUGGESTION_TYPE.MODIFY_ANTEN :{
          suggestTypeName = Constants.SUGGESTION_TYPE_NAME.MODIFY_ANTEN.toString();
          break;
        }
        case Constants.SUGGESTION_TYPE.ADD_CELL :{
          suggestTypeName = Constants.SUGGESTION_TYPE_NAME.ADD_CELL.toString();
          break;
        }
        case Constants.SUGGESTION_TYPE.SWAP_DEVICE :{
          suggestTypeName = Constants.SUGGESTION_TYPE_NAME.SWAP_DEVICE.toString();
          break;
        }
        case Constants.SUGGESTION_TYPE.DESTROY :{
          suggestTypeName = Constants.SUGGESTION_TYPE_NAME.DESTROY.toString();
          break;
        }

        default:break;
      }
      String suggestCode = suggestTypeName + "_" + stationCode + "_" + strDate + "_" + nextIndex;
      return suggestCode;
  }

}
