package com.viettel.nims.transmission.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.nims.transmission.model.CableInStationBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewCableInStationBO;

import net.sf.json.JSONObject;

/**
 * Created by HieuDT on 08/27/2019.
 */
public interface CableInStationService {
	// Tim kiem co ban
	ResponseEntity<?> findBasicCable(CableInStationBO cableInStationBO);

	// Tim nang cao
	ResponseEntity<?> findAdvanceCable(CableInStationBO cableInStationBO, String langCode,HttpServletRequest request);

	// Them cap
	ResponseEntity<?> saveCable(CableInStationBO cableInStationBO);

	// Tim cap theo id
	ResponseEntity<?> findCableById(Long id, String langCode);

	// List loại cap
	ResponseEntity<?> findListCableType();

	// List ODF dau
	ResponseEntity<?> findListODFFist(Long stationId, HttpServletRequest request);

	// List ODF cuoi
	ResponseEntity<?> findListODFEnd(Long stationId, HttpServletRequest request);

	// get stationcode
	ResponseEntity<?> getDataSearchAdvance(InfraStationsBO infraStationsBO);

	// Xoa cap
	JSONObject delete(CableInStationBO cableInStationBO);

	JSONObject deleteMultipe(List<CableInStationBO> cableInStationBOList);

	// tim chi so ma doan cap
	ResponseEntity<?> getCableIndex(Long sourceId, Long destId);

	// check isexitcode
	ResponseEntity<?> isExitCode(CableInStationBO cableInStationBO);

	// check ODF duoc dau noi chua
	ResponseEntity<?> checkWeldODFByCable(CableInStationBO cableInStationBO);

	// dowload file mau type : 1 add , 2 edit
	String downloadTeamplate(List<ViewCableInStationBO> cableInStationBOList, Integer type, String langCode, HttpServletRequest request);

	// import add
	String importCableInStation(MultipartFile file, String langCode, HttpServletRequest request) throws IOException, ClassNotFoundException;

	// import edit
	String editCableInStation(MultipartFile file, String langCode, HttpServletRequest request) throws IOException, ClassNotFoundException;

	String exportExcel(CableInStationBO cableInStationBO, String langCode, HttpServletRequest request);

	String exportExcelChose(List<ViewCableInStationBO> listData, String langCode);

}
