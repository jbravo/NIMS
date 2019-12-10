package com.viettel.nims.transmission.dao;

import java.math.BigInteger;
import java.sql.Date;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.viettel.nims.transmission.commom.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.viettel.nims.commons.util.CommonUtils;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.InfraPointsBO;
import com.viettel.nims.transmission.model.InfraPoolsBO;
import com.viettel.nims.transmission.model.view.ViewHangCableLaneBO;
import com.viettel.nims.transmission.model.view.ViewInfraPoolsBO;
import com.viettel.nims.transmission.model.view.ViewInfraSleevesBO;
import com.viettel.nims.transmission.utils.Constains;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by ThieuNV on 08/23/2019.
 */
@Slf4j
@Repository
public class InfraPoolDaoImpl implements InfraPoolDao {

	@Autowired
	EntityManager entityManager;
	@Autowired
	TransmissionDao transmissionDao;

	@Override
	public FormResult findBasicPool(InfraPoolsBO infraPoolsBO) {

		StringBuilder sqlCount = new StringBuilder("Select Count(po.poolId) ");
		StringBuilder sql = new StringBuilder("from ");
		sql.append(ViewInfraPoolsBO.class.getName());
		sql.append(" po where 1=1");
		if (infraPoolsBO.getBasicInfo() != null && StringUtils.isNotEmpty(infraPoolsBO.getBasicInfo().trim())) {
			sql.append(" AND po.poolCode LIKE :basicInfo");
		}
		sqlCount.append(sql.toString());
		sql.append(" ORDER BY po.poolCode");
		Query query = entityManager.createQuery(sql.toString(), ViewInfraPoolsBO.class);
		Query queryCount = entityManager.createQuery(sqlCount.toString());
		if (StringUtils.isNotEmpty(infraPoolsBO.getBasicInfo().trim())) {
			query.setParameter("basicInfo", CommonUtils.getLikeCondition(infraPoolsBO.getBasicInfo()));
			queryCount.setParameter("basicInfo", CommonUtils.getLikeCondition(infraPoolsBO.getBasicInfo()));
		}
		FormResult result = new FormResult();
		result.setTotalRecords((Long) queryCount.getSingleResult());
		query.setMaxResults(infraPoolsBO.getRows());
		query.setFirstResult(infraPoolsBO.getFirst());
		List<ViewInfraPoolsBO> resultList = query.getResultList();

//		for (ViewInfraPoolsBO v : resultList) {
//			v.setLongitude(v.getGeometry().getX());
//			v.setLatitude(v.getGeometry().getY());
//		}
		result.setListData(resultList);
		return result;
	}

