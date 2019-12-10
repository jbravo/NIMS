package com.viettel.nims.geo.service.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface GenericDaoService<T, PK extends Serializable> {

  public T findById(PK id);

  public void delete(T object);

  public void delete(List<T> objects);

  public T saveOrUpdate(T object) throws Exception;

  public PK save(T object) throws Exception;

  public void saveOrUpdate(List<T> objects) throws Exception;

  public int count();

  public int count(Map<String, Object> filters, Map<String, Object> sqlRes) throws Exception;

  public List<T> findList() throws Exception;

  public List<T> findList(int first, int pageSize, Map<String, Object> filters,
      Map<String, Object> sqlRes, LinkedHashMap<String, String> orders) throws Exception;

}
