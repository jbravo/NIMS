package com.viettel.nims.transmission.controller;

import com.viettel.nims.transmission.controller.base.BaseNimsController;
import com.viettel.nims.transmission.model.WeldingOdfBO;
import com.viettel.nims.transmission.service.WeldingOdfService;
import net.sf.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by VTN-PTPM-NV64 on 7/31/2019.
 */
@RestController
@RequestMapping(value = "/weldingOdf")
public class WeldingOdfController extends BaseNimsController {

  private JSONObject jsonObject = new JSONObject();
  private WeldingOdfService weldingOdfService;

  public WeldingOdfController(WeldingOdfService weldingOdfService) {
    this.weldingOdfService = weldingOdfService;
  }

  @PostMapping(value = "/listBy{id}")
  public ResponseEntity<?> getWeldingOdfs(@PathVariable("id") Long id){ return weldingOdfService.getWeldingOdfList(id);}

  @PostMapping(value = "/{id}")
  public ResponseEntity<?> getOdfCodes(@PathVariable("id") Long id){
    return weldingOdfService.getOdfCode(id);
  }

  @PostMapping(value = "/cableCodesBy{id}")
  public ResponseEntity<?> getCableCodes(@PathVariable("id") Long id){
    return weldingOdfService.getCableCodes(id);
  }

  @PostMapping(value = "/couplersBy{id}")
  public ResponseEntity<?> getCouplers(@PathVariable("id") Long id){
    return weldingOdfService.getCouplers(id);
  }

  @PostMapping(value = "/linesBy{id}")
  public ResponseEntity<?> getLines(@PathVariable("id") Long id){
    return weldingOdfService.getLines(id);
  }

  @PostMapping(value = "/destOdfCodesBy{id}")
  public ResponseEntity<?> getDestOdfCodes(@PathVariable("id") Long id){
    return weldingOdfService.getDestOdfs(id);
  }

  @PostMapping(value = "/jointCouplersBy{id}")
  public ResponseEntity<?> getJointCouplers(@PathVariable("id") Long id){
    return weldingOdfService.getJointCouplers(id);
  }

  @PostMapping(value = "/jointDestCouplersBy{id}")
  public ResponseEntity<?> getJointDestCouplers(@PathVariable("id") Long id){
    return weldingOdfService.getJointCouplers(id);
  }

  @PostMapping  ("/delete")
  public JSONObject deleteWeldingOdfs(@RequestBody List<WeldingOdfBO> weldingOdfs) {
    JSONObject data = weldingOdfService.deleteWeldingOdfs(weldingOdfs);
    jsonObject = buildResultJson(1, "success", data);
    return jsonObject;
  }

  @PostMapping  ("/saveNew")
  public JSONObject saveWeldingOdf(@RequestBody WeldingOdfBO weldingOdf) {
    JSONObject data = weldingOdfService.saveWeldingOdf(weldingOdf);
    jsonObject = buildResultJson(1, "success", data);
    return jsonObject;
  }

  @PostMapping  ("/saveEdit")
  public JSONObject updateWeldingOdf(@RequestBody WeldingOdfBO updatingDto) {
    JSONObject data = weldingOdfService.updateWeldingOdf(updatingDto);
    jsonObject = (data.get("code").equals(1)) ?
        buildResultJson(1, "success", data) : buildResultJson(1, "error", data);
    return jsonObject;
  }

  @PostMapping(value = "/exportExcel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody ResponseEntity<InputStreamResource> exportExcel(@RequestBody HashMap<String, Object> paramMap){
    ByteArrayInputStream exportInputStream = weldingOdfService.exportData(paramMap,"vi");
    HttpHeaders headers = new HttpHeaders();
    String fileSuffix = new SimpleDateFormat("MM_dd_yy_HH_mm").format(new Date());
    String exportFileName = "EXPORT_HANDAUNOIODF_"+fileSuffix+".xlsx";
    headers.add("File", exportFileName);
    headers.add("Content-Disposition", "attachment; filename="+ exportFileName);
    headers.add("Access-Control-Expose-Headers", "File");
    return ResponseEntity.ok().headers(headers).body(new InputStreamResource(exportInputStream));
  }
}