	@Override
	public FormResult findAdvancePool(List<InfraPoolsBO> infraPoolsBOs, Long userId) {
		StringBuilder sqlCount = new StringBuilder("Select Count(po.poolId) ");
		StringBuilder sql = new StringBuilder("from ");
		sql.append(ViewInfraPoolsBO.class.getName());
		sql.append(" po where 1=1");
		HashMap<String, Object> params = new HashMap<>();
		sql.append(" and po.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
		sql.append(" and po.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
		params.put("userId", userId);
		for (int i = 0; i < infraPoolsBOs.size(); i++) {
			InfraPoolsBO infraPoolsBO = infraPoolsBOs.get(i);

			if (infraPoolsBO.getBasicInfo() != null && StringUtils.isNotEmpty(infraPoolsBO.getBasicInfo().trim())) {
				sql.append(" AND po.poolCode LIKE :basicInfo" + i);
				params.put("basicInfo" + i, CommonUtils.getLikeCondition(infraPoolsBO.getBasicInfo()));
			}

			if (infraPoolsBO.getPoolCode() != null && StringUtils.isNotEmpty(infraPoolsBO.getPoolCode().trim())) {
				sql.append(" AND po.poolCode LIKE :POOL_CODE" + i);
				params.put("POOL_CODE" + i, CommonUtils.getLikeCondition(infraPoolsBO.getPoolCode()));
			}
			if (infraPoolsBO.getDeptName() != null && StringUtils.isNotEmpty(infraPoolsBO.getDeptName().trim())) {
				sql.append(" AND po.deptName LIKE :DEPT_NAME" + i);
				params.put("DEPT_NAME" + i, CommonUtils.getLikeCondition(infraPoolsBO.getDeptName()));
			}
			if (infraPoolsBO.getLocationName() != null
					&& StringUtils.isNotEmpty(infraPoolsBO.getLocationName().trim())) {
				sql.append(" AND po.locationName LIKE :LOCATION_NAME" + i);
				params.put("LOCATION_NAME" + i, CommonUtils.getLikeCondition(infraPoolsBO.getLocationName()));
			}
			if (infraPoolsBO.getPoolTypeId() != null) {
				sql.append(" AND po.poolTypeId = :POOL_TYPE_ID" + i);
				params.put("POOL_TYPE_ID" + i, infraPoolsBO.getPoolTypeId());
			}
			if (infraPoolsBO.getPoolTypeCode() != null
					&& StringUtils.isNotEmpty(infraPoolsBO.getPoolTypeCode().trim())) {
				sql.append(" AND po.poolTypeCode LIKE :POOL_TYPE_CODE" + i);
				params.put("POOL_TYPE_CODE" + i, CommonUtils.getLikeCondition(infraPoolsBO.getPoolTypeCode()));
			}
			if (infraPoolsBO.getOwnerId() != null) {
				sql.append(" AND po.ownerId = :OWNER_ID" + i);
				params.put("OWNER_ID" + i, infraPoolsBO.getOwnerId());
			}
			if (infraPoolsBO.getAddress() != null && StringUtils.isNotEmpty(infraPoolsBO.getAddress().trim())) {
				sql.append(" AND po.address LIKE :ADDRESS" + i);
				params.put("ADDRESS" + i, CommonUtils.getLikeCondition(infraPoolsBO.getAddress()));
			}
			if (infraPoolsBO.getNote() != null && StringUtils.isNotEmpty(infraPoolsBO.getNote().trim())) {
				sql.append(" AND po.note LIKE :NOTE" + i);
				params.put("NOTE" + i, CommonUtils.getLikeCondition(infraPoolsBO.getNote()));
			}
			if (infraPoolsBO.getStatus() != null) {
				sql.append(" AND po.status = :STATUS" + i);
				params.put("STATUS" + i, infraPoolsBO.getStatus());
			}
			if (infraPoolsBO.getConstructionDate() != null) {
				sql.append(" AND po.constructionDate LIKE :CONSTRUCTION_DATE" + i);
				params.put("CONSTRUCTION_DATE" + i, infraPoolsBO.getConstructionDate());
			}
			if (infraPoolsBO.getDeliveryDate() != null) {
				sql.append(" AND po.deliveryDate LIKE :DELIVERY_DATE" + i);
				params.put("DELIVERY_DATE" + i, infraPoolsBO.getDeliveryDate());
			}
			if (infraPoolsBO.getAcceptanceDate() != null) {
				sql.append(" AND po.acceptanceDate LIKE :ACCEPTANCE_DATE" + i);
				params.put("ACCEPTANCE_DATE" + i, infraPoolsBO.getAcceptanceDate());
			}

			if (infraPoolsBO.getLongitudeText() != null) {
//				sql.append(" AND FLOOR(po.longitude) = :LONGITUDE" + i);
//				params.put("LONGITUDE" + i, infraPoolsBO.getLongitude() >= 0 ? infraPoolsBO.getLongitude().intValue() : infraPoolsBO.getLongitude().intValue() - 1);
				sql.append(" AND CONVERT(po.longitude as char) LIKE :LONGITUDE" + i);
				params.put("LONGITUDE" + i, CommonUtils.getLikeCondition(infraPoolsBO.getLongitudeText()));
			}

			if (infraPoolsBO.getLatitudeText() != null) {
//				sql.append(" AND FLOOR(po.latitude) = :LATITUDE" + i);
//				params.put("LATITUDE" + i, infraPoolsBO.getLatitude() >= 0 ? infraPoolsBO.getLatitude().intValue() : infraPoolsBO.getLatitude().intValue() - 1);
				sql.append(" AND CONVERT(po.latitude as char) LIKE :LATITUDE" + i);
				params.put("LATITUDE" + i, CommonUtils.getLikeCondition(infraPoolsBO.getLatitudeText()));
			}
		}
		sqlCount.append(sql.toString());

		if (infraPoolsBOs.get(Constains.NUMBER_ZERO).getSortField() != null
				&& infraPoolsBOs.get(Constains.NUMBER_ZERO).getSortOrder() != null) {
			if ("longitude".equalsIgnoreCase(infraPoolsBOs.get(Constains.NUMBER_ZERO).getSortField())) {

				if (infraPoolsBOs.get(Constains.NUMBER_ZERO).getSortOrder() == 1) {
					sql.append(" order by po.longitude  desc");
				} else {
					sql.append(" order by po.longitude  asc");
				}

			} else if ("latitude".equalsIgnoreCase(infraPoolsBOs.get(Constains.NUMBER_ZERO).getSortField())) {

				if (infraPoolsBOs.get(Constains.NUMBER_ZERO).getSortOrder() == 1) {
					sql.append(" order by po.latitude  desc");
				} else {
					sql.append(" order by po.latitude  asc");
				}

			} else

			{
				if (infraPoolsBOs.get(Constains.NUMBER_ZERO).getSortOrder() == 1) {
					sql.append(" order by po." + infraPoolsBOs.get(Constains.NUMBER_ZERO).getSortField() + "  desc");
				} else {
					sql.append(" order by po." + infraPoolsBOs.get(Constains.NUMBER_ZERO).getSortField() + "  asc");
				}
			}

		} else {
			sql.append(" ORDER BY po.poolCode");
		}
		Query query = entityManager.createQuery(sql.toString(), ViewInfraPoolsBO.class);
		Query queryCount = entityManager.createQuery(sqlCount.toString());

		FormResult result = new FormResult();
		if (params.size() > 0) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
				queryCount.setParameter(param.getKey(), param.getValue());
			}
		}
		result.setTotalRecords((Long) queryCount.getSingleResult());
		if (infraPoolsBOs.get(Constains.NUMBER_ZERO).getRows() != null
				&& infraPoolsBOs.get(Constains.NUMBER_ZERO).getFirst() != null) {
			query.setMaxResults(infraPoolsBOs.get(Constains.NUMBER_ZERO).getRows());
			query.setFirstResult(infraPoolsBOs.get(Constains.NUMBER_ZERO).getFirst());
		}
		List<ViewInfraPoolsBO> resultList = query.getResultList();

//		for (ViewInfraPoolsBO v : resultList) {
//			v.setLongitude(v.getGeometry().getX());
//			v.setLatitude(v.getGeometry().getY());
//		}
		result.setListData(resultList);
		return result;
	}

