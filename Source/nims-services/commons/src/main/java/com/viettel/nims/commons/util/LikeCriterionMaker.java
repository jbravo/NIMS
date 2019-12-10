package com.viettel.nims.commons.util;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;


public class LikeCriterionMaker {

  public static Criterion ilike(String propertyName, String value, MatchMode matchMode) {
    return new IlikeExpression(propertyName, value, matchMode);
  }
}
