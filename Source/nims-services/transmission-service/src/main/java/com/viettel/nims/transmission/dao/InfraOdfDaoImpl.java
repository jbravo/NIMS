package com.viettel.nims.transmission.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;


//import com.viettel.nims.transmission.model.dto.DMOdfDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.model.CntCouplerToCouplerBO;
import com.viettel.nims.transmission.model.CntCouplerToLineBO;
import com.viettel.nims.transmission.model.InfraCableLineBO;
import com.viettel.nims.transmission.model.InfraCouplerBO;
import com.viettel.nims.transmission.model.InfraOdfBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewInfraOdfBO;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewODFCableBO;
import com.viettel.nims.transmission.model.view.ViewOdfLaneBO;
import com.viettel.nims.transmission.utils.Constains;

/**
 * InfraOdfDaoImpl Version 1.0 Date: 08-23-2019 Copyright Modification Logs:
 * DATE AUTHOR DESCRIPTION
 * -----------------------------------------------------------------------
 * 08-23-2019 HungNV Create
 */
@Slf4j
@Repository
public class InfraOdfDaoImpl implements InfraOdfDao {

// ------------DI----------------------

	@Autowired
	private EntityManager entityManager;

// ------------Properties--------------

// ------------------------------------

	/**
	 * method find advance list information odf
	 *
	 * @param infraOdfBO
	 * @return list information odf
	 * @author hungnv
	 * @date 23/8/2019
	 */
	@Override
	public FormResult findAdvanceOdf(ViewInfraOdfBO infraOdfBO, Long userId) {
		FormResult result = new FormResult();
		HashMap<String, Object> params = new HashMap<>();
		StringBuilder sqlCount = new StringBuilder("SELECT COUNT(odf.odfId) ");
		StringBuilder sql = new StringBuilder("FROM ");

		sql.append(ViewInfraOdfBO.class.getName());
		sql.append(" odf WHERE 1=1 AND odf.rowStatus = 1 ");
		sql.append(" and odf.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
		params.put("userId",userId);
		// get data for Odf code
		if (infraOdfBO.getOdfCode() != null && StringUtils.isNotEmpty(infraOdfBO.getOdfCode().trim())) {
			sql.append(" AND odf.odfCode LIKE :ODF_CODE");
			params.put("ODF_CODE", CommonUtils.getLikeCondition(infraOdfBO.getOdfCode()));
		}

		// get data for station code
		if (infraOdfBO.getStationCode() != null && StringUtils.isNotEmpty(infraOdfBO.getStationCode().trim())) {
			sql.append(" AND odf.stationCode LIKE :STATION_CODE");
			params.put("STATION_CODE", CommonUtils.getLikeCondition(infraOdfBO.getStationCode()));
		}

		// get data for station code
		if (infraOdfBO.getStationId() != null) {
			sql.append(" AND odf.stationId = :STATION_ID");
			params.put("STATION_ID", infraOdfBO.getStationId());
		}

		// get data for station code
		if (infraOdfBO.getDeptName() != null && StringUtils.isNotEmpty(infraOdfBO.getDeptName().trim())) {
			sql.append(" AND odf.deptName LIKE :DEPT_NAME");
			params.put("DEPT_NAME", CommonUtils.getLikeCondition(infraOdfBO.getDeptName()));
		}

		// get data for type Odf
		if (infraOdfBO.getOdfTypeCode() != null && StringUtils.isNotEmpty(infraOdfBO.getOdfTypeCode().trim())) {
			sql.append(" AND odf.odfTypeCode LIKE :ODF_TYPE_CODE");
			params.put("ODF_TYPE_CODE", CommonUtils.getLikeCondition(infraOdfBO.getOdfTypeCode()));
		}

		// get data for department
		if (infraOdfBO.getDeptPath() != null && StringUtils.isNotEmpty(infraOdfBO.getDeptPath().trim())) {
			sql.append(" AND odf.deptPath LIKE :DEPT_PATH");
			params.put("DEPT_PATH", CommonUtils.getLikeCondition(infraOdfBO.getDeptPath()));
		}

		// get data for owner name
		if (infraOdfBO.getOwnerName() != null && StringUtils.isNotEmpty(infraOdfBO.getOwnerName().trim())) {
			sql.append(" AND odf.ownerName LIKE :OWNER_NAME");
			params.put("OWNER_NAME", CommonUtils.getLikeCondition(infraOdfBO.getOwnerName()));
		}

		// get data for owner name
		if (infraOdfBO.getVendorName() != null && StringUtils.isNotEmpty(infraOdfBO.getVendorName().trim())) {
			sql.append(" AND odf.vendorName LIKE :VENDOR_NAME");
			params.put("VENDOR_NAME", CommonUtils.getLikeCondition(infraOdfBO.getVendorName()));
		}

		// get data for install date
		if (infraOdfBO.getInstallationDate() != null) {
			sql.append(" AND odf.installationDate LIKE :INSTALL_DATE");
			params.put("INSTALL_DATE", infraOdfBO.getInstallationDate());
		}

		// TODO

		// get data for note
		if (infraOdfBO.getNote() != null && StringUtils.isNotEmpty(infraOdfBO.getNote().trim())) {
			sql.append(" AND odf.note LIKE :NOTE");
			params.put("NOTE", CommonUtils.getLikeCondition(infraOdfBO.getNote()));
		}

		if (infraOdfBO.getSortField() != null && infraOdfBO.getSortOrder() != null) {
			if (infraOdfBO.getSortOrder() == 1) {
				sql.append(" order BY odf." + infraOdfBO.getSortField() + "	DESC");
			} else {
				sql.append(" order BY odf." + infraOdfBO.getSortField() + "	ASC");
			}
		} else {
			sql.append(" ORDER BY odf.odfCode");
		}
		sqlCount.append(sql.toString());
		try {
			// Query Advance
			Query query = entityManager.createQuery(sql.toString(), ViewInfraOdfBO.class);
			// Count record
			Query queryCount = entityManager.createQuery(sqlCount.toString());
			if (params.size() > 0) {
				for (Map.Entry<String, Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
					queryCount.setParameter(param.getKey(), param.getValue());
				}
			}

			// Set count record
			result.setTotalRecords((Long) queryCount.getSingleResult());

			// Set Count record per one page
      if (!infraOdfBO.getRows().equals(Constains.NUMBER_MINUS_ONE)){
        query.setMaxResults(infraOdfBO.getRows());
      }
			query.setFirstResult(infraOdfBO.getFirst());

			List<ViewInfraStationsBO> resultList = query.getResultList();
			result.setListData(resultList);
			return result;
		} catch (Exception e) {
			log.error("Exception", e);
		}

		return null;
	}

	/**
	 * method get data form search advance
	 *
	 * @return form result
	 * @author hungnv
	 * @date 26/8/2019
	 */
	@Override
	public FormResult getDataFormSearchAdvance() {
		FormResult result = new FormResult();
		StringBuilder sqlSelect = new StringBuilder(" FROM ");
		sqlSelect.append(ViewInfraOdfBO.class.getName());
		sqlSelect.append(" odf WHERE 1=1");

		Query query = entityManager.createQuery(sqlSelect.toString(), ViewInfraOdfBO.class);

		List<ViewInfraOdfBO> resultList = query.getResultList();
		result.setListData(resultList);
		return result;
	}

	/**
	 * method delete odf
	 *
	 * @param odfId
	 * @return int
	 * @author hungnv
	 * @date 28/8/2019
	 */
	@Override
	public int deleteOdf(Long odfId) {
		Session session = entityManager.unwrap(Session.class);

		InfraOdfBO infraOdfBO = entityManager.find(InfraOdfBO.class, odfId);
		if (infraOdfBO != null && infraOdfBO.getRowStatus().equals(Constains.NUMBER_ONE)) {
			infraOdfBO.setRowStatus(Constains.NUMBER_TWO);
			session.save(infraOdfBO);
			return Constains.NUMBER_ONE;
		}
		return Constains.NUMBER_ZERO;
	}

	/**
	 * method get ref coupler to line if return 0 then have NOT reference table
	 * CNT_COUPLER_TO_LINE , return1 then have reference table CNT_COUPLER_TO_LINE
	 *
	 * @param odfId
	 * @return String
	 * @author hungnv
	 * @date 28/8/2019
	 */
	@Override
	public String countRecordRefCouplerToLine(Long odfId) {
		String sumRecordRef = null;
		StringBuilder sqlCount = new StringBuilder("SELECT COUNT(odf.odfId) ");
		sqlCount.append("FROM ");

		StringBuilder nestSql = new StringBuilder("SELECT 1 FROM ");

		sqlCount.append(InfraOdfBO.class.getName());
		sqlCount.append(" AS odf WHERE EXISTS ( ");

		nestSql.append(CntCouplerToLineBO.class.getName());
		nestSql.append(" AS cntctl WHERE odf.odfId = cntctl.odfId ");

		sqlCount.append(nestSql.toString());
		sqlCount.append(") AND odf.odfId = :odfId ");

		Query queryCount = entityManager.createQuery(sqlCount.toString()).setParameter("odfId", odfId);
		List<Object> result = queryCount.getResultList();

		for (Object countRef : result) {
			sumRecordRef = countRef.toString();
			break;
		}
		return sumRecordRef;
	}

	/**
	 * method get ref coupler to coupler if return 0 then have NOT reference table
	 * CNT_COUPLER_TO_COUPLER , return 1 then have reference table
	 * CNT_COUPLER_TO_COUPLER
	 *
	 * @param odfId
	 * @return String
	 * @author hungnv
	 * @date 28/8/2019
	 */
	@Override
	public String countRecordRefCouplerToCoupler(Long odfId) {
		String sumRecordRef = null;
		StringBuilder sqlCount = new StringBuilder("SELECT COUNT(odf.odfId) ");
		sqlCount.append("FROM ");

		StringBuilder nestSql = new StringBuilder("SELECT 1 FROM ");

		sqlCount.append(InfraOdfBO.class.getName());
		sqlCount.append(" AS odf WHERE EXISTS ( ");
		nestSql.append(CntCouplerToCouplerBO.class.getName());
		nestSql.append(" AS cntc2c WHERE odf.odfId = cntc2c.sourceOdfId OR odf.odfId = cntc2c.destOdfId ");
		sqlCount.append(nestSql.toString());
		sqlCount.append(") AND odf.odfId = :odfId ");

		Query queryCount = entityManager.createQuery(sqlCount.toString()).setParameter("odfId", odfId);
		List<Object> result = queryCount.getResultList();

		for (Object countRef : result) {
			sumRecordRef = countRef.toString();
			break;
		}
		return sumRecordRef;
	}

	/**
	 * method add or update odf
	 *
	 * @param infraOdfBO
	 * @author dungph
	 * @date 27/8/2019
	 */
	@Override
	public Long saveOrUpdateOdf(InfraOdfBO infraOdfBO) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(infraOdfBO);
		return infraOdfBO.getOdfId();
	}

