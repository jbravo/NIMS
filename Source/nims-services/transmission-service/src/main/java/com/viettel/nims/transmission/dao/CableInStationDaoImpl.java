package com.viettel.nims.transmission.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.model.CableInStationBO;
import com.viettel.nims.transmission.model.CatOpticalCableTypeBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.WeldingOdfToOdfBO;
import com.viettel.nims.transmission.model.view.ViewCableInStationBO;
import com.viettel.nims.transmission.model.view.ViewInfraOdfBO;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by HieuDT on 08/26/2019.
 */
@Slf4j
@Repository
public class CableInStationDaoImpl implements CableInStationDao {
	@Autowired
	private EntityManager entityManager;

	@Override
	public FormResult findBasicCable(CableInStationBO cableInStationBO) {
		StringBuilder sqlCount = new StringBuilder("Select Count(cis.stationId) ");
		StringBuilder sql = new StringBuilder("from ");
		sql.append(ViewCableInStationBO.class.getName());
		sql.append(" cis where 1=1");
		if (StringUtils.isNotEmpty(cableInStationBO.getBasicInfo().trim())) {
			sql.append(" AND cis.cableCode LIKE :basicInfo");
		}
		sql.append(" order by cis.createTime desc");
		sqlCount.append(sql.toString());
		// tim kiem co ban
		Query query = entityManager.createQuery(sql.toString(), ViewCableInStationBO.class);
		// lay tong so ban ghi
		Query queryCount = entityManager.createQuery(sqlCount.toString());
		if (StringUtils.isNotEmpty(cableInStationBO.getBasicInfo().trim())) {
			query.setParameter("basicInfo", CommonUtils.getLikeCondition(cableInStationBO.getBasicInfo()));
			queryCount.setParameter("basicInfo", CommonUtils.getLikeCondition(cableInStationBO.getBasicInfo()));
		}
		FormResult result = new FormResult();
		// Set tong so ban ghi
		result.setTotalRecords((Long) queryCount.getSingleResult());
		// So ban ghi tren 1 trang
		query.setMaxResults(cableInStationBO.getRows());
		// bat dau tu ban ghi so
		query.setFirstResult(cableInStationBO.getFirst());
		List<ViewCableInStationBO> resultList = query.getResultList();
		result.setListData(resultList);
		return result;
	}

