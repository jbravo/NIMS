package com.viettel.nims.transmission.service;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.commons.util.PaginationDTO;
import com.viettel.nims.commons.util.Response;
import com.viettel.nims.transmission.commom.*;
import com.viettel.nims.transmission.dao.InfraCablesDao;
import com.viettel.nims.transmission.dao.WeldSleeveDao;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.CableInSleeveResponDto;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import com.viettel.nims.transmission.model.view.WeldSleeveRequestDto;

import com.viettel.nims.transmission.model.view.ViewWeldSleeveBO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
@Slf4j
@Service
@Transactional(transactionManager="globalTransactionManager")
public class WeldSleeveServiceImpl implements WeldSleeveService {
	private static final Logger LOGGER = Logger.getLogger(WeldSleeveService.class.getName());
	@Autowired
	WeldSleeveDao weldSleeveDao;
	PaginationDTO paginationDTO;
	WeldSleeveBO weldSleeveBO = new WeldSleeveBO();
	WeldSleeveBO.PK pk = new WeldSleeveBO.PK();

	@Autowired
	InfraCablesDao infraCablesDao;
	public static final String TYPE_WELD_DIRECT = "direct";
	public static final String TYPE_WELD_NORMAL = "normal";
	public static final String WELD_AMOUNT_ONE = "weldOne";
	public static final String WELD_AMOUNT_MANY = "weldMany";

  @Autowired
  MessageResource messageSource;

