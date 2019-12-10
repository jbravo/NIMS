package com.viettel.nims.transmission.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import com.viettel.nims.transmission.commom.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.dao.CatOdfTypeDao;
import com.viettel.nims.transmission.dao.InfraPoolDao;
import com.viettel.nims.transmission.dao.InfraSleeveDao;
import com.viettel.nims.transmission.dao.InfraSleeveDaoImpl;
import com.viettel.nims.transmission.dao.PillarDao;
import com.viettel.nims.transmission.dao.TransmissionDao;
import com.viettel.nims.transmission.dao.WeldSleeveDao;
import com.viettel.nims.transmission.model.CatDepartmentEntity;
import com.viettel.nims.transmission.model.CatSleeveTypeBO;
import com.viettel.nims.transmission.model.InfraCableLanesBO;
import com.viettel.nims.transmission.model.InfraSleevesBO;
import com.viettel.nims.transmission.model.PillarsBO;
import com.viettel.nims.transmission.model.dto.InfraSleeveDTO;
import com.viettel.nims.transmission.model.view.ViewCatDepartmentBO;
import com.viettel.nims.transmission.model.view.ViewCatItemBO;
import com.viettel.nims.transmission.model.view.ViewInfraPoolsBO;
import com.viettel.nims.transmission.model.view.ViewInfraSleevesBO;
import com.viettel.nims.transmission.utils.Constains;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Slf4j
@Service
@Transactional(transactionManager = "globalTransactionManager")
public class InfraSleeveServiceImpl implements InfraSleeveService {

  @Autowired
  InfraSleeveDao infraSleeveDao;

  @Autowired
  WeldSleeveDao weldSleeveDao;

  @Autowired
  MessageResource messageSource;

  @Autowired
  TransmissionDao transmissionDao;

  @Autowired
  PillarDao pillarDao;

  @Autowired
  InfraPoolDao infraPoolDao;

  @Autowired
  CatOdfTypeDao catOdfTypeDao;

