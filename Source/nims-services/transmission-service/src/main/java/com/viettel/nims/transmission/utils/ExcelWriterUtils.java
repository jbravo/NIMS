package com.viettel.nims.transmission.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.internal.util.logging.Log;

import java.io.*;
import java.security.SecureRandom;
import java.util.Date;

@Log4j2
public class ExcelWriterUtils {

  private Workbook workbook;
  private SXSSFWorkbook SXSSFworkbook;
  private FileOutputStream fileOut;
  private Log loger;


  //Tuantm set workbook
  public void setWorkBook(Workbook workbook) {
    this.workbook = workbook;
  }

  /**
   * Method to create a workbook to work with excel
   *
   * @param filePathName ThuanNHT
   */
  public void createWorkBook(String filePathName) {
    if (filePathName.endsWith(".xls") || filePathName.endsWith(".XLS")) {
      workbook = new HSSFWorkbook();
    } else if (filePathName.endsWith(".xlsx") || filePathName.endsWith(".XLSX")) {
      workbook = new XSSFWorkbook();
    }
  }

  /**
   * Method to create a new excel(xls,xlsx) file with file Name
   *
   */
  public void saveToFileExcel(String filePathName) {
    try {
      fileOut = new FileOutputStream(filePathName);
      workbook.write(fileOut);
    } catch (Exception ex) {
      loger.error(ex);
    } finally {
      try {
        fileOut.close();
        workbook = null;
      } catch (IOException ex) {
        loger.error(ex);
      }
    }
  }

  public void saveToFileExcelSXSSF(String filePathName) {
    try {
      fileOut = new FileOutputStream(filePathName);
      SXSSFworkbook.write(fileOut);
    } catch (Exception ex) {
      loger.error(ex);
    } finally {
      try {
        fileOut.close();
        SXSSFworkbook = null;
      } catch (IOException ex) {
        loger.error(ex);
      }
    }
  }


  public void writeToOutputStream(FileOutputStream out) {
    try {
      SXSSFworkbook.write(out);
    } catch (Exception ex) {
      loger.error(ex);
    } finally {
      try {
        fileOut.close();
        SXSSFworkbook = null;
      } catch (IOException ex) {
        loger.error(ex);
      }
    }
  }

  /**
   * method to create a sheet
   *
   * @param sheetName ThuanNHT
   */
  public Sheet createSheet(String sheetName) {
    String temp = WorkbookUtil.createSafeSheetName(sheetName);
    return workbook.createSheet(temp);
  }

  /**
   * method t create a row
   *
   * @return ThuanNHT
   */
  public Row createRow(Sheet sheet, int r) {
    Row row = sheet.createRow(r);
    return row;
  }

  /**
   * method to create a cell with value
   *
   * @param cellValue ThuanNHT
   */
  public Cell createCell(Row row, int column, String cellValue) {
    // Create a cell and put a value in it.
    Cell cell = row.createCell(column);
    cell.setCellValue(cellValue);
    return cell;
  }

  /**
   * method to create a cell with value
   *
   * @param cellValue ThuanNHT
   */
  public Cell createCell(Sheet sheet, int c, int r, String cellValue) {
    Row row = sheet.getRow(r);
    if (row == null) {
      row = sheet.createRow(r);
    }
    // Create a cell and put a value in it.
    Cell cell = row.createCell(c);
    cell.setCellValue(cellValue);
    return cell;
  }

  //dungvv8_start_2/6/2014
  public Cell createCellObject(Sheet sheet, int c, int r, Object obj, CellStyle cs) {
    Row row = sheet.getRow(r);
    if (row == null) {
      row = sheet.createRow(r);
    }
    // Create a cell and put a value in it.
    Cell cell = row.createCell(c);
    if (obj != null) {
      if (obj instanceof String) {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue((String) obj);
      } else if (obj instanceof Double) {
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue((Double) obj);
      } else if (obj instanceof Long) {
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue((Long) obj);
      } else if (obj instanceof Integer) {
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue((Integer) obj);
      } else if (obj instanceof Date) {
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue((Date) obj);
      }
    }
    // CellStyle
    if (cs != null) {
      cell.setCellStyle(cs);
    }
    return cell;
  }

