package com.viettel.nims.optimalDesign.controller;


import com.viettel.nims.optimalDesign.dto.CatDepartmentDTO;
import com.viettel.nims.optimalDesign.dto.commons.ResponseDTO;
import com.viettel.nims.optimalDesign.service.CatDepartmentService;
import com.viettel.nims.optimalDesign.utils.Constants;
import com.viettel.nims.optimalDesign.utils.ResponseUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("${app.prefix}/search")
public class CatDepartmentController {
  @Autowired
  ReloadableResourceBundleMessageSource messageSource;
  @Autowired
  CatDepartmentService catDepartmentService;
  @PostMapping(value = "/getTreeDepartment",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseDTO getTreeDepartment(@RequestBody Map<String,String> payload,@RequestParam(required = false) String lang){
    lang = lang == null ? "vi" : lang.trim().equals("") ? "vi":lang;
    String username = payload.get("userName");
    if(username==null || "".equalsIgnoreCase(username.trim())){
      return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage("username.valid",null, Locale.forLanguageTag("vi")),null);
    }
    CatDepartmentDTO dto = catDepartmentService.getTreeCatDepartmentByUserName(username);
    List<CatDepartmentDTO> dtos = Arrays.asList(dto);
    return ResponseUtils.responseByCon(Constants.STATUS_CODE.SUCCESS, messageSource.getMessage("success",null, Locale.forLanguageTag("vi")),dtos);
  }


  @PostMapping(value = "/getTreeDepartmentByDeptId",produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseDTO getTreeDepartmentByDepId(@RequestBody Map<String,String> payload,@RequestParam(required = false) String lang){
    lang = lang == null ? "vi" : lang.trim().equals("") ? "vi":lang;
    String username = payload.get("userName");
    if(username==null || "".equalsIgnoreCase(username.trim())){
      return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage("username.valid",null, Locale.forLanguageTag("vi")),null);
    }
    String deptIdStr = payload.get("deptId");
    if(deptIdStr == null || "".equals(deptIdStr.trim())){
      CatDepartmentDTO dto = catDepartmentService.getTreeCatDepartmentByUserName(username);
      List<CatDepartmentDTO> dtos = Arrays.asList(dto);

      return ResponseUtils.responseByCon(Constants.STATUS_CODE.SUCCESS, messageSource.getMessage("success",null, Locale.forLanguageTag("vi")),dtos);
    }
    try {
      Long deptId = Long.parseLong(payload.get("deptId"));
      List<CatDepartmentDTO> dto = catDepartmentService.getTreeCatDepartmentByDeptId(deptId);
      return ResponseUtils.responseByCon(Constants.STATUS_CODE.SUCCESS, messageSource.getMessage("success",null, Locale.forLanguageTag("vi")),dto);
    }catch (Exception e){
      return ResponseUtils.responseByCon(Constants.STATUS_CODE.INVALID, messageSource.getMessage("suggestion.deptId.required",null, Locale.forLanguageTag("vi")),null);
    }
  }
}
