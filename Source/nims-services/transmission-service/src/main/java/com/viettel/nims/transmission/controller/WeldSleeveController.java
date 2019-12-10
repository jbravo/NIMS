package com.viettel.nims.transmission.controller;


import com.viettel.nims.transmission.model.InfraCableLanesBO;
import com.viettel.nims.transmission.model.InfraCablesBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.WeldSleeveBO;
import com.viettel.nims.transmission.model.view.CableInSleeveResponDto;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import com.viettel.nims.transmission.model.view.WeldSleeveRequestDto;
import com.viettel.nims.transmission.service.InfraSleeveService;
import com.viettel.nims.transmission.model.view.ViewWeldSleeveBO;
import com.viettel.nims.transmission.service.WeldSleeveService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/weldSleeve")
public class WeldSleeveController {

  JSONObject jsonObject = new JSONObject();

  @Autowired
  WeldSleeveService weldSleeveService;

  @Autowired
  InfraSleeveService infraSleeveService;

  @PostMapping(value = "/search/basic")
  public ResponseEntity<?> findBasicsWeld(@RequestBody ViewWeldSleeveBO viewWeldSleeveBO) {
    return weldSleeveService.findBasicsWeld(viewWeldSleeveBO);
  }

  @PostMapping(value = "/checkSleeveIdLaneId")
  public ResponseEntity<?> checkSleeveIdLaneId(@RequestBody List<ViewWeldSleeveBO> listViewWeldSleeveBO) {
    return weldSleeveService.checkSleeveIdLaneId(listViewWeldSleeveBO);
  }
  //Luu m?i h�n
  @PostMapping(value = "/save")
  public  ResponseEntity<?> saveWeldSleeve(@RequestBody WeldSleeveRequestDto weldSleeveRequestDto){
	 String save = weldSleeveService.saveWeldSleeve(weldSleeveRequestDto);
	 if("Secces".equals(save)) {
	  return new ResponseEntity<>(HttpStatus.OK,HttpStatus.OK);
	  }else {
		  return new ResponseEntity<>(HttpStatus.NOT_FOUND,HttpStatus.OK);
	  }
  }
  //T�m ki?m m� c�p theo di?m d?u v� di?m cu?i
  @GetMapping("/findCable/{holderId}")
  public  ResponseEntity<?> findCableByHolderId(@PathVariable("holderId") Long holderId){
	  return weldSleeveService.findCableByHolderId(holderId);
  }
  //Load danh s�ch c�p chua h�n n?i
  @GetMapping("/get-list-cable/{cableTypeId}/{sleeveId}/{cableId}")
  public ResponseEntity<?> getCableListById(@PathVariable("cableTypeId") long cableTypeId,@PathVariable("sleeveId") long sleeveId,@PathVariable("cableId")long cableId){
	  List<CableInSleeveResponDto> result =  weldSleeveService.getListCableId(cableTypeId, sleeveId, cableId);
	  return new ResponseEntity<>(result, HttpStatus.OK);
  }
  //T�m ki?m m� mang x�ng theo Id;
  @GetMapping("/findSleeve/{sleeveId}")
  public ResponseEntity<?> findSleeveById(@PathVariable("sleeveId") Long sleeveId){
    return weldSleeveService.findSleeveCodeById(sleeveId);
  }

  //KienNT
  @GetMapping(value = "/search/weldSleeveBO")
  public ResponseEntity<?> findWeldSleeveBO() {return weldSleeveService.findWeldSleeveBO(); }
  //KienNT
  @GetMapping(value = "/search/weldSleeveTestBO")
  public ResponseEntity<?> findWeldSleeveTestBO() {return weldSleeveService.findWeldSleeveTestBO(); }
  //View detail weld Sleeve
  @PostMapping(value ="/detail")
  public ViewWeldSleeveBO getDetailWeldSleeve(@RequestBody ViewWeldSleeveBO viewWeldSleeveBO) {
	  System.out.print("SleeveId "+viewWeldSleeveBO.getSleeveId()+" SourceCableId "+viewWeldSleeveBO.getSourceCableId() + " SourceCableNO " + viewWeldSleeveBO.getDestCableId() + " DestCableID "+ viewWeldSleeveBO.getDestCableId() +" DestCableNo " + viewWeldSleeveBO.getDestLineNo() );
	  return null;
  }

//  @PostMapping("/delete")
//  public @ResponseBody
//  JSONObject deleteMultipe(@RequestBody List<WeldSleeveBO> weldSleeveBO) {
//    JSONObject data = weldSleeveService.deleteMultipe(weldSleeveBO);
////    if (infraStationService.delete(id) > 0) {
//    jsonObject = buildResultJson(1, "success", data);
////      return new ResponseEntity<>(HttpStatus.OK);
////    }
//    return jsonObject;
//  }
  //KienNT
  protected JSONObject buildResultJson(Object status, Object message, Object data) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("status", status);
    jsonObject.put("message", message);
    jsonObject.put("data", data);
    return jsonObject;
  }
  //KienNT
  @PostMapping(value = "/deleteWeld")
  public @ResponseBody
  JSONObject delete(@RequestBody WeldSleeveBO.PK id) {
    System.out.print(id.toString());
    weldSleeveService.delele(id);
    jsonObject = buildResultJson(1, "success", 1);
//    model.addAttribute("ListWeldSleeve", weldSleeveService.findAll());
    return jsonObject;
  }
  //KienNT
  @PostMapping(value = "/deleteByFiveField")
  public JSONObject deleteEmbeddedId(@RequestBody List<WeldSleeveBO.PK> weldSleeveBO) {
    JSONObject data = weldSleeveService.deleteEmbeddedId(weldSleeveBO);
    jsonObject = buildResultJson(1, "success", 1);
    return jsonObject;
  }

//  @GetMapping(value = "/list")
//  public ResponseEntity<?> findAllWeldSleeve() {return weldingSlee}
  //KienNT
  @PostMapping ("/delete")
  public @ResponseBody
  JSONObject deleteMultipe(@RequestBody List<WeldSleeveBO> weldSleeveBOList) {
    JSONObject data = weldSleeveService.deleteMultipe(weldSleeveBOList);
    jsonObject = buildResultJson(1, "success", data);
    return jsonObject;
  }
//lay cable theo id
  @GetMapping("/getCableById/{cableId}")
  public InfraCablesBO getCableId(@PathVariable("cableId") Long cableId){
	  InfraCablesBO cablesBO =  weldSleeveService.getCableById(cableId);
	  return cablesBO;
  }
  @PostMapping(value = "/update")
  public  ResponseEntity<?> updateWeldSleeve(@RequestBody WeldSleeveRequestDto weldSleeveRequestDto){
	 String save = weldSleeveService.updateWeldSleeve(weldSleeveRequestDto);
	 if("Secces".equals(save)) {
	  return new ResponseEntity<>(HttpStatus.OK,HttpStatus.OK);
	  }else {
		  return new ResponseEntity<>(HttpStatus.NOT_FOUND,HttpStatus.OK);
	  }
  }

  @PostMapping(value = "/excel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody
  ResponseEntity<FileSystemResource> onToExcel(@RequestBody ViewWeldSleeveBO viewWeldSleeveBO, HttpServletRequest request) throws ParseException {

    String path = weldSleeveService.exportExcel(viewWeldSleeveBO, "vi", request);
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
  ResponseEntity<FileSystemResource> onToExcelChose(@RequestBody List<ViewWeldSleeveBO> listData) throws ParseException {
    String path = weldSleeveService.exportExcelChose(listData, "vi");
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


}
