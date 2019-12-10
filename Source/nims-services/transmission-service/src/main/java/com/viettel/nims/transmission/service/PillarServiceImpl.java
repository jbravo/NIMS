package com.viettel.nims.transmission.service;
import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.commom.*;
import com.viettel.nims.transmission.dao.*;
import com.viettel.nims.transmission.dao.PillarDao;
import com.viettel.nims.transmission.dao.InfraCablesDao;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.dto.InfraPillarDTO;
import com.viettel.nims.transmission.model.dto.PGInfraPillarsDto;
import com.viettel.nims.transmission.model.view.*;
import com.viettel.nims.transmission.model.view.ViewPillarsBO;
import com.viettel.nims.transmission.model.view.ViewHangCableBO;
import com.viettel.nims.transmission.model.view.ViewInfraSleevesBO;
import com.viettel.nims.transmission.postgre.dao.PGInfraPillarsDao;
import com.viettel.nims.transmission.utils.Constains;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
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
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by VAN-BA on 8/24/2019.
 */
@Slf4j
@Service
@Transactional(transactionManager = "globalTransactionManager")
public class PillarServiceImpl implements PillarService {

  @Autowired
  PillarDao pillarDao;

  @Autowired
  TransmissionDao transmissionDao;

  @Autowired
  InfraCablesDao infraCablesDao;

  @Autowired
  InfraSleeveDao infraSleeveDao;

  @Autowired
  PGInfraPillarsDao pgInfraPillarsDao;

  @Autowired
  MessageResource messageSource;

  private final String HANG = "hang";
  private final String CABLE = "cable";
  private final String SLEEVE = "sleeve";
  private final String PILLARCODE = "pillarCode";
  private final String OTHER = "other";
  private final String LISTDELETEPILLAR = "listDeletePillarId";

//  @Override
//  // Tim kiem co ban
//  public ResponseEntity<?> findBasicPillar(PillarsBO pillarsBO) {
//    try {
//      FormResult result = pillarDao.findBasicPillar(pillarsBO);
////      Response<FormResult> response = new Response<>();
////      response.setContent(result);
////      return new ResponseEntity<>(response, HttpStatus.OK);
//      return ResponseBase.createResponse(result, Constains.UPDATE, 200);
//    } catch (Exception e) {
//      return ResponseBase.createResponse(null, "error", 500);
//    }
//  }

