package com.viettel.nims.transmission.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.viettel.nims.transmission.commom.*;
import com.viettel.nims.transmission.model.dto.PGInfraPoolsDto;
import com.viettel.nims.transmission.postgre.dao.PGInfraPoolsDao;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.servlet.http.HttpServletRequest;

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
import com.viettel.nims.transmission.dao.InfraCablesDao;
import com.viettel.nims.transmission.dao.InfraPoolDao;
import com.viettel.nims.transmission.dao.InfraSleeveDao;
import com.viettel.nims.transmission.dao.PillarDao;
import com.viettel.nims.transmission.dao.TransmissionDao;
import com.viettel.nims.transmission.model.CatDepartmentEntity;
import com.viettel.nims.transmission.model.CatLocationBO;
import com.viettel.nims.transmission.model.CatPoolTypeBO;
import com.viettel.nims.transmission.model.InfraCablesBO;
import com.viettel.nims.transmission.model.InfraPoolsBO;
import com.viettel.nims.transmission.model.InfraSleevesBO;
import com.viettel.nims.transmission.model.PillarsBO;
import com.viettel.nims.transmission.model.dto.InfraPoolDTO;
import com.viettel.nims.transmission.model.view.ViewCatDepartmentBO;
import com.viettel.nims.transmission.model.view.ViewCatItemBO;
import com.viettel.nims.transmission.model.view.ViewCatLocationBO;
import com.viettel.nims.transmission.model.view.ViewHangCableBO;
import com.viettel.nims.transmission.model.view.ViewInfraPoolsBO;
import com.viettel.nims.transmission.model.view.ViewInfraSleevesBO;
import com.viettel.nims.transmission.model.view.ViewTreeCatLocationBO;
import com.viettel.nims.transmission.utils.Constains;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * Created by ThieuNV on 08/23/2019.
 */
@Slf4j
@Service
@Transactional(transactionManager = "globalTransactionManager")
public class InfraPoolServiceImpl implements InfraPoolService {

  @Autowired
  InfraPoolDao infraPoolDao;

  @Autowired
  InfraSleeveDao infraSleeveDao;

  @Autowired
  InfraCablesDao infraCablesDao;

  @Autowired
  PGInfraPoolsDao pgInfraPoolsDao;

  @Autowired
  PillarDao pillarDao;

  @Autowired
  MessageResource messageSource;
  @Autowired
  TransmissionDao transmissionDao;
  static final Long Max_0 = new Long(0);
  static final Long Max_1 = new Long(1);
  static final Long Max_9999 = new Long(9999);
  private final String HANG = "hang";
  private final String CABLE = "cable";
  private final String SLEEVE = "sleeve";
  private final String POOLCODE = "poolCode";
  private final String OTHER = "other";
  private final String LISTDELETEPOOL = "listDeletePoolId";

