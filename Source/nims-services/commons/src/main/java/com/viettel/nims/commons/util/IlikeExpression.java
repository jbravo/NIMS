package com.viettel.nims.commons.util;

import org.hibernate.criterion.MatchMode;
import org.hibernate.dialect.Dialect;

public class IlikeExpression extends LikeExpression {

  protected IlikeExpression(
      String propertyName,
      String value,
      MatchMode matchMode) {
    super(propertyName, value, matchMode);
  }

  @Override
  protected String lhs(Dialect dialect, String column) {
    return dialect.getLowercaseFunction() + '(' + column + ')';
  }

  @Override
  protected String typedValue(String value) {
    return super.typedValue(value).toLowerCase();
  }
}
