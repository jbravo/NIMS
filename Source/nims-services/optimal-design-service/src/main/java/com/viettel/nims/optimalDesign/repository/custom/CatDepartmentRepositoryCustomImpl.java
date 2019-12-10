package com.viettel.nims.optimalDesign.repository.custom;

import com.viettel.nims.optimalDesign.dto.CatDepartmentDTO;
import com.viettel.nims.optimalDesign.entity.CatDepartment;
import com.viettel.nims.optimalDesign.mapper.BaseMapper;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CatDepartmentRepositoryCustomImpl implements CatDepartmentRepositoryCustom {
  private final Logger log = LoggerFactory.getLogger(CatDepartment.class);
  @PersistenceContext
  EntityManager entityManager;
  BaseMapper<CatDepartment, CatDepartmentDTO> mapper = new BaseMapper<>(CatDepartment.class, CatDepartmentDTO.class);

  @Override
  public List<Long> getListIdDepartmentByParent(Long deptId) {
    Query query = entityManager.createNativeQuery("with recursive cte as" +
        "         ( select *  from   CAT_DEPARTMENT " +
        "                        where      CAT_DEPARTMENT.DEPT_ID = ?" +
        "                        union all" +
        "                        select     d.*" +
        "                         from       CAT_DEPARTMENT d" +
        "                         inner join cte" +
        "                               on d.PARENT_ID = cte.DEPT_ID)" +
        "                       select DEPT_ID as deptId  from cte");
    query.unwrap(NativeQuery.class).addScalar("deptId", LongType.INSTANCE);
    query.setParameter(1, deptId);
    return query.getResultList();
  }

  @Override
  public Long getCatDepartmentByUserSys(Long idUser) {
    Query query = entityManager.createNativeQuery("select sud.DEPT_ID from SYS_USERS as sud where sud.USER_ID=?");
    query.setParameter(1, idUser);
    List list = query.getResultList();
    if (list == null || list.size() == 0) {
      return 0L;
    }
    return ((BigInteger) query.getResultList().get(0)).longValue();
  }

  @Override
  public List<CatDepartmentDTO> findAllByParentId(Long deptId) {

    StringBuffer sb = new StringBuffer();
    if(deptId == null) {
      sb.append(" select DEPT_ID, DEPT_CODE, DEPT_NAME from CAT_DEPARTMENT where PARENT_ID is null");
    }else{
      sb.append(" select DEPT_ID, DEPT_CODE, DEPT_NAME from CAT_DEPARTMENT where PARENT_ID = ?1");
    }

    Query query = entityManager.createNativeQuery(sb.toString());
    if(deptId != null){
      query.setParameter(1, deptId);;
    }
    List<Object[]> objs = query.getResultList();

    List<CatDepartmentDTO> dtos = new ArrayList<>();
    if(objs != null && objs.size() > 0){
      for(Object[] obj: objs) {
        CatDepartmentDTO cat = new CatDepartmentDTO();
        cat.setDeptId(((BigInteger) obj[0]).longValue());
        cat.setDeptCode((String)obj[1]);
        cat.setDeptName((String)obj[2]);
        dtos.add(cat);
      }
    }
  return dtos;
  }

  @Override
  public String findDeptNameById(Long deptId) {
    if(deptId == null)
      return "";
    StringBuffer sb = new StringBuffer();
    sb.append(" select DEPT_NAME from CAT_DEPARTMENT where DEPT_ID = ?1");

    Query query = entityManager.createNativeQuery(sb.toString());
      query.setParameter(1, deptId);;
    List<Object> objs = query.getResultList();

    if(objs != null && objs.size() > 0){
      return (String)objs.get(0);
    }
    return "";
  }

  public void createDepartmentTree(List<CatDepartment> listDepartment,CatDepartmentDTO parent ){
    listDepartment.forEach(item -> {
      if(item.getParentId().compareTo(parent.getDeptId())==0){
        CatDepartmentDTO itemDTO = mapper.toDtoBean(item);
        parent.getListChild().add(itemDTO);
        itemDTO.setListChild(new ArrayList<>());
        createDepartmentTree(listDepartment,itemDTO);
      }
    });
  }

}
