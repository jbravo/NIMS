package com.viettel.nims.transmission.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.nims.commons.util.Constant;
import com.viettel.nims.commons.util.StringUtils;
import com.viettel.nims.transmission.controller.base.BaseNimsController;
import com.viettel.nims.transmission.model.InfraOdfBO;
import com.viettel.nims.transmission.model.dto.ImportOdfDTO;
import com.viettel.nims.transmission.model.view.ViewInfraOdfBO;
import com.viettel.nims.transmission.model.view.ViewOdfLaneBO;
import com.viettel.nims.transmission.service.InfraOdfService;
import com.viettel.nims.transmission.service.TransmissionService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * InfraOdfController
 * Version 1.0
 * Date: 08-24-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 08-24-2019				HungNV				Create
 */
@RestController
@RequestMapping(value = "/infraOdf")
public class InfraOdfController extends BaseNimsController {

  // ------------DI----------------------
  @Autowired
  InfraOdfService infraOdfService;

  @Autowired
  TransmissionService transmissionService;

  // ------------Properties--------------
  JSONObject responsejsonObject = new JSONObject();

// ------------------------------------
  /**
   * search advance
   *
   * @param infraOdfBO
   * @return service findAdvanceOdf(infraOdfBO)
   */
  @PostMapping("/search/advance")
  public ResponseEntity<?> findAdvanceOdf(@RequestBody ViewInfraOdfBO infraOdfBO, HttpServletRequest request) {
    return infraOdfService.findAdvanceOdf(infraOdfBO, request);
  }


  /**
   * getOdfLaneById
   * get data search advance
   *
   * @return service getDataSearchAdvance()
   */
  @GetMapping("/search/dataSearchAdvance")
  public ResponseEntity<?> dataSearchAdvance() {
    return infraOdfService.getDataSearchAdvance();
  }


  /**
   * add odf
   *
   * @param infraOdfBO
   * @return service saveOdf(infraOdfBO)
   */
  @PostMapping("/manage/modify")
  public ResponseEntity<?> saveOdf(@RequestBody InfraOdfBO infraOdfBO) {
    return infraOdfService.saveOdf(infraOdfBO,"vi");
  }


  /**
   * get all stations
   *
   * @return service getAllStations()
   */
  @GetMapping("/search/getAllStations")
  public ResponseEntity<?> getAllStations() {
    return infraOdfService.getAllStations();
  }


  /**
   * delete multiple
   *
   * @param infraOdfBOList
   * @return responseStatusDelete
   */
  @PostMapping("/delete")
  public JSONObject deleteMultiple(@RequestBody List<InfraOdfBO> infraOdfBOList) {
    responsejsonObject.clear();
    JSONArray responseJsonList = infraOdfService.deleteOdfMultiple(infraOdfBOList);
    responsejsonObject = buildResultJson(200, "delete odfs", responseJsonList);
    return responsejsonObject;
  }


  /**
   * get	odf by odfId
   *
   * @param id
   * @return service findOdfById(id)
   */
  @GetMapping("/search/getOdfById/{id}")
  public ResponseEntity<?> getOdfById(@PathVariable Long id) {
    return infraOdfService.findOdfById(id);
  }


  /**
   * get OdfIndex By OdfId
   *
   * @param stationId
   * @return getOdfIndexByOdfId
   */
  @GetMapping("/search/getMaxOdfIndexByStationId/{stationId}")
  public JSONObject getOdfIndexByStationId(@PathVariable Long stationId) {
    responsejsonObject.clear();
    responsejsonObject = buildResultJson(200, "", infraOdfService.getMaxOdfIndexByStationId(stationId));
    return responsejsonObject;
  }

  /**
   * get Odf List By StationId
   *
   * @param viewInfraOdfBO
   * @return getOdfIndexByOdfId
   */
  @PostMapping("/search/getOdfsByStationId")
  public ResponseEntity<?> getOdfsByStationId(@RequestBody ViewInfraOdfBO viewInfraOdfBO) {
    return infraOdfService.getAllOdfByStationId(viewInfraOdfBO);
  }