	@Override
	public Long savePool(InfraPoolsBO infraPoolsBO) {
		Long poolId = infraPoolsBO.getPoolId();
		try {
			Session session = entityManager.unwrap(Session.class);
			if (infraPoolsBO.getPoolId() != null) {
				infraPoolsBO.setUpdateTime(new Date(Calendar.getInstance().getTimeInMillis()));
			}
			if (infraPoolsBO.getPoolId() == null) {
				InfraPointsBO infraPointsBO = new InfraPointsBO();
				infraPointsBO.setType(BigInteger.valueOf(Constains.INFRA_POINT_TYPE_POOL));
				poolId = (Long) session.save(infraPointsBO);
				infraPoolsBO.setPoolId(poolId);
				infraPoolsBO.setCreateTime(new Date(Calendar.getInstance().getTimeInMillis()));
			}
			infraPoolsBO.setRowStatus(Constains.NUMBER_ONE);
			session.saveOrUpdate(infraPoolsBO);
			return poolId;
		} catch (Exception e) {
			log.error("Exception", e);
			return null;
		}
	}

	@Override
	public Long getMaxNumberPool(Long path, Long userId) {
//		StringBuilder sql = new StringBuilder(" Select MAX(po.poolYYYY) from ");
//		sql.append(ViewInfraPoolsBO.class.getName());
//		sql.append(" po where 1=1");
//		sql.append(" AND po.poolTTT = :TTT");
//		Query query = entityManager.createQuery(sql.toString());
//		query.setParameter("TTT", transmissionDao.findDepartmentById(path, userId).getDeptCode());
//		Object ob = query.getResultList().get(Constains.NUMBER_ZERO);
//		if (ob == null)
//			return null;
//		else
//			return Long.parseLong(ob.toString());

		StringBuilder sql = new StringBuilder(" Select Max(po.poolCode) from ");
		sql.append(ViewInfraPoolsBO.class.getName());
		sql.append(" po where 1=1");
		sql.append(" AND SUBSTR(po.poolCode,1,3) = :TTT");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("TTT", transmissionDao.findDepartmentById(path, userId).getDeptCode());
		Object ob = query.getResultList().get(Constains.NUMBER_ZERO);
		if (ob == null)
			return null;
		else
			return Long.parseLong(ob.toString().substring(4, 8));// TTTPYYYYZ
	}