  @Override
  public ResponseEntity<?> findBasicPool(InfraPoolsBO infraPoolsBO) {
    try {
      FormResult result = infraPoolDao.findBasicPool(infraPoolsBO);
//      Response<FormResult> response = new Response<>();
//      response.setContent(result);
//      return new ResponseEntity<>(response, HttpStatus.OK);

      return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  public ResponseEntity<?> findAdvancePool(List<InfraPoolsBO> infraPoolsBO, String langCode,
                                           HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    try {
      FormResult result = infraPoolDao.findAdvancePool(infraPoolsBO, userId);
      List<ViewInfraPoolsBO> resultList = (List<ViewInfraPoolsBO>) result.getListData();
//			for (ViewInfraPoolsBO v : resultList) {
//				v.setGeometry(null);
//			}
      result.setListData(resultList);
//      Response<FormResult> response = new Response<>();
//      response.setContent(result);
//      return new ResponseEntity<>(response, HttpStatus.OK);

      return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  public ResponseEntity<?> savePool(InfraPoolsBO infraPoolsBO) {
    if (infraPoolsBO.getPoolCode() == null) {
//      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
      return ResponseBase.createResponse(null, Constains.REQUIRED, 500);
    } else if (infraPoolsBO.getDeptId() == null) {
//      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
      return ResponseBase.createResponse(null, Constains.REQUIRED, 500);
    } else if (infraPoolsBO.getLocationId() == null) {
//      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
      return ResponseBase.createResponse(null, Constains.REQUIRED, 500);
    } else if (infraPoolsBO.getPoolTypeId() == null) {
//      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
      return ResponseBase.createResponse(null, Constains.REQUIRED, 500);
    } else if (infraPoolsBO.getLongitude() == null) {
//      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
      return ResponseBase.createResponse(null, Constains.REQUIRED, 500);
    } else if (infraPoolsBO.getLatitude() == null) {
//      return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
      return ResponseBase.createResponse(null, Constains.REQUIRED, 500);
    } else {
      try {

        PGInfraPoolsDto pgDto = new PGInfraPoolsDto();
        if (infraPoolsBO.getPoolId() != null) {
          pgDto.setUpdate(Boolean.TRUE);
        }
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(infraPoolsBO.getLongitude(), infraPoolsBO.getLatitude()));
        infraPoolsBO.setGeometry(point);
//				infraPoolsBO.setRowStatus(Constains.NUMBER_ONE);
        Long result = infraPoolDao.savePool(infraPoolsBO);
        pgDto.setPool_id(result);
        pgDto.setPool_code(infraPoolsBO.getPoolCode());
        pgDto.setDept_id(infraPoolsBO.getDeptId());
        pgDto.setLocation_id(infraPoolsBO.getLocationId());
        pgDto.setStatus(infraPoolsBO.getStatus());
        pgDto.setLongitude(infraPoolsBO.getLongitude());
        pgDto.setLatitude(infraPoolsBO.getLatitude());
        pgDto.setAddress(infraPoolsBO.getAddress());
        pgDto.setOwner_id(infraPoolsBO.getOwnerId());
        pgDto.setPool_type_id(infraPoolsBO.getPoolTypeId());
        pgInfraPoolsDao.insertOrUpdate(pgDto);
//        Response<FormResult> response = new Response<>();
//        response.setStatus(HttpStatus.OK.toString());
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return ResponseBase.createResponse(null, Constains.SUCCESS, 200);
      } catch (Exception e) {
        return ResponseBase.createResponse(null, "error", 500);
      }
    }
  }

  @Override
  public ResponseEntity<?> getNumberGenerate(Long path, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    if (path == null || "".equals(path))
      return ResponseBase.createResponse(null, "error", 304);

//    Response<Long> response = new Response<>();
//    response.setStatus(HttpStatus.OK.toString());
    Long Max = infraPoolDao.getMaxNumberPool(path, userId);
    if (Max == null || Max == 0)
      Max = Max_1;
    else if (Max >= Max_9999)
      Max = Max_0;
    else
      Max++;
//    response.setContent(Max);
//    return new ResponseEntity<>(response, HttpStatus.OK);

    return ResponseBase.createResponse(Max, Constains.SUCCESS, 200);
  }

  @Override
  public ResponseEntity<?> checkNumberAndGetZ(String dept_TTT, Long number_YYYY) {
//    Response<Object> response = new Response<>();
//    response.setStatus(HttpStatus.OK.toString());
//    response.setContent(infraPoolDao.checkNumberAndGetZ(dept_TTT, number_YYYY));
//    return new ResponseEntity<>(response, HttpStatus.OK);
    return ResponseBase.createResponse(infraPoolDao.checkNumberAndGetZ(dept_TTT, number_YYYY), Constains.SUCCESS, 200);
  }

  @Override
  public ResponseEntity<?> findById(Long id) {
    try {
      ViewInfraPoolsBO viewInfraPoolsBO = infraPoolDao.findPoolById(id);
//			viewInfraPoolsBO.setLatitude(viewInfraPoolsBO.getGeometry().getY());
//			viewInfraPoolsBO.setLongitude(viewInfraPoolsBO.getGeometry().getX());
//      Response<ViewInfraPoolsBO> response = new Response<>();
//      response.setStatus(Constains.UPDATE);
//      response.setContent(viewInfraPoolsBO);
//      return new ResponseEntity<>(response, HttpStatus.OK);
      return ResponseBase.createResponse(viewInfraPoolsBO, Constains.SUCCESS, 200);
    } catch (Exception e) {
      log.error("Exception", e);
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  public List<JSONObject> deleteOdfMultiple(List<InfraPoolsBO> listInfraPoolsBO) {
//		Constants.listDeletePool = new ArrayList<InfraPoolsBO>();
    boolean isErrorList = false;

    List<JSONObject> listResponse = new ArrayList<JSONObject>();

    for (InfraPoolsBO infraPoolsBO : listInfraPoolsBO) {
      JSONObject error = new JSONObject();

      List<String> listHangError = new ArrayList<String>();
      List<String> listCableError = new ArrayList<String>();
      List<String> listSleeveError = new ArrayList<String>();

      boolean other = false;

      // List Sleeves in Pool
      List<ViewInfraSleevesBO> sleeves = infraPoolDao.listSleevesInPool(infraPoolsBO.getPoolId());
      // List cables - By HolderId Pool
      List<InfraCablesBO> cables = infraCablesDao.findCableByHolderId(infraPoolsBO.getPoolId());
      // List Hangcables - via Pool
      List<ViewHangCableBO> hangCables = pillarDao.listPillarHangCable(infraPoolsBO.getPoolId());

      if (cables.size() == 0 && sleeves.size() == 0 && hangCables.size() == 0) {
        // TH 2.1 -khong la diem dau/cuoi cua doan cap nao VA ko co mang xong VA be
        // KHONG CO tuyen cap vat qua
        other = true;
      }

      if (sleeves.size() > 0) {

        boolean isWelding = false;
        for (ViewInfraSleevesBO v : sleeves) {
          List<InfraSleevesBO> listWedSleeves = infraSleeveDao.findWeldSleeveBySleeveId(v.getSleeveId());
          if (listWedSleeves.size() > 0) {
            // 2.5 SleeveCode dc han noi - luu thong tin mang xong da dc han noi
            listSleeveError.add(v.getSleeveCode());

            isWelding = true;
          }
        }
        if (isWelding) {
          other = true;
          isErrorList = true;
          // TH2.5 -co mang xong da han noi, ko dc xoa
        } else {
          if (cables.size() == 0 && hangCables.size() == 0) {
            other = true;
            // TH2.2 -khong la diem dau/cuoi cua doan cap nao VA be KHONG CO tuyen cap vat
            // qua VA tat ca cac mong xong deu chua dc han noi
          }
        }
      }

      if (cables.size() == 0 && sleeves.size() == 0 && hangCables.size() > 0) {
        other = true;
        for (ViewHangCableBO v : hangCables) {
          listHangError.add(v.getCableCode());
        }
        // TH 2.3 -khong la diem dau/cuoi cua doan cap nao VA ko co mang xong VA be CO
        // tuyen cap vat qua - can xac nhan de xoa?
      }

      if (cables.size() > 0) {
        other = true;
        isErrorList = true;
        // TH2.4 : la diem dau/cuoi cua doan cap bat ky/khong the xoa hien thi thong bao

        for (InfraCablesBO cable : cables) {
          listCableError.add(cable.getCableCode());
        }

      }
      if (!other) {
        isErrorList = true;
        // TH2.6 : Da co loi xay ra, he thong khong the xoa ban ghi
        // 1 . Be ko la diem dau cuoi, be co mang xong nhung chua dc han noi , be co
        // tuyen cap vat qua
        error.put(POOLCODE, infraPoolsBO.getPoolCode());
        error.put(OTHER, true);
      }

      if (listCableError.size() > 0 || listSleeveError.size() > 0 || listHangError.size() > 0) {
        error.put(POOLCODE, infraPoolsBO.getPoolCode()); // POOLCODE
        error.put(CABLE, listCableError); // 2.4
        error.put(SLEEVE, listSleeveError); // 2.5
        error.put(HANG, listHangError); // 2.3

        List<Long> listPoolId = new ArrayList<Long>();
        for (InfraPoolsBO i : listInfraPoolsBO) {
          listPoolId.add(i.getPoolId());
        }
        error.put(LISTDELETEPOOL, listPoolId);
        listResponse.add(error);
      } else if (error.get(OTHER) != null) {
        listResponse.add(error);
      }
    }
    if (!isErrorList) {
      if (!CollectionUtils.isEmpty(listResponse)) {
        return listResponse;
      }

      for (InfraPoolsBO infraPoolsBO : listInfraPoolsBO) {
        List<ViewInfraSleevesBO> sleeves = infraPoolDao.listSleevesInPool(infraPoolsBO.getPoolId());
        for (ViewInfraSleevesBO v : sleeves) {
          infraSleeveDao.delete(v.getSleeveId());
        }
        infraPoolDao.deletePool(infraPoolsBO.getPoolId());
        pgInfraPoolsDao.delete(infraPoolsBO.getPoolId());
      }
      return new ArrayList<JSONObject>();
    } else {
      // khong the xoa danh sach da chon vi co it nhat 1 ban ghi khong the xoa !!
      return listResponse;
    }
  }

  @Override
  public ResponseEntity<?> findByPoolCode(String poolCode) {
    try {

      ViewInfraPoolsBO viewInfraPoolsBO = infraPoolDao.findPoolByPoolCode(poolCode);
//			if (viewInfraPoolsBO != null && viewInfraPoolsBO.getGeometry() != null) {
//				viewInfraPoolsBO.setLatitude(viewInfraPoolsBO.getGeometry().getY());
//				viewInfraPoolsBO.setLongitude(viewInfraPoolsBO.getGeometry().getX());
//			}
//      Response<ViewInfraPoolsBO> response = new Response<>();
//      response.setStatus(Constains.UPDATE);
//      response.setContent(viewInfraPoolsBO);
//      return new ResponseEntity<>(response, HttpStatus.OK);
      return ResponseBase.createResponse(viewInfraPoolsBO, Constains.SUCCESS, 200);
    } catch (Exception e) {
      log.error("Exception", e);
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  public String exportExcelChoose(List<ViewInfraPoolsBO> data, String langCode) {
    if (CollectionUtils.isEmpty(data)) {
      return null;
    }

    Workbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("pool.sheet", langCode));

    // Set title
    Row firstRow = sheet.createRow(0);
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
    Cell titleCell = firstRow.createCell(0);
    titleCell.setCellValue(messageSource.getMessage("pool.title", langCode));
    titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

    Row secondRow = sheet.createRow(2);
    sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 13));
    Cell titleCell2 = secondRow.createCell(0);
    titleCell2.setCellValue(messageSource.getMessage("station.report.date", langCode)
        + CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
    titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

    // Set header
    List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));//
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.poolCode", langCode), 20 * 256));// 1
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.poolTypeCode", langCode), 20 * 256));// 2
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.deptName", langCode), 20 * 256));// 3
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.ownerName", langCode), 20 * 256));// 4
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.constructionDate", langCode), 20 * 256));// 5
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.status", langCode), 20 * 256));// 6
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.deliveryDate", langCode), 20 * 256));// 7
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.acceptanceDate", langCode), 20 * 256));// 8
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.locationName", langCode), 20 * 256));// 9
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.address", langCode), 20 * 256));// 10
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.latitude", langCode), 20 * 256));// 11
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.longitude", langCode), 20 * 256));// 12
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.note", langCode), 20 * 256));// 13
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
    path = path + "EXPORT_BE" + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm")
        + ".xlsx";

    DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);

    // Set data
    for (ViewInfraPoolsBO item : data) {

      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell1 = row.createCell(1);
      cell1.setCellValue(item.getPoolCode() != null ? item.getPoolCode() : "");
      cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell2 = row.createCell(2);
      cell2.setCellValue(item.getPoolTypeCode() != null ? item.getPoolTypeCode() : "");
      cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell3 = row.createCell(3);
      cell3.setCellValue(item.getDeptId() != null ? item.getDeptId() : null);
      cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell5 = row.createCell(4);
      cell5.setCellValue(item.getOwnerName() != null ? item.getOwnerName() : "");
      cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell7 = row.createCell(5);
      if (item.getConstructionDate() != null) {
        cell7.setCellValue(df.format(item.getConstructionDate()));
      } else {
        cell7.setCellValue("");
      }
      cell7.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));//

      Cell cell12 = row.createCell(6);
      cell12.setCellValue(item.getStatus() != null ? item.getStatus().toString() : "");
      cell12.setCellValue(item.getStatus() == 0 ? messageSource.getMessage("pool.status.zero", langCode)
          : item.getStatus() == 1 ? messageSource.getMessage("pool.status.one", langCode) : "");
      cell12.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));//

