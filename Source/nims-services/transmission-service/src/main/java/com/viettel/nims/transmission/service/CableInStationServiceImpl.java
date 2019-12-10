package com.viettel.nims.transmission.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.commom.Constants;
import com.viettel.nims.transmission.commom.ExcelDataUltils;
import com.viettel.nims.transmission.commom.ExcelStyleUtil;
import com.viettel.nims.transmission.commom.HeaderDTO;
import com.viettel.nims.transmission.commom.JSONResponse;
import com.viettel.nims.transmission.commom.MessageResource;
import com.viettel.nims.transmission.commom.ResponseBase;
import com.viettel.nims.transmission.dao.CableInStationDao;
import com.viettel.nims.transmission.dao.InfraOdfDao;
import com.viettel.nims.transmission.dao.InfraStationDao;
import com.viettel.nims.transmission.dao.TransmissionDao;
import com.viettel.nims.transmission.model.CableInStationBO;
import com.viettel.nims.transmission.model.CatOpticalCableTypeBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.dto.InfraCableInStationDTO;
import com.viettel.nims.transmission.model.view.ViewCableInStationBO;
import com.viettel.nims.transmission.model.view.ViewInfraOdfBO;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import com.viettel.nims.transmission.utils.Constains;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Slf4j
@Service
@Transactional(transactionManager = "globalTransactionManager")
public class CableInStationServiceImpl implements CableInStationService {

	@Autowired
	CableInStationDao cableInStationDao;

	@Autowired
	TransmissionDao transmissionDao;

	@Autowired
	InfraStationDao infraStationDao;

	@Autowired
	InfraOdfDao infraOdfDao;

	@Autowired
	TransmissionService transmissionService;

	@Autowired
	MessageResource messageSource;

	@Override
	// Tim kiem co ban
	public ResponseEntity<?> findBasicCable(CableInStationBO cableInStationBO) {
		try {
			FormResult result = cableInStationDao.findBasicCable(cableInStationBO);
			Response<FormResult> response = new Response<>();
			response.setContent(result);
			return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
		} catch (Exception e) {
			return ResponseBase.createResponse(null, Constains.FAILED, 500);
		}
	}

	@Override
	// Tim kiem nang cao
	public ResponseEntity<?> findAdvanceCable(CableInStationBO cableInStationBO, String langCode,
			HttpServletRequest request) {
		Long userId = CommonUtil.getUserId(request);
		try {
			FormResult result = cableInStationDao.findAdvanceCable(cableInStationBO, userId);
			Response<FormResult> response = new Response<>();
			response.setContent(result);
			return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
		} catch (Exception e) {
			return ResponseBase.createResponse(null, Constains.FAILED, 500);
		}
	}

	@Override
	// Them cap
	public ResponseEntity<?> saveCable(CableInStationBO cableInStationBO) {
		JSONObject jsonObject = new JSONObject();
		if (cableInStationBO.getCableCode() == null) {
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ma doan cap", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		} else if (cableInStationBO.getCableTypeId() == null) {
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ma loai cap", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		} else if (cableInStationBO.getSourceId() == null) {
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ODF dau", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		} else if (cableInStationBO.getDestId() == null) {
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ODF cuoi", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		} else if (cableInStationBO.getStationId() == null) {
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ma nha tram", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		} else {
			try {
				ViewInfraStationsBO infraStationsBO = infraStationDao.findStationById(cableInStationBO.getStationId());
				cableInStationBO.setDeptId(infraStationsBO.getDeptId());
				cableInStationDao.saveCable(cableInStationBO);
				Response<FormResult> response = new Response<>();
				response.setStatus(HttpStatus.OK.toString());
				return ResponseBase.createResponse(null, Constains.SUCCESS, 200);
			} catch (Exception e) {
				return ResponseBase.createResponse(null, Constains.NOT_MODIFIED, 304);
			}
		}
	}

	@Override
	public ResponseEntity<?> findCableById(Long id, String langCode) {
		try {
			ViewCableInStationBO viewCableInStationBO = cableInStationDao.findCableById(id);
			Response<ViewCableInStationBO> response = new Response<>();
			response.setStatus(Constains.UPDATE);
			response.setContent(viewCableInStationBO);
			return ResponseBase.createResponse(viewCableInStationBO, Constains.UPDATE, 200);
		} catch (Exception e) {
			log.error("Exception", e);
			return ResponseBase.createResponse(null, "error", 500);
		}
	}

