package com.viettel.nims.transmission.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.viettel.nims.commons.util.Constant;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.commons.util.Response;
import com.viettel.nims.commons.util.StringUtils;
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.commom.Constants;
import com.viettel.nims.transmission.commom.ExcelDataUltils;
import com.viettel.nims.transmission.commom.ExcelStyleUtil;
import com.viettel.nims.transmission.commom.HeaderDTO;
import com.viettel.nims.transmission.commom.MessageResource;
import com.viettel.nims.transmission.commom.StringPool;
import com.viettel.nims.transmission.dao.CatOdfTypeDao;
import com.viettel.nims.transmission.dao.InfraCablesDao;
import com.viettel.nims.transmission.dao.InfraCouplerDao;
import com.viettel.nims.transmission.dao.InfraOdfDao;
import com.viettel.nims.transmission.dao.InfraStationDao;
import com.viettel.nims.transmission.dao.TransmissionDao;
import com.viettel.nims.transmission.dao.WeldingOdfDao;
import com.viettel.nims.transmission.model.CatDepartmentEntity;
import com.viettel.nims.transmission.model.CatOdfTypeBO;
import com.viettel.nims.transmission.model.CntCouplerToCouplerBO;
import com.viettel.nims.transmission.model.CntCouplerToLineBO;
import com.viettel.nims.transmission.model.InfraCablesBO;
import com.viettel.nims.transmission.model.InfraCouplerBO;
import com.viettel.nims.transmission.model.InfraOdfBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.dto.ImportOdfDTO;
import com.viettel.nims.transmission.model.dto.InfraOdfDTO;
import com.viettel.nims.transmission.model.dto.JointCouplersOdfDTO;
import com.viettel.nims.transmission.model.dto.WeldingOdfDTO;
import com.viettel.nims.transmission.model.dto.enums.EnumTypeImport;
import com.viettel.nims.transmission.model.view.ViewCatDepartmentBO;
import com.viettel.nims.transmission.model.view.ViewCatItemBO;
import com.viettel.nims.transmission.model.view.ViewInfraOdfBO;
import com.viettel.nims.transmission.model.view.ViewODFCableBO;
import com.viettel.nims.transmission.model.view.ViewOdfLaneBO;
import com.viettel.nims.transmission.model.view.ViewWeldingOdfBO;
import com.viettel.nims.transmission.utils.Constains;
import com.viettel.nims.transmission.utils.OdfStringUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * InfraOdfServiceImpl	 	Version 1.0			Date: 08-24-2019		Copyright Modification Logs:
 * DATE		 				AUTHOR		 		DESCRIPTION
 * -----------------------------------------------------------------------
 * 08-24-2019 				HungNV 				Create
 */
@Slf4j
@Service
@Transactional(transactionManager = "globalTransactionManager")
public class InfraOdfServiceImpl implements InfraOdfService {

// ----------------Constants------------
	private final String INFO_COUPLER_SOURCE_ODF_REF = "infoCouplerSourceOdfRef";
	private final String INFO_COUPLER_DIS_ODF_REF = "infoCouplerDisOdfRef";
	private final String INFO_LINE_REF = "infoLineRef";
	private final String INFO_ERROR_DELETE = "infoErrorDelete";
	private final String ODF_ID = "odfId";
	private final String ODF_CODE = "odfCode";
	private final String NOT_REF_COUPLER = "notRefCoupler";
	private final String NOT_REF_LINE = "notRefLine";
	private final String ROW_STATUS = "rowStatus";
	private final String INFO_COUPLER_REF = "infoCouplerRef";
// ------------DI----------------------
	@Autowired
	InfraCouplerDao infraCouplerDao;
	@Autowired
	CatOdfTypeDao catOdfTypeDao;
	@Autowired
	InfraOdfDao infraOdfDao;
	@Autowired
	TransmissionDao transmissionDao;
	@Autowired
	WeldingOdfDao weldingOdfDao;
	@Autowired
	InfraCablesDao infraCablesDao;
	@Autowired
	InfraStationDao infraStationDao;
	@Autowired
	MessageResource messageSource;
	@Autowired
	TransmissionService transmissionService;

// ------------Properties--------------
	private JSONObject responseJson = new JSONObject();
	private JSONArray responseJsonList = new JSONArray();
	List<Long> listCouplerNo = new ArrayList<>();

// ------------------------------------

	/**
	 * method find advance odf
	 *
	 * @param infraOdfBO
	 * @return ResponseEntity
	 * @author hungnv
	 * @date 26/8/2019
	 */
	@Override
	public ResponseEntity<?> findAdvanceOdf(ViewInfraOdfBO infraOdfBO, HttpServletRequest request) {
		Long userId = CommonUtil.getUserId(request);
		try {
			FormResult result = infraOdfDao.findAdvanceOdf(infraOdfBO, userId);
			Response<FormResult> response = new Response<>();
			response.setContent(result);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * method get station code
	 *
	 * @return ResponseEntity
	 * @author hungnv
	 * @date 26/8/2019
	 */
	@Override
	public ResponseEntity<?> getDataSearchAdvance() {
		Response<FormResult> response = new Response<>();
		try {
			FormResult result = infraOdfDao.getDataFormSearchAdvance();
			response.setContent(result);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * method find odf by id
	 *
	 * @param id
	 * @return ResponseEntity
	 * @author dungph
	 * @date 26/8/2019
	 */
	@Override
	public ResponseEntity<?> findOdfById(Long id) {
		try {
			ViewInfraOdfBO viewInfraOdfBO = infraOdfDao.findOdfById(id);
			List<InfraCouplerBO> infraCouplerBOs = infraCouplerDao.getInfraCouplersByOdfId(viewInfraOdfBO.getOdfId());
			Integer unusedCoupler = 0;
			for (InfraCouplerBO item : infraCouplerBOs) {
				if (item.getStatuz().equals(Constains.NUMBER_ZERO)) {
					unusedCoupler++;
				}
			}
			viewInfraOdfBO.setUnusedCoupler(unusedCoupler);
			viewInfraOdfBO.setUsedCoupler(infraCouplerBOs.size() - unusedCoupler);

			Response<ViewInfraOdfBO> response = new Response<>();
			response.setContent(viewInfraOdfBO);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * method get all stations
	 *
	 * @return ResponseEntity
	 * @author dungph
	 * @date 26/8/2019
	 */
	@Override
	public ResponseEntity<?> getAllStations() {
		try {
			List<InfraStationsBO> infraStationsBOList = infraOdfDao.getAllStations();
			return new ResponseEntity<>(setListResponse(infraStationsBOList), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * method delete multiple ODF
	 *
	 * @param infraOdfBOList
	 * @return JSONObject
	 * @author hungnv
	 * @date 28/8/2019
	 */
	@Override
	public JSONArray deleteOdfMultiple(List<InfraOdfBO> infraOdfBOList) {
		StringBuffer infoCouplerRef;
		StringBuffer infoCouplerSourceOdfRef;
		StringBuffer infoCouplerDisOdfRef;
		StringBuffer infoLineRef;
		responseJsonList.clear();
		if (checkDelete(infraOdfBOList)) {
			for (InfraOdfBO infraOdfBO : infraOdfBOList) {
				initInfoDelete(infraOdfBO.getOdfId());
				if (infraOdfDao.getOdfRowStatus(infraOdfBO.getOdfId()).equals(Constains.NUMBER_ONE)) {
					responseJson.put(ODF_CODE, infraOdfDao.getOdfCodeById(infraOdfBO.getOdfId()));
					infraOdfDao.deleteOdf(infraOdfBO.getOdfId());
					responseJson.put(ROW_STATUS, Constains.NUMBER_TWO);
					responseJsonList.add(responseJson);
				} else {
					responseJson.put(ODF_CODE, infraOdfDao.getOdfCodeById(infraOdfBO.getOdfId()));
					responseJson.put(INFO_ERROR_DELETE, false);
					responseJsonList.add(responseJson);
				}
			}

		} else {
			for (InfraOdfBO infraOdfBO : infraOdfBOList) {
				initInfoDelete(infraOdfBO.getOdfId());
				responseJson.put(ODF_CODE, infraOdfDao.getOdfCodeById(infraOdfBO.getOdfId()));
				if (refCouplerToCoupler(infraOdfBO.getOdfId())) {
					ArrayList<CntCouplerToCouplerBO> arrCoupler = infraOdfDao.getCouplerNoRef(infraOdfBO.getOdfId());
					infoCouplerRef = new StringBuffer();
					infoCouplerSourceOdfRef = new StringBuffer();
					infoCouplerDisOdfRef = new StringBuffer();
					responseJson.put(NOT_REF_COUPLER, false);
					for (CntCouplerToCouplerBO couplerBO : arrCoupler) {
						infoCouplerSourceOdfRef.append(infraOdfDao.getOdfCodeById(couplerBO.getSourceOdfId()));
						infoCouplerDisOdfRef.append(infraOdfDao.getOdfCodeById(couplerBO.getDestOdfId()));
						break;
					}
					for (CntCouplerToCouplerBO couplerBO : arrCoupler) {
						infoCouplerRef.append(couplerBO.getSourceCouplerNo() + Constains.SEMICOLON + Constains.SPACE);
					}
					String infoCouplerRefString = infoCouplerRef.substring(0,
							infoCouplerRef.length() - Constains.NUMBER_TWO);
					responseJson.put(INFO_COUPLER_REF, infoCouplerRefString);
					responseJson.put(INFO_COUPLER_SOURCE_ODF_REF, infoCouplerSourceOdfRef.toString());
					responseJson.put(INFO_COUPLER_DIS_ODF_REF, infoCouplerDisOdfRef.toString());
				}
				if (refCouplerToLine(infraOdfBO.getOdfId())) {
					infoLineRef = new StringBuffer();
					responseJson.put(NOT_REF_LINE, false);
					ArrayList<ViewODFCableBO> arrCouplerRefLine = infraOdfDao.getCableCodeRef(infraOdfBO.getOdfId());
					for (ViewODFCableBO couplerRefLine : arrCouplerRefLine) {
						infoLineRef.append(couplerRefLine.getCableCode() + Constains.SEMICOLON + Constains.SPACE);
					}
					String infoLineRefString = infoLineRef.substring(0, infoLineRef.length() - Constains.NUMBER_TWO);
					responseJson.put(INFO_LINE_REF, infoLineRefString);
				}
				responseJsonList.add(responseJson);
			}
		}
		return responseJsonList;
	}

	/**
	 * getMaxOdfIndexByStationId
	 *
	 * @param stationId
	 * @return ResponseEntity
	 * @author dungph
	 * @date 4/9/2019
	 */
	@Override
	public JSONObject getMaxOdfIndexByStationId(Long stationId) {
		responseJson.clear();
		responseJson.put("stationId", stationId);
		Integer odfIndex = getMaxOdfIndex(stationId);
		responseJson.put("odfIndex", odfIndex);
		return responseJson;
	}

	/**
	 * exportExcelChose
	 *
	 * @param listData, langCode, request
	 * @return ResponseEntity
	 * @author dungph
	 * @date 10/10/2019
	 */
	@Override
	public String exportExcelChose(List<ViewInfraOdfBO> listData, String langCode, HttpServletRequest request) {
		Long userId = CommonUtil.getUserId(request);
		if (CollectionUtils.isEmpty(listData)) {
			listData = infraOdfDao.getAllOdf( null, userId);
		}

		Workbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("odf.sheet.export", langCode));

		// Set title
		Row firstRow = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));
		Cell titleCell = firstRow.createCell(0);
		titleCell.setCellValue(messageSource.getMessage("odf.title.export", langCode));
		titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

		Row secondRow = sheet.createRow(2);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 5));
		Cell titleCell2 = secondRow.createCell(3);
		titleCell2.setCellValue(messageSource.getMessage("odf.report.date", langCode)
				+ CommonUtils.getStrDate(System.currentTimeMillis(), "hh:mm dd/MM/yyyy"));
		titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

//		 Set header
		List<HeaderDTO> headerDTOList = new ArrayList<>();
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.code", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("odf.code", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("odf.type", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("odf.ownerName", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("odf.vendorName", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("odf.deptCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("odf.installationDate", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("odf.note", langCode), 20 * 256));
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
		path = path + "EXPORT_ODF" + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm")
				+ ".xlsx";

		DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
		// Set data
		for (ViewInfraOdfBO item : listData) {

			Row row = sheet.createRow(rowNum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(index++);
			cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(item.getStationCode() != null ? item.getStationCode() : "");
			cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(item.getOdfCode() != null ? item.getOdfCode() : "");
			cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(item.getOdfTypeCode() != null ? item.getOdfTypeCode() : "");
			cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(item.getOwnerName() != null ? item.getOwnerName() : "");
			cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(item.getVendorName() != null ? item.getVendorName() : "");
			cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(item.getDeptCode() != null ? item.getDeptCode() : "");
			cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));
			Cell cell7 = row.createCell(7);
			if (item.getInstallationDate() != null) {
				cell7.setCellValue(df.format(item.getInstallationDate()));
			} else {
				cell7.setCellValue("");
			}
			cell7.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(item.getNote() != null ? item.getNote() : "");
			cell8.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));
		}

		try {
//	 Write file
			FileOutputStream outputStream = new FileOutputStream(path);
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			log.error("Exception", e);
		}

		return path;
	}

