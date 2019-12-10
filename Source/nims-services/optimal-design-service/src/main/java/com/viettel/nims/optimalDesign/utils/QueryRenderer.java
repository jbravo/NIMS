package com.viettel.nims.optimalDesign.utils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rabbit on 9/7/2019.
 */
public class QueryRenderer {

  /**
   * Lay tat ca column cua cac dong trong db theo reference parent id
   *
   * @param tableName
   * @param selectColumnName
   * @param parentColumnName
   * @param parentColumnValue
   * @return sql
   * @author quang dv
   */
  public static String getAllValueOfColumnByParentId(String tableName, String selectColumnName, String parentColumnName, Long parentColumnValue) {
    String sql = "SELECT " + selectColumnName + " FROM " + tableName;
    sql += " WHERE " + parentColumnName + " = " + parentColumnValue;

    return sql;
  }

  /**
   * Xoa tat ca cac dong voi dieu kien id trong list
   * Mục dich: Xoa cac dong nam trong danh sach id
   *
   * @param tableName
   * @param idColumnName
   * @param ids
   * @return sql
   * @author quang dv
   */
  public static String deleteAllInListIds(String tableName, String idColumnName, List<Long> ids) {
    String sql = "DELETE FROM " + tableName;

    if (ids != null) {
      String idsStr = ids.stream().map(v -> String.valueOf(v)).collect(Collectors.joining(","));
      sql += " WHERE " + idColumnName + " IN (" + idsStr + ")";
    }

    return sql;
  }

  public static String deleteAllByListParentId(String tableName, String parentColumnName, List<Long> parentIds) {
    String sql = "DELETE FROM " + tableName;

    if (parentIds != null) {
      String idsStr = parentIds.stream().map(v -> String.valueOf(v)).collect(Collectors.joining(","));
      sql += " WHERE " + parentColumnName + " IN (" + idsStr + ")";
    }

    return sql;
  }
}