  @Override
  public ResponseEntity<?> findBasicSleeve(InfraSleevesBO infraSleevesBO, HttpServletRequest request) {
    try {
      Long userId = CommonUtil.getUserId(request);
      FormResult result = infraSleeveDao.findBasicSleeve(infraSleevesBO, userId);
      return ResponseBase.createResponse(result, "OK", HttpStatus.OK);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> findAdvanceSleeve(InfraSleevesBO infraSleevesBO, HttpServletRequest request) {
    try {
      Long userId = CommonUtil.getUserId(request);
      FormResult result = infraSleeveDao.findAdvanceSleeve(infraSleevesBO, userId);
      return ResponseBase.createResponse(result, "OK", HttpStatus.OK);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> saveSleeve(InfraSleevesBO infraSleevesBO) {
    if (infraSleevesBO.getSleeveCode() == null) {
      return ResponseBase.createResponse(null, "PRECONDITION_REQUIRED", HttpStatus.PRECONDITION_REQUIRED);
    } else if (infraSleevesBO.getInstallationDate() == null) {
      return ResponseBase.createResponse(null, "PRECONDITION_REQUIRED", HttpStatus.PRECONDITION_REQUIRED);
    } else if (infraSleevesBO.getUpdateTime() == null) {
      return ResponseBase.createResponse(null, "PRECONDITION_REQUIRED", HttpStatus.PRECONDITION_REQUIRED);
    } else if (infraSleevesBO.getSleeveTypeId() == null) {
      return ResponseBase.createResponse(null, "PRECONDITION_REQUIRED", HttpStatus.PRECONDITION_REQUIRED);
    } else if (infraSleevesBO.getDeptId() == null) {
      return ResponseBase.createResponse(null, "PRECONDITION_REQUIRED", HttpStatus.PRECONDITION_REQUIRED);
    } else if (infraSleevesBO.getHolderId() == null) {
      return ResponseBase.createResponse(null, "PRECONDITION_REQUIRED", HttpStatus.PRECONDITION_REQUIRED);
    } else if (infraSleevesBO.getLaneCode() == null) {
      return ResponseBase.createResponse(null, "PRECONDITION_REQUIRED", HttpStatus.PRECONDITION_REQUIRED);
    } else {
      try {
        infraSleeveDao.saveSleeve(infraSleevesBO);
        return ResponseBase.createResponse(null, "OK", HttpStatus.OK);
      } catch (Exception e) {
        return ResponseBase.createResponse(null, "NOT_FOUND", HttpStatus.NOT_FOUND);
      }
    }
  }

  @Override
  public JSONObject deleteMultipe(List<InfraSleevesBO> infraSleevesBOList) {
    JSONObject data = new JSONObject();
    // Tim mang xong da duoc han noi
    JSONArray weldSleeve = new JSONArray();
    for (int i = 0; i < infraSleevesBOList.size(); i++) {
      List<InfraSleevesBO> infraSleevesList = infraSleeveDao
          .findWeldSleeveBySleeveId(infraSleevesBOList.get(i).getSleeveId());
      if (infraSleevesList.size() != 0) {
        for (int j = 0; j < infraSleevesList.size(); j++) {
          weldSleeve.add(infraSleevesList.get(j).getSleeveCode());
        }
        data.put("weldSleeve", weldSleeve);
      }
    }
    if (data.size() == 0) {
      for (int i = 0; i < infraSleevesBOList.size(); i++) {
        InfraSleevesBO infraSleevesBO = infraSleeveDao.findSleeveById(infraSleevesBOList.get(i).getSleeveId());
        if (infraSleevesBO != null) {
          int result = infraSleeveDao.delete(infraSleevesBOList.get(i).getSleeveId());
          if (result > 0) {
            data.put("code", 1);
          }
        }
      }
    }
    return data;
  }

  @Override
  public JSONObject delete(Long id) {
    JSONObject data = new JSONObject();
    // Tim mang xong duoc han noi
    List<InfraSleevesBO> infraSleevesBOList = infraSleeveDao.findWeldSleeveBySleeveId(id);
    if (infraSleevesBOList.size() != 0) {
      JSONArray weldSleeve = new JSONArray();
      for (int i = 0; i < infraSleevesBOList.size(); i++) {
        weldSleeve.add(infraSleevesBOList.get(i).getSleeveCode());
      }
      data.put("weldSleeve", weldSleeve);
    }
    if (data.size() == 0) {
      InfraSleevesBO infraSleeveBO = infraSleeveDao.findSleeveById(id);
      if (infraSleeveBO != null) {
        int result = infraSleeveDao.delete(id);
        if (result > 0) {
          data.put("code", 1);
        }
      }
    }
    return data;
  }

  @Override
  public ResponseEntity<?> findSleeveById(Long id) {
    try {
      InfraSleevesBO infraSleevesBO = infraSleeveDao.findSleeveById(id);
      return ResponseBase.createResponse(infraSleevesBO, "OK", HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception", e);
      return ResponseBase.createResponse(null, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> getSleeveTypeList() {
    try {
      FormResult result = infraSleeveDao.getSleeveTypeList();
      return ResponseBase.createResponse(result, "OK", HttpStatus.OK);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> getDataSearchAdvance(HttpServletRequest request) {
    try {
      Long userId = CommonUtil.getUserId(request);
      FormResult result = infraSleeveDao.getDataFormSearchAdvance(userId);
      return ResponseBase.createResponse(result, "OK", HttpStatus.OK);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> getVendorList() {
    try {
      FormResult result = infraSleeveDao.getVendorList();
      return ResponseBase.createResponse(result, "OK", HttpStatus.OK);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public boolean checkDuplicateSleeve(String sleeveCode, Long sleeveId) {
    List<InfraSleevesBO> resultList = new ArrayList<>();
    if (StringUtils.isNotEmpty(sleeveCode.trim())) {
      resultList = infraSleeveDao.findListSleeveBySleeveCode(sleeveCode.trim(), sleeveId);
    }
    if (resultList.size() > 0) {
      return true;
    }
    return false;
  }

  @Override
  public ResponseEntity<?> getListSleeve(HttpServletRequest request) {
    try {
      Long userId = CommonUtil.getUserId(request);
      FormResult result = infraSleeveDao.listSleeve(userId);
      return ResponseBase.createResponse(result, "OK", HttpStatus.OK);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> findLaneListByCode(String laneCode) {
    FormResult result = infraSleeveDao.findLaneListByCode(laneCode);
    return ResponseBase.createResponse(result, "OK", HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> findViewSleeveById(Long id) {
    try {
      ViewInfraSleevesBO vInfraSleevesBO = infraSleeveDao.findViewSleeveById(id);
      return ResponseBase.createResponse(vInfraSleevesBO, "OK", HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception", e);
      return ResponseBase.createResponse(null, "NOT_FOUND", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public String exportExcel(InfraSleevesBO infraSleevesBO, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = infraSleeveDao.findAdvanceSleeve(infraSleevesBO, userId);
    List<ViewInfraSleevesBO> data = (List<ViewInfraSleevesBO>) result.getListData();
    if (CollectionUtils.isEmpty(data)) {
      return null;
    }

    Workbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("sleeves.sheet", langCode));

    // Set title
    Row firstRow = sheet.createRow(1);
    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 17));
    Cell titleCell = firstRow.createCell(0);
    titleCell.setCellValue(messageSource.getMessage("sleeves.title", langCode));
    titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

    Row secondRow = sheet.createRow(2);
    Cell titleCell2 = secondRow.createCell(8);
    titleCell2.setCellValue(messageSource.getMessage("sleeves.report.date", langCode)
        + CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
    titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

//     Set header
    List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.province", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.district", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.type.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.contain.pillar", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.contain.pool", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.owner", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.unit", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.latitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.longitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.label.serial", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.installation.date", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.modify.date", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.purpose", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.cause.error", langCode), 20 * 256));
    Row thirdRow = sheet.createRow(4);

    for (int i = 0; i < headerDTOList.size(); i++) {
      Cell cell = thirdRow.createCell(i);
      cell.setCellValue(headerDTOList.get(i).getName());
      cell.setCellStyle(ExcelStyleUtil.getHeaderCellStyle((XSSFWorkbook) workbook));
      sheet.setColumnWidth(i, headerDTOList.get(i).getSize());
    }

    int rowNum = 5;
    int index = 1;
    String path = messageSource.getMessage("report.out", langCode);
    File dir = new File(path);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    path = path + messageSource.getMessage("export.sleeves", langCode) + "_"
        + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm") + ".xlsx";

    DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
    // Set data
    for (ViewInfraSleevesBO item : data) {

      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      String provinceDistrict[] = {};
      if (item.getLocalPathPillarPool() != null) {
        provinceDistrict = item.getLocalPathPillarPool().split("/");
      }
      Cell cell1 = row.createCell(1);//
      if (provinceDistrict.length > 2) {
        cell1.setCellValue(provinceDistrict[2]);
      } else {
        cell1.setCellValue("");
      }
      cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell2 = row.createCell(2);//
      if (provinceDistrict.length > 3) {
        cell2.setCellValue(provinceDistrict[3]);
      } else {
        cell2.setCellValue("");
      }
      cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell3 = row.createCell(3);
      cell3.setCellValue(item.getSleeveCode() != null ? item.getSleeveCode() : "");
      cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell4 = row.createCell(4);
      cell4.setCellValue(item.getSleeveTypeCode() != null ? item.getSleeveTypeCode() : "");
      cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell5 = row.createCell(5);
      cell5.setCellValue(item.getPillarCode() != null ? item.getPillarCode() : "");
      cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell6 = row.createCell(6);
      cell6.setCellValue(item.getPoolCode() != null ? item.getPoolCode() : "");
      cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell7 = row.createCell(7);
      cell7.setCellValue(item.getOwnerName() != null ? item.getOwnerName() : "");
      cell7.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell8 = row.createCell(8);
      cell8.setCellValue(item.getDeptPath() != null ? item.getDeptPath() : "");
      cell8.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell9 = row.createCell(9);
      cell9.setCellValue(item.getLatitude() != null ? item.getLatitude().toString() : "");
      cell9.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell10 = row.createCell(10);
      cell10.setCellValue(item.getLongitude() != null ? item.getLongitude().toString() : "");
      cell10.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell11 = row.createCell(11);
      cell11.setCellValue(item.getSerial() != null ? item.getSerial() : "");
      cell11.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell12 = row.createCell(12);
      if (item.getInstallationDate() != null) {
        cell12.setCellValue(item.getInstallation());
      } else {
        cell12.setCellValue("");
      }
      cell12.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell13 = row.createCell(13);
      if (item.getUpdateTime() != null) {
        cell13.setCellValue(df.format(item.getUpdateTime()));
      } else {
        cell13.setCellValue("");
      }
      cell13.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell14 = row.createCell(14);
      String purposeName = "";
      if (item.getPurpose() != null) {
        if (item.getPurpose() == 0) {
          purposeName = messageSource.getMessage("sleeves.normal", langCode);
        } else if (item.getPurpose() == 1) {
          purposeName = messageSource.getMessage("sleeves.trouble", langCode);
        }
      }
      cell14.setCellValue(purposeName);
      cell14.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell15 = row.createCell(15);
      cell15.setCellValue(item.getNote() != null ? item.getNote() : "");
      cell15.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));
    }
    try {
//     Write file
      FileOutputStream outputStream = new FileOutputStream(path);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      log.error("Exception", e);
    }
    return path;
  }

  @Override
  public String exportExcelChose(List<ViewInfraSleevesBO> data, String langCode) {

    if (CollectionUtils.isEmpty(data)) {
      return null;
    }

    Workbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("sleeves.sheet", langCode));

    // Set title
    Row firstRow = sheet.createRow(1);
    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 17));
    Cell titleCell = firstRow.createCell(0);
    titleCell.setCellValue(messageSource.getMessage("sleeves.title", langCode));
    titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

    Row secondRow = sheet.createRow(2);
    Cell titleCell2 = secondRow.createCell(8);
    titleCell2.setCellValue(messageSource.getMessage("sleeves.report.date", langCode)
        + CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
    titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

//     Set header
    List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.province", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.district", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.type.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.contain.pillar", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.contain.pool", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.owner", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.unit", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.latitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.longitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.label.serial", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.installation.date", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.modify.date", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.purpose", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeves.cause.error", langCode), 20 * 256));
    Row thirdRow = sheet.createRow(4);

    for (int i = 0; i < headerDTOList.size(); i++) {
      Cell cell = thirdRow.createCell(i);
      cell.setCellValue(headerDTOList.get(i).getName());
      cell.setCellStyle(ExcelStyleUtil.getHeaderCellStyle((XSSFWorkbook) workbook));
      sheet.setColumnWidth(i, headerDTOList.get(i).getSize());
    }

    int rowNum = 5;
    int index = 1;
    String path = messageSource.getMessage("report.out", langCode);
    File dir = new File(path);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    path = path + messageSource.getMessage("export.sleeves", langCode) + "_"
        + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm") + ".xlsx";

    DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
    // Set data
    for (ViewInfraSleevesBO item : data) {

      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      String provinceDistrict[] = {};
      if (item.getLocalPathPillarPool() != null) {
        provinceDistrict = item.getLocalPathPillarPool().split("/");
      }
      Cell cell1 = row.createCell(1);//
      if (provinceDistrict.length > 2) {
        cell1.setCellValue(provinceDistrict[2]);
      } else {
        cell1.setCellValue("");
      }
      cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell2 = row.createCell(2);//
      if (provinceDistrict.length > 3) {
        cell2.setCellValue(provinceDistrict[3]);
      } else {
        cell2.setCellValue("");
      }
      cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell3 = row.createCell(3);
      cell3.setCellValue(item.getSleeveCode() != null ? item.getSleeveCode() : "");
      cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell4 = row.createCell(4);
      cell4.setCellValue(item.getSleeveTypeCode() != null ? item.getSleeveTypeCode() : "");
      cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell5 = row.createCell(5);
      cell5.setCellValue(item.getPillarCode() != null ? item.getPillarCode() : "");
      cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell6 = row.createCell(6);
      cell6.setCellValue(item.getPoolCode() != null ? item.getPoolCode() : "");
      cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell7 = row.createCell(7);
      cell7.setCellValue(item.getOwnerName() != null ? item.getOwnerName() : "");
      cell7.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell8 = row.createCell(8);
      cell8.setCellValue(item.getDeptPath() != null ? item.getDeptPath() : "");
      cell8.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell9 = row.createCell(9);
      cell9.setCellValue(item.getLatitude() != null ? item.getLatitude().toString() : "");
      cell9.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell10 = row.createCell(10);
      cell10.setCellValue(item.getLongitude() != null ? item.getLongitude().toString() : "");
      cell10.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell11 = row.createCell(11);
      cell11.setCellValue(item.getSerial() != null ? item.getSerial() : "");
      cell11.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell12 = row.createCell(12);
      if (item.getInstallationDate() != null) {
        cell12.setCellValue(item.getInstallation());
      } else {
        cell12.setCellValue("");
      }
      cell12.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell13 = row.createCell(13);
      if (item.getUpdateTime() != null) {
        cell13.setCellValue(df.format(item.getUpdateTime()));
      } else {
        cell13.setCellValue("");
      }
      cell13.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell14 = row.createCell(14);
      String purposeName = "";
      if (item.getPurpose() != null) {
        if (item.getPurpose() == 0) {
          purposeName = messageSource.getMessage("sleeves.normal", langCode);
        } else if (item.getPurpose() == 1) {
          purposeName = messageSource.getMessage("sleeves.trouble", langCode);
        }
      }
      cell14.setCellValue(purposeName);
      cell14.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell15 = row.createCell(15);
      cell15.setCellValue(item.getNote() != null ? item.getNote() : "");
      cell15.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));
    }
    try {
//     Write file
      FileOutputStream outputStream = new FileOutputStream(path);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      log.error("Exception", e);
    }
    return path;
  }

  @Override
  public String downloadTeamplate(List<ViewInfraSleevesBO> infraSleeveBOList, int type, String langCode,
                                  HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    String savePath = messageSource.getMessage("report.out", langCode);
    File dir = new File(savePath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    if (type == 1) {
      savePath = savePath + messageSource.getMessage("sleeve.importAdd.teamplate.fileName", langCode)
          + CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
    } else {
      savePath = savePath + messageSource.getMessage("sleeve.importEdit.teamplate.fileName", langCode)
          + CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
    }
    InputStream excelFile;
    XSSFWorkbook workbook = null;
    try {
      if (type == 1) {
        excelFile = new ClassPathResource(Constants.TEMPLATE_FILE.TEAMPLATE_SLEEVE_INSERT).getInputStream();
      } else {
        excelFile = new ClassPathResource(Constants.TEMPLATE_FILE.TEAMPLATE_SLEEVE).getInputStream();
      }
      workbook = new XSSFWorkbook(excelFile);
    } catch (Exception e) {
      log.error("Exception", e);
    }
    XSSFSheet sheetData = workbook.getSheetAt(0);
    int rowNum = 4;
    int index = 1;
    for (ViewInfraSleevesBO bo : infraSleeveBOList) {
      Row row = sheetData.createRow(rowNum++);
      int i = 0;

      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getPillarCode() == null ? "" : bo.getPillarCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell2 = row.createCell(i++);
      cell2.setCellValue(bo.getPoolCode() == null ? "" : bo.getPoolCode());
      cell2.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell3 = row.createCell(i++);
      cell3.setCellValue(bo.getLaneCode() == null ? "" : bo.getLaneCode());
      cell3.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell4 = row.createCell(i++);
      cell4.setCellValue(bo.getSleeveIndex() == null ? "" : bo.getSleeveIndex().toString());
      cell4.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

      Cell cell5 = row.createCell(i++);
      cell5.setCellValue(bo.getSleeveCode() == null ? "" : bo.getSleeveCode());
      cell5.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell6 = row.createCell(i++);
      cell6.setCellValue(bo.getSleeveTypeCode() == null ? "" : bo.getSleeveTypeCode());
      cell6.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell7 = row.createCell(i++);
      ViewCatDepartmentBO departmentBO = transmissionDao.findDepartmentById(bo.getDeptId(), userId);
      cell7.setCellValue(departmentBO == null ? "" : departmentBO.getDeptCode());
      cell7.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell8 = row.createCell(i++);
      ViewCatItemBO itemVendor = transmissionDao.findCatItemByCategoryCodeAndId(bo.getVendorId(), "VENDOR");
      cell8.setCellValue(itemVendor == null ? "" : itemVendor.getItemCode());
      cell8.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell9 = row.createCell(i++);
      ViewCatItemBO itemOwner = transmissionDao.findCatItemByCategoryCodeAndId(bo.getOwnerId(), "CAT_OWNER");
      cell9.setCellValue(itemOwner == null ? "" : itemOwner.getItemCode());
      cell9.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell10 = row.createCell(i++);
      cell10.setCellValue(bo.getPurposeName() == null ? "" : bo.getPurposeName());
      cell10.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell11 = row.createCell(i++);
      cell11.setCellValue(bo.getStatusName() == null ? "" : bo.getStatusName());
      cell11.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell12 = row.createCell(i++);
      cell12.setCellValue(bo.getInstallation() == null ? "" : bo.getInstallation());
      cell12.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell13 = row.createCell(i++);
      cell13.setCellValue(bo.getModifyDate() == null ? "" : bo.getModifyDate());
      cell13.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell14 = row.createCell(i++);
      cell14.setCellValue(bo.getSerial() == null ? "" : bo.getSerial());
      cell14.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell15 = row.createCell(i++);
      cell15.setCellValue(bo.getNote() == null ? "" : bo.getNote());
      cell15.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

    }

    XSSFSheet sheetSleeveType = workbook.getSheetAt(1);
    FormResult result = infraSleeveDao.getSleeveTypeList();
    List<CatSleeveTypeBO> catSleeveTypeBOS = (List<CatSleeveTypeBO>) result.getListData();
    index = 1;
    rowNum = 2;
    for (CatSleeveTypeBO bo : catSleeveTypeBOS) {
      int i = 0;
      Row row = sheetSleeveType.createRow(rowNum++);
      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getSleeveTypeCode() == null ? "" : bo.getSleeveTypeCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
    }

    List<ViewCatItemBO> listOwner = transmissionDao.findCatItemByCategoryId("CAT_OWNER");
    XSSFSheet sheetOwner = workbook.getSheetAt(2);

    index = 1;
    rowNum = 2;
    for (ViewCatItemBO bo : listOwner) {
      int i = 0;
      Row row = sheetOwner.createRow(rowNum++);
      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getItemCode() == null ? "" : bo.getItemCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell2 = row.createCell(i++);
      cell2.setCellValue(bo.getItemName() == null ? "" : bo.getItemName());
      cell2.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

    }

    List<ViewCatItemBO> listVendor = transmissionDao.findCatItemByCategoryId("VENDOR");
    XSSFSheet sheetVendor = workbook.getSheetAt(5);

    index = 1;
    rowNum = 2;
    for (ViewCatItemBO bo : listVendor) {
      int i = 0;
      Row row = sheetVendor.createRow(rowNum++);
      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getItemCode() == null ? "" : bo.getItemCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell2 = row.createCell(i++);
      cell2.setCellValue(bo.getItemName() == null ? "" : bo.getItemName());
      cell2.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

    }

    // station dept
    CatDepartmentEntity departmentEntity = new CatDepartmentEntity();
    FormResult formResult = transmissionDao.findDepartment(departmentEntity, userId);
    XSSFSheet sheetDept = workbook.getSheetAt(4);
    List<ViewCatDepartmentBO> listDept = (List<ViewCatDepartmentBO>) formResult.getListData();
    index = 1;
    rowNum = 2;
    for (ViewCatDepartmentBO bo : listDept) {
      int i = 0;
      Row row = sheetDept.createRow(rowNum++);
      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getDeptCode() == null ? "" : bo.getDeptCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell2 = row.createCell(i++);
      cell2.setCellValue(bo.getDeptName() == null ? "" : bo.getDeptName());
      cell2.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell3 = row.createCell(i++);
      cell3.setCellValue(bo.getPathName() == null ? "" : bo.getPathName());
      cell3.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
    }

    try {
      // Write file
      FileOutputStream outputStream = new FileOutputStream(savePath);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (FileNotFoundException e) {
      log.error("Exception", e);
    } catch (IOException e) {
      log.error("Exception", e);
      return "";
    }
    return savePath;
  }

  int successRecord = 0;
  int errRecord = 0;

  @Override
  public String importSleeve(MultipartFile file, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    InfraSleevesBO entity = null;
    List<InfraSleeveDTO> datas;
    InfraSleeveDTO flagList;
    Map<String, String> errData = null;
    try {
      // check header is changed
      InfraSleeveDTO header = ExcelDataUltils.getHeaderList(file, new InfraSleeveDTO());
      if (!messageSource.checkEqualHeader(header, langCode)) {
        return "template-error";
      }
      datas = (List<InfraSleeveDTO>) ExcelDataUltils.getListInExcel(file, new InfraSleeveDTO());
      flagList = ExcelDataUltils.getFlagList(file, new InfraSleeveDTO());

    } catch (Exception e) {
      log.error("Exception", e);
      return "template-error";
    }
    int successRecord = 0;
    int errRecord = 0;

    Map<Long, String> mapObj = new HashMap<>();
//    get index for err mapObj
    long index = 0;

    for (InfraSleeveDTO infraSleeveDTO : datas) {
      errData = new HashMap<>();
      entity = new InfraSleevesBO();

      if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getPillarCode())
          && CommonUtils.isNullOrEmpty(infraSleeveDTO.getPoolCode())) {
        ExcelDataUltils.addMessage(errData,
            messageSource.getMessage("err.import.sleeve.requie.poolAndPillar", langCode), null);
      } else if (!CommonUtils.isNullOrEmpty(infraSleeveDTO.getPillarCode())
          && !CommonUtils.isNullOrEmpty(infraSleeveDTO.getPoolCode())) {
        ExcelDataUltils.addMessage(errData,
            messageSource.getMessage("err.import.sleeve.empty.poolAndPillar", langCode), null);
      } else {
        if (!CommonUtils.isNullOrEmpty(infraSleeveDTO.getPillarCode())) {
          // TODO phan quyen nguoi dung
          String pillarCode = infraSleeveDTO.getPillarCode();
          PillarsBO pillarsBO = pillarDao.findPillarByCode(pillarCode);
          if (Objects.isNull(pillarsBO)) {
            ExcelDataUltils.addMessage(errData, "err.import.sleeve.empty.codeNotExist",
                messageSource.getMessage("sleeves.contain.pillar", langCode));
          } else {
            if(!pillarDao.checkRolePillarWithPillarCode(pillarCode, userId)) {
              ExcelDataUltils.addMessage(errData, "err.pillar.do.not.have.role",
                  messageSource.getMessage("sleeves.contain.pillar", langCode));
            } else {
              entity.setHolderId(pillarsBO.getPillarId());
            }
          }
        } else {
          // TODO phan quyen nguoi dung
          String poolCode = infraSleeveDTO.getPoolCode();
          ViewInfraPoolsBO infraPoolsBO = infraPoolDao.findPoolByPoolCode(poolCode);
          if (Objects.isNull(infraPoolsBO)) {
            ExcelDataUltils.addMessage(errData, "err.import.sleeve.empty.codeNotExist",
                messageSource.getMessage("sleeves.contain.pool", langCode));
          } else {
            if(!infraPoolDao.checkRolePoolWithPoolCode(poolCode, userId)){
              ExcelDataUltils.addMessage(errData, "err.pool.do.not.have.role",
                  messageSource.getMessage("sleeves.contain.pool", langCode));
            } else {
              entity.setHolderId(infraPoolsBO.getPoolId());
            }
          }
        }
      }

      String laneCode = infraSleeveDTO.getCableLaneCode();
      if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getCableLaneCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("pillar.laneCode", langCode));
      } else {
        List<InfraCableLanesBO> listInfraCableLanesBO = (List<InfraCableLanesBO>) infraSleeveDao
            .findLaneListByCode(laneCode).getListData();
        if (CommonUtils.isNullOrEmpty(listInfraCableLanesBO)) {
          ExcelDataUltils.addMessage(errData, "err.import.sleeve.empty.codeNotExist",
              messageSource.getMessage("pillar.laneCode", langCode));
        } else {
          entity.setLaneCode(laneCode);
        }
      }

      // sleeve code
      if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getSleeveIndex())) {
        if (CommonUtils.isNullOrEmpty(laneCode)) {
          ExcelDataUltils.addMessage(errData, "err.empty",
              messageSource.getMessage("pillar.laneCode", langCode));
        } else {
          if (Objects.isNull(infraSleeveDao.findLaneListByCode(laneCode))) {
            ExcelDataUltils.addMessage(errData, "err.incorrect",
                messageSource.getMessage("pillar.laneCode", langCode));
          } else {
            Integer maxIndex = getMaxSleeveIndex(laneCode);
            if (maxIndex < 999) {
              Integer newIndex = ++maxIndex;
              String newIndexString = newIndex.toString();
              if (newIndexString.matches("^\\d{1}$")) {
                newIndexString = "00" + newIndexString;
              } else if (newIndexString.matches("^\\d{2}$")) {
                newIndexString = "0" + newIndexString;
              }
              entity.setSleeveCode(laneCode + StringPool.PERIOD + newIndexString);
              infraSleeveDTO.setSleeveCode(entity.getSleeveCode());
              infraSleeveDTO.setSleeveIndex(newIndexString);
            } else {
              ExcelDataUltils.addMessage(errData, "err.can.not.autoconplete.index",
                  messageSource.getMessage("pillar.laneCode", langCode));
            }
          }
        }
      } else if (infraSleeveDTO.getSleeveIndex().matches("\\d{3}")) {

        if (Objects.isNull(infraSleeveDao.findLaneListByCode(laneCode))) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("pillar.laneCode", langCode));
        } else {

          List<InfraSleevesBO> lstInfraSleevesBO = infraSleeveDao
              .findListSleeveBySleeveCode(infraSleeveDTO.getCableLaneCode() + StringPool.PERIOD + infraSleeveDTO.getSleeveIndex(), null);
          if (CommonUtils.isNullOrEmpty(lstInfraSleevesBO)) {
            // check phan quyen
            entity.setSleeveCode(laneCode + StringPool.PERIOD + infraSleeveDTO.getSleeveIndex());
          } else {
            ExcelDataUltils.addMessage(errData, "err.exits",
                messageSource.getMessage("sleeves.code", langCode));
          }
        }

      } else {
        ExcelDataUltils.addMessage(errData, "err.number.length.sleeve.3",
            messageSource.getMessage("sleeve.header.sleeveCode", langCode));
      }

      Boolean isExist = false;
      // type sleeve code
      String sleeveTypeCode = infraSleeveDTO.getSleeveTypeCode();
      if (CommonUtils.isNullOrEmpty(sleeveTypeCode)) {
        ExcelDataUltils.addMessage(errData, "err.choose",
            messageSource.getMessage("sleeves.type.code2", langCode));
      } else {
        List<CatSleeveTypeBO> listCatSleeveTypeBO = (List<CatSleeveTypeBO>) infraSleeveDao.getSleeveTypeList()
            .getListData();
        for (CatSleeveTypeBO catSleeveTypeBO : listCatSleeveTypeBO) {
          if (sleeveTypeCode.equals(catSleeveTypeBO.getSleeveTypeCode())) {
            entity.setSleeveTypeId(catSleeveTypeBO.getSleeveTypeId());
            isExist = true;
          }
        }
        if (isExist.equals(false)) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("sleeves.type.code2", langCode));
        }
      }

      // dept code

      String deptCode = infraSleeveDTO.getDeptCode();
      if (CommonUtils.isNullOrEmpty(deptCode)) {
        ExcelDataUltils.addMessage(errData, "err.empty",
            messageSource.getMessage("station.dep.code", langCode));
      } else {
        ViewCatDepartmentBO viewCatDepartmentBO = transmissionDao.findDepartmentByCode(deptCode, userId);
        if (Objects.isNull(viewCatDepartmentBO)) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("odf.deptCode", langCode));
        } else {
          entity.setDeptId(viewCatDepartmentBO.getDeptId());
        }
      }

      // owner id
      if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getOwnerCode())) {
        entity.setOwnerId(null);
      } else {
        ViewCatItemBO catOwner = transmissionDao.findCatItemByItemNameAndCaregoryCode(infraSleeveDTO.getOwnerCode(), "CAT_OWNER");

        if (catOwner == null) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("odf.ownerName", langCode));
        } else {
          entity.setOwnerId(catOwner.getItemId());
        }
      }


      // vendor name, insert vendor id
      if (!CommonUtils.isNullOrEmpty(infraSleeveDTO.getVendorCode())) {
        ViewCatItemBO listVendor = transmissionDao.findCatItemByItemNameAndCaregoryCode(infraSleeveDTO.getVendorCode(), "VENDOR");
        if (listVendor == null) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("odf.vendorName", langCode));
        } else {
          entity.setVendorId(listVendor.getItemId());
        }
      } else {
        entity.setVendorId(null);
      }

