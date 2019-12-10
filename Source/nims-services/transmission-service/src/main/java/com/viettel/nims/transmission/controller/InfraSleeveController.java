package com.viettel.nims.transmission.controller;

import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.commom.ResponseBase;
import com.viettel.nims.transmission.controller.base.BaseNimsController;
import com.viettel.nims.transmission.model.InfraCableLanesBO;
import com.viettel.nims.transmission.model.InfraSleevesBO;
import com.viettel.nims.transmission.model.view.ViewInfraSleevesBO;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import com.viettel.nims.transmission.service.InfraSleeveService;
import com.viettel.nims.transmission.service.TransmissionService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/infraSleeve")
public class InfraSleeveController extends BaseNimsController {

  @Autowired
  InfraSleeveService infraSleeveService;

  @Autowired
  TransmissionService transmissionService;

  JSONObject jsonObject = new JSONObject();

  @PostMapping("/search/basic")
  public ResponseEntity<?> findBasicSleeve(@RequestBody InfraSleevesBO infraSleevesBO, HttpServletRequest request) {
    return infraSleeveService.findBasicSleeve(infraSleevesBO, request);
  }

  @PostMapping("/search/advance")
  public ResponseEntity<?> findAdvanceSleeve(@RequestBody InfraSleevesBO infraSleevesBO, HttpServletRequest request) {
    return infraSleeveService.findAdvanceSleeve(infraSleevesBO, request);
  }

  @PostMapping("/list/sleeve")
  public ResponseEntity<?> listSleeve(HttpServletRequest request) {
    return infraSleeveService.getListSleeve(request);
  }

  @PostMapping("/save")
  public ResponseEntity<?> saveSleeve(@RequestBody InfraSleevesBO infraSleevesBO) {
    if (StringUtils.isNotEmpty(infraSleevesBO.getSleeveCode())) {
      if (!infraSleeveService.checkDuplicateSleeve(infraSleevesBO.getSleeveCode().trim(), infraSleevesBO.getSleeveId())) {
        return infraSleeveService.saveSleeve(infraSleevesBO);
      } else {
        return ResponseBase.createResponse(null,"IM_USED",HttpStatus.IM_USED);
      }
    }
    return ResponseBase.createResponse(null,"Error",HttpStatus.NOT_MODIFIED);
  }

  @GetMapping("/find/{id}")
  public ResponseEntity<?> findSleeveById(@PathVariable("id") Long id) {
    return infraSleeveService.findSleeveById(id);
  }

  @GetMapping("/find/view/sleeve/{id}")
  public ResponseEntity<?> findViewSleeveById(@PathVariable("id") Long id) {
    return infraSleeveService.findViewSleeveById(id);
  }

  @PostMapping("/list/sleeve-type")
  public ResponseEntity<?> getSleeveTypeCodeList() {
    return infraSleeveService.getSleeveTypeList();
  }

  @GetMapping("/dataAdvance")
  public ResponseEntity<?> getDeptName(HttpServletRequest request) {
    return infraSleeveService.getDataSearchAdvance(request);
  }

  @PostMapping("/list/vendor")
  public ResponseEntity<?> getVendorNameList() {
    return infraSleeveService.getVendorList();
  }

  @PostMapping("/delete")
  public @ResponseBody
  JSONObject deleteMultipe(@RequestBody List<InfraSleevesBO> infraStationsBOList) {
    JSONObject data = infraSleeveService.deleteMultipe(infraStationsBOList);
    jsonObject = buildResultJson(1, "success", data);
    return jsonObject;
  }

  @PostMapping("/list/laneCode")
  public ResponseEntity<?> getLaneListByCode(@RequestBody String laneCode) {
    return infraSleeveService.findLaneListByCode(laneCode);
  }