  @Override
  // Get List pillar code
  public ResponseEntity<?> getPillarTypeCodeList() {
    try {
      FormResult result = pillarDao.getPillarTypeCodeList();
//      Response<FormResult> response = new Response<>();
//      response.setContent(result);
//      return new ResponseEntity<>(response, HttpStatus.OK);
      return ResponseBase.createResponse(result, Constains.UPDATE, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

//  @Override
//  // Get List Owner
//  public ResponseEntity<?> getOwnerName() {
//    try {
//      FormResult result = pillarDao.getOwnerName();
////      Response<FormResult> response = new Response<>();
////      response.setContent(result);
////      return new ResponseEntity<>(response, HttpStatus.OK);
//      return ResponseBase.createResponse(result, Constains.UPDATE, 200);
//    } catch (Exception e) {
//      return ResponseBase.createResponse(null, "error", 500);
//    }
//  }

  @Override
  // Tim kiem nang cao
  public ResponseEntity<?> findAdvancePillar(PillarsBO pillarsBO, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    try {
      FormResult result = pillarDao.findAdvancePillar(pillarsBO, userId);
//      Response<FormResult> response = new Response<>();
//      response.setContent(result);
//      return new ResponseEntity<>(response, HttpStatus.OK);
      return ResponseBase.createResponse(result, Constains.UPDATE, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  // Lay danh sach cot cho mang xong
  public ResponseEntity<?> getPillarList(PillarsBO pillarsBO, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    try {
      FormResult result = pillarDao.getPillarList(pillarsBO, userId);
//      Response<FormResult> response = new Response<>();
//      response.setContent(result);
//      return new ResponseEntity<>(response, HttpStatus.OK);
      return ResponseBase.createResponse(result, Constains.UPDATE, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  // Get List laneCode
  public ResponseEntity<?> getLaneCodeList(InfraCableLanesBO entity) {
    try {
      FormResult result = pillarDao.getLaneCodeList(entity);
//      Response<FormResult> response = new Response<>();
//      response.setContent(result);
//      return new ResponseEntity<>(response, HttpStatus.OK);
//      return new ResponseEntity<>(response, HttpStatus.OK);
      return ResponseBase.createResponse(result, Constains.UPDATE, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  // Get List laneCode cho popup
  public ResponseEntity<?> getListLaneCode(InfraCableLanesBO entity, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    try {
      FormResult result = pillarDao.getListLaneCode(entity, userId);
//      Response<FormResult> response = new Response<>();
//      response.setContent(result);
//      return new ResponseEntity<>(response, HttpStatus.OK);
      return ResponseBase.createResponse(result, Constains.UPDATE, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  // Get danh sach tuyen cap vat qua cot/be
  public ResponseEntity<?> getListLaneCodeHangPillar(PillarsBO entity, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    try {
      FormResult result = pillarDao.getListLaneCodeHangPillar(entity,userId);
//      Response<FormResult> response = new Response<>();
//      response.setContent(result);
//      return new ResponseEntity<>(response, HttpStatus.OK);
      return ResponseBase.createResponse(result, Constains.UPDATE, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

//  @Override
//  // Get tuyen cap cho cot/be chua mang xong
//  public ResponseEntity<?> getListLaneCodeForSleeve(InfraCableLanesBO entity, String langCode, HttpServletRequest request) {
//    Long userId = CommonUtil.getUserId(request);
//    try {
//      FormResult result = pillarDao.getListLaneCodeForSleeve(entity, userId);
////      Response<FormResult> response = new Response<>();
////      response.setContent(result);
//      return ResponseBase.createResponse(result, Constains.UPDATE, 200);
//    } catch (Exception e) {
//      return ResponseBase.createResponse(null, "error", 500);
//    }
//  }

  @Override
  // Them
  public ResponseEntity<?> savePillar(PillarsBO pillarsBO) {
    JSONObject jsonObject = new JSONObject();
    try {
      if (pillarsBO != null) {
        if (pillarsBO.getLaneCode() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co ma tuyen", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (pillarsBO.getDeptId() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co don vi", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (pillarsBO.getPillarCode() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co ma cot", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (pillarsBO.getOwnerId() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co chu so huu", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (pillarsBO.getPillarTypeId() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co ma loai cot", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (pillarsBO.getLongitude() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co kinh do", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (pillarsBO.getLatitude() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co vi do", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (pillarsBO.getLocationId() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co dia ban", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else {
          PGInfraPillarsDto pgDto = new PGInfraPillarsDto();
          if (pillarsBO.getPillarId() != null) {
            pgDto.setUpdate(Boolean.TRUE);
          }
          GeometryFactory gf = new GeometryFactory();
          Point point = gf.createPoint(new Coordinate(pillarsBO.getLongitude(), pillarsBO.getLatitude()));
          pillarsBO.setGeometry(point);
          Long result = pillarDao.savePillar(pillarsBO);
          if (result != 0) {
            pgDto.setPillar_id(result);
            pgDto.setPillar_code(pillarsBO.getPillarCode());
            pgDto.setDept_id(pillarsBO.getDeptId());
            pgDto.setLocation_id(pillarsBO.getLocationId());
            pgDto.setStatus(pillarsBO.getStatus());
            pgDto.setLongitude(pillarsBO.getLongitude());
            pgDto.setLatitude(pillarsBO.getLatitude());
            pgDto.setAddress(pillarsBO.getAddress());
            pgDto.setOwner_id(pillarsBO.getOwnerId());
            pgDto.setPillar_type_id(pillarsBO.getPillarTypeId());
            pgInfraPillarsDao.insertOrUpdate(pgDto);
            Response<FormResult> response = new Response<>();
            response.setStatus(HttpStatus.OK.toString());
            return ResponseBase.createResponse(null, Constains.SUCCESS, 200);
          } else {
            return ResponseBase.createResponse(null, Constains.FAILED, 500);
          }
        }
      } else {
        return ResponseBase.createResponse(null, Constains.NOT_MODIFIED, 304);
      }
    } catch (Exception e) {
      return ResponseBase.createResponse(null, Constains.NOT_MODIFIED, 304);
    }
  }

  @Override
  public ResponseEntity<?> findPillarById(Long id) {
    try {
      PillarsBO pillarsBO = pillarDao.findPillarById(id);
      pillarsBO.setLatitude(pillarsBO.getGeometry().getY());
      pillarsBO.setLongitude(pillarsBO.getGeometry().getX());
//      Response<PillarsBO> response = new Response<>();
//      response.setStatus(Constains.UPDATE);
//      response.setContent(pillarsBO);
      return ResponseBase.createResponse(pillarsBO, Constains.UPDATE, 200);
    } catch (Exception e) {
      log.error("Exception", e);
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  public ResponseEntity<?> getPillarIndex(String laneCode) {
    try {
      String pillarsBO = pillarDao.getPillarIndex(laneCode);
//      Response<String> response = new Response<>();
//      response.setContent(pillarsBO);
      return ResponseBase.createResponse(pillarsBO, Constains.UPDATE, 200);
    } catch (Exception e) {
      log.error("Exception", e);
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  public ResponseEntity<?> isExitCode(PillarsBO pillarsBO) {
    try {
      Boolean result = pillarDao.isExitByCode(pillarsBO);
//      Response<Boolean> response = new Response<>();
//      response.setContent(result);
      return ResponseBase.createResponse(result, Constains.UPDATE, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  public boolean checkExitPillarCode(String pillarCode, Long pillarId) {
    return pillarDao.checkExitPillarCode(pillarCode, pillarId);
  }

  @Override
  // Xoa Cot
  public List<JSONObject> deleteMultipe(List<PillarsBO> pillarsBOList) {
    JSONObject responseJson = new JSONObject();
    boolean isErrorList = false;

    List<JSONObject> listResponse = new ArrayList<JSONObject>();

    for (PillarsBO pillarsBO : pillarsBOList) {
      JSONObject error = new JSONObject();

      List<String> listHangError = new ArrayList<String>();
      List<String> listCableError = new ArrayList<String>();
      List<String> listSleeveError = new ArrayList<String>();

      boolean other = false;

      // List Sleeves in Pool
      List<ViewInfraSleevesBO> sleeves = pillarDao.listSleevesInPillar(pillarsBO.getPillarId());
      // List cables - By HolderId Pool
      List<InfraCablesBO> cables = infraCablesDao.findCableByHolderId(pillarsBO.getPillarId());
      // List Hangcables - via Pool
      List<ViewHangCableBO> hangCables = pillarDao.listPillarHangCable(pillarsBO.getPillarId());

      if (cables.size() == 0 && sleeves.size() == 0 && hangCables.size() == 0) {
        System.out.println(
            "--------------TH 2.1 -khong la diem dau/cuoi cua doan cap nao VA ko co mang xong VA be KHONG CO tuyen cap vat qua");
        other = true;
      }

      if (sleeves.size() > 0) {

        boolean isWelding = false;
        for (ViewInfraSleevesBO v : sleeves) {
          List<InfraSleevesBO> listWedSleeves = infraSleeveDao.findWeldSleeveBySleeveId(v.getSleeveId());
          if (listWedSleeves.size() > 0) {
            listSleeveError.add(v.getSleeveCode());
            System.out.println("--------------SleeveCode dc han noi : " + v.getSleeveCode());
            isWelding = true;
          }
        }
        if (isWelding) {
          other = true;
          isErrorList = true;
          System.out.println(
              "--------------TH2.5 -co mang xong da han noi, ko dc xoa, luu thong tin mang xong han noi o tren ");
        } else {
          if (cables.size() == 0 && hangCables.size() == 0) {
            other = true;
            System.out.println(
                "--------------TH2.2 -khong la diem dau/cuoi cua doan cap nao VA be KHONG CO tuyen cap vat qua VA tat ca cac mong xong deu chua dc han noi");
          }
        }
      }

      if (cables.size() == 0 && sleeves.size() == 0 && hangCables.size() > 0) {
        other = true;
        for (ViewHangCableBO v : hangCables) {
          listHangError.add(v.getCableCode());
        }
        System.out.println(
            "--------------TH 2.3 -khong la diem dau/cuoi cua doan cap nao VA ko co mang xong VA be CO tuyen cap vat qua - can xac nhan de xoa?");
      }

      if (cables.size() > 0) {
        other = true;
        isErrorList = true;
        System.out.println(
            "--------------TH2.4 : la diem dau/cuoi cua doan cap bat ky/khong  the xoa hien thi thong bao");

        for (InfraCablesBO cable : cables) {
          listCableError.add(cable.getCableCode());
          System.out.println("--------------TH 2.4 -la diem dau/cuoi cua doan cap : " + cable.getCableCode());
        }

      }
      if (!other) {
        isErrorList = true;
        System.out.println("---------TH2.6 : Da co loi xay ra, he thong khong the xoa ban ghi");
        error.put(PILLARCODE, pillarsBO.getPillarCode());
        error.put(OTHER, true);
      }

      if (listCableError.size() > 0 || listSleeveError.size() > 0 || listHangError.size() > 0) {
        error.put(PILLARCODE, pillarsBO.getPillarCode()); // PILLARCODE
        error.put(CABLE, listCableError); // 2.4
        error.put(SLEEVE, listSleeveError); // 2.5
        error.put(HANG, listHangError); // 2.3

        List<Long> listPillarId = new ArrayList<Long>();
        for (PillarsBO i : pillarsBOList) {
          listPillarId.add(i.getPillarId());
        }
        error.put(LISTDELETEPILLAR, listPillarId);
        listResponse.add(error);
      } else if (error.get(OTHER) != null) {
        listResponse.add(error);
      }
    }
    if (!isErrorList) {

      if (!CollectionUtils.isEmpty(listResponse)) {
        return listResponse;
      }

      for (PillarsBO pillarsBO : pillarsBOList) {
        List<ViewInfraSleevesBO> sleeves = pillarDao.listSleevesInPillar(pillarsBO.getPillarId());
        for (ViewInfraSleevesBO v : sleeves) {
          infraSleeveDao.delete(v.getSleeveId());
        }
        pillarDao.deletePillar(pillarsBO.getPillarId());
        pgInfraPillarsDao.delete(pillarsBO.getPillarId());
      }
      return new ArrayList<JSONObject>();
    } else {
      System.out.println("khong the xoa danh sach da chon vi co it nhat 1 ban ghi khong the xoa !!");
      return listResponse;
    }
  }

  @Override
  public List<JSONObject> deleteHangConfirm(List<PillarsBO> pillarsBOList) {
    for (PillarsBO pillarsBO : pillarsBOList) {
      pillarDao.deletePillar(pillarsBO.getPillarId());
      List<ViewInfraSleevesBO> sleeves = pillarDao.listSleevesInPillar(pillarsBO.getPillarId());
      for (ViewInfraSleevesBO v : sleeves) {
        infraSleeveDao.delete(v.getSleeveId());
      }
    }
    return new ArrayList<JSONObject>();
  }

  @Override
  public String exportExcel(PillarsBO pillarsBO, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = pillarDao.findAdvancePillar(pillarsBO, userId);
    List<ViewPillarsBO> data = (List<ViewPillarsBO>) result.getListData();

//    if (CollectionUtils.isEmpty(data)) {
//      return null;
//    }

    Workbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("pillar.sheet", langCode));

    // Set title
    Row firstRow = sheet.createRow(0);
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
    Cell titleCell = firstRow.createCell(0);
    titleCell.setCellValue(messageSource.getMessage("pillar.title", langCode));
    titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

    Row thirdRow = sheet.createRow(2);
    Cell titleCell2 = thirdRow.createCell(6);
    titleCell2.setCellValue(messageSource.getMessage("station.report.date", langCode) + CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
    titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

//     Set header
    List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pillar.pillarCode", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pillar.pillarTypeCode", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.ownerName", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.constructionDate", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.status", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pillar.deptCode", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pillar.locationCode", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.address", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.latitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.longitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.note", langCode), 20 * 256));
    Row secondRow = sheet.createRow(4);

    for (int i = 0; i < headerDTOList.size(); i++) {
      Cell cell = secondRow.createCell(i);
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
    path = path + "EXPORT_COT" + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm") + ".xlsx";

    DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
    // Set data
    for (ViewPillarsBO item : data) {

      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell1 = row.createCell(1);
      cell1.setCellValue(item.getPillarCode() != null ? item.getPillarCode() : "");
      cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell2 = row.createCell(2);
      cell2.setCellValue(item.getPillarTypeCode() != null ? item.getPillarTypeCode() : "");
      cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell3 = row.createCell(3);
      cell3.setCellValue(item.getOwnerName() != null ? item.getOwnerName() : "");
      cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell4 = row.createCell(4);
      if (item.getConstructionDate() != null) {
        cell4.setCellValue(df.format(item.getConstructionDate()));
      } else {
        cell4.setCellValue("");
      }
      cell4.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell5 = row.createCell(5);
      if (item.getStatus() != null) {
        item.setStatusName(item.getStatus() == 0 ? messageSource.getMessage("pillar.status.one", langCode) : item.getStatus() == 1 ? messageSource.getMessage("cable.statusName.one", langCode) : "");
      }
      cell5.setCellValue(item.getStatusName() != null ? item.getStatusName() : "");
      cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell6 = row.createCell(6);
      cell6.setCellValue(item.getDeptCode() != null ? item.getDeptCode() : "");
      cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell7 = row.createCell(7);
      cell7.setCellValue(item.getLocationCode() != null ? item.getLocationCode() : "");
      cell7.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell8 = row.createCell(8);
      cell8.setCellValue(item.getAddress() != null ? item.getAddress() : "");
      cell8.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

//      if (item.getGeometry() != null) {
//        item.setLongitude(item.getGeometry().getX());
//        item.setLatitude(item.getGeometry().getY());
//      }

      Cell cell9 = row.createCell(9);
      cell9.setCellValue(item.getLatitude() != null ? item.getLatitude().toString() : "");
      cell9.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell10 = row.createCell(10);
      cell10.setCellValue(item.getLongitude() != null ? item.getLongitude().toString() : "");
      cell10.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell11 = row.createCell(11);
      cell11.setCellValue(item.getNote() != null ? item.getNote() : "");
      cell11.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

    }
    try {
//     Write file
      FileOutputStream outputStream = new FileOutputStream(path);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      log.error("Exception", e);
//      logger.error(e.getMessage());
    }

    return path;
  }

  @Override
  public String exportExcelChose(List<ViewPillarsBO> data, String langCode) {
//    if (CollectionUtils.isEmpty(data)) {
//      return null;
//    }

    Workbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("pillar.sheet", langCode));

    // Set title
    Row firstRow = sheet.createRow(0);
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
    Cell titleCell = firstRow.createCell(0);
    titleCell.setCellValue(messageSource.getMessage("pillar.title", langCode));
    titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

    Row thirdRow = sheet.createRow(2);
    Cell titleCell2 = thirdRow.createCell(6);
    titleCell2.setCellValue(messageSource.getMessage("station.report.date", langCode) + CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
    titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

//     Set header
    List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pillar.pillarCode", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pillar.pillarTypeCode", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.ownerName", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.constructionDate", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.status", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pillar.deptCode", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pillar.locationCode", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.address", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.latitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.longitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.note", langCode), 20 * 256));
    Row secondRow = sheet.createRow(4);

    for (int i = 0; i < headerDTOList.size(); i++) {
      Cell cell = secondRow.createCell(i);
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
    path = path + "EXPORT_COT" + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm") + ".xlsx";

    DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
    // Set data
    for (ViewPillarsBO item : data) {

      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell1 = row.createCell(1);
      cell1.setCellValue(item.getPillarCode() != null ? item.getPillarCode() : "");
      cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell2 = row.createCell(2);
      cell2.setCellValue(item.getPillarTypeCode() != null ? item.getPillarTypeCode() : "");
      cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell3 = row.createCell(3);
      cell3.setCellValue(item.getOwnerName() != null ? item.getOwnerName() : "");
      cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell4 = row.createCell(4);
      if (item.getConstructionDate() != null) {
        cell4.setCellValue(df.format(item.getConstructionDate()));
      } else {
        cell4.setCellValue("");
      }
      cell4.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell5 = row.createCell(5);
      if (item.getStatus() != null) {
        item.setStatusName(item.getStatus() == 0 ? messageSource.getMessage("pillar.status.one", langCode) : item.getStatus() == 1 ? messageSource.getMessage("cable.statusName.one", langCode) : "");
      }
      cell5.setCellValue(item.getStatusName() != null ? item.getStatusName() : "");
      cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell6 = row.createCell(6);
      cell6.setCellValue(item.getDeptCode() != null ? item.getDeptCode() : "");
      cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell7 = row.createCell(7);
      cell7.setCellValue(item.getLocationCode() != null ? item.getLocationCode() : "");
      cell7.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell8 = row.createCell(8);
      cell8.setCellValue(item.getAddress() != null ? item.getAddress() : "");
      cell8.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell9 = row.createCell(9);
      cell9.setCellValue(item.getLatitude() != null ? item.getLatitude().toString() : "");
      cell9.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell10 = row.createCell(10);
      cell10.setCellValue(item.getLongitude() != null ? item.getLongitude().toString() : "");
      cell10.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell11 = row.createCell(11);
      cell11.setCellValue(item.getNote() != null ? item.getNote() : "");
      cell11.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));
    }

    try {
//     Write file
      FileOutputStream outputStream = new FileOutputStream(path);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      log.error("Exception", e);
//      logger.error(e.getMessage());
    }
    return path;
  }


  //------------------------KienNT---------------------
  @Override
  public String downloadTemplate(List<ViewPillarsBO> viewPillarsBOList, Integer type, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    String savePath = messageSource.getMessage("report.out", langCode);
    File dir = new File(savePath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    if (type == 1) {
      savePath = savePath + "Temp_IMP_Cot_Insert_" + CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
    } else {
      savePath = savePath + "Temp_IMP_Cot_Update_" + CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
    }

    InputStream excelFile;
    XSSFWorkbook workbook = null;
    try {
      excelFile = new ClassPathResource(Constants.TEMPLATE_FILE.TEAMPLATE_PILLAR).getInputStream();
      workbook = new XSSFWorkbook(excelFile);
    } catch (Exception e) {
      log.error("Exception", e);
    }

    XSSFSheet sheetData = workbook.getSheetAt(0);

    int rowNum = 4;
    int index = 1;
    for (ViewPillarsBO viewPillarsBO : viewPillarsBOList) {
      Row row = sheetData.createRow(rowNum++);
      int i = 0;
      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(viewPillarsBO.getLaneCode() == null ? "" : viewPillarsBO.getLaneCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell2 = row.createCell(i++);
      cell2.setCellValue(viewPillarsBO.getPillarIndex());
      cell2.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell3 = row.createCell(i++);
      cell3.setCellValue(viewPillarsBO.getPillarCode() == null ? "" : viewPillarsBO.getPillarCode());
      cell3.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell4 = row.createCell(i++);
      cell4.setCellValue(viewPillarsBO.getPillarTypeCode() == null ? "" : viewPillarsBO.getPillarTypeCode());
      cell4.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

//      Cell cell5 = row.createCell(i++);
//      cell5.setCellValue(viewPillarsBO.getDeptId() == null ? "" : viewPillarsBO.getDeptId().toString());
//      cell5.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
      Cell cell5 = row.createCell(i++);
      ViewCatDepartmentBO catDept = transmissionDao.findDepartmentById(viewPillarsBO.getDeptId(), userId);
      cell5.setCellValue(catDept == null ? "" : catDept.getDeptCode());
      cell5.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell6 = row.createCell(i++);
      ViewTreeCatLocationBO catLocation = transmissionDao.findCatLocationById(viewPillarsBO.getLocationId(), userId);
      cell6.setCellValue(catLocation == null ? "" : catLocation.getLocationCode());
      cell6.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell7 = row.createCell(i++);
      ViewCatItemBO catOwner = transmissionDao.findCatItemByCategoryCodeAndId(viewPillarsBO.getOwnerId(), "CAT_OWNER");
      cell7.setCellValue(catOwner == null ? "" : catOwner.getItemCode());
      cell7.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell8 = row.createCell(i++);
      cell8.setCellValue(viewPillarsBO.getAddress() == null ? "" : viewPillarsBO.getAddress());
      cell8.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
      Cell cell9 = row.createCell(i++);
      cell9.setCellValue(viewPillarsBO.getConstructionDate() == null ? "" : simpleDateFormat.format(viewPillarsBO.getConstructionDate()));
      cell9.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell10 = row.createCell(i++);
      cell10.setCellValue(viewPillarsBO.getStatusName() == null ? "" : viewPillarsBO.getStatusName());
      cell10.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell11 = row.createCell(i++);
      cell11.setCellValue(viewPillarsBO.getLongitude() == null ? "" : viewPillarsBO.getLongitude().toString());
      cell11.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell12 = row.createCell(i++);
      cell12.setCellValue(viewPillarsBO.getLatitude() == null ? "" : viewPillarsBO.getLatitude().toString());
      cell12.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell13 = row.createCell(i++);
      cell13.setCellValue(viewPillarsBO.getNote() == null ? "" : viewPillarsBO.getNote());
      cell13.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

    }

    // DM_dia ban station location
    CatLocationBO locationEntity = new CatLocationBO();
    FormResult formResultLocation = transmissionDao.findCatLocation(locationEntity, userId);
    XSSFSheet sheetLocation = workbook.getSheetAt(1);
    List<ViewCatLocationBO> listLocation = (List<ViewCatLocationBO>) formResultLocation.getListData();
    index = 1;
    rowNum = 2;
    for (ViewCatLocationBO bo : listLocation) {
      int i = 0;
      Row row = sheetLocation.createRow(rowNum++);
      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getLocationCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell2 = row.createCell(i++);
      cell2.setCellValue(bo.getLocationName() == null ? "" : bo.getLocationName());
      cell2.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell3 = row.createCell(i++);
      cell3.setCellValue(bo.getPathLocalName() == null ? "" : bo.getPathLocalName());
      cell3.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

    }

    // DM_don vi station dept
    CatDepartmentEntity departmentEntity = new CatDepartmentEntity();
    FormResult formResult = transmissionDao.findDepartment(departmentEntity, userId);
    XSSFSheet sheetDept = workbook.getSheetAt(2);
    List<ViewCatDepartmentBO> listDept = (List<ViewCatDepartmentBO>) formResult.getListData();
    index = 1;
    rowNum = 3;
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


    //DM_Tuyen
    InfraCableLanesBO infraCableLanesBO = new InfraCableLanesBO();
//    FormResult formResult1 = transmissionDao.findLaneCode(infraCableLanesBO, userId);
    FormResult formResult1 = pillarDao.getListLaneCode(infraCableLanesBO, userId);
    XSSFSheet sheetInfraCableLanes = workbook.getSheetAt(3);
    List<ViewInfraCableLaneBO> infraCableLanesBOList = (List<ViewInfraCableLaneBO>) formResult1.getListData();
    index = 1;
    rowNum = 2;
    for (ViewInfraCableLaneBO bo : infraCableLanesBOList) {
      int i = 0;
      Row row = sheetInfraCableLanes.createRow(rowNum++);
      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getLaneCode() == null ? "" : bo.getLaneCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

    }

    List<ViewCatItemBO> listOwner = transmissionDao.findCatItemByCategoryId("CAT_OWNER");
    XSSFSheet sheetOwner = workbook.getSheetAt(4);

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
    // DM_LOAI_cot
    CatPillarTypeBO catPillarTypeBO = new CatPillarTypeBO();
    FormResult formResult2 = transmissionDao.findPillarTypeCode(catPillarTypeBO);
    XSSFSheet sheetPillarTypeCode = workbook.getSheetAt(5);
    List<CatPillarTypeBO> catPillarTypeBOList = (List<CatPillarTypeBO>) formResult2.getListData();
    index = 1;
    rowNum = 2;
    for (CatPillarTypeBO bo : catPillarTypeBOList) {
      int i = 0;
      Row row = sheetPillarTypeCode.createRow((rowNum++));

      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getPillarTypeCode() == null ? "" : bo.getPillarTypeCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
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

  @Override
  public String importPillar(MultipartFile file, String langCode, HttpServletRequest request) throws IOException, ClassNotFoundException {
    Long userId = CommonUtil.getUserId(request);
    List<InfraPillarDTO> datas;
    InfraPillarDTO flaglist;
    try {
      InfraPillarDTO header = ExcelDataUltils.getHeaderList(file, new InfraPillarDTO());
      if (!messageSource.checkEqualHeader(header, langCode)) {
        return "template-error";
      }
      datas = (List<InfraPillarDTO>) ExcelDataUltils.getListInExcel(file, new InfraPillarDTO());
      flaglist = ExcelDataUltils.getFlagList(file, new InfraPillarDTO());
    } catch (Exception e) {
      e.printStackTrace();
      return "template-error";
    }
    int successRecord = 0;
    int errRecord = 0;
    Map<Long, String> mapObj = new HashMap<>();

    long index = 0;
    for (InfraPillarDTO dto : datas) {
      Map<String, String> errData = new HashMap<>();
      PillarsBO entity = new PillarsBO();
      //
      if (CommonUtils.isNullOrEmpty(dto.getLaneCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("pillar.laneCode", langCode));
      } else {
        InfraCableLanesBO bo = transmissionDao.findLaneCodeByCode(dto.getLaneCode());
        if (bo == null) {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("pillar.laneCode", langCode));
        } else {
          entity.setLaneCode(dto.getLaneCode());
        }
      }

//      String pillarIndexRegex = "[0-9]";
//      if (!dto.getPillarIndex().matches(pillarIndexRegex) && dto.getPillarIndex().length() != 4) {
//        ExcelDataUltils.addMessage(errData, "err.pillarIndex", messageSource.getMessage("pillar.pillarIndex", langCode));
//      } else {
//        entity.setPillarIndex(dto.getPillarIndex());
//      }
      if (CommonUtils.isNullOrEmpty(dto.getPillarIndex())) {
        String pillarIndex = pillarDao.getPillarIndex(entity.getLaneCode());
        int a = Integer.parseInt(pillarIndex);
        if (a < 9999) {
//          a = a + 1;
          if (a < 10) {
            entity.setPillarIndex("000" + a);
          } else if (a < 100) {
            entity.setPillarIndex("00" + a);
          } else if (a < 1000) {
            entity.setPillarIndex("0" + a);
          } else {
            entity.setPillarIndex(a + "");
          }
          dto.setPillarIndex(entity.getPillarIndex());
        } else {
          ExcelDataUltils.addMessage(errData, "err.notGenerate.pillarIndex",
              dto.getPillarCode());
        }
      } else {
        String pillarIndexRegex = "[0-9]";
        if (!dto.getPillarIndex().matches(pillarIndexRegex) && dto.getPillarIndex().length() != 4) {
          ExcelDataUltils.addMessage(errData, "err.pillarIndex", messageSource.getMessage("pillar.pillarIndex", langCode));
        } else {
          entity.setPillarIndex(dto.getPillarIndex());
        }
      }

      if (!CommonUtils.isNullOrEmpty(entity.getLaneCode()) && !CommonUtils.isNullOrEmpty(entity.getPillarIndex())) {
        String pillarCode = dto.getLaneCode() + "_" + entity.getPillarIndex();
        boolean isExitCode = transmissionDao.isExitByCode(pillarCode);
        if (isExitCode) {
          ExcelDataUltils.addMessage(errData, "err.exits", messageSource.getMessage("pillar.pillarCode", langCode));
        } else {
          entity.setPillarCode(pillarCode);
          dto.setPillarCode(entity.getPillarCode());
        }
      }
      //5 Ma loai cot
      CatPillarTypeBO pillarsTypeCode = transmissionDao.getPillarTypeCodeList(dto.getPillarTypeCode(), "PILLAR_TYPE_CODE");
      if (pillarsTypeCode != null) {
        entity.setPillarTypeId(pillarsTypeCode.getPillarTypeId());
      } else {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("pillar.pillarTypeCode", langCode));
      }

      //6 Ma don vi
      if (CommonUtils.isNullOrEmpty(dto.getDeptCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.dep", langCode));
      } else {
        ViewCatDepartmentBO bo = transmissionDao.findDepartmentByCode(dto.getDeptCode(), userId);
        if (bo == null) {
          ExcelDataUltils.addMessage(errData, "err.noscope", messageSource.getMessage("station.dep.code", langCode));
        } else {
          entity.setDeptId(bo.getDeptId());
        }
      }

      //7 Ma dia ban/cap phuong xa
      if (CommonUtils.isNullOrEmpty(dto.getLocationCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.location", langCode));
      } else {
        if (!transmissionDao.checkExitsLocation(dto.getLocationCode())) {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.location", langCode));
        } else {
          ViewCatLocationBO bo = transmissionDao.findLocationByCode(dto.getLocationCode(), userId);
          if (bo == null) {
            ExcelDataUltils.addMessage(errData, "err.noscope", messageSource.getMessage("station.location", langCode));
          } else {
            entity.setLocationId(bo.getLocationId());
          }
        }
      }
      //Chu so huu
      if (!CommonUtils.isNullOrEmpty(dto.getOwnerCode())) {
        ViewCatItemBO ownerBo = transmissionDao.findCatItemByItemNameAndCaregoryCode(dto.getOwnerCode(), "CAT_OWNER");
        if (ownerBo != null) {
          entity.setOwnerId(ownerBo.getItemId());
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.ownerName", langCode));
        }
      } else {
        ExcelDataUltils.addMessage(errData, "err.choose", messageSource.getMessage("station.ownerName", langCode));
      }

      //dia chi'
      if (dto.getAddress().length() > 200) {
        ExcelDataUltils.addMessage(errData, "err.maxLength.200", messageSource.getMessage("station.address", langCode));
      } else {
        entity.setAddress(dto.getAddress());
      }

      //ngay xay dung
      if (!CommonUtils.isNullOrEmpty(dto.getConstructionDateStr())) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
          Date conDate = dateFormat.parse(dto.getConstructionDateStr());
          entity.setConstructionDate(conDate);
        } catch (Exception e) {
          ExcelDataUltils.addMessage(errData, "err.dateFormat", messageSource.getMessage("station.constructionDate", langCode));
        }
      }

      //trang thai
      Map<String, Long> listStatus = new WeakHashMap<>();
      listStatus.put(messageSource.getMessage("pool.status.one", langCode), 1L);
      listStatus.put(messageSource.getMessage("pool.status.zero", langCode), 2L);
//      listStatus.put(messageSource.getMessage("station.status.three", langCode), 3L);
//      listStatus.put(messageSource.getMessage("station.status.four", langCode), 4L);
//      listStatus.put(messageSource.getMessage("station.status.fine", langCode), 5L);
//      listStatus.put(messageSource.getMessage("station.status.six", langCode), 6L);

      if (CommonUtils.isNullOrEmpty(dto.getStatusName())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.status", langCode));
      } else {
        if (listStatus.containsKey(dto.getStatusName())) {
          entity.setStatus(listStatus.get(dto.getStatusName()));
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.status", langCode));
        }
      }

//      if (!CommonUtils.isNullOrEmpty(dto.getStatusName())) {
//        if (listStatus.containsKey(dto.getStatusName())) {
//          entity.setStatus(listStatus.get(dto.getStatusName()));
//        } else {
//          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.status", langCode));
//        }
//      }

      // kinh do
      if (CommonUtils.isNullOrEmpty(dto.getLongitudeStr())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.longitude", langCode));
      } else {
        try {
          String regex = "^[0-9-]+\\.[0-9]{5,}$";
          if (dto.getLongitudeStr().matches(regex)) {
            if (StringUtils.countMatches(dto.getLongitudeStr(), "-") > 1) {
              ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen", messageSource.getMessage("station.longitude", langCode));
            } else {
              Double longitude = Double.parseDouble(dto.getLongitudeStr());
              DecimalFormat df = new DecimalFormat("#.#####");
              df.setRoundingMode(RoundingMode.DOWN);
              if (longitude > 180 || longitude < -180) {
                ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo", messageSource.getMessage("station.longitude", langCode));
              } else {
                entity.setLongitude(Double.valueOf(df.format(longitude)));
              }
            }
          } else {
            ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo", messageSource.getMessage("station.longitude", langCode));
          }

        } catch (Exception e) {
          ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.longitude", langCode));
        }
      }

      //vi do
      if (CommonUtils.isNullOrEmpty(dto.getLatitudeStr())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.latitude", langCode));
      } else {
        try {
          String regex = "^[0-9-]+\\.[0-9]{5,}$";
          if (dto.getLatitudeStr().matches(regex)) {
            if (StringUtils.countMatches(dto.getLatitudeStr(), "-") > 1) {
              ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen", messageSource.getMessage("station.latitude", langCode));
            } else {
              Double latitude = Double.parseDouble(dto.getLatitudeStr());
              DecimalFormat df = new DecimalFormat("#.#####");
              df.setRoundingMode(RoundingMode.DOWN);
              if (latitude > 90 || latitude < -90) {
                ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo", messageSource.getMessage("station.latitude", langCode));
              } else {
                entity.setLatitude(Double.valueOf(df.format(latitude)));
              }
            }
          } else {
            ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo", messageSource.getMessage("station.latitude", langCode));
          }
        } catch (Exception e) {
          ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.latitude", langCode));
        }
      }


      // ghi chu
      if (dto.getNote().length() > 500) {
        ExcelDataUltils.addMessage(errData, "err.maxLength.500", messageSource.getMessage("station.note", langCode));
      } else {
        entity.setNote(dto.getNote());
      }

      if (entity.getLongitude() != null && entity.getLatitude() != null) {
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(entity.getLongitude(), entity.getLatitude()));
        entity.setGeometry(point);
      }


      if (errData.isEmpty()) {
        pillarDao.savePillar(entity);
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
    String savePath = ExcelDataUltils.writeResultExcel(file, new InfraPillarDTO(), mapObj, "KetQua_Temp_IMP_Cot_Insert", datas);
    return savePath + "+" + successRecord + "+" + errRecord;
  }

  @Override
  public String editPillar(MultipartFile file, String langCode, HttpServletRequest request) throws IOException, ClassNotFoundException {
    Long userId = CommonUtil.getUserId(request);
    List<InfraPillarDTO> datas = (List<InfraPillarDTO>) ExcelDataUltils.getListInExcel(file, new InfraPillarDTO());
    InfraPillarDTO flagList = ExcelDataUltils.getFlagList(file, new InfraPillarDTO());
    int successRecord = 0;
    int errRecord = 0;

    Map<Long, String> mapObj = new HashMap<>();
    long index = 0;
    for (InfraPillarDTO dto : datas) {
      Map<String, String> errData = new HashMap<>();
      PillarsBO entity = new PillarsBO();

      // ma cot
      if (CommonUtils.isNullOrEmpty(dto.getPillarCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("pillar.pillarCode", langCode));
        break;
      } else {
        dto.setPillarCode(dto.getPillarCode().toLowerCase());
        entity = pillarDao.findPillarByCode(dto.getPillarCode());
        if (entity == null) {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("pillar.pillarCode", langCode));
          break;
        } else {
          entity.setPillarTypeCode(dto.getPillarTypeCode());
        }
      }

      //ma don vi
      if (flagList.getDeptCode().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getDeptCode())) {
          ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.dep", langCode));
        } else {
          ViewCatDepartmentBO bo = transmissionDao.findDepartmentByCode(dto.getDeptCode(), userId);
          if (bo == null) {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.dep", langCode));
          } else {
            entity.setDeptId(bo.getDeptId());
          }
        }
      }
      // ma dia ban cap phuong xa
      if (flagList.getLocationCode().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getLocationCode())) {
          ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.location", langCode));
        } else {
          if (!transmissionDao.checkExitsLocation(dto.getLocationCode())) {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.location", langCode));
          } else {
            ViewCatLocationBO bo = transmissionDao.findLocationByCode(dto.getLocationCode(), userId);
            if (bo == null) {
              ExcelDataUltils.addMessage(errData, "err.noscope", messageSource.getMessage("station.location", langCode));
            } else {
              entity.setLocationId(bo.getLocationId());
            }
          }
        }
      }
      //chu so huu
      if (flagList.getOwnerCode().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getOwnerCode())) {
          ViewCatItemBO ownerBo = transmissionDao.findCatItemByItemNameAndCaregoryCode(dto.getOwnerCode(), "CAT_OWNER");
          if (ownerBo != null) {

            entity.setOwnerId(ownerBo.getItemId());

          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.ownerName", langCode));
          }
        } else {
          ExcelDataUltils.addMessage(errData, "err.choose", messageSource.getMessage("station.ownerName", langCode));
        }
      }
      //dia chi
      if (flagList.getAddress().equals("Y")) {
        if (dto.getAddress().length() > 200) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.200", messageSource.getMessage("station.address", langCode));
        } else {
          entity.setAddress(dto.getAddress());
        }
      }

      //Ngày xây dựng
      if (flagList.getConstructionDateStr().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getConstructionDateStr())) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
          dateFormat.setLenient(false);
          try {
            Date conDate = dateFormat.parse(dto.getConstructionDateStr());

            entity.setConstructionDate(conDate);

          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.dateFormat", messageSource.getMessage("station.constructionDate", langCode));
          }
        }
      }

      //trang thai
      Map<String, Long> listStatus = new WeakHashMap<>();
      listStatus.put(messageSource.getMessage("pool.status.one", langCode), 1L);
      listStatus.put(messageSource.getMessage("pool.status.zero", langCode), 2L);
//      listStatus.put(messageSource.getMessage("station.status.one", langCode), 1L);
//      listStatus.put(messageSource.getMessage("station.status.two", langCode), 2L);
//      listStatus.put(messageSource.getMessage("station.status.three", langCode), 3L);
//      listStatus.put(messageSource.getMessage("station.status.four", langCode), 4L);
//      listStatus.put(messageSource.getMessage("station.status.fine", langCode), 5L);
//      listStatus.put(messageSource.getMessage("station.status.six", langCode), 6L);

      if (!CommonUtils.isNullOrEmpty(dto.getStatusName())) {
        if (listStatus.containsKey(dto.getStatusName())) {
          entity.setStatus(listStatus.get(dto.getStatusName()));
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.status", langCode));
        }
      }

      //kinh do
      if (flagList.getLongitudeStr().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getLongitudeStr())) {
          ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.longitude", langCode));
        } else {
          try {
            String regex = "^[0-9-]+\\.[0-9][0-9][0-9][0-9][0-9]$";
            if (dto.getLongitudeStr().matches(regex)) {
              if (StringUtils.countMatches(dto.getLongitudeStr(), "-") > 1) {
                ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen", messageSource.getMessage("station.longitude", langCode));
              } else {
                Double longitude = Double.parseDouble(dto.getLongitudeStr());
                if (longitude > 180 || longitude < -180) {
                  ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo", messageSource.getMessage("station.longitude", langCode));
                } else {
                  entity.setLongitude(longitude);
                }
              }
            } else {
              ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo", messageSource.getMessage("station.longitude", langCode));
            }
          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.longitude", langCode));
          }
        }
      }

      //vi do
      if (flagList.getLatitudeStr().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getLatitudeStr())) {
          ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.latitude", langCode));
        } else {
          try {
            String regex = "^[0-9-]+\\.[0-9][0-9][0-9][0-9][0-9]$";
            if (dto.getLatitudeStr().matches(regex)) {
              if (StringUtils.countMatches(dto.getLatitudeStr(), "-") > 1) {
                ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen", messageSource.getMessage("station.latitude", langCode));
              } else {
                Double latitude = Double.parseDouble(dto.getLatitudeStr());
                if (latitude > 90 || latitude < -90) {
                  ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo", messageSource.getMessage("station.latitude", langCode));
                } else {
                  entity.setLatitude(latitude);
                }
              }
            } else {
              ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo", messageSource.getMessage("station.latitude", langCode));
            }
          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.latitude", langCode));
          }
        }
      }
      //ghi chu
      if (flagList.getNote().equals("Y")) {
        if (dto.getNote().length() > 500) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.500", messageSource.getMessage("station.note", langCode));
        } else {
          entity.setNote(dto.getNote());
        }
      }

      if (entity.getLongitude() != null && entity.getLatitude() != null) {
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(entity.getLongitude(), entity.getLatitude()));
        entity.setGeometry(point);
      }

      if (errData.isEmpty()) {
        pillarDao.savePillar(entity);
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
          temp = temp + value + "\n";
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

    String savePath = ExcelDataUltils.writeResultExcel(file, new InfraPillarDTO(), mapObj, "KetQua_Temp_IMP_Cot_Update");
    return savePath + "+" + successRecord + "+" + errRecord;
  }
  //------------------------KienNT---------------------

}
