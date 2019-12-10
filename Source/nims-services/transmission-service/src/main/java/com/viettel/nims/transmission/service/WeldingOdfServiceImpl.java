package com.viettel.nims.transmission.service;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.commom.MessageResource;
import com.viettel.nims.transmission.dao.WeldingOdfDao;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.ViewWeldingOdfBO;
import com.viettel.nims.transmission.utils.Constains;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by BinhNV on 23/08/2019.
 */
@Slf4j
@Service
@Transactional(transactionManager="globalTransactionManager")
public class WeldingOdfServiceImpl implements WeldingOdfService{

  private WeldingOdfDao weldingOdfDao;

  @Autowired
  MessageResource messageSource;

  public WeldingOdfServiceImpl(WeldingOdfDao weldingOdfDao) {
    this.weldingOdfDao = weldingOdfDao;
  }

  @Override
  public ResponseEntity<?> getWeldingOdfList(Long id) {
    try {
      List<ViewWeldingOdfBO> weldingOdfList = weldingOdfDao.selectAllWeldingOdf(id);
      FormResult result = new FormResult();
      result.setListData(weldingOdfList);
      result.setTotalRecords((long) weldingOdfList.size());
      Response<FormResult> response = new Response<>();
      response.setContent(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception", e);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> getOdfCode(Long id) {
    try {
      String result = weldingOdfDao.selectOdfCodeById(id);
      Response<String> response = new Response<>();
      response.setContent(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception", e);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
  }

  @Override
  public ResponseEntity<?> getCouplers(Long id) {
    try {
      List result = weldingOdfDao.selectCouplerNos(id);
      Response<List> response = new Response<>();
      response.setContent(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception", e);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> getLines(Long id) {
    try {
      List result = weldingOdfDao.selectLineNos(id);
      Response<List> response = new Response<>();
      response.setContent(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception", e);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> getCableCodes(Long id) {
    try {
      List<CableBO> result = weldingOdfDao.selectCableCodes(id);
      Response<List<CableBO>> response = new Response<>();
      response.setContent(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception", e);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> getDestOdfs(Long id) {
    try {
      List<DestOdfBO> result = weldingOdfDao.selectDestOdfs(id);
      Response<List<DestOdfBO>> response = new Response<>();
      response.setContent(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception", e);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> getJointCouplers(Long id) {
    try {
      List<JointCouplersBO> result = weldingOdfDao.selectJointCouplerNos(id);
      Response<List<JointCouplersBO>> response = new Response<>();
      response.setContent(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Exception", e);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public JSONObject deleteWeldingOdfs(List<WeldingOdfBO> weldingOdfs) {
    JSONObject data = new JSONObject();
    int result = weldingOdfDao.deleteWeldingOdfs(weldingOdfs);
    if (result > 0 && result == weldingOdfs.size()) {
      data.put("code", 1);
    } else {
      data.put("code", 0);
    }
    return data;
  }

  @Override
  public JSONObject saveWeldingOdf(WeldingOdfBO weldingOdf) {
    JSONObject data = new JSONObject();
    try {
      weldingOdfDao.insertWeldingOdf(weldingOdf);
      data.put("code", 1);
    } catch (Exception e) {
      data.put("code", 0);
      log.error("Exception", e);
      return data;
    }
    return data;
  }

  @Override
  public JSONObject updateWeldingOdf(WeldingOdfBO params) {
    JSONObject data = new JSONObject();
    try {
      ViewWeldingOdfBO weldingODf = weldingOdfDao.findSelectedWeldingOdf(params.getOdfId(), params.getCouplerNo(),
          params.getOdfConnectType());
      if (weldingODf != null){
        weldingOdfDao.updateWeldingOdf(params);
        data.put("code", 1);
      } else {
        data.put("code", 0);
      }
    } catch (Exception e) {
      data.put("code", 0);
      log.error("Exception", e);
      return data;
    }
    return data;
  }

  @Override
  public ByteArrayInputStream exportData(HashMap<String, Object> paramsMap, String langCode) {
    FileInputStream templateInputStream = null;
    Workbook workbook;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      InputStream inputStream =  new ClassPathResource("/templates/EXPORT_HANNOIDAUNOIODF.xlsx").getInputStream();
      workbook = new XSSFWorkbook(inputStream);
      Sheet sheet = workbook.getSheet("Han_dau_noi_ODF") ;
      Row rowReport;
      int rowNum = 5;
      rowReport = sheet.getRow(2);
      Cell cell = rowReport.getCell(7);
      cell.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

      Long id = Long.parseLong(paramsMap.get("odfId").toString());
      List<ViewWeldingOdfBO> data;
      if (Objects.isNull(paramsMap.get("couplers"))){
        data = weldingOdfDao.selectAllWeldingOdf(id);
      } else {
        ArrayList couplers = (ArrayList) paramsMap.get("couplers");
        List<Long> couplerList = new ArrayList<>();
        for (Object coupler: couplers ) {
          couplerList.add(Long.parseLong(coupler.toString()));
        }
        data = weldingOdfDao.selectWeldingOdfs(id, couplerList);
      }

      if (data.size() > 0) {
        for (ViewWeldingOdfBO weldingOdf : data) {
          int colNum = 0;
          rowReport = sheet.createRow(rowNum);
          rowReport.createCell(colNum).setCellValue(rowNum - 4);
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getOdfCode());
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getCouplerNo());
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getCableCode());
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getLineNo());
          this.setCell(rowReport, colNum);
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getDestOdfCode());
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getDestCouplerNo());
          this.setCell(rowReport, colNum);
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getCreateUser());
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getAttenuation());
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getCreateDate());
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getOdfConnectType());
          rowReport.createCell(++colNum).setCellValue(weldingOdf.getNote());
          rowNum++;
        }
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(7);
        sheet.autoSizeColumn(11);
      } else {
        int colNum = 0;
        rowReport = sheet.createRow(rowNum);
        rowReport.createCell(colNum).setCellValue(Constains.WELD_ODF_NO_DATA);
        sheet.addMergedRegion(new CellRangeAddress(2,2,0,11 ));
      }

      workbook.write(byteArrayOutputStream);
      return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    } catch (Exception e) {
      // Throw exception
      log.error("Exception", e);
    } finally {
      IOUtils.closeQuietly(templateInputStream);
    }
    return null;
  }

  private void setCell(Row rowReport, int colNum){
    CellStyle cellStyle = rowReport.getCell(colNum).getCellStyle();
    cellStyle.setAlignment(HorizontalAlignment.RIGHT);
    rowReport.getCell(colNum).setCellStyle(cellStyle);
  }
}
