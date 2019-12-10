package com.viettel.nims.commons.controller;

import com.viettel.nims.commons.controller.base.BaseController;
import com.viettel.nims.commons.model.CatDepartmentBO;
import com.viettel.nims.commons.model.SysUserBO;
import com.viettel.nims.commons.service.SysUserServiceImpl;
import com.viettel.nims.commons.utils.ExcelWriterUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@RestController
@RequestMapping("${app.prefix}/user")
public class SysUserController extends BaseController<SysUserBO, Long> {

  private static final int sizeToCommit = 3000;
  private static final String OK = "OK";


  public SysUserController(SysUserServiceImpl genericDao) {
    super(genericDao);
  }

  @PostMapping(value = "/import")
  public @ResponseBody
  HttpEntity<byte[]> onImport(@RequestParam(name = "file") MultipartFile file,
                              @RequestParam(name = "username") String username, @RequestParam(name = "ip") String ip) {
    String pathOut = "";
    byte [] body = new byte[0];
    try {
      String importFileFileName = file.getOriginalFilename();
      Workbook wb = null;
      if (importFileFileName != null) {
        if (importFileFileName.endsWith("xls")) {
          wb = new HSSFWorkbook(file.getInputStream());
        } else if (importFileFileName.endsWith("xlsx")) {
          wb = new XSSFWorkbook(file.getInputStream());
        }
      }
      assert wb != null;
      wb.setMissingCellPolicy(HSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK);
      Sheet dataSheet = wb.getSheetAt(0);
      if (dataSheet == null) {
        return getHttpEntityFail(body, 0L, "import.fileTemplate.error.notExistSheet");
//        return buildResultJson(0, "import.fileTemplate.error.notExistSheet", null).toString();
      }
      int col = dataSheet.getRow(0).getPhysicalNumberOfCells();
      if(col != 6){
        return getHttpEntityFail(body, 0L, "import.fileTemplate.error.fileInvalid");
      }
      List<SysUserBO> dataList = readSheetData(dataSheet);
      if (dataList == null || dataList.isEmpty()) {
        log.info("Data Empty");
        return getHttpEntityFail(body, 0L, "datatable.emptyMessage");
//        return buildResultJson(0, "datatable.emptyMessage", null).toString();
      }
      int rowSheet = 1;
      Map<Integer, String> mapResult = new HashMap<>();
      List<SysUserBO> totalStations = new ArrayList<>();
      for (int i = 0; i < dataList.size(); i++) {
        SysUserBO item = dataList.get(i);
        String validate = validateForm(item);
        if (OK.equals(validate)) {
          Map<String, Object> filters = new HashMap<>();
          filters.put("username-EXAC", item.getUsername());
          try {
            List<SysUserBO> objects = genericDao.findList(-1, -1, filters, null, null);
            SysUserBO obj;
            if (objects != null && !objects.isEmpty()) {
              obj = objects.get(0);
            } else {
              obj = new SysUserBO();
              obj.setUsername(item.getUsername());
            }
            obj.setFullname(item.getFullname());
            obj.setEmail(item.getEmail());
            obj.setPhone(item.getPhone());
            obj.setDeptId(item.getDeptId());
            obj.setUserRole(item.getUserRole());
            obj.setStatus(item.getStatus());
            totalStations.add(obj);
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
          mapResult.put(i, "Success");
        } else {
          mapResult.put(i, validate);
        }
        if (totalStations.size() == sizeToCommit) {
          genericDao.saveOrUpdate(totalStations);
          totalStations.clear();
        }
      }
      if (!totalStations.isEmpty()) {
        genericDao.saveOrUpdate(totalStations);
        totalStations.clear();
      }
      ExcelWriterUtils excelWriterUtils = new ExcelWriterUtils();
      Row row;
      row = excelWriterUtils.getOrCreateRow(dataSheet, 0);
      int resultColumn = 6;
      row.getCell(resultColumn).setCellValue("Result");
      row.getCell(resultColumn).setCellStyle(row.getCell(resultColumn - 1).getCellStyle());
      for (Map.Entry<Integer, String> result : mapResult.entrySet()) {
        row = excelWriterUtils.getOrCreateRow(dataSheet, result.getKey() + rowSheet);
        row.getCell(resultColumn).setCellValue(result.getValue());
        row.getCell(resultColumn).setCellStyle(row.getCell(resultColumn - 1).getCellStyle());
      }
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
      String strCurTimeExp = dateFormat.format(new Date());

      String fileName = strCurTimeExp + "_Result_" + importFileFileName;
//      File tmpTemplateFile = new ClassPathResource("report_out").getFile();
//      String path = tmpTemplateFile.getPath();
//      File fileNew = new File(path + File.separator + fileName);
//      fileNew.createNewFile();
      try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
        wb.write(outputStream);
        outputStream.flush();
        byte[] documentContent = outputStream.toByteArray();
        return getHttpEntity(documentContent, "vnd.ms-excel", fileName);
      }
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    }
    return getHttpEntityFail(body, 0L, pathOut);
  }

