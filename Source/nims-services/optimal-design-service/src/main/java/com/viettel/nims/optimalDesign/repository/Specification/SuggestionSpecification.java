package com.viettel.nims.optimalDesign.repository.Specification;


import antlr.StringUtils;
import com.viettel.nims.optimalDesign.dto.SuggestionSearchDTO;
import com.viettel.nims.optimalDesign.entity.Suggestion;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SuggestionSpecification {
  public static Specification<Suggestion> doSearch(SuggestionSearchDTO suggestionSearchDTO, List<Long> deptId, List<String> userSearch) {
    return new Specification<Suggestion>() {

      @Override
      public Predicate toPredicate(Root<Suggestion> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Collection<Predicate> predicates = new ArrayList<>();
          predicates.add(cb.equal(root.get("rowStatus"),1));
          if (suggestionSearchDTO.getSuggestCode() != null && !suggestionSearchDTO.getSuggestCode().trim().equalsIgnoreCase("")) {
            predicates.add(cb.like(cb.upper(root.<String>get("suggestCode")), "%"+suggestionSearchDTO.getSuggestCode().trim().toUpperCase()+"%"));
          }
          predicates.add(root.get("deptId").in(deptId));
          if (suggestionSearchDTO.getSuggestType() != null) {
            predicates.add(cb.equal(root.get("suggestType"), suggestionSearchDTO.getSuggestType()));
          }
          if (suggestionSearchDTO.getSuggestStatus() != null) {
            predicates.add(cb.equal(root.get("suggestStatus"), suggestionSearchDTO.getSuggestStatus()));
          }
          if(userSearch.isEmpty()){
            predicates.add(cb.equal(cb.upper(root.get("userName")),suggestionSearchDTO.getUserName().trim().toUpperCase()));
          } else
            predicates.add(cb.upper(root.get("userName")).in(userSearch));
          if (suggestionSearchDTO.getBeforeDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(cb.function("date_format", String.class, root.get("createTime"), cb.literal("%Y-%m-%d")), suggestionSearchDTO.getBeforeDate()));
          }
          if (suggestionSearchDTO.getAfterDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(cb.function("date_format", String.class, root.get("createTime"), cb.literal("%Y-%m-%d")), suggestionSearchDTO.getAfterDate()));
          }
        return predicates.size() == 0 ? null : cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    };
  }
}

