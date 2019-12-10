package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.*;
import com.viettel.nims.transmission.model.view.ViewWeldingOdfBO;
import com.viettel.nims.transmission.utils.Constains;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BinhNV on --/08/2019.
 * Updated by BinhNV on 17/09/2019.
 */
@Slf4j
@Repository
public class WeldingOdfDaoImpl implements WeldingOdfDao {

  private EntityManager entityManager;

  public WeldingOdfDaoImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<ViewWeldingOdfBO> selectAllWeldingOdf(Long id) {
    List<ViewWeldingOdfBO> weldingOdfList = new ArrayList<>();
    Query queryData = entityManager.createQuery("from " + ViewWeldingOdfBO.class.getName()
        + " vwo where 1=1 and vwo.odfId = :ODF_ID ORDER BY vwo.couplerNo asc");
    queryData.setParameter("ODF_ID", id);
    this.getData(queryData, weldingOdfList);
    return weldingOdfList;
  }

  @Override
  public List<ViewWeldingOdfBO> selectWeldingOdfs(Long id, List<Long> couplers) {
    List<ViewWeldingOdfBO> weldingOdfList = new ArrayList<>();
    Query queryData = entityManager.createQuery("from " + ViewWeldingOdfBO.class.getName()
        + " vwo where 1=1 and vwo.odfId = :ODF_ID and vwo.couplerNo in (:COUPLERS) ORDER BY vwo.couplerNo asc");
    queryData.setParameter("ODF_ID", id);
    queryData.setParameter("COUPLERS", couplers);
    this.getData(queryData, weldingOdfList);
    return weldingOdfList;
  }

  @Override
  public ViewWeldingOdfBO findSelectedWeldingOdf(Long id, Long couplerNo, Integer odfConnectType) {
    Query queryData = entityManager.createQuery("from " + ViewWeldingOdfBO.class.getName()
        + " vwo where 1=1 and vwo.odfId = :ODF_ID and vwo.couplerNo = :COUPLER");
    queryData.setParameter("ODF_ID", id);
    queryData.setParameter("COUPLER", couplerNo);
    return (ViewWeldingOdfBO) queryData.getSingleResult();
  }

  @Override
  public String selectOdfCodeById(Long id) {
    Query query = entityManager.createQuery("select vo.odfCode from "+ InfraOdfBO.class.getName()
                + " vo where 1=1 and vo.odfId = :ODF_ID");
    query.setParameter("ODF_ID", id);
    return (String) query.getSingleResult();
  }

  @Override
  public List selectCouplerNos(Long id) {
    Query query1 = entityManager.createQuery("SELECT ic.couplerNo from " + InfraCouplerBO.class.getName()
        + " ic where 1=1 and ic.statuz = 0 and ic.odfId = :ODF_ID order by ic.couplerNo asc");
    query1.setParameter("ODF_ID", id);
    return query1.getResultList();
  }

  @Override
  public List<CableBO> selectCableCodes(Long id) {
    Query query = entityManager.createQuery("SELECT cab.cableId, cab.cableCode FROM "
        + InfraCablesBO.class.getName()+ " cab where 1=1 and cab.rowStatus = 1 and (SELECT odf.stationId FROM "+ InfraOdfBO.class.getName()
        + " odf where odf.odfId = :ODF_ID ) IN (cab.sourceId, cab.destId) order by cab.cableCode asc");
    query.setParameter("ODF_ID", id);
    List<CableBO> resultList = new ArrayList<>();
    List objects = query.getResultList();
    for (Object obj : objects ) {
      Object[] objArr = (Object[]) obj;
      CableBO cable = new CableBO();
      cable.setCableId((Long) objArr[0]);
      cable.setCableCode(objArr[1].toString());
      resultList.add(cable);
    }
    return resultList;
  }

  @Override
  public List selectLineNos(Long id) {
    Query query1 = entityManager.createQuery("SELECT icl.lineNo from " + InfraCableLineBO.class.getName()
        + " icl where 1=1 and icl.statuz = 0 and icl.cableId = :CABLE_ID  order by icl.lineNo asc");
    query1.setParameter("CABLE_ID", id);
    return query1.getResultList();
  }

