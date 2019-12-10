package com.viettel.nims.transmission.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.nims.transmission.model.DocumentBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;

import net.sf.json.JSONObject;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
public interface InfraStationService {

	// Tim kiem co ban
	ResponseEntity<?> findBasicStation(InfraStationsBO infraStationsBO);

	// Tim nang cao
	ResponseEntity<?> findAdvanceStation(InfraStationsBO infraStationsBO, String langCode, HttpServletRequest request);

	// Them tram
	ResponseEntity<?> saveStation(InfraStationsBO infraStationsBO);

	// tim tram theo id
	ResponseEntity<?> findStationById(Long id, String langCode);

	// Xoa nha tram
	JSONObject delete(Long id);

	JSONObject deleteMultipe(List<InfraStationsBO> infraStationsBOList);

	String exportExcel(InfraStationsBO infraStationsBO, String langCode, HttpServletRequest request);

	// type : 1 add , 2 edit
	String downloadTeamplate(List<ViewInfraStationsBO> infraStationsBOList, Integer type, String langCode,
			HttpServletRequest request);

	String exportExcelChose(List<ViewInfraStationsBO> listData, String langCode);

	String importStation(MultipartFile file, String langCode, HttpServletRequest request)
			throws IOException, ClassNotFoundException;

	String editStation(MultipartFile file, String langCode, HttpServletRequest request)
			throws IOException, ClassNotFoundException;

	// Them document
	ResponseEntity<?> saveDocument(DocumentBO documentBO);

	//get document
	DocumentBO getDocument(Long stationId, int type);
}
