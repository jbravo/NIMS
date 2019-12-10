package com.viettel.nims.transmission.commom;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.transmission.commom.annotation.Element;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelDataUltils {
  private static final char[] EXCEL_SHEET_NAME_INVALID_CHARS = {'/', '\\', '?', '*', ']', '[', ':'};
  private static final char INVALID_REPLACE_CHAR = '_';


  public static List<Object[]> createObjFromExcel(MultipartFile file, Object object) throws IOException {
    XSSFWorkbook workbook = null;
    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream(saveFile(file));
      Class<?> clazz = object.getClass();
      SheetSerializable sheetSerializable = clazz.getDeclaredAnnotation(SheetSerializable.class);
      workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workbook.getSheetAt(sheetSerializable.sheetDataIndex());
      List<Object[]> result = new ArrayList<>();
      Iterator<Row> iterator = sheet.iterator();
      try {
        for (int i = 0; i < sheetSerializable.startRow(); i++) {
          iterator.next();
        }
        DataFormatter dataFormatter = new DataFormatter();
        while (iterator.hasNext()) {
          Object[] obj = new Object[sheetSerializable.totalCols()];
          Row row = iterator.next();
          for (int j = 0; j < sheetSerializable.totalCols(); j++) {
            Cell cell = row.getCell(j);
            if (cell != null) {
              if (cell.getCellTypeEnum() == CellType.STRING) {
                obj[j] = cell.getStringCellValue().trim();
              } else {
                obj[j] = dataFormatter.formatCellValue(cell).trim();
              }
            }
          }
          if (!isNullOrEmpty(obj)) {
            result.add(obj);
          }
        }
        return result;
      } catch (Exception e) {
        return null;
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      if (workbook != null) {
        workbook.close();
      }
      if (inputStream != null) {
        inputStream.close();
      }
    }
  }

  public static List<?> convertObjToList(List<Object[]> objects, Object object) throws ClassNotFoundException {
    Class<?> c = Class.forName(object.getClass().getName());
    Class<?> clazz = object.getClass();
    SheetSerializable sheetSerializable = clazz.getDeclaredAnnotation(SheetSerializable.class);
    List<Object> result = new ArrayList<>();

    try {
      for (Object[] o : objects) {
        Constructor<?> ctor = clazz.getConstructor();
        Object obj = c.newInstance();

        for (Field field : clazz.getDeclaredFields()) {
          Element element = field.getAnnotation(Element.class);
          if (element.index() != -1) {
            clazz.getDeclaredMethod("set" + StringUtils.capitalize(field.getName()), String.class).invoke(obj, o[element.index()] == null ? "" : o[element.index()]);
          }
        }
        result.add(obj);
      }
      return result;

    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;

  }

  public static List<?> getListInExcel(MultipartFile file, Object object) {
    List<Object[]> datas = null;
    try {
      datas = createObjFromExcel(file, object);
      List<?> result = convertObjToList(datas, object);
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static <T extends Object> T getFlagList(MultipartFile file, Object object) throws IOException {
    XSSFWorkbook workbook = null;
    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream(saveFile(file));
      Class<?> clazz = object.getClass();
      Class<?> c = Class.forName(object.getClass().getName());
      SheetSerializable sheetSerializable = clazz.getDeclaredAnnotation(SheetSerializable.class);
      workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workbook.getSheetAt(sheetSerializable.sheetDataIndex());

      Row flagRow = sheet.getRow(sheetSerializable.flagRow());
      Object obj = c.newInstance();

      for (Field field : clazz.getDeclaredFields()) {
        Element element = field.getAnnotation(Element.class);
        if (element.index() != -1) {
          try {
            String data = flagRow.getCell(element.index()).getStringCellValue().trim();
            clazz.getDeclaredMethod("set" + StringUtils.capitalize(field.getName()), String.class).invoke(obj, data);
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          }
        }
      }
      return (T) obj;

    } catch (IOException | ClassNotFoundException e) {

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } finally {
      if (workbook != null) workbook.close();
      if (inputStream != null) inputStream.close();
    }
    return null;
  }

  public static String writeResultExcel(MultipartFile file, Object object, Map<Long, String> listResult, String fileName) {
    String savePath = null;
    XSSFWorkbook workbook = null;
    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream(saveFile(file));
      Class<?> clazz = object.getClass();
      SheetSerializable sheetSerializable = clazz.getDeclaredAnnotation(SheetSerializable.class);
      workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workbook.getSheetAt(sheetSerializable.sheetDataIndex());
      int headerIndex = sheetSerializable.startRow() - 2;

      Row row02 = sheet.getRow(headerIndex);
      Cell pre02Cell = row02.getCell(sheetSerializable.resultCols() - 1);
      Cell cell24 = row02.createCell(sheetSerializable.resultCols());
      CellStyle style = pre02Cell.getCellStyle();
      cell24.setCellStyle(style);
      cell24.setCellValue("Kết quả import");
      sheet.setColumnWidth(sheetSerializable.resultCols(), 50 * 256);

      Row row03 = sheet.getRow(headerIndex + 1);
      Cell pre03Cell = row02.getCell(sheetSerializable.resultCols() - 1);
      Cell cell25 = row03.createCell(sheetSerializable.resultCols());
      CellStyle style03 = pre03Cell.getCellStyle();
      cell25.setCellStyle(style03);
      cell25.setCellValue("N");

      Iterator<Row> iterator = sheet.rowIterator();

      for (int i = 0; i < sheetSerializable.startRow(); i++) {
        iterator.next();
      }
      long idx = 0;
      while (iterator.hasNext() && idx < listResult.size()) {
        Row row = iterator.next();
        Cell cell2 = row.createCell(sheetSerializable.resultCols());
        cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle(workbook));
        cell2.setCellValue(listResult.get(idx++));

//      create index
        Cell cellIdx = row.getCell(0);
        if (cellIdx == null) {
          cellIdx = row.createCell(0);
          cellIdx.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));
        }
        cellIdx.setCellValue(idx);

      }
      savePath = "./report_out/";
      File dir = new File(savePath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      savePath = savePath + fileName + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
      FileOutputStream outputStream = new FileOutputStream(savePath);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      if (workbook != null) try {
        workbook.close();
        if (inputStream != null) inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return savePath;
  }

  public static String writeResultExcel(MultipartFile file, Object object, Map<Long, String> listResult, String fileName, List<?> listData) {
    String savePath = null;
    XSSFWorkbook workbook = null;
    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream(saveFile(file));
      Class<?> clazz = object.getClass();
      SheetSerializable sheetSerializable = clazz.getDeclaredAnnotation(SheetSerializable.class);
      workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workbook.getSheetAt(sheetSerializable.sheetDataIndex());
      int headerIndex = sheetSerializable.startRow() - 2;

      Row row02 = sheet.getRow(headerIndex);
      Cell pre02Cell = row02.getCell(sheetSerializable.resultCols() - 1);
      Cell cell24 = row02.createCell(sheetSerializable.resultCols());
      CellStyle style = pre02Cell.getCellStyle();
      cell24.setCellStyle(style);
      cell24.setCellValue("Kết quả import");
      sheet.setColumnWidth(sheetSerializable.resultCols(), 50 * 256);

      Row row03 = sheet.getRow(headerIndex + 1);
      Cell pre03Cell = row02.getCell(sheetSerializable.resultCols() - 1);
      Cell cell25 = row03.createCell(sheetSerializable.resultCols());
      CellStyle style03 = pre03Cell.getCellStyle();
      cell25.setCellStyle(style03);
      cell25.setCellValue("N");

      Iterator<Row> iterator = sheet.rowIterator();

      for (int i = 0; i < sheetSerializable.startRow(); i++) {
        iterator.next();
      }
      long idx = 0;
      int ix = 0;
      while (iterator.hasNext() && idx < listResult.size()) {
        Row row = iterator.next();
        Object data = listData.get(ix++);
        Cell cell2 = row.createCell(sheetSerializable.resultCols());
        cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle(workbook));
        cell2.setCellValue(listResult.get(idx++));

//      create index
        Cell cellIdx = row.getCell(0);
        if (cellIdx == null) {
          cellIdx = row.createCell(0);
          cell2.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));
        }
        cellIdx.setCellValue(idx);
//        generate value

        for (Field field : clazz.getDeclaredFields()) {
          Element element = field.getAnnotation(Element.class);
          if (element.isGenerateValue()) {
            try {
              Object objSrc = data.getClass().getMethod("get" + StringUtils.capitalize(field.getName())).invoke(data);
              Cell cell = row.getCell(element.index());
              if (cell == null) {
                cell = row.createCell(element.index());
                if (element.type().equals("number")) {
                  cell.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));
                } else {
                  cell.setCellStyle(ExcelStyleUtil.getStringCellStyle(workbook));
                }
              }
              cell.setCellValue(objSrc.toString());
            } catch (NoSuchMethodException e) {
              e.printStackTrace();
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            } catch (InvocationTargetException e) {
              e.printStackTrace();
            }
          }
        }

      }
      savePath = "./report_out/";
      File dir = new File(savePath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      savePath = savePath + fileName + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
      FileOutputStream outputStream = new FileOutputStream(savePath);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      if (workbook != null) try {
        workbook.close();
        if (inputStream != null) inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return savePath;
  }

  public static File saveFile(MultipartFile file) throws IOException {
    File convFile = new File(file.getOriginalFilename());
    convFile.createNewFile();
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }

  public static void addMessage(Map<String, String> data, String errorType, String field) {
    if (data.containsKey(errorType)) {
      String temp = data.get(errorType) + "," + field;
      data.put(errorType, temp);
    } else {
      data.put(errorType, field);
    }
  }

  private static boolean isNullOrEmpty(Object[] objects) {
    if (objects == null || objects.length == 0) {
      return true;
    } else {
      for (Object o : objects) {
        if (o != null && !o.equals("")) {
          return false;
        }
      }
      return true;
    }
  }

  public static <T extends Object> T getHeaderList(MultipartFile file, Object object) throws IOException {
    XSSFWorkbook workbook = null;
    try {
      FileInputStream inputStream = new FileInputStream(saveFile(file));
      Class<?> clazz = object.getClass();
      Class<?> c = Class.forName(object.getClass().getName());
      SheetSerializable sheetSerializable = clazz.getDeclaredAnnotation(SheetSerializable.class);
      workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workbook.getSheetAt(sheetSerializable.sheetDataIndex());

      Row flagRow = sheet.getRow(sheetSerializable.flagRow() - 1);
      Object obj = c.newInstance();

      for (Field field : clazz.getDeclaredFields()) {
        Element element = field.getAnnotation(Element.class);
        if (element.index() != -1) {
          try {
            String data = flagRow.getCell(element.index()).getStringCellValue().trim();
            clazz.getDeclaredMethod("set" + StringUtils.capitalize(field.getName()), String.class).invoke(obj, data);
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          }
        }
      }
      return (T) obj;

    } catch (IOException | ClassNotFoundException e) {

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } finally {
      workbook.close();
    }
    return null;
  }
}
