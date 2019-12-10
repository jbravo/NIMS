package com.viettel.nims.optimalDesign.repository.custom;

import com.viettel.nims.optimalDesign.dto.commons.ParamDTO;
import com.viettel.nims.optimalDesign.dto.SuggestionSearchDTO;
import com.viettel.nims.optimalDesign.entity.CatDepartment;
import com.viettel.nims.optimalDesign.entity.Suggestion;
import com.viettel.nims.optimalDesign.repository.Specification.SuggestionSpecification;
import com.viettel.nims.optimalDesign.repository.SysUsersRepository;
import com.viettel.nims.optimalDesign.service.CatDepartmentService;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class SuggestionRepositoryCustomImpl implements SuggestionRepositoryCustom {

  @PersistenceContext
  EntityManager entityManager;

  @Autowired
  private CatDepartmentService catDepartmentService;

  @Autowired
  private SysUsersRepository sysUsersRepository;


  private Logger logger = LoggerFactory.getLogger(SuggestionRepositoryCustom.class);

  @Override
  public List<Suggestion> findAllSuggestion(SuggestionSearchDTO input) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Suggestion> cq = cb.createQuery(Suggestion.class);
    Root<Suggestion> root = cq.from(Suggestion.class);
    List<Long> deptId = input.getDeptId() == null ? catDepartmentService.getListDepartment(input.getUserName().trim().toUpperCase()) : catDepartmentService.getListDepartment(input.getDeptId().longValue(), input.getUserName().trim().toUpperCase());
    if (deptId == null || deptId.isEmpty())
      return new ArrayList<>();
    List<String> userSearchs = new ArrayList<>();
    if (input.getUserSearch() != null && !"".equals(input.getUserSearch().trim()))
      userSearchs = sysUsersRepository.getAllUserLikeInput(input.getUserSearch(), deptId);
    if(userSearchs.size() > 1){
      userSearchs.removeIf(username -> {
        return  username.equalsIgnoreCase(input.getUserName().trim()) && input.getUserSearch() != null && !input.getUserName().trim().contains(input.getUserSearch().trim());
      });
    }
    Join<Suggestion, CatDepartment> join =  root.join("catDepartment", JoinType.INNER);
    Predicate predicate = SuggestionSpecification.doSearch(input, deptId, userSearchs).toPredicate(root, cq, cb);
    cq.multiselect(root.get("suggestId").alias("suggestId"), root.get("suggestType").alias("suggestType"), root.get("suggestCode").alias("suggestCode"),
        join.get("deptName").alias("deptName"), root.get("suggestStatus").alias("suggestStatus"), root.get("userName").alias("userName"),
        root.get("createTime").alias("createTime"));
    if (predicate != null)
      cq.where(predicate);
    cq.orderBy(cb.desc(root.get("createTime")));
    TypedQuery<Suggestion> typedQuery = entityManager.createQuery(cq);
//    typedQuery.unwrap(TypedQuery.class).
//        .addScalar("suggestId", LongType.INSTANCE)
//        .addScalar("suggestType", IntegerType.INSTANCE)
//        .addScalar("suggestCode", StringType.INSTANCE)
//        .addScalar("deptName", StringType.INSTANCE)
//        .addScalar("suggestStatus", IntegerType.INSTANCE)
//        .addScalar("userName", StringType.INSTANCE)
//        .addScalar("createTime", DateType.INSTANCE)
//        .setResultTransformer(Transformers.aliasToBean(Suggestion.class));
    List<Suggestion> rs = typedQuery.getResultList();
    return rs;
  }

  public List<SuggestionSearchDTO> findAllSuggestionSearch(SuggestionSearchDTO searchDTO){
      List<ParamDTO> params = new ArrayList<>();
      StringBuilder sb = new StringBuilder();
      sb.append("select s.SUGGEST_ID as suggestId, s.CREATE_TIME as createTime, " +
                " s.DEPT_ID as deptId, s.ROW_STATUS as rowStatus, s.SUGGEST_CODE as suggestCode, " +
                " s.SUGGEST_STATUS as suggestStatus, s.SUGGEST_TYPE as suggestType, s.UPDATE_TIME as updateTime, " +
                " s.USER_NAME as userName, cd.DEPT_NAME as deptName " +
                " from SUGGESTION s , CAT_DEPARTMENT cd " +
                " where 1 = 1 " +
                " and s.DEPT_ID = cd.DEPT_ID " +
                " and s.ROW_STATUS = 1 ");

    List<Long> deptIds = searchDTO.getDeptId() == null ? catDepartmentService.getListDepartment(searchDTO.getUserName().trim().toUpperCase()) : catDepartmentService.getListDepartment(searchDTO.getDeptId().longValue(), searchDTO.getUserName().trim().toUpperCase());

    List<String> userSearchs = new ArrayList<>();

    if(searchDTO.getUserSearch() != null && !"".equals(searchDTO.getUserSearch().trim()))
      userSearchs = sysUsersRepository.getAllUserLikeInput(searchDTO.getUserSearch().trim(),deptIds);
    if(userSearchs != null && userSearchs.size() > 0) {
      userSearchs.removeIf(username -> {
        return  username.equalsIgnoreCase(searchDTO.getUserName().trim()) && searchDTO.getUserSearch() != null && !searchDTO.getUserName().trim().toUpperCase().contains(searchDTO.getUserSearch().trim().toUpperCase());
      });
    }

    if(deptIds != null && deptIds.size() > 0){
//      String idsStr = deptIds.stream().map(v -> String.valueOf(v)).collect(Collectors.joining(","));
      sb.append("  and s.DEPT_ID in (:deptIds)");//.append(idsStr).append(") ");
      params.add(new ParamDTO("deptIds", deptIds));
    }
//
    if(userSearchs != null && userSearchs.size() > 0) {

      sb.append(" and s.USER_NAME in (:userNames) ");
      params.add(new ParamDTO("userNames", userSearchs));

    }else if (searchDTO.getUserName() != null && !"".equals(searchDTO.getUserName())) {
        sb.append(" and upper(s.USER_NAME) = upper(:userName) ");
        params.add(new ParamDTO("userName", searchDTO.getUserName().trim()));

    }

    if(searchDTO.getSuggestStatus() != null){
      sb.append(" and s.SUGGEST_STATUS = :suggestStatus ");
      params.add(new ParamDTO("suggestStatus", searchDTO.getSuggestStatus()));
    }

    if(searchDTO.getSuggestCode() != null && !"".equals(searchDTO.getSuggestCode())){
      sb.append(" and upper(s.SUGGEST_CODE) like upper(:suggestCode) ");
      params.add(new ParamDTO("suggestCode", "%"+searchDTO.getSuggestCode().trim()+"%"));
    }

    if(searchDTO.getSuggestType() != null){
      sb.append(" and s.SUGGEST_TYPE = :suggestType ");
      params.add(new ParamDTO("suggestType", searchDTO.getSuggestType()));
    }

    if(searchDTO.getBeforeDate() != null && !"".equals(searchDTO.getBeforeDate().trim())){
      sb.append(" and date_format(s.CREATE_TIME, '%Y-%m-%d') >= :fromDate ");
      params.add(new ParamDTO("fromDate", searchDTO.getBeforeDate()));
    }

    if(searchDTO.getAfterDate() != null && !"".equals(searchDTO.getAfterDate().trim())){
      sb.append(" and date_format(s.CREATE_TIME, '%Y-%m-%d') <= :toDate ");
      params.add(new ParamDTO("toDate", searchDTO.getAfterDate()));
    }

    sb.append(" order by s.CREATE_TIME DESC  ");

    Query query = entityManager.createNativeQuery(sb.toString());

    for(ParamDTO paramDTO: params){
      query.setParameter(paramDTO.getName(), paramDTO.getValue());
    }
    SQLQuery nativeQuery = query.unwrap(SQLQuery.class);
    nativeQuery
        .addScalar("suggestId", LongType.INSTANCE)
        .addScalar("createTime", TimestampType.INSTANCE)
        .addScalar("deptId", IntegerType.INSTANCE)
        .addScalar("rowStatus", IntegerType.INSTANCE)
        .addScalar("suggestCode", StringType.INSTANCE)
        .addScalar("suggestStatus", IntegerType.INSTANCE)
        .addScalar("suggestType", IntegerType.INSTANCE)
        .addScalar("updateTime", TimestampType.INSTANCE)
        .addScalar("userName", StringType.INSTANCE)
        .addScalar("deptName", StringType.INSTANCE).setResultTransformer(Transformers.aliasToBean(SuggestionSearchDTO.class));

    List<SuggestionSearchDTO> lst = nativeQuery.list();
//    List<Object[]> lst = query.getResultList();
    return lst;
  }
}