	@Override
	public FormResult findAdvanceCable(CableInStationBO cableInStationBO, Long userId) {
		try {
			FormResult result = new FormResult();
			StringBuilder sqlCount = new StringBuilder("Select Count(cis.stationId) ");
			StringBuilder sql = new StringBuilder("from ");
			sql.append(ViewCableInStationBO.class.getName());
			sql.append(" cis where 1=1");
			sql.append(" and cis.rowStatus = 1 ");
			HashMap<String, Object> params = new HashMap<>();
			sql.append(" and cis.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
			params.put("userId", userId);
			if (cableInStationBO.getBasicInfo() != null
					&& StringUtils.isNotEmpty(cableInStationBO.getBasicInfo().trim())) {
				sql.append(" AND cis.cableCode LIKE :basicInfo");
				params.put("basicInfo", CommonUtils.getLikeCondition(cableInStationBO.getBasicInfo()));
			} else {
				if (cableInStationBO.getCableCode() != null && StringUtils.isNotEmpty(cableInStationBO.getCableCode().trim())) {
					sql.append(" AND cis.cableCode LIKE :CABLE_CODE");
					params.put("CABLE_CODE", CommonUtils.getLikeCondition(cableInStationBO.getCableCode()));
				}
				if (cableInStationBO.getDeptName() != null
						&& StringUtils.isNotEmpty(cableInStationBO.getDeptName().trim())) {
					sql.append(" AND cis.deptName LIKE :DEPT_NAME");
					params.put("DEPT_NAME", CommonUtils.getLikeCondition(cableInStationBO.getDeptName()));
				}
				if (cableInStationBO.getDeptId() != null) {
					sql.append(" and cis.deptId = :deptId");
					params.put("deptId", cableInStationBO.getDeptId());
				}
				if (cableInStationBO.getSourceId() != null) {
					sql.append(" AND cis.sourceId LIKE :SOURCE_ID");
					params.put("SOURCE_ID", cableInStationBO.getSourceId());
				}

				if (cableInStationBO.getDestId() != null) {
					sql.append(" AND cis.destId LIKE :DEST_ID");
					params.put("DEST_ID", cableInStationBO.getDestId());
				}
//				if (cableInStationBO.getStationCode() != null
//						&& StringUtils.isNotEmpty(cableInStationBO.getStationCode().trim())) {
//					sql.append(" AND cis.stationCode LIKE :STATION_CODE");
//					params.put("STATION_CODE", CommonUtils.getLikeCondition(cableInStationBO.getStationCode()));
//				}
				if (cableInStationBO.getStationId() != null) {
					sql.append(" AND cis.stationId = :STATION_ID");
					params.put("STATION_ID", cableInStationBO.getStationId());
				}
//				if (cableInStationBO.getCableTypeCode() != null
//						&& StringUtils.isNotEmpty(cableInStationBO.getCableTypeCode().trim())) {
//					sql.append(" AND cis.cableTypeCode LIKE :CABLE_TYPE_CODE");
//					params.put("CABLE_TYPE_CODE", CommonUtils.getLikeCondition(cableInStationBO.getCableTypeCode()));
//				}
				if (cableInStationBO.getCableTypeId() != null) {
					sql.append(" AND cis.cableTypeId = :CABLE_TYPE_ID");
					params.put("CABLE_TYPE_ID", cableInStationBO.getCableTypeId());
				}

			}

			if (cableInStationBO.getCableCodeTable() != null && StringUtils.isNotEmpty(cableInStationBO.getCableCodeTable().trim())) {
				sql.append(" AND cis.cableCode LIKE :CABLE_CODE_Table");
				params.put("CABLE_CODE_Table", CommonUtils.getLikeCondition(cableInStationBO.getCableCodeTable()));
			}
			if (cableInStationBO.getDeptNameTable() != null && StringUtils.isNotEmpty(cableInStationBO.getDeptNameTable().trim())) {
				sql.append(" AND cis.deptName LIKE :deptNameTable");
				params.put("deptNameTable", CommonUtils.getLikeCondition(cableInStationBO.getDeptNameTable()));
			}
			if (cableInStationBO.getDeptIdTable() != null) {
				sql.append(" and cis.deptId = :deptIdTable");
				params.put("deptIdTable", cableInStationBO.getDeptIdTable());
			}
			if (cableInStationBO.getSourceIdTable() != null && cableInStationBO.getSourceId() == null) {
				sql.append(" AND cis.sourceId LIKE :SOURCE_ID_Table");
				params.put("SOURCE_ID_Table", cableInStationBO.getSourceIdTable());
			}

			if (cableInStationBO.getDestIdTable() != null && cableInStationBO.getDestId() == null) {
				sql.append(" AND cis.destId LIKE :DEST_ID_Table");
				params.put("DEST_ID_Table", cableInStationBO.getDestIdTable());
			}
			if (cableInStationBO.getStationCodeTable() != null
					&& StringUtils.isNotEmpty(cableInStationBO.getStationCodeTable().trim())) {
				sql.append(" AND cis.stationCode LIKE :STATION_CODE_Table");
				params.put("STATION_CODE_Table", CommonUtils.getLikeCondition(cableInStationBO.getStationCodeTable()));
			}
			if (cableInStationBO.getStationIdTable() != null) {
				sql.append(" AND cis.stationId = :STATION_ID_TABLE");
				params.put("STATION_ID_TABLE", cableInStationBO.getStationIdTable());
			}
			if (cableInStationBO.getCableTypeCodeTable() != null && StringUtils.isNotEmpty(cableInStationBO.getCableTypeCodeTable().trim())
					&& (cableInStationBO.getCableTypeCode() == null || StringUtils.isEmpty(cableInStationBO.getCableTypeCode().trim() ))) {
				sql.append(" AND cis.cableTypeCode LIKE :CABLE_TYPE_CODE_Table");
				params.put("CABLE_TYPE_CODE_Table", CommonUtils.getLikeCondition(cableInStationBO.getCableTypeCodeTable()));
			}
			if (cableInStationBO.getCableTypeIdTable() != null && cableInStationBO.getCableTypeId() == null 
					&& (cableInStationBO.getCableTypeCode() == null || StringUtils.isEmpty(cableInStationBO.getCableTypeCode().trim()) )) {
				sql.append(" AND cis.cableTypeId = :CABLE_TYPE_ID_Table");
				params.put("CABLE_TYPE_ID_Table", cableInStationBO.getCableTypeIdTable());
			}

			
			
			if (cableInStationBO.getLengthStr() != null) {
				sql.append(" AND CONVERT(cis.length, CHAR(150)) LIKE :LENGTH");
				// if(cableInStationBO.getLength().toString().endsWith(".0")) {
				// params.put("LENGTH",
				// CommonUtils.getLikeCondition(cableInStationBO.getLengStr().replace(".0",
				// "")));
				// }else {
				params.put("LENGTH", CommonUtils.getLikeCondition(cableInStationBO.getLengthStr()));
				// }

			}
			if (cableInStationBO.getInstallationDate() != null) {
				sql.append(" AND cis.installationDate  =:INSTALLATION_DATE");
				params.put("INSTALLATION_DATE", cableInStationBO.getInstallationDate());
			}
			if (cableInStationBO.getNote() != null && StringUtils.isNotEmpty(cableInStationBO.getNote().trim())) {
				sql.append(" AND cis.note LIKE :NOTE");
				params.put("NOTE", CommonUtils.getLikeCondition(cableInStationBO.getNote()));
			}
			if (cableInStationBO.getStatus() != null) {
				sql.append(" and cis.status =:status");
				params.put("status", cableInStationBO.getStatus());
			}

			if (cableInStationBO.getSortField() != null && cableInStationBO.getSortOrder() != null) {
				if (cableInStationBO.getSortOrder() == 1) {
					sql.append(" order by cis." + cableInStationBO.getSortField() + "  desc");
				} else {
					sql.append(" order by cis." + cableInStationBO.getSortField() + "  asc");
				}
			} else {
				sql.append(" order by cis.cableCode asc");
			}

			sqlCount.append(sql.toString());
			// tim kiem nang cao
			Query query = entityManager.createQuery(sql.toString(), ViewCableInStationBO.class);
			// lay tong so ban ghi
			Query queryCount = entityManager.createQuery(sqlCount.toString());
			if (params.size() > 0) {
				for (Map.Entry<String, Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
					queryCount.setParameter(param.getKey(), param.getValue());
				}
			}
			// Set tong so ban ghi
			result.setTotalRecords((Long) queryCount.getSingleResult());
			if (cableInStationBO.getRows() != null) {
				// So ban ghi tren 1 trang
				query.setMaxResults(cableInStationBO.getRows());
			}
			if (cableInStationBO.getFirst() != null) {
				// bat dau tu ban ghi so
				query.setFirstResult(cableInStationBO.getFirst());
			}
			List<ViewCableInStationBO> resultList = query.getResultList();
			result.setListData(resultList);
			return result;
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return null;
	}

	@Override
	// Them cap
	public void saveCable(CableInStationBO cableInStationBO) {
		try {
			Session session = entityManager.unwrap(Session.class);
			if (cableInStationBO.getCableId() != null) {
				cableInStationBO.setUpdateTime(new Date());
			} else

			if (cableInStationBO.getCableId() == null) {
				// Long cableId = (Long) session.save(cableInStationBO);
				// cableInStationBO.setCableId(cableId);
				cableInStationBO.setCreateTime(new Date());
				cableInStationBO.setRowStatus(1L);
			}
			session.saveOrUpdate(cableInStationBO);
		} catch (Exception e) {
			log.error("Exception", e);
		}
	}

	@Override
	public ViewCableInStationBO findCableById(Long id) {
		ViewCableInStationBO viewCableInStationBO = entityManager.find(ViewCableInStationBO.class, id);
		return viewCableInStationBO;
	}

	@Override
	// Xoa cap trong nha tram
	public int delete(Long id) {
		Session session = entityManager.unwrap(Session.class);
		CableInStationBO cableInStationBO = entityManager.find(CableInStationBO.class, id);
		if (cableInStationBO != null && cableInStationBO.getRowStatus() == 1) {
			cableInStationBO.setRowStatus(2L);
			session.save(cableInStationBO);
			return 1;
		}
		return 0;
	}

	@Override
	// List loai cap
	public FormResult findListCableType() {
		StringBuilder sql = new StringBuilder("select p.cableTypeId,p.cableTypeCode from ");
		sql.append(CatOpticalCableTypeBO.class.getName());
		sql.append(" p where 1=1");
		sql.append(" and p.rowStatus = 1 ");
		FormResult result = new FormResult();
		try {
			Query query = entityManager.createQuery(sql.toString());
			List<ViewCableInStationBO> resultList = query.getResultList();
			result.setListData(resultList);
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return result;
	}

	@Override
	// List ODF dau
	public FormResult findListODFFist(Long stationId, Long userId) {
		StringBuilder sql = new StringBuilder("select p.odfId,p.odfCode from ");
		sql.append(ViewInfraOdfBO.class.getName());
		sql.append(" p where 1=1");
		sql.append(" and p.rowStatus = 1 ");
		// HashMap<String, Object> params = new HashMap<>();
		sql.append(" and p.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
		// params.put("userId", userId);
		FormResult result = new FormResult();
		if (stationId != null) {
			sql.append(" and p.stationId = :stationId");
			try {
				Query query = entityManager.createQuery(sql.toString()).setParameter("stationId", stationId)
						.setParameter("userId", userId);
				List<ViewInfraOdfBO> resultList = query.getResultList();
				result.setListData(resultList);
			} catch (Exception e) {
				log.error("Exception", e);
			}
			return result;
		} else {
			try {
				Query query = entityManager.createQuery(sql.toString()).setParameter("userId", userId);
				List<ViewInfraOdfBO> resultList = query.getResultList();
				result.setListData(resultList);
			} catch (Exception e) {
				log.error("Exception", e);
			}
			return result;
		}

	}

	@Override
	// List ODF cuoi
	public FormResult findListODFEnd(Long stationId, Long userId) {
		StringBuilder sql = new StringBuilder("select p.odfId,p.odfCode from ");
		sql.append(ViewInfraOdfBO.class.getName());
		sql.append(" p where 1=1");
		sql.append(" and p.rowStatus = 1 ");
		HashMap<String, Object> params = new HashMap<>();
		sql.append(" and p.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
		params.put("userId", userId);
		FormResult result = new FormResult();
		if (stationId != null) {
			sql.append(" and p.stationId = :stationId");
			try {
				Query query = entityManager.createQuery(sql.toString()).setParameter("stationId", stationId)
						.setParameter("userId", userId);
				List<ViewInfraOdfBO> resultList = query.getResultList();
				result.setListData(resultList);
			} catch (Exception e) {
				log.error("Exception", e);
			}
			return result;
		} else {
			try {
				Query query = entityManager.createQuery(sql.toString()).setParameter("userId", userId);
				List<ViewInfraOdfBO> resultList = query.getResultList();
				result.setListData(resultList);
			} catch (Exception e) {
				log.error("Exception", e);
			}
			return result;
		}
	}

	@Override
	// get stationcode
	public FormResult getDataFormSearchAdvance(InfraStationsBO infraStationsBO) {
		StringBuilder sql = new StringBuilder("from ");
		sql.append(InfraStationsBO.class.getName());
		sql.append(" ifs where 1=1 ");
		sql.append(" and ifs.rowStatus = 1 ");
		HashMap<String, Object> params = new HashMap<>();
		if (infraStationsBO.getStationCode() != null
				&& StringUtils.isNotEmpty(infraStationsBO.getStationCode().trim())) {
			sql.append(" and ifs.stationCode LIKE :stationCode");
			params.put("stationCode", CommonUtils.getLikeCondition(infraStationsBO.getStationCode()));
		}
		if (infraStationsBO.getStationId() != null) {
			sql.append(" and ifs.stationId = :stationId");
			params.put("stationId", infraStationsBO.getStationId());
		}
		Query query = entityManager.createQuery(sql.toString(), InfraStationsBO.class);
		if (params.size() > 0) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		List<InfraStationsBO> resultList = query.getResultList();
		FormResult result = new FormResult();
		result.setListData(resultList);
		return result;
	}

	@Override
	// Chi so doan cap
	public String getCableIndex(Long sourceId, Long destId) {
		StringBuilder sqlMax = new StringBuilder("Select Max(st.cableIndex) ");
		StringBuilder sql = new StringBuilder("from ");
		sql.append(ViewCableInStationBO.class.getName());
		sql.append(" st where 1=1");
		sql.append(" and st.rowStatus = 1 ");
		sql.append(" and st.sourceId = '" + sourceId + "'");
		sql.append(" and st.destId = '" + destId + "'");
		sqlMax.append(sql.toString());
		Query query = entityManager.createQuery(sqlMax.toString(), Integer.class);
		Integer result = (Integer) query.getSingleResult();
		if (result == null)
			result = 1;
		else {
			result += 1;
		}
		return formatCableIndex(result);
	}

	public static String formatCableIndex(Integer cableIndex) {
		String s = "";
		if (cableIndex < 10)
			s = "0" + cableIndex;
		else if (cableIndex < 101)
			s = "" + cableIndex;
		else
			s = "";
		return s;
	}

	@Override
	// Check ton tai ma doan cap
	public Boolean isExitByCode(CableInStationBO cableInStationBO) {
		try {
			HashMap<String, Object> params = new HashMap<>();
			StringBuilder sql = new StringBuilder("from ");
			sql.append(CableInStationBO.class.getName());
			sql.append(" cd where 1=1 ");
			sql.append(" and cd.cableCode = :cableCode ");
			params.put("cableCode", cableInStationBO.getCableCode());
			sql.append(" and cd.rowStatus = 1 ");
			Query query = entityManager.createQuery(sql.toString(), CableInStationBO.class);
			if (params.size() > 0) {
				for (Map.Entry<String, Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}
			List<CableInStationBO> resultList = query.getResultList();
			if (resultList.size() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Exception", e);
		}
		return null;
	}

	@Override
	// Check ODF duoc han noi
	public Boolean checkWeldODFByCable(CableInStationBO cableInStationBO) {
		List<CableInStationBO> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(WeldingOdfToOdfBO.class.getName());
		sql.append(" b WHERE b.odfId = :sourceId ");
		sql.append(" and b.destOdfId = :destId ");
		Query query = entityManager.createQuery(sql.toString(), WeldingOdfToOdfBO.class)
				.setParameter("sourceId", cableInStationBO.getSourceId())
				.setParameter("destId", cableInStationBO.getDestId());
		try {
			result = query.getResultList();
			if (result.size() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return null;
	}

	@Override
	public CatOpticalCableTypeBO findCableTypeByCode(String code) {
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(CatOpticalCableTypeBO.class.getName());
		sql.append(" p where 1=1");
		sql.append(" and p.rowStatus = 1 and p.cableTypeCode = :cableTypeCode ");
		try {
			Query query = entityManager.createQuery(sql.toString(), CatOpticalCableTypeBO.class)
					.setParameter("cableTypeCode", code);
			List<CatOpticalCableTypeBO> resultList = query.getResultList();
			if (resultList.size() > 0) {
				return resultList.get(0);
			}
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return null;
	}

	@Override
	public CableInStationBO findCableByCode(String code) {
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(CableInStationBO.class.getName());
		sql.append(" p where 1=1");
		sql.append(" and p.rowStatus = 1 and p.cableCode = :cableCode ");
		try {
			Query query = entityManager.createQuery(sql.toString(), CableInStationBO.class).setParameter("cableCode",
					code);
			List<CableInStationBO> resultList = query.getResultList();
			if (resultList.size() > 0) {
				return resultList.get(0);
			}
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return null;
	}

	@Override
	public FormResult getAllODF(Long stationId, Long userId) {
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(ViewInfraOdfBO.class.getName());
		sql.append(" p where 1=1");
		sql.append(" and p.rowStatus = 1 ");
		sql.append(" and p.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
		FormResult result = new FormResult();
		if (stationId != null) {
			sql.append(" and p.stationId = :stationId");
			try {
				Query query = entityManager.createQuery(sql.toString(), ViewInfraOdfBO.class)
						.setParameter("stationId", stationId).setParameter("userId", userId);
				List<ViewInfraOdfBO> resultList = query.getResultList();
				result.setListData(resultList);
			} catch (Exception e) {
				log.error("Exception", e);
			}
			return result;
		} else {
			try {
				Query query = entityManager.createQuery(sql.toString(), ViewInfraOdfBO.class).setParameter("userId",
						userId);
				List<ViewInfraOdfBO> resultList = query.getResultList();
				result.setListData(resultList);
			} catch (Exception e) {
				log.error("Exception", e);
			}
			return result;
		}
	}

}