	@Override
	public ResponseEntity<?> findListCableType() {
		JSONObject jsonObject = new JSONObject();
		try {
			FormResult result = cableInStationDao.findListCableType();
			Response<FormResult> response = new Response<>();
			response.setContent(result);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ma loai cap", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		}
	}

	@Override
	// List ODF
	public ResponseEntity<?> findListODFFist(Long stationId, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Long userId = CommonUtil.getUserId(request);
		try {
			FormResult result = cableInStationDao.findListODFFist(stationId, userId);
			// Response<FormResult> response = new Response<>();
			// response.setContent(result);
			return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
		} catch (Exception e) {
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ma ODF dau ", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		}
	}

	@Override
	// List ODF
	public ResponseEntity<?> findListODFEnd(Long stationId, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Long userId = CommonUtil.getUserId(request);
		try {
			FormResult result = cableInStationDao.findListODFEnd(stationId, userId);
			// Response<FormResult> response = new Response<>();
			// response.setContent(result);
			// return new ResponseEntity<>(response, HttpStatus.OK);
			return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
		} catch (Exception e) {
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ma ODF cuoi ", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		}
	}

	@Override
	// get stationcode
	public ResponseEntity<?> getDataSearchAdvance(InfraStationsBO infraStationsBO) {
		JSONObject jsonObject = new JSONObject();
		try {
			FormResult result = cableInStationDao.getDataFormSearchAdvance(infraStationsBO);
			// Response<FormResult> response = new Response<>();
			// response.setContent(result);
			// return new ResponseEntity<>(response, HttpStatus.OK);
			return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
		} catch (Exception e) {
			// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ma nha tram ", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		}
	}

	@Override
	public JSONObject delete(CableInStationBO cableInStationBO) {
		JSONObject data = new JSONObject();
		// check ODF da duoc dau noi
		Boolean cableInStationBOCheck = cableInStationDao.checkWeldODFByCable(cableInStationBO);
		if (cableInStationBOCheck == true) {
			data.put("code", 0);
		} else {
			int result = cableInStationDao.delete(cableInStationBO.getCableId());
			data.put("code", result);
		}
		return data;
	}

	@Override
	public JSONObject deleteMultipe(List<CableInStationBO> cableInStationBOList) {
		JSONObject data = new JSONObject();
		boolean isErr = false;
		for (int i = 0; i < cableInStationBOList.size(); i++) {
			// Check ODF da duoc dau noi
			Boolean cableInStationBOCheck = cableInStationDao.checkWeldODFByCable(cableInStationBOList.get(i));
			if (cableInStationBOCheck == true) {
				data.put("code", 0);
				if (data.get("message") != null) {
					data.put("message",
							data.get("message") + "," + "[" + cableInStationBOList.get(i).getCableCode() + "]");
				} else {
					data.put("message", "[" + cableInStationBOList.get(i).getCableCode() + "]");
				}
				isErr = true;
			}
		}
		if (isErr == false) {
			for (int i = 0; i < cableInStationBOList.size(); i++) {
				int result = cableInStationDao.delete(cableInStationBOList.get(i).getCableId());
			}
			data.put("code", 1);
			data.put("message", "Success");
		}
		return data;
	}

	@Override
	public ResponseEntity<?> getCableIndex(Long sourceId, Long destId) {
		JSONObject jsonObject = new JSONObject();
		try {
			String result = cableInStationDao.getCableIndex(sourceId, destId);
			Response<String> response = new Response<>();
			response.setStatus(Constains.UPDATE);
			response.setContent(result);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception", e);
			jsonObject = JSONResponse.buildResultJson(1, "Khong co chi so doan cap ", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		}
	}

	@Override
	public ResponseEntity<?> isExitCode(CableInStationBO cableInStationBO) {
		JSONObject jsonObject = new JSONObject();
		try {
			Boolean result = cableInStationDao.isExitByCode(cableInStationBO);
			// Response<Boolean> response = new Response<>();
			// response.setContent(result);
			// return new ResponseEntity<>(response, HttpStatus.OK);
			return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
		} catch (Exception e) {
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ma doan cap", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		}
	}

	@Override
	public ResponseEntity<?> checkWeldODFByCable(CableInStationBO cableInStationBO) {
		JSONObject jsonObject = new JSONObject();
		try {
			Boolean result = cableInStationDao.checkWeldODFByCable(cableInStationBO);
			// Response<Boolean> response = new Response<>();
			// response.setContent(result);
			// return new ResponseEntity<>(response, HttpStatus.OK);
			return ResponseBase.createResponse(result, Constains.SUCCESS, 200);
		} catch (Exception e) {
			jsonObject = JSONResponse.buildResultJson(1, "Khong co ODF dau noi", "error");
			return ResponseBase.createResponse(jsonObject, Constains.REQUIRED, 500);
		}
	}

	@Override
	public String downloadTeamplate(List<ViewCableInStationBO> cableInStationBOList, Integer type, String langCode,
			HttpServletRequest request) {
		String savePath = messageSource.getMessage("report.out", langCode);
		File dir = new File(savePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (type == 1) {
			savePath = savePath + messageSource.getMessage("cable.template.file.import.add.name", langCode)
					+ CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
		} else {
			savePath = savePath + messageSource.getMessage("cable.template.file.import.edit.name", langCode)
					+ CommonUtils.getStrDate(System.currentTimeMillis(), "yyyyMMdd") + ".xlsx";
		}
		InputStream excelFile;
		XSSFWorkbook workbook = null;
		try {
			excelFile = new ClassPathResource(Constants.TEMPLATE_FILE.TEAMPLATE_CABLEINSTATION).getInputStream();
			// excelFile = new
			// ClassPathResource("/templates/teamplate_import_doan_cap_trong_nha_tram.xlsx").getInputStream();
			workbook = new XSSFWorkbook(excelFile);
		} catch (Exception e) {
			log.error("Exception", e);
		}

		XSSFSheet sheetData = workbook.getSheetAt(0);
		int rowNum = 4;
		int index = 1;
		for (ViewCableInStationBO bo : cableInStationBOList) {
			Row row = sheetData.createRow(rowNum++);
			int i = 0;
			Cell cell0 = row.createCell(i++);
			cell0.setCellValue(index++);
			cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			Cell cell1 = row.createCell(i++);
			cell1.setCellValue(bo.getStationCode() == null ? "" : bo.getStationCode());
			cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			Cell cell2 = row.createCell(i++);
			cell2.setCellValue(bo.getSourceCode() == null ? "" : bo.getSourceCode());
			cell2.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			Cell cell3 = row.createCell(i++);
			cell3.setCellValue(bo.getDestCode() == null ? "" : bo.getDestCode());
			cell3.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			Cell cell4 = row.createCell(i++);
			cell4.setCellValue(String.valueOf(bo.getCableIndex()));
			cell4.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

			Cell cell5 = row.createCell(i++);
			// if (type != 1) {
			// cell5.setCellValue(bo.getCableCode() == null ? "" : bo.getCableCode());
			// }
			cell5.setCellValue(bo.getCableCode() == null ? "" : bo.getCableCode());
			cell5.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			Cell cell6 = row.createCell(i++);
			cell6.setCellValue(bo.getCableTypeCode() == null ? "" : bo.getCableTypeCode());
			cell6.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			Cell cell7 = row.createCell(i++);
			// cell7.setCellValue(bo.getLength() == null ? "" : bo.getLength().toString());
			// cell7.setCellValue(bo.getLength() == null ? "":
			// bo.getLength().toPlainString());
			cell7.setCellValue(bo.getLength() == null ? "" : String.format("%.3f", bo.getLength()));
			cell7.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle(workbook));

			Cell cell8 = row.createCell(i++);
			cell8.setCellValue(bo.getStatusName() == null ? "" : bo.getStatusName());
			cell8.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Cell cell9 = row.createCell(i++);
			cell9.setCellValue(
					bo.getInstallationDate() == null ? "" : simpleDateFormat.format(bo.getInstallationDate()));
			cell9.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			Cell cell10 = row.createCell(i++);
			cell10.setCellValue(bo.getNote() == null ? "" : bo.getNote());
			cell10.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));
		}

		// cable type code
		List<CatOpticalCableTypeBO> listCableTypeCode = transmissionDao.getAllCableType();
		XSSFSheet sheetStationType = workbook.getSheetAt(1);

		index = 1;
		rowNum = 2;
		for (CatOpticalCableTypeBO bo : listCableTypeCode) {
			int i = 0;
			Row row = sheetStationType.createRow(rowNum++);
			Cell cell0 = row.createCell(i++);
			cell0.setCellValue(index++);
			cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle(workbook));

			Cell cell1 = row.createCell(i++);
			cell1.setCellValue(bo.getCableTypeCode() == null ? "" : bo.getCableTypeCode());
			cell1.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

			// Cell cell2 = row.createCell(i++);
			// cell2.setCellValue(bo.getItemName() == null ? "" : bo.getItemName());
			// cell2.setCellStyle(ExcelStyleUtil.getTextCellStyle(workbook));

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
	public String importCableInStation(MultipartFile file, String langCode, HttpServletRequest request)
			throws IOException, ClassNotFoundException {
		Long userId = CommonUtil.getUserId(request);
		List<InfraCableInStationDTO> datas;
		InfraCableInStationDTO flagList;
		try {
			InfraCableInStationDTO header = ExcelDataUltils.getHeaderList(file, new InfraCableInStationDTO());
			if (!messageSource.checkEqualHeader(header, langCode)) {
				return "template-error";
			}

			datas = (List<InfraCableInStationDTO>) ExcelDataUltils.getListInExcel(file, new InfraCableInStationDTO());
			flagList = ExcelDataUltils.getFlagList(file, new InfraCableInStationDTO());
		} catch (Exception e) {
			return "template-error";
		}
		int successRecord = 0;
		int errRecord = 0;

		Map<Long, String> mapObj = new HashMap<>();
		FormResult rsODF = cableInStationDao.getAllODF(null, userId);
		List<ViewInfraOdfBO> lisODFByUser = rsODF.getListData() != null ? (List<ViewInfraOdfBO>) rsODF.getListData()
				: new ArrayList<>();

		// get index for err mapObj
		long index = 0;
		for (InfraCableInStationDTO dto : datas) {
			Map<String, String> errData = new HashMap<>();
			CableInStationBO entity = new CableInStationBO();

			// if (CommonUtils.isNullOrEmpty(dto.getStationCode()) &&
			// CommonUtils.isNullOrEmpty(dto.getSourceCode()) &&
			// CommonUtils.isNullOrEmpty(dto.getDestCode()) &&
			// CommonUtils.isNullOrEmpty(dto.getCableTypeCode()) &&
			// CommonUtils.isNullOrEmpty(dto.getLength()) ) {
			// ExcelDataUltils.addMessage(errData, "err.empty",
			// messageSource.getMessage("station.code", langCode));
			// }

			if (CommonUtils.isNullOrEmpty(dto.getStationCode())) {
				ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("station.code", langCode));
			} else {
				if (!transmissionService.checkExitStationCode(dto.getStationCode(), request)) {
					ExcelDataUltils.addMessage(errData, "err.incorrect",
							messageSource.getMessage("station.code", langCode));
				} else {
					InfraStationsBO isbo = infraStationDao.findStationByCode(dto.getStationCode(), userId);
					if (isbo == null) {
						ExcelDataUltils.addMessage(errData, "err.noscope",
								messageSource.getMessage("station.code", langCode));
					} else {
						entity.setStationId(isbo.getStationId());
						entity.setDeptId(isbo.getDeptId());
					}
				}
			}

			if (!CommonUtils.isNullOrEmpty(dto.getStationCode())) {
				if (CommonUtils.isNullOrEmpty(dto.getSourceCode())) {
					ExcelDataUltils.addMessage(errData, "err.empty",
							messageSource.getMessage("cable.sourceCode", langCode));
				} else {
					ViewInfraOdfBO bo = infraOdfDao.findOdfByCode(dto.getSourceCode());
					if (bo == null) {
						ExcelDataUltils.addMessage(errData, "err.incorrect",
								messageSource.getMessage("cable.sourceCode", langCode));
					} else {
						ViewInfraOdfBO odfD = lisODFByUser.stream()
								.filter(x -> x.getOdfCode().equals(dto.getDestCode())).findAny().orElse(null);
						if (odfD == null) {
							ExcelDataUltils.addMessage(errData, "err.noscope",
									messageSource.getMessage("cable.sourceCode", langCode));
						} else {
							if (entity.getStationId() != null) {
								if (bo.getStationId().longValue() == entity.getStationId().longValue()) {
									entity.setSourceId(bo.getOdfId());
								} else {
									ExcelDataUltils.addMessage(errData, "err.notExit.station",
											messageSource.getMessage("cable.sourceCode", langCode));
								}
							}
						}
					}
				}

				if (CommonUtils.isNullOrEmpty(dto.getDestCode())) {
					ExcelDataUltils.addMessage(errData, "err.empty",
							messageSource.getMessage("cable.destCode", langCode));
				} else {
					ViewInfraOdfBO bo = infraOdfDao.findOdfByCode(dto.getDestCode());
					if (bo == null) {
						ExcelDataUltils.addMessage(errData, "err.incorrect",
								messageSource.getMessage("cable.destCode", langCode));
					} else {
						ViewInfraOdfBO odfD = lisODFByUser.stream()
								.filter(x -> x.getOdfCode().equals(dto.getDestCode())).findAny().orElse(null);
						if (odfD == null) {
							ExcelDataUltils.addMessage(errData, "err.noscope",
									messageSource.getMessage("cable.destCode", langCode));
						} else {
							if (entity.getStationId() != null) {
								if (bo.getStationId().longValue() == entity.getStationId().longValue()) {
									entity.setDestId(bo.getOdfId());
									if (entity.getSourceId() == entity.getDestId()) {
										ExcelDataUltils.addMessage(errData, "err.odf",
												messageSource.getMessage("cable.odf", langCode));
									}
								} else {
									ExcelDataUltils.addMessage(errData, "err.notExit.station",
											messageSource.getMessage("cable.destCode", langCode));
								}
							}
						}
					}
				}
			}

			if (CommonUtils.isNullOrEmpty(dto.getCableIndex())) {
				String cableIndex = cableInStationDao.getCableIndex(entity.getSourceId(), entity.getDestId());
				if (Integer.parseInt(cableIndex) < 100) {
					entity.setCableIndex(Integer.parseInt(cableIndex));
					dto.setCableIndex(cableIndex);
				} else {
					ExcelDataUltils.addMessage(errData, messageSource.getMessage("err.notGenerate.cableIndex", langCode,
							Arrays.asList(dto.getSourceCode(), dto.getDestCode())), null);
				}

			} else {
				String regex = "[0-9]+";
				if (!dto.getCableIndex().matches(regex)) {
					ExcelDataUltils.addMessage(errData, "err.numberFormatCableIndex",
							messageSource.getMessage("cable.cableIndex", langCode));
				} else {
					if (dto.getCableIndex().length() > 2) {
						ExcelDataUltils.addMessage(errData, "err.numberFormatCableIndex",
								messageSource.getMessage("cable.cableIndex", langCode));
					} else {
						if (dto.getCableIndex().length() == 1) {
							dto.setCableIndex(dto.getCableIndex() + "0");
						}
						entity.setCableIndex(Integer.parseInt(dto.getCableIndex()));
					}
				}
			}

			if (entity.getCableIndex() != null && entity.getSourceId() != null && entity.getDestId() != null) {
				String cableCode = "";
				if (entity.getCableIndex() < 10) {
					cableCode = dto.getSourceCode() + "-" + dto.getDestCode() + "/" + "0" + entity.getCableIndex();
				} else {
					cableCode = dto.getSourceCode() + "-" + dto.getDestCode() + "/" + entity.getCableIndex();
				}
				entity.setCableCode(cableCode);
				boolean isExits = cableInStationDao.isExitByCode(entity);
				if (isExits) {
					ExcelDataUltils.addMessage(errData, "err.exits",
							messageSource.getMessage("cable.cableCode", langCode) + " " + cableCode);
					entity.setCableCode(null);
				} else {
					entity.setCableCode(cableCode);
					dto.setCableCode(cableCode);
				}
			}

			if (CommonUtils.isNullOrEmpty(dto.getCableTypeCode())) {
				ExcelDataUltils.addMessage(errData, "err.empty",
						messageSource.getMessage("cable.cableTypeCode", langCode));
			} else {
				CatOpticalCableTypeBO bo = cableInStationDao.findCableTypeByCode(dto.getCableTypeCode());
				if (bo == null) {
					ExcelDataUltils.addMessage(errData, "err.incorrect",
							messageSource.getMessage("cable.cableTypeCode", langCode));
				} else {
					entity.setCableTypeId(bo.getCableTypeId());
				}
			}

			if (CommonUtils.isNullOrEmpty(dto.getLength())) {
				ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("cable.lenghth", langCode));
			} else {
				if (dto.getLength().length() > 17) {
					ExcelDataUltils.addMessage(errData, "err.maxLength.19",
							messageSource.getMessage("cable.length", langCode));
				} else {
					try {
						String regex = "[0-9.]+";
						if (dto.getLength().matches(regex)) {
							if (StringUtils.countMatches(dto.getLength(), ".") > 1) {
								ExcelDataUltils.addMessage(errData, "err.cable.lenghth",
										messageSource.getMessage("cable.lenghth", langCode));
							} else {
								Float lengthDecimal = Float.parseFloat(dto.getLength());
								if (lengthDecimal.intValue() < 0) {
									ExcelDataUltils.addMessage(errData, "err.cable.lenghth",
											messageSource.getMessage("cable.lenghth", langCode));
								} else {
									DecimalFormat fm = new DecimalFormat("#.###");
									fm.setRoundingMode(RoundingMode.DOWN);
									BigDecimal lengthF = new BigDecimal(fm.format(lengthDecimal));
									entity.setLength(lengthF);
								}
							}

						} else {
							ExcelDataUltils.addMessage(errData, "err.cable.lenghth",
									messageSource.getMessage("cable.lenghth", langCode));
						}
					} catch (Exception e) {
						ExcelDataUltils.addMessage(errData, "err.cable.lenghth",
								messageSource.getMessage("cable.lenghth", langCode));
					}
				}
			}

			Map<String, Integer> listStatus = new WeakHashMap<>();
			listStatus.put(messageSource.getMessage("cable.statusName.zero", langCode), 0);
			listStatus.put(messageSource.getMessage("cable.statusName.one", langCode), 1);
			listStatus.put(messageSource.getMessage("cable.statusName.two", langCode), 2);

			if (!CommonUtils.isNullOrEmpty(dto.getStatusName())) {
				if (listStatus.containsKey(dto.getStatusName())) {
					entity.setStatus(listStatus.get(dto.getStatusName()));
				} else {
					ExcelDataUltils.addMessage(errData, "err.incorrect",
							messageSource.getMessage("cable.status", langCode));
				}
			} else {
				ExcelDataUltils.addMessage(errData, "err.choose", messageSource.getMessage("cable.status", langCode));
			}

			if (!CommonUtils.isNullOrEmpty(dto.getInstallationDate())) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				dateFormat.setLenient(false);
				try {
					Date conDate = dateFormat.parse(dto.getInstallationDate());
					entity.setInstallationDate(conDate);
				} catch (Exception e) {
					ExcelDataUltils.addMessage(errData, "err.dateFormat",
							messageSource.getMessage("cable.installationDate", langCode));
				}
			}

			if (dto.getNote().length() > 500) {
				ExcelDataUltils.addMessage(errData, "err.maxlength.500",
						messageSource.getMessage("station.note", langCode));
			} else {
				entity.setNote(dto.getNote());
			}

			if (errData.isEmpty()) {
				cableInStationDao.saveCable(entity);
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
			// get err list by left to right;
			for (int i = errList.size() - 1; i >= 0; i--) {
				rs.append(errList.get(i));
			}

			if (CommonUtils.isNullOrEmpty(rs.toString())) {
				rs.append("OK");
			}
			mapObj.put(index++, rs.toString());

		}
		String savePath = ExcelDataUltils.writeResultExcel(file, new InfraCableInStationDTO(), mapObj,
				"KetQua_IMP_DCTNT_Insert", datas);
		return savePath + "+" + successRecord + "+" + errRecord;
	}

	@Override
	public String editCableInStation(MultipartFile file, String langCode, HttpServletRequest request)
			throws IOException, ClassNotFoundException {
		Long userId = CommonUtil.getUserId(request);
		List<InfraCableInStationDTO> datas = (List<InfraCableInStationDTO>) ExcelDataUltils.getListInExcel(file,
				new InfraCableInStationDTO());
		InfraCableInStationDTO flagList = ExcelDataUltils.getFlagList(file, new InfraCableInStationDTO());
		int successRecord = 0;
		int errRecord = 0;

		Map<Long, String> mapObj = new HashMap<>();

		// get index for err mapObj
		long index = 0;
		for (InfraCableInStationDTO dto : datas) {
			Map<String, String> errData = new HashMap<>();
			CableInStationBO entity = new CableInStationBO();

			if (CommonUtils.isNullOrEmpty(dto.getCableCode())) {
				ExcelDataUltils.addMessage(errData, "err.empty", messageSource.getMessage("cable.cableCode", langCode));
			} else {
				dto.setCableCode((dto.getCableCode().toLowerCase()));
				entity = cableInStationDao.findCableByCode(dto.getCableCode());
				if (entity == null) {
					ExcelDataUltils.addMessage(errData, "err.incorrect",
							messageSource.getMessage("cable.cableCode", langCode));
				} else {
					if (dto.getLength().length() > 19) {
						ExcelDataUltils.addMessage(errData, "err.maxlength.19",
								messageSource.getMessage("cable.length", langCode));
					} else {
						try {
							String regex = "[0-9.]+";
							if (dto.getLength().matches(regex)) {
								if (StringUtils.countMatches(dto.getLength(), ".") > 1) {
									ExcelDataUltils.addMessage(errData, "err.cable.lenghth",
											messageSource.getMessage("cable.lenghth", langCode));
								} else {
									Float lengthDecimal = Float.parseFloat(dto.getLength());
									if (lengthDecimal.intValue() < 0) {
										ExcelDataUltils.addMessage(errData, "err.cable.lenghth",
												messageSource.getMessage("cable.lenghth", langCode));
									} else {
										DecimalFormat fm = new DecimalFormat("#.###");
										fm.setRoundingMode(RoundingMode.DOWN);
										BigDecimal lengthF = new BigDecimal(fm.format(lengthDecimal));
										// entity.setLength(lengthF);
										if (flagList.getLength().equals("Y")) {
											entity.setLength(lengthF);
										}

									}
								}

							} else {
								ExcelDataUltils.addMessage(errData, "err.cable.lenghth",
										messageSource.getMessage("cable.lenghth", langCode));
							}
						} catch (Exception e) {
							ExcelDataUltils.addMessage(errData, "err.cable.lenghth",
									messageSource.getMessage("cable.lenghth", langCode));
						}
					}

					Map<String, Integer> listStatus = new WeakHashMap<>();
					listStatus.put(messageSource.getMessage("cable.statusName.zero", langCode), 0);
					listStatus.put(messageSource.getMessage("cable.statusName.one", langCode), 1);
					listStatus.put(messageSource.getMessage("cable.statusName.two", langCode), 2);
					if (flagList.getStatusName().equals("Y")) {
						if (!CommonUtils.isNullOrEmpty(dto.getStatusName())) {
							if (listStatus.containsKey(dto.getStatusName())) {
								entity.setStatus(listStatus.get(dto.getStatusName()));
							} else {
								ExcelDataUltils.addMessage(errData, "err.incorrect",
										messageSource.getMessage("cable.status", langCode));
							}
							// entity.setStatus(listStatus.get(dto.getStatusName()));
						} else {
							ExcelDataUltils.addMessage(errData, "err.choose",
									messageSource.getMessage("cable.status", langCode));
						}
					}

					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					dateFormat.setLenient(false);
					if (flagList.getInstallationDate().equals("Y")) {
						if (!CommonUtils.isNullOrEmpty(dto.getInstallationDate())) {
							try {
								Date conDate = dateFormat.parse(dto.getInstallationDate());

								entity.setInstallationDate(conDate);

							} catch (Exception e) {
								ExcelDataUltils.addMessage(errData, "err.dateFormat",
										messageSource.getMessage("cable.installationDate", langCode));
							}
						} else {
							entity.setInstallationDate(null);
						}
					}

					if (dto.getNote().length() > 500) {
						ExcelDataUltils.addMessage(errData, "err.maxlength.500",
								messageSource.getMessage("station.note", langCode));
					} else {
						if (flagList.getNote().equals("Y")) {
							entity.setNote(dto.getNote());
						}

					}
					if (flagList.getCableTypeCode().equals("Y")) {
						if (CommonUtils.isNullOrEmpty(dto.getCableTypeCode())) {
							ExcelDataUltils.addMessage(errData, "err.choose",
									messageSource.getMessage("cable.cableTypeCode", langCode));
						} else {
							CatOpticalCableTypeBO bo = cableInStationDao.findCableTypeByCode(dto.getCableTypeCode());
							if (bo == null) {
								ExcelDataUltils.addMessage(errData, "err.incorrect",
										messageSource.getMessage("cable.cableTypeCode", langCode));
							} else {
								entity.setCableTypeId(bo.getCableTypeId());
							}
						}
					}
				}
			}

			if (errData.isEmpty()) {
				cableInStationDao.saveCable(entity);
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

			// get err list by left to right;
			for (int i = errList.size() - 1; i >= 0; i--) {
				rs.append(errList.get(i));
			}

			if (CommonUtils.isNullOrEmpty(rs.toString())) {
				rs.append("OK");
			}
			mapObj.put(index++, rs.toString());

		}

		String savePath = ExcelDataUltils.writeResultExcel(file, new InfraCableInStationDTO(), mapObj,
				"KetQua_IMP_DCTNT_Update");
		return savePath + "+" + successRecord + "+" + errRecord;
	}

	@Override
	public String exportExcel(CableInStationBO cableInStationBO, String langCode, HttpServletRequest request) {
		Long userId = CommonUtil.getUserId(request);
		FormResult result = cableInStationDao.findAdvanceCable(cableInStationBO, userId);
		List<ViewCableInStationBO> data = (List<ViewCableInStationBO>) result.getListData();

		if (CollectionUtils.isEmpty(data)) {
			return null;
		}

		Workbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("cable.sheet", langCode));

		// Set title
		Row firstRow = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11));
		Cell titleCell = firstRow.createCell(0);
		titleCell.setCellValue(messageSource.getMessage("cable.title", langCode));
		titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

		Row secondRow = sheet.createRow(2);
		Cell titleCell2 = secondRow.createCell(6);
		titleCell2.setCellValue(messageSource.getMessage("station.report.date", langCode)
				+ CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
		titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

		// Set header
		List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.stationCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.sourceCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.destCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.cableIndex", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.cableCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.cableTypeCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.status", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.lenghth", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.installationDate", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.dept", langCode), 20 * 256));
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
		path = path + "EXPORT_CAPTRONGNHATRAM" + "_"
				+ CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm") + ".xlsx";

		DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
		// Set data
		for (ViewCableInStationBO item : data) {

			Row row = sheet.createRow(rowNum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(index++);
			cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(item.getStationCode() != null ? item.getStationCode() : "");
			cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(item.getSourceCode() != null ? item.getSourceCode() : "");
			cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(item.getDestCode() != null ? item.getDestCode() : "");
			cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(item.getCableIndex());
			cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(item.getCableCode() != null ? item.getCableCode() : "");
			cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(item.getCableTypeCode() != null ? item.getCableTypeCode() : "");
			cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell7 = row.createCell(7);
			if (item.getStatus() != null) {
				item.setStatusName(item.getStatus() == 0 ? messageSource.getMessage("cable.statusName.zero", langCode)
						: item.getStatus() == 1 ? messageSource.getMessage("cable.statusName.one", langCode)
								: item.getStatus() == 2 ? messageSource.getMessage("cable.statusName.two", langCode)
										: null);
			}
			cell7.setCellValue(item.getStatusName() != null ? item.getStatusName() : "");
			cell7.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(item.getLength() != null ? String.format("%.3f", item.getLength().floatValue()) : "");
			cell8.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));

			Cell cell9 = row.createCell(9);
			if (item.getInstallationDate() != null) {
				cell9.setCellValue(df.format(item.getInstallationDate()));
			} else {
				cell8.setCellValue("");
			}
			cell9.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

			Cell cell10 = row.createCell(10);
			cell10.setCellValue(item.getDeptName() != null ? item.getDeptName() : "");
			cell10.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell11 = row.createCell(11);
			cell11.setCellValue(item.getNote() != null ? item.getNote() : "");
			cell11.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));
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
	public String exportExcelChose(List<ViewCableInStationBO> data, String langCode) {

		if (CollectionUtils.isEmpty(data)) {
			return null;
		}

		Workbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("cable.sheet", langCode));

		// Set title
		Row firstRow = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11));
		Cell titleCell = firstRow.createCell(0);
		titleCell.setCellValue(messageSource.getMessage("cable.title", langCode));
		titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

		Row secondRow = sheet.createRow(2);
		Cell titleCell2 = secondRow.createCell(6);
		titleCell2.setCellValue(messageSource.getMessage("station.report.date", langCode)
				+ CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
		titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

		// Set header
		List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.stationCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.sourceCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.destCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.cableIndex", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.cableCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.cableTypeCode", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("station.status", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.lenghth", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.installationDate", langCode), 20 * 256));
		headerDTOList.add(new HeaderDTO(messageSource.getMessage("cable.dept", langCode), 20 * 256));
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
		path = path + "EXPORT_CAPTRONGNHATRAM" + "_"
				+ CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm") + ".xlsx";

		DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);

		// Set data
		for (ViewCableInStationBO item : data) {

			Row row = sheet.createRow(rowNum++);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(index++);
			cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(item.getStationCode() != null ? item.getStationCode() : "");
			cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(item.getSourceCode() != null ? item.getSourceCode() : "");
			cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(item.getDestCode() != null ? item.getDestCode() : "");
			cell3.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(item.getCableIndex());
			cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell5 = row.createCell(5);
			cell5.setCellValue(item.getCableCode() != null ? item.getCableCode() : "");
			cell5.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(item.getCableTypeCode() != null ? item.getCableTypeCode() : "");
			cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(item.getStatusName() != null ? item.getStatusName() : "");
			cell7.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell8 = row.createCell(8);
			// cell8.setCellValue(item.getLength() != null ?
			// String.valueOf(item.getLength().longValue()) : "");
			cell8.setCellValue(item.getLength() != null ? String.format("%.3f", item.getLength()) : "");
			cell8.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));

			Cell cell9 = row.createCell(9);
			if (item.getInstallationDate() != null) {
				cell9.setCellValue(df.format(item.getInstallationDate()));
			} else {
				cell9.setCellValue("");
			}
			cell9.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

			Cell cell10 = row.createCell(10);
			cell10.setCellValue(item.getDeptName() != null ? item.getDeptName() : "");
			cell10.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

			Cell cell11 = row.createCell(11);
			cell11.setCellValue(item.getNote() != null ? item.getNote() : "");
			cell11.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));
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

}
