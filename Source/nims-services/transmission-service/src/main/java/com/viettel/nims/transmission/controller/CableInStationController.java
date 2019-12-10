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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.nims.transmission.controller.base.BaseNimsController;
import com.viettel.nims.transmission.model.CableInStationBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewCableInStationBO;
import com.viettel.nims.transmission.service.CableInStationService;
import com.viettel.nims.transmission.service.TransmissionService;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * Created by HieuDT on 08/27/2019.
 */
@Slf4j
@RestController
@RequestMapping(value = "/infraCable")
public class CableInStationController extends BaseNimsController {
	JSONObject jsonObject = new JSONObject();
	@Autowired
	CableInStationService cableInStationService;

	@Autowired
	TransmissionService transmissionService;

	@PostMapping("/search/basic")
	public ResponseEntity<?> findBasicCable(@RequestBody CableInStationBO cableInStationBO) {
		return cableInStationService.findBasicCable(cableInStationBO);
	}

	@PostMapping("/search/advance")
	public ResponseEntity<?> findAdvanceCable(@RequestBody CableInStationBO cableInStationBO,
			HttpServletRequest request) {
		return cableInStationService.findAdvanceCable(cableInStationBO, "vi", request);
	}

	@PostMapping("/search/dataSearchAdvance")
	public ResponseEntity<?> getStationCode(@RequestBody InfraStationsBO infraStationsBO) {
		return cableInStationService.getDataSearchAdvance(infraStationsBO);
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveCable(@RequestBody CableInStationBO cableInStationBO) {
		return cableInStationService.saveCable(cableInStationBO);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<?> findCableById(@PathVariable("id") Long id) {
		return cableInStationService.findCableById(id, "vi");
	}

	@PostMapping("/delete")
	public @ResponseBody JSONObject deleteMultipe(@RequestBody List<CableInStationBO> cableInStationBOList) {
		JSONObject data = cableInStationService.deleteMultipe(cableInStationBOList);
		jsonObject = buildResultJson(1, "success", data);
		return jsonObject;
	}

	@GetMapping(path = { "/findlistcabletype" })
	public ResponseEntity<?> findListCableType() {
		return cableInStationService.findListCableType();

	}

	@GetMapping(path = { "/findlistodffirst" })
	public ResponseEntity<?> findListODFFist(@RequestParam(value = "stationId", required = false) Long stationId, HttpServletRequest request) {
		return cableInStationService.findListODFFist(stationId, request);

	}

	@GetMapping(path = { "/findlistodfend" })
	public ResponseEntity<?> findListODFEnd(@RequestParam(value = "stationId", required = false) Long stationId, HttpServletRequest request) {
		return cableInStationService.findListODFEnd(stationId, request);

	}

	@GetMapping("/cableIndex")
	public ResponseEntity<?> getCableIndex(@RequestParam("sourceId") Long sourceId,
			@RequestParam("destId") Long destId) {
		return cableInStationService.getCableIndex(sourceId, destId);
	}

	@PostMapping(path = { "/isexitcode" })
	public ResponseEntity<?> checkId(@RequestBody CableInStationBO cableInStationBO) {
		return cableInStationService.isExitCode(cableInStationBO);
	}

	@PostMapping(path = { "/checkweldODFbycable" })
	public ResponseEntity<?> checkODF(@RequestBody CableInStationBO cableInStationBO) {
		return cableInStationService.checkWeldODFByCable(cableInStationBO);
	}

	@PostMapping(path = { "/downloadTeamplate" })
	public @ResponseBody ResponseEntity<FileSystemResource> downloadTeamplate(
			@RequestBody List<ViewCableInStationBO> cableInStationBOList, HttpServletRequest request)
			throws ParseException {
		String path = cableInStationService.downloadTeamplate(cableInStationBOList, 1, "vi", request);
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
			@RequestBody List<ViewCableInStationBO> cableInStationBOList, HttpServletRequest request)
			throws ParseException {
		String path = cableInStationService.downloadTeamplate(cableInStationBOList, 2, "vi", request);
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

	@PostMapping(value = "/importCableInStation")
	public @ResponseBody JSONObject importAddStation(@RequestParam(name = "file") MultipartFile file,
			HttpServletRequest request) throws ParseException, IOException, ClassNotFoundException {

		String path = cableInStationService.importCableInStation(file, "vi", request);
		if (path != null && path.equals("template-error")) {
			jsonObject = buildResultJson(0, "error", path);
			return jsonObject;
		} else if (path != null) {
			return jsonObject = buildResultJson(1, "error", path);
		}
		return null;
	}

	@PostMapping(value = "/editCableInStation")
	public @ResponseBody JSONObject importEdit(@RequestParam(name = "file") MultipartFile file,
			HttpServletRequest request) throws ParseException, IOException, ClassNotFoundException {

		String path = cableInStationService.editCableInStation(file, "vi", request);
		;
		if (path != null && path.equals("template-error")) {
			jsonObject = buildResultJson(0, "error", path);
			return jsonObject;
		} else if (path != null) {
			return jsonObject = buildResultJson(1, "error", path);
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

	@PostMapping(value = "/excel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<FileSystemResource> onToExcel(@RequestBody CableInStationBO cableInStationBO,
			HttpServletRequest request) throws ParseException {

		String path = cableInStationService.exportExcel(cableInStationBO, "vi", request);
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
	public @ResponseBody ResponseEntity<FileSystemResource> onToExcelChose(
			@RequestBody List<ViewCableInStationBO> listData) throws ParseException {
		String path = cableInStationService.exportExcelChose(listData, "vi");
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