  public CellStyle cellStyle(HorizontalAlignment halign, VerticalAlignment valign,
      BorderStyle border,
      short borderColor, int foregroundColor,
      int fontHeight, int fontWeight, boolean wraptext) {
    CellStyle style;
    Font font;
    if (workbook != null) {
      style = workbook.createCellStyle();
      font = workbook.createFont();

    } else {
      style = SXSSFworkbook.createCellStyle();
      font = SXSSFworkbook.createFont();
    }
    font.setFontHeightInPoints((short) fontHeight);
    font.setFontName("Times New Roman");
    if (fontWeight != -1) {
      font.setBold(true);
    }
    style.setAlignment(halign);
    style.setVerticalAlignment(valign);
    style.setBorderBottom(border);
    style.setBottomBorderColor(borderColor);
    style.setBorderLeft(border);
    style.setLeftBorderColor(borderColor);
    style.setBorderRight(border);
    style.setRightBorderColor(borderColor);
    style.setBorderTop(border);
    style.setTopBorderColor(borderColor);
    style.setFont(font);
    if (foregroundColor != -1) {
      style.setFillForegroundColor((short) foregroundColor);
      style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }
    style.setWrapText(wraptext);
    return style;
  }

  public CellStyle getCsLeftBoder() {
    return cellStyle(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM, BorderStyle.THIN,
        IndexedColors.BLACK.getIndex(), -1, 11, -1, true);
  }

  public CellStyle getCsRightBoder() {
    return cellStyle(HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM, BorderStyle.THIN,
        IndexedColors.BLACK.getIndex(), -1, 11, -1, true);
  }

  public CellStyle getCsCenterBoder() {
    return cellStyle(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, BorderStyle.THIN,
        IndexedColors.BLACK.getIndex(), -1, 11, -1, true);
  }

