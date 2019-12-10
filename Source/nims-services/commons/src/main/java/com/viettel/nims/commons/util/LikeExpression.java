package com.viettel.nims.commons.util;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.TypedValue;

public class LikeExpression implements Criterion {

  private final String propertyName;
  private final String value;
  private final Character escapeChar;

  LikeExpression(
      String propertyName,
      String value,
      MatchMode matchMode) {
    this(propertyName, matchMode.toMatchString(value
        .replaceAll("!", "!!")
        .replaceAll("%", "!%")
        .replaceAll("_", "!_")), '!');
  }

  private LikeExpression(
      String propertyName,
      String value,
      Character escapeChar) {
    this.propertyName = propertyName;
    this.value = value;
    this.escapeChar = escapeChar;
  }

  public String toSqlString(
      Criteria criteria,
      CriteriaQuery criteriaQuery) throws HibernateException {
    Dialect dialect = criteriaQuery.getFactory().getDialect();
    String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);
    if (columns.length != 1) {
      throw new HibernateException("Like may only be used with single-column properties");
    }
    String lhs = lhs(dialect, columns[0]);
    return lhs + " like ?" + (escapeChar == null ? "" : " escape ?");

  }

  public TypedValue[] getTypedValues(
      Criteria criteria,
      CriteriaQuery criteriaQuery) throws HibernateException {
    return new TypedValue[]{
        criteriaQuery.getTypedValue(criteria, propertyName, typedValue(value)),
        criteriaQuery.getTypedValue(criteria, propertyName, escapeChar.toString())
    };
  }

  protected String lhs(Dialect dialect, String column) {
    return column;
  }

  protected String typedValue(String value) {
    return value;
  }
}
