package com.viettel.nims.optimalDesign.repository.custom;

import com.viettel.nims.optimalDesign.utils.QueryRenderer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rabbit on 9/7/2019.
 */


@Repository
public class RepositoryCustomUtilsImpl implements RepositoryCustomUtils{

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<Object> getAllValueOfColumnByParentId(String tableName, String columnSelectName, String parentColumnName, Long parentIds) {
    Query query = entityManager.createNativeQuery(QueryRenderer.getAllValueOfColumnByParentId(tableName, columnSelectName, parentColumnName, parentIds));
    return query.getResultList();
  }

  @Override
  public void deleteAllInListId(String tableName, String idColumnName, List<Long> ids) {
    Query query = entityManager.createNativeQuery(QueryRenderer.deleteAllInListIds(tableName, idColumnName, ids));
    query.executeUpdate();
  }

  @Override
  public void deleteAllByListParentId(String tableName, String parentColumnName, List<Long> parentIds) {
    Query query = entityManager.createNativeQuery(QueryRenderer.deleteAllByListParentId(tableName, parentColumnName, parentIds));
    query.executeUpdate();
  }
}
