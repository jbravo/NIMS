package com.viettel.nims.transmission.commom;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.transmission.commom.annotation.SheetSerializable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ExcelStyleUtil {

  private ExcelStyleUtil() {

  }

  /**
   * Set title style
   *
   * @param workbook
   * @return
   */
  public static XSSFCellStyle getTitleCellStyle(XSSFWorkbook workbook) {
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setAlignment(HorizontalAlignment.CENTER);
    cellStyle.setFont(getTitleFontStyle(workbook));
    return cellStyle;
  }

  /**
   * Set report date style
   *
   * @param workbook
   * @return
   */
  public static XSSFCellStyle getReportDateCellStyle(XSSFWorkbook workbook) {
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setAlignment(HorizontalAlignment.CENTER);
    cellStyle.setFont(getReportDateFontStyle(workbook));
    return cellStyle;
  }

  /**
   * Set header style
   *
   * @param workbook
   * @return
   */
  public static XSSFCellStyle getHeaderCellStyle(XSSFWorkbook workbook) {
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    getBorderStyle(cellStyle);
    cellStyle.setAlignment(HorizontalAlignment.CENTER);
    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    byte[] rgb = new byte[]{(byte) 252, (byte) 228, (byte) 214};
    cellStyle.setFillForegroundColor(new XSSFColor(rgb, null));
    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    cellStyle.setWrapText(true);
    cellStyle.setFont(getHeaderFontStyle(workbook));
    return cellStyle;
  }

  /**
   * Set string style
   *
   * @param workbook
   * @return
   */
  public static XSSFCellStyle getStringCellStyle(XSSFWorkbook workbook) {
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    getBorderStyle(cellStyle);
    cellStyle.setAlignment(HorizontalAlignment.LEFT);
    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    cellStyle.setWrapText(true);
    cellStyle.setFont(getDataFontStyle(workbook));
    return cellStyle;
  }

  /**
   * Set number style
   *
   * @param workbook
   * @return
   */
  public static XSSFCellStyle getNumberCellStyle(XSSFWorkbook workbook) {
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    getBorderStyle(cellStyle);
    cellStyle.setAlignment(HorizontalAlignment.RIGHT);
    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    cellStyle.setWrapText(true);
    cellStyle.setFont(getDataFontStyle(workbook));
    return cellStyle;
  }

  /**
   * Set date style
   *
   * @param workbook
   * @return
   */
  public static XSSFCellStyle getDateCellStyle(XSSFWorkbook workbook) {
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    getBorderStyle(cellStyle);
    cellStyle.setAlignment(HorizontalAlignment.CENTER);
    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    cellStyle.setWrapText(true);
    cellStyle.setFont(getDataFontStyle(workbook));
    DataFormat fmt = workbook.createDataFormat();
    cellStyle.setDataFormat(fmt.getFormat("@"));
    return cellStyle;
  }

  /**
   * Set border
   *
   * @param cellStyle
   */
  public static void getBorderStyle(XSSFCellStyle cellStyle) {
    cellStyle.setBorderTop(BorderStyle.THIN);
    cellStyle.setBorderRight(BorderStyle.THIN);
    cellStyle.setBorderBottom(BorderStyle.THIN);
    cellStyle.setBorderLeft(BorderStyle.THIN);
  }

  /**
   * Set font style of title
   *
   * @param workbook
   * @return
   */
  public static XSSFFont getTitleFontStyle(XSSFWorkbook workbook) {
    XSSFFont font = workbook.createFont();
    // font.setFontHeightInPoints((short) 30);
    font.setFontName("Times New Roman");
//    font.setBold(true);
    font.setFontHeightInPoints((short) 20);
    return font;
  }

  /**
   * Set font style of report date
   *
   * @param workbook
   * @return
   */
  public static XSSFFont getReportDateFontStyle(XSSFWorkbook workbook) {
    XSSFFont font = workbook.createFont();
    // font.setFontHeightInPoints((short) 30);
    font.setFontName("Times New Roman");
//    font.setBold(true);
    font.setFontHeightInPoints((short) 12);
    return font;
  }

  /**
   * Set font of header
   *
   * @param workbook
   * @return
   */
  public static XSSFFont getHeaderFontStyle(XSSFWorkbook workbook) {
    XSSFFont font = workbook.createFont();
    font.setFontHeightInPoints((short) 12);
    font.setFontName("Times New Roman");
    font.setBold(true);
    return font;
  }

  /**
   * Set font of data
   *
   * @param workbook
   * @return
   */
  public static XSSFFont getDataFontStyle(XSSFWorkbook workbook) {
    XSSFFont font = workbook.createFont();
    font.setFontHeightInPoints((short) 10);
    font.setFontName("Times New Roman");
    return font;
  }

  /**
   * Set style for text
   *
   * @param workbook
   * @param horizontalAlignment
   * @param verticalAlignment
   * @param textFont
   * @param fontSize
   * @param textStyleExcel
   * @return
   */
  public static XSSFCellStyle getTextCellStyle(
      XSSFWorkbook workbook,
      HorizontalAlignment horizontalAlignment,
      VerticalAlignment verticalAlignment,
      String textFont,
      int fontSize,
      TextStyleExcel textStyleExcel) {

    XSSFCellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setAlignment(horizontalAlignment);
    cellStyle.setVerticalAlignment(verticalAlignment);
    cellStyle.setWrapText(true);
    cellStyle.setFont(getTextFontStyle(workbook, textFont, fontSize, textStyleExcel));
    return cellStyle;
  }


  /**
   * Set text font
   *
   * @param workbook
   * @param textFont
   * @param fontSize
   * @param textStyleExcel
   * @return
   */
  public static XSSFFont getTextFontStyle(
      XSSFWorkbook workbook,
      String textFont,
      int fontSize,
      TextStyleExcel textStyleExcel) {

    XSSFFont font = workbook.createFont();
    font.setFontHeightInPoints((short) fontSize);
    font.setFontName(textFont);
    if (TextStyleExcel.BOLD == textStyleExcel) {
      font.setBold(true);
    } else if (TextStyleExcel.ITALIC == textStyleExcel) {
      font.setItalic(true);
    } else if (TextStyleExcel.UNDERLINE == textStyleExcel) {
      font.setItalic(true);
    }
    return font;
  }

  /**
   * Set table style
   *
   * @param workbook
   * @return
   */
  public static XSSFCellStyle getCellTableStyle(
      XSSFWorkbook workbook,
      HorizontalAlignment horizontalAlignment,
      VerticalAlignment verticalAlignment,
      boolean isRotation,
      String textFont,
      int fontSize,
      TextStyleExcel textStyleExcel) {

    XSSFCellStyle cellStyle = workbook.createCellStyle();
    getBorderStyle(cellStyle);
    cellStyle.setAlignment(horizontalAlignment);
    cellStyle.setVerticalAlignment(verticalAlignment);
    if (isRotation) {
      cellStyle.setRotation((short) 90);
    }
    cellStyle.setWrapText(true);
    cellStyle.setFont(getTextFontStyle(workbook, textFont, fontSize, textStyleExcel));
    return cellStyle;
  }

  /**
   * Set border cell merge
   *
   * @param cellRangeAddress
   * @param sheet
   * @param workbook
   */
  @SuppressWarnings("deprecation")
  public static void setBorderMergeStyle(CellRangeAddress cellRangeAddress, XSSFSheet sheet, XSSFWorkbook workbook) {
    RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet);
    RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet);
    RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet);
    RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, sheet);
  }