	@Override
	public String checkNumberAndGetZ(String dept_TTT, Long number_YYYY) {
//		StringBuilder sql = new StringBuilder(" Select MAX(po.poolZ) from ");
//		sql.append(ViewInfraPoolsBO.class.getName());
//		sql.append(" po where po.poolTTT = :TTT");
//		sql.append(" and po.poolYYYY = :YYYY");
//		Query query = entityManager.createQuery(sql.toString());
//		query.setParameter("TTT", dept_TTT);
//		query.setParameter("YYYY", number_YYYY);
//
//		Object ob = query.getResultList().get(Constains.NUMBER_ZERO);
//		if (ob == null)
//			return null;
//		else
//			return ob.toString();

		String poolCode = dept_TTT + "P" + String.format("%04d", number_YYYY);
		StringBuilder sql = new StringBuilder(" Select MAX(po.poolCode) from ");
		sql.append(ViewInfraPoolsBO.class.getName());
		sql.append(" po where po.poolCode like :POOLCODE");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("POOLCODE", poolCode + "%");

		Object ob = query.getResultList().get(Constains.NUMBER_ZERO);
		if (ob == null)
			return null;
		else
			return ob.toString().substring(8, 9);

	}

	@Override
	public ViewInfraPoolsBO findPoolById(Long id) {
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(ViewInfraPoolsBO.class.getName());
		sql.append(" po where 1=1");
		sql.append(" AND po.poolId = :ID");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("ID", id);
		return (ViewInfraPoolsBO) query.getResultList().get(Constains.NUMBER_ZERO);

	}

	@Override
	public int deletePool(Long id) {
		Session session = entityManager.unwrap(Session.class);

		InfraPoolsBO infraPoolsBO = entityManager.find(InfraPoolsBO.class, id);
		if (infraPoolsBO != null && infraPoolsBO.getRowStatus().equals(Constains.NUMBER_ONE)) {
			infraPoolsBO.setRowStatus(Constains.NUMBER_TWO);
			session.save(infraPoolsBO);
			return Constains.NUMBER_ONE;
		}
		return Constains.NUMBER_ZERO;
	}

	@Override
	public ViewInfraPoolsBO findPoolByPoolCode(String poolCode) {
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(ViewInfraPoolsBO.class.getName());
		sql.append(" po where 1=1");
		sql.append(" AND po.poolCode = :PC");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("PC", poolCode);
		List<?> list = query.getResultList();
		if (list.size() == 0)
			return null;
		return (ViewInfraPoolsBO) query.getResultList().get(Constains.NUMBER_ZERO);
	}

