package com.viettel.nims.optimalDesign.controller;

import com.viettel.nims.optimalDesign.dto.DeleteSuggestionDTO;
import com.viettel.nims.optimalDesign.dto.SuggestStatusDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionSearchDTO;
import com.viettel.nims.optimalDesign.dto.commons.ItemComboboxDTO;
import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;
import com.viettel.nims.optimalDesign.entity.Suggestion;
import com.viettel.nims.optimalDesign.entity.SuggestionNewSite;
import com.viettel.nims.optimalDesign.entity.SuggestionRadio;
import com.viettel.nims.optimalDesign.repository.SuggestionNewSiteRepository;
import com.viettel.nims.optimalDesign.repository.SuggestionRadioRepository;
import com.viettel.nims.optimalDesign.repository.SuggestionRepository;
import com.viettel.nims.optimalDesign.repository.SysUsersRepository;
import com.viettel.nims.optimalDesign.service.SuggestionService;
import com.viettel.nims.optimalDesign.service.impl.CatDepartmentServiceImpl;
import com.viettel.nims.optimalDesign.utils.Constants;
import com.viettel.nims.optimalDesign.utils.ResponseUtils;
import com.viettel.nims.optimalDesign.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @author quangdv on 8/19/2019.
 */

@Log4j2
@RestController
@RequestMapping("${app.prefix}/suggestion")
public class SuggestionController {
  @Autowired
  ReloadableResourceBundleMessageSource messageSource;
  @Autowired
  SuggestionService suggestionService;

  @Autowired
  private CatDepartmentServiceImpl catDepartmentService;

  @Autowired
  private SysUsersRepository sysUsersRepository;

  @Autowired
  private SuggestionRepository suggestionRepository;

  @Autowired
  private SuggestionNewSiteRepository suggestionNewSiteRepository;

  @Autowired
  private SuggestionRadioRepository suggestionRadioRepository;

  private static final String SUCCESS_CODE = "success";

  private Logger logger = LoggerFactory.getLogger(SuggestionController.class);

  private static final String SYSTEM_ERROR = "system.error";

  /*
   * Datnt
   * get Suggestion by Condition
   * */
  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseDTO getAllSuggestions(@Valid @RequestBody SuggestionSearchDTO suggestionSearchDTO, BindingResult bindingResult, @RequestHeader("Accept-Language") String lang) {
    ResponseDTO responseDTO = new ResponseDTO();
    List<SuggestionSearchDTO> suggestionDTOList = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if (lang == null || "".equals(lang.trim())) {
      lang = "vi";
    }
    if (suggestionSearchDTO.getType() == null || "".equalsIgnoreCase(suggestionSearchDTO.getType().trim())) {
      suggestionSearchDTO.setType(Constants.SEARCH.SIMPLE);
    }
    if (Constants.SEARCH.SIMPLE.equalsIgnoreCase(suggestionSearchDTO.getType().toUpperCase())) {
      if (suggestionSearchDTO.getUserName() == null || "".equals(suggestionSearchDTO.getUserName().trim()))
        return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage("access.deny", null, Locale.forLanguageTag(lang)), suggestionDTOList);
      suggestionSearchDTO.setDeptId(null);
      suggestionSearchDTO.setSuggestStatus(null);
      suggestionSearchDTO.setRowStatus(null);
      suggestionSearchDTO.setSuggestType(null);
      suggestionSearchDTO.setAfterDate(simpleDateFormat.format(java.sql.Date.valueOf(LocalDate.now())));
      suggestionSearchDTO.setBeforeDate(simpleDateFormat.format(java.sql.Date.valueOf(LocalDate.now().minusYears(1))));
      suggestionSearchDTO.setUserSearch(null);
        suggestionDTOList = suggestionService.getAllSSuggestions(suggestionSearchDTO);
      logger.info("ket qua tim kiem simple: " + suggestionDTOList.size() + " ban ghi");
      return ResponseUtils.responseByCon(Constants.STATUS_CODE.SUCCESS, messageSource.getMessage("success", null, Locale.forLanguageTag(lang)), suggestionDTOList);
    } else {
      if (bindingResult.hasErrors()) {
        return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage(bindingResult.getFieldErrors().get(0).getDefaultMessage(), null, Locale.forLanguageTag("vi")), suggestionDTOList);
      }