//  set fomat cell as text
  public static XSSFCellStyle getTextCellStyle(XSSFWorkbook workbook) {
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    getBorderStyle(cellStyle);
    cellStyle.setAlignment(HorizontalAlignment.LEFT);
    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    cellStyle.setWrapText(true);
    cellStyle.setFont(getDataFontStyle(workbook));
    DataFormat fmt = workbook.createDataFormat();
    cellStyle.setDataFormat(fmt.getFormat("@"));
    return cellStyle;
  }
// set fomat cell as text and align right
  public static XSSFCellStyle getTextNumberCellStyle(XSSFWorkbook workbook) {
	    XSSFCellStyle cellStyle = workbook.createCellStyle();
	    getBorderStyle(cellStyle);
	    cellStyle.setAlignment(HorizontalAlignment.RIGHT);
	    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    cellStyle.setWrapText(true);
	    cellStyle.setFont(getDataFontStyle(workbook));
	    DataFormat fmt = workbook.createDataFormat();
	    cellStyle.setDataFormat(fmt.getFormat("@"));
	    return cellStyle;
	  }

  public static List<Object[]> readDataFromSheet(MultipartFile file, int sheetIndex, int startRow, int cols) throws IOException {
    XSSFWorkbook workbook = null;
    try {
      FileInputStream inputStream = new FileInputStream(saveFile(file));
      workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
      List<Object[]> result = new ArrayList<>();
      Iterator<Row> iterator = sheet.iterator();
      try {
        for (int i = 0; i < startRow; i++) {
          iterator.next();
        }
      } catch (Exception e) {
        return null;
      }
      DataFormatter dataFormatter = new DataFormatter();
      while (iterator.hasNext()) {
        Object[] obj = new Object[cols];
        Row row = iterator.next();
        for (int j = 0; j < cols; j++) {
          Cell cell = row.getCell(j);
          if (cell == null) {
            break;
          }
          if (cell.getCellTypeEnum() == CellType.STRING) {
            obj[j] = cell.getStringCellValue();
          } else {
            obj[j] = dataFormatter.formatCellValue(cell).trim();
          }
        }
        if (!CommonUtils.isEmpty(obj)) {
          result.add(obj);
        }
      }
      return result;
    } catch (IOException e) {
      log.error("Exception", e);
      return null;
    } finally {
      workbook.close();
    }
  }

  public static File saveFile(MultipartFile file) throws IOException {
    String path = "";
    File dest = new File(path);
    FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
    return dest;
  }


}