  public CellStyle getCsCenterVertical() {
    return cellStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, BorderStyle.THIN,
        IndexedColors.BLACK.getIndex(), -1, 11, -1, true);
  }

  public CellStyle getCsLeftVertical() {
    return cellStyle(HorizontalAlignment.LEFT, VerticalAlignment.CENTER, BorderStyle.THIN,
        IndexedColors.BLACK.getIndex(), -1, 11, -1, true);
  }

  public CellStyle getCsCenterNoBoder() {
    return cellStyle(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, BorderStyle.NONE,
        IndexedColors.BLACK.getIndex(), -1, 11, -1, true);
  }

  public CellStyle getCsCenterNoboderBoldweight() {
    return cellStyle(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM, BorderStyle.NONE,
        IndexedColors.BLACK.getIndex(), -1, 11, 1, false);
  }

  public CellStyle getCsCenterNoboderBoldweight1() {
    return cellStyle(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, BorderStyle.THIN,
        IndexedColors.BLACK.getIndex(), -1, 11, 1, true);
  }

  public CellStyle getCsColHeader() {
    return cellStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, BorderStyle.THIN,
        IndexedColors.BLACK.getIndex(), IndexedColors.SKY_BLUE.getIndex(),
        11, 1, true);
  }

  public CellStyle getCsColHeader2() {
    return cellStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, BorderStyle.NONE,
        IndexedColors.BLACK.getIndex(), IndexedColors.WHITE.getIndex(),
        11, 1, true);
  }

  public CellStyle getCsColHeader1() {
    return cellStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, BorderStyle.THIN,
        IndexedColors.BLACK.getIndex(), IndexedColors.YELLOW.getIndex(),
        11, 1, true);
  }

  public CellStyle getCsColorRed() {
    return cellStyle(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, BorderStyle.THIN,
        IndexedColors.BLACK.getIndex(), IndexedColors.RED.getIndex(),
        11, 1, true);
  }

  public void setRowHeight(Sheet sheet, int rowIndex, int rowHeight) {
    Row row = sheet.getRow(rowIndex);
    if (row == null) {
      row = sheet.createRow(rowIndex);
    }
    row.setHeight((short) rowHeight);
  }

  public void autoSizeColumn(Sheet sheet, int firstCol, int lastCol) {
    for (int j = firstCol; j <= lastCol; j++) {
      sheet.autoSizeColumn(j);
    }
  }
  //dungvv8_end_2/6/2014

  public Cell createCell1(Sheet sheet, int c, int r, double cellValue) {
    Row row = sheet.getRow(r);
    if (row == null) {
      row = sheet.createRow(r);
    }
    // Create a cell and put a value in it.
    Cell cell = row.createCell(c);
    cell.setCellValue(cellValue);
    return cell;
  }

  /**
   * method to create a cell with value with style
   *
   * @param cellValue ThuanNHT
   */
  public Cell createCell(Sheet sheet, int c, int r, String cellValue, CellStyle style) {
    Row row = sheet.getRow(r);
    if (row == null) {
      row = sheet.createRow(r);
    }
    // Create a cell and put a value in it.
    Cell cell = row.createCell(c);
    cell.setCellValue(cellValue);
    cell.setCellStyle(style);
    return cell;
  }

  /**
   * Method get primitive content Of cell
   */
  public static Object getCellContent(Sheet sheet, int c, int r) {
    Cell cell = getCellOfSheet(r, c, sheet);
    if (cell == null) {
      return "";
    }
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_STRING:
        return cell.getRichStringCellValue().getString();
      case Cell.CELL_TYPE_NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {
          return cell.getDateCellValue();
        } else {
          return cell.getNumericCellValue();
        }
      case Cell.CELL_TYPE_BOOLEAN:
        return cell.getBooleanCellValue();
      case Cell.CELL_TYPE_FORMULA:
        return cell.getCellFormula();
      default:
        return "";

    }
  }

  /**
   * Method set sheet is selected when is opened
   */
  public void setSheetSelected(int posSheet) {
    try {
      workbook.setActiveSheet(posSheet);
    } catch (IllegalArgumentException ex) {
      loger.error(ex);
      workbook.setActiveSheet(0);
    }
  }

  public void setSheetSelectedSXSSF(int posSheet) {
    try {
      SXSSFworkbook.setActiveSheet(posSheet);
    } catch (IllegalArgumentException ex) {
      loger.error(ex);
      SXSSFworkbook.setActiveSheet(0);
    }
  }

  /**
   * method to merge cell
   *
   * @param firstRow based 0
   * @param lastRow based 0
   * @param firstCol based 0
   * @param lastCol based 0
   */
  public static void mergeCells(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
    sheet.addMergedRegion(new CellRangeAddress(
        firstRow, //first row (0-based)
        lastRow, //last row  (0-based)
        firstCol, //first column (0-based)
        lastCol //last column  (0-based)
    ));
  }

  /**
   * method to fill color background for cell
   *
   * @param colors:BLACK, WHITE, RED, BRIGHT_GREEN, BLUE, YELLOW, PINK, TURQUOISE, DARK_RED, GREEN,
   * DARK_BLUE, DARK_YELLOW, VIOLET, TEAL, GREY_25_PERCENT, GREY_50_PERCENT, CORNFLOWER_BLUE,
   * MAROON, LEMON_CHIFFON, ORCHID, CORAL, ROYAL_BLUE, LIGHT_CORNFLOWER_BLUE, SKY_BLUE,
   * LIGHT_TURQUOISE, LIGHT_GREEN, LIGHT_YELLOW, PALE_BLUE, ROSE, LAVENDER, TAN, LIGHT_BLUE, AQUA,
   * LIME, GOLD, LIGHT_ORANGE, ORANGE, BLUE_GREY, GREY_40_PERCENT, DARK_TEAL, SEA_GREEN, DARK_GREEN,
   * OLIVE_GREEN, BROWN, PLUM, INDIGO, GREY_80_PERCENT, AUTOMATIC;
   */
  public void fillAndColorCell(Cell cell, IndexedColors colors) {
    CellStyle style = workbook.createCellStyle();
    style.setFillBackgroundColor(colors.getIndex());
    cell.setCellStyle(style);
  }
  // datpk  lay object tu Row

  public static Object getCellContentRow(int c, Row row) {
    Cell cell = getCellOfSheetRow(c, row);
    if (cell == null) {
      return "";
    }
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_STRING:
        return cell.getRichStringCellValue().getString();
      case Cell.CELL_TYPE_NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {
          return cell.getDateCellValue();
        } else {
          return cell.getNumericCellValue();
        }
      case Cell.CELL_TYPE_BOOLEAN:
        return cell.getBooleanCellValue();
      case Cell.CELL_TYPE_FORMULA:
        return cell.getCellFormula();
      default:
        return "";

    }
  }

  /**
   * Method get text content Of cell
   */
  public static String getCellStrContent(Sheet sheet, int c, int r) {
    Cell cell = getCellOfSheet(r, c, sheet);
    if (cell == null) {
      return "";
    }
    String temp = getCellContent(sheet, c, r).toString().trim();
    if (temp.endsWith(".0")) {
      return temp.substring(0, temp.length() - 2);
    }
    return temp;
  }
  // datpk getStringconten tu Row

  public static String getCellStrContentRow(int c, Row row) {
    Cell cell = getCellOfSheetRow(c, row);
    if (cell == null) {
      return "";
    }
    String temp = getCellContentRow(c, row).toString().trim();
    if (temp.endsWith(".0")) {
      return temp.substring(0, temp.length() - 2);
    }
    return temp;
  }

  /**
   * method to create validation from array String.But String do not exceed 255 characters
   *
   * @param arrValidate * ThuanNHT
   */
  public void createDropDownlistValidateFromArr(Sheet sheet, String[] arrValidate, int firstRow,
                                                int lastRow, int firstCol, int lastCol) {
    CellRangeAddressList addressList = new CellRangeAddressList(
        firstRow, lastRow, firstCol, lastCol);
    DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(arrValidate);
    HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
    dataValidation.setSuppressDropDownArrow(false);
    HSSFSheet sh = (HSSFSheet) sheet;
    sh.addValidationData(dataValidation);
  }

  /**
   * Method to create validation from spread sheet via range
   *
   * @param lastCol * ThuanNHT
   */
  public void createDropDownListValidateFromSpreadSheet(String range, int firstRow, int lastRow,
                                                        int firstCol, int lastCol, Sheet shet) {
    Name namedRange = workbook.createName();
    SecureRandom rd = new SecureRandom();
    String refName = ("List" + rd.nextInt()).toString().replace("-", "");
    namedRange.setNameName(refName);
//        namedRange.setRefersToFormula("'Sheet1'!$A$1:$A$3");
    namedRange.setRefersToFormula(range);
    DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(refName);
    CellRangeAddressList addressList = new CellRangeAddressList(
        firstRow, lastRow, firstCol, lastCol);
    HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
    dataValidation.setSuppressDropDownArrow(false);
    HSSFSheet sh = (HSSFSheet) shet;
    sh.addValidationData(dataValidation);
  }

  public void createDropDownListValidateFromSpreadSheet(String sheetName, String columnRangeName,
                                                        int rowRangeStart, int rowRangeEnd, int firstRow, int lastRow, int firstCol, int lastCol,
                                                        Sheet shet) {
    String range = "'" + sheetName + "'!$" + columnRangeName + "$" + rowRangeStart + ":" + "$"
        + columnRangeName + "$" + rowRangeEnd;
    Name namedRange = workbook.createName();
    SecureRandom rd = new SecureRandom();
    String refName = ("List" + rd.nextInt()).replace("-", "");
    namedRange.setNameName(refName);
//        namedRange.setRefersToFormula("'Sheet1'!$A$1:$A$3");
    namedRange.setRefersToFormula(range);
    DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(refName);
    CellRangeAddressList addressList = new CellRangeAddressList(
        firstRow, lastRow, firstCol, lastCol);
    HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
    dataValidation.setSuppressDropDownArrow(false);
    HSSFSheet sh = (HSSFSheet) shet;
    sh.addValidationData(dataValidation);
  }

  public Sheet getSheetAt(int pos) {
    return workbook.getSheetAt(pos);
  }

  public Sheet getSheet(String name) {
    return workbook.getSheet(name);
  }

  /**
   * Method to read an excel file
   *
   * @return * ThuanNHT
   */
  public Workbook readFileExcel(String filePathName) {
    InputStream inp = null;
    try {
      inp = new FileInputStream(filePathName);
      workbook = WorkbookFactory.create(inp);
    } catch (FileNotFoundException ex) {
      loger.error(ex);
    } catch (Exception ex) {
      loger.error(ex);
    } finally {
      try {
        if (inp != null) {
          inp.close();
        }
      } catch (IOException ex) {
        loger.error(ex);
      }
    }
    return workbook;
  }

  public SXSSFWorkbook readFileExcelSXSSF(String filePathName) {
    InputStream inp = null;
    try {
      inp = new FileInputStream(filePathName);
      workbook = WorkbookFactory.create(inp);
      SXSSFworkbook = new SXSSFWorkbook((XSSFWorkbook) workbook);
    } catch (FileNotFoundException ex) {
      loger.error(ex);
    } catch (Exception ex) {
      loger.error(ex);
    } finally {
      try {
        if (inp != null) {
          inp.close();
        }
      } catch (IOException ex) {
        loger.error(ex);
      }
    }
    return SXSSFworkbook;
  }

  /**
   * * ThuanNHT
   */
  public static Cell getCellOfSheet(int r, int c, Sheet sheet) {
    try {
      Row row = sheet.getRow(r);
      if (row == null) {
        return null;
      }
      return row.getCell(c);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return null;
    }
  }

  /**
   * set style for cell
   */
  public void setCellStyle(Cell cell, HorizontalAlignment halign, VerticalAlignment valign,
      BorderStyle border, short borderColor, int fontHeight) {
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setFontHeightInPoints((short) fontHeight);
    font.setFontName("Times New Roman");
    style.setAlignment(halign);
    style.setVerticalAlignment(valign);
    style.setBorderBottom(border);
    style.setBottomBorderColor(borderColor);
    style.setBorderLeft(border);
    style.setLeftBorderColor(borderColor);
    style.setBorderRight(border);
    style.setRightBorderColor(borderColor);
    style.setBorderTop(border);
    style.setTopBorderColor(borderColor);
    style.setFont(font);
    cell.setCellStyle(style);
  }

  /**
   * Method to draw an image on excel file
   */
  public void drawImageOnSheet(
      String imgSrc, Sheet sheet, int colCorner, int rowCorner) throws IOException {
    InputStream is = null;
    try {
      is = new FileInputStream(imgSrc);
      byte[] bytes = IOUtils.toByteArray(is);
      int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
      if (imgSrc.endsWith(".jpg") || imgSrc.endsWith(".JPG") || imgSrc.endsWith(".jpeg") || imgSrc
          .endsWith(".JPEG")) {
        pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
      } else if (imgSrc.endsWith(".png") || imgSrc.endsWith(".PNG")) {
        pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
      }

      CreationHelper helper = workbook.getCreationHelper();
      // Create the drawing patriarch.  This is the top level container for all shapes.
      Drawing drawing = sheet.createDrawingPatriarch();
      //add a picture shape
      ClientAnchor anchor = helper.createClientAnchor();
      //set top-left corner of the picture,
      //subsequent call of Picture#resize() will operate relative to it
      anchor.setCol1(colCorner);
      anchor.setRow1(rowCorner);
      Picture pict = drawing.createPicture(anchor, pictureIdx);

      //auto-size picture relative to its top-left corner
      pict.resize();
    } catch (Exception ex) {
      loger.error(ex);
    } finally {
      if (is != null) {
        is.close();
      }
    }

  }

  public void setStandardCellStyle(Cell cell) {
    setCellStyle(cell, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, BorderStyle.THIN,
        IndexedColors.BLACK.getIndex(), 12);
  }

  // datpk: lay cell tu Row
  public static Cell getCellOfSheetRow(int c, Row row) {
    try {
      if (row == null) {
        return null;
      }
      return row.getCell(c);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return null;
    }
  }

  public static Boolean compareToLong(String str, Long t) {
    Boolean check = false;
    try {
      Double d = Double.valueOf(str);
      Long l = d.longValue();
      if (l.equals(t)) {
        check = true;
      }
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      check = false;
    }
    return check;
  }

  public static Boolean doubleIsLong(String str) {
    Boolean check = false;
    try {
      Double d = Double.valueOf(str);
      Long l = d.longValue();
      if (d.equals(Double.valueOf(l))) {
        check = true;
      }
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      check = false;
    }
    return check;
  }

  public Workbook getWorkbook() {
    return workbook;
  }

  public void setWorkbook(Workbook workbook) {
    this.workbook = workbook;
  }

  public SXSSFWorkbook getSXSSFworkbook() {
    return SXSSFworkbook;
  }

  public void setSXSSFworkbook(SXSSFWorkbook SXSSFworkbook) {
    this.SXSSFworkbook = SXSSFworkbook;
  }

  //R3490.1_trangltt9_new_25022013_start
  public void saveToFile(OutputStream out) {
    try {
      workbook.write(out);
    } catch (Exception ex) {
      loger.error(ex);
    } finally {
      workbook = null;
    }
  }
  //R3490.1_trangltt9_new_25022013_end

  public Sheet getSheetAtSXSSF(int pos) {
    return SXSSFworkbook.getSheetAt(pos);
  }

  //vinhvh add import
  public Row getOrCreateRow(Sheet sheet, int rowIndex) {
    if (sheet.getRow(rowIndex) == null) {
      return sheet.createRow(rowIndex);
    }
    return sheet.getRow(rowIndex);
  }

  public boolean isEmptyRow(Row row, int startCol, int endCol) {
    if (row == null) {
      return true;
    }
    Cell cell;
    for (int i = startCol; i <= endCol; i++) {
      cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
      if (cell != null && !"".equals(cell.toString().trim())) {
        return false;
      }
    }
    return true;
  }

}
