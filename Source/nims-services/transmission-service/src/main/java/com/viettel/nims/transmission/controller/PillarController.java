package com.viettel.nims.transmission.controller;
import com.viettel.nims.transmission.commom.ResponseBase;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.ViewPillarsBO;
import com.viettel.nims.transmission.service.PillarService;
import com.viettel.nims.transmission.service.TransmissionService;
import com.viettel.nims.transmission.utils.Constains;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by VAN-BA on 8/23/2019.
 */
@RestController
@RequestMapping(value = "/pillar")
public class PillarController {

  JSONObject responsejsonObject = new JSONObject();
  JSONObject jsonObject = new JSONObject();


  @Autowired
  PillarService pillarService;

  @Autowired
  TransmissionService transmissionService;

//  @PostMapping("/search/basic")
//  public ResponseEntity<?> findBasicPillar(@RequestBody PillarsBO pillarsBO) {
//    return pillarService.findBasicPillar(pillarsBO);
//  }

  @PostMapping("/list/pillar")
  public ResponseEntity<?> getPillarTypeCodeList() {
    return pillarService.getPillarTypeCodeList();
  }

//  @PostMapping("/list/owner")
//  public ResponseEntity<?> getOwnerName() {
//    return pillarService.getOwnerName();
//  }

  @PostMapping("/search/advance")
  public ResponseEntity<?> findAdvancePillar(@RequestBody PillarsBO pillarsBO, HttpServletRequest request) {
    return pillarService.findAdvancePillar(pillarsBO,"vi",request);
  }

  @PostMapping("/search/pillarList")
  public ResponseEntity<?> getPillarList(@RequestBody PillarsBO pillarsBO, HttpServletRequest request) {
    return pillarService.getPillarList(pillarsBO, "vi", request);
  }

  @PostMapping("/save")
  public ResponseEntity<?> savePillar(@RequestBody PillarsBO pillarsBO) {
//    if (StringUtils.isNotEmpty(pillarsBO.getPillarCode())) {
//      if (pillarService.checkExitPillarCode(pillarsBO.getPillarCode().trim(), pillarsBO.getPillarId())) {
//        return pillarService.savePillar(pillarsBO);
//      } else {
//        Response<PillarsBO> response = new Response();
//        response.setStatus(HttpStatus.IM_USED.toString());
//        return new ResponseEntity<>(response, HttpStatus.IM_USED);
//      }
//    }
//    return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
   return pillarService.savePillar(pillarsBO);
  }

  @PostMapping("/laneCode/list")
  public ResponseEntity<?> getLaneCodeList(@RequestBody InfraCableLanesBO entity) {
    return pillarService.getLaneCodeList(entity);
  }

  @PostMapping("/list/laneCode")
  public ResponseEntity<?> getListLaneCode(@RequestBody InfraCableLanesBO entity, HttpServletRequest request) {
    return pillarService.getListLaneCode(entity, "vi", request);
  }

  // Get danh sach tuyen cap vat qua cot
  @PostMapping("/get/laneCode/pillar")
  public ResponseEntity<?> getListLaneCodeHangPillar(@RequestBody PillarsBO entity, HttpServletRequest request) {
    return pillarService.getListLaneCodeHangPillar(entity, "vi", request);
  }

//  @PostMapping("/list/sleeve/laneCode")
//  public ResponseEntity<?> getListLaneCodeForSleeve(@RequestBody InfraCableLanesBO entity, HttpServletRequest request) {
//    return pillarService.getListLaneCodeForSleeve(entity, "vi", request);
//  }