      Cell cell8 = row.createCell(7);
      if (item.getDeliveryDate() != null) {
        cell8.setCellValue(df.format(item.getDeliveryDate()));
      } else {
        cell8.setCellValue("");
      }
      cell8.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));//

      Cell cell9 = row.createCell(8);
      if (item.getAcceptanceDate() != null) {
        cell9.setCellValue(df.format(item.getAcceptanceDate()));
      } else {
        cell9.setCellValue("");
      }
      cell9.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));//

      Cell cell4 = row.createCell(9);
      cell4.setCellValue(item.getLocationId() != null ? item.getLocationId() : null);
      cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));//

      Cell cell6 = row.createCell(10);
      cell6.setCellValue(item.getAddress() != null ? item.getAddress() : "");
      cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));//

      Cell cell11 = row.createCell(11);
      cell11.setCellValue(
          item.getLatitude() != null ? String.format("%.5f", item.getLatitude()) : String.format("%.5f", 0));
      cell11.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell10 = row.createCell(12);
      cell10.setCellValue(item.getLongitude() != null ? String.format("%.5f", item.getLongitude())
          : String.format("%.5f", 0));
      cell10.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell13 = row.createCell(13);
      cell13.setCellValue(item.getNote() != null ? item.getNote() : "");
      cell13.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));
    }
    try {
      // Write file
      FileOutputStream outputStream = new FileOutputStream(path);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      log.error("Exception", e);
      // logger.error(e.getMessage());
    }

    return path;

  }

  @Override
  public String exportExcel(List<InfraPoolsBO> dataInfraPoolsBO, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    for (int i = 0; i < dataInfraPoolsBO.size(); i++) {
      dataInfraPoolsBO.get(i).setFirst(null);
      dataInfraPoolsBO.get(i).setRows(null);
    }

    FormResult result = infraPoolDao.findAdvancePool(dataInfraPoolsBO, userId);
    List<ViewInfraPoolsBO> data = (List<ViewInfraPoolsBO>) result.getListData();
//		for (ViewInfraPoolsBO v : data) {
//			v.setGeometry(null);
//		}
    if (CollectionUtils.isEmpty(data)) {
      return null;
    }

    Workbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("pool.sheet", langCode));

    // Set title
    Row firstRow = sheet.createRow(0);
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
    Cell titleCell = firstRow.createCell(0);
    titleCell.setCellValue(messageSource.getMessage("pool.title", langCode));
    titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

    Row secondRow = sheet.createRow(2);
    sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 13));
    Cell titleCell2 = secondRow.createCell(0);
    titleCell2.setCellValue(messageSource.getMessage("station.report.date", langCode)
        + CommonUtils.getStrDate(System.currentTimeMillis(), "hh:mm dd/MM/yyyy"));
    titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

    // Set header
    List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));//
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.poolCode", langCode), 20 * 256));// 1
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.poolTypeCode", langCode), 20 * 256));// 2
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.deptName", langCode), 20 * 256));// 3
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.ownerName", langCode), 20 * 256));// 4
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.constructionDate", langCode), 20 * 256));// 5
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.status", langCode), 20 * 256));// 6
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.deliveryDate", langCode), 20 * 256));// 7
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.acceptanceDate", langCode), 20 * 256));// 8
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.locationName", langCode), 20 * 256));// 9
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.address", langCode), 20 * 256));// 10
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.latitude", langCode), 20 * 256));// 11
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.longitude", langCode), 20 * 256));// 12
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("pool.note", langCode), 20 * 256));// 13
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
    path = path + "EXPORT_BE" + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm")
        + ".xlsx";

    DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);

    // Set data
    for (ViewInfraPoolsBO item : data) {

      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell1 = row.createCell(1);
      cell1.setCellValue(item.getPoolCode() != null ? item.getPoolCode() : "");
      cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell2 = row.createCell(2);
      cell2.setCellValue(item.getPoolTypeCode() != null ? item.getPoolTypeCode() : "");
      cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell3 = row.createCell(3);
      cell3.setCellValue(item.getDeptId() != null ? item.getDeptId() : null);
      cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell5 = row.createCell(4);
      cell5.setCellValue(item.getOwnerName() != null ? item.getOwnerName() : "");
      cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell7 = row.createCell(5);
      if (item.getConstructionDate() != null) {
        cell7.setCellValue(df.format(item.getConstructionDate()));
      } else {
        cell7.setCellValue("");
      }
      cell7.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));//

      Cell cell12 = row.createCell(6);
      cell12.setCellValue(item.getStatus() != null ? item.getStatus().toString() : "");
      cell12.setCellValue(item.getStatus() == 0 ? messageSource.getMessage("pool.status.zero", langCode)
          : item.getStatus() == 1 ? messageSource.getMessage("pool.status.one", langCode) : "");
      cell12.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));//

      Cell cell8 = row.createCell(7);
      if (item.getDeliveryDate() != null) {
        cell8.setCellValue(df.format(item.getDeliveryDate()));
      } else {
        cell8.setCellValue("");
      }
      cell8.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));//

      Cell cell9 = row.createCell(8);
      if (item.getAcceptanceDate() != null) {
        cell9.setCellValue(df.format(item.getAcceptanceDate()));
      } else {
        cell9.setCellValue("");
      }
      cell9.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));//

      Cell cell4 = row.createCell(9);
      cell4.setCellValue(item.getLocationId() != null ? item.getLocationId() : null);
      cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));//

      Cell cell6 = row.createCell(10);
      cell6.setCellValue(item.getAddress() != null ? item.getAddress() : "");
      cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));//

      Cell cell11 = row.createCell(11);
      cell11.setCellValue(
          item.getLatitude() != null ? String.format("%.5f", item.getLatitude()) : String.format("%.5f", 0));
      cell11.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell10 = row.createCell(12);
      cell10.setCellValue(item.getLongitude() != null ? String.format("%.5f", item.getLongitude())
          : String.format("%.5f", 0));
      cell10.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell13 = row.createCell(13);
      cell13.setCellValue(item.getNote() != null ? item.getNote() : "");
      cell13.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));
    }
    try {
      // Write file
      FileOutputStream outputStream = new FileOutputStream(path);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      log.error("Exception", e);
      // logger.error(e.getMessage());
    }

    return path;

  }

  @Override
  public ResponseEntity<?> getLaneCodeHang(PillarsBO entity, String langCode,HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    try {
      FormResult result = pillarDao.getListLaneCodeHangPillar(entity,userId);
//      Response<FormResult> response = new Response<>();
//      response.setContent(result);
//      return new ResponseEntity<>(response, HttpStatus.OK);
      return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  public List<JSONObject> deleteHangConfirm(List<InfraPoolsBO> listInfraPoolsBO) {
    for (InfraPoolsBO infraPoolsBO : listInfraPoolsBO) {
      infraPoolDao.deletePool(infraPoolsBO.getPoolId());
      List<ViewInfraSleevesBO> sleeves = infraPoolDao.listSleevesInPool(infraPoolsBO.getPoolId());
      for (ViewInfraSleevesBO v : sleeves) {
        infraSleeveDao.delete(v.getSleeveId());
      }
    }
//		Constants.listDeletePool = new ArrayList<InfraPoolsBO>();
    return new ArrayList<JSONObject>();
  }

  @Override
  public String downloadTeamplate(List<ViewInfraPoolsBO> viewInfraPoolsBOS, Integer type, String langCode,
                                  HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    String savePath = messageSource.getMessage("report.out", langCode);
    File dir = new File(savePath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    if (type == 1) {
      savePath = savePath + messageSource.getMessage("pool.template.file.import.add.name", langCode)
          + CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
    } else {
      savePath = savePath + messageSource.getMessage("pool.template.file.import.edit.name", langCode)
          + CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
    }
    InputStream excelFile;
    XSSFWorkbook workbook = null;
    try {
      excelFile = new ClassPathResource(Constants.TEMPLATE_FILE.TEAMPLATE_POOL).getInputStream();
//      excelFile = new ClassPathResource("/templates/teamplate_import_nha_tram.xlsx").getInputStream();
      workbook = new XSSFWorkbook(excelFile);
    } catch (Exception e) {
      log.error("Exception", e);
    }
    XSSFSheet sheetData = workbook.getSheetAt(0);
    int rowNum = 4;
    int index = 1;
    for (ViewInfraPoolsBO bo : viewInfraPoolsBOS) {
      Row row = sheetData.createRow(rowNum++);
      int i = 0;
      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getDeptCode() == null ? "" : bo.getDeptCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell2 = row.createCell(i++);
      String poolIndex = bo.getPoolCode().substring(4, 8);
      cell2.setCellValue(bo.getPoolCode() == null ? "" : poolIndex);
      cell2.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

      Cell cell3 = row.createCell(i++);
      cell3.setCellValue(bo.getPoolCode() == null ? "" : bo.getPoolCode());
      cell3.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      String lastC = bo.getPoolCode().substring(bo.getPoolCode().length() - 1, bo.getPoolCode().length());
      Cell cell5 = row.createCell(i++);
      cell5.setCellValue(lastC.equals("A") ? messageSource.getMessage("common.no", langCode)
          : messageSource.getMessage("common.yes", langCode));
      cell5.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell6 = row.createCell(i++);
      cell6.setCellValue(bo.getPoolTypeCode() == null ? "" : bo.getPoolTypeCode());
      cell6.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell7 = row.createCell(i++);
      ViewTreeCatLocationBO viewCatLocationBO = transmissionDao.findCatLocationById(bo.getLocationId(), userId);
      cell7.setCellValue(viewCatLocationBO == null ? "" : viewCatLocationBO.getLocationCode());
      cell7.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell8 = row.createCell(i++);
      cell8.setCellValue(bo.getAddress() == null ? "" : bo.getAddress());
      cell8.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
      Cell cell9 = row.createCell(i++);
      cell9.setCellValue(
          bo.getConstructionDate() == null ? "" : simpleDateFormat.format(bo.getConstructionDate()));
      cell9.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell10 = row.createCell(i++);
      cell10.setCellValue(bo.getDeliveryDate() == null ? "" : simpleDateFormat.format(bo.getDeliveryDate()));
      cell10.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell11 = row.createCell(i++);
      cell11.setCellValue(bo.getAcceptanceDate() == null ? "" : simpleDateFormat.format(bo.getAcceptanceDate()));
      cell11.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell12 = row.createCell(i++);
      ViewCatItemBO catOwner = transmissionDao.findCatItemByCategoryCodeAndId(bo.getOwnerId(), "CAT_OWNER");
      cell12.setCellValue(catOwner == null ? "" : catOwner.getItemCode());
      cell12.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      // longitude
      Cell cell13 = row.createCell(i++);
      cell13.setCellValue(bo.getLongitude() == null ? "" : bo.getLongitude().toString());
      cell13.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

//      latitude
      Cell cell14 = row.createCell(i++);
      cell14.setCellValue(bo.getLatitude() == null ? "" : bo.getLatitude().toString());
      cell14.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

      Cell cell15 = row.createCell(i++);
      cell15.setCellValue(bo.getStatusName() == null ? "" : bo.getStatusName());
      cell15.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

      Cell cell16 = row.createCell(i++);
      cell16.setCellValue(bo.getNote() == null ? "" : bo.getNote());
      cell16.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
    }

    // department
    CatDepartmentEntity departmentEntity = new CatDepartmentEntity();
    FormResult formResult = transmissionDao.findDepartment(departmentEntity, userId);
    XSSFSheet sheetDept = workbook.getSheetAt(1);
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
//  location
    CatLocationBO locationEntity = new CatLocationBO();
    FormResult formResultLocation = transmissionDao.findCatLocation(locationEntity, userId);
    XSSFSheet sheetLocation = workbook.getSheetAt(2);
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

    List<ViewCatItemBO> listOwner = transmissionDao.findCatItemByCategoryId("CAT_OWNER");
    XSSFSheet sheetOwner = workbook.getSheetAt(3);

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

    List<CatPoolTypeBO> poolTypeBOList = transmissionDao.getPoolTypeList();
    XSSFSheet sheetPoolType = workbook.getSheetAt(4);
    index = 1;
    rowNum = 2;
    for (CatPoolTypeBO bo : poolTypeBOList) {
      int i = 0;
      Row row = sheetPoolType.createRow(rowNum++);
      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getPoolTypeCode() == null ? "" : bo.getPoolTypeCode());
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
  public String importPool(MultipartFile file, String langCode, HttpServletRequest request) {

    Long userId = CommonUtil.getUserId(request);
    List<InfraPoolDTO> datas;
    try {
      datas = (List<InfraPoolDTO>) ExcelDataUltils.getListInExcel(file, new InfraPoolDTO());
      //      check header is changed
      InfraPoolDTO header = ExcelDataUltils.getHeaderList(file, new InfraPoolDTO());
      if (!messageSource.checkEqualHeader(header, langCode)) {
        return "template-error";
      }
    } catch (Exception e) {
      return "template-error";
    }
    int successRecord = 0;
    int errRecord = 0;
    Map<Long, String> mapObj = new HashMap<>();
//    get index for err mapObj
    long index = 0;
    for (InfraPoolDTO dto : datas) {
      Map<String, String> errData = new HashMap<>();
      InfraPoolsBO entity = new InfraPoolsBO();
      String providerCode = null;
      if (CommonUtils.isNullOrEmpty(dto.getDeptCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty",
            messageSource.getMessage("station.dep.code", langCode));
      } else {
        if (!transmissionDao.checkExitsDept(dto.getDeptCode())) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("station.dep", langCode));
        } else {
          ViewCatDepartmentBO bo = transmissionDao.findDepartmentByCode(dto.getDeptCode(), userId);
          if (bo == null) {
            ExcelDataUltils.addMessage(errData, "err.noscope",
                messageSource.getMessage("station.dep.code", langCode));
          } else {
            try {
              if (bo.getPathName().split("/").length >= 3) {
                entity.setDeptId(bo.getDeptId());
                providerCode = bo.getPathcode().split("/")[2];
              } else {
                ExcelDataUltils.addMessage(errData,
                    messageSource.getMessage("pool.err.province.require", langCode), null);
              }
            } catch (Exception e1) {
              ExcelDataUltils.addMessage(errData,
                  messageSource.getMessage("pool.err.province.require", langCode), null);
            }
          }
        }
      }
//    generate pool index
      String poolIndex = null;
      if (CommonUtils.isNullOrEmpty(dto.getPoolIndex())) {
        if (entity.getDeptId() != null) {
          Long Max = infraPoolDao.getMaxNumberPool(entity.getDeptId(), userId);
          if (Max == null || Max == 0) {
            poolIndex = String.format("%04d", Max_1);
          } else if (Max >= Max_9999) {
            ExcelDataUltils.addMessage(errData, "pool.err.not.generate.poolIndex", dto.getDeptCode());
          } else {
            poolIndex = String.format("%04d", ++Max);
          }
        }
      } else {
        try {
          Long poolIdx = Long.parseLong(dto.getPoolIndex());
          if (poolIdx < 0L || poolIdx > 9999L) {
            ExcelDataUltils.addMessage(errData,
                messageSource.getMessage("pool.err.poolIndex.numberMax", langCode), null);

          } else {
            poolIndex = String.format("%-4s", dto.getPoolIndex()).replace(' ', '0');
          }
        } catch (Exception e) {
          ExcelDataUltils.addMessage(errData,
              messageSource.getMessage("pool.err.poolIndex.numberFormat", langCode), null);
        }
      }
      if (CommonUtils.isNullOrEmpty(dto.getIsComplementaryPool())) {
        ExcelDataUltils.addMessage(errData, "err.choose",
            messageSource.getMessage("pool.IsComplementaryPool", langCode));
      }
//    generate poolCode with not empty poolIndex and providerCode
      if (!CommonUtils.isNullOrEmpty(poolIndex) && !CommonUtils.isNullOrEmpty(providerCode)) {
        if (dto.getIsComplementaryPool().equals(messageSource.getMessage("common.yes", langCode))) {
          try {
            String Zchar = infraPoolDao.checkNumberAndGetZ(providerCode, Long.parseLong(poolIndex));
            if (Zchar == null) {
              entity.setPoolCode(providerCode + "P" + poolIndex + "A");
              dto.setPoolCode(entity.getPoolCode());
              dto.setPoolIndex(poolIndex);
            } else if (Zchar.equals("Z")) {
              ExcelDataUltils.addMessage(errData,
                  messageSource.getMessage("pool.err.generate.poolCode", langCode), null);
            } else {
              int charCode = Character.codePointAt(Zchar, 0);
              charCode++;
              char c = (char) charCode;
              entity.setPoolCode(providerCode + "P" + poolIndex + c);
              dto.setPoolCode(entity.getPoolCode());
              dto.setPoolIndex(poolIndex);
            }
          } catch (Exception e) {

          }
        } else if (dto.getIsComplementaryPool().equals(messageSource.getMessage("common.no", langCode))) {
          String poolCode = providerCode + "P" + poolIndex + "A";
          ViewInfraPoolsBO viewInfraPoolsBO = infraPoolDao.findPoolByPoolCode(poolCode);
          if (viewInfraPoolsBO != null) {
            ExcelDataUltils.addMessage(errData, "pool.err.not.generate.poolIndex", poolCode);
          } else {
            entity.setPoolCode(poolCode);
            dto.setPoolCode(entity.getPoolCode());
            dto.setPoolIndex(poolIndex);
          }
        }
      }

      if (CommonUtils.isNullOrEmpty(dto.getPoolTypeCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty",
            messageSource.getMessage("pool.poolTypeCode", langCode));
      } else {
        CatPoolTypeBO catPoolTypeBO = transmissionDao.getPoolTypeByCode(dto.getPoolTypeCode());
        if (catPoolTypeBO == null) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("pool.poolTypeCode", langCode));
        } else {
          entity.setPoolTypeId(catPoolTypeBO.getPoolTypeId());
        }
      }

      if (CommonUtils.isNullOrEmpty(dto.getLocationCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty",
            messageSource.getMessage("station.location", langCode));
      } else {
        if (!transmissionDao.checkExitsLocation(dto.getLocationCode())) {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("station.location", langCode));
        } else {
          ViewCatLocationBO bo = transmissionDao.findLocationByCode(dto.getLocationCode(), userId);
          if (bo == null) {
            ExcelDataUltils.addMessage(errData, "err.noscope",
                messageSource.getMessage("station.location", langCode));
          } else {
            entity.setLocationId(bo.getLocationId());
          }
        }
      }

      if (dto.getAddress().length() > 500) {
        ExcelDataUltils.addMessage(errData, "err.maxLength.500",
            messageSource.getMessage("station.address", langCode));
      } else {
        entity.setAddress(dto.getAddress());
      }

//      ngay xay dung
      if (!CommonUtils.isNullOrEmpty(dto.getConstructionDate())) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
        dateFormat.setLenient(false);
        try {
          Date conDate = dateFormat.parse(dto.getConstructionDate());
          entity.setConstructionDate(conDate);
        } catch (Exception e) {
          ExcelDataUltils.addMessage(errData, "err.dateFormat",
              messageSource.getMessage("station.constructionDate", langCode));
        }
      }

