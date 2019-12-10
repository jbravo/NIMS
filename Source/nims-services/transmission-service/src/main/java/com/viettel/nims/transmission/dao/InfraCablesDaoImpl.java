package com.viettel.nims.transmission.dao;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.commom.CommonUtil;
import com.viettel.nims.transmission.model.InfraCablesBO;
import com.viettel.nims.transmission.model.InfraPointsBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewInfraOdfBO;
import com.viettel.nims.transmission.utils.Constains;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class InfraCablesDaoImpl implements InfraCablesDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public List<InfraCablesBO> findCablesByStationId(Long id) {
		List<InfraCablesBO> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder("from ");
		sql.append(InfraCablesBO.class.getName());
		sql.append(" ic where 1=1 ");
		sql.append(" and ic.sourceId = :sourceId or ic.destId = :destId");

		Query query = entityManager.createQuery(sql.toString(), InfraCablesBO.class).setParameter("sourceId", id)
				.setParameter("destId", id);

		try {
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<InfraCablesBO> findCableByHolderId(Long holderId) {
		List<InfraCablesBO> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" FROM ");
		sql.append(InfraCablesBO.class.getName());
		sql.append(" ic WHERE ic.sourceId= :holderId OR ic.destId=:holderId  AND ic.rowStatus=1");
		Query query = entityManager.createQuery(sql.toString(), InfraCablesBO.class);
		query.setParameter("holderId", holderId);
		try {
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public InfraCablesBO finById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		InfraCablesBO  infraCable = new InfraCablesBO();
		try {
			infraCable= session.find(InfraCablesBO.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(infraCable!=null) {
			return infraCable;
		}
		return null;
	}
	

  @Override
  public void saveOrUpdateCable(InfraCablesBO infraCablesBO) {
    try {
      Session session = entityManager.unwrap(Session.class);
      if (infraCablesBO.getCableId() != null) {
        infraCablesBO.setUpdateTime(new Date());
      }
      session.saveOrUpdate(infraCablesBO);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


	/**
	 * @author hungnv
	 * get all cable
	 * @param type = 1 => station ; 2 => pillar; 3 => pool 
	 * 
	 */
	@Override
	public List<InfraCablesBO> getAllCalble(Long userId,BigInteger type) {
		List<InfraCablesBO> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" FROM ");
		sql.append(InfraCablesBO.class.getName());
		sql.append(" ic WHERE 1=1 ");
		sql.append(" AND ic.sourceId IS NOT NULL  AND ic.destId IS NOT NULL ");
		sql.append(" AND ic.rowStatus = 1 ");
		sql.append(" AND ic.deptId in(" + CommonUtil.sqlAppendDepIds() + ")");
		sql.append(" AND ic.destId IN (SELECT ip.id FROM ");
		sql.append(InfraPointsBO.class.getName());
		sql.append(" ip WHERE ip.type = :TYPE )");
		
		Query query = entityManager.createQuery(sql.toString()).setParameter("userId", userId).setParameter("TYPE", type);
		// query.setParameter("TYPE", BigInteger.ONE);
	
		try {
			result = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public InfraCablesBO finByCableCode(String cableCode) {
		List<InfraCablesBO> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" FROM ");
		sql.append(InfraCablesBO.class.getName());
		sql.append(" ic WHERE 1=1 ");
		sql.append(" AND ic.cableCode = :cableCode ");
		sql.append(" AND ic.rowStatus = 1 ");
		Query query = entityManager.createQuery(sql.toString());
		query.setParameter("cableCode", cableCode);
		result = query.getResultList();
		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
}