  @Override
  public List<DestOdfBO> selectDestOdfs(Long id) {
    Query query = entityManager.createQuery("select o1.odfId, o1.odfCode from "+ InfraOdfBO.class.getName()
        + " o1 where 1=1 and o1.rowStatus = 1 and o1.stationId = ( select o2.stationId from "+ InfraOdfBO.class.getName()
        + " o2 where o2.odfId = :ODF_ID )");
    query.setParameter("ODF_ID", id);
    List destOdfList = query.getResultList();
    List<DestOdfBO> resultList = new ArrayList<>();
    for (Object obj: destOdfList) {
      Object[] objArr = (Object[]) obj;
      DestOdfBO destOdf = new DestOdfBO();
      destOdf.setOdfId((Long) objArr[0]);
      destOdf.setOdfCode(objArr[1].toString());
      resultList.add(destOdf);
     }
    return resultList;
  }

  @Override
  public List<JointCouplersBO> selectJointCouplerNos(Long id) {
    Query query1 = entityManager.createQuery("SELECT ic.couplerNo from "+ InfraCouplerBO.class.getName()
        + " ic where 1=1 and ic.statuz = 0 and ic.odfId = :ODF_ID order by ic.couplerNo asc");
    query1.setParameter("ODF_ID", id);
    List couplers = query1.getResultList();
    List<JointCouplersBO> resultList = new ArrayList<>();
    if (couplers.size() > 0){
      Query query2 = entityManager.createQuery("from "+ ViewCableLinesInStationBO.class.getName()
          + " where 1=1 and ( sourceOdfId = :ODF_ID or destOdfId = :ODF_ID)");
      query2.setParameter("ODF_ID", id);
      List cableLines = query2.getResultList();
      for (Object obj: cableLines ) {
        ViewCableLinesInStationBO cableInStation = (ViewCableLinesInStationBO) obj;
        for (int i = 1; i <= cableInStation.getCapacity() ; i++) {
          JointCouplersBO bo = new JointCouplersBO();
          bo.setCableCode(cableInStation.getCableCode());
          bo.setLineNo(i);
          resultList.add(bo);
        }
      }
      int j = -1;
      for (JointCouplersBO result :resultList) {
        j++;
        if (j < couplers.size()){
          result.setCouplerNo((long) couplers.get(j));
        } else {
          break;
        }
      }
    }
    return resultList;
  }

  @Override
  public int deleteWeldingOdfs(List<WeldingOdfBO> weldingOdfs) {
    Session session = entityManager.unwrap(Session.class);
    int result = 0;
    try {
      for (WeldingOdfBO weldingOdf : weldingOdfs ) {
        String sql;
        Query query;
        if (weldingOdf.getCableId() != null){
          sql = "delete from " + WeldingOdfToCableBO.class.getName()+ " wo ";
          this.setStatusCoupler(weldingOdf.getOdfId(), weldingOdf.getCouplerNo(),0 , session);
          this.setStatusCableLine(weldingOdf.getCableId(),weldingOdf.getLineNo(), 0, session);
        } else {
          sql = "delete from " + WeldingOdfToOdfBO.class.getName()+ " wo ";
          this.setStatusCoupler(weldingOdf.getOdfId(), weldingOdf.getCouplerNo(),0 , session);
          this.setStatusCoupler(weldingOdf.getDestOdfId(), weldingOdf.getDestCouplerNo(),0 , session);
        }
        query = session.createQuery(sql + this.queryCondition());
        query.setParameter("ODF_ID", weldingOdf.getOdfId());
        query.setParameter("COUPLER_NO", weldingOdf.getCouplerNo());
        int deletedCnt = query.executeUpdate();
        result += deletedCnt;
      }
    } catch (Exception e) {
      log.error("Exception", e);
    }
    return result;
  }