//      ngay ban giao
      if (!CommonUtils.isNullOrEmpty(dto.getDeliverDate())) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
        dateFormat.setLenient(false);
        try {
          Date conDate = dateFormat.parse(dto.getDeliverDate());
          entity.setDeliveryDate(conDate);
        } catch (Exception e) {
          ExcelDataUltils.addMessage(errData, "err.dateFormat",
              messageSource.getMessage("pool.deliveryDate", langCode));
        }
      }
//      ngay nghiem thu
      if (!CommonUtils.isNullOrEmpty(dto.getAcceptanceDate())) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
        dateFormat.setLenient(false);
        try {
          Date conDate = dateFormat.parse(dto.getAcceptanceDate());
          entity.setAcceptanceDate(conDate);
        } catch (Exception e) {
          ExcelDataUltils.addMessage(errData, "err.dateFormat",
              messageSource.getMessage("pool.acceptanceDate", langCode));
        }
      }
      if (!CommonUtils.isNullOrEmpty(dto.getOwnerCode())) {
        ViewCatItemBO ownerBo = transmissionDao.findCatItemByItemCodeAndCaregoryCode(dto.getOwnerCode(),
            "CAT_OWNER");
        if (ownerBo != null) {
          entity.setOwnerId(ownerBo.getItemId());
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("station.ownerName", langCode));
        }
      }