  @PostMapping(value = "/excel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody
  ResponseEntity<FileSystemResource> onToExcel(@RequestBody PillarsBO pillarsBO , HttpServletRequest request) throws ParseException {

    String path = pillarService.exportExcel(pillarsBO, "vi",request);
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
  ResponseEntity<FileSystemResource> onToExcelChose(@RequestBody List<ViewPillarsBO> listData) throws ParseException {
    String path = pillarService.exportExcelChose(listData, "vi");
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

  @GetMapping("/find/{id}")
  public ResponseEntity<?> findPillarById(@PathVariable("id") Long id) {
    return pillarService.findPillarById(id);
  }

  @GetMapping("/pillarIndex/{laneCode}")
  public ResponseEntity<?> getPillarIndex(@PathVariable("laneCode") String laneCode) {
    return pillarService.getPillarIndex(laneCode);
  }

  @PostMapping("/delete")
  public ResponseEntity<?> deleteMultipe(@RequestBody List<PillarsBO> pillarsBOList) {
    List<JSONObject> responseJson = pillarService.deleteMultipe(pillarsBOList);
//    Response<List<JSONObject>> response = new Response<>();
//    response.setStatus(HttpStatus.OK.toString());
//    response.setContent(responseJson);
//    return new ResponseEntity<>(response, HttpStatus.OK);
    return ResponseBase.createResponse(responseJson, Constains.UPDATE, 200);
  }

  @PostMapping("/deleteHangConfirm")
  public ResponseEntity<?> deleteHangConfirm(@RequestBody List<PillarsBO> pillarsBOList) {
    List<JSONObject> responseJson = pillarService.deleteHangConfirm(pillarsBOList);
//    Response<List<JSONObject>> response = new Response<>();
//    response.setStatus(HttpStatus.OK.toString());
//    response.setContent(responseJson);
//    return new ResponseEntity<>(response, HttpStatus.OK);
    return ResponseBase.createResponse(responseJson, Constains.UPDATE, 200);
  }

  @PostMapping(path = {"/isexitcode"})
  public ResponseEntity<?> checkId(@RequestBody PillarsBO pillarsBO) {
    return pillarService.isExitCode(pillarsBO);

  }

  protected JSONObject buildResultJson(Object status, Object message, Object data) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", status);
    jsonObject.put("message", message);
    jsonObject.put("data", data);
    return jsonObject;
  }
//  protected JSONObject buildResultJsonTest(Object status, Object message, Object path) {
//    JSONObject jsonObject = new JSONObject();
//    jsonObject.put("status", status);
//    jsonObject.put("message", message);
//    jsonObject.put("data", path);
//    return jsonObject;
//  }


  //------------------KienNT------------------------
  @PostMapping(path = {"/downloadTemplate"})
  public @ResponseBody
  ResponseEntity<FileSystemResource> downloadTemplate(@RequestBody List<ViewPillarsBO> viewPillarsBOList, HttpServletRequest request)
      throws ParseException {
    String path = pillarService.downloadTemplate(viewPillarsBOList, 1, "vi", request);
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

  @PostMapping(path = {"/downloadTemplateEdit"})
  public @ResponseBody
  ResponseEntity<FileSystemResource> downloadTeamplateEdit(@RequestBody List<ViewPillarsBO> viewPillarsBOList, HttpServletRequest request)
      throws ParseException {
    String path = pillarService.downloadTemplate(viewPillarsBOList, 2, "vi", request);
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

  @PostMapping(value = "/importPillar")
  public @ResponseBody
  JSONObject importAddStation(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) throws ParseException, IOException, ClassNotFoundException {

    String path = pillarService.importPillar(file, "vi", request);
    if (path != null && path.equals("template-error")) {
      jsonObject = buildResultJson(0, "error", path);
      return jsonObject;
    }else{
      return jsonObject = buildResultJson(1, "error", path);
    }
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

  @PostMapping(value = "/editPillar")
  public @ResponseBody
  JSONObject importEdit(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) throws ParseException, IOException, ClassNotFoundException {
    String path = pillarService.editPillar(file, "vi", request);
    if (path != null) {
      jsonObject = buildResultJson(1, "success", path);
      return jsonObject;
    }
    return null;
  }


  //-----------------KienNT-------------------------
}
