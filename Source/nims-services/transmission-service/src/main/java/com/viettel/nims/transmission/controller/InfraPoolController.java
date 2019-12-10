package com.viettel.nims.transmission.controller;

import java.io.File;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.viettel.nims.transmission.utils.Constains;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.commom.ResponseBase;
import com.viettel.nims.transmission.model.InfraPoolsBO;
import com.viettel.nims.transmission.model.PillarsBO;
import com.viettel.nims.transmission.model.view.ViewInfraPoolsBO;
import com.viettel.nims.transmission.service.InfraPoolService;

import net.sf.json.JSONObject;

/**
 * Created by ThieuNV on 08/23/2019.
 */
@RestController
@RequestMapping(value = "/infraPool")
public class InfraPoolController {

	@Autowired
	InfraPoolService infraPoolService;

	@PostMapping("/search/basic")
	public ResponseEntity<?> findBasicPool(@RequestBody InfraPoolsBO infraPoolsBO) {
		return infraPoolService.findBasicPool(infraPoolsBO);
	}

	@PostMapping("/search/advance")
	public ResponseEntity<?> findAdvancePool(@RequestBody List<InfraPoolsBO> infraPoolsBO, HttpServletRequest request) {
		return infraPoolService.findAdvancePool(infraPoolsBO,"vi",request);
	}

	@PostMapping("/save")
	public ResponseEntity<?> savePool(@RequestBody InfraPoolsBO infraPoolsBO) {
		return infraPoolService.savePool(infraPoolsBO);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<?> findPoolById(@PathVariable("id") Long id) {
		return infraPoolService.findById(id);
	}

	@GetMapping("/find/poolCode/{poolCode}")
	public ResponseEntity<?> findPoolByPoolCode(@PathVariable("poolCode") String poolCode) {
		return infraPoolService.findByPoolCode(poolCode);
	}

	@GetMapping("/numberAuto")
	public ResponseEntity<?> getNumberGenerate(@RequestParam(value = "path") Long path, HttpServletRequest request) {
		return infraPoolService.getNumberGenerate(path, request);

	}

	@GetMapping("/checkNumber")
	public ResponseEntity<?> checkNumberAndGetZ(@RequestParam(value = "path") String dept_TTT,
			@RequestParam(value = "number") Long number_YYYY) {
		return infraPoolService.checkNumberAndGetZ(dept_TTT, number_YYYY);

	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteMultiple(@RequestBody List<InfraPoolsBO> listInfraPoolsBO) throws JsonProcessingException {
		List<JSONObject> responseJson = infraPoolService.deleteOdfMultiple(listInfraPoolsBO);
//		Response<List<JSONObject>> response = new Response<>();
//		response.setStatus(HttpStatus.OK.toString());
//		response.setContent(responseJson);
//		return new ResponseEntity<>(response, HttpStatus.OK);
    return ResponseBase.createResponse(responseJson, Constains.UPDATE, 200);

  }

	@PostMapping("/deleteHangConfirm")
	public ResponseEntity<?> deleteHangConfirm(@RequestBody List<InfraPoolsBO> listInfraPoolsBO) {
		List<JSONObject> responseJson = infraPoolService.deleteHangConfirm(listInfraPoolsBO);
//		Response<List<JSONObject>> response = new Response<>();
//		response.setStatus(HttpStatus.OK.toString());
//		response.setContent(responseJson);
//		return new ResponseEntity<>(response, HttpStatus.OK);
    return ResponseBase.createResponse(responseJson, Constains.UPDATE, 200);

  }

	@PostMapping(value = "/excel-choose", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<FileSystemResource> onToExcelChose(@RequestBody List<ViewInfraPoolsBO> listData)
			throws ParseException {
		String path = infraPoolService.exportExcelChoose(listData, "vi");
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

	@PostMapping(value = "/excel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<FileSystemResource> onToExcel(@RequestBody List<InfraPoolsBO> infraPoolsBO, HttpServletRequest request)
			throws ParseException {
		String path = infraPoolService.exportExcel(infraPoolsBO,"vi",request);
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

	// Get danh sach tuyen cap vat qua be
	@PostMapping("/getLaneCodeHang")
	public ResponseEntity<?> getListLaneCodeHangPillar(@RequestBody PillarsBO entity, HttpServletRequest request) {
		return infraPoolService.getLaneCodeHang(entity, "vi", request);
	}

	@PostMapping(path = { "/downloadTeamplate" })
	public @ResponseBody ResponseEntity<FileSystemResource> downloadTeamplate(
			@RequestBody List<ViewInfraPoolsBO> viewInfraPoolsBOS, HttpServletRequest request) throws ParseException {
		String path = infraPoolService.downloadTeamplate(viewInfraPoolsBOS, 1, "vi", request);
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
			@RequestBody List<ViewInfraPoolsBO> viewInfraPoolsBOS, HttpServletRequest request) throws ParseException {
		String path = infraPoolService.downloadTeamplate(viewInfraPoolsBOS, 2, "vi", request);
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

	@PostMapping(value = "/importPool")
	public @ResponseBody ResponseEntity<?> importAddPool(@RequestParam(name = "file") MultipartFile file,
			HttpServletRequest request) {
		String path = infraPoolService.importPool(file, "vi", request);
    if (path != null && path.equals("template-error")) {
      return ResponseBase.createResponse(path, "error", 0);
    } else if (path != null) {
      return ResponseBase.createResponse(path, "Success", 200);
    }
    return null;
	}

	@PostMapping(value = "/importEditPool")
	public @ResponseBody ResponseEntity<?> importEditPool(@RequestParam(name = "file") MultipartFile file,
			HttpServletRequest request) {
		String path = infraPoolService.importEditPool(file, "vi", request);
    if (path != null && path.equals("template-error")) {
      return ResponseBase.createResponse(path, "error", 0);
    } else if (path != null) {
      return ResponseBase.createResponse(path, "Success", 200);
    }
    return null;
	}

}