//      else {
//        ExcelDataUltils.addMessage(errData, "err.choose",
//            messageSource.getMessage("station.ownerName", langCode));
//      }

      if (CommonUtils.isNullOrEmpty(dto.getLongtitude())) {
        ExcelDataUltils.addMessage(errData, "err.empty",
            messageSource.getMessage("station.longitude", langCode));
      } else {
//				try {
//					String regex = "^[0-9-]+\\.[0-9][0-9][0-9][0-9][0-9]$";
//					if (dto.getLongtitude().matches(regex)) {
//						if (StringUtils.countMatches(dto.getLongtitude(), "-") > 1) {
//							ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen",
//									messageSource.getMessage("station.longitude", langCode));
//						} else {
//							Double longitude = Double.parseDouble(dto.getLongtitude());
//							if (longitude > 180 || longitude < -180) {
//								ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
//										messageSource.getMessage("station.longitude", langCode));
//							} else {
//								entity.setLongitude(longitude);
//							}
//						}
//					} else {
//						ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
//								messageSource.getMessage("station.longitude", langCode));
//					}
//
//				} catch (Exception e) {
//					ExcelDataUltils.addMessage(errData, "err.numberFormat",
//							messageSource.getMessage("station.longitude", langCode));
//				}

        int kq = checkLongLat(dto.getLongtitude(), 180);
        if (kq == Constains.NUMBER_ONE) {
          ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen",
              messageSource.getMessage("station.longitude", langCode));
        } else if (kq == Constains.NUMBER_TWO) {
          ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
              messageSource.getMessage("station.longitude", langCode));
        } else if (kq == Constains.NUMBER_THREE) {
          entity.setLongitude(Double.parseDouble(
              dto.getLongtitude().substring(0, dto.getLongtitude().indexOf(".") + Constains.NUMBER_SIX)));
        } else if (kq == Constains.NUMBER_FOUR) {
          ExcelDataUltils.addMessage(errData, "err.missing.5.outOfGeo",
              messageSource.getMessage("station.longitude", langCode));
        }
      }

      if (CommonUtils.isNullOrEmpty(dto.getLatitude())) {
        ExcelDataUltils.addMessage(errData, "err.empty",
            messageSource.getMessage("station.latitude", langCode));
      } else {
//				try {
//					String regex = "^[0-9-]+\\.[0-9][0-9][0-9][0-9][0-9]$";
//					if (dto.getLatitude().matches(regex)) {
//						if (StringUtils.countMatches(dto.getLatitude(), "-") > 1) {
//							ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen",
//									messageSource.getMessage("station.latitude", langCode));
//						} else {
//							Double latitude = Double.parseDouble(dto.getLatitude());
//							if (latitude > 90 || latitude < -90) {
//								ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
//										messageSource.getMessage("station.latitude", langCode));
//							} else {
//								entity.setLatitude(latitude);
//							}
//						}
//					} else {
//						ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
//								messageSource.getMessage("station.latitude", langCode));
//					}
//				} catch (Exception e) {
//					ExcelDataUltils.addMessage(errData, "err.numberFormat",
//							messageSource.getMessage("station.latitude", langCode));
//				}

        int kq = checkLongLat(dto.getLatitude(), 90);
        messageSource.getMessage("station.latitude", langCode);
        if (kq == Constains.NUMBER_ONE) {
          ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen",
              messageSource.getMessage("station.latitude", langCode));
        } else if (kq == Constains.NUMBER_TWO) {
          ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
              messageSource.getMessage("station.latitude", langCode));
        } else if (kq == Constains.NUMBER_THREE) {
          entity.setLatitude(Double.parseDouble(
              dto.getLatitude().substring(0, dto.getLatitude().indexOf(".") + Constains.NUMBER_SIX)));
        } else if (kq == Constains.NUMBER_FOUR) {
          ExcelDataUltils.addMessage(errData, "err.missing.5.outOfGeo",
              messageSource.getMessage("station.latitude", langCode));
        }
      }
      Map<String, Integer> listStatus = new WeakHashMap<>();
      listStatus.put(messageSource.getMessage("cable.statusName.zero", langCode), 0);
      listStatus.put(messageSource.getMessage("cable.statusName.one", langCode), 1);
      if (!CommonUtils.isNullOrEmpty(dto.getStatus())) {
        if (listStatus.containsKey(dto.getStatus())) {
          entity.setStatus(listStatus.get(dto.getStatus()));
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect",
              messageSource.getMessage("station.status", langCode));
        }
      } else {
        ExcelDataUltils.addMessage(errData, "err.choose", messageSource.getMessage("station.status", langCode));
      }

      if (dto.getNote().length() > 500) {
        ExcelDataUltils.addMessage(errData, "err.maxLength.500",
            messageSource.getMessage("station.note", langCode));
      } else {
        entity.setNote(dto.getNote());
      }

      if (entity.getLongitude() != null && entity.getLatitude() != null) {
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(entity.getLongitude(), entity.getLatitude()));
        entity.setGeometry(point);
      }
      if (errData.isEmpty()) {
        infraPoolDao.savePool(entity);
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

    String savePath = ExcelDataUltils.writeResultExcel(file, new InfraPoolDTO(), mapObj,
        messageSource.getMessage("pool.result.import.fileName", langCode), datas);
    return savePath + "+" + successRecord + "+" + errRecord;

  }

  @Override
  public String importEditPool(MultipartFile file, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    List<InfraPoolDTO> datas;
    InfraPoolDTO flagList;
    try {
      datas = (List<InfraPoolDTO>) ExcelDataUltils.getListInExcel(file, new InfraPoolDTO());
      flagList = ExcelDataUltils.getFlagList(file, new InfraPoolDTO());
      //      check header is changed
      InfraPoolDTO header = ExcelDataUltils.getHeaderList(file, new InfraPoolDTO());
      if (!messageSource.checkEqualHeader(header, langCode)) {
        return "template-error";
      }
    } catch (Exception e) {
      return "template-error";
    }
    int successRecord = 0;
    int errRecord = 0;
    Map<Long, String> mapObj = new HashMap<>();
//    get index for err mapObj
    long index = 0;
    for (InfraPoolDTO dto : datas) {
      Map<String, String> errData = new HashMap<>();
      InfraPoolsBO entity = new InfraPoolsBO();
      if (CommonUtils.isNullOrEmpty(dto.getPoolCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.note", langCode));
      } else {
        entity = infraPoolDao.findPoolBOByPoolCode(dto.getPoolCode());
        if (entity == null) {
          ExcelDataUltils.addMessage(errData, "err.empty",
              messageSource.getMessage("station.note", langCode));
          entity = new InfraPoolsBO();
        } else {
          if (!infraPoolDao.checkPoolPermission(entity, userId)) {
            ExcelDataUltils.addMessage(errData, "pool.err.permission",
                messageSource.getMessage("station.note", langCode));
          }
        }
      }
      if (flagList.getLocationCode().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getLocationCode())) {
          ExcelDataUltils.addMessage(errData, "err.empty",
              messageSource.getMessage("station.location", langCode));
        } else {
          if (!transmissionDao.checkExitsLocation(dto.getLocationCode())) {
            ExcelDataUltils.addMessage(errData, "err.incorrect",
                messageSource.getMessage("station.location", langCode));
          } else {
            ViewCatLocationBO bo = transmissionDao.findLocationByCode(dto.getLocationCode(), userId);
            if (bo == null) {
              ExcelDataUltils.addMessage(errData, "err.noscope",
                  messageSource.getMessage("station.location", langCode));
            } else {
              entity.setLocationId(bo.getLocationId());
            }
          }
        }
      }
      if (flagList.getAddress().equals("Y")) {
        if (dto.getAddress().length() > 500) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.500",
              messageSource.getMessage("station.address", langCode));
        } else {
          entity.setAddress(dto.getAddress());
        }
      }

      if (flagList.getConstructionDate().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getConstructionDate())) {
          SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
          dateFormat.setLenient(false);
          try {
            Date conDate = dateFormat.parse(dto.getConstructionDate());
            entity.setConstructionDate(conDate);
          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.dateFormat",
                messageSource.getMessage("station.constructionDate", langCode));
          }
        }
      }

      if (flagList.getDeliverDate().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getDeliverDate())) {
          SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
          dateFormat.setLenient(false);
          try {
            Date conDate = dateFormat.parse(dto.getDeliverDate());
            entity.setDeliveryDate(conDate);
          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.dateFormat",
                messageSource.getMessage("pool.deliveryDate", langCode));
          }
        }
      }
      if (flagList.getAcceptanceDate().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getAcceptanceDate())) {
          SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
          dateFormat.setLenient(false);
          try {
            Date conDate = dateFormat.parse(dto.getAcceptanceDate());
            entity.setAcceptanceDate(conDate);
          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.dateFormat",
                messageSource.getMessage("pool.acceptanceDate", langCode));
          }
        }
      }
      if (flagList.getOwnerCode().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getOwnerCode())) {
          ViewCatItemBO ownerBo = transmissionDao.findCatItemByItemCodeAndCaregoryCode(dto.getOwnerCode(),
              "CAT_OWNER");
          if (ownerBo != null) {
            entity.setOwnerId(ownerBo.getItemId());
          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect",
                messageSource.getMessage("station.ownerName", langCode));
          }
        }
      }
      if (flagList.getLongtitude().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getLongtitude())) {
          ExcelDataUltils.addMessage(errData, "err.empty",
              messageSource.getMessage("station.longitude", langCode));
        } else {
//					try {
//						String regex = "^[0-9-]+\\.[0-9][0-9][0-9][0-9][0-9]$";
//						if (dto.getLongtitude().matches(regex)) {
//							if (StringUtils.countMatches(dto.getLongtitude(), "-") > 1) {
//								ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen",
//										messageSource.getMessage("station.longitude", langCode));
//							} else {
//								Double longitude = Double.parseDouble(dto.getLongtitude());
//								if (longitude > 180 || longitude < -180) {
//									ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
//											messageSource.getMessage("station.longitude", langCode));
//								} else {
//									entity.setLongitude(longitude);
//								}
//							}
//						} else {
//							ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
//									messageSource.getMessage("station.longitude", langCode));
//						}
//
//					} catch (Exception e) {
//						ExcelDataUltils.addMessage(errData, "err.numberFormat",
//								messageSource.getMessage("station.longitude", langCode));
//					}
          int kq = checkLongLat(dto.getLongtitude(), 180);
          if (kq == Constains.NUMBER_ONE) {
            ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen",
                messageSource.getMessage("station.longitude", langCode));
          } else if (kq == Constains.NUMBER_TWO) {
            ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
                messageSource.getMessage("station.longitude", langCode));
          } else if (kq == Constains.NUMBER_THREE) {
            entity.setLongitude(Double.parseDouble(dto.getLongtitude().substring(0,
                dto.getLongtitude().indexOf(".") + Constains.NUMBER_SIX)));
          } else if (kq == Constains.NUMBER_FOUR) {
            ExcelDataUltils.addMessage(errData, "err.missing.5.outOfGeo",
                messageSource.getMessage("station.longitude", langCode));
          }
        }
      }

      if (flagList.getLatitude().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getLatitude())) {
          ExcelDataUltils.addMessage(errData, "err.empty",
              messageSource.getMessage("station.latitude", langCode));
        } else {
//					try {
//						String regex = "^[0-9-]+\\.[0-9][0-9][0-9][0-9][0-9]$";
//						if (dto.getLatitude().matches(regex)) {
//							if (StringUtils.countMatches(dto.getLatitude(), "-") > 1) {
//								ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen",
//										messageSource.getMessage("station.latitude", langCode));
//							} else {
//								Double latitude = Double.parseDouble(dto.getLatitude());
//								if (latitude > 90 || latitude < -90) {
//									ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
//											messageSource.getMessage("station.latitude", langCode));
//								} else {
//									entity.setLatitude(latitude);
//								}
//							}
//						} else {
//							ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
//									messageSource.getMessage("station.latitude", langCode));
//						}
//					} catch (Exception e) {
//						ExcelDataUltils.addMessage(errData, "err.numberFormat",
//								messageSource.getMessage("station.latitude", langCode));
//					}
          int kq = checkLongLat(dto.getLatitude(), 90);
          messageSource.getMessage("station.latitude", langCode);
          if (kq == Constains.NUMBER_ONE) {
            ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen",
                messageSource.getMessage("station.latitude", langCode));
          } else if (kq == Constains.NUMBER_TWO) {
            ExcelDataUltils.addMessage(errData, "err.hasPre.outOfGeo",
                messageSource.getMessage("station.latitude", langCode));
          } else if (kq == Constains.NUMBER_THREE) {
            entity.setLatitude(Double.parseDouble(
                dto.getLatitude().substring(0, dto.getLatitude().indexOf(".") + Constains.NUMBER_SIX)));
          } else if (kq == Constains.NUMBER_FOUR) {
            ExcelDataUltils.addMessage(errData, "err.missing.5.outOfGeo",
                messageSource.getMessage("station.latitude", langCode));
          }
        }
      }

      Map<String, Integer> listStatus = new WeakHashMap<>();
      listStatus.put(messageSource.getMessage("cable.statusName.zero", langCode), 0);
      listStatus.put(messageSource.getMessage("cable.statusName.one", langCode), 1);
      if (flagList.getStatus().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getStatus())) {
          if (listStatus.containsKey(dto.getStatus())) {
            entity.setStatus(listStatus.get(dto.getStatus()));
          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect",
                messageSource.getMessage("station.status", langCode));
          }
        } else {
          ExcelDataUltils.addMessage(errData, "err.choose",
              messageSource.getMessage("station.status", langCode));
        }
      }

      if (flagList.getLatitude().equals("Y")) {
        if (dto.getNote().length() > 500) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.500",
              messageSource.getMessage("station.note", langCode));
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
        infraPoolDao.savePool(entity);
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

    String savePath = ExcelDataUltils.writeResultExcel(file, new InfraPoolDTO(), mapObj,
        messageSource.getMessage("pool.result.import.edit.fileName", langCode));
    return savePath + "+" + successRecord + "+" + errRecord;

  }

  private static int checkLongLat(String dto, int max) {
    String regex = "^[0-9-]+\\.[0-9]{0,}$";
    if (dto.matches(regex)) {
      String regex2 = "^[0-9-]+\\.[0-9]{5,}$";
      if (dto.matches(regex2)) {
        try {
          Double latitude = Double.parseDouble(dto);
          if (latitude > max || latitude < (0 - max)) {
            return Constains.NUMBER_TWO;
          } else {
//					dto = dto.substring(0,dto.indexOf(".") + 6);
            return Constains.NUMBER_THREE;
          }
        } catch (Exception e) {
          return Constains.NUMBER_ONE;
        }
      } else {
        return Constains.NUMBER_FOUR;
      }
    } else {
      return Constains.NUMBER_ONE;
    }

  }
}