  @Override
  public void insertWeldingOdf(WeldingOdfBO weldingOdf){
    Session session = entityManager.unwrap(Session.class);
    try {
      if (weldingOdf.getCreateDate() != null) {
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        weldingOdf.setCreate_Date(sdf.parse(weldingOdf.getCreateDate()));
      } else { weldingOdf.setCreate_Date(null);}
      if (weldingOdf.getCableId() != null) {
        if (weldingOdf.getCouplerNoFrom() != null) {
          long j = weldingOdf.getLineNoFrom()-1;
          for (long i = weldingOdf.getCouplerNoFrom(); i <= weldingOdf.getCouplerNoTo(); i++) {
            j++;
            if (j<=weldingOdf.getLineNoTo()){
              weldingOdf.setCouplerNo(i);
              weldingOdf.setLineNo(j);
              this.executeSaveWeldingODf(weldingOdf, session, weldingOdf.getCreate_Date());
            }
          }
        } else { this.executeSaveWeldingODf(weldingOdf, session, weldingOdf.getCreate_Date());}
      } else {
        if (weldingOdf.getSourceCouplerNos().length > 0) {
          int j = -1;
          for (int i = 0; i < weldingOdf.getSourceCouplerNos().length; i++) {
            j ++;
            weldingOdf.setCouplerNo(weldingOdf.getSourceCouplerNos()[i]);
            weldingOdf.setDestCouplerNo(weldingOdf.getDestCouplerNos()[j]);
            this.executeSaveJointingODf(weldingOdf, session, weldingOdf.getCreate_Date());
          }
        } else { this.executeSaveJointingODf(weldingOdf, session, weldingOdf.getCreate_Date());}
      }
    } catch (Exception e) {
      log.error("Exception", e);
    }
  }

  @Override
  public void updateWeldingOdf(WeldingOdfBO updatingDto) {
    Session session = entityManager.unwrap(Session.class);
    try {
      if (updatingDto.getCreateDate() != null) {
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        updatingDto.setCreate_Date(sdf.parse(updatingDto.getCreateDate()));
      } else {
        updatingDto.setCreate_Date(null);
      }
      String sqlBegin;
      if (updatingDto.getOdfConnectType().equals(1) ) {
        sqlBegin = "update "+ WeldingOdfToCableBO.class.getName();
      } else {
        sqlBegin = "update "+ WeldingOdfToOdfBO.class.getName();
      }
      Query query = session.createQuery(sqlBegin + " wo set wo.attenuation = :ATTENUATION , wo.note = :NOTE , "
          + " wo.createUser = :CREATE_USER , wo.createDate = :CREATE_DATE" + this.queryCondition());
      query.setParameter("ODF_ID", updatingDto.getOdfId());
      query.setParameter("COUPLER_NO", updatingDto.getCouplerNo());
      query.setParameter("ATTENUATION", updatingDto.getAttenuation());
      query.setParameter("CREATE_USER", updatingDto.getCreateUser());
      query.setParameter("CREATE_DATE", updatingDto.getCreate_Date());
      query.setParameter("NOTE", updatingDto.getNote());
      query.executeUpdate();
    } catch (Exception e) {
      log.error("Exception", e);
    }
  }

	@Override
	public void executeSaveWeldingODf(CntCouplerToLineBO cntCouplerToLineBO) {
		Session session  = entityManager.unwrap(Session.class);
		session.saveOrUpdate(cntCouplerToLineBO);

	}

	@Override
	public void executeSaveJointODf(CntCouplerToCouplerBO cntCouplerToCouplerBO) {
		Session session  = entityManager.unwrap(Session.class);
		session.saveOrUpdate(cntCouplerToCouplerBO);
	}

