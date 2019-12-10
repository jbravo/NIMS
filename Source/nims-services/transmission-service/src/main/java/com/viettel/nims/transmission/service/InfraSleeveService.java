package com.viettel.nims.transmission.service;

import com.viettel.nims.transmission.model.InfraCableLanesBO;
import com.viettel.nims.transmission.model.InfraSleevesBO;
import com.viettel.nims.transmission.model.WeldSleeveBO;
import com.viettel.nims.transmission.model.view.ViewInfraSleevesBO;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public interface InfraSleeveService {
  // Tim kiem co ban
  public ResponseEntity<?> findBasicSleeve(InfraSleevesBO infraSleevesBO, HttpServletRequest request);

  // Tim nang cao
  public ResponseEntity<?> findAdvanceSleeve(InfraSleevesBO infraSleevesBO, HttpServletRequest request);

  //Them mang xong
  public ResponseEntity<?> saveSleeve(InfraSleevesBO infraSleevesBO);

// Xoa mang xong
  JSONObject delete(Long id);

  JSONObject deleteMultipe(List<InfraSleevesBO> infraStationsBOList);

  // tim mang xong theo id
  public ResponseEntity<?> findSleeveById(Long id);

  //  get list ma loai mang xong
  public ResponseEntity<?> getSleeveTypeList();

  //  tim nang cao don vi
  public ResponseEntity<?> getDataSearchAdvance(HttpServletRequest request);

  //  Nha san xuat
  public ResponseEntity<?> getVendorList();

  // get list tuyen theo laneCode
  ResponseEntity<?> findLaneListByCode(String laneCode);

  // check trung ma mang xong
  public boolean checkDuplicateSleeve(String sleeveCode, Long sleeveId);

  // get list mang xong
  ResponseEntity<?> getListSleeve(HttpServletRequest request);

  // tim mang xong theo Id trong View
  ResponseEntity<?> findViewSleeveById(Long id);

  // Export
  String exportExcel(InfraSleevesBO infraSleevesBO, String langCode, HttpServletRequest request);

  String exportExcelChose(List<ViewInfraSleevesBO> listData, String langCode);

  String downloadTeamplate(List<ViewInfraSleevesBO> infraSleeveBOList, int type, String langCode, HttpServletRequest request);

  String editSleeve(MultipartFile file, String langCode, HttpServletRequest request);

  String importSleeve(MultipartFile file, String langCode, HttpServletRequest request);
}
