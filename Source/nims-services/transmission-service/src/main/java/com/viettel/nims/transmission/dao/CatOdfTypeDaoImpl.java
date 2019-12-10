package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.CatOdfTypeBO;
import com.viettel.nims.transmission.utils.Constains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

@Repository
public class CatOdfTypeDaoImpl implements CatOdfTypeDao {

  @Autowired
  private EntityManager entityManager;


  /**
   * method get all stations
   *
   * @param odfTypeId
   * @return CatOdfTypeBO
   * @author dungph
   * @date 25/9/2019
   */
  @Override
  @SuppressWarnings("unchecked")
  public CatOdfTypeBO getOdfTypeByOdfTypeId(Long odfTypeId){
    StringBuilder sql = new StringBuilder(" from ");
    sql.append(CatOdfTypeBO.class.getName());
    sql.append(" cot WHERE cot.odfTypeId = :id");
    Query query = entityManager.createQuery(sql.toString(), CatOdfTypeBO.class).setParameter("id", odfTypeId);
    return (CatOdfTypeBO) query.getSingleResult();
  }
  
  /**
   * @author DungPH
   *
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<CatOdfTypeBO> getAllCatOdfType() {
	  StringBuilder sql = new StringBuilder("from ");
	    sql.append(CatOdfTypeBO.class.getName());
	    sql.append(" cot where 1=1 and rowStatus = :ROW_STATUS");
	    Query query = entityManager.createQuery(sql.toString(), CatOdfTypeBO.class).setParameter("ROW_STATUS", Constains.NUMBER_ONE);
	    List<CatOdfTypeBO> listEntity = query.getResultList();
	    return listEntity;
  }

  /**
   * method get all odf type by odf type code
   *
   * @param odfTypeCode
   * @return CatOdfTypeBO
   * @author hungnv
   * @date 30/9/2019
   */
  @Override
	public CatOdfTypeBO getOdfTypeByCode(String odfTypeCode) {
		StringBuilder sql = new StringBuilder(" from ");
		sql.append(CatOdfTypeBO.class.getName());
		sql.append(" cot WHERE cot.odfTypeCode = :odfTypeCode AND rowStatus = 1");
		Query query = entityManager.createQuery(sql.toString()).setParameter("odfTypeCode", odfTypeCode);
		if (Objects.isNull(query.getSingleResult())) {
			return null;
		}
		return (CatOdfTypeBO) query.getSingleResult();
  }


}