      try {
        Date beforeDate = simpleDateFormat.parse(suggestionSearchDTO.getBeforeDate());
        Date afterDate = simpleDateFormat.parse(suggestionSearchDTO.getAfterDate());
        Long diff = (afterDate.getTime() - beforeDate.getTime()) / 1000 / 60 / 60 / 24;
        if (diff < 0) {
          return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage("distance.valid", null, Locale.forLanguageTag(lang)), suggestionDTOList);
        }
        if (diff > Constants.MAX_DATE_COMPARE) {
          return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage("distance.compare", null, Locale.forLanguageTag(lang)), suggestionDTOList);
        }
      } catch (ParseException e) {
        logger.error(e.getMessage(), e);
        return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage("date.valid", null, Locale.forLanguageTag(lang)), suggestionDTOList);
      }
      suggestionDTOList = suggestionService.getAllSSuggestions(suggestionSearchDTO);
      logger.info("ket qua tim kiem advance: " + suggestionDTOList.size() + " ban ghi");

      return ResponseUtils.responseByCon(Constants.STATUS_CODE.SUCCESS, messageSource.getMessage("success", null, Locale.forLanguageTag(lang)), suggestionDTOList);
    }
  }

  @PostMapping(value = "/newsuggestion", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseDTO createNewSuggestion(@Valid @RequestBody SuggestionDTO suggestionDTO, BindingResult bindingResult, @RequestHeader("Accept-Language") String lang, HttpServletRequest request) {
    Locale locale = lang == null ? Locale.getDefault() : lang.trim().equals("") ? Locale.getDefault() : Locale.forLanguageTag(lang);

    if (bindingResult.hasErrors()) {
      StringBuilder stringBuilder = new StringBuilder();
      if (bindingResult.getAllErrors().size() > 0) {
        ObjectError err = bindingResult.getAllErrors().get(0);
        stringBuilder.append(messageSource.getMessage(err.getDefaultMessage(), null, locale));

        return new ResponseDTO(Constants.STATUS_CODE.ERROR, stringBuilder.toString(), suggestionDTO);
      }
    }
    BigInteger idDept = sysUsersRepository.getIdDepartmentByUsername(suggestionDTO.getUserName());
    if (idDept == null || idDept.longValue() != suggestionDTO.getDeptId())
      return new ResponseDTO(Constants.STATUS_CODE.ERROR, messageSource.getMessage("access.deny", null, locale), suggestionDTO);


    String ip = Utils.getClientIpAddr(request);

    try {
      String messageErrorCode = suggestionService.createNewSuggestion(suggestionDTO, ip);
      if (messageErrorCode != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(messageSource.getMessage(messageErrorCode, null, locale));
        return new ResponseDTO(Constants.STATUS_CODE.ERROR, stringBuilder.toString(), suggestionDTO);
      }

      String stringBuilder = (messageSource.getMessage(SUCCESS_CODE, null, locale));
      return new ResponseDTO(Constants.STATUS_CODE.SUCCESS, stringBuilder, suggestionDTO);

    } catch (Exception e) {
      e.printStackTrace();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(messageSource.getMessage(SYSTEM_ERROR, null, locale));
      return new ResponseDTO(Constants.STATUS_CODE.ERROR, stringBuilder.toString(), suggestionDTO);
    }
  }

  @PutMapping(value = "/newsuggestion", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseDTO updateNewSuggestion(@Valid @RequestBody SuggestionDTO suggestionDTO, BindingResult bindingResult, @RequestHeader("Accept-Language") String lang, HttpServletRequest request) {
    Locale locale = lang == null ? Locale.getDefault() : lang.trim().equals("") ? Locale.getDefault() : Locale.forLanguageTag(lang);

    if (bindingResult.hasErrors()) {
      StringBuilder stringBuilder = new StringBuilder();
      if (bindingResult.getAllErrors().size() > 0) {
        ObjectError err = bindingResult.getAllErrors().get(0);
        stringBuilder.append(messageSource.getMessage(err.getDefaultMessage(), null, locale));

        return new ResponseDTO(Constants.STATUS_CODE.ERROR, stringBuilder.toString(), suggestionDTO);
      }
    }

    String ip = Utils.getClientIpAddr(request);

    try {
      suggestionDTO.getSuggestionNewSiteDTO().setSuggestNewSiteId(suggestionDTO.getSuggestId());
      String messageErrorCode = suggestionService.updateNewSuggestion(suggestionDTO, ip);
      if (messageErrorCode != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(messageSource.getMessage(messageErrorCode, null, locale));
        return new ResponseDTO(Constants.STATUS_CODE.ERROR, stringBuilder.toString(), suggestionDTO);
      }
      suggestionService.changeStatusAfterUpdate(suggestionRepository.getOne(suggestionDTO.getSuggestId()));
      String stringBuilder = (messageSource.getMessage(SUCCESS_CODE, null, locale));
      return new ResponseDTO(Constants.STATUS_CODE.SUCCESS, stringBuilder, suggestionDTO);

    } catch (Exception e) {
      e.printStackTrace();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(messageSource.getMessage(SYSTEM_ERROR, null, locale));
      return new ResponseDTO(Constants.STATUS_CODE.ERROR, stringBuilder.toString(), suggestionDTO);
    }
  }

  @PostMapping(value = "/delete")
  public ResponseDTO deleteSuggestion(@RequestBody DeleteSuggestionDTO deleteSuggestionDTO) {
    if (deleteSuggestionDTO.getIdSuggestions() == null && deleteSuggestionDTO.getIdSuggestions().isEmpty()) {
      return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage("suggestion.delete.empty", null, Locale.forLanguageTag("vi")), null);
    }
    if (deleteSuggestionDTO.getUserName() == null || deleteSuggestionDTO.getUserName().equalsIgnoreCase("")) {
      return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage("suggestion.username.detete.empty", null, Locale.forLanguageTag("vi")), null);
    }
    return suggestionService.deleteListSuggesstion(deleteSuggestionDTO);
  }

  @GetMapping(value = "/newsuggestion/{suggestId}")
  public ResponseDTO findOne(@PathVariable("suggestId") Long suggestId, @RequestHeader("Accept-Language") String lang) {
    Locale locale = lang == null ? Locale.getDefault() : lang.trim().equals("") ? Locale.getDefault() : Locale.forLanguageTag(lang);

    Object obj = suggestionService.get(suggestId);
    if (obj instanceof SuggestionDTO) {
      SuggestionDTO suggestionDTO = (SuggestionDTO) obj;
      String stringBuilder = (messageSource.getMessage(SUCCESS_CODE, null, locale));
      return new ResponseDTO(Constants.STATUS_CODE.SUCCESS, stringBuilder, suggestionDTO);
    } else {
      String messageCode = (String) obj;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(messageSource.getMessage(messageCode, null, locale));
      return new ResponseDTO(Constants.STATUS_CODE.ERROR, stringBuilder.toString(), suggestId);
    }
  }


  @GetMapping(value = "/suggestcode")
  public ResponseDTO suggestCode(@RequestParam("suggestType") Integer suggestType, @RequestParam("stationCode") String stationCode, @RequestHeader("Accept-Language") String lang) {
    Locale locale = lang == null ? Locale.getDefault() : lang.trim().equals("") ? Locale.getDefault() : Locale.forLanguageTag(lang);

    String suggestCode = suggestionService.getSuggestCode(suggestType, stationCode);
    String stringBuilder = (messageSource.getMessage(SUCCESS_CODE, null, locale));
    return new ResponseDTO(Constants.STATUS_CODE.SUCCESS, stringBuilder, suggestCode);
  }

  @GetMapping(value = "/stationtechtypes")
  public ResponseDTO getStationTechType(@RequestHeader("Accept-Language") String lang) {
    Locale locale = lang == null ? Locale.getDefault() : lang.trim().equals("") ? Locale.getDefault() : Locale.forLanguageTag(lang);

    List<ItemComboboxDTO> itemComboboxDTOS = new ArrayList<>();
    for (int i = 0; i < Constants.STATION_TECH_TYPE.CODES.length; ++i) {
      ItemComboboxDTO itemComboboxDTO = new ItemComboboxDTO(Constants.STATION_TECH_TYPE.CODES[i].toString(), Constants.STATION_TECH_TYPE.NAMES[i], null);
      itemComboboxDTOS.add(itemComboboxDTO);
    }

    String stringBuilder = (messageSource.getMessage(SUCCESS_CODE, null, locale));
    return new ResponseDTO(Constants.STATUS_CODE.SUCCESS, stringBuilder, itemComboboxDTOS);
  }

  @PostMapping(value = "/suggeststatus/change")
  public ResponseDTO changeSuggestStatus(@RequestBody @Valid SuggestStatusDTO suggestStatusDTO, @RequestHeader("Accept-Language") String lang) {
    Locale locale = lang == null ? Locale.getDefault() : lang.trim().equals("") ? Locale.getDefault() : Locale.forLanguageTag(lang);
    if (suggestStatusDTO.getSuggestId() == null)
      return new ResponseDTO(Constants.STATUS_CODE.ERROR, "not.found.suggestion", suggestStatusDTO);
    Optional<Suggestion> suggestion = suggestionRepository.findById(suggestStatusDTO.getSuggestId());
    if (!suggestion.isPresent())
      return new ResponseDTO(Constants.STATUS_CODE.ERROR, "not.found.suggestion", suggestStatusDTO);
    ResponseDTO responseDTO = null;
    Optional<SuggestionRadio> suggestionRadio = suggestionRadioRepository.findById(suggestion.get().getSuggestId());
    Optional<SuggestionNewSite> suggestionNewSite = suggestionNewSiteRepository.findById(suggestion.get().getSuggestId());
    switch (suggestion.get().getSuggestStatus()) {
      case 0:
        String err = validateApproveDesign(suggestionRadio.get(), suggestStatusDTO);
        if (!err.isEmpty()) {
          responseDTO = new ResponseDTO(Constants.STATUS_CODE.ERROR, err, suggestStatusDTO);
          break;
        }
        try {
          suggestionService.approveDesign(suggestion.get(), suggestStatusDTO);
          updateNote(suggestionNewSite.get(),suggestStatusDTO.getNote());
          responseDTO = new ResponseDTO(Constants.STATUS_CODE.SUCCESS, "success", suggestStatusDTO);
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
          responseDTO = new ResponseDTO(Constants.STATUS_CODE.ERROR, "system.err", suggestStatusDTO);
          break;
        }
        break;
      case 1:
        break;
      case 2:
        break;
      case 3:
        try {
          int status = suggestStatusDTO.getSuggestStatus() == 1 ? 4 : 5;
          suggestionService.changeSuggestStatus(suggestion.get(),status);
          updateNote(suggestionNewSite.get(),suggestStatusDTO.getNote());
          responseDTO = new ResponseDTO(Constants.STATUS_CODE.SUCCESS, "success", suggestStatusDTO);
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
          responseDTO = new ResponseDTO(Constants.STATUS_CODE.ERROR, "system.err", suggestStatusDTO);
          break;
        }
        break;
      case 4:
        try {
          if(suggestStatusDTO.getSuggestStatus() == 1){
            suggestionService.changeSuggestStatus(suggestion.get(),6);
            updateNote(suggestionNewSite.get(),suggestStatusDTO.getNote());
            responseDTO = new ResponseDTO(Constants.STATUS_CODE.SUCCESS, "success", suggestStatusDTO);
          }
        } catch (Exception e) {
          logger.error(e.getMessage(), e);
          responseDTO = new ResponseDTO(Constants.STATUS_CODE.ERROR, "system.err", suggestStatusDTO);
          break;
        }
        break;
      case 5:
        break;
      case 6:
        break;
      case 7:
        break;
      case 8:
        break;
      case 10:
        break;
      case 11:
        break;
      case 12:
        break;
      case 13:
        break;
      case 14:
        break;
      case 15:
        break;
      case 16:
        break;
      case 17:
        break;
      case 18:
        break;
      case 19:
        break;
      case 20:
        break;
      default:
        break;

    }
    return responseDTO == null ? new ResponseDTO(Constants.STATUS_CODE.ERROR, "sys.err", suggestStatusDTO) : responseDTO;
  }

  private void updateNote(SuggestionNewSite suggestionNewSite, String note){
    suggestionNewSite.setNote(note);
    suggestionNewSiteRepository.save(suggestionNewSite);
  }

  private String validateApproveDesign(SuggestionRadio suggestionRadio, SuggestStatusDTO suggestStatusDTO) {
    if (suggestStatusDTO.getSuggestStatus() == 2)
      return "";
    if (Utils.isNullOrEmpty(suggestStatusDTO.getSuggestStationCode()))
      return "empty.station.code";
    if (Utils.isNullOrEmpty(suggestStatusDTO.getCalloff2gCode()) && (suggestionRadio.getStationTechType() == 0 || suggestionRadio.getStationTechType() == 4
        || suggestionRadio.getStationTechType() == 5 || suggestionRadio.getStationTechType() == 6 ||
        suggestionRadio.getStationTechType() == 10 || suggestionRadio.getStationTechType() == 12 || suggestionRadio.getStationTechType() == 13))
      return "empty.station.code.2g";
    if (Utils.isNullOrEmpty(suggestStatusDTO.getCalloff3gCode()) && (suggestionRadio.getStationTechType() == 1 || suggestionRadio.getStationTechType() == 4
        || suggestionRadio.getStationTechType() == 7 || suggestionRadio.getStationTechType() == 8 ||
        suggestionRadio.getStationTechType() == 10 || suggestionRadio.getStationTechType() == 11 || suggestionRadio.getStationTechType() == 13))
      return "empty.station.code.3g";
    if (Utils.isNullOrEmpty(suggestStatusDTO.getCalloff4gCode()) && (suggestionRadio.getStationTechType() == 2 || suggestionRadio.getStationTechType() == 5
        || suggestionRadio.getStationTechType() == 7 || suggestionRadio.getStationTechType() == 9 ||
        suggestionRadio.getStationTechType() == 10 || suggestionRadio.getStationTechType() == 12 || suggestionRadio.getStationTechType() == 11 || suggestionRadio.getStationTechType() == 13))
      return "empty.station.code.4g";
    return "";
  }
}
