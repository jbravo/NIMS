package com.viettel.nims.optimalDesign.repository.custom;

import java.util.List;

/**
 * @author rabbit on 9/7/2019.
 */
public interface RepositoryCustomUtils {
  List<Object> getAllValueOfColumnByParentId(String tableName, String idColumnName, String parentColumnName, Long parentIds);

  void deleteAllInListId(String tableName, String idColumnName, List<Long> ids);

  void deleteAllByListParentId(String tableName, String parentColumnName, List<Long> parentIds);
}
