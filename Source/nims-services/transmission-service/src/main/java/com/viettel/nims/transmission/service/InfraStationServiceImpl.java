package com.viettel.nims.transmission.service;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.commom.*;
import com.viettel.nims.transmission.dao.*;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.dto.InfraStationDTO;
import com.viettel.nims.transmission.model.dto.PGInfraStationDto;
import com.viettel.nims.transmission.model.view.*;
import com.viettel.nims.transmission.postgre.dao.PGInfraStationDao;
import com.viettel.nims.transmission.utils.Constains;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
@Slf4j
@Service
@Transactional(transactionManager = "globalTransactionManager")
public class InfraStationServiceImpl implements InfraStationService {

  @Autowired
  InfraStationDao infraStationDao;

  @Autowired
  InfraOdfDao infraOdfDao;

  @Autowired
  PGInfraStationDao pgInfraStationDao;

  @Autowired
  InfraCablesDao infraCablesDao;

  @Autowired
  InfraCableLanesDao infraCableLanesDao;

  @Autowired
  TransmissionDao transmissionDao;

  @Autowired
  TransmissionService transmissionService;

  @Autowired
  MessageResource messageSource;

  @Override
  // Tim kiem co ban
  public ResponseEntity<?> findBasicStation(InfraStationsBO infraStationsBO) {
    try {
      FormResult result = infraStationDao.findBasicStation(infraStationsBO);
      Response<FormResult> response = new Response<>();
      response.setContent(result);
      return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
    } catch (Exception e) {
      return ResponseBase.createResponse(null, Constains.FAILED, 500);
    }
  }

  @Override
  // Tim kiem nang cao
  public ResponseEntity<?> findAdvanceStation(InfraStationsBO infraStationsBO, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    try {
      FormResult result = infraStationDao.findAdvanceStation(infraStationsBO, userId);
      if (result.getListData().size() > 0) {
        List<ViewInfraStationsBO> listData = (List<ViewInfraStationsBO>) result.getListData();
        for (ViewInfraStationsBO item : listData) {
//          if (item.getGeometry() != null) {
//            item.setLongitude(item.getGeometry().getX());
//            item.setLatitude(item.getGeometry().getY());
//            item.setGeometry(null);
//          }
          if (item.getTerrain() != null) {
            item.setTerrainName(item.getTerrain() == 0 ? messageSource.getMessage("station.terrain.zero", langCode) : item.getTerrain() == 1 ? messageSource.getMessage("station.terrain.one", langCode) : item.getTerrain() == 2 ? messageSource.getMessage("station.terrain.two", langCode) : item.getTerrain() == 3 ? messageSource.getMessage("station.terrain.three", langCode) : item.getTerrain() == 4 ? messageSource.getMessage("station.terrain.four", langCode) : item.getTerrain() == 5 ? messageSource.getMessage("station.terrain.fine", langCode) : "");
          }
          if (item.getStatus() != null) {
            item.setStatusName(item.getStatus() == 1 ? messageSource.getMessage("station.status.one", langCode) : item.getStatus() == 2 ? messageSource.getMessage("station.status.two", langCode) : item.getStatus() == 3 ? messageSource.getMessage("station.status.three", langCode) : item.getStatus() == 4 ? messageSource.getMessage("station.status.four", langCode) : item.getStatus() == 5 ? messageSource.getMessage("station.status.fine", langCode) : item.getStatus() == 6 ? messageSource.getMessage("station.status.six", langCode) : "");
          }
          if (item.getBackupStatus() != null) {
            item.setBackupStatusName(item.getBackupStatus() == 0 ? messageSource.getMessage("station.backup.status.zero", langCode) : item.getBackupStatus() == 1 ? messageSource.getMessage("station.backup.status.one", langCode) : "");
          }
          if (item.getPosition() != null) {
            item.setPositionName(item.getPosition() == 1 ? messageSource.getMessage("station.position.one", langCode) : item.getPosition() == 2 ? messageSource.getMessage("station.position.two", langCode) : item.getPosition() == 3 ? messageSource.getMessage("station.position.three", langCode) : "");
          }
          if (item.getAuditType() != null) {
            item.setAuditTypeName(item.getAuditType() == 1 ? messageSource.getMessage("station.audit.type.one", langCode) : item.getAuditType() == 2 ? messageSource.getMessage("station.audit.type.two", langCode) : item.getAuditType() == 3 ? messageSource.getMessage("station.audit.type.three", langCode) : "");
          }
          if (item.getAuditStatus() != null) {
            item.setAuditStatusName(item.getAuditStatus() == 0 ? messageSource.getMessage("station.audit.status.zero", langCode) : item.getAuditStatus() == 1 ? messageSource.getMessage("station.audit.status.one", langCode) : item.getAuditStatus() == 2 ? messageSource.getMessage("station.audit.status.two", langCode) : item.getAuditStatus() == 3 ? messageSource.getMessage("station.audit.status.three", langCode) : item.getAuditStatus() == 4 ? messageSource.getMessage("station.audit.status.four", langCode) : item.getAuditStatus() == 5 ? messageSource.getMessage("station.audit.status.fine", langCode) : item.getAuditStatus() == 6 ? messageSource.getMessage("station.audit.status.six", langCode) : item.getAuditStatus() == 7 ? messageSource.getMessage("station.audit.status.seven", langCode) : item.getAuditStatus() == 71 ? messageSource.getMessage("station.audit.status.seventy.one", langCode) : item.getAuditStatus() == 72 ? messageSource.getMessage("station.audit.status.seventy.two", langCode) : item.getAuditStatus() == 8 ? messageSource.getMessage("station.audit.status.eight", langCode) : item.getAuditStatus() == 81 ? messageSource.getMessage("station.audit.status.eighty.one", langCode) : item.getAuditStatus() == 82 ? messageSource.getMessage("station.audit.status.eighty.two", langCode) : item.getAuditStatus() == 9 ? messageSource.getMessage("station.audit.status.nine", langCode) : item.getAuditStatus() == 10 ? messageSource.getMessage("station.audit.status.ten", langCode) : "");
          }
        }
        result.setListData(listData);
      } else {
        result.setListData(null);
      }
      return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
    } catch (Exception e) {
      log.error("Exception", e);
      return ResponseBase.createResponse(null, Constains.FAILED, 500);
    }
  }

