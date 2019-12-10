package com.viettel.nims.transmission.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.nims.transmission.commom.ResponseBase;
import com.viettel.nims.transmission.controller.base.BaseNimsController;
import com.viettel.nims.transmission.model.DocumentBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import com.viettel.nims.transmission.service.InfraStationService;
import com.viettel.nims.transmission.service.TransmissionService;
import com.viettel.nims.transmission.utils.Constains;
import org.springframework.core.io.Resource;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * Created by VTN-PTPM-NV64 on 7/31/2019.
 */
@Slf4j
@RestController
@RequestMapping(value = "/infraStations")
public class InfraStationController extends BaseNimsController {

	JSONObject jsonObject = new JSONObject();

	@Autowired
	InfraStationService infraStationService;

	@Autowired
	TransmissionService transmissionService;

//	@Autowired
	StorageService storageService = new StorageService();

	@PostMapping("/search/basic")
	public ResponseEntity<?> findBasicStation(@RequestBody InfraStationsBO infraStationsBO) {
		return infraStationService.findBasicStation(infraStationsBO);
	}

	@PostMapping("/search/advance")
	public ResponseEntity<?> findAdvanceStation(@RequestBody InfraStationsBO infraStationsBO,
			HttpServletRequest request) {
		return infraStationService.findAdvanceStation(infraStationsBO, "vi", request);
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveStation(@RequestBody InfraStationsBO infraStationsBO, HttpServletRequest request) {
		try {
			if (infraStationsBO.getStationId() == null) {
				if (!transmissionService.checkExitStationCode(infraStationsBO.getStationCode().trim(), request)) {
					return infraStationService.saveStation(infraStationsBO);
				} else {
					return ResponseBase.createResponse(null, Constains.IM_USED, 226);
				}
			} else {
				return infraStationService.saveStation(infraStationsBO);
			}
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return ResponseBase.createResponse(null, Constains.NOT_MODIFIED, 304);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<?> findStationById(@PathVariable("id") Long id) {
		return infraStationService.findStationById(id, "vi");
	}

//  @PostMapping  ("/delete/{id}")
//  public @ResponseBody
//  JSONObject deleteById(@PathVariable("id") Long id) {
//    JSONObject data = infraStationService.delete(id);
////    if (infraStationService.delete(id) > 0) {
//      jsonObject = buildResultJson(1, "success", data);
////      return new ResponseEntity<>(HttpStatus.OK);
////    }
//    return jsonObject;
//  }

	@PostMapping("/delete")
	public @ResponseBody JSONObject deleteMultipe(@RequestBody List<InfraStationsBO> infraStationsBOList) {
		JSONObject data = infraStationService.deleteMultipe(infraStationsBOList);
		jsonObject = buildResultJson(1, "success", data);
		return jsonObject;
	}

	@PostMapping(value = "/excel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<FileSystemResource> onToExcel(@RequestBody InfraStationsBO infraStationsBO,
			HttpServletRequest request) throws ParseException {

		String path = infraStationService.exportExcel(infraStationsBO, "vi", request);
		if (path != null && path.equals("")) {
			return ResponseBase.createResponse(null, "empty", 200);
		} else if (path != null) {
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
	public @ResponseBody ResponseEntity<FileSystemResource> onToExcelChose(
			@RequestBody List<ViewInfraStationsBO> listData) throws ParseException {
		String path = infraStationService.exportExcelChose(listData, "vi");
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

	@PostMapping(path = { "/downloadTeamplate" })
	public @ResponseBody ResponseEntity<FileSystemResource> downloadTeamplate(
			@RequestBody List<ViewInfraStationsBO> infraStationsBOList, HttpServletRequest request)
			throws ParseException {
		String path = infraStationService.downloadTeamplate(infraStationsBOList, 1, "vi", request);
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

	@PostMapping(path = { "/downloadTeamplateEdit" })
	public @ResponseBody ResponseEntity<FileSystemResource> downloadTeamplateEdit(
			@RequestBody List<ViewInfraStationsBO> infraStationsBOList, HttpServletRequest request)
			throws ParseException {
		String path = infraStationService.downloadTeamplate(infraStationsBOList, 2, "vi", request);
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

	@PostMapping(value = "/importStation")
	public @ResponseBody JSONObject importAddStation(@RequestParam(name = "file") MultipartFile file,
			HttpServletRequest request) throws ParseException, IOException, ClassNotFoundException {
		String path = infraStationService.importStation(file, "vi", request);
		if (path != null && path.equals("template-error")) {
			jsonObject = buildResultJson(0, "error", path);
			return jsonObject;
		} else if (path != null) {
			return jsonObject = buildResultJson(1, "success", path);
		}
		return null;
	}

	@PostMapping(value = "/editStation")
	public @ResponseBody JSONObject importEdit(@RequestParam(name = "file") MultipartFile file,
			HttpServletRequest request) throws ParseException, IOException, ClassNotFoundException {
		String path = infraStationService.editStation(file, "vi", request);
		if (path != null && path.equals("template-error")) {
			jsonObject = buildResultJson(0, "error", path);
			return jsonObject;
		} else if (path != null) {
			return jsonObject = buildResultJson(1, "success", path);
		}
		return null;
	}

	@GetMapping(path = { "/downloadFile" })
	public @ResponseBody ResponseEntity<FileSystemResource> downloadFile(HttpServletRequest req, String path)
			throws ParseException, IOException {
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

	@PostMapping("/attachFile")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("stationCode") String stCode, @RequestParam("stId") Long stationId,
			@RequestParam("type") int type) {
		
		List<String> files = new ArrayList<String>();
		String message = "";
		if (infraStationService.getDocument(stationId, type) != null) {
			return ResponseBase.createResponse("valid", Constains.SUCCESS, 200);
		} else {
			try {
				message = storageService.store(file, stationId + "");
				if (message == "") {
					return ResponseBase.createResponse("error", Constains.FAILED, 500);
				}
				files.add(file.getOriginalFilename());
				return ResponseBase.createResponse(message, Constains.SUCCESS, 200);
			} catch (Exception e) {
				return ResponseBase.createResponse("error", Constains.FAILED, 500);
			}
		}
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("/saveDoc")
	public ResponseEntity<?> saveDoc(@RequestBody DocumentBO documentBO) {
		System.out.println(documentBO.getSgcnkdNumber());
		try {
			return infraStationService.saveDocument(documentBO);
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return ResponseBase.createResponse(null, Constains.NOT_MODIFIED, 304);
	}

}
