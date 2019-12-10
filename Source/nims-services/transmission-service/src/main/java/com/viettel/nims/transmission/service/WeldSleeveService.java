package com.viettel.nims.transmission.service;

import com.viettel.nims.transmission.dao.WeldSleeveDao;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.CableInSleeveResponDto;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import com.viettel.nims.transmission.model.view.WeldSleeveRequestDto;
import com.viettel.nims.transmission.model.view.ViewWeldSleeveBO;
import net.sf.json.JSONObject;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface WeldSleeveService {

	// tim kiem co ban
	public ResponseEntity<?> findBasicsWeld(ViewWeldSleeveBO viewWeldSleeveBO);

	public ResponseEntity<?> findWeldSleeveBO();

	public ResponseEntity<?> findWeldSleeveTestBO();

	public ResponseEntity<?> checkSleeveIdLaneId(List<ViewWeldSleeveBO> listViewWeldSleeveBO);
	// xoa

//  JSONObject delete(Long id);
	//
	// T�m ki?m c�p theo di?m d?u v� di?m cu?i HolderId
	public ResponseEntity<?> findCableByHolderId(Long holderId);

	// Save moi han
	public String saveWeldSleeve(WeldSleeveRequestDto weldSleeveRequestDto);

	//
	public List<CableInSleeveResponDto> getListCableId(long cableTypeId, long sleeveId, long cableId);

	// T�m m� mang x�ng theo Id mang x�ng
	public ResponseEntity<?> findSleeveCodeById(Long id);
//  JSONObject deleteMultipe(List<WeldSleeveBO> weldSleeveBO);

	public void delele(WeldSleeveBO.PK id);

	public WeldSleeveBO findById(WeldSleeveBO.PK id);

	public List<WeldSleeveBO> findAll();

	JSONObject deleteMultipe(List<WeldSleeveBO> weldSleeveBOList);

	JSONObject deleteByFiveField(List<WeldSleeveBO> weldSleeveBO);

	JSONObject deleteEmbeddedId(List<WeldSleeveBO.PK> weldSleeveBO);

	// lay cable theo id
	public InfraCablesBO getCableById(Long cableId);
	//Cap nhat moi han
	public String updateWeldSleeve(WeldSleeveRequestDto weldSleeveRequestDto);
  String dowloadTemplate(List<ViewWeldSleeveBO> viewWeldSleeveBO);

  //export
  String exportExcel(ViewWeldSleeveBO viewWeldSleeveBO, String langCode, HttpServletRequest request);

  String exportExcelChose(List<ViewWeldSleeveBO> listData, String langCode);

  //lay laneId theo cableId
//  public CntCableLaneBO getLaneId(Long cableId);
}
