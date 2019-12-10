package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.CfgMapUserBO;
import com.viettel.nims.transmission.model.CfgMapUserObjectBO;
import com.viettel.nims.transmission.model.CfgMapUserObjectPK;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by VTN-PTPM-NV64 on 9/13/2019.
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class ConfigMapDaoImpl implements  ConfigMapDao {

  @Autowired
  EntityManager entityManager;

  @Override
  public void saveConfigCommon(CfgMapUserBO cfgMapUserBO) {
    Session session = entityManager.unwrap(Session.class);
    Long id = null;
    if(cfgMapUserBO.getCfgMapUserId() != null) {
      session.saveOrUpdate(cfgMapUserBO);
    }else{
       id  = (Long) session.save(cfgMapUserBO);
    }
      if(cfgMapUserBO.getMapConfig().size() > 0){
        for(int i = 0; i< cfgMapUserBO.getMapConfig().size(); i++){
          CfgMapUserObjectBO cfgMapUserObjectBO = cfgMapUserBO.getMapConfig().get(i);
          CfgMapUserObjectPK cfgMapUserObjectPK = new CfgMapUserObjectPK();
          if(cfgMapUserBO.getCfgMapUserId() != null){
            cfgMapUserObjectPK.setCfgMapUserId(cfgMapUserBO.getCfgMapUserId());
          }else{
            cfgMapUserObjectPK.setCfgMapUserId(id);
          }
          cfgMapUserObjectPK.setObjectCode(cfgMapUserObjectBO.getProperty());
          cfgMapUserObjectBO.setId(cfgMapUserObjectPK);
          session.saveOrUpdate(cfgMapUserObjectBO);
          if(i % 20 == 0){
            session.flush();
            session.clear();
          }
        }
      }

  }

  @Override
  public CfgMapUserBO getInfoConfigMap(Long id) {
    CfgMapUserBO  cfgMapUserBO = entityManager.find(CfgMapUserBO.class,id);
    CfgMapUserObjectPK cfgMapUserObjectPK = new CfgMapUserObjectPK();

    StringBuilder sql = new StringBuilder("from ");
    sql.append(CfgMapUserObjectBO.class.getName());
    sql.append(" cf where cf.id.cfgMapUserId = :cfgMapUserId");
    Query query = entityManager.createQuery(sql.toString(),CfgMapUserObjectBO.class);
    query.setParameter("cfgMapUserId",id);
    List<CfgMapUserObjectBO> listObj = query.getResultList();
    cfgMapUserBO.setMapConfig(listObj);
    return cfgMapUserBO;
  }
}