	public CntCouplerToLineBO selectWeldingOdfByCode(Long odfId, Integer couplerNo, Long cableId, Integer lineNo) {
		List<CntCouplerToLineBO> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder("FROM ");
		sql.append(CntCouplerToLineBO.class.getName());
		sql.append(" io WHERE 1=1 ");
		sql.append(" AND io.odfId = :odfId");
		sql.append(" AND io.couplerNo = :couplerNo");
		sql.append(" AND io.cableId = :cableId");
		sql.append(" AND io.lineNo = :lineNo");

		Query query = entityManager.createQuery(sql.toString(), InfraOdfBO.class).setParameter("odfId", odfId ).setParameter("couplerNo", couplerNo).setParameter("cableId", cableId).setParameter("lineNo", lineNo);
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

  private void executeSaveWeldingODf(WeldingOdfBO weldingOdf, Session session, Date createTime) {
    // create object
    WeldingOdfToCableBO odfToCable = new WeldingOdfToCableBO(weldingOdf.getOdfId(), weldingOdf.getCouplerNo(),
        weldingOdf.getCableId(),weldingOdf.getLineNo(),weldingOdf.getAttenuation(),weldingOdf.getNote(),
        createTime, weldingOdf.getCreateUser());
    //insert object to DB
    session.save(odfToCable);
    //set status for coupler and line
    this.setStatusCoupler(weldingOdf.getOdfId(), weldingOdf.getCouplerNo(),1 , session);
    this.setStatusCableLine(weldingOdf.getCableId(),weldingOdf.getLineNo(), 1, session);
  }

  private void executeSaveJointingODf(WeldingOdfBO weldingOdf, Session session, Date createTime) {
    // create object
    WeldingOdfToOdfBO odfToOdf = new WeldingOdfToOdfBO(weldingOdf.getOdfId(), weldingOdf.getCouplerNo(),
        weldingOdf.getDestOdfId(),weldingOdf.getDestCouplerNo(), weldingOdf.getAttenuation(),
        weldingOdf.getNote(), createTime, weldingOdf.getCreateUser());
    //insert object to DB
    session.save(odfToOdf);
    // set status for couplers
    this.setStatusCoupler(weldingOdf.getOdfId(), weldingOdf.getCouplerNo(),1 , session);
    this.setStatusCoupler(weldingOdf.getDestOdfId(), weldingOdf.getDestCouplerNo(),1 , session);
  }

  private void setStatusCoupler(Long odf, Long coupler, int statuz, Session sson){
    // set status for couplers
    InfraCouplerBO couplerBO = new InfraCouplerBO(odf, coupler, statuz);
    sson.update(couplerBO);
  }

  private void setStatusCableLine(Long cable, Long line, int statuz, Session sson){
    // set status for line
    Query query2 = sson.createQuery("update " + InfraCableLineBO.class.getName()
        + " icl set icl.statuz = :STATUS where 1=1 and icl.cableId = :CABLE_ID and icl.lineNo = :LINE_NO ");
    query2.setParameter("CABLE_ID", cable);
    query2.setParameter("LINE_NO", line);
    query2.setParameter("STATUS", statuz);
    query2.executeUpdate();
  }

  private String queryCondition(){
    return " where 1=1 and wo.odfId = :ODF_ID and wo.couplerNo = :COUPLER_NO ";
  }

  private void getData(Query query, List<ViewWeldingOdfBO> weldingOdfList){
    List resultList = query.getResultList();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    for (Object object: resultList ) {
      ViewWeldingOdfBO weldingOdf = (ViewWeldingOdfBO) object;
      weldingOdf.setCableId(weldingOdf.getCable_Id() > 0 ? String.valueOf(weldingOdf.getCable_Id()) : "");
      weldingOdf.setDestOdfId(weldingOdf.getDest_OdfId() > 0 ? String.valueOf(weldingOdf.getDest_OdfId()) : "");
      weldingOdf.setLineNo(weldingOdf.getLine_No() > 0 ? String.valueOf(weldingOdf.getLine_No()) : "");
      Long destCoupler = weldingOdf.getDest_Coupler();
      weldingOdf.setDestCouplerNo(destCoupler > 0 ? String.valueOf(destCoupler) : "");
      weldingOdf.setCreateDate(weldingOdf.getCreate_Date() != null ? sdf.format(weldingOdf.getCreate_Date()) : "");
      weldingOdf.setOdfConnectType(weldingOdf.getCable_Id() > 0 ? Constains.WELD_ODF_TYPE_0: Constains.WELD_ODF_TYPE_1);
      BigDecimal attenuation = weldingOdf.getReal_attenuation();
      weldingOdf.setAttenuation(attenuation != null ? attenuation.toString() : "");
      weldingOdfList.add(weldingOdf);
    }
  }
}