	/**
	 * method save odf
	 *
	 * @param infraOdfBO
	 * @return ResponseEntity
	 * @author dungph
	 * @date 26/8/2019
	 */
	@Override
	public ResponseEntity<?> saveOdf(InfraOdfBO infraOdfBO, String langCode) {
		Response<FormResult> response = new Response<>();
		if (Objects.isNull(infraOdfBO.getOdfTypeId())) {
			response.setMessage(messageSource.getMessage("odf.save.odfType.missing", langCode));
			response.setStatus(HttpStatus.PRECONDITION_REQUIRED.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else if (Objects.isNull(infraOdfBO.getOdfCode())) {
			response.setMessage(messageSource.getMessage("odf.save.odfCode.missing", langCode));
			response.setStatus(HttpStatus.PRECONDITION_REQUIRED.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else if (Objects.isNull(infraOdfBO.getStationId())) {
			response.setMessage(messageSource.getMessage("odf.save.stationId.missing", langCode));
			response.setStatus(HttpStatus.PRECONDITION_REQUIRED.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else if (Objects.isNull(infraOdfBO.getDeptId())) {
			response.setMessage(messageSource.getMessage("odf.save.deptId.missing", langCode));
			response.setStatus(HttpStatus.PRECONDITION_REQUIRED.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		try {
			CatOdfTypeBO catOdfTypeBO = catOdfTypeDao.getOdfTypeByOdfTypeId(infraOdfBO.getOdfTypeId());
			infraOdfBO.setRowStatus(Constains.NUMBER_ONE);
			// Create new ODF

			if (Objects.isNull(infraOdfBO.getOdfId())) {
				Long odfId = infraOdfDao.saveOrUpdateOdf(infraOdfBO);
				List<Long> list = new ArrayList<>();
				saveCoupler(catOdfTypeBO.getCapacity(), odfId, list);

			} else {
				// Update ODF
				ViewInfraOdfBO tempOdf = infraOdfDao.findOdfById(infraOdfBO.getOdfId());
				// check change ODF Type
				CatOdfTypeBO tempCatOdfType = catOdfTypeDao.getOdfTypeByOdfTypeId(tempOdf.getOdfTypeId());
				Integer newCapacity = catOdfTypeBO.getCapacity();
				Integer storageCapacity = tempCatOdfType.getCapacity();
				List<InfraCouplerBO> infraCouplerBOList = infraCouplerDao
						.getInfraCouplersByOdfId(infraOdfBO.getOdfId());
				List<InfraCouplerBO> unusedList = new ArrayList<>();
				List<Long> couplerNoList = new ArrayList<>();
				for (InfraCouplerBO item : infraCouplerBOList) {
					if (item.getStatuz().equals(Constains.NUMBER_ZERO)) {
						unusedList.add(item);
						couplerNoList.add(item.getCouplerNo());
					}
				}

				if (CommonUtils.isNullOrEmpty(unusedList)) {
					response.setMessage(messageSource.getMessage("odf.save.coupler.notAccept", langCode));
					response.setStatus(HttpStatus.CONFLICT.toString());
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

				// newCapacity > storageCapacity
				Integer compare = newCapacity - storageCapacity;
				if (((Integer) newCapacity.compareTo(storageCapacity)).equals(Constains.NUMBER_ONE)) {
					saveCoupler(compare, infraOdfBO.getOdfId(), couplerNoList);
				}

				// newCat # oldCat && storageCapacity >= newCapacity

				if (!catOdfTypeBO.equals(tempCatOdfType)
						&& ((Integer) storageCapacity.compareTo(newCapacity - 1)).equals(Constains.NUMBER_ONE)) {
					response.setMessage(messageSource.getMessage("odf.save.coupler.conflict", langCode));
					response.setStatus(HttpStatus.NOT_ACCEPTABLE.toString());
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				infraOdfBO.setUpdateTime(new Date());
				infraOdfDao.saveOrUpdateOdf(infraOdfBO);
			}
			response = new Response<>();
			response.setMessage("OK");
			response.setStatus(HttpStatus.OK.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception", e);
			response.setMessage(e.getMessage());
			response.setStatus(HttpStatus.NOT_MODIFIED.toString());
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}

	}

	/**
	 * getAllOdfByStationId
	 *
	 * @param viewInfraOdfBO
	 * @return ResponseEntity
	 * @author dungph
	 * @date 11/9/2019
	 */
	@Override
	public ResponseEntity<?> getAllOdfByStationId(ViewInfraOdfBO viewInfraOdfBO) {
		try {
			List<ViewInfraOdfBO> infraOdfBOs = infraOdfDao.findOdfByStationId(viewInfraOdfBO.getStationId());

			Integer first = viewInfraOdfBO.getFirst();
			Integer rows = viewInfraOdfBO.getRows();
			Integer size = infraOdfBOs.size();

			if (size <= 0)
				new ResponseEntity<>(HttpStatus.NOT_FOUND);

			FormResult result = new FormResult();
			result.setListData(infraOdfBOs.subList(first, Math.min(first + rows, size)));
			result.setFirst(first);
			result.setRows(infraOdfBOs.subList(first, Math.min(first + rows, size)).size());
			result.setTotalRecords((Long.parseLong(String.valueOf(size))));

			Response<FormResult> response = new Response<>();
			response.setContent(result);
			response.setStatus(HttpStatus.OK.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * method get all stations
	 *
	 * @return ResponseEntity
	 * @author dungph
	 * @date 26/8/2019
	 */
	@Override
	public ResponseEntity<?> getOdfLaneById(Long odfId) {
		try {
			List<ViewOdfLaneBO> viewOdfLaneBOList = infraOdfDao.getOdfLaneByOdfId(odfId);
			return new ResponseEntity<>(setListResponse(viewOdfLaneBOList), HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * downloadTeamplate
	 *
	 * @param importOdfDTO
	 * @return String
	 * @author hungnv
	 * @date 24/9/2019
	 */
	@Override
	public String downloadTeamplate(ImportOdfDTO importOdfDTO, Integer type, String langCode,
			HttpServletRequest request) {

		List<ViewInfraOdfBO> viewInfraOdfBOList = importOdfDTO.getResult();
		InputStream excelFile = null;
		XSSFWorkbook workbook = null;
		String classPath = "";

		// Get path current working folder
		String savePath = "";
		File dir = new File(savePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// chose option import ODF
		if (importOdfDTO.getType().equals(EnumTypeImport.IMPORT_ODF.type())) {
			if (type.equals(Constains.NUMBER_ONE)) {
				// class path example: /template/emp_Imp_ODF_insert.xlsx
				classPath = messageSource.getMessage("folder.template.import")
						+ messageSource.getMessage("file.name.odf.import.insert") + StringPool.PERIOD
						+ StringPool.SUFFIX_XLSX;

				savePath = savePath + messageSource.getMessage("file.name.odf.import.insert") + StringPool.UNDERLINE
						+ CommonUtils.getStrDate(System.currentTimeMillis(), Constants.DATE.YYYYMMDD)
						+ StringPool.PERIOD + StringPool.SUFFIX_XLSX;

			} else if (type.equals(Constains.NUMBER_TWO)) {
				// class path example: /template/emp_Imp_ODF_insert.xlsx
				classPath = messageSource.getMessage("folder.template.import")
						+ messageSource.getMessage("file.name.odf.import.update") + StringPool.PERIOD
						+ StringPool.SUFFIX_XLSX;

				savePath = savePath + messageSource.getMessage("file.name.odf.import.update") + StringPool.UNDERLINE
						+ CommonUtils.getStrDate(System.currentTimeMillis(), Constants.DATE.YYYYMMDD)
						+ StringPool.PERIOD + StringPool.SUFFIX_XLSX;
			}
			workbook = getPathFile(excelFile, workbook, classPath);
			if (Objects.nonNull(workbook)) {

				// Set data for sheet [Import ODF]
				setDataSheetImportODF(workbook, viewInfraOdfBOList);

				// Set data for sheet [DM_LoaiODF]
				setDataSheetTypeODF(workbook);

				// Set data for sheet [DM_DonVi]
				setDataSheetDept(workbook, request);

				// Set data for sheet [DM_ChuSoHuu]
				setDataSheetOwner(workbook);

				// Set data for sheet [DM_HangSanXuat]
				setDataSheetVendor(workbook);
			}

		} else if (importOdfDTO.getType().equals(EnumTypeImport.IMPORT_COUPLE_TO_LINE_ODF.type())) {
			if (type.equals(Constains.NUMBER_ONE)) {
				classPath = messageSource.getMessage("folder.template.import")
						+ messageSource.getMessage("file.name.welding.odf.insert") + StringPool.PERIOD
						+ StringPool.SUFFIX_XLSX;

				savePath = savePath + messageSource.getMessage("file.name.welding.odf.insert") + StringPool.UNDERLINE
						+ CommonUtils.getStrDate(System.currentTimeMillis(), Constants.DATE.YYYYMMDD)
						+ StringPool.PERIOD + StringPool.SUFFIX_XLSX;

			} else if (type.equals(Constains.NUMBER_TWO)) {

				classPath = messageSource.getMessage("folder.template.import")
						+ messageSource.getMessage("file.name.welding.odf.update") + StringPool.PERIOD
						+ StringPool.SUFFIX_XLSX;

				savePath = savePath + messageSource.getMessage("file.name.welding.odf.update") + StringPool.UNDERLINE
						+ CommonUtils.getStrDate(System.currentTimeMillis(), Constants.DATE.YYYYMMDD)
						+ StringPool.PERIOD + StringPool.SUFFIX_XLSX;
			}
			workbook = getPathFile(excelFile, workbook, classPath);

			if (Objects.nonNull(workbook)) {

				// Set data for sheet [Import_Han noi]
				setDataSheetWeldOdf(workbook, viewInfraOdfBOList);

				// Set data for sheet [DM_ODF]
				setDataSheetDMODF(workbook, request);

				// Set data for sheet [DM_CapDen]
				setDataSheetCable(workbook, request);
			}


		} else if (importOdfDTO.getType().equals(EnumTypeImport.IMPORT_COUPLE_TO_COUPLE_ODF.type())) {
			if (type.equals(Constains.NUMBER_ONE)) {
				classPath = messageSource.getMessage("folder.template.import")
						+ messageSource.getMessage("file.name.joint.odf.insert") + StringPool.PERIOD
						+ StringPool.SUFFIX_XLSX;

				savePath = savePath + messageSource.getMessage("file.name.joint.odf.insert") + StringPool.UNDERLINE
						+ CommonUtils.getStrDate(System.currentTimeMillis(), Constants.DATE.YYYYMMDD)
						+ StringPool.PERIOD + StringPool.SUFFIX_XLSX;

			} else if (type.equals(Constains.NUMBER_TWO)) {

				classPath = messageSource.getMessage("folder.template.import")
						+ messageSource.getMessage("file.name.joint.odf.update") + StringPool.PERIOD
						+ StringPool.SUFFIX_XLSX;

				savePath = savePath + messageSource.getMessage("file.name.joint.odf.update")
						+ StringPool.PERIOD + StringPool.SUFFIX_XLSX;
			}
			workbook = getPathFile(excelFile, workbook, classPath);
			if (Objects.nonNull(workbook)) {

				// Set data for sheet [Import Han noi]
				setDataSheetImportCoupletoCouple(workbook, viewInfraOdfBOList);

				// Set data for sheet [DM_ODF]
				setDataSheetDMODF(workbook, request);
			}
		}

		try {
			// Write file
			FileOutputStream outputStream = new FileOutputStream(savePath);
			if (Objects.nonNull(workbook)) {
				workbook.write(outputStream);
			}

			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			log.error("Exception", e);
		} catch (IOException e) {
			log.error("Exception", e);
			return Constains.BLANK;
		}
		return savePath;
	}

	/**
	 * Import Odf
	 *
	 * @param file, String langCode,
	 * @author hungnv
	 * @date 28/9/2019
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String importOdf(MultipartFile file, String langCode, HttpServletRequest request, String type) {
		// count record success
		Integer successRecord = Constains.NUMBER_ZERO;
		// count record error
		Integer errRecord = Constains.NUMBER_ZERO;
		// get user id
		Long userId = CommonUtil.getUserId(request);
		// get list odf
		List<InfraOdfDTO> datas = new ArrayList<InfraOdfDTO>();
		// trafer data
		InfraOdfBO entity;
		// maping error data
		Map<String, String> errData;
		// flag Y/N for Update data
		InfraOdfDTO flagList;

		try {
			// check header is changed
			InfraOdfDTO header = ExcelDataUltils.getHeaderList(file, new InfraOdfDTO());
			if (Boolean.FALSE.equals(messageSource.checkEqualHeader(header, langCode))) {
				return "template-error";
			}
			datas = (List<InfraOdfDTO>) ExcelDataUltils.getListInExcel(file, new InfraOdfDTO());
			flagList = ExcelDataUltils.getFlagList(file, new InfraOdfDTO());
		} catch (Exception e) {
			log.error("Exception", e);
			return "template-error";
		}

		Map<Long, String> mapObj = new HashMap<>();
		long index = Constains.NUMBER_ZERO;
		// begin reading
		for (InfraOdfDTO infraOdfDTO : datas) {
			errData = new HashMap<>();
			entity = new InfraOdfBO();
			Integer maxOdfIndex;
			Boolean isExistStation = false;
			// insert ODF
			String odfIndex = convertIndex(infraOdfDTO.getOdfIndex());
			if (type.equals(Constant.CHANGE_TYPE.ADD)) {
				// station code
				if (CommonUtils.isNullOrEmpty(infraOdfDTO.getStationCode())) {
					ExcelDataUltils.addMessage(errData, "err.empty",
							messageSource.getMessage("odf.stationCode", langCode));
				} else {
					if (infraOdfDTO.getStationCode().length() > 50) {
						ExcelDataUltils.addMessage(errData, "err.maxLength.50",
								messageSource.getMessage("odf.stationCode", langCode));

					} else {
						if (Objects.isNull(infraStationDao.findStationByCode(infraOdfDTO.getStationCode(), userId))) { 
							ExcelDataUltils.addMessage(errData, "err.incorrect",
									messageSource.getMessage("odf.stationCode", langCode));
						} else {
							isExistStation = true;
							if (Objects.isNull(
									infraStationDao.findStationByCode(infraOdfDTO.getStationCode(), userId).getStationId())) {
								ExcelDataUltils.addMessage(errData, "err.numberFormat",
										messageSource.getMessage("odf.odfIndex", langCode));
							}
							entity.setStationId(
									infraStationDao.findStationByCode(infraOdfDTO.getStationCode(), userId).getStationId());
						}
					}
				}

				// odf index
				if (CommonUtils.isNullOrEmpty(infraOdfDTO.getOdfIndex())) {
					if (CommonUtils.isNullOrEmpty(infraOdfDTO.getStationCode())) {
						ExcelDataUltils.addMessage(errData, "err.empty",
								messageSource.getMessage("odf.deptCode", langCode));
					} else {
						if (Boolean.TRUE.equals(isExistStation)) {
							if (Objects.isNull(
									infraStationDao.findStationByCode(infraOdfDTO.getStationCode(), userId).getStationId())) {
								ExcelDataUltils.addMessage(errData, "err.numberFormat",
										messageSource.getMessage("odf.odfIndex", langCode));
							}
							maxOdfIndex = getMaxOdfIndex(
									infraStationDao.findStationByCode(infraOdfDTO.getStationCode(), userId).getStationId());
							if (maxOdfIndex < 99) {
								entity.setOdfCode(infraOdfDTO.getStationCode() + StringPool.MINUS + OdfStringUtils.ODF + StringPool.MINUS
										+ (++maxOdfIndex));
								infraOdfDTO.setOdfIndex(maxOdfIndex.toString());
								infraOdfDTO.setOdfCode(entity.getOdfCode());
							} else {
								ExcelDataUltils.addMessage(errData, "err.notGenerate.odfIndex",
										messageSource.getMessage("odf.stationCode", langCode));
							}
						}

					}

					// if odfIndex is 2 digit number
					} else if (OdfStringUtils.TWO_DIGIT_NUMBER.matcher(odfIndex).matches()) {
						infraOdfDTO.setOdfIndex(odfIndex);
						ViewInfraOdfBO viewInfraOdfBO = infraOdfDao.findOdfByCode(infraOdfDTO.getStationCode()
							+ StringPool.MINUS + OdfStringUtils.ODF + StringPool.MINUS + infraOdfDTO.getOdfIndex());

						if (Objects.nonNull(viewInfraOdfBO)) {
						ExcelDataUltils.addMessage(errData, "err.exits",
								messageSource.getMessage("odf.odfIndex", langCode));
						} else {
							entity.setOdfCode(infraOdfDTO.getStationCode() + StringPool.MINUS + OdfStringUtils.ODF + StringPool.MINUS
								+ infraOdfDTO.getOdfIndex());
						}

				} else {
					ExcelDataUltils.addMessage(errData, "err.odfIndex.numberFormat",
							messageSource.getMessage("odf.odfIndex", langCode));
				}

				// Dept code
				// Insert department id

				if (CommonUtils.isNullOrEmpty(infraOdfDTO.getDeptCode())) {
					ExcelDataUltils.addMessage(errData, "err.empty",
							messageSource.getMessage("odf.deptCode", langCode));
				} else {
					ViewCatDepartmentBO viewCatDepartmentBO = transmissionDao
							.findDepartmentByCode(infraOdfDTO.getDeptCode(), userId);
					if (Objects.isNull(viewCatDepartmentBO)) {
						ExcelDataUltils.addMessage(errData, "err.noscope",
								messageSource.getMessage("odf.deptCode", langCode));
					} else {
						if (Objects.isNull(viewCatDepartmentBO.getDeptId())) {
							ExcelDataUltils.addMessage(errData, "err.noscope",
									messageSource.getMessage("odf.deptCode", langCode));
						}
						entity.setDeptId(viewCatDepartmentBO.getDeptId());
					}
				}

				// Type Odf
				List<InfraCouplerBO> infraCouplerBOList = infraCouplerDao
						.getInfraCouplersByOdfId(entity.getOdfId());

				List<InfraCouplerBO> unUsedCouplerList = new ArrayList<>();
				for (InfraCouplerBO item : infraCouplerBOList) {
					if (item.getStatuz().equals(Constains.NUMBER_ZERO)) {
						unUsedCouplerList.add(item);
					}
				}

				if (CommonUtils.isNullOrEmpty(infraOdfDTO.getOdfTypeCode())) {
					ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("odf.type", langCode));
				} else {
					if (infraOdfDTO.getOdfTypeCode().length() > 100) {
						ExcelDataUltils.addMessage(errData, "err.maxLength.100",
								messageSource.getMessage("odf.type", langCode));
					} else {
						CatOdfTypeBO catOdfTypeBO = catOdfTypeDao.getOdfTypeByCode(infraOdfDTO.getOdfTypeCode());

						if (CommonUtils.isNullOrEmpty(catOdfTypeBO.getOdfTypeId().toString())) {
							ExcelDataUltils.addMessage(errData, "err.incorrect",
									messageSource.getMessage("odf.deptCode", langCode));
						} else {
							if (CommonUtils.isNullOrEmpty(catOdfTypeDao.getOdfTypeByCode(infraOdfDTO.getOdfTypeCode())
									.getOdfTypeId().toString())) {
								ExcelDataUltils.addMessage(errData, "err.incorrect",
										messageSource.getMessage("odf.type", langCode));
							} else {
								entity.setOdfTypeId(
										catOdfTypeDao.getOdfTypeByCode(infraOdfDTO.getOdfTypeCode()).getOdfTypeId());
							}
						}
					}
				}

				// owner id
				if (CommonUtils.isNullOrEmpty(infraOdfDTO.getOwnerName())) {
					ExcelDataUltils.addMessage(errData, "err.choose",
							messageSource.getMessage("odf.ownerName", langCode));
				} else {
					if (infraOdfDTO.getOwnerName().length() > 100) {
						ExcelDataUltils.addMessage(errData, "err.maxLength.100",
								messageSource.getMessage("odf.ownerName", langCode));
					} else {
						List<ViewCatItemBO> listOwner = transmissionDao.findCatItemByCategoryId(OdfStringUtils.CAT_OWNER);
						Integer countStatus = 0;
						for (ViewCatItemBO viewCatItemBO : listOwner) {
							if (infraOdfDTO.getOwnerName().trim().equals(viewCatItemBO.getItemName().trim())) {
								if (Objects.isNull(viewCatItemBO.getItemId())) {
									ExcelDataUltils.addMessage(errData, "err.incorrect",
											messageSource.getMessage("odf.ownerName", langCode));
								} else {
									entity.setOwnerId(viewCatItemBO.getItemId());
									countStatus++;
									break;
								}
							}
						}
						if (countStatus.equals(Constains.NUMBER_ZERO)) {
							ExcelDataUltils.addMessage(errData, "err.incorrect",
									messageSource.getMessage("odf.ownerName", langCode));
						}
					}
				}

				if (CommonUtils.isNullOrEmpty(infraOdfDTO.getVendorName())) {
					entity.setVendorId(null);
				} else {
					// vendor name, insert vendor id
					if (infraOdfDTO.getVendorName().length() > 200) {
						ExcelDataUltils.addMessage(errData, "err.maxLength.200",
								messageSource.getMessage("odf.vendorName", langCode));
					} else {
						List<ViewCatItemBO> listVendor = transmissionDao.findCatItemByCategoryId(OdfStringUtils.VENDOR);
						Integer countStatus = Constains.NUMBER_ZERO;
						for (ViewCatItemBO viewCatItemBO : listVendor) {
							if (infraOdfDTO.getVendorName().trim().equals(viewCatItemBO.getItemName().trim())) {
								countStatus ++;
								if (Objects.isNull(viewCatItemBO.getItemId())) {
									ExcelDataUltils.addMessage(errData, "err.incorrect",
											messageSource.getMessage("odf.vendorName", langCode));
								} else {
									entity.setVendorId(viewCatItemBO.getItemId());
								}
							}
						}
						if (countStatus.equals(Constains.NUMBER_ZERO)) {
							ExcelDataUltils.addMessage(errData, "err.incorrect",
									messageSource.getMessage("odf.vendorName", langCode));
						}
					}
				}

				// installation date
				Date dateFormat;
				try {
					if (CommonUtils.isNullOrEmpty(infraOdfDTO.getInstallationDate())) {
						entity.setInstallationDate(null);
					} else {
						if (OdfStringUtils.PATTERN_DATETIME_DDMMYYYY.matcher(infraOdfDTO.getInstallationDate()).matches()) {
							if (isValidDate(infraOdfDTO.getInstallationDate(), Constants.DATE.DEFAULT_FORMAT)) {
								dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT)
										.parse(infraOdfDTO.getInstallationDate());
								java.sql.Date sqlDate = new java.sql.Date(dateFormat.getTime());
								entity.setInstallationDate(sqlDate);
							} else {
								ExcelDataUltils.addMessage(errData, "err.not.valiable",
										messageSource.getMessage("odf.installationDate", langCode));
							}
						} else {
							ExcelDataUltils.addMessage(errData, "err.dateFormat",
									messageSource.getMessage("odf.installationDate", langCode));
						}
					}
				} catch (ParseException e) {
					log.error("Exception", e);
				}

				// note
				if (infraOdfDTO.getNote().length() > 500) {
					ExcelDataUltils.addMessage(errData, "err.maxLength.500",
							messageSource.getMessage("odf.note", langCode));
				} else {
					if (CommonUtils.isNullOrEmpty(infraOdfDTO.getNote())) {
						entity.setNote(StringPool.BLANK);
					}
					entity.setNote(infraOdfDTO.getNote());
				}

				//
				entity.setCreateTime(new Date());

				// row status
				entity.setRowStatus(Constains.NUMBER_ONE);

				// UPDATE TAB
			} else if (type.equals(Constant.CHANGE_TYPE.UPDATE)) {
			
					String odfCode = infraOdfDTO.getOdfCode();
					entity = infraOdfDao.selectOdfByCode(odfCode);

					// Dept code
					// Insert department id
					if (flagList.getDeptCode().equals(OdfStringUtils.ALLOW_UPDATE)) {
						if (CommonUtils.isNullOrEmpty(infraOdfDTO.getDeptCode())) {
							ExcelDataUltils.addMessage(errData, "err.empty",
									messageSource.getMessage("odf.deptCode", langCode));
						} else {
							ViewCatDepartmentBO viewCatDepartmentBO = transmissionDao
									.findDepartmentByCode(infraOdfDTO.getDeptCode(), userId);
							if (Objects.isNull(viewCatDepartmentBO)) {
								ExcelDataUltils.addMessage(errData, "err.noscope",
										messageSource.getMessage("odf.deptCode", langCode));
							} else {
								if (Objects.isNull(viewCatDepartmentBO.getDeptId())) {
									ExcelDataUltils.addMessage(errData, "err.noscope",
											messageSource.getMessage("odf.deptCode", langCode));
								}
								entity.setDeptId(viewCatDepartmentBO.getDeptId());
							}
						}
					}
					Integer isExist = 0;
					// Type Odf
					List<InfraCouplerBO> infraCouplerBOList = infraCouplerDao
							.getInfraCouplersByOdfId(entity.getOdfId());

					List<InfraCouplerBO> unUsedCouplerList = new ArrayList<>();
					for (InfraCouplerBO item : infraCouplerBOList) {
						if (item.getStatuz().equals(Constains.NUMBER_ONE)) {
							unUsedCouplerList.add(item);
						}
					} 
					if (flagList.getOdfTypeCode().equals(OdfStringUtils.ALLOW_UPDATE)) {
						if (CommonUtils.isNullOrEmpty(infraOdfDTO.getOdfTypeCode())) {
							ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("odf.type", langCode));
						} else {
							if (infraOdfDTO.getOdfTypeCode().length() > 100) {
								ExcelDataUltils.addMessage(errData, "err.maxLength.100",
										messageSource.getMessage("odf.type", langCode));
							} else {
								if (Objects.isNull(catOdfTypeDao.getOdfTypeByCode(infraOdfDTO.getOdfTypeCode()))) {
									ExcelDataUltils.addMessage(errData, "err.incorrect",
											messageSource.getMessage("odf.type", langCode));
								} else {
									for (InfraCouplerBO infraCouplerBO : unUsedCouplerList) {
										if (infraOdfDTO.getOdfCode().equals(infraOdfDao.findOdfById(infraCouplerBO.getOdfId()).getOdfCode())) {
											isExist ++;
										} 
									}
									if (isExist.equals(Constains.NUMBER_ZERO)) {
										entity.setOdfTypeId(
												catOdfTypeDao.getOdfTypeByCode(infraOdfDTO.getOdfTypeCode()).getOdfTypeId());
									} else {
										ViewInfraOdfBO viewInfraOdfBO = infraOdfDao.findOdfByCode(infraOdfDTO.getStationCode()
												+ StringPool.MINUS + "ODF" + StringPool.MINUS + infraOdfDTO.getOdfIndex());
										if (infraOdfDTO.getOdfTypeCode().equals(viewInfraOdfBO.getOdfTypeCode())) {
											catOdfTypeDao.getOdfTypeByCode(infraOdfDTO.getOdfTypeCode()).getOdfTypeId();
										} else {
											ExcelDataUltils.addMessage(errData, "err.odf.type.coupler.joint",
													messageSource.getMessage("odf.type", langCode));
										}
									}
								}
							}
						}
					}

					// owner id
					if (flagList.getOwnerName().equals(OdfStringUtils.ALLOW_UPDATE)) {
						if (CommonUtils.isNullOrEmpty(infraOdfDTO.getOwnerName())) {
							ExcelDataUltils.addMessage(errData, "err.choose",
									messageSource.getMessage("odf.ownerName", langCode));
						} else {
							if (infraOdfDTO.getOwnerName().length() > 100) {
								ExcelDataUltils.addMessage(errData, "err.maxLength.100",
										messageSource.getMessage("odf.ownerName", langCode));
							} else {
								List<ViewCatItemBO> listOwner = transmissionDao.findCatItemByCategoryId(OdfStringUtils.CAT_OWNER);
								Integer countStatus = 0;
								for (ViewCatItemBO viewCatItemBO : listOwner) {
									if (infraOdfDTO.getOwnerName().trim().equals(viewCatItemBO.getItemName().trim())) {
										if (Objects.isNull(viewCatItemBO.getItemId())) {
											ExcelDataUltils.addMessage(errData, "err.incorrect",
													messageSource.getMessage("odf.ownerName", langCode));
										} else {
											entity.setOwnerId(viewCatItemBO.getItemId());
											countStatus++;
											break;
										}
									}
								}
								if (countStatus.equals(Constains.NUMBER_ZERO)) {
									ExcelDataUltils.addMessage(errData, "err.incorrect",
											messageSource.getMessage("odf.ownerName", langCode));
								}
							}
						}
					}

					if (flagList.getVendorName().equals(OdfStringUtils.ALLOW_UPDATE)) {
						if (CommonUtils.isNullOrEmpty(infraOdfDTO.getVendorName())) {
							entity.setVendorId(null);
						} else {
							// vendor name, insert vendor id
							if (infraOdfDTO.getVendorName().length() > 200) {
								ExcelDataUltils.addMessage(errData, "err.maxLength.200",
										messageSource.getMessage("odf.vendorName", langCode));
							} else {
								List<ViewCatItemBO> listVendor = transmissionDao.findCatItemByCategoryId(OdfStringUtils.VENDOR);
								Integer countStatus = Constains.NUMBER_ZERO;
								for (ViewCatItemBO viewCatItemBO : listVendor) {
									if (infraOdfDTO.getVendorName().trim().equals(viewCatItemBO.getItemName().trim())) {
										countStatus ++;
										if (Objects.isNull(viewCatItemBO.getItemId())) {
											ExcelDataUltils.addMessage(errData, "err.incorrect",
													messageSource.getMessage("odf.vendorName", langCode));
										} else {
											entity.setVendorId(viewCatItemBO.getItemId());
										}
									}
								}
								if (countStatus.equals(Constains.NUMBER_ZERO)) {
									ExcelDataUltils.addMessage(errData, "err.incorrect",
											messageSource.getMessage("odf.vendorName", langCode));
								}
							}
						}

					}

					if (flagList.getInstallationDate().equals(OdfStringUtils.ALLOW_UPDATE)) {

						Date dateFormat;
						try {
							if (!CommonUtils.isNullOrEmpty(infraOdfDTO.getInstallationDate())) {
								if (OdfStringUtils.PATTERN_DATETIME_DDMMYYYY.matcher(infraOdfDTO.getInstallationDate()).matches()) {
									if (isValidDate(infraOdfDTO.getInstallationDate(), Constants.DATE.DEFAULT_FORMAT)) {
										dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT)
												.parse(infraOdfDTO.getInstallationDate());
										java.sql.Date sqlDate = new java.sql.Date(dateFormat.getTime());
										entity.setInstallationDate(sqlDate);
									} else {
										ExcelDataUltils.addMessage(errData, "err.not.valiable",
												messageSource.getMessage("odf.installationDate", langCode));
									}
								} else {
									ExcelDataUltils.addMessage(errData, "err.dateFormat",
											messageSource.getMessage("odf.installationDate", langCode));
								}

							} else {
								entity.setInstallationDate(null);
							}
						} catch (ParseException e) {
							log.error("Exception", e);
						}
					}

					if (flagList.getNote().equals(OdfStringUtils.ALLOW_UPDATE)) {
						// note
						if (infraOdfDTO.getNote().length() > 500) {
							ExcelDataUltils.addMessage(errData, "err.maxLength.500",
									messageSource.getMessage("odf.note", langCode));
						} else {
							if (CommonUtils.isNullOrEmpty(infraOdfDTO.getNote())) {
								entity.setNote(StringPool.BLANK);
							}
							entity.setNote(infraOdfDTO.getNote());
						}
					}

					entity.setUpdateTime(new Date());
					// row status
					entity.setRowStatus(Constains.NUMBER_ONE);
			}

			if (errData.isEmpty()) {
				saveOdf(entity, langCode);
				successRecord++;
			} else {
				errRecord++;
			}

			StringBuilder rs = new StringBuilder();
			List<String> errList = new ArrayList<>();
			for (Map.Entry<String, String> entry : errData.entrySet()) {
				String temp = StringPool.BLANK;
				String key = entry.getKey();
				String value = entry.getValue();
				if (Objects.isNull(value)) {
					temp = temp + value + StringPool.NEW_LINE;
				} else {
					temp = temp + messageSource.getMessage(key, langCode, Arrays.asList(value)) + StringPool.NEW_LINE;
				}
				errList.add(temp);
			}

			for (int i = errList.size() - 1; i >= 0; i--) {
				rs.append(errList.get(i));
			}
			if (CommonUtils.isNullOrEmpty(rs.toString())) {
				rs.append(Constant.RESULT.OK);
			}
			mapObj.put(index++, rs.toString());

		}

		String savePath = ExcelDataUltils.writeResultExcel(file, new InfraOdfDTO(), mapObj,
				messageSource.getMessage("file.name.odf.import.result", langCode),datas);
		if (CommonUtils.isNullOrEmpty(savePath)) {
			return savePath;
		}
		return savePath + StringPool.PLUS + successRecord + StringPool.PLUS + errRecord;
	}

	/**
	 * Import Welding Odf [Han noi]
	 *
	 * @param file, String langCode,
	 * @author hungnv
	 * @date 28/9/2019
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String importWeldingOdf(MultipartFile file, String langCode, String type, HttpServletRequest request) {
		Integer successRecord = Constains.NUMBER_ZERO;
		Integer errRecord = Constains.NUMBER_ZERO;
		long index = Constains.NUMBER_ZERO;

		CntCouplerToLineBO entity;
		Map<String, String> errData;
		List<WeldingOdfDTO> datas = new ArrayList<WeldingOdfDTO>();
		WeldingOdfDTO flagList;
		try {
			// check header is changed
			WeldingOdfDTO header = ExcelDataUltils.getHeaderList(file, new WeldingOdfDTO());
			if (!messageSource.checkEqualHeader(header, langCode)) {
				return "template-error";
			}
			datas = (List<WeldingOdfDTO>) ExcelDataUltils.getListInExcel(file, new WeldingOdfDTO());
			flagList = ExcelDataUltils.getFlagList(file, new WeldingOdfDTO());
		} catch (Exception e) {
			log.error("Exception", e);
			return "template-error";
		}

		Map<Long, String> mapObj = new HashMap<>();

		// begin reading
		for (WeldingOdfDTO weldingOdfDTO : datas) {
			errData = new HashMap<>();
			entity = new CntCouplerToLineBO();
			boolean isOdfCodeExist = false;
			Long cableId ;
			// Add Han noi
			if (type.equals(Constant.CHANGE_TYPE.ADD)) {

				
				// insert ODF code
				if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getOdfCode())) {
					ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("odf.code", langCode));
				} else {
					ViewInfraOdfBO viewInfraOdfBO = infraOdfDao.findOdfByCode(weldingOdfDTO.getOdfCode());
					if (!Objects.isNull(viewInfraOdfBO)) {
						if (CommonUtils.isNullOrEmpty(viewInfraOdfBO.getOdfId().toString())) {
							ExcelDataUltils.addMessage(errData, "err.incorrect",
									messageSource.getMessage("odf.code", langCode));
						} else {
							entity.setOdfId(infraOdfDao.findOdfByCode(weldingOdfDTO.getOdfCode()).getOdfId());
							isOdfCodeExist = true;
						}

					} else {
						ExcelDataUltils.addMessage(errData, "err.incorrect",
								messageSource.getMessage("odf.code", langCode));
					}
				}

				// coupler no
				if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getCouplerNo())) {
					ExcelDataUltils.addMessage(errData, "err.empty",
							messageSource.getMessage("odf.couplerNo", langCode));

				} else if (!StringUtils.isNumeric(weldingOdfDTO.getCouplerNo())) {
					ExcelDataUltils.addMessage(errData, "err.number.thanZero.couplerNo",
							messageSource.getMessage("odf.couplerNo", langCode));
				} else {
					if (isOdfCodeExist) {
						if (Integer.parseInt(weldingOdfDTO.getCouplerNo()) > 0 ) {
							if (OdfStringUtils.ONE_TO_FIVE_DIGIT_NUMBER.matcher(weldingOdfDTO.getCouplerNo()).matches()) {
								List<Long> listCoupler = getCouplerNoAvailability(
										infraOdfDao.findOdfByCode(weldingOdfDTO.getOdfCode()).getOdfId());
								Integer countEmpty = Constains.NUMBER_ZERO;
								Long couplerNoLong = new Long(weldingOdfDTO.getCouplerNo());
								for (Long couplerNo : listCoupler) {
									if (couplerNoLong.equals(couplerNo)) {
										entity.setCouplerNo(Integer.parseInt(weldingOdfDTO.getCouplerNo()));
										countEmpty++;
									}
								}

								if (countEmpty.equals(Constains.NUMBER_ZERO)) {
									ExcelDataUltils.addMessage(errData, "err.incorrect",
											messageSource.getMessage("odf.couplerNo", langCode));
								}
							} else {
								ExcelDataUltils.addMessage(errData, "err.number.couplerNo",
										messageSource.getMessage("odf.couplerNo", langCode));
							}

						} else {
							ExcelDataUltils.addMessage(errData, "err.number.thanZero.couplerNo",
									messageSource.getMessage("odf.couplerNo", langCode));
						}
					}
				}

				// Insert cable id

				ViewInfraOdfBO viewInfraOdfBO = infraOdfDao.findOdfByCode(weldingOdfDTO.getOdfCode());
				Long stationIdOfODF = viewInfraOdfBO.getStationId();
				InfraCablesBO infraCablesBO = infraCablesDao.finByCableCode(weldingOdfDTO.getCableCode());
				if (Objects.nonNull(infraCablesBO) ) {
					if (stationIdOfODF.equals(infraCablesBO.getDestId()) || stationIdOfODF.equals(infraCablesBO.getSourceId())) {
						if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getCableCode())) {
							ExcelDataUltils.addMessage(errData, "err.empty",
									messageSource.getMessage("odf.cableCode", langCode));
						} else {
							cableId = infraCablesDao.finByCableCode(weldingOdfDTO.getCableCode()).getCableId();
							if (Objects.isNull(cableId)) {
								ExcelDataUltils.addMessage(errData, "err.incorrect",
										messageSource.getMessage("odf.cableCode", langCode));
							} else {
								entity.setCableId(cableId);
							}
						}
					} else {
						ExcelDataUltils.addMessage(errData, "err.odf.cable.not.together.odf",
								messageSource.getMessage("odf.cableCode", langCode));
					}
				} else {
					ExcelDataUltils.addMessage(errData, "err.incorrect",
							messageSource.getMessage("odf.cableCode", langCode));
				}

				// Insert Line Cable
				if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getLineNo())) {
					ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("odf.lineNo", langCode));

				} else if (!StringUtils.isNumeric(weldingOdfDTO.getLineNo())) {
					ExcelDataUltils.addMessage(errData, "err.number.thanZero.couplerNo",
							messageSource.getMessage("odf.lineNo", langCode));

				} else {
					if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getLineNo())) {
						entity.setLineNo(null);
					} else {
						if (isOdfCodeExist) {
							if (!Objects.isNull(infraCablesDao.finByCableCode(weldingOdfDTO.getCableCode()))) {
								cableId = infraCablesDao.finByCableCode(weldingOdfDTO.getCableCode()).getCableId();
								List<Long> listLineNo = getLineNosAvailability(cableId);
								Integer countEmpty = 0;
								for (Long lineNo : listLineNo) {
									if (weldingOdfDTO.getLineNo().equals(lineNo.toString())) {
										entity.setLineNo(Integer.parseInt(weldingOdfDTO.getLineNo()));
										countEmpty++;
									}
								}
								if (countEmpty.equals(Constains.NUMBER_ZERO)) {
									ExcelDataUltils.addMessage(errData, "err.incorrect",
											messageSource.getMessage("odf.lineNo", langCode));
								}
							} else {
								ExcelDataUltils.addMessage(errData, "err.incorrect",
										messageSource.getMessage("odf.lineNo", langCode));
							}
						}
					}
				}

				// create user
				if (weldingOdfDTO.getCreateUser().length() > 200) {
					ExcelDataUltils.addMessage(errData, "err.maxLength.200",
							messageSource.getMessage("odf.welding.createUser", langCode));
				} else {
					entity.setCreateUser(weldingOdfDTO.getCreateUser());
				}

				// no attenuation
				String attenuation = weldingOdfDTO.getAttenuation();
				if (weldingOdfDTO.getAttenuation().length() > 19) {
					ExcelDataUltils.addMessage(errData, "err.maxLength.19",
							messageSource.getMessage("odf.attenuation", langCode));
				} else {
					if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getAttenuation())) {
						Long attenuationLong = 0L;
						entity.setAttenuation(BigDecimal.valueOf(attenuationLong));
					} else {
						if (OdfStringUtils.PATTERN_ATTENUATION.matcher(attenuation).matches() && Double.parseDouble(attenuation) != 0) {
							if (attenuation.split(OdfStringUtils.PATTERN_DOT)[0].length() <= 13 ) {
								entity.setAttenuation(BigDecimal.valueOf(Double.parseDouble(attenuation)));
							} else {
								ExcelDataUltils.addMessage(errData, "err.overlength",
										messageSource.getMessage("odf.attenuation", langCode));
							}
						} else {
							ExcelDataUltils.addMessage(errData, "err.actual.number",
									messageSource.getMessage("odf.attenuation", langCode));
						}

					}

				}

				// create date
				if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getCreateDate())) {
					java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
					entity.setCreateDate(sqlDate);

				} else {
					Date dateFormat;
					try {
						if (OdfStringUtils.PATTERN_DATETIME_DDMMYYYY.matcher(weldingOdfDTO.getCreateDate()).matches()) {
							if (isValidDate(weldingOdfDTO.getCreateDate(), Constants.DATE.DEFAULT_FORMAT)) {
								dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT)
										.parse(weldingOdfDTO.getCreateDate());
								java.sql.Date sqlDate = new java.sql.Date(dateFormat.getTime());
								entity.setCreateDate(sqlDate);
							} else {
								ExcelDataUltils.addMessage(errData, "err.not.valiable",
										messageSource.getMessage("odf.welding.createDate", langCode));
							}
						} else {
							ExcelDataUltils.addMessage(errData, "err.dateFormat",
									messageSource.getMessage("odf.welding.createDate", langCode));
						}

					} catch (ParseException e) {
						log.error("Exception", e);
					}
				}

				// create note
				if (weldingOdfDTO.getNote().length() > 500) {
					ExcelDataUltils.addMessage(errData, "err.maxLength.500",
							messageSource.getMessage("odf.note", langCode));
				} else {
					if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getNote())) {
						weldingOdfDTO.setNote(StringPool.BLANK);
					}
					entity.setNote(weldingOdfDTO.getNote());
				}

			// UPDATE HAN NOI
			} else if (type.equals(Constant.CHANGE_TYPE.UPDATE)) {
				try {

					flagList = ExcelDataUltils.getFlagList(file, new WeldingOdfDTO());
					
					Long odfId = infraOdfDao.findOdfByCode(weldingOdfDTO.getOdfCode()).getOdfId();
					Integer couplerNo = Integer.parseInt(weldingOdfDTO.getCouplerNo());
					cableId = infraCablesDao.finByCableCode(weldingOdfDTO.getCableCode()).getCableId();
					Integer lineNo = Integer.parseInt(weldingOdfDTO.getLineNo());
					entity = new CntCouplerToLineBO();

					entity = weldingOdfDao.selectWeldingOdfByCode(odfId, couplerNo, cableId, lineNo);
					
					// create user
					if (flagList.getCreateUser().equals(OdfStringUtils.ALLOW_UPDATE)) {
						if (weldingOdfDTO.getCreateUser().length() > 200) {
							ExcelDataUltils.addMessage(errData, "err.maxLength.200",
									messageSource.getMessage("odf.welding.createUser", langCode));
						} else {
							entity.setCreateUser(weldingOdfDTO.getCreateUser());
						}
					}

					// no attenuation
					if (flagList.getAttenuation().equals(OdfStringUtils.ALLOW_UPDATE)) {
						String attenuation = weldingOdfDTO.getAttenuation();
						if (weldingOdfDTO.getAttenuation().length() > 19) {
							ExcelDataUltils.addMessage(errData, "err.maxLength.19",
									messageSource.getMessage("odf.attenuation", langCode));
						} else {
							if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getAttenuation())) {
								Long attenuationLong = 0L;
								entity.setAttenuation(BigDecimal.valueOf(attenuationLong));
							} else {
								if (OdfStringUtils.PATTERN_ATTENUATION.matcher(attenuation).matches() && Double.parseDouble(attenuation) != 0) {
									if (attenuation.split(OdfStringUtils.PATTERN_DOT)[0].length() <= 13 ) {
										entity.setAttenuation(BigDecimal.valueOf(Double.parseDouble(attenuation)));
									} else {
										ExcelDataUltils.addMessage(errData, "err.overlength",
												messageSource.getMessage("odf.attenuation", langCode));
									}
								} else {
									ExcelDataUltils.addMessage(errData, "err.actual.number",
											messageSource.getMessage("odf.attenuation", langCode));
								}

							}

						}
					}

					// create date
					if (flagList.getCreateDate().equals(OdfStringUtils.ALLOW_UPDATE)) {
						if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getCreateDate())) {
							java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
							entity.setCreateDate(sqlDate);

						} else {
							Date dateFormat;
							try {
								if (OdfStringUtils.PATTERN_DATETIME_DDMMYYYY.matcher(weldingOdfDTO.getCreateDate()).matches()) {
									if (isValidDate(weldingOdfDTO.getCreateDate(), Constants.DATE.DEFAULT_FORMAT)) {
										dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT)
												.parse(weldingOdfDTO.getCreateDate());
										java.sql.Date sqlDate = new java.sql.Date(dateFormat.getTime());
										entity.setCreateDate(sqlDate);
									} else {
										ExcelDataUltils.addMessage(errData, "err.not.valiable",
												messageSource.getMessage("odf.welding.createDate", langCode));
									}
								} else {
									ExcelDataUltils.addMessage(errData, "err.dateFormat",
											messageSource.getMessage("odf.welding.createDate", langCode));
								}

							} catch (ParseException e) {
								log.error("Exception", e);
							}
						}
					}

					// create note
					if (flagList.getNote().equals(OdfStringUtils.ALLOW_UPDATE)) {
						if (weldingOdfDTO.getNote().length() > 500) {
							ExcelDataUltils.addMessage(errData, "err.maxLength.500",
									messageSource.getMessage("odf.note", langCode));
						} else {
							if (CommonUtils.isNullOrEmpty(weldingOdfDTO.getNote())) {
								weldingOdfDTO.setNote(StringPool.BLANK);
							}
							entity.setNote(weldingOdfDTO.getNote());
						}
					}

				
				} catch (Exception e) {
					log.error("Exception", e);
				}
			}

			if (errData.isEmpty()) {
				weldingOdfDao.executeSaveWeldingODf(entity);
				Long couplerNoLong = new Long(entity.getCouplerNo());
				infraOdfDao.setStatusCoupler(entity.getOdfId(), couplerNoLong, Constains.NUMBER_ONE);
				successRecord++;
			} else {
				errRecord++;
			}

			StringBuilder rs = new StringBuilder();
			for (Map.Entry<String, String> entry : errData.entrySet()) {
				String temp = "";
				String key = entry.getKey();
				String value = entry.getValue();
				if (Objects.isNull(value)) {
					temp = temp + value + StringPool.NEW_LINE;
				} else {
					temp = temp + messageSource.getMessage(key, langCode, Arrays.asList(value)) + StringPool.NEW_LINE;
				}
				rs.append(temp);
			}
			if (CommonUtils.isNullOrEmpty(rs.toString())) {
				rs.append("OK");
			}
			mapObj.put(index++, rs.toString());
		}

		String savePath = ExcelDataUltils.writeResultExcel(file, new WeldingOdfDTO(), mapObj,
				messageSource.getMessage("file.name.welding.odf.result", langCode));
		return savePath + StringPool.PLUS + successRecord + StringPool.PLUS + errRecord;
	}

	/**
	 * Import Joint Coupler Odf [dau noi]
	 *
	 * @param file, String langCode,
	 * @author hungnv
	 * @date 01/10/2019
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String importJointCouplerOdf(MultipartFile file, String langCode, String type) {
		Integer successRecord = Constains.NUMBER_ZERO;
		Integer errRecord = Constains.NUMBER_ZERO;
		long index = Constains.NUMBER_ZERO;

		CntCouplerToCouplerBO entity;
		Map<String, String> errData;
		JointCouplersOdfDTO flagList;
		List<JointCouplersOdfDTO> datas = new ArrayList<>();

		try {
			// check header is changed
			JointCouplersOdfDTO header = ExcelDataUltils.getHeaderList(file, new JointCouplersOdfDTO());
			if (!messageSource.checkEqualHeader(header, langCode)) {
				return "template-error";
			}
			datas = (List<JointCouplersOdfDTO>) ExcelDataUltils.getListInExcel(file, new JointCouplersOdfDTO());
			flagList = ExcelDataUltils.getFlagList(file, new JointCouplersOdfDTO());
		} catch (Exception e) {
			log.error("Exception", e);
			return "template-error";
		}

		Map<Long, String> mapObj = new HashMap<>();

		// begin reading
		for (JointCouplersOdfDTO jointCouplersOdfDTO : datas) {
			errData = new HashMap<>();
			entity = new CntCouplerToCouplerBO();
			boolean isOdfCodeExist = false;
			boolean isDestOdfCodeExist = false;

			if (type.equals(Constant.CHANGE_TYPE.ADD)) {
				ViewInfraOdfBO viewInfraOdfBO = new ViewInfraOdfBO();
				// insert ODF source code
				if (CommonUtils.isNullOrEmpty(jointCouplersOdfDTO.getOdfCode())) {
					ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("odf.code", langCode));
				} else {
					viewInfraOdfBO = infraOdfDao.findOdfByCode(jointCouplersOdfDTO.getOdfCode());
					if (!Objects.isNull(viewInfraOdfBO)) {
						entity.setSourceOdfId(viewInfraOdfBO.getOdfId());
						isOdfCodeExist = true;
					} else {
						ExcelDataUltils.addMessage(errData, "err.incorrect",
								messageSource.getMessage("odf.code", langCode));
					}
				}

				Boolean isExistSourceCoupler = false;
				// insert source coupler
				String sourceCoupler = jointCouplersOdfDTO.getCouplerNo();
				if (CommonUtils.isNullOrEmpty(sourceCoupler)) {
					ExcelDataUltils.addMessage(errData, "err.empty",
							messageSource.getMessage("odf.couplerNo", langCode));
				} else if (!StringUtils.isNumeric(sourceCoupler)) {
					ExcelDataUltils.addMessage(errData, "err.number.thanZero.couplerNo",
							messageSource.getMessage("odf.couplerNo", langCode));
				} else {
					if (isOdfCodeExist) {
						List<Long> listCoupler = getCouplerNoAvailability(viewInfraOdfBO.getOdfId());
						Integer countEmpty = Constains.NUMBER_ZERO;
						if (CommonUtils.isNullOrEmpty(listCoupler)) {
							ExcelDataUltils.addMessage(errData, "err.conflict",
									messageSource.getMessage("odf.couplerNo", langCode));
						} else {
							for (Long couplerNo : listCoupler) {
								if (jointCouplersOdfDTO.getCouplerNo().equals(couplerNo.toString())) {
									entity.setSourceCouplerNo(Integer.parseInt(jointCouplersOdfDTO.getCouplerNo()));
									countEmpty++;
								}
							}
							if (countEmpty.equals(Constains.NUMBER_ZERO)) {
								isExistSourceCoupler = true;
							}
						}
					} else {
						ExcelDataUltils.addMessage(errData, "err.incorrect",
								messageSource.getMessage("odf.couplerNo", langCode));
					}
				}
				ViewInfraOdfBO viewInfraDestOdfBO = null;
				// insert dest odf code
				if (CommonUtils.isNullOrEmpty(jointCouplersOdfDTO.getDestOdfCode())) {
					ExcelDataUltils.addMessage(errData, "err.empty",
							messageSource.getMessage("odf.destCode", langCode));
				} else {
					viewInfraDestOdfBO = infraOdfDao.findOdfByCode(jointCouplersOdfDTO.getDestOdfCode());
					if (!Objects.isNull(viewInfraDestOdfBO)) {
						entity.setDestOdfId(viewInfraDestOdfBO.getOdfId());
						isDestOdfCodeExist = true;
					} else {
						ExcelDataUltils.addMessage(errData, "err.incorrect",
								messageSource.getMessage("", langCode));
					}
				}

				if (isOdfCodeExist && isDestOdfCodeExist) {
					List<?> obj = infraOdfDao.getStationByOdfCodes(jointCouplersOdfDTO.getOdfCode(), jointCouplersOdfDTO.getDestOdfCode());
					if (CommonUtils.isNullOrEmpty(obj)) {
						ExcelDataUltils.addMessage(errData, "err.notMatchOdfs",
								 messageSource.getMessage("odf.destCode", langCode));
				  }
				}

				Boolean isExistDestCoupler = false;
				// Insert dest coupler
				String destCoupler = jointCouplersOdfDTO.getDestCouplerNo();
				if (CommonUtils.isNullOrEmpty(destCoupler)) {
					ExcelDataUltils.addMessage(errData, "err.empty",
							messageSource.getMessage("odf.destCouplerNo", langCode));
				} else if (!StringUtils.isNumeric(destCoupler)) {
					ExcelDataUltils.addMessage(errData, "err.number.thanZero.couplerNo",
							messageSource.getMessage("odf.destCouplerNo", langCode));
				} else {
					if (isDestOdfCodeExist) {
						List<Long> listCoupler = getCouplerNoAvailability(viewInfraDestOdfBO.getOdfId());
						Integer countEmpty = Constains.NUMBER_ZERO;
						if (CommonUtils.isNullOrEmpty(listCoupler)) {
							ExcelDataUltils.addMessage(errData, "err.conflict",
									messageSource.getMessage("odf.destCouplerNo", langCode));
						} else {
							for (Long couplerNo : listCoupler) {
								if (destCoupler.equals(couplerNo.toString())) {
									entity.setDestCouplerNo(Integer.parseInt(destCoupler));
									countEmpty++;
								}
							}
							if (countEmpty.equals(Constains.NUMBER_ZERO)) {
								isExistDestCoupler = true;
							}
						}
					} else {
						ExcelDataUltils.addMessage(errData, "err.incorrect",
								messageSource.getMessage("odf.destCouplerNo", langCode));
					}
				}

				if (Boolean.TRUE.equals(isExistDestCoupler) && Boolean.TRUE.equals(isExistSourceCoupler)) {
					ExcelDataUltils.addMessage(errData, "err.double.coupler.connected",
							messageSource.getMessage("odf.destCouplerNo", langCode));
				} else if (Boolean.TRUE.equals(isExistDestCoupler)) {
					ExcelDataUltils.addMessage(errData, "err.conflict",
							messageSource.getMessage("odf.destCouplerNo", langCode));
				} else if(Boolean.TRUE.equals(isExistSourceCoupler)) {
					ExcelDataUltils.addMessage(errData, "err.conflict",
							messageSource.getMessage("odf.couplerNo", langCode));
				}

				// create user
				if (jointCouplersOdfDTO.getCreateUser().length() > 100) {
					ExcelDataUltils.addMessage(errData, "err.maxLength.100",
							messageSource.getMessage("odf.joint.createUser", langCode));
				} else {
					entity.setCreateUser(jointCouplersOdfDTO.getCreateUser());
				}

				// no attenuation
				String attenuation = jointCouplersOdfDTO.getAttenuation();
				if (attenuation.length() > 19) {
					ExcelDataUltils.addMessage(errData, "err.maxLength.19",
							messageSource.getMessage("odf.attenuation", langCode));
				} else {
					if (CommonUtils.isNullOrEmpty(attenuation) || attenuation.trim().length() == 0) {
						entity.setAttenuation(null);
					} else {
						if (OdfStringUtils.PATTERN_ATTENUATION.matcher(attenuation).matches() && Double.parseDouble(attenuation) > 0) {
							if (attenuation.split(OdfStringUtils.PATTERN_DOT)[0].length() <= 13 ) {
								entity.setAttenuation(BigDecimal.valueOf(Double.parseDouble(attenuation)));
							} else {
								ExcelDataUltils.addMessage(errData, "err.overlength",
										messageSource.getMessage("odf.attenuation", langCode));
							}
						} else {
							ExcelDataUltils.addMessage(errData, "err.hasPre.twopoint",
									messageSource.getMessage("odf.attenuation", langCode));
						}
					}
				}

				// create date
				if (CommonUtils.isNullOrEmpty(jointCouplersOdfDTO.getCreateDate())) {
					java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
					entity.setCreateDate(sqlDate);

				} else {
					Date dateFormat;
					try {
						if (OdfStringUtils.PATTERN_DATETIME_DDMMYYYY.matcher(jointCouplersOdfDTO.getCreateDate()).matches()) {
							if (isValidDate(jointCouplersOdfDTO.getCreateDate(), Constants.DATE.DEFAULT_FORMAT)) {
								dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT)
										.parse(jointCouplersOdfDTO.getCreateDate());
								java.sql.Date sqlDate = new java.sql.Date(dateFormat.getTime());
								entity.setCreateDate(sqlDate);
							} else {
								ExcelDataUltils.addMessage(errData, "err.not.valiable",
										messageSource.getMessage("odf.welding.createDate", langCode));
							}
						} else {
							ExcelDataUltils.addMessage(errData, "err.dateFormat",
									messageSource.getMessage("odf.welding.createDate", langCode));
						}

					} catch (ParseException e) {
						log.error("Exception", e);
					}
				}

				// create user
				if (jointCouplersOdfDTO.getNote().length() > 500) {
					ExcelDataUltils.addMessage(errData, "err.maxLength.500",
							messageSource.getMessage("odf.note", langCode));
				} else {
					entity.setNote(jointCouplersOdfDTO.getNote());
				}

			} else if (type.equals(Constant.CHANGE_TYPE.UPDATE)) {
				try {

					flagList = ExcelDataUltils.getFlagList(file, new JointCouplersOdfDTO());
					Long odfId = infraOdfDao.findOdfByCode(jointCouplersOdfDTO.getOdfCode()).getOdfId();
					Long coupler = Long.parseLong(jointCouplersOdfDTO.getCouplerNo());
					ViewWeldingOdfBO weldingOdf = weldingOdfDao.findSelectedWeldingOdf(odfId, coupler, 2);
					if (Objects.nonNull(weldingOdf)) {
						entity.setSourceOdfId(odfId);
						entity.setSourceCouplerNo(Integer.valueOf(coupler.toString()));
						entity.setDestOdfId(weldingOdf.getDest_OdfId());
						entity.setDestCouplerNo(Integer.valueOf(jointCouplersOdfDTO.getDestCouplerNo()));

						// create user
						if (flagList.getCreateUser().equals(OdfStringUtils.ALLOW_UPDATE)) {
							if (jointCouplersOdfDTO.getCreateUser().length() > 200) {
								ExcelDataUltils.addMessage(errData, "err.maxLength.200",
										messageSource.getMessage("odf.joint.createUser", langCode));
							} else {
								entity.setCreateUser(jointCouplersOdfDTO.getCreateUser());
							}
						}

						//no attenuation
						if (flagList.getAttenuation().equals(OdfStringUtils.ALLOW_UPDATE)) {
							String attenuation = jointCouplersOdfDTO.getAttenuation();
							if (attenuation.length() > 19) {
								ExcelDataUltils.addMessage(errData, "err.maxLength.19",
										messageSource.getMessage("odf.attenuation", langCode));
							} else {
								if (CommonUtils.isNullOrEmpty(attenuation) || attenuation.trim().length() == 0) {
									entity.setAttenuation(null);
								} else {
									if (OdfStringUtils.PATTERN_ATTENUATION.matcher(attenuation).matches() && Double.parseDouble(attenuation) > 0) {
										if (attenuation.split(OdfStringUtils.PATTERN_DOT)[0].length() <= 13 ) {
											entity.setAttenuation(BigDecimal.valueOf(Double.parseDouble(attenuation)));
										} else {
											ExcelDataUltils.addMessage(errData, "err.overlength",
													messageSource.getMessage("odf.attenuation", langCode));
										}
									} else {
										ExcelDataUltils.addMessage(errData, "err.hasPre.twopoint",
												messageSource.getMessage("odf.attenuation", langCode));
									}
								}
							}
						}
						
						// create date
						if (flagList.getCreateDate().equals(OdfStringUtils.ALLOW_UPDATE)) {
							if (CommonUtils.isNullOrEmpty(jointCouplersOdfDTO.getCreateDate())) {
								java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
								entity.setCreateDate(sqlDate);

							} else {
								Date dateFormat;
								try {
									if (OdfStringUtils.PATTERN_DATETIME_DDMMYYYY.matcher(jointCouplersOdfDTO.getCreateDate()).matches()) {
										if (isValidDate(jointCouplersOdfDTO.getCreateDate(), Constants.DATE.DEFAULT_FORMAT)) {
											dateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT)
													.parse(jointCouplersOdfDTO.getCreateDate());
											java.sql.Date sqlDate = new java.sql.Date(dateFormat.getTime());
											entity.setCreateDate(sqlDate);
										} else {
											ExcelDataUltils.addMessage(errData, "err.not.valiable",
													messageSource.getMessage("odf.welding.createDate", langCode));
										}
									} else {
										ExcelDataUltils.addMessage(errData, "err.dateFormat",
												messageSource.getMessage("odf.welding.createDate", langCode));
									}

								} catch (ParseException e) {
									log.error("Exception", e);
								}
							}
						}
						
						//  note
						if (flagList.getNote().equals(OdfStringUtils.ALLOW_UPDATE)) {
							if (jointCouplersOdfDTO.getNote().length() > 500) {
								ExcelDataUltils.addMessage(errData, "err.maxLength.500",
										messageSource.getMessage("odf.note", langCode));
							} else {
								entity.setNote(jointCouplersOdfDTO.getNote());
							}
						}

					} else {
						ExcelDataUltils.addMessage(errData, "err.exist", langCode);
					}
				
				} catch (Exception e) {
					log.error("Exception", e);
				}
			}

			if (errData.isEmpty()) {
				weldingOdfDao.executeSaveJointODf(entity);
				Long couplerNoLong = new Long(entity.getSourceCouplerNo());
				infraOdfDao.setStatusCoupler(entity.getSourceOdfId(), couplerNoLong, Constains.NUMBER_ONE);
				successRecord++;
			} else {
				errRecord++;
			}

			StringBuilder rs = new StringBuilder();
			for (Map.Entry<String, String> entry : errData.entrySet()) {
				String temp = "";
				String key = entry.getKey();
				String value = entry.getValue();
				if (Objects.isNull(value)) {
					temp = temp + value + StringPool.NEW_LINE;
				} else {
					temp = temp + messageSource.getMessage(key, langCode, Arrays.asList(value)) + StringPool.NEW_LINE;
				}
				rs.append(temp);
			}
			if (CommonUtils.isNullOrEmpty(rs.toString())) {
				rs.append("OK");
			}
			mapObj.put(index++, rs.toString());
		}

		String savePath = ExcelDataUltils.writeResultExcel(file, new JointCouplersOdfDTO(), mapObj,
				messageSource.getMessage("file.name.joint.odf.result", langCode));
		return savePath + StringPool.PLUS + successRecord +  StringPool.PLUS + errRecord;
	}

	/**
	 * Search ODF Lane
	 *
	 * @param viewOdfLaneBO
	 * @author dungph
	 * @date 08/10/2019
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<?> searchOdfLane(ViewOdfLaneBO viewOdfLaneBO) {
		List<ViewOdfLaneBO> viewOdfLaneBOList = infraOdfDao.getOdfLaneBySearch(viewOdfLaneBO);
		Response response = new Response();
		response.setContent(viewOdfLaneBOList);
		response.setStatus(HttpStatus.OK.toString());
		response.setMessage("OK");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//--------Sub Methods---------
//------------***-------------

	/**
	 * method init info delete
	 *
	 * @param odfId
	 * @author hungnv
	 * @date 11/09/2019
	 */
	private void initInfoDelete(Long odfId) {
		responseJson.put(ODF_ID, odfId);
		responseJson.put(ODF_CODE, Constains.BLANK);
		responseJson.put(NOT_REF_COUPLER, true);
		responseJson.put(NOT_REF_LINE, true);
		responseJson.put(ROW_STATUS, Constains.NUMBER_ONE);
		responseJson.put(INFO_COUPLER_REF, Constains.BLANK);
		responseJson.put(INFO_COUPLER_SOURCE_ODF_REF, Constains.BLANK);
		responseJson.put(INFO_COUPLER_DIS_ODF_REF, Constains.BLANK);
		responseJson.put(INFO_LINE_REF, Constains.BLANK);
		responseJson.put(INFO_ERROR_DELETE, true);
	}

	/**
	 * method check data for delete
	 *
	 * @param infraOdfBOList
	 * @return boolean
	 * @author hungnv
	 * @date 28/8/2019
	 */

	private boolean checkDelete(List<InfraOdfBO> infraOdfBOList) {
		boolean status = true;
		for (InfraOdfBO infraOdfBO : infraOdfBOList) {
			if (refCouplerToCoupler(infraOdfBO.getOdfId()) || refCouplerToLine(infraOdfBO.getOdfId())) {
				status = false;
				break;
			}
		}
		return status;
	}

	/**
	 * method check coupler to coupler if return false then have NOT reference table
	 * CNT_COUPLER_TO_COUPLER , return true then have reference table
	 * CNT_COUPLER_TO_COUPLER
	 *
	 * @param odfId
	 * @return boolean
	 * @author hungnv
	 * @date 28/8/2019
	 */
	private boolean refCouplerToCoupler(Long odfId) {
		return !Constains.STRING_ZERO.equals(infraOdfDao.countRecordRefCouplerToCoupler(odfId));
	}

	/**
	 * method check coupler to line if return false then have NOT reference table
	 * CNT_COUPLER_TO_LINE , return true then have reference table
	 * CNT_COUPLER_TO_LINE
	 *
	 * @param odfId
	 * @return boolean
	 * @author hungnv
	 * @date 28/8/2019
	 */
	private boolean refCouplerToLine(Long odfId) {
		return !Constains.STRING_ZERO.equals(infraOdfDao.countRecordRefCouplerToLine(odfId));
	}

	/**
	 * method save Coupler list
	 *
	 * @param to,odfId
	 * @return void
	 * @author dungph
	 * @date 25/9/2019
	 */
	private void saveCoupler(Integer to, Long odfId, List<Long> inputList) {
		for (Integer i = 1; i <= to; i++) {
			Long couplerNo = i.longValue();
			if (Objects.nonNull(inputList)) {
				while (inputList.contains(couplerNo)) {
					++couplerNo;
				}
				inputList.add(couplerNo);
			}

			InfraCouplerBO infraCouplersBO = new InfraCouplerBO();
			infraCouplersBO.setOdfId(odfId);
			infraCouplersBO.setCouplerNo(couplerNo);
			infraCouplersBO.setStatuz(Constains.NUMBER_ZERO);
			infraCouplerDao.saveOrUpdateInfraCoupler(infraCouplersBO);
		}
	}

	/**
	 * method set List<T> to Response<FormResult>
	 *
	 * @param input
	 * @return Response
	 * @author dungph
	 * @date 28/8/2019
	 */
	private Response<FormResult> setListResponse(List<?> input) {
		FormResult result = new FormResult();
		result.setListData(input);
		Response<FormResult> response = new Response<>();
		response.setContent(result);
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}

	private XSSFWorkbook getPathFile(InputStream excelFile, XSSFWorkbook workbook, String classPathStr) {
		try {
			excelFile = new ClassPathResource(classPathStr).getInputStream();
			workbook = new XSSFWorkbook(excelFile);
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return workbook;
	}

	/**
	 * set data for sheet import odf file Temp_Imp_ODF_insert.xlsx
	 *
	 * @param workbook, viewInfraOdfBOList
	 * @author hungnv
	 * @date 25/9/2019
	 */
	private void setDataSheetImportODF(XSSFWorkbook workbook, List<ViewInfraOdfBO> viewInfraOdfBOList) {
		int rowIndex = Constains.NUMBER_FOUR;
		int indexColumn = Constains.NUMBER_ONE;
		int indexCell;
		Cell cell;
		// get sheet Import ODF
		XSSFSheet sheetData = workbook.getSheetAt(Constains.NUMBER_ZERO);
		for (ViewInfraOdfBO viewInfraOdfBO : viewInfraOdfBOList) {
			Row row = sheetData.createRow(rowIndex++);
			indexCell = Constains.NUMBER_ZERO;

			cell = row.createCell(indexCell++);
			cell.setCellValue(indexColumn++);
			cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			// set station code in cell
			cell = row.createCell(indexCell++);
			cell.setCellValue(Objects.isNull(viewInfraOdfBO.getStationCode()) ? Constains.BLANK
					: viewInfraOdfBO.getStationCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			// set odf index in cell
			cell = row.createCell(indexCell++);
			cell.setCellValue(Objects.isNull(viewInfraOdfBO.getOdfCode()) ? Constains.BLANK
					: viewInfraOdfBO.getOdfCode().substring(viewInfraOdfBO.getOdfCode().length() - Constains.NUMBER_TWO,
							viewInfraOdfBO.getOdfCode().length()));
			cell.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

			// set odf code in cell
			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(viewInfraOdfBO.getOdfCode()) ? Constains.BLANK : viewInfraOdfBO.getOdfCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			// set dept code in cell
			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(viewInfraOdfBO.getDeptCode()) ? Constains.BLANK : viewInfraOdfBO.getDeptCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			// set type odf in cell
			cell = row.createCell(indexCell++);
			cell.setCellValue(Objects.isNull(viewInfraOdfBO.getOdfTypeCode()) ? Constains.BLANK
					: viewInfraOdfBO.getOdfTypeCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			// set owner name in cell
			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(viewInfraOdfBO.getOwnerName()) ? Constains.BLANK : viewInfraOdfBO.getOwnerName());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			// set vendor name in cell
			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(viewInfraOdfBO.getVendorName()) ? Constains.BLANK : viewInfraOdfBO.getVendorName());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			// set install date in cell
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
			cell = row.createCell(indexCell++);
			cell.setCellValue(Objects.isNull(viewInfraOdfBO.getInstallationDate()) ? Constains.BLANK
					: simpleDateFormat.format(viewInfraOdfBO.getInstallationDate()));
			cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			// set note in cell
			cell = row.createCell(indexCell++);
			cell.setCellValue(Objects.isNull(viewInfraOdfBO.getNote()) ? Constains.BLANK : viewInfraOdfBO.getNote());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
		}
	}

	/**
	 * Set data for sheet [DM_LoaiODF]
	 *
	 * @param workbook, viewInfraOdfBOList file Temp_Imp_ODF_insert.xlsx
	 * @author hungnv
	 * @date 25/9/2019
	 */
	private void setDataSheetTypeODF(XSSFWorkbook workbook) {

		int rowIndex = Constains.NUMBER_THREE;
		int indexColumn = Constains.NUMBER_ONE;
		int indexCell;
		Cell cell;

		List<CatOdfTypeBO> listCatOdfTypeBO = catOdfTypeDao.getAllCatOdfType();
		XSSFSheet sheetTypeOdf = workbook.getSheetAt(Constains.NUMBER_ONE);

		for (CatOdfTypeBO catOdfTypeBO : listCatOdfTypeBO) {
			indexCell = Constains.NUMBER_ZERO;
			Row row = sheetTypeOdf.createRow(rowIndex++);
			cell = row.createCell(indexCell++);
			cell.setCellValue(indexColumn++);
			cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(catOdfTypeBO.getOdfTypeCode()) ? Constains.BLANK : catOdfTypeBO.getOdfTypeCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
		}
	}

	/**
	 * Set data for sheet [DM_DonVi] file Temp_Imp_ODF_insert.xlsx
	 *
	 * @param workbook
	 * @author hungnv
	 * @date 25/9/2019
	 */
	@SuppressWarnings("unchecked")
	private void setDataSheetDept(XSSFWorkbook workbook, HttpServletRequest request) {
		Long userId = CommonUtil.getUserId(request);
		CatDepartmentEntity departmentEntity = new CatDepartmentEntity();
		FormResult formResult = transmissionDao.findDepartment(departmentEntity, userId);
		XSSFSheet sheetTypeOdf = workbook.getSheetAt(Constains.NUMBER_TWO);
		int rowIndex = Constains.NUMBER_THREE;
		int indexColumn = Constains.NUMBER_ONE;
		int indexCell;
		Cell cell;

		List<ViewCatDepartmentBO> listCatDepartmentBO = (List<ViewCatDepartmentBO>) formResult.getListData();

		for (ViewCatDepartmentBO catDepartmentBO : listCatDepartmentBO) {
			indexCell = Constains.NUMBER_ZERO;
			Row row = sheetTypeOdf.createRow(rowIndex++);
			cell = row.createCell(indexCell++);
			cell.setCellValue(indexColumn++);
			cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			// set data for dept code
			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(catDepartmentBO.getDeptCode()) ? Constains.BLANK : catDepartmentBO.getDeptCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			// set data for dept name
			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(catDepartmentBO.getDeptName()) ? Constains.BLANK : catDepartmentBO.getDeptName());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			// set data for path name
			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(catDepartmentBO.getPathName()) ? Constains.BLANK : catDepartmentBO.getPathName());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
		}
	}

	/**
	 * Set data for sheet [DM_ChuSoHuu] file Temp_Imp_ODF_insert.xlsx
	 *
	 * @param workbook,
	 * @author hungnv
	 * @date 25/9/2019
	 */
	private void setDataSheetOwner(XSSFWorkbook workbook) {
		List<ViewCatItemBO> listViewCatItemBO = transmissionDao.findCatItemByCategoryId("CAT_OWNER");
		XSSFSheet sheetOwner = workbook.getSheetAt(Constains.NUMBER_THREE);
		int rowIndex = Constains.NUMBER_THREE;
		int indexColumn = Constains.NUMBER_ONE;
		int indexCell;
		Cell cell;

		for (ViewCatItemBO viewCatItemBO : listViewCatItemBO) {
			indexCell = Constains.NUMBER_ZERO;
			Row row = sheetOwner.createRow(rowIndex++);
			cell = row.createCell(indexCell++);
			cell.setCellValue(indexColumn++);
			cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(viewCatItemBO.getItemCode()) ? Constains.BLANK : viewCatItemBO.getItemCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(viewCatItemBO.getItemName()) ? Constains.BLANK : viewCatItemBO.getItemName());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

		}
	}

	/**
	 * Set data for sheet [DM_HangSanXuat] file Temp_Imp_ODF_insert.xlsx
	 *
	 * @param workbook,
	 * @author hungnv
	 * @date 25/9/2019
	 */
	private void setDataSheetVendor(XSSFWorkbook workbook) {
		List<ViewCatItemBO> listViewCatItemBO = transmissionDao.findCatItemByCategoryId("VENDOR");
		XSSFSheet sheetOwner = workbook.getSheetAt(Constains.NUMBER_FOUR);
		int rowIndex = Constains.NUMBER_THREE;
		int indexColumn = Constains.NUMBER_ONE;
		int indexCell;
		Cell cell;

		for (ViewCatItemBO viewCatItemBO : listViewCatItemBO) {
			indexCell = Constains.NUMBER_ZERO;
			Row row = sheetOwner.createRow(rowIndex++);
			cell = row.createCell(indexCell++);
			cell.setCellValue(indexColumn++);
			cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(viewCatItemBO.getItemCode()) ? Constains.BLANK : viewCatItemBO.getItemCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(viewCatItemBO.getItemName()) ? Constains.BLANK : viewCatItemBO.getItemName());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

		}
	}

	/**
	 * Set data for sheet [Import_Han noi] file Temp_Imp_Han_noi_insert.xlsx
	 *
	 * @param workbook,
	 * @author hungnv
	 * @date 26/9/2019
	 */
	private void setDataSheetWeldOdf(XSSFWorkbook workbook, List<ViewInfraOdfBO> viewInfraOdfBOList) {
		int rowIndex = Constains.NUMBER_FOUR;
		int indexColumn = Constains.NUMBER_ONE;
		int indexCell;
		Cell cell;
		// get sheet [Import Han noi]
		XSSFSheet sheetData = workbook.getSheetAt(Constains.NUMBER_ZERO);

		for (ViewInfraOdfBO viewInfraOdfBO : viewInfraOdfBOList) {
			List<ViewWeldingOdfBO> listViewWeldingOdfBO = (List<ViewWeldingOdfBO>) weldingOdfDao
					.selectAllWeldingOdf(viewInfraOdfBO.getOdfId());

			for (ViewWeldingOdfBO viewWeldingOdfBO : listViewWeldingOdfBO) {
				if (!CommonUtils.isNullOrEmpty(viewWeldingOdfBO.getCableCode().toString().trim())) {
					indexCell = Constains.NUMBER_ZERO;
					Row row = sheetData.createRow(rowIndex++);
					cell = row.createCell(indexCell++);
					cell.setCellValue(indexColumn++);
					cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(Objects.isNull(viewWeldingOdfBO.getOdfCode()) ? Constains.BLANK
							: viewWeldingOdfBO.getOdfCode());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(viewWeldingOdfBO.getCouplerNo());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(Objects.isNull(viewWeldingOdfBO.getCableCode()) ? Constains.BLANK
							: viewWeldingOdfBO.getCableCode());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(viewWeldingOdfBO.getLineNo());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(Objects.isNull(viewWeldingOdfBO.getCreateUser()) ? Constains.BLANK
							: viewWeldingOdfBO.getCreateUser());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(Objects.isNull(viewWeldingOdfBO.getAttenuation()) ? Constains.BLANK
							: viewWeldingOdfBO.getAttenuation());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));


					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
					cell = row.createCell(indexCell++);
					java.sql.Date sqlDate = null;
					if (Objects.isNull(viewWeldingOdfBO.getCreate_Date())) {
						cell.setCellValue(Objects.isNull(sqlDate) ? Constains.BLANK : simpleDateFormat.format(sqlDate));
						cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));
					} else {
						sqlDate = new java.sql.Date(viewWeldingOdfBO.getCreate_Date().getTime());
						cell.setCellValue(Objects.isNull(sqlDate) ? Constains.BLANK : simpleDateFormat.format(sqlDate));
						cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));
					}


					cell = row.createCell(indexCell++);
					cell.setCellValue(
							Objects.isNull(viewWeldingOdfBO.getNote()) ? Constains.BLANK : viewWeldingOdfBO.getNote());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
				}

			}
		}

	}

	/**
	 * Set data for sheet [DM_ODF] file Temp_Imp_Han_noi_insert.xlsx and
	 * Temp_Imp_Dau_noi_insert.xlsx
	 *
	 * @param workbook,
	 * @author hungnv
	 * @date 26/9/2019
	 */
	private void setDataSheetDMODF(XSSFWorkbook workbook, HttpServletRequest request) {
		Long userId = CommonUtil.getUserId(request);
		XSSFSheet sheetTypeCoubleODF = workbook.getSheetAt(Constains.NUMBER_ONE);
		int rowIndex = Constains.NUMBER_THREE;
		int indexColumn = Constains.NUMBER_ONE;
		int indexCell;
		Cell cell;

		List<ViewInfraOdfBO> listViewInfraOdfBO = infraOdfDao.getAllOdf(null, userId);

		for (ViewInfraOdfBO DMOdfDto : listViewInfraOdfBO) {
			indexCell = 0;
			Row row = sheetTypeCoubleODF.createRow(rowIndex++);
			cell = row.createCell(indexCell++);
			cell.setCellValue(indexColumn++);
			cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			cell = row.createCell(indexCell++);
			cell.setCellValue(Objects.isNull(DMOdfDto.getStationCode()) ? Constains.BLANK
					: DMOdfDto.getStationCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(DMOdfDto.getOdfCode()) ? Constains.BLANK : DMOdfDto.getOdfCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
		}
	}

	/**
	 * Set data for sheet [DM_CapDen] file Temp_Imp_Han_noi_insert.xlsx
	 *
	 * @param workbook,
	 * @author hungnv
	 * @date 26/9/2019
	 */
	private void setDataSheetCable(XSSFWorkbook workbook, HttpServletRequest request) {
		XSSFSheet sheetTypeCoubleODF = workbook.getSheetAt(Constains.NUMBER_TWO);
		int rowIndex = Constains.NUMBER_THREE;
		int indexColumn = Constains.NUMBER_ONE;
		int indexCell;
		Cell cell;
		Long userId = CommonUtil.getUserId(request);

		// get cable through station
		List<InfraCablesBO> listInfraCablesBO = infraCablesDao.getAllCalble(userId, BigInteger.ONE);

		for (InfraCablesBO infraCablesBO : listInfraCablesBO) {
			indexCell = Constains.NUMBER_ZERO;
			Row row = sheetTypeCoubleODF.createRow(rowIndex++);
			cell = row.createCell(indexCell++);
			cell.setCellValue(indexColumn++);
			cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			cell = row.createCell(indexCell++);
			String stationCode = infraStationDao.findStationById(infraCablesBO.getDestId()).getStationCode();

			cell.setCellValue(Objects.isNull(stationCode) ? Constains.BLANK : stationCode);
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			cell = row.createCell(indexCell++);
			cell.setCellValue(
					Objects.isNull(infraCablesBO.getCableCode()) ? Constains.BLANK : infraCablesBO.getCableCode());
			cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

		}
	}

	/**
	 * Set data for sheet [Import Dau noi] file Temp_Imp_Dau_noi_insert.xlsx
	 *
	 * @param workbook,
	 * @author hungnv
	 * @date 26/9/2019
	 */
	private void setDataSheetImportCoupletoCouple(XSSFWorkbook workbook, List<ViewInfraOdfBO> viewInfraOdfBOList) {
		XSSFSheet sheetTypeCoubleODF = workbook.getSheetAt(Constains.NUMBER_ZERO);
		int rowIndex = Constains.NUMBER_FOUR;
		int indexColumn = Constains.NUMBER_ONE;
		int indexCell;
		Cell cell;

		for (ViewInfraOdfBO viewInfraOdfBO : viewInfraOdfBOList) {
			List<ViewWeldingOdfBO> listViewWeldingOdfBO =  weldingOdfDao
					.selectAllWeldingOdf(viewInfraOdfBO.getOdfId());
			for (ViewWeldingOdfBO viewWeldingOdfBO : listViewWeldingOdfBO) {
				if (CommonUtils.isNullOrEmpty(viewWeldingOdfBO.getCableCode().trim())) {
					indexCell = Constains.NUMBER_ZERO;
					Row row = sheetTypeCoubleODF.createRow(rowIndex++);
					cell = row.createCell(indexCell++);
					cell.setCellValue(indexColumn++);
					cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(Objects.isNull(viewWeldingOdfBO.getOdfCode()) ? Constains.BLANK
							: viewWeldingOdfBO.getOdfCode());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(viewWeldingOdfBO.getCouplerNo());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(Objects.isNull(viewWeldingOdfBO.getDestOdfCode()) ? Constains.BLANK
							: viewWeldingOdfBO.getDestOdfCode());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(viewWeldingOdfBO.getDestCouplerNo());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(Objects.isNull(viewWeldingOdfBO.getCreateUser()) ? Constains.BLANK
							: viewWeldingOdfBO.getCreateUser());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					cell = row.createCell(indexCell++);
					cell.setCellValue(Objects.isNull(viewWeldingOdfBO.getAttenuation()) ? Constains.BLANK
							: viewWeldingOdfBO.getAttenuation());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
					cell = row.createCell(indexCell++);
					java.sql.Date sqlDate = null;
					if (Objects.isNull(viewWeldingOdfBO.getCreate_Date())) {
						cell.setCellValue(Objects.isNull(sqlDate) ? Constains.BLANK : simpleDateFormat.format(sqlDate));
						cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));
					} else {
						sqlDate = new java.sql.Date(viewWeldingOdfBO.getCreate_Date().getTime());
						cell.setCellValue(Objects.isNull(sqlDate) ? Constains.BLANK : simpleDateFormat.format(sqlDate));
						cell.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));
					}


					cell = row.createCell(indexCell++);
					cell.setCellValue(
							Objects.isNull(viewWeldingOdfBO.getNote()) ? Constains.BLANK : viewWeldingOdfBO.getNote());
					cell.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
				}

			}
		}
	}

	/**
	 * convert index
	 *
	 * @param rawIndex
	 * @author hungnv
	 * @date 29/9/2019
	 */

	private String convertIndex(String rawIndex) {
		Integer index = 0;
		if (OdfStringUtils.ONE_DIGIT_NUMBER.matcher(rawIndex).matches()) {
			index = Integer.parseInt(rawIndex);
			index = index * 10;
			rawIndex = index.toString();
		}
		return rawIndex;
	}

	/**
	 * get max odf index
	 *
	 * @param stationId
	 * @author hungnv
	 * @date 1/10/2019
	 */
	private Integer getMaxOdfIndex(Long stationId) {
		Integer odfIndex;
		List<ViewInfraOdfBO> odfBOList = infraOdfDao.getMaxOdfIndexByStationId(stationId);
		odfIndex = Constains.NUMBER_ZERO;
		// odfBOList.size() > 0
		if (((Object) Integer.compare(odfBOList.size(), Constains.NUMBER_ZERO)).equals(Constains.NUMBER_ONE)) {
			List<Integer> odfIndexList = new ArrayList<>();
			for (ViewInfraOdfBO item : odfBOList) {
				String[] temp = item.getOdfCode().split(StringPool.MINUS);
				// temp.length > 2
				if (((Object) Integer.compare(temp.length, Constains.NUMBER_TWO)).equals(Constains.NUMBER_ONE)) {
					odfIndexList.add(Integer.parseInt(temp[temp.length - Constains.NUMBER_ONE]));
				}
			}
			// odfIndexList.size() > 0
			if (((Object) Integer.compare(odfIndexList.size(), Constains.NUMBER_ZERO)).equals(Constains.NUMBER_ONE)) {
				odfIndex = Collections.max(odfIndexList);
			}
		}
		return odfIndex;

	}

	/**
	 * @author HungNV
	 * @param odfId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getCouplerNoAvailability(Long odfId) {
		listCouplerNo = weldingOdfDao.selectCouplerNos(odfId);
		return listCouplerNo;
	}

	/**
	 * author HungNV
	 *
	 * @param odfId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getLineNosAvailability(Long odfId) {
		List<Long> listLineNo = new ArrayList<>();
		listLineNo = weldingOdfDao.selectLineNos(odfId);
		return listLineNo;
	}

	/**
	 *
	 * @param srcDate
	 * @param format
	 * @return
	 */
	private static boolean isValidDate(String srcDate, String format) {

		boolean result = true;

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);

		try {
			dateFormat.parse(srcDate);
		} catch (ParseException e) {
			result = false;
		}
		return result;
	}

}
