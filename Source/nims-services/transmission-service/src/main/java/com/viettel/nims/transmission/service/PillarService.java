package com.viettel.nims.transmission.service;

import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.ViewPillarsBO;
import net.sf.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by VANBA on 8/23/2019.
 */
public interface PillarService {

  // Tim kiem co ban
//  public ResponseEntity<?> findBasicPillar(PillarsBO pillarsBO);

  // Tim nang cao
  public ResponseEntity<?> findAdvancePillar(PillarsBO pillarsBO, String langCode,HttpServletRequest request);

  //Them cot
  public ResponseEntity<?> savePillar(PillarsBO pillarsBO);

  // tim cot theo id
  public ResponseEntity<?> findPillarById(Long id);

  // Get list pillar code
  public ResponseEntity<?> getPillarTypeCodeList();

  // Get list owner
//  public ResponseEntity<?> getOwnerName();

  //Get list laneCode
  public ResponseEntity<?> getLaneCodeList(InfraCableLanesBO entity);

  //Get list laneCode hien thi len popup
  public ResponseEntity<?> getListLaneCode(InfraCableLanesBO entity, String langCode,HttpServletRequest request);

  // tim chi so cot theo ma tuyen
  public ResponseEntity<?> getPillarIndex(String  laneCode);

  // Xoa Cot
  public List<JSONObject> deleteMultipe(List<PillarsBO> pillarsBOList);

  public ResponseEntity<?> isExitCode(PillarsBO pillarsBO);

  // Lay danh sach cot cho bang mang xong
  public ResponseEntity<?> getPillarList(PillarsBO pillarsBO, String langCode,HttpServletRequest request);

  //Get tuyen cap qua cot/be chua mang xong
//  public ResponseEntity<?> getListLaneCodeForSleeve(InfraCableLanesBO entity, String langCode,HttpServletRequest request);

  //Get danh sach tuyen cap vat qua cot/be
  public ResponseEntity<?> getListLaneCodeHangPillar(PillarsBO entity, String langCode,HttpServletRequest request);

  String exportExcel(PillarsBO pillarsBO,String langCode,HttpServletRequest request);

  String exportExcelChose(List<ViewPillarsBO> listData, String langCode);

  List<JSONObject> deleteHangConfirm(List<PillarsBO> pillarsBOList);

  boolean checkExitPillarCode(String pillarCode, Long pillarId);

  //-----------------KienNT--------------------
  String downloadTemplate(List<ViewPillarsBO> viewPillarsBOList, Integer type, String langCode, HttpServletRequest request);

  String importPillar(MultipartFile file, String langCode, HttpServletRequest request) throws IOException, ClassNotFoundException;

  String editPillar(MultipartFile file, String langCode, HttpServletRequest request) throws IOException, ClassNotFoundException;
  //----------------KienNT---------------------
}