	/**
	 * method find odf by station id
	 *
	 * @param id
	 * @return InfraOdfBO
	 * @author toanld
	 * @date 27/8/2019
	 */
	@Override
	public List<ViewInfraOdfBO> findOdfByStationId(Long id) {
		List<ViewInfraOdfBO> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder("FROM ");
		sql.append(ViewInfraOdfBO.class.getName());
		sql.append(" io WHERE 1=1 ");
		sql.append(" AND io.stationId = :stationId");

		Query query = entityManager.createQuery(sql.toString(), ViewInfraOdfBO.class).setParameter("stationId", id);
		try {
			result = query.getResultList();
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return result;
	}

	/**
	 * method find odf by odf id
	 *
	 * @param id
	 * @return InfraOdfBO
	 * @author dungph
	 * @date 27/8/2019
	 */
	@Override
	public ViewInfraOdfBO findOdfById(Long id) {
		return entityManager.find(ViewInfraOdfBO.class, id);
	}

	/**
	 * method get max odf id
	 *
	 * @return long
	 * @author dungph
	 * @date 27/8/2019
	 */
	@Override
	public Object getMaxOdfId() {
		StringBuilder sqlSelect = new StringBuilder("SELECT MAX(odf.odfId) FROM ");
		sqlSelect.append(InfraOdfBO.class.getName());
		sqlSelect.append(" odf WHERE 1=1 ");
		Query query = entityManager.createQuery(sqlSelect.toString());
		Object maxOdfId = query.getSingleResult();
		return maxOdfId;
	}

	/**
	 * method get all odf
	 *
	 * @return List<ViewInfraOdfBO>
	 * @author dungph
	 * @date 27/8/2019
	 */
	@Override
	public List<ViewInfraOdfBO> getAllOdf(Long stationId, Long userId) {
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(ViewInfraOdfBO.class.getName());
		sql.append(" p where 1=1");
		sql.append(" and p.rowStatus = 1 ");
		sql.append(" and p.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
		List<ViewInfraOdfBO> result = new ArrayList<ViewInfraOdfBO>();
		if (stationId != null) {
			sql.append(" and p.stationId = :stationId");
			try {
				Query query = entityManager.createQuery(sql.toString(), ViewInfraOdfBO.class).setParameter("stationId", stationId).setParameter("userId", userId);
				List<ViewInfraOdfBO> resultList = query.getResultList();
				return resultList;
			} catch (Exception e) {
				log.error("Exception", e);
			}
			return  result;
		} else {
			try {
				Query query = entityManager.createQuery(sql.toString(), ViewInfraOdfBO.class).setParameter("userId", userId);
				List<ViewInfraOdfBO> resultList = query.getResultList();
				return resultList;
			} catch (Exception e) {
				log.error("Exception", e);
			}
			return result;
		}
	}

  public List getStationByOdfCodes(String odfCode, String destOdfCode) {
	  Query query = entityManager.createQuery("SELECT dodf.stationId from " + InfraOdfBO.class.getName()
        + " dodf where 1=1 and dodf.odfCode = :DEST_ODF_CODE and dodf.stationId in (SELECT odf.stationId from "
        + InfraOdfBO.class.getName() + " odf where 1=1 and odf.odfCode = :ODF_CODE) ");
	  query.setParameter("ODF_CODE", odfCode);
    query.setParameter("DEST_ODF_CODE", destOdfCode);
    List data = query.getResultList();
    return data;
  }

	/**
	 * method get all stations
	 *
	 * @return FormResult
	 * @author dungph
	 * @date 27/8/2019
	 */
	@Override
	public List<InfraStationsBO> getAllStations() {
		StringBuilder sqlSelect = new StringBuilder(" FROM ");
		sqlSelect.append(InfraStationsBO.class.getName());
		sqlSelect.append(" WHERE 1=1 ");
		Query query = entityManager.createQuery(sqlSelect.toString());
		List<InfraStationsBO> infraStationsBOList = query.getResultList();
		return infraStationsBOList;
	}

	/**
	 * method get coupler no
	 *
	 * @return listCoupler
	 * @author hungnv
	 * @date 10/09/2019
	 */
	@Override
	public ArrayList<CntCouplerToCouplerBO> getCouplerNoRef(Long odfId) {
		ArrayList<CntCouplerToCouplerBO> listCoupler = new ArrayList<CntCouplerToCouplerBO>();

		StringBuilder sql = new StringBuilder(" FROM ");
		sql.append(CntCouplerToCouplerBO.class.getName());
		sql.append(" AS c2c WHERE c2c.sourceOdfId = :odfId ");
		sql.append(" OR ");
		sql.append(" c2c.destOdfId = :odfId ");

		Query query = entityManager.createQuery(sql.toString()).setParameter("odfId", odfId);
		listCoupler = (ArrayList<CntCouplerToCouplerBO>) query.getResultList();

		return listCoupler;
	}

	/**
	 * method get cable code
	 *
	 * @return cable code
	 * @author hungnv
	 * @date 10/09/2019
	 */
	@Override
	public ArrayList<ViewODFCableBO> getCableCodeRef(Long odfId) {
		ArrayList<ViewODFCableBO> cableList = new ArrayList<ViewODFCableBO>();
		StringBuilder sql = new StringBuilder(" FROM ");
		sql.append(ViewODFCableBO.class.getName());
		sql.append(" AS vocb WHERE vocb.odfId = :odfId ");

		Query query = entityManager.createQuery(sql.toString()).setParameter("odfId", odfId);
		cableList = (ArrayList<ViewODFCableBO>) query.getResultList();
		System.out.println(cableList.size());
		return cableList;
	}

	/**
	 * method get all stations
	 *
	 * @param stationId
	 * @return Integer
	 * @author dungph
	 * @date 5/9/2019
	 */
	@Override
	public List<ViewInfraOdfBO> getMaxOdfIndexByStationId(Long stationId) {
		StringBuilder sqlSelect = new StringBuilder(" FROM ");
		sqlSelect.append(ViewInfraOdfBO.class.getName());
		sqlSelect.append(" WHERE rowStatus = 1 and stationId = :STATION_ID ");
		Query query = entityManager.createQuery(sqlSelect.toString()).setParameter("STATION_ID", stationId);
		Object result = query.getResultList();
		return (List<ViewInfraOdfBO>) result;
	}

	/**
	 * method get odf code by odf id
	 *
	 * @param odfId
	 * @return String
	 * @author hungnv
	 * @date 13/9/2019
	 */
	@Override
	public String getOdfCodeById(Long odfId) {
		StringBuilder sql = new StringBuilder("SELECT odfCode FROM ");
		sql.append(InfraOdfBO.class.getName());
		sql.append(" WHERE odfId = :odfId ");
		Query query = entityManager.createQuery(sql.toString()).setParameter("odfId", odfId);
		return query.getResultList().get(0).toString();
	}

	/**
	 * method get all stations
	 *
	 * @param odfId
	 * @return String
	 * @author hungnv
	 * @date 13/9/2019
	 */
	@Override
	public Integer getOdfRowStatus(Long odfId) {
		StringBuilder sql = new StringBuilder("SELECT rowStatus FROM ");
		sql.append(InfraOdfBO.class.getName());
		sql.append(" WHERE odfId = :odfId ");
		Query query = entityManager.createQuery(sql.toString()).setParameter("odfId", odfId);
		return Integer.parseInt(query.getResultList().get(0).toString());
	}

	/**
	 * method get all stations
	 *
	 * @param odfId
	 * @return List<ViewOdfLaneBO>
	 * @author dungph
	 * @date 24/9/2019
	 */
	@Override
	public List<ViewOdfLaneBO> getOdfLaneByOdfId(Long odfId) {
		StringBuilder sqlSelect = new StringBuilder(" FROM ");
		sqlSelect.append(ViewOdfLaneBO.class.getName());
		sqlSelect.append(" WHERE odfId = :ODF_ID ");
		Query query = entityManager.createQuery(sqlSelect.toString()).setParameter("ODF_ID", odfId);
		List<ViewOdfLaneBO> viewOdfLaneBOList = query.getResultList();
//	Session session = entityManager.unwrap(Session.class);
//	List<ViewOdfLaneBO> viewOdfLaneBOList = session.find(ViewOdfLaneBO.class, odfId);
		return viewOdfLaneBOList;
	}

	/**
	 * method find odf code by stations in view
	 * khong phan quyen
	 * @param code
	 * @return String
	 * @author HieuDT
	 * @date 24/9/2019
	 */
	@Override
	public ViewInfraOdfBO findOdfByCode(String code) {
		List<ViewInfraOdfBO> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder("FROM ");
		sql.append(ViewInfraOdfBO.class.getName());
		sql.append(" io WHERE 1=1 ");
		sql.append(" AND io.odfCode = :odfCode");

		Query query = entityManager.createQuery(sql.toString(), ViewInfraOdfBO.class).setParameter("odfCode", code);
		try {
			result = query.getResultList();
		} catch (Exception e) {
			log.error("Exception", e);
		}
		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	/**
	 * method find odf code by stations
	 *
	 * @param code
	 * @return String
	 * @author HungNV
	 * @date 14/10/2019
	 */
	@Override
	public InfraOdfBO selectOdfByCode(String code) {
		List<InfraOdfBO> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder("FROM ");
		sql.append(InfraOdfBO.class.getName());
		sql.append(" io WHERE 1=1 ");
		sql.append(" AND io.odfCode = :odfCode");

		Query query = entityManager.createQuery(sql.toString(), InfraOdfBO.class).setParameter("odfCode", code);
		try {
			result = query.getResultList();
		} catch (Exception e) {
			log.error("Exception", e);
		}
		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	/**
	 *
	 */
	@Override
	public void setStatusCoupler(Long odf, Long coupler, int statuz) {
		Session session = entityManager.unwrap(Session.class);
		// set status for couplers
		InfraCouplerBO couplerBO = new InfraCouplerBO(odf, coupler, statuz);
		session.update(couplerBO);
	}

	/**
	 *
	 */
	@Override
	public void setStatusCableLine(Long cable, Long line, int statuz) {
		Session session = entityManager.unwrap(Session.class);
		// set status for line
		Query query2 = session.createQuery("update " + InfraCableLineBO.class.getName()
				+ " icl set icl.statuz = :STATUS where 1=1 and icl.cableId = :CABLE_ID and icl.lineNo = :LINE_NO ");
		query2.setParameter("CABLE_ID", cable);
		query2.setParameter("LINE_NO", line);
		query2.setParameter("STATUS", statuz);
		query2.executeUpdate();
	}

	/**
	 * method Search Odf Lane
	 *
	 * @param viewOdfLaneBO
	 * @return String
	 * @author DungPH
	 * @date 10/8/2019
	 */
	@Override
	public List<ViewOdfLaneBO> getOdfLaneBySearch(ViewOdfLaneBO viewOdfLaneBO) {

		HashMap<String, Object> data = new HashMap<>();
		StringBuilder sql = new StringBuilder("FROM ");
		sql.append(ViewOdfLaneBO.class.getName());
		sql.append(" vol WHERE 1=1 ");
		sql.append(" AND vol.odfId = :ODF_ID");
		data.put("ODF_ID", viewOdfLaneBO.getOdfId());
		// get data for Odf code
		if (viewOdfLaneBO.getOdfCode() != null && StringUtils.isNotEmpty(viewOdfLaneBO.getOdfCode().trim())) {
			sql.append(" AND vol.odfCode LIKE :ODF_CODE");
			data.put("ODF_CODE", CommonUtils.getLikeCondition(viewOdfLaneBO.getOdfCode()));
		}
		// get data for Odf code
		if (viewOdfLaneBO.getCableCode() != null && StringUtils.isNotEmpty(viewOdfLaneBO.getCableCode().trim())) {
			sql.append(" AND vol.cableCode LIKE :CABLE_CODE");
			data.put("CABLE_CODE", CommonUtils.getLikeCondition(viewOdfLaneBO.getCableCode()));
		}
		// get data for Odf code
		if (viewOdfLaneBO.getLaneCode() != null && StringUtils.isNotEmpty(viewOdfLaneBO.getLaneCode().trim())) {
			sql.append(" AND vol.laneCode LIKE :LANE_CODE");
			data.put("LANE_CODE", CommonUtils.getLikeCondition(viewOdfLaneBO.getLaneCode()));
		}

		try {
			// Query Advance
			Query query = entityManager.createQuery(sql.toString(), ViewOdfLaneBO.class);

			if (data.size() > 0) {
				for (Map.Entry<String, Object> param : data.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}
			// Set Count record per one page
			query.setMaxResults(viewOdfLaneBO.getRows());
			query.setFirstResult(viewOdfLaneBO.getFirst());
			List<ViewOdfLaneBO> result = query.getResultList();

			return result;
		} catch (Exception e) {
			log.error("Exception", e);
		}

		return null;
	}
}