  @Override
  // Them
  public ResponseEntity<?> saveStation(InfraStationsBO infraStationsBO) {
    JSONObject jsonObject = new JSONObject();
    try {
      if (infraStationsBO != null) {
        if (infraStationsBO.getStationCode() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co ma nha tram", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (infraStationsBO.getDeptId() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co don vi", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (infraStationsBO.getLocationId() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co dia ban", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (infraStationsBO.getHouseOwnerPhone() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co so dien thoai chu nha", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (infraStationsBO.getOwnerId() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co chu so huu", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (infraStationsBO.getStationFeatureId() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co tinh chat nha tram", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (infraStationsBO.getLongitude() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co kinh do", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else if (infraStationsBO.getLatitude() == null) {
          jsonObject = JSONResponse.buildResultJson(1, "Khong co vi do", "error");
          return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
        } else {
          PGInfraStationDto pgDto = new PGInfraStationDto();
          if (infraStationsBO.getStationId() != null) {
            pgDto.setUpdate(Boolean.TRUE);
          }
          GeometryFactory gf = new GeometryFactory();
          Point point = gf.createPoint(new Coordinate(infraStationsBO.getLongitude(), infraStationsBO.getLatitude()));
          infraStationsBO.setGeometry(point);
          Long result = infraStationDao.saveStation(infraStationsBO);
          if (result != 0) {
            pgDto.setStation_id(result);
            pgDto.setStation_code(infraStationsBO.getStationCode());
            pgDto.setDept_id(infraStationsBO.getDeptId());
            pgDto.setLocation_id(infraStationsBO.getLocationId());
            pgDto.setStatus(infraStationsBO.getStatus());
            pgDto.setLongitude(infraStationsBO.getLongitude());
            pgDto.setLatitude(infraStationsBO.getLatitude());
            pgDto.setAddress(infraStationsBO.getAddress());
            pgDto.setOwner_id(infraStationsBO.getOwnerId());
            pgInfraStationDao.insertOrUpdate(pgDto);
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
  public ResponseEntity<?> findStationById(Long id, String langCode) {
    try {
      ViewInfraStationsBO infraStationsBO = infraStationDao.findStationById(id);
//      if (infraStationsBO.getGeometry() != null) {
//        infraStationsBO.setLongitude(infraStationsBO.getGeometry().getX());
//        infraStationsBO.setLatitude(infraStationsBO.getGeometry().getY());
//        infraStationsBO.setGeometry(null);
//      }
      if (infraStationsBO.getTerrain() != null) {
        infraStationsBO.setTerrainName(infraStationsBO.getTerrain() == 0 ? messageSource.getMessage("station.terrain.zero", langCode) : infraStationsBO.getTerrain() == 1 ? messageSource.getMessage("station.terrain.one", langCode) : infraStationsBO.getTerrain() == 2 ? messageSource.getMessage("station.terrain.two", langCode) : infraStationsBO.getTerrain() == 3 ? messageSource.getMessage("station.terrain.three", langCode) : infraStationsBO.getTerrain() == 4 ? messageSource.getMessage("station.terrain.four", langCode) : infraStationsBO.getTerrain() == 5 ? messageSource.getMessage("station.terrain.fine", langCode) : "");
      }
      if (infraStationsBO.getStatus() != null) {
        infraStationsBO.setStatusName(infraStationsBO.getStatus() == 1 ? messageSource.getMessage("station.status.one", langCode) : infraStationsBO.getStatus() == 2 ? messageSource.getMessage("station.status.two", langCode) : infraStationsBO.getStatus() == 3 ? messageSource.getMessage("station.status.three", langCode) : infraStationsBO.getStatus() == 4 ? messageSource.getMessage("station.status.four", langCode) : infraStationsBO.getStatus() == 5 ? messageSource.getMessage("station.status.fine", langCode) : infraStationsBO.getStatus() == 6 ? messageSource.getMessage("station.status.six", langCode) : "");
      }
      if (infraStationsBO.getBackupStatus() != null) {
        infraStationsBO.setBackupStatusName(infraStationsBO.getBackupStatus() == 0 ? messageSource.getMessage("station.backup.status.zero", langCode) : infraStationsBO.getBackupStatus() == 1 ? messageSource.getMessage("station.backup.status.one", langCode) : "");
      }
      if (infraStationsBO.getPosition() != null) {
        infraStationsBO.setPositionName(infraStationsBO.getPosition() == 1 ? messageSource.getMessage("station.position.one", langCode) : infraStationsBO.getPosition() == 2 ? messageSource.getMessage("station.position.two", langCode) : infraStationsBO.getPosition() == 3 ? messageSource.getMessage("station.position.three", langCode) : "");
      }
      if (infraStationsBO.getAuditType() != null) {
        infraStationsBO.setAuditTypeName(infraStationsBO.getAuditType() == 1 ? messageSource.getMessage("station.audit.type.one", langCode) : infraStationsBO.getAuditType() == 2 ? messageSource.getMessage("station.audit.type.two", langCode) : infraStationsBO.getAuditType() == 3 ? messageSource.getMessage("station.audit.type.three", langCode) : "");
      }
      if (infraStationsBO.getAuditStatus() != null) {
        infraStationsBO.setAuditStatusName(infraStationsBO.getAuditStatus() == 0 ? messageSource.getMessage("station.audit.status.zero", langCode) : infraStationsBO.getAuditStatus() == 1 ? messageSource.getMessage("station.audit.status.one", langCode) : infraStationsBO.getAuditStatus() == 2 ? messageSource.getMessage("station.audit.status.two", langCode) : infraStationsBO.getAuditStatus() == 3 ? messageSource.getMessage("station.audit.status.three", langCode) : infraStationsBO.getAuditStatus() == 4 ? messageSource.getMessage("station.audit.status.four", langCode) : infraStationsBO.getAuditStatus() == 5 ? messageSource.getMessage("station.audit.status.fine", langCode) : infraStationsBO.getAuditStatus() == 6 ? messageSource.getMessage("station.audit.status.six", langCode) : infraStationsBO.getAuditStatus() == 7 ? messageSource.getMessage("station.audit.status.seven", langCode) : infraStationsBO.getAuditStatus() == 71 ? messageSource.getMessage("station.audit.status.seventy.one", langCode) : infraStationsBO.getAuditStatus() == 72 ? messageSource.getMessage("station.audit.status.seventy.two", langCode) : infraStationsBO.getAuditStatus() == 8 ? messageSource.getMessage("station.audit.status.eight", langCode) : infraStationsBO.getAuditStatus() == 81 ? messageSource.getMessage("station.audit.status.eighty.one", langCode) : infraStationsBO.getAuditStatus() == 82 ? messageSource.getMessage("station.audit.status.eighty.two", langCode) : infraStationsBO.getAuditStatus() == 9 ? messageSource.getMessage("station.audit.status.nine", langCode) : infraStationsBO.getAuditStatus() == 10 ? messageSource.getMessage("station.audit.status.ten", langCode) : "");
      }

//      Response<InfraStationsBO> response = new Response<>();
//      response.setStatus(Constains.UPDATE);
//      response.setContent(infraStationsBO);
//      return new ResponseEntity<>(response, HttpStatus.OK);
      return ResponseBase.createResponse(infraStationsBO, Constains.UPDATE, 200);
    } catch (Exception e) {
      log.error("Exception", e);
      return ResponseBase.createResponse(null, "error", 500);
    }
  }

  @Override
  // Xoa nha tram
  public JSONObject delete(Long id) {
    JSONObject data = new JSONObject();
    // Tim odf thuoc nha tram
    List<ViewInfraOdfBO> infraOdfBOList = infraOdfDao.findOdfByStationId(id);
    if (infraOdfBOList.size() != 0) {
      JSONArray odf = new JSONArray();
      for (int i = 0; i < infraOdfBOList.size(); i++) {
        odf.add(infraOdfBOList.get(i).getOdfCode());
      }
      data.put("odf", odf);
    }
    // Tim doan cap thuoc nha tram
    List<InfraCablesBO> infraCablesBOList = infraCablesDao.findCablesByStationId(id);
    if (infraCablesBOList.size() != 0) {
      JSONArray cables = new JSONArray();
      for (int i = 0; i < infraCablesBOList.size(); i++) {
        cables.add(infraCablesBOList.get(i).getCableCode());
      }
      data.put("cables", cables);
    }
    // Tim doan cap thuoc nha tram
    List<InfraCableLanesBO> infraCableLanesBOList = infraCableLanesDao.findCableLanesByStationId(id);
    if (infraCableLanesBOList.size() != 0) {
      JSONArray cableLanes = new JSONArray();
      for (int i = 0; i < infraCablesBOList.size(); i++) {
        cableLanes.add(infraCableLanesBOList.get(i).getLaneCode());
      }
      data.put("cableLanes", cableLanes);
    }
    if (data.size() == 0) {
      ViewInfraStationsBO infraStationsBO = infraStationDao.findStationById(id);
      if (infraStationsBO != null) {
        int result = infraStationDao.delete(id);
        if (result > 0) {
          data.put("code", 1);
        }
      }
    }
    return data;
  }

  @Override
  public JSONObject deleteMultipe(List<InfraStationsBO> infraStationsBOList) {
    JSONObject data = new JSONObject();
    // Tim odf thuoc nha tram
    JSONArray odf = new JSONArray();
    JSONArray cables = new JSONArray();
    JSONArray cableLanes = new JSONArray();

    for (int i = 0; i < infraStationsBOList.size(); i++) {
      List<ViewInfraOdfBO> infraOdfBOList = infraOdfDao.findOdfByStationId(infraStationsBOList.get(i).getStationId());
      if (infraOdfBOList.size() != 0) {
        data.put("stationCode", infraStationsBOList.get(i).getStationCode());
        for (int j = 0; j < infraOdfBOList.size(); j++) {
          odf.add(infraOdfBOList.get(j).getOdfCode());
        }
        data.put("odf", odf);
      }
      // Tim doan cap thuoc nha tram
      List<InfraCablesBO> infraCablesBOList = infraCablesDao.findCablesByStationId(infraStationsBOList.get(i).getStationId());
      if (infraCablesBOList.size() != 0) {
        data.put("stationCode", infraStationsBOList.get(i).getStationCode());
        for (int j = 0; j < infraCablesBOList.size(); j++) {
          cables.add(infraCablesBOList.get(j).getCableCode());
        }
        data.put("cables", cables);
      }
      // Tim doan cap thuoc nha tram
      List<InfraCableLanesBO> infraCableLanesBOList = infraCableLanesDao.findCableLanesByStationId(infraStationsBOList.get(i).getStationId());
      if (infraCableLanesBOList.size() != 0) {
        data.put("stationCode", infraStationsBOList.get(i).getStationCode());
        for (int j = 0; j < infraCablesBOList.size(); j++) {
          cableLanes.add(infraCableLanesBOList.get(j).getLaneCode());
        }
        data.put("cableLanes", cableLanes);
      }
    }

    if (data.size() == 0) {
      for (int i = 0; i < infraStationsBOList.size(); i++) {
        ViewInfraStationsBO infraStationsBO = infraStationDao.findStationById(infraStationsBOList.get(i).getStationId());
        if (infraStationsBO != null) {
          int result = infraStationDao.delete(infraStationsBOList.get(i).getStationId());
          if (result > 0) {
            data.put("code", 1);
          }
        }
      }
    }
    return data;
  }

  @Override
  public String exportExcel(InfraStationsBO infraStationsBO, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = infraStationDao.findAdvanceStation(infraStationsBO, userId);
    List<ViewInfraStationsBO> data = (List<ViewInfraStationsBO>) result.getListData();

    if (CollectionUtils.isEmpty(data)) {
      return "";
    }

    Workbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("station.sheet", langCode));

    // Set title
    // Set title
    Row firstRow = sheet.createRow(1);
    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 25));
    Cell titleCell = firstRow.createCell(0);
    titleCell.setCellValue(messageSource.getMessage("odf.title", langCode));
    titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

    Row secondRow = sheet.createRow(2);
    sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 25));
    Cell titleCell2 = secondRow.createCell(0);
    titleCell2.setCellValue(messageSource.getMessage("station.report.date", langCode) + CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
    titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

//     Set header
    List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.dep.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.location.name", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.address.detail", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.houseOwnerName", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.houseOwnerPhone", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.address", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.ownerName", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.constructionDate", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.status", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.houseStationType", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.stationType", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.stationFeature", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.backupStatus", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.position", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.length", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.width", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.height", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.heightestBuilding", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.longitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.latitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.auditType", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.auditStatus", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.auditReason", langCode), 20 * 256));
//    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.fileCheck", langCode), 20 * 256));
//    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.fileListed", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.note", langCode), 20 * 256));
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
    path = path + "EXPORT_NHATRAM" + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm") + ".xlsx";
    System.out.println(path);

    DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
    // Set data
    for (ViewInfraStationsBO item : data) {

      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell1 = row.createCell(1);
      cell1.setCellValue(item.getStationCode() != null ? item.getStationCode() : "");
      cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell2 = row.createCell(2);
      cell2.setCellValue(item.getDeptCode() != null ? item.getDeptCode() : "");
      cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell3 = row.createCell(3);
      cell3.setCellValue(item.getPathLocalName() != null ? item.getPathLocalName() : "");
      cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell4 = row.createCell(4);
      cell4.setCellValue(item.getLocationCode() != null ? item.getLocationCode() : "");
      cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell5 = row.createCell(5);
      cell5.setCellValue(item.getHouseOwnerName() != null ? item.getHouseOwnerName() : "");
      cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell6 = row.createCell(6);
      cell6.setCellValue(item.getHouseOwnerPhone() != null ? item.getHouseOwnerPhone() : "");
      cell6.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell7 = row.createCell(7);
      cell7.setCellValue(item.getAddress() != null ? item.getAddress() : "");
      cell7.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell8 = row.createCell(8);
      cell8.setCellValue(item.getOwnerName() != null ? item.getOwnerName() : "");
      cell8.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell9 = row.createCell(9);
      if (item.getConstructionDate() != null) {
        cell9.setCellValue(df.format(item.getConstructionDate()));
      } else {
        cell9.setCellValue("");
      }
      cell9.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell10 = row.createCell(10);
      if (item.getStatus() != null) {
        item.setStatusName(item.getStatus() == 1 ? messageSource.getMessage("station.status.one", langCode) : item.getStatus() == 2 ? messageSource.getMessage("station.status.two", langCode) : item.getStatus() == 3 ? messageSource.getMessage("station.status.three", langCode) : item.getStatus() == 4 ? messageSource.getMessage("station.status.four", langCode) : item.getStatus() == 5 ? messageSource.getMessage("station.status.fine", langCode) : item.getStatus() == 6 ? messageSource.getMessage("station.status.six", langCode) : "");
      }
      cell10.setCellValue(item.getStatusName() != null ? item.getStatusName() : "");
      cell10.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell11 = row.createCell(11);
      cell11.setCellValue(item.getHouseStationTypeName() != null ? item.getHouseStationTypeName() : "");
      cell11.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell12 = row.createCell(12);
      cell12.setCellValue(item.getStationTypeName() != null ? item.getStationTypeName() : "");
      cell12.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell13 = row.createCell(13);
      cell13.setCellValue(item.getStationFeatureName() != null ? item.getStationFeatureName() : "");
      cell13.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell14 = row.createCell(14);
      if (item.getBackupStatus() != null) {
        item.setBackupStatusName(item.getBackupStatus() == 0 ? messageSource.getMessage("station.backup.status.zero", langCode) : item.getBackupStatus() == 1 ? messageSource.getMessage("station.backup.status.one", langCode) : "");
      }
      cell14.setCellValue(item.getBackupStatusName() != null ? item.getBackupStatusName() : "");
      cell14.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell15 = row.createCell(15);
      if (item.getPosition() != null) {
        item.setPositionName(item.getPosition() == 1 ? messageSource.getMessage("station.position.one", langCode) : item.getPosition() == 2 ? messageSource.getMessage("station.position.two", langCode) : item.getPosition() == 3 ? messageSource.getMessage("station.position.three", langCode) : "");
      }
      cell15.setCellValue(item.getPositionName() != null ? item.getPositionName() : "");
      cell15.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell16 = row.createCell(16);
      cell16.setCellValue(item.getLength() != null ? String.format("%.5f", item.getLength()) : "");
      cell16.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell17 = row.createCell(17);
      cell17.setCellValue(item.getWidth() != null ? String.format("%.5f", item.getWidth()) : "");
      cell17.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell18 = row.createCell(18);
      cell18.setCellValue(item.getHeight() != null ? String.format("%.5f", item.getHeight()) : "");
      cell18.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell19 = row.createCell(19);
      cell19.setCellValue(item.getHeightestBuilding() != null ? String.format("%.5f", item.getHeightestBuilding()) : "");
      cell19.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));
//
//      if (item.getGeometry() != null) {
//        item.setLongitude(item.getGeometry().getX());
//        item.setLatitude(item.getGeometry().getY());
//      }
      Cell cell20 = row.createCell(20);
      cell20.setCellValue(item.getLongitude() != null ? String.format("%.5f", item.getLongitude()) : "");
      cell20.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell21 = row.createCell(21);
      cell21.setCellValue(item.getLatitude() != null ? String.format("%.5f", item.getLatitude()) : "");
      cell21.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell22 = row.createCell(22);
      if (item.getAuditType() != null) {
        item.setAuditTypeName(item.getAuditType() == 1 ? messageSource.getMessage("station.audit.type.one", langCode) : item.getAuditType() == 2 ? messageSource.getMessage("station.audit.type.two", langCode) : item.getAuditType() == 3 ? messageSource.getMessage("station.audit.type.three", langCode) : "");
      }
      cell22.setCellValue(item.getAuditTypeName() != null ? item.getAuditTypeName() : "");
      cell22.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell23 = row.createCell(23);
      if (item.getAuditStatus() != null) {
        item.setAuditStatusName(item.getAuditStatus() == 0 ? messageSource.getMessage("station.audit.status.zero", langCode) : item.getAuditStatus() == 1 ? messageSource.getMessage("station.audit.status.one", langCode) : item.getAuditStatus() == 2 ? messageSource.getMessage("station.audit.status.two", langCode) : item.getAuditStatus() == 3 ? messageSource.getMessage("station.audit.status.three", langCode) : item.getAuditStatus() == 4 ? messageSource.getMessage("station.audit.status.four", langCode) : item.getAuditStatus() == 5 ? messageSource.getMessage("station.audit.status.fine", langCode) : item.getAuditStatus() == 6 ? messageSource.getMessage("station.audit.status.six", langCode) : item.getAuditStatus() == 7 ? messageSource.getMessage("station.audit.status.seven", langCode) : item.getAuditStatus() == 71 ? messageSource.getMessage("station.audit.status.seventy.one", langCode) : item.getAuditStatus() == 72 ? messageSource.getMessage("station.audit.status.seventy.two", langCode) : item.getAuditStatus() == 8 ? messageSource.getMessage("station.audit.status.eight", langCode) : item.getAuditStatus() == 81 ? messageSource.getMessage("station.audit.status.eighty.one", langCode) : item.getAuditStatus() == 82 ? messageSource.getMessage("station.audit.status.eighty.two", langCode) : item.getAuditStatus() == 9 ? messageSource.getMessage("station.audit.status.nine", langCode) : item.getAuditStatus() == 10 ? messageSource.getMessage("station.audit.status.ten", langCode) : "");
      }
      cell23.setCellValue(item.getAuditStatusName() != null ? item.getAuditStatusName() : "");
      cell23.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell24 = row.createCell(24);
      cell24.setCellValue("");
      cell24.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

//      Cell cell25 = row.createCell(25);
//      cell25.setCellValue("");
//      cell25.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));
//
//      Cell cell26 = row.createCell(26);
//      cell26.setCellValue("");
//      cell26.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell27 = row.createCell(25);
      cell27.setCellValue(item.getNote() != null ? item.getNote() : "");
      cell27.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

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
  public String exportExcelChose(List<ViewInfraStationsBO> data, String langCode) {

    if (CollectionUtils.isEmpty(data)) {
      return null;
    }

    Workbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("station.sheet", langCode));

    // Set title
    Row firstRow = sheet.createRow(1);
    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 25));
    Cell titleCell = firstRow.createCell(0);
    titleCell.setCellValue(messageSource.getMessage("station.title", langCode));
    titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

    Row secondRow = sheet.createRow(2);
    sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 25));
    Cell titleCell2 = secondRow.createCell(0);
    titleCell2.setCellValue(messageSource.getMessage("station.report.date", langCode) + CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
    titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

//     Set header
    List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.dep.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.location.name", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.address.detail", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.houseOwnerName", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.houseOwnerPhone", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.address", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.ownerName", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.constructionDate", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.status", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.houseStationType", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.stationType", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.stationFeature", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.backupStatus", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.position", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.length", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.width", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.height", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.heightestBuilding", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.longitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.latitude", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.auditType", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.auditStatus", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.auditReason", langCode), 20 * 256));
//    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.fileCheck", langCode), 20 * 256));
//    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.fileListed", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.note", langCode), 20 * 256));
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
    path = path + "EXPORT_NHATRAM" + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm") + ".xlsx";

    DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
    // Set data
    for (ViewInfraStationsBO item : data) {

      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell1 = row.createCell(1);
      cell1.setCellValue(item.getStationCode() != null ? item.getStationCode() : "");
      cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell2 = row.createCell(2);
      cell2.setCellValue(item.getDeptCode() != null ? item.getDeptCode() : "");
      cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell3 = row.createCell(3);
      cell3.setCellValue(item.getPathLocalName() != null ? item.getPathLocalName() : "");
      cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell4 = row.createCell(4);
      cell4.setCellValue(item.getLocationCode() != null ? item.getLocationCode() : "");
      cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell5 = row.createCell(5);
      cell5.setCellValue(item.getHouseOwnerName() != null ? item.getHouseOwnerName() : "");
      cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell6 = row.createCell(6);
      cell6.setCellValue(item.getHouseOwnerPhone() != null ? item.getHouseOwnerPhone() : "");
      cell6.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell7 = row.createCell(7);
      cell7.setCellValue(item.getAddress() != null ? item.getAddress() : "");
      cell7.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell8 = row.createCell(8);
      cell8.setCellValue(item.getOwnerName() != null ? item.getOwnerName() : "");
      cell8.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell9 = row.createCell(9);
      if (item.getConstructionDate() != null) {
        cell9.setCellValue(df.format(item.getConstructionDate()));
      } else {
        cell9.setCellValue("");
      }
      cell9.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell10 = row.createCell(10);
      cell10.setCellValue(item.getStatusName() != null ? item.getStatusName() : "");
      cell10.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell11 = row.createCell(11);
      cell11.setCellValue(item.getHouseStationTypeName() != null ? item.getHouseStationTypeName() : "");
      cell11.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell12 = row.createCell(12);
      cell12.setCellValue(item.getStationTypeName() != null ? item.getStationTypeName() : "");
      cell12.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell13 = row.createCell(13);
      cell13.setCellValue(item.getStationFeatureName() != null ? item.getStationFeatureName() : "");
      cell13.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell14 = row.createCell(14);
      cell14.setCellValue(item.getBackupStatusName() != null ? item.getBackupStatusName() : "");
      cell14.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell15 = row.createCell(15);
      cell15.setCellValue(item.getPositionName() != null ? item.getPositionName() : "");
      cell15.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell16 = row.createCell(16);
      cell16.setCellValue(item.getLength() != null ? String.format("%.5f", item.getLength()) : "");
      cell16.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell17 = row.createCell(17);
      cell17.setCellValue(item.getWidth() != null ? String.format("%.5f", item.getWidth()) : "");
      cell17.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell18 = row.createCell(18);
      cell18.setCellValue(item.getHeight() != null ? String.format("%.5f", item.getHeight()) : "");
      cell18.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell19 = row.createCell(19);
      cell19.setCellValue(item.getHeightestBuilding() != null ? String.format("%.5f", item.getHeightestBuilding()) : "");
      cell19.setCellStyle(ExcelStyleUtil.getNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell20 = row.createCell(20);
      cell20.setCellValue(item.getLongitude() != null ? String.format("%.5f", item.getLongitude()) : "");
      cell20.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell21 = row.createCell(21);
      cell21.setCellValue(item.getLatitude() != null ? String.format("%.5f", item.getLatitude()) : "");
      cell21.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell22 = row.createCell(22);
      cell22.setCellValue(item.getAuditTypeName() != null ? item.getAuditTypeName() : "");
      cell22.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell23 = row.createCell(23);
      cell23.setCellValue(item.getAuditStatusName() != null ? item.getAuditStatusName() : "");
      cell23.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell24 = row.createCell(24);
      cell24.setCellValue("");
      cell24.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell27 = row.createCell(25);
      cell27.setCellValue(item.getNote() != null ? item.getNote() : "");
      cell27.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

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
  public String downloadTeamplate(List<ViewInfraStationsBO> infraStationsBOList, Integer type, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    String savePath = messageSource.getMessage("report.out", langCode);
    File dir = new File(savePath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    if (type == 1) {
      savePath = savePath + messageSource.getMessage("station.template.file.import.add.name", langCode) + CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
    } else {
      savePath = savePath + messageSource.getMessage("station.template.file.import.edit.name", langCode) + CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
    }

    InputStream excelFile;
    XSSFWorkbook workbook = null;
    try {
      excelFile = new ClassPathResource(Constants.TEMPLATE_FILE.TEAMPLATE_INFRASTATION).getInputStream();
//      excelFile = new ClassPathResource("/templates/teamplate_import_nha_tram.xlsx").getInputStream();
      workbook = new XSSFWorkbook(excelFile);
    } catch (Exception e) {
      log.error("Exception", e);
    }
    XSSFSheet sheetData = workbook.getSheetAt(0);
    int rowNum = 4;
    int index = 1;
    for (ViewInfraStationsBO bo : infraStationsBOList) {
      Row row = sheetData.createRow(rowNum++);
      int i = 0;
      Cell cell0 = row.createCell(i++);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell1 = row.createCell(i++);
      cell1.setCellValue(bo.getStationCode() == null ? "" : bo.getStationCode());
      cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell2 = row.createCell(i++);
      ViewCatDepartmentBO catDept = transmissionDao.findDepartmentById(bo.getDeptId(), userId);
      cell2.setCellValue(catDept == null ? "" : catDept.getDeptCode());
      cell2.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell3 = row.createCell(i++);
      ViewTreeCatLocationBO catLocation = transmissionDao.findCatLocationById(bo.getLocationId(), userId);
      cell3.setCellValue(catLocation == null ? "" : catLocation.getLocationCode());
      cell3.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell5 = row.createCell(i++);
      cell5.setCellValue(bo.getHouseOwnerName() == null ? "" : bo.getHouseOwnerName());
      cell5.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell6 = row.createCell(i++);
      cell6.setCellValue(bo.getHouseOwnerPhone() == null ? "" : bo.getHouseOwnerPhone());
      cell6.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

      Cell cell7 = row.createCell(i++);
      cell7.setCellValue(bo.getAddress() == null ? "" : bo.getAddress());
      cell7.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell8 = row.createCell(i++);
      ViewCatItemBO catOwner = transmissionDao.findCatItemByCategoryCodeAndId(bo.getOwnerId(), "CAT_OWNER");
      cell8.setCellValue(catOwner == null ? "" : catOwner.getItemCode());
      cell8.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
      Cell cell9 = row.createCell(i++);
      cell9.setCellValue(bo.getConstructionDate() == null ? "" : simpleDateFormat.format(bo.getConstructionDate()));
      cell9.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

      Cell cell10 = row.createCell(i++);
      cell10.setCellValue(bo.getStatusName() == null ? "" : bo.getStatusName());
      cell10.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell11 = row.createCell(i++);
      ViewCatItemBO catHouseTypeBo = transmissionDao.findCatItemByCategoryCodeAndId(bo.getHouseStationTypeId(), "HOUSE_STATION_TYPE");
      cell11.setCellValue(catHouseTypeBo == null ? "" : catHouseTypeBo.getItemCode());
      cell11.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell13 = row.createCell(i++);
      ViewCatItemBO catStationType = transmissionDao.findCatItemByCategoryCodeAndId(bo.getStationTypeId(), "STATION_TYPE");
      cell13.setCellValue(catStationType == null ? "" : catStationType.getItemCode());
      cell13.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell14 = row.createCell(i++);
      ViewCatItemBO catStationFeature = transmissionDao.findCatItemByCategoryCodeAndId(bo.getStationFeatureId(), "STATION_FEATURE");
      cell14.setCellValue(catStationFeature == null ? "" : catStationFeature.getItemCode());
      cell14.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell15 = row.createCell(i++);
      cell15.setCellValue(bo.getBackupStatusName() == null ? "" : bo.getBackupStatusName());
      cell15.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell16 = row.createCell(i++);
      cell16.setCellValue(bo.getPositionName() == null ? "" : bo.getPositionName());
      cell16.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell18 = row.createCell(i++);
      cell18.setCellValue(bo.getLength() == null ? "" : String.format("%.5f", bo.getLength()));
      cell18.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

      Cell cell19 = row.createCell(i++);
      cell19.setCellValue(bo.getWidth() == null ? "" : String.format("%.5f", bo.getWidth()));
      cell19.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

      Cell cell20 = row.createCell(i++);
      cell20.setCellValue(bo.getHeight() == null ? "" : String.format("%.5f", bo.getHeight()));
      cell20.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

      Cell cell21 = row.createCell(i++);
      cell21.setCellValue(bo.getHeightestBuilding() == null ? "" : String.format("%.5f", bo.getHeightestBuilding()));
      cell21.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

//      longitude
      Cell cell22 = row.createCell(i++);
      cell22.setCellValue(bo.getLongitude() == null ? "" : String.format("%.5f", bo.getLongitude()));
      cell22.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

//      latitude
      Cell cell23 = row.createCell(i++);
      cell23.setCellValue(bo.getLatitude() == null ? "" : String.format("%.5f", bo.getLatitude()));
      cell23.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

      Cell cell24 = row.createCell(i++);
      cell24.setCellValue(bo.getAuditTypeName() == null ? "" : bo.getAuditTypeName());
      cell24.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell25 = row.createCell(i++);
      cell25.setCellValue(bo.getAuditStatusName() == null ? "" : bo.getAuditStatusName());
      cell25.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

      Cell cell26 = row.createCell(i++);
      cell26.setCellValue(bo.getNote() == null ? "" : bo.getNote());
      cell26.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
    }
//  house station type
    List<ViewCatItemBO> listHouseStationType = transmissionDao.findCatItemByCategoryId("HOUSE_STATION_TYPE");
    XSSFSheet sheetHouseStationType = workbook.getSheetAt(1);

    index = 1;
    rowNum = 2;
    for (ViewCatItemBO bo : listHouseStationType) {
      int i = 0;
      Row row = sheetHouseStationType.createRow(rowNum++);
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

//  station type
    List<ViewCatItemBO> listStationType = transmissionDao.findCatItemByCategoryId("STATION_TYPE");
    XSSFSheet sheetStationType = workbook.getSheetAt(2);

    index = 1;
    rowNum = 2;
    for (ViewCatItemBO bo : listStationType) {
      int i = 0;
      Row row = sheetStationType.createRow(rowNum++);
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

    //  station feature
    List<ViewCatItemBO> listStationFeature = transmissionDao.findCatItemByCategoryId("STATION_FEATURE");
    XSSFSheet sheetStationFeature = workbook.getSheetAt(3);

    index = 1;
    rowNum = 2;
    for (ViewCatItemBO bo : listStationFeature) {
      int i = 0;
      Row row = sheetStationFeature.createRow(rowNum++);
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

    //  station dept
    CatDepartmentEntity departmentEntity = new CatDepartmentEntity();
    FormResult formResult = transmissionDao.findDepartment(departmentEntity, userId);
    XSSFSheet sheetDept = workbook.getSheetAt(4);
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

    //  station owner
    List<ViewCatItemBO> listOwner = transmissionDao.findCatItemByCategoryId("CAT_OWNER");
    XSSFSheet sheetOwner = workbook.getSheetAt(5);

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

    //  station location
    CatLocationBO locationEntity = new CatLocationBO();
    FormResult formResultLocation = transmissionDao.findCatLocation(locationEntity, userId);
    XSSFSheet sheetLocation = workbook.getSheetAt(6);
    List<ViewCatLocationBO> listLocation = (List<ViewCatLocationBO>) formResultLocation.getListData();
    index = 1;
    rowNum = 2;
    for (ViewCatLocationBO bo : listLocation) {
      int i = 0;
      Row row = sheetLocation.createRow(rowNum++);
      Cell cell0 = row.createCell(i++);
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
  public String importStation(MultipartFile file, String langCode, HttpServletRequest request) throws IOException, ClassNotFoundException {
    Long userId = CommonUtil.getUserId(request);
    List<InfraStationDTO> datas;
    InfraStationDTO flagList;
    try {
//      check header is changed
      InfraStationDTO header = ExcelDataUltils.getHeaderList(file, new InfraStationDTO());
      if (!messageSource.checkEqualHeader(header, langCode)) {
        return "template-error";
      }
      datas = (List<InfraStationDTO>) ExcelDataUltils.getListInExcel(file, new InfraStationDTO());
      flagList = ExcelDataUltils.getFlagList(file, new InfraStationDTO());
    } catch (Exception e) {
      log.error("Exception", e);
      return "template-error";
    }

    int successRecord = 0;
    int errRecord = 0;

    Map<Long, String> mapObj = new HashMap<>();
//    get index for err mapObj
    long index = 0;
    for (InfraStationDTO dto : datas) {
      Map<String, String> errData = new HashMap<>();
      InfraStationsBO entity = new InfraStationsBO();

      if (CommonUtils.isNullOrEmpty(dto.getStationCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.code", langCode));
      } else if (dto.getStationCode().length() > 30) {
        ExcelDataUltils.addMessage(errData, "err.maxLength.30", messageSource.getMessage("station.code", langCode));
      } else {
        String codeRegex = "[a-zA-Z0-9-]+";
        if (!dto.getStationCode().matches(codeRegex)) {
          ExcelDataUltils.addMessage(errData, "err.stationCodeFormat", messageSource.getMessage("station.code", langCode));
        } else {
          dto.setStationCode(dto.getStationCode().toUpperCase());
          boolean isExitCode = transmissionService.checkExitStationCode(dto.getStationCode(), request);
          if (isExitCode) {
            ExcelDataUltils.addMessage(errData, "err.exits", messageSource.getMessage("station.code", langCode));
          } else {
            entity.setStationCode(dto.getStationCode());
          }
        }
      }

      if (CommonUtils.isNullOrEmpty(dto.getDeptCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.dep", langCode));
      } else {
        if (!transmissionDao.checkExitsDept(dto.getDeptCode())) {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.dep", langCode));
        } else {
          ViewCatDepartmentBO bo = transmissionDao.findDepartmentByCode(dto.getDeptCode(), userId);
          if (bo == null) {
            ExcelDataUltils.addMessage(errData, "err.noscope", messageSource.getMessage("station.dep", langCode));
          } else {
            entity.setDeptId(bo.getDeptId());
          }
        }
      }

      if (CommonUtils.isNullOrEmpty(dto.getLocationCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.location.level5", langCode));
      } else {
        if (!transmissionDao.checkExitsLocation(dto.getLocationCode())) {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.location.level5", langCode));
        } else {
          ViewCatLocationBO bo = transmissionDao.findLocationByCode(dto.getLocationCode(), userId);
          if (bo == null) {
            ExcelDataUltils.addMessage(errData, "err.noscope", messageSource.getMessage("station.location.level5", langCode));
          } else {
            entity.setLocationId(bo.getLocationId());
          }
        }
      }

      if (dto.getHouseOwnerName().length() > 100) {
        ExcelDataUltils.addMessage(errData, "err.maxLength.100", messageSource.getMessage("station.houseOwnerName", langCode));
      } else {
        entity.setHouseOwnerName(dto.getHouseOwnerName());
      }
      if (!CommonUtils.isNullOrEmpty(dto.getHouseOwnerPhone())) {
        if (dto.getHouseOwnerPhone().length() > 20) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.20", messageSource.getMessage("station.houseOwnerPhone", langCode));
        } else {
          String regex = "[0-9]+";
          if (!dto.getHouseOwnerPhone().matches(regex)) {
            ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.houseOwnerPhone", langCode));
          } else {
            entity.setHouseOwnerPhone(dto.getHouseOwnerPhone());
          }
        }
      } else {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.houseOwnerPhone", langCode));
      }

      if (dto.getAddress().length() > 500) {
        ExcelDataUltils.addMessage(errData, "err.maxLength.500", messageSource.getMessage("station.address", langCode));
      } else {
        entity.setAddress(dto.getAddress());
      }

      if (!CommonUtils.isNullOrEmpty(dto.getOwnerCode())) {
        ViewCatItemBO ownerBo = transmissionDao.findCatItemByItemCodeAndCaregoryCode(dto.getOwnerCode(), "CAT_OWNER");
        if (ownerBo != null) {
          entity.setOwnerId(ownerBo.getItemId());
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.ownerName", langCode));
        }
      } else {
        ExcelDataUltils.addMessage(errData, "err.choose", messageSource.getMessage("station.ownerName", langCode));
      }
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

      Map<String, Long> listStatus = new WeakHashMap<>();
      listStatus.put(messageSource.getMessage("station.status.one", langCode), 1L);
      listStatus.put(messageSource.getMessage("station.status.two", langCode), 2L);
      listStatus.put(messageSource.getMessage("station.status.three", langCode), 3L);
      listStatus.put(messageSource.getMessage("station.status.four", langCode), 4L);
      listStatus.put(messageSource.getMessage("station.status.fine", langCode), 5L);
      listStatus.put(messageSource.getMessage("station.status.six", langCode), 6L);

      if (!CommonUtils.isNullOrEmpty(dto.getStatusName())) {
        if (listStatus.containsKey(dto.getStatusName())) {
          entity.setStatus(listStatus.get(dto.getStatusName()));
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.status", langCode));
        }
      }
      if (!CommonUtils.isNullOrEmpty(dto.getHouseStationTypeName())) {
        ViewCatItemBO houseStationTypeBo = transmissionDao.findCatItemByItemCodeAndCaregoryCode(dto.getHouseStationTypeName(), "HOUSE_STATION_TYPE");
        if (houseStationTypeBo != null) {
          entity.setHouseStationTypeId(houseStationTypeBo.getItemId());
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.houseStationType", langCode));
        }
      }

      if (CommonUtils.isNullOrEmpty(dto.getStationTypeName())) {
        ExcelDataUltils.addMessage(errData, "err.choose", messageSource.getMessage("station.stationType", langCode));
      } else {

        ViewCatItemBO stationTypeBo = transmissionDao.findCatItemByItemCodeAndCaregoryCode(dto.getStationTypeName(), "STATION_TYPE");
        if (stationTypeBo != null) {
          entity.setStationTypeId(stationTypeBo.getItemId());
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.stationType", langCode));
        }
      }

      if (CommonUtils.isNullOrEmpty(dto.getStationFeatureName())) {
        ExcelDataUltils.addMessage(errData, "err.choose", messageSource.getMessage("station.stationFeature", langCode));
      } else {
        ViewCatItemBO stationFeatureBo = transmissionDao.findCatItemByItemCodeAndCaregoryCode(dto.getStationFeatureName(), "STATION_FEATURE");
        if (stationFeatureBo != null) {
          entity.setStationFeatureId(stationFeatureBo.getItemId());
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.stationFeature", langCode));
        }
      }

      Map<String, Long> listBackup = new WeakHashMap<>();
      listBackup.put(messageSource.getMessage("station.backup.status.zero", langCode), 0L);
      listBackup.put(messageSource.getMessage("station.backup.status.one", langCode), 1L);

      if (!CommonUtils.isNullOrEmpty(dto.getBackupStatusName())) {
        if (listBackup.containsKey(dto.getBackupStatusName())) {
          entity.setBackupStatus(listBackup.get(dto.getBackupStatusName()));
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.backupStatus", langCode));
        }
      }

      Map<String, Long> listPosition = new WeakHashMap<>();
      listPosition.put(messageSource.getMessage("station.position.one", langCode), 1L);
      listPosition.put(messageSource.getMessage("station.position.two", langCode), 2L);
      listPosition.put(messageSource.getMessage("station.position.three", langCode), 3L);

      if (!CommonUtils.isNullOrEmpty(dto.getPositionName())) {
        if (listPosition.containsKey(dto.getPositionName())) {
          entity.setPosition(listPosition.get(dto.getPositionName()));
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.position", langCode));
        }
      }
      if (!CommonUtils.isNullOrEmpty(dto.getLengthStr())) {
        if (dto.getLengthStr().length() > 10) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.10", messageSource.getMessage("station.length", langCode));
        } else {
          try {
            String regex = "[0-9-.]+";
            if (dto.getLengthStr().matches(regex)) {
              if (StringUtils.countMatches(dto.getLengthStr(), ".") > 1) {
                ExcelDataUltils.addMessage(errData, "err.hasPre.twopoint", messageSource.getMessage("station.length", langCode));
              } else if (StringUtils.countMatches(dto.getLengthStr(), "-") > 1) {
                ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen", messageSource.getMessage("station.length", langCode));
              } else {
                Double length = Double.parseDouble(dto.getLengthStr());
                BigDecimal lengthDecimal = BigDecimal.valueOf(length);
                if (lengthDecimal.intValue() < 0) {
                  ExcelDataUltils.addMessage(errData, "err.negative", messageSource.getMessage("station.length", langCode));
                } else {
                  entity.setLength(lengthDecimal);
                }
              }

            } else {
              ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.length", langCode));
            }


          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.length", langCode));
          }
        }
      }

      if (!CommonUtils.isNullOrEmpty(dto.getWidthStr())) {
        if (dto.getWidthStr().length() > 10) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.10", messageSource.getMessage("station.width", langCode));
        } else {
          try {
            Double width = Double.parseDouble(dto.getWidthStr());
            BigDecimal widthDecimal = BigDecimal.valueOf(width);
            if (widthDecimal.intValue() < 0) {
              ExcelDataUltils.addMessage(errData, "err.negative", messageSource.getMessage("station.width", langCode));
            } else {
              entity.setWidth(widthDecimal);
            }
          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.width", langCode));
          }
        }
      }

      if (!CommonUtils.isNullOrEmpty(dto.getHeightStr())) {
        if (dto.getHeightStr().length() > 10) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.10", messageSource.getMessage("station.height", langCode));
        } else {
          try {
            Double height = Double.parseDouble(dto.getHeightStr());
            BigDecimal heightDecimal = BigDecimal.valueOf(height);
            if (heightDecimal.intValue() < 0) {
              ExcelDataUltils.addMessage(errData, "err.negative", messageSource.getMessage("station.height", langCode));
            } else {
              entity.setHeight(heightDecimal);
            }
          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.height", langCode));
          }
        }
      }
      if (!CommonUtils.isNullOrEmpty(dto.getHeightestBuildingStr())) {
        try {
          Double heightest = Double.parseDouble(dto.getHeightestBuildingStr());
          BigDecimal heightestDecimal = BigDecimal.valueOf(heightest);
          if (heightestDecimal.intValue() < 0) {
            ExcelDataUltils.addMessage(errData, "err.negative", messageSource.getMessage("station.heightestBuilding", langCode));
          } else {
            entity.setHeightestBuilding(heightestDecimal);
          }
        } catch (Exception e) {
          ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.heightestBuilding", langCode));
        }
      }

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
              DecimalFormat df = new DecimalFormat("#.######");
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
              DecimalFormat df = new DecimalFormat("#.######");
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

      Map<String, Long> listAuditType = new WeakHashMap<>();
      listAuditType.put(messageSource.getMessage("station.audit.type.one", langCode), 1L);
      listAuditType.put(messageSource.getMessage("station.audit.type.two", langCode), 2L);
      listAuditType.put(messageSource.getMessage("station.audit.type.three", langCode), 3L);

      if (!CommonUtils.isNullOrEmpty(dto.getAuditTypeName())) {
        if (listAuditType.containsKey(dto.getAuditTypeName())) {
          entity.setAuditType(listAuditType.get(dto.getAuditTypeName()));
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.position", langCode));
        }
      }

      if (entity.getAuditType() != null) {
        if (entity.getAuditType() == 3L) {
          entity.setAuditStatus(10L);
          dto.setAuditStatusName(messageSource.getMessage("station.audit.status.ten"));
        } else {
          entity.setAuditStatus(0L);
          dto.setAuditStatusName(messageSource.getMessage("station.audit.status.zero"));
        }
      }

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
        PGInfraStationDto pgDto = new PGInfraStationDto();
        Long result = infraStationDao.saveStation(entity);
        if (result != 0) {
          pgDto.setStation_id(result);
          pgDto.setStation_code(entity.getStationCode());
          pgDto.setDept_id(entity.getDeptId());
          pgDto.setLocation_id(entity.getLocationId());
          pgDto.setStatus(entity.getStatus());
          pgDto.setLongitude(entity.getLongitude());
          pgDto.setLatitude(entity.getLatitude());
          pgDto.setAddress(entity.getAddress());
          pgDto.setOwner_id(entity.getOwnerId());
          pgDto.setUpdate(Boolean.FALSE);
          pgInfraStationDao.insertOrUpdate(pgDto);
          Response<FormResult> response = new Response<>();
          response.setStatus(HttpStatus.OK.toString());
        }
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


//      get err list by left to right;
      for (int i = errList.size() - 1; i >= 0; i--) {
        rs.append(errList.get(i));
      }

      if (CommonUtils.isNullOrEmpty(rs.toString())) {
        rs.append("OK");
      }
      mapObj.put(index++, rs.toString());

    }

    String savePath = ExcelDataUltils.writeResultExcel(file, new InfraStationDTO(), mapObj, messageSource.getMessage("station.result.import.fileName", langCode), datas);
    return savePath + "+" + successRecord + "+" + errRecord;
  }

  @Override
  public String editStation(MultipartFile file, String langCode, HttpServletRequest request) throws IOException, ClassNotFoundException {
    Long userId = CommonUtil.getUserId(request);
    List<InfraStationDTO> datas = (List<InfraStationDTO>) ExcelDataUltils.getListInExcel(file, new InfraStationDTO());
    InfraStationDTO flagList = ExcelDataUltils.getFlagList(file, new InfraStationDTO());
    int successRecord = 0;
    int errRecord = 0;

    Map<Long, String> mapObj = new HashMap<>();

//    get index for err mapObj
    long index = 0;
    for (InfraStationDTO dto : datas) {
      Map<String, String> errData = new HashMap<>();
      InfraStationsBO entity = new InfraStationsBO();

      if (CommonUtils.isNullOrEmpty(dto.getStationCode())) {
        ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.code", langCode));
      } else if (dto.getStationCode().length() > 30) {
        ExcelDataUltils.addMessage(errData, "err.maxLength.30", messageSource.getMessage("station.code", langCode));
      } else {
        String codeRegex = "[a-zA-Z0-9-]+";
        if (!dto.getStationCode().matches(codeRegex)) {
          ExcelDataUltils.addMessage(errData, "err.stationCodeFormat", messageSource.getMessage("station.code", langCode));
        } else {
          dto.setStationCode(dto.getStationCode().toUpperCase());
          if (transmissionService.checkExitStationCode(dto.getStationCode(), request)) {
            entity = infraStationDao.findStationByCode(dto.getStationCode(), userId);
            if (entity == null) {
              ExcelDataUltils.addMessage(errData, "err.noscope", messageSource.getMessage("station.code", langCode));
              entity = new InfraStationsBO();
            } else {
              entity.setStationCode(dto.getStationCode());
            }
          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.code", langCode));
            entity = new InfraStationsBO();
          }

        }
      }
      if (flagList.getDeptCode().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getDeptCode())) {
          ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.dep", langCode));
        } else {
          if (!transmissionDao.checkExitsDept(dto.getDeptCode())) {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.dep", langCode));
          } else {
            ViewCatDepartmentBO bo = transmissionDao.findDepartmentByCode(dto.getDeptCode(), userId);
            if (bo == null) {
              ExcelDataUltils.addMessage(errData, "err.noscope", messageSource.getMessage("station.dep", langCode));
            } else {
              entity.setDeptId(bo.getDeptId());
            }
          }
        }
      }
      if (flagList.getLocationCode().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getLocationCode())) {
          ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.location.level5", langCode));
        } else {
          if (!transmissionDao.checkExitsLocation(dto.getLocationCode())) {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.location.level5", langCode));
          } else {
            ViewCatLocationBO bo = transmissionDao.findLocationByCode(dto.getLocationCode(), userId);
            if (bo == null) {
              ExcelDataUltils.addMessage(errData, "err.noscope", messageSource.getMessage("station.location.level5", langCode));
            } else {
              entity.setLocationId(bo.getLocationId());
            }
          }
        }
      }
      if (flagList.getHouseOwnerName().equals("Y")) {
        if (dto.getHouseOwnerName().length() > 100) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.100", messageSource.getMessage("station.houseOwnerName", langCode));
        } else {
          entity.setHouseOwnerName(dto.getHouseOwnerName());
        }
      }
      if (flagList.getHouseOwnerPhone().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getHouseOwnerPhone())) {
          if (dto.getHouseOwnerPhone().length() > 20) {
            ExcelDataUltils.addMessage(errData, "err.maxLength.20", messageSource.getMessage("station.houseOwnerPhone", langCode));
          } else {
            String regex = "[0-9]+";
            if (!dto.getHouseOwnerPhone().matches(regex)) {
              ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.houseOwnerPhone", langCode));
            } else {

              entity.setHouseOwnerPhone(dto.getHouseOwnerPhone());

            }
          }
        } else {
          ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.houseOwnerPhone", langCode));
        }
      }
      if (flagList.getAddress().equals("Y")) {
        if (dto.getAddress().length() > 500) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.500", messageSource.getMessage("station.address", langCode));
        } else {
          entity.setAddress(dto.getAddress());
        }
      }
      if (flagList.getOwnerCode().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getOwnerCode())) {
          ViewCatItemBO ownerBo = transmissionDao.findCatItemByItemCodeAndCaregoryCode(dto.getOwnerCode(), "CAT_OWNER");
          if (ownerBo != null) {

            entity.setOwnerId(ownerBo.getItemId());

          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.ownerName", langCode));
          }
        } else {
          ExcelDataUltils.addMessage(errData, "err.choose", messageSource.getMessage("station.ownerName", langCode));
        }
      }
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

      Map<String, Long> listStatus = new WeakHashMap<>();
      listStatus.put(messageSource.getMessage("station.status.one", langCode), 1L);
      listStatus.put(messageSource.getMessage("station.status.two", langCode), 2L);
      listStatus.put(messageSource.getMessage("station.status.three", langCode), 3L);
      listStatus.put(messageSource.getMessage("station.status.four", langCode), 4L);
      listStatus.put(messageSource.getMessage("station.status.fine", langCode), 5L);
      listStatus.put(messageSource.getMessage("station.status.six", langCode), 6L);
      if (flagList.getStatusName().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getStatusName())) {
          if (listStatus.containsKey(dto.getStatusName())) {
            entity.setStatus(listStatus.get(dto.getStatusName()));
          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.status", langCode));
          }
        }
      }
      if (!CommonUtils.isNullOrEmpty(dto.getHouseStationTypeName())) {
        ViewCatItemBO houseStationTypeBo = transmissionDao.findCatItemByItemCodeAndCaregoryCode(dto.getHouseStationTypeName(), "HOUSE_STATION_TYPE");
        if (houseStationTypeBo != null) {
          if (flagList.getHouseStationTypeName().equals("Y")) {
            entity.setHouseStationTypeId(houseStationTypeBo.getItemId());
          }
        } else {
          ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.houseStationType", langCode));
        }
      }
      if (flagList.getStationTypeName().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getStationTypeName())) {
          ExcelDataUltils.addMessage(errData, "err.choose", messageSource.getMessage("station.stationType", langCode));
        } else {
          ViewCatItemBO stationTypeBo = transmissionDao.findCatItemByItemCodeAndCaregoryCode(dto.getStationTypeName(), "STATION_TYPE");
          if (stationTypeBo != null) {
            entity.setStationTypeId(stationTypeBo.getItemId());
          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.stationType", langCode));
          }
        }
      }

      if (flagList.getStationFeatureName().equals("Y")) {
        if (CommonUtils.isNullOrEmpty(dto.getStationFeatureName())) {
          ExcelDataUltils.addMessage(errData, "err.choose", messageSource.getMessage("station.stationFeature", langCode));
        } else {
          ViewCatItemBO stationFeatureBo = transmissionDao.findCatItemByItemCodeAndCaregoryCode(dto.getStationFeatureName(), "STATION_FEATURE");
          if (stationFeatureBo != null) {
            entity.setStationFeatureId(stationFeatureBo.getItemId());
          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.stationFeature", langCode));
          }
        }
      }

      Map<String, Long> listBackup = new WeakHashMap<>();
      listBackup.put(messageSource.getMessage("station.backup.status.zero", langCode), 0L);
      listBackup.put(messageSource.getMessage("station.backup.status.one", langCode), 1L);
      if (flagList.getBackupStatusName().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getBackupStatusName())) {
          if (listBackup.containsKey(dto.getBackupStatusName())) {
            entity.setBackupStatus(listBackup.get(dto.getBackupStatusName()));
          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.backupStatus", langCode));
          }
        }
      }
      Map<String, Long> listPosition = new WeakHashMap<>();
      listPosition.put(messageSource.getMessage("station.position.one", langCode), 1L);
      listPosition.put(messageSource.getMessage("station.position.two", langCode), 2L);
      listPosition.put(messageSource.getMessage("station.position.three", langCode), 3L);
      if (flagList.getBackupStatusName().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getPositionName())) {
          if (listPosition.containsKey(dto.getPositionName())) {
            entity.setPosition(listPosition.get(dto.getPositionName()));
          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.position", langCode));
          }
        }
      }
      if (!CommonUtils.isNullOrEmpty(dto.getLengthStr())) {
        if (dto.getLengthStr().length() > 10) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.10", messageSource.getMessage("station.length", langCode));
        } else {
          try {
            String regex = "[0-9-.]+";
            if (dto.getLengthStr().matches(regex)) {
              if (StringUtils.countMatches(dto.getLengthStr(), ".") > 1) {
                ExcelDataUltils.addMessage(errData, "err.hasPre.twopoint", messageSource.getMessage("station.length", langCode));
              } else if (StringUtils.countMatches(dto.getLengthStr(), "-") > 1) {
                ExcelDataUltils.addMessage(errData, "err.hasPre.twohyphen", messageSource.getMessage("station.length", langCode));
              } else {
                Double length = Double.parseDouble(dto.getLengthStr());
                BigDecimal lengthDecimal = BigDecimal.valueOf(length);
                if (lengthDecimal.intValue() < 0) {
                  ExcelDataUltils.addMessage(errData, "err.negative", messageSource.getMessage("station.length", langCode));
                } else {
                  if (flagList.getLengthStr().equals("Y")) {
                    entity.setLength(lengthDecimal);
                  }
                }
              }
            } else {
              ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.length", langCode));
            }
          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.length", langCode));
          }
        }
      }
      if (flagList.getWidthStr().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getWidthStr())) {
          if (dto.getWidthStr().length() > 10) {
            ExcelDataUltils.addMessage(errData, "err.maxLength.10", messageSource.getMessage("station.width", langCode));
          } else {
            try {
              Double width = Double.parseDouble(dto.getWidthStr());
              BigDecimal widthDecimal = BigDecimal.valueOf(width);
              if (widthDecimal.intValue() < 0) {
                ExcelDataUltils.addMessage(errData, "err.negative", messageSource.getMessage("station.width", langCode));
              } else {
                entity.setWidth(widthDecimal);
              }
            } catch (Exception e) {
              ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.width", langCode));
            }
          }
        }
      }
      if (flagList.getHeightStr().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getHeightStr())) {
          if (dto.getHeightStr().length() > 10) {
            ExcelDataUltils.addMessage(errData, "err.maxLength.10", messageSource.getMessage("station.height", langCode));
          } else {
            try {
              Double height = Double.parseDouble(dto.getHeightStr());
              BigDecimal heightDecimal = BigDecimal.valueOf(height);
              if (heightDecimal.intValue() < 0) {
                ExcelDataUltils.addMessage(errData, "err.negative", messageSource.getMessage("station.height", langCode));
              } else {
                entity.setHeight(heightDecimal);
              }
            } catch (Exception e) {
              ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.height", langCode));
            }
          }
        }
      }
      if (flagList.getHeightestBuildingStr().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getHeightestBuildingStr())) {
          try {
            Double heightest = Double.parseDouble(dto.getHeightestBuildingStr());
            BigDecimal heightestDecimal = BigDecimal.valueOf(heightest);
            if (heightestDecimal.intValue() < 0) {
              ExcelDataUltils.addMessage(errData, "err.negative", messageSource.getMessage("station.heightestBuilding", langCode));
            } else {
              entity.setHeightestBuilding(heightestDecimal);
            }
          } catch (Exception e) {
            ExcelDataUltils.addMessage(errData, "err.numberFormat", messageSource.getMessage("station.heightestBuilding", langCode));
          }
        }
      }
      if (flagList.getLongitudeStr().equals("Y")) {
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
                DecimalFormat df = new DecimalFormat("#.######");
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
      }
      if (flagList.getLatitudeStr().equals("Y")) {
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
                DecimalFormat df = new DecimalFormat("#.######");
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
      }

      Map<String, Long> listAuditType = new WeakHashMap<>();
      listAuditType.put(messageSource.getMessage("station.audit.type.one", langCode), 1L);
      listAuditType.put(messageSource.getMessage("station.audit.type.two", langCode), 2L);
      listAuditType.put(messageSource.getMessage("station.audit.type.three", langCode), 3L);

      if (flagList.getAuditTypeName().equals("Y")) {
        if (!CommonUtils.isNullOrEmpty(dto.getAuditTypeName())) {
          if (listAuditType.containsKey(dto.getAuditTypeName())) {
            entity.setAuditType(listAuditType.get(dto.getAuditTypeName()));
          } else {
            ExcelDataUltils.addMessage(errData, "err.incorrect", messageSource.getMessage("station.position", langCode));
          }
        }
      }
      if (entity.getAuditType() != null) {
        if (entity.getAuditType() == 3L) {
          entity.setAuditStatus(10L);
          dto.setAuditStatusName(messageSource.getMessage("station.audit.status.ten"));
        } else {
          entity.setAuditStatus(0L);
          dto.setAuditStatusName(messageSource.getMessage("station.audit.status.zero"));
        }
      }
      if (flagList.getNote().equals("Y")) {
        if (dto.getNote().length() > 500) {
          ExcelDataUltils.addMessage(errData, "err.maxLength.500", messageSource.getMessage("station.note", langCode));
        } else {
          entity.setNote(dto.getNote());
        }
      }

      if (entity.getLongitude() != null && entity.getLatitude() != null) {
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(new Coordinate(entity.getLongitude(), entity.getLongitude()));
        entity.setGeometry(point);
      }

      if (errData.isEmpty()) {
        PGInfraStationDto pgDto = new PGInfraStationDto();
        Long result = infraStationDao.saveStation(entity);
        if (result != 0) {
          pgDto.setStation_id(result);
          pgDto.setStation_code(entity.getStationCode());
          pgDto.setDept_id(entity.getDeptId());
          pgDto.setLocation_id(entity.getLocationId());
          pgDto.setStatus(entity.getStatus());
          pgDto.setLongitude(entity.getLongitude());
          pgDto.setLatitude(entity.getLatitude());
          pgDto.setAddress(entity.getAddress());
          pgDto.setOwner_id(entity.getOwnerId());
          pgDto.setUpdate(Boolean.TRUE);
          pgInfraStationDao.insertOrUpdate(pgDto);
          Response<FormResult> response = new Response<>();
          response.setStatus(HttpStatus.OK.toString());
        }
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
    String savePath = ExcelDataUltils.writeResultExcel(file, new InfraStationDTO(), mapObj, messageSource.getMessage("station.result.importEdit.fileName", langCode), datas);
    return savePath + "+" + successRecord + "+" + errRecord;
  }

  @Override
  public ResponseEntity<?> saveDocument(DocumentBO documentBO) {
    if (documentBO != null) {
      if (documentBO.getAttachFileType() == null
          || documentBO.getFileName() == null || documentBO.getDocumentId() == null) {
        return ResponseBase.createResponse("error", Constains.REQUIRED, 500);
      }
    }
    documentBO.setCreateTime(new Date());
    return ResponseBase.createResponse(infraStationDao.saveDocument(documentBO), Constains.SUCCESS, 200);
  }

  @Override
  public DocumentBO getDocument(Long stationId, int type) {
    return infraStationDao.getDocument(stationId, type);
  }


}
