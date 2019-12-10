package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.CatOdfTypeBO;

import java.util.List;

public interface CatOdfTypeDao {

  public CatOdfTypeBO getOdfTypeByOdfTypeId(Long odfTypeId);
  
  public List<CatOdfTypeBO> getAllCatOdfType();

  public CatOdfTypeBO getOdfTypeByCode(String odfTypeCode);
}