  public HttpEntity<byte[]> getHttpEntityFail(byte[] document, Object status, String message) {
    HttpHeaders header = new HttpHeaders();
    header.setContentType(new MediaType("text", "plain"));
    header.add("status", status.toString());
    header.add("message", message);
    return new HttpEntity<>(document, header);
  }


  public HttpEntity<byte[]> getHttpEntity(byte[] document, String fileName, String subType) throws IOException {
    HttpHeaders header = new HttpHeaders();
    header.setContentType(new MediaType("application", subType));
    header.set("Content-Disposition", "attachment; filename=" + fileName);
    header.setContentLength(document.length);
    header.add("status", "1");
    header.add("message", "success");
    return new HttpEntity<>(document, header);
  }

  public String validateForm(SysUserBO form) {
    if (form == null) {
      return "Form is NULL";
    } else {
      if (form.getUsername() == null || "".equals(form.getUsername())) {
        return "Username is not null";
      }
      if (form.getFullname() == null || "".equals(form.getFullname())) {
        return "Fullname is not null";
      }

      if (form.getEmail() == null || "".equals(form.getEmail())) {
        return "Email is not null";
      }
      if (form.getPhone() == null || "".equals(form.getPhone())) {
        return "Phone number is not null";
      }

      if (form.getDeptId() == null || form.getDeptId() == 0L) {
        return "Department is not null";
      }
      CatDepartmentBO object = getDepartmentBO(form.getDeptId());
      if (object == null) {
        return "DeptId invalid";
      }
    }
    return OK;
  }

  private CatDepartmentBO getDepartmentBO(Long depId) {
    CatDepartmentBO object = null;

    return object;
  }


  private List<SysUserBO> readSheetData(Sheet dataSheet) {
    ExcelWriterUtils excelUtils = new ExcelWriterUtils();
    List<SysUserBO> dataList = new ArrayList<>();
    int rowSheet = 1;
    Row row;
    SysUserBO temp;
    try {
      int totalRow = dataSheet.getLastRowNum();
      while (rowSheet <= totalRow) {
        if (!excelUtils.isEmptyRow(dataSheet.getRow(rowSheet), 0, 5)) {
          row = dataSheet.getRow(rowSheet);
          temp = new SysUserBO();
          int i = 1;
          row.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
          temp.setUsername(row.getCell(i).toString().trim());
          i++;
          row.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
          temp.setFullname(row.getCell(i).toString().trim());
          i++;
          row.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
          temp.setEmail(row.getCell(i).toString().trim());
          i++;
          row.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
          temp.setPhone(row.getCell(i).toString().trim());

          i++;
          row.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
          if (!"".equals(row.getCell(i).toString().trim())) {
            try {
              temp.setDeptId(Long.parseLong(row.getCell(i).toString().trim()));
            } catch (Exception e) {
              log.error(e.getMessage(), e);
            }
          }
          temp.setUserRole(0L);
          temp.setStatus(1L);
          dataList.add(temp);
        }
        rowSheet++;
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return dataList;
  }
}