  @PostMapping(value = "/excel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody
  ResponseEntity<FileSystemResource> onToExcel(@RequestBody InfraSleevesBO infraSleevesBO, HttpServletRequest request) throws ParseException {

    String path = infraSleeveService.exportExcel(infraSleevesBO, "vi", request);
    if (path != null) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      File file = FileUtils.getFile(path);
      FileSystemResource fileSystemResource = new FileSystemResource(file);
      headers.set("File", file.getName());
      headers.set("Content-Disposition", "attachment; filename=" + file.getName());
      headers.set("Access-Control-Expose-Headers", "File");
      return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    }
    return null;
  }

  @PostMapping(value = "/excel-chose", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody
  ResponseEntity<FileSystemResource> onToExcelChose(@RequestBody List<ViewInfraSleevesBO> listData) throws ParseException {
    String path = infraSleeveService.exportExcelChose(listData, "vi");
    if (path != null) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      File file = FileUtils.getFile(path);
      FileSystemResource fileSystemResource = new FileSystemResource(file);
      headers.set("File", file.getName());
      headers.set("Content-Disposition", "attachment; filename=" + file.getName());
      headers.set("Access-Control-Expose-Headers", "File");
      return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    }
    return null;
  }

  @GetMapping(path = {"/downloadFile"})
  public @ResponseBody
  ResponseEntity<FileSystemResource> downloadFile(HttpServletRequest req, String path) throws ParseException, IOException {
    if (path != null) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      File file = FileUtils.getFile(path);
      FileSystemResource fileSystemResource = new FileSystemResource(file);
      headers.set("File", file.getName());
      headers.set("Content-Disposition", "attachment; filename=" + file.getName());
      headers.set("Access-Control-Expose-Headers", "File");
      return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    }
    return null;
  }

  @PostMapping(path = {"/downloadTeamplate"})
  public @ResponseBody
  ResponseEntity<FileSystemResource> downloadTeamplate(@RequestBody List<ViewInfraSleevesBO> infraSleeveBOList, HttpServletRequest request)
      throws ParseException {
    String path = infraSleeveService.downloadTeamplate(infraSleeveBOList, 1, "vi", request);
    if (path != null) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      File file = FileUtils.getFile(path);
      FileSystemResource fileSystemResource = new FileSystemResource(file);
      headers.set("File", file.getName());
      headers.set("Content-Disposition", "attachment; filename=" + file.getName());
      headers.set("Access-Control-Expose-Headers", "File");
      return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    }

    return null;
  }

  @PostMapping(path = {"/downloadTeamplateEdit"})
  public @ResponseBody
  ResponseEntity<FileSystemResource> downloadTeamplateEdit(@RequestBody List<ViewInfraSleevesBO> infraSleeveBOList, HttpServletRequest request)
      throws ParseException {
    String path = infraSleeveService.downloadTeamplate(infraSleeveBOList, 2, "vi", request);
    if (path != null) {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      File file = FileUtils.getFile(path);
      FileSystemResource fileSystemResource = new FileSystemResource(file);
      headers.set("File", file.getName());
      headers.set("Content-Disposition", "attachment; filename=" + file.getName());
      headers.set("Access-Control-Expose-Headers", "File");
      return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    }

    return null;
  }

  @PostMapping(value = "/importSleeve")
  public @ResponseBody
  JSONObject importAddStation(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) throws ParseException, IOException, ClassNotFoundException {
    String path = infraSleeveService.importSleeve(file, "vi", request);
    if (path != null && path.equals("template-error")) {
      jsonObject = buildResultJson(0, "error", path);
      return jsonObject;
    } else if (path != null) {
      return jsonObject = buildResultJson(1, "success", path);
    }
    return null;
  }

  @PostMapping(value = "/editSleeve")
  public @ResponseBody
  JSONObject importEdit(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) throws ParseException, IOException, ClassNotFoundException {
    String path = infraSleeveService.editSleeve(file, "vi", request);
    if (path != null && path.equals("template-error")) {
      jsonObject = buildResultJson(0, "error", path);
      return jsonObject;
    } else if (path != null) {
      return jsonObject = buildResultJson(1, "success", path);
    }
    return null;
  }

}
