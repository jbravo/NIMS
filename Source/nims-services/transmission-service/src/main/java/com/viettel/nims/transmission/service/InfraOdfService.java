package com.viettel.nims.transmission.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.viettel.nims.transmission.model.view.ViewOdfLaneBO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.nims.transmission.model.InfraOdfBO;
import com.viettel.nims.transmission.model.dto.ImportOdfDTO;
import com.viettel.nims.transmission.model.view.ViewInfraOdfBO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * InfraOdfService
 * Version 1.0
 * Date: 08-24-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 08-24-2019				HungNV				Create
 */

public interface InfraOdfService {
	// Search advance
	public ResponseEntity<?> findAdvanceOdf(ViewInfraOdfBO viewInfraOdfBO, HttpServletRequest request);

	// get station code
	public ResponseEntity<?> getDataSearchAdvance();

	// add ODF
	ResponseEntity<?> saveOdf(InfraOdfBO infraOdfBO,String langCode);

	// find Odf by id
	ResponseEntity<?> findOdfById(Long id);

	public ResponseEntity<?> getAllStations();

	// Delete multiple ODF
	public JSONArray deleteOdfMultiple(List<InfraOdfBO> infraOdfBOList);

	// Get Max ODF Index By Station ID
	public JSONObject getMaxOdfIndexByStationId(Long stationId);

	// Get ODF By Station ID
	public ResponseEntity<?> getAllOdfByStationId(ViewInfraOdfBO viewInfraOdfBO);

	// export excel file with chose
	public String exportExcelChose(List<ViewInfraOdfBO> listData, String langCode, HttpServletRequest request);

	// get Odf Lane by ODF Id
	public ResponseEntity<?> getOdfLaneById(Long odfId);

	// download file template import excel
	public String downloadTeamplate(ImportOdfDTO importOdfDTO, Integer type, String langCode, HttpServletRequest request);

	// import Odf
	public String importOdf(MultipartFile file, String langCode, HttpServletRequest request, String type);

	// import welding odf
	public String importWeldingOdf(MultipartFile file, String langCode, String type, HttpServletRequest request);

	// import jointCouplerOdf
	public String importJointCouplerOdf(MultipartFile file, String langCode, String type);

	// export Odf
//	public String exportExcel(InfraOdfBO infraOdfBO, String langCode);

  // Search ODF Lane
  public ResponseEntity<?> searchOdfLane(ViewOdfLaneBO viewOdfLaneBO);

}
