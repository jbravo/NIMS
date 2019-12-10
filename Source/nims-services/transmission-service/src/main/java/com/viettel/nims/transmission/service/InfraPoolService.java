package com.viettel.nims.transmission.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.nims.transmission.model.InfraPoolsBO;
import com.viettel.nims.transmission.model.PillarsBO;
import com.viettel.nims.transmission.model.view.ViewInfraPoolsBO;

import net.sf.json.JSONObject;

/**
 * Created by ThieuNV on 08/23/2019.
 */
public interface InfraPoolService {

	// Tim kiem co ban
	ResponseEntity<?> findBasicPool(InfraPoolsBO infraPoolsBO);

	// Tim nang cao
	ResponseEntity<?> findAdvancePool(List<InfraPoolsBO> infraPoolsBO, String langCode, HttpServletRequest request);

	// Them
	ResponseEntity<?> savePool(InfraPoolsBO infraPoolsBO);

	// getNumberGenerate checkNumber
	ResponseEntity<?> getNumberGenerate(Long path, HttpServletRequest request);

	// checkNumber
	ResponseEntity<?> checkNumberAndGetZ(String dept_TTT, Long number_YYYY);

	// Tim theo id
	ResponseEntity<?> findById(Long id);

	// Tim theo poolCode
	ResponseEntity<?> findByPoolCode(String poolCode);

	// Delete multiple
	List<JSONObject> deleteOdfMultiple(List<InfraPoolsBO> listInfraPoolsBO);

	String exportExcelChoose(List<ViewInfraPoolsBO> data, String langCode);

	String exportExcel(List<InfraPoolsBO> data, String langCode, HttpServletRequest request);

	// Get list laneCodeHang
	ResponseEntity<?> getLaneCodeHang(PillarsBO entity, String langCode,HttpServletRequest request);

	List<JSONObject> deleteHangConfirm(List<InfraPoolsBO> listInfraPoolsBO);

	// type : 1 add , 2 edit
	String downloadTeamplate(List<ViewInfraPoolsBO> viewInfraPoolsBOS, Integer type, String langCode,
			HttpServletRequest request);

	String importPool(MultipartFile file, String langCode, HttpServletRequest request);

	String importEditPool(MultipartFile file, String langCode, HttpServletRequest request);

}
