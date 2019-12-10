package com.viettel.nims.optimalDesign.controller;

import com.viettel.nims.optimalDesign.dto.commons.ItemComboboxDTO;
import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;
import com.viettel.nims.optimalDesign.service.CatItemService;
import com.viettel.nims.optimalDesign.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * @author rabbit on 8/28/2019.
 */

@Log4j2
@RestController
@RequestMapping("${app.prefix}/catitems")
public class CatItemController {
  @Autowired
  ReloadableResourceBundleMessageSource messageSource;

  @Autowired
  private CatItemService catItemService;

  private static final String SUCCESS_CODE = "success";

  @RequestMapping(value = "/{catCategoryCode}", method = RequestMethod.GET)
  public ResponseDTO findAllByCatCategoryCode(@PathVariable("catCategoryCode") String catCategoryCode, @RequestHeader("Accept-Language") String lang){
    List<ItemComboboxDTO> itemComboboxDTOS = catItemService.findAllByCatCategoryCode(catCategoryCode, lang);

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(messageSource.getMessage(SUCCESS_CODE, null, Locale.forLanguageTag(lang)));

    return new ResponseDTO(Constants.STATUS_CODE.SUCCESS, stringBuilder.toString(), itemComboboxDTOS);
  }
}