	@Override
	public List<ViewInfraSleevesBO> listSleevesInPool(Long poolId) {
		StringBuilder sql = new StringBuilder("from ");
		sql.append(ViewInfraSleevesBO.class.getName());
		sql.append(" sl where 1=1");
		sql.append(" AND sl.holderId = :HOLDERID");
		Query query = entityManager.createQuery(sql.toString(), ViewInfraSleevesBO.class);
		query.setParameter("HOLDERID", poolId);
		return query.getResultList();
	}

	@Override
	public FormResult getLaneCodeHang(InfraPoolsBO entity) {
		StringBuilder sqlCount = new StringBuilder("SELECT COUNT(cd.cableId) ");
		StringBuilder sql = new StringBuilder("from ");
		sql.append(ViewHangCableLaneBO.class.getName());
		sql.append(" cd where 1=1 ");
		HashMap<String, Object> params = new HashMap<>();
		if (entity.getPoolCode() != null && StringUtils.isNotEmpty(entity.getPoolCode().trim())) {
			sql.append(" and cd.poolCode LIKE :poolCode");
			params.put("poolCode", CommonUtils.getLikeCondition(entity.getPoolCode()));
		}

		sqlCount.append(sql.toString());
		Query query = entityManager.createQuery(sql.toString(), ViewHangCableLaneBO.class);
		Query queryCount = entityManager.createQuery(sqlCount.toString());
		if (params.size() > 0) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
				queryCount.setParameter(param.getKey(), param.getValue());
			}
		}
		if (entity.getRows() != null) {
			query.setMaxResults(entity.getRows());
		}
		if (entity.getFirst() != null) {
			query.setFirstResult(entity.getFirst());
		}
		List<ViewHangCableLaneBO> resultList = query.getResultList();
		FormResult result = new FormResult();
		result.setListData(resultList);
		result.setTotalRecords((Long) queryCount.getSingleResult());
		return result;
	}

	@Override
	public InfraPoolsBO findPoolBOByPoolCode(String poolCode) {
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(InfraPoolsBO.class.getName());
		sql.append(" po where 1=1");
		sql.append(" AND po.poolCode = :PC and po.rowStatus = 1 ");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("PC", poolCode);
		List<?> list = query.getResultList();
		if (list.size() == 0)
			return null;
		return (InfraPoolsBO) query.getResultList().get(Constains.NUMBER_ZERO);
	}

	@Override
	public boolean checkPoolPermission(InfraPoolsBO bo, Long userId) {
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(InfraPoolsBO.class.getName());
		sql.append(" po where 1=1");
		sql.append(" AND po.poolId = :PC and po.rowStatus = 1 ");
		sql.append(" and po.deptId  in (" + CommonUtil.sqlAppendDepIds() + ")");
		sql.append(" and po.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("PC", bo.getPoolId()).setParameter("userId", userId);
		List<?> list = query.getResultList();
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}

	}

  @Override
  public boolean checkRolePoolWithPoolCode(String poolCode, Long userId) {
    StringBuilder sql = new StringBuilder(" from ");
    sql.append(ViewInfraPoolsBO.class.getName());
    sql.append(" po where 1=1");
    sql.append(" AND po.poolCode = :poolCode");
    sql.append(" and po.deptId  in (" + CommonUtil.sqlAppendDepIds() + ")");
    sql.append(" and po.locationId in ( " + CommonUtil.sqlAppendLocationIds() + ")");
    Query query = entityManager.createQuery(sql.toString(), ViewInfraPoolsBO.class);
    query.setParameter("poolCode", poolCode);
    query.setParameter("userId", userId);
    try {
      List<?> result = query.getResultList();
      if(result.size() > 0){
        return true;
      }
    } catch (Exception e) {
      log.error("Exception", e);
    }
      return false;
  }
}