      // purpose
      if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getPurpose())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("sleeves.purpose", langCode));
      } else if (infraSleeveDTO.getPurpose()
          .equals(messageSource.getMessage("sleeve.purpose.normal", langCode).toString())) {
        entity.setPurpose(0L);
      } else if (infraSleeveDTO.getPurpose()
          .equals(messageSource.getMessage("sleeve.purpose.abnormal", langCode).toString())) {
        entity.setPurpose(1L);
      } else {
        ExcelDataUltils.addMessage(errData, "err.incorrect",
            messageSource.getMessage("sleeves.purpose", langCode));
      }

      if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getStatus())) {
        ExcelDataUltils.addMessage(errData, "err.empty",
            messageSource.getMessage("common.label.status", langCode));
      } else if (infraSleeveDTO.getStatus().equals(messageSource.getMessage("sleeve.status.use", langCode))) {
        entity.setsStatus(0L);
      } else if (infraSleeveDTO.getStatus().equals(messageSource.getMessage("sleeve.status.notUse", langCode))) {
        entity.setsStatus(1L);
      } else {
        ExcelDataUltils.addMessage(errData, "err.incorrect",
            messageSource.getMessage("common.label.status", langCode));
      }

      // installation date
      if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getInstalationDate())) {
        ExcelDataUltils.addMessage(errData, "err.empty",
            messageSource.getMessage("sleeves.installation.date", langCode));
      } else {
        Date dateFormat;
        try {
          if (isValidDate(infraSleeveDTO.getInstalationDate(), Constants.DATE.DEFAULT_FORMAT, "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")) {
            dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT)
                .parse(infraSleeveDTO.getInstalationDate());
            java.sql.Date sqlDate = new java.sql.Date(dateFormat.getTime());
            entity.setInstallationDate(sqlDate);
          } else {
            ExcelDataUltils.addMessage(errData, "err.dateFormat",
                messageSource.getMessage("sleeves.installation.date", langCode));
          }

        } catch (ParseException e) {
          log.error("Exception", e);
        }
      }

      // modify date
      if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getModifiedDate())) {
        ExcelDataUltils.addMessage(errData, "err.empty",
            messageSource.getMessage("sleeves.modify.date", langCode));

      } else {
        Date dateFormat;
        try {
          if (isValidDate(infraSleeveDTO.getModifiedDate().toString(), Constants.DATE.DEFAULT_FORMAT, "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")) {
            dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT)
                .parse(infraSleeveDTO.getModifiedDate());
            Date sqlDate = new Date(dateFormat.getTime());
            if (entity.getInstallationDate() != null && sqlDate != null) {
              if (entity.getInstallationDate().getTime() > sqlDate.getTime()) {
                ExcelDataUltils.addMessage(errData,
                    messageSource.getMessage("sleeve.err.modifyDate.earlier.instalationDate", langCode), null);

              } else {
                entity.setUpdateTime(sqlDate);
              }
            }
          } else {
            ExcelDataUltils.addMessage(errData, "err.dateFormat",
                messageSource.getMessage("sleeves.modify.date", langCode));
          }

        } catch (ParseException e) {
          log.error("Exception", e);
        }
      }

      // serial no
      if (infraSleeveDTO.getSerialCode().length() > 100) {
        ExcelDataUltils.addMessage(errData, "err.maxLength.100",
            messageSource.getMessage("sleeves.label.serial", langCode));
      } else {
        entity.setSerial(infraSleeveDTO.getSerialCode());
      }

      //
      if (infraSleeveDTO.getNote().length() > 500) {
        ExcelDataUltils.addMessage(errData, "err.maxLength.500",
            messageSource.getMessage("sleeves.cause.error", langCode));
      } else {
        entity.setNote(infraSleeveDTO.getNote());
      }
      if (errData.isEmpty()) {
        infraSleeveDao.saveSleeve(entity);
        successRecord++;
      } else {
        errRecord++;
      }
      StringBuilder rs = new StringBuilder();
      List<String> errList = new ArrayList<>();
      for (Map.Entry<String, String> entry : errData.entrySet()) {
        String temp = "";
        String key = entry.getKey();
        String value = entry.getValue();
        if (value == null) {
          temp = key + temp + "\n";
        } else {
          temp = messageSource.getMessage(key, langCode, Arrays.asList(value)) + temp + "\n";
        }
        errList.add(temp);
      }

      for (int i = errList.size() - 1; i >= 0; i--) {
        rs.append(errList.get(i));
      }

      if (CommonUtils.isNullOrEmpty(rs.toString())) {
        rs.append("OK");
      }
      mapObj.put(index++, rs.toString());
    }


    String savePath = ExcelDataUltils.writeResultExcel(file, new InfraSleeveDTO(), mapObj,
        messageSource.getMessage("sleeve.result.import.fileName", langCode),datas);

    return savePath + StringPool.PLUS + successRecord + StringPool.PLUS + errRecord;

  }

  @Override
  public String editSleeve(MultipartFile file, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    List<InfraSleeveDTO> datas;
    InfraSleeveDTO flagList;
    try {
//      check header is changed
      InfraSleeveDTO header = ExcelDataUltils.getHeaderList(file, new InfraSleeveDTO());
      if (!messageSource.checkEqualHeader(header, langCode)) {
        return "template-error";
      }
      datas = (List<InfraSleeveDTO>) ExcelDataUltils.getListInExcel(file, new InfraSleeveDTO());
      flagList = ExcelDataUltils.getFlagList(file, new InfraSleeveDTO());

    } catch (Exception e) {
      log.error("Exception", e);
      return "template-error";
    }
    int successRecord = 0;
    int errRecord = 0;

    Map<Long, String> mapObj = new HashMap<>();
//    get index for err mapObj
    long index = 0;

    for (InfraSleeveDTO infraSleeveDTO : datas) {
      Map<String, String> errData = new HashMap<>();
      InfraSleevesBO entity = new InfraSleevesBO();

      errData = new HashMap<>();
      // check ton tai mang xong
      String sleeveCode = infraSleeveDTO.getSleeveCode();
      List<InfraSleevesBO> lstInfraSleevesBO = infraSleeveDao
          .findListSleeveBySleeveCode(sleeveCode, null);
      if (!CommonUtils.isNullOrEmpty(lstInfraSleevesBO)) {
        // check phan quyen
        List<InfraSleevesBO> listInfraSleevesBO = infraSleeveDao
            .getListSleeveBySleeveCode(infraSleeveDTO.getSleeveCode(), userId);
        for (InfraSleevesBO infraSleevesBO : listInfraSleevesBO) {
          entity = infraSleevesBO;
          break;
        }
      } else {
        ExcelDataUltils.addMessage(errData, "err.import.sleeve.empty.codeNotExist",
            messageSource.getMessage("sleeve.code", langCode));
      }
      Boolean isExist = false;
      // type sleeve code
      String sleeveTypeCode = infraSleeveDTO.getSleeveTypeCode();
      if (CommonUtils.isNullOrEmpty(sleeveTypeCode)) {
        ExcelDataUltils.addMessage(errData, "err.empty",
            messageSource.getMessage("sleeves.type.code2", langCode));
      } else {
        List<CatSleeveTypeBO> listCatSleeveTypeBO = (List<CatSleeveTypeBO>) infraSleeveDao.getSleeveTypeList()
            .getListData();
        for (CatSleeveTypeBO catSleeveTypeBO : listCatSleeveTypeBO) {
          if (sleeveTypeCode.equals(catSleeveTypeBO.getSleeveTypeCode())) {
            entity.setSleeveTypeId(catSleeveTypeBO.getSleeveTypeId());
            isExist = true;
          }
        }
        if (isExist.equals(false)) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("sleeves.type.code2", langCode));
        }
      }

      // dept code
      if (flagList.getDeptCode().equals("Y")) {
        String deptCode = infraSleeveDTO.getDeptCode();
        if (CommonUtils.isNullOrEmpty(deptCode)) {
          ExcelDataUltils.addMessage(errData, "err.empty",
              messageSource.getMessage("station.dep.code", langCode));
        } else {
          ViewCatDepartmentBO viewCatDepartmentBO = transmissionDao.findDepartmentByCode(deptCode, userId);
          if (Objects.isNull(viewCatDepartmentBO)) {
            ExcelDataUltils.addMessage(errData, "err.incorrect",
                messageSource.getMessage("odf.deptCode", langCode));
          } else {
            entity.setDeptId(viewCatDepartmentBO.getDeptId());
          }
        }
      }
      // owner id
      if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getOwnerCode())) {
        entity.setOwnerId(null);
      } else {
        ViewCatItemBO catOwner = transmissionDao.findCatItemByItemNameAndCaregoryCode(infraSleeveDTO.getOwnerCode(), "CAT_OWNER");

        if (catOwner == null) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("odf.ownerName", langCode));
        } else {
          entity.setOwnerId(catOwner.getItemId());
        }
      }

      // vendor name, insert vendor id
      if (!CommonUtils.isNullOrEmpty(infraSleeveDTO.getVendorCode())) {
        ViewCatItemBO listVendor = transmissionDao.findCatItemByItemNameAndCaregoryCode(infraSleeveDTO.getVendorCode(), "VENDOR");
        if (listVendor == null) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("odf.vendorName", langCode));
        } else {
          entity.setVendorId(listVendor.getItemId());
        }
      } else {
        entity.setVendorId(null);
      }
      // purpose
      if (flagList.getPurpose().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getPurpose())) {
          ExcelDataUltils.addMessage(errData, "err.empty",
              messageSource.getMessage("sleeves.purpose ", langCode));
        } else if (infraSleeveDTO.getPurpose()
            .equals(messageSource.getMessage("sleeve.purpose.normal", langCode))) {
          entity.setPurpose(0L);
        } else if (infraSleeveDTO.getPurpose()
            .equals(messageSource.getMessage("sleeve.purpose.abnormal", langCode))) {
          entity.setPurpose(1L);
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("sleeves.purpose", langCode));
        }

        if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getStatus())) {
          ExcelDataUltils.addMessage(errData, "err.empty",
              messageSource.getMessage("common.label.status", langCode));
        } else if (infraSleeveDTO.getStatus().equals(messageSource.getMessage("sleeve.status.use", langCode))) {
          entity.setsStatus(0L);
        } else if (infraSleeveDTO.getStatus().equals(messageSource.getMessage("sleeve.status.notUse", langCode))) {
          entity.setsStatus(1L);
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("common.label.status", langCode));
        }
      }
      // installation date
      if (flagList.getInstalationDate().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getInstalationDate())) {
          ExcelDataUltils.addMessage(errData, "err.empty",
              messageSource.getMessage("sleeves.installation.date", langCode));

        } else {
          Date dateFormat;
          try {
            if (isValidDate(infraSleeveDTO.getInstalationDate().toString(), Constants.DATE.DEFAULT_FORMAT, "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")) {
              dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT)
                  .parse(infraSleeveDTO.getInstalationDate());
              java.sql.Date sqlDate = new java.sql.Date(dateFormat.getTime());
              entity.setInstallationDate(sqlDate);
            } else {
              ExcelDataUltils.addMessage(errData, "err.dateFormat",
                  messageSource.getMessage("sleeves.installation.date", langCode));
            }

          } catch (ParseException e) {
            log.error("Exception", e);
            ExcelDataUltils.addMessage(errData, "err.dateFormat",
                messageSource.getMessage("sleeves.installation.date", langCode));
          }
        }
      }
      // modify date
      if (flagList.getModifiedDate().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(infraSleeveDTO.getModifiedDate())) {
          ExcelDataUltils.addMessage(errData, "err.empty",
              messageSource.getMessage("sleeves.modify.date", langCode));

        } else {
          Date dateFormat;
          try {
            if (isValidDate(infraSleeveDTO.getModifiedDate().toString(), Constants.DATE.DEFAULT_FORMAT, "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")) {
              dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT)
                  .parse(infraSleeveDTO.getModifiedDate());
              java.sql.Date sqlDate = new java.sql.Date(dateFormat.getTime());

              if (entity.getInstallationDate().getTime() > sqlDate.getTime()) {
                ExcelDataUltils.addMessage(errData,
                    messageSource.getMessage("sleeve.err.modifyDate.earlier.instalationDate", langCode), null);

              } else {
                entity.setUpdateTime(sqlDate);
              }
            } else {
              ExcelDataUltils.addMessage(errData, "err.dateFormat",
                  messageSource.getMessage("sleeves.modify.date", langCode));
            }

          } catch (ParseException e) {
            log.error("Exception", e);
            ExcelDataUltils.addMessage(errData, "err.dateFormat",
                messageSource.getMessage("sleeves.modify.date", langCode));
          }
        }
      }
      // serial no
      if (flagList.getSerialCode().equals("Y")) {
        if (infraSleeveDTO.getSerialCode().length() > 100) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.100",
              messageSource.getMessage("sleeves.label.serial", langCode));
        } else {
          entity.setSerial(infraSleeveDTO.getSerialCode());
        }
      }
      // get
      if (flagList.getNote().equals("Y")) {
        if (infraSleeveDTO.getNote().length() > 500) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.500",
              messageSource.getMessage("sleeves.cause.error", langCode));
        } else {
          entity.setNote(infraSleeveDTO.getNote());
        }
      }
      if (errData.isEmpty()) {
        infraSleeveDao.saveSleeve(entity);
        successRecord++;
      } else {
        errRecord++;
      }
      StringBuilder rs = new StringBuilder();
      for (Map.Entry<String, String> entry : errData.entrySet()) {

        String temp = "";
        String key = entry.getKey();
        String value = entry.getValue();
        if (value == null) {
          temp = temp + key + "\n";
        } else {
          temp = temp + messageSource.getMessage(key, langCode, Arrays.asList(value)) + "\n";
        }
        rs.append(temp);
      }
      if (CommonUtils.isNullOrEmpty(rs.toString())) {
        rs.append("OK");
      }
      mapObj.put(index++, rs.toString());
    }

    String savePath = ExcelDataUltils.writeResultExcel(file, new InfraSleeveDTO(), mapObj,
        messageSource.getMessage("sleeve.result.update.fileName", langCode));

    return savePath + StringPool.PLUS + successRecord + StringPool.PLUS + errRecord;

  }

  /**
   * @param srcDate
   * @param format
   * @return
   */
  private static boolean isValidDate(String srcDate, String format, String pattern) {

    boolean result = true;

    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setLenient(false);

    try {
      dateFormat.parse(srcDate);
      if (!srcDate.matches(pattern)) {
        return false;
      }
    } catch (ParseException e) {
      result = false;
    } catch (NullPointerException e) {
      result = false;
    }

    return result;
  }

  /**
   * get max odf index
   *
   * @param laneCode
   * @author hungnv
   * @date 1/10/2019
   */
  private Integer getMaxSleeveIndex(String laneCode) {
    Integer sleeveIndex;
    Integer maxIndex = Constains.NUMBER_ONE;
    List<InfraSleevesBO> odfInfraSleevesBO = infraSleeveDao.findSleeveByLaneCode(laneCode);

    for (InfraSleevesBO infraSleevesBO : odfInfraSleevesBO) {
      sleeveIndex = Constains.NUMBER_ZERO;
      String sleeveIndexString = infraSleevesBO.getSleeveCode().substring(
          infraSleevesBO.getSleeveCode().length() - Constains.NUMBER_THREE,
          infraSleevesBO.getSleeveCode().length());
      if (sleeveIndexString.matches("\\d{3}")) {
        sleeveIndex = Integer.parseInt(sleeveIndexString);
        if (sleeveIndex > maxIndex) {
          maxIndex = sleeveIndex;
        }
      }
    }
    return maxIndex;

  }
}
