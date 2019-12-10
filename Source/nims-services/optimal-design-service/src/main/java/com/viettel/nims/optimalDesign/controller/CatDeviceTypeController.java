package com.viettel.nims.optimalDesign.controller;

import com.viettel.nims.optimalDesign.dto.CatDeviceTypeDTO;
import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;
import com.viettel.nims.optimalDesign.service.CatDeviceTypeService;
import com.viettel.nims.optimalDesign.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * @author rabbit on 9/3/2019.
 */
@Log4j2
@RestController
@RequestMapping("${app.prefix}/catdevicetypes")
public class CatDeviceTypeController {
  @Autowired
  ReloadableResourceBundleMessageSource messageSource;

  @Autowired
  private CatDeviceTypeService catDeviceTypeService;

  private static final String SUCCESS_CODE = "success";

  @RequestMapping(value = "/{rowStatus}", method = RequestMethod.GET)
  public ResponseDTO findAllByRowStatus(@PathVariable("rowStatus")Integer rowStatus, @RequestHeader("Accept-Language") String lang){
    Locale locale = lang == null ?  Locale.getDefault() : lang.trim().equals("") ? Locale.getDefault():Locale.forLanguageTag(lang);

    List<CatDeviceTypeDTO> catDeviceTypeDTOS = catDeviceTypeService.findAllByRowStatus(rowStatus);

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(messageSource.getMessage(SUCCESS_CODE, null, locale));

    return new ResponseDTO(Constants.STATUS_CODE.SUCCESS, stringBuilder.toString(), catDeviceTypeDTOS);
  }
}