	@Override
	// tim kiem co ban
	public ResponseEntity<?> findBasicsWeld(ViewWeldSleeveBO viewWeldSleeveBO) {
		try {
			FormResult result = weldSleeveDao.findBasicWeld(viewWeldSleeveBO);
			Response<FormResult> response = new Response<>();
			response.setContent(result);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> findWeldSleeveBO() {
		try {
			FormResult result = weldSleeveDao.findWeldSleeveBO();
			Response<FormResult> response = new Response<>();
			response.setContent(result);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> findWeldSleeveTestBO() {
		try {
			FormResult result = weldSleeveDao.findWeldSleeveTestBO();
			Response<FormResult> response = new Response<>();
			response.setContent(result);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> checkSleeveIdLaneId(List<ViewWeldSleeveBO> listViewWeldSleeveBO) {
	  List<ViewWeldSleeveBO> rs = new ArrayList<>();
		try {
		  for(ViewWeldSleeveBO bo : listViewWeldSleeveBO) {
        boolean isLast = weldSleeveDao.checkSleeveIdLaneId(bo);
        if(isLast){
          rs.add(bo);
        }
      }
      if(CommonUtils.isNullOrEmpty(rs)){
        return ResponseBase.createResponse(null,"Success",HttpStatus.OK);
      }else{
        return ResponseBase.createResponse(rs,"Error",HttpStatus.OK);
      }
		} catch (Exception e) {
		  log.error("Exception", e);
			return ResponseBase.createResponse(null,"Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
  }

//  @Override
//  public JSONObject delete(Long id) {
//    return null;
//  }

//  @Override
//  public JSONObject deleteMultipe(List<WeldSleeveBO> weldSleeveBO) {
//    return null;
//  }

	@Override
	public void delele(WeldSleeveBO.PK id) {
		// WeldSleeveBO weldSleeveBO = weldSleeveDao.findById(id);
		if (weldSleeveBO != null) {
			weldSleeveDao.delete(weldSleeveBO.getPk());
		}
	}

	@Override
	public JSONObject deleteByFiveField(List<WeldSleeveBO> weldSleeveBO) {
		JSONObject data = new JSONObject();
		return data;
	}

	@Override
	public JSONObject deleteEmbeddedId(List<WeldSleeveBO.PK> weldSleeveBO) {
		JSONObject data = new JSONObject();
//		for (WeldSleeveBO.PK weldSleeveBOS : weldSleeveBO ) {
      if (weldSleeveBO != null) {
        Integer result = weldSleeveDao.deleteByFiveField(weldSleeveBO);
        CntCableLaneBO result1 = weldSleeveDao.findLaneIdByCableId(weldSleeveBO.get(0).getSourceCableId());
        if (result1 != null ) {
         String result2 = weldSleeveDao.updateInfraCableLane(result1.getLaneId());
        }
//      String result1 = weldSleeveDao.updateInfraCableLane();
        if (result > 0 ) {
         data.put("code", 1);
        } else {
          data.put("code", 0);
        }
      }
//    }


		return data;
	}

  @Override
  public String dowloadTemplate(List<ViewWeldSleeveBO> viewWeldSleeveBO) {
    String savePath = "";
    File dir = new File(savePath);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return null;
  }


  // T?m ki?m m? c?p theo di?m d?u v? di?m cu?i
	@Override
	public ResponseEntity<?> findCableByHolderId(Long holderId) {
		try {
			List<InfraCablesBO> result = infraCablesDao.findCableByHolderId(holderId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.info("Context: " + LOGGER.getName());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public String saveWeldSleeve(WeldSleeveRequestDto weldSleeveRequestDto) {
		// Lay thong tin cap
		try {
			InfraCablesBO infraCablesBOSource, infraCablesBODest;
			if (weldSleeveRequestDto != null) {
				infraCablesBOSource = infraCablesDao.finById(weldSleeveRequestDto.getCableSourceId());// Cap den
				infraCablesBODest = infraCablesDao.finById(weldSleeveRequestDto.getCableDestId());// Cap
			} else {
				return "Fail";
			}
			List<CableInSleeveResponDto> listCableInSleeveResponceSource = new ArrayList<CableInSleeveResponDto>();
			List<CableInSleeveResponDto> listCableInSleeveResponceDest = new ArrayList<CableInSleeveResponDto>();
			if (infraCablesBOSource != null && infraCablesBODest != null) {
				// Danh sanh soi chua noi cap den
				listCableInSleeveResponceSource = weldSleeveDao.getListCableId(infraCablesBOSource.getCableTypeId(),
						weldSleeveRequestDto.getSleeveId(), weldSleeveRequestDto.getCableSourceId());
				// Danh sach soi da noi
				listCableInSleeveResponceDest = weldSleeveDao.getListCableId(infraCablesBODest.getCableTypeId(),
						weldSleeveRequestDto.getSleeveId(), weldSleeveRequestDto.getCableDestId());
			}
			if (TYPE_WELD_DIRECT.equals(weldSleeveRequestDto.getTypeWeld())) {// han truc tiep
				if (WELD_AMOUNT_ONE.equals(weldSleeveRequestDto.getWeldAmount())) {// Han mot cap
					// Han 1 cap va truc tiep
					// Check so soi theo quad
					// Danh sach soi cung quad den
					// Kiem tra gia tri quad den, quad di
					if (weldSleeveRequestDto.getQuadSource() != null && weldSleeveRequestDto.getQuadSource() != null) {
						List<CableInSleeveResponDto> listInputCableSleeveSource = new ArrayList<CableInSleeveResponDto>();
						// Danh sach soi cung quad di
						List<CableInSleeveResponDto> listInputCableSleeveDest = new ArrayList<CableInSleeveResponDto>();
						// Cap den
						for (CableInSleeveResponDto cab : listCableInSleeveResponceSource) {
							if (cab.getQuad() == weldSleeveRequestDto.getQuadSource()) {
								listInputCableSleeveSource.add(cab);
							}
						}
						// Cap di
						for (CableInSleeveResponDto cab : listCableInSleeveResponceDest) {
							if (cab.getQuad() == weldSleeveRequestDto.getQuadDest()) {
								listInputCableSleeveDest.add(cab);
							}
						}
						// Kiem tra dieu kien khi them moi khi han quad den, quad di
						if ((listInputCableSleeveSource.size() == listInputCableSleeveDest.size())
								&& listInputCableSleeveDest.size() > 0) {
							int listInputCableSleeveDestSize = listInputCableSleeveDest.size();
							for (int i = 0; i < listInputCableSleeveDestSize; i++) {
								WeldSleeveBO weldSleeveBO = new WeldSleeveBO();
								WeldSleeveBO.PK pk = new WeldSleeveBO.PK();
								pk.setSleeveId(weldSleeveRequestDto.getSleeveId());
								pk.setSourceCableId(weldSleeveRequestDto.getCableSourceId());
								pk.setSourceLineNo(listInputCableSleeveSource.get(i).getYarn());
								pk.setDestCableId(weldSleeveRequestDto.getCableDestId());
								pk.setDestLineNo(listInputCableSleeveDest.get(i).getYarn());
								weldSleeveBO.setPk(pk);
								if(weldSleeveRequestDto.getAttenuation()!=null && weldSleeveRequestDto.getAttenuation().toString()!="") {
									weldSleeveBO.setAttenuation(weldSleeveRequestDto.getAttenuation());
								}
								if(weldSleeveRequestDto.getWelddingBy()!=null && weldSleeveRequestDto.getWelddingBy().trim()!="") {
									weldSleeveBO.setCreateUser(weldSleeveRequestDto.getWelddingBy().trim());
								}
								Date createDate = new Date();
								weldSleeveBO.setCreateDate(createDate);
								weldSleeveDao.saveWeldSleeve(weldSleeveBO);
							}
						} else {
							return "Fail";
						}
					} else {
						return "Fail";
					}

				} else if (WELD_AMOUNT_MANY.equals(weldSleeveRequestDto.getWeldAmount())) {// Han nhieu cap
					// Han truc tiep va han nhieu cap
					// Check so soi theo quad
					// Kiem tra quad den (tu,den), quad di(tu,den)
					if (weldSleeveRequestDto.getQuadDestStart() != null && weldSleeveRequestDto.getQuadDestEnd() != null
							&& weldSleeveRequestDto.getQuadSourceStart() != null
							&& weldSleeveRequestDto.getQuadSourceEnd() != null) {
						// Danh sach soi cung quad den
						List<CableInSleeveResponDto> listInputCableSleeveSource = new ArrayList<CableInSleeveResponDto>();
						// Danh sach soi cung quad di
						List<CableInSleeveResponDto> listInputCableSleeveDest = new ArrayList<CableInSleeveResponDto>();
						// Cap den
						for (CableInSleeveResponDto cab : listCableInSleeveResponceSource) {
							int weldSleeveRequestDtoQuadSourceStart = weldSleeveRequestDto.getQuadSourceStart();
							int weldSleeveRequestDtoQuadSourceEnd = weldSleeveRequestDto.getQuadSourceEnd();
							for (int i = weldSleeveRequestDtoQuadSourceStart; i <= weldSleeveRequestDtoQuadSourceEnd; i++) {
								if (cab.getQuad() == i) {
									listInputCableSleeveSource.add(cab);
								}
							}
						}
						// Cap di
						for (CableInSleeveResponDto cab : listCableInSleeveResponceDest) {
							int weldSleeveRequestDtoQuadDestStart = weldSleeveRequestDto.getQuadDestStart();
							int weldSleeveRequestDtoQuadDestEnd = weldSleeveRequestDto.getQuadDestEnd();
							for (int i = weldSleeveRequestDtoQuadDestStart; i <= weldSleeveRequestDtoQuadDestEnd; i++) {
								if (cab.getQuad() == i) {
									listInputCableSleeveDest.add(cab);
								}
							}
						}
						if ((listInputCableSleeveDest.size() == listInputCableSleeveSource.size())
								&& listInputCableSleeveDest.size() > 0) {
							int listInputCableSleeveDestSize = listInputCableSleeveDest.size();
							for (int i = 0; i < listInputCableSleeveDestSize; i++) {
								WeldSleeveBO weldSleeveBO = new WeldSleeveBO();
								WeldSleeveBO.PK pk = new WeldSleeveBO.PK();
								pk.setSleeveId(weldSleeveRequestDto.getSleeveId());
								pk.setSourceCableId(weldSleeveRequestDto.getCableSourceId());
								pk.setSourceLineNo(listInputCableSleeveSource.get(i).getYarn());
								pk.setDestCableId(weldSleeveRequestDto.getCableDestId());
								pk.setDestLineNo(listInputCableSleeveDest.get(i).getYarn());
								weldSleeveBO.setPk(pk);
								if(weldSleeveRequestDto.getAttenuation()!=null && weldSleeveRequestDto.getAttenuation().toString()!="") {
									weldSleeveBO.setAttenuation(weldSleeveRequestDto.getAttenuation());
								}
								if(weldSleeveRequestDto.getWelddingBy()!=null && weldSleeveRequestDto.getWelddingBy().trim()!="") {
									weldSleeveBO.setCreateUser(weldSleeveRequestDto.getWelddingBy().trim());
								}
								Date createDate = new Date();
								weldSleeveBO.setCreateDate(createDate);
								weldSleeveDao.saveWeldSleeve(weldSleeveBO);
							}
						} else {
							return "Fail";
						}
					}

				} else {
					return "Fail";
				}
			} else if (TYPE_WELD_NORMAL.equals(weldSleeveRequestDto.getTypeWeld())) {// Han thong thuong
				if (WELD_AMOUNT_ONE.equals(weldSleeveRequestDto.getWeldAmount())) {// Han mot cap
					// Han 1 cap va thong thuong
					if (weldSleeveRequestDto.getYarnSource() != null && weldSleeveRequestDto.getYarnDest() != null) {
						boolean checkYarnSource = false;
						boolean checkYarnDest = false;
						// Kiem tra soi den
						for (CableInSleeveResponDto cab : listCableInSleeveResponceSource) {
							if (cab.getYarn() == weldSleeveRequestDto.getYarnSource()) {
								checkYarnSource = true;
							}
						}
						// kiem tra soi di
						for (CableInSleeveResponDto cab : listCableInSleeveResponceDest) {
							if (cab.getYarn() == weldSleeveRequestDto.getYarnDest()) {
								checkYarnDest = true;
							}
						}
						if (checkYarnDest && checkYarnSource) {
							WeldSleeveBO weldSleeveBO = new WeldSleeveBO();
							WeldSleeveBO.PK pk = new WeldSleeveBO.PK();
							pk.setSleeveId(weldSleeveRequestDto.getSleeveId());
							pk.setSourceCableId(weldSleeveRequestDto.getCableSourceId());
							pk.setSourceLineNo(weldSleeveRequestDto.getYarnSource());
							pk.setDestCableId(weldSleeveRequestDto.getCableDestId());
							pk.setDestLineNo(weldSleeveRequestDto.getYarnDest());
							weldSleeveBO.setPk(pk);
							if(weldSleeveRequestDto.getAttenuation()!=null && weldSleeveRequestDto.getAttenuation().toString()!="") {
								weldSleeveBO.setAttenuation(weldSleeveRequestDto.getAttenuation());
							}
							if(weldSleeveRequestDto.getWelddingBy()!=null && weldSleeveRequestDto.getWelddingBy().trim()!="") {
								weldSleeveBO.setCreateUser(weldSleeveRequestDto.getWelddingBy().trim());
							}
							Date createDate = new Date();
							weldSleeveBO.setCreateDate(createDate);
							weldSleeveDao.saveWeldSleeve(weldSleeveBO);
						}
					}
				} else if (WELD_AMOUNT_MANY.equals(weldSleeveRequestDto.getWeldAmount())) {// Han nhieu cap
					// Han truc tiep va thong thuong
					if (weldSleeveRequestDto.getCableSourceYarnStart() != null
							&& weldSleeveRequestDto.getCableSourceYarnEnd() != null
							&& weldSleeveRequestDto.getCableDestYarnStart() != null
							&& weldSleeveRequestDto.getCableDestYarnEnd() != null) {
						List<CableInSleeveResponDto> listInputCableSleeveSource = new ArrayList<CableInSleeveResponDto>();
						// Danh sach soi cung quad di
						List<CableInSleeveResponDto> listInputCableSleeveDest = new ArrayList<CableInSleeveResponDto>();
						for (CableInSleeveResponDto cab : listCableInSleeveResponceSource) {
							int weldSleeveRequestDtoCableSourceYarnStart = weldSleeveRequestDto
									.getCableSourceYarnStart();
							int weldSleeveRequestDtoCableSourceYarnEnd = weldSleeveRequestDto.getCableSourceYarnEnd();
							for (int i = weldSleeveRequestDtoCableSourceYarnStart; i <= weldSleeveRequestDtoCableSourceYarnEnd; i++) {
								if (cab.getYarn() == i) {
									listInputCableSleeveSource.add(cab);
								}
							}
						}
						for (CableInSleeveResponDto cab : listCableInSleeveResponceDest) {
							int weldSleeveRequestDtoCableDestYarnStart = weldSleeveRequestDto.getCableDestYarnStart();
							int weldSleeveRequestDtogetCableDestYarnEnd = weldSleeveRequestDto.getCableDestYarnEnd();
							for (int i = weldSleeveRequestDtoCableDestYarnStart; i <= weldSleeveRequestDtogetCableDestYarnEnd; i++) {
								if (cab.getYarn() == i) {
									listInputCableSleeveDest.add(cab);
								}
							}
						}
						if ((listInputCableSleeveDest.size() == listInputCableSleeveSource.size())
								&& listInputCableSleeveSource.size() > 0) {
							int listInputCableSleeveDestSize = listInputCableSleeveDest.size();
							for (int i = 0; i < listInputCableSleeveDestSize; i++) {
								WeldSleeveBO weldSleeveBO = new WeldSleeveBO();
								WeldSleeveBO.PK pk = new WeldSleeveBO.PK();
								pk.setSleeveId(weldSleeveRequestDto.getSleeveId());
								pk.setSourceCableId(weldSleeveRequestDto.getCableSourceId());
								pk.setSourceLineNo(listInputCableSleeveSource.get(i).getYarn());
								pk.setDestCableId(weldSleeveRequestDto.getCableDestId());
								pk.setDestLineNo(listInputCableSleeveDest.get(i).getYarn());
								weldSleeveBO.setPk(pk);
								if(weldSleeveRequestDto.getAttenuation()!=null && weldSleeveRequestDto.getAttenuation().toString()!="") {
									weldSleeveBO.setAttenuation(weldSleeveRequestDto.getAttenuation());
								}
								if(weldSleeveRequestDto.getWelddingBy()!=null && weldSleeveRequestDto.getWelddingBy().trim()!="") {
									weldSleeveBO.setCreateUser(weldSleeveRequestDto.getWelddingBy().trim());
								}
								Date createDate = new Date();
								weldSleeveBO.setCreateDate(createDate);
								weldSleeveDao.saveWeldSleeve(weldSleeveBO);
							}
						}
					}
				} else {
					return "Fail";
				}
			} else {
				return "Fail";
			}
		} catch (Exception e) {
			LOGGER.info("context" + LOGGER.getName());
		}

		return "Secces";
	}

	@Override
	public List<CableInSleeveResponDto> getListCableId(long cableTypeId, long sleeveId, long cableId) {
		return weldSleeveDao.getListCableId(cableTypeId, sleeveId, cableId);
	}

	@Override
	public ResponseEntity<?> findSleeveCodeById(Long id) {
		InfraSleevesBO infraSleeveBO = weldSleeveDao.findSleeveCodeById(id);
		return new ResponseEntity<>(infraSleeveBO, HttpStatus.OK);
	}

	@Override
	public WeldSleeveBO findById(WeldSleeveBO.PK id) {
		return weldSleeveDao.findById(id);
	}

	@Override
	public List<WeldSleeveBO> findAll() {
		return weldSleeveDao.findAll();
	}

	@Override
	public JSONObject deleteMultipe(List<WeldSleeveBO> weldSleeveBOList) {
		JSONObject data = new JSONObject();

		if (data.size() == 0) {
			for (int i = 0; i < weldSleeveBOList.size(); i++) {
				WeldSleeveBO weldSleeveBO = weldSleeveDao.findById(weldSleeveBOList.get(i).getPk());
				if (weldSleeveBO != null) {
					int result = weldSleeveDao.delete(weldSleeveBOList.get(i).getPk());
					if (result > 0) {
						data.put("code", 1);
					}
				}
			}
		}
		return data;
	}

	@Override
	public InfraCablesBO getCableById(Long cableId) {
		return weldSleeveDao.getCableById(cableId);
	}

	@Override
	public String updateWeldSleeve(WeldSleeveRequestDto weldSleeveRequestDto) {
		if (weldSleeveRequestDto != null) {
			WeldSleeveBO weldSleeveBO = new WeldSleeveBO();
			WeldSleeveBO.PK pk = new WeldSleeveBO.PK();
			pk.setSleeveId(weldSleeveRequestDto.getSleeveId());
			pk.setSourceCableId(weldSleeveRequestDto.getCableSourceId());
			pk.setSourceLineNo(weldSleeveRequestDto.getYarnSource());
			pk.setDestCableId(weldSleeveRequestDto.getCableDestId());
			pk.setDestLineNo(weldSleeveRequestDto.getYarnDest());
			weldSleeveBO.setPk(pk);
			if(weldSleeveRequestDto.getAttenuation()!=null && weldSleeveRequestDto.getAttenuation().toString()!="") {
				weldSleeveBO.setAttenuation(weldSleeveRequestDto.getAttenuation());
			}
			if(weldSleeveRequestDto.getWelddingBy()!=null && weldSleeveRequestDto.getWelddingBy().trim()!="") {
				weldSleeveBO.setCreateUser(weldSleeveRequestDto.getWelddingBy().trim());
			}
			return weldSleeveDao.updateWeldSleeve(weldSleeveBO);
		}
		return "fail";
	}

	//export
  @Override
  public String exportExcel(ViewWeldSleeveBO viewWeldSleeveBO, String langCode, HttpServletRequest request) {
    Long userId = CommonUtil.getUserId(request);
    FormResult result = weldSleeveDao.findBasicWeld(viewWeldSleeveBO);
    List<ViewWeldSleeveBO> data = (List<ViewWeldSleeveBO>) result.getListData();

    if (CollectionUtils.isEmpty(data)) {
      return null;
    }

    Workbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("weldsleeve.sheet", langCode));

    Row firstRow = sheet.createRow(1);
    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));
    Cell titleCell = firstRow.createCell(0);
    titleCell.setCellValue(messageSource.getMessage("weldsleeve.title", langCode));
    titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

    Row secondRow = sheet.createRow(2);
    sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
    Cell titleCell2 = secondRow.createCell(0);
    titleCell2.setCellValue(messageSource.getMessage("station.report.date", langCode) + CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
    titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));


    //header
    List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeve.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("source.cable.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("source.line.no", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("dest.cable.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("dest.line.no", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("create.user", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("attennuation", langCode), 20 * 256));

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
    path = path + "EXPORT_HANNOITAIMANGXONG" + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm") + ".xlsx";
    System.out.println(path);
    DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);
    for(ViewWeldSleeveBO item : data) {
      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell1 = row.createCell(1);
      cell1.setCellValue(item.getSleeveCode() != null ? item.getSleeveCode() : "");
      cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell2 = row.createCell(2);
      cell2.setCellValue(item.getSourceCableCode() != null ? item.getSourceCableCode() : "");
      cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell3 = row.createCell(3);
      cell3.setCellValue(item.getSourceLineNo().toString());
      cell3.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell4 = row.createCell(4);
      cell4.setCellValue(item.getDestCableCode() != null ? item.getDestCableCode() : "");
      cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell5 = row.createCell(5);
      cell5.setCellValue(item.getDestLineNo().toString());
      cell5.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell6 = row.createCell(6);
      cell6.setCellValue(item.getCreateUser() != null ? item.getCreateUser() : "");
      cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell7 = row.createCell(7);
      cell7.setCellValue( item.getAttenuation() != null ? String.format("%.2f", item.getAttenuation()) : "");
      cell7.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));
    }

    try {
//     Write file
      FileOutputStream outputStream = new FileOutputStream(path);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
//      logger.error(e.getMessage());
    }

    return path;
  }

  @Override
  public String exportExcelChose(List<ViewWeldSleeveBO> data, String langCode) {

    if (CollectionUtils.isEmpty(data)) {
      return null;
    }

    Workbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = (XSSFSheet) workbook.createSheet(messageSource.getMessage("weldsleeve.sheet", langCode));

    // Set title
    Row firstRow = sheet.createRow(1);
    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));
    Cell titleCell = firstRow.createCell(0);
    titleCell.setCellValue(messageSource.getMessage("weldsleeve.title", langCode));
    titleCell.setCellStyle(ExcelStyleUtil.getTitleCellStyle((XSSFWorkbook) workbook));

    Row secondRow = sheet.createRow(2);
    sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
    Cell titleCell2 = secondRow.createCell(0);
    titleCell2.setCellValue(messageSource.getMessage("station.report.date", langCode) + CommonUtils.getStrDate(System.currentTimeMillis(), "dd/MM/yyyy"));
    titleCell2.setCellStyle(ExcelStyleUtil.getReportDateCellStyle((XSSFWorkbook) workbook));

    //header
    List<HeaderDTO> headerDTOList = new ArrayList<HeaderDTO>();
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("common.label.stt", langCode), 10 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("sleeve.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("source.cable.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("source.line.no", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("dest.cable.code", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("dest.line.no", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("create.user", langCode), 20 * 256));
    headerDTOList.add(new HeaderDTO(messageSource.getMessage("attennuation", langCode), 20 * 256));

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
    path = path + "EXPORT_HANNOITAIMANGXONG" + "_" + CommonUtils.getStrDate(System.currentTimeMillis(), "MM_dd_yy_hh_mm") + ".xlsx";
    System.out.println(path);
    DateFormat df = new SimpleDateFormat(Constants.DATE.DEFAULT_FORMAT);

    for(ViewWeldSleeveBO item : data) {
      Row row = sheet.createRow(rowNum++);

      Cell cell0 = row.createCell(0);
      cell0.setCellValue(index++);
      cell0.setCellStyle(ExcelStyleUtil.getDateCellStyle((XSSFWorkbook) workbook));

      Cell cell1 = row.createCell(1);
      cell1.setCellValue(item.getSleeveCode() != null ? item.getSleeveCode() : "");
      cell1.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell2 = row.createCell(2);
      cell2.setCellValue(item.getSourceCableCode() != null ? item.getSourceCableCode() : "");
      cell2.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell3 = row.createCell(3);
      cell3.setCellValue(item.getSourceLineNo().toString());
      cell3.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell4 = row.createCell(4);
      cell4.setCellValue(item.getDestCableCode() != null ? item.getDestCableCode() : "");
      cell4.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell5 = row.createCell(5);
      cell5.setCellValue(item.getDestLineNo().toString());
      cell5.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));

      Cell cell6 = row.createCell(6);
      cell6.setCellValue(item.getCreateUser() != null ? item.getCreateUser() : "");
      cell6.setCellStyle(ExcelStyleUtil.getStringCellStyle((XSSFWorkbook) workbook));

      Cell cell7 = row.createCell(7);
      cell7.setCellValue( item.getAttenuation() != null ?  String.format("%.2f", item.getAttenuation()) : "");
      cell7.setCellStyle(ExcelStyleUtil.getTextNumberCellStyle((XSSFWorkbook) workbook));
    }

    try {
//     Write file
      FileOutputStream outputStream = new FileOutputStream(path);
      workbook.write(outputStream);
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
//      logger.error(e.getMessage());
    }

    return path;
  }

//  @Override
  public CntCableLaneBO getLaneId(Long cableId) {
    return null;
  }

}