  /**
   * export excel
   * onToExcel
   *
   * @param viewInfraOdfBOs
   * @return ResponseEntity<FileSystemResource>
   */
  @PostMapping(value = "/excel-choose", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody
  ResponseEntity<FileSystemResource> onToExcel(@RequestBody List<ViewInfraOdfBO> viewInfraOdfBOs, HttpServletRequest request) throws ParseException {

    String path = infraOdfService.exportExcelChose(viewInfraOdfBOs, "vi", request);
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

  /**
   * get Odf Lane By OdfId
   *
   * @param odfId
   * @return getOdfIndexByOdfId
   */
  @GetMapping("/search/getOdfLaneById/{odfId}")
  public ResponseEntity<?> getOdfLaneById(@PathVariable Long odfId) {
    return infraOdfService.getOdfLaneById(odfId);
  }


  /**
   * download file import excel
   * downloadTeamplate
   *
   * @param importOdfDTO
   * @return ResponseEntity<FileSystemResource>
   */
  @PostMapping(path = {"/downloadTeamplate"})
  public @ResponseBody
  ResponseEntity<FileSystemResource> downloadTeamplate(@RequestBody ImportOdfDTO importOdfDTO, HttpServletRequest request)
      throws ParseException {
    String path = infraOdfService.downloadTeamplate(importOdfDTO, 1, "vi", request);
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


  /**
   * download file import excel
   * downloadTeamplate
   *
   * @param importOdfDTO
   * @return ResponseEntity<FileSystemResource>
   */
  @PostMapping(path = {"/downloadTeamplateEdit"})
  public @ResponseBody
  ResponseEntity<FileSystemResource> downloadTeamplateEdit(@RequestBody ImportOdfDTO importOdfDTO, HttpServletRequest request)
      throws ParseException {
    String path = infraOdfService.downloadTeamplate(importOdfDTO, 2, "vi", request);
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


	/**
	 * import add ODF 
	 * importOdf
	 * @param file
	 * @return JSONObject
	 */
	@PostMapping(path = "/importOdf")
	public @ResponseBody
	JSONObject importAddOdf(@RequestBody MultipartFile file, HttpServletRequest request) throws ParseException {
		String path = infraOdfService.importOdf (file, "vi", request, Constant.CHANGE_TYPE.ADD);
		 if (path != null && path.equals("template-error")) {
			responsejsonObject = buildResultJson(0, "error", path);
			return responsejsonObject;
			} else if (path != null) {
				return responsejsonObject = buildResultJson(1, "success", path);
			}
		 return null;
	}


	/**
	 * import add CoupleToLineOdf
	 * importCoupleToLineOdf
	 * @param file
	 * @return JSONObject
	 */
	@PostMapping(path = "/importCoupleToLineOdf")
	public @ResponseBody
	JSONObject importAddCoupleToLineOdf(@RequestBody MultipartFile file, HttpServletRequest request) throws ParseException {
		String path = infraOdfService.importWeldingOdf(file, "vi", Constant.CHANGE_TYPE.ADD, request);
		 if (path != null && path.equals("template-error")) {
			responsejsonObject = buildResultJson(0, "error", path);
			return responsejsonObject;
			} else if (path != null) {
				return responsejsonObject = buildResultJson(1, "success", path);
			}
		 return null;
	}


	/**
	 * import couple to couple Odf 
	 * importCoupleToCoupleOdf
	 * @param file
	 * @return JSONObject
	 */
	@PostMapping(path = "/importCoupleToCoupleOdf")
	public @ResponseBody JSONObject importCoupleToCoupleOdf(@RequestBody MultipartFile file) throws ParseException {
		String path = infraOdfService.importJointCouplerOdf(file, "vi", Constant.CHANGE_TYPE.ADD);
		 if (path != null && path.equals("template-error")) {
			responsejsonObject = buildResultJson(0, "error", path);
			return responsejsonObject;
			} else if (path != null) {
				return responsejsonObject = buildResultJson(1, "success", path);
			}
		 return null;
	}

	/**
	 * download temp ODF 
	 * download result
	 * @param req req, String path
	 * @return ResponseEntity<FileSystemResource>
	 */
	@GetMapping(path = {"/downloadFile"})
	public @ResponseBody
	ResponseEntity<FileSystemResource> downloadFile(HttpServletRequest req, String path) throws ParseException, IOException {
		if (StringUtils.isNotNull(path)) {
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

	/**
	 * import add ODF 
	 * importOdf
	 * @param file
	 * @return JSONObject
	 */
	@PostMapping(path = "/importUpdateOdf")
	public @ResponseBody
	JSONObject importUpdateOdf(@RequestBody MultipartFile file, HttpServletRequest request) throws ParseException {
		String path = infraOdfService.importOdf (file, "vi", request, Constant.CHANGE_TYPE.UPDATE );
		 if (path != null && path.equals("template-error")) {
			responsejsonObject = buildResultJson(0, "error", path);
			return responsejsonObject;
			} else if (path != null) {
				return responsejsonObject = buildResultJson(1, "success", path);
			}
		 return null;
	}


	/**
	 * import add CoupleToLineOdf
	 * importCoupleToLineOdf
	 * @param file
	 * @return JSONObject
	 */
	@PostMapping(path = "/importUpdateCoupleToLineOdf")
	public @ResponseBody
	JSONObject importUpdateCoupleToLineOdf(@RequestBody MultipartFile file, HttpServletRequest request) throws ParseException {
		String path = infraOdfService.importWeldingOdf(file, "vi", Constant.CHANGE_TYPE.UPDATE, request);
		 if (path != null && path.equals("template-error")) {
			responsejsonObject = buildResultJson(0, "error", path);
			return responsejsonObject;
			} else if (path != null) {
				return responsejsonObject = buildResultJson(1, "success", path);
			}
		 return null;
	}

	/**
	 * import couple to couple Odf 
	 * importCoupleToCoupleOdf
	 * @param file
	 * @return JSONObject
	 */
	@PostMapping(path = "/importUpdateCoupleToCoupleOdf")
	public @ResponseBody JSONObject importUpdateCoupleToCoupleOdf(@RequestBody MultipartFile file) throws ParseException {
		String path = infraOdfService.importJointCouplerOdf(file, "vi", Constant.CHANGE_TYPE.UPDATE);
		 if (path != null && path.equals("template-error")) {
			responsejsonObject = buildResultJson(0, "error", path);
			return responsejsonObject;
			} else if (path != null) {
				return responsejsonObject = buildResultJson(1, "success", path);
			}
		 return null;
	}

	@PostMapping(path= "/search/OdfLane")
  public ResponseEntity<?> searchOdfLane(@RequestBody ViewOdfLaneBO viewOdfLaneBO) {
	  ResponseEntity<?> responseEntity = infraOdfService.searchOdfLane(viewOdfLaneBO);
	  return responseEntity;
  }
}
