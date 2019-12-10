package com.viettel.nims.transmission.dao;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.InfraCableLanesBO;
import com.viettel.nims.transmission.model.InfraSleevesBO;
import com.viettel.nims.transmission.model.WeldSleeveBO;
import com.viettel.nims.transmission.model.view.ViewInfraSleevesBO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface InfraSleeveDao {

  // Tim kiem co ban
  public FormResult findBasicSleeve(InfraSleevesBO infraSleevesBO, Long userId);

  // Tim kiem nang cao
  public FormResult findAdvanceSleeve(InfraSleevesBO infraSleevesBO, Long userId);

  // Them moi mang xong
  public void saveSleeve(InfraSleevesBO infraSleevesBO);

  // tim mang xong theo id
  public InfraSleevesBO findSleeveById(Long id);

  // Xoa mang xong
  public int delete(Long id);

  // Get list ma loai mang xong
  public FormResult getSleeveTypeList();

  // search of autocomplete
  public FormResult getDataFormSearchAdvance(Long id);

  // tim mang xong da duoc han noi
  public List<InfraSleevesBO> findWeldSleeveBySleeveId(Long id);

  // Nha san xuat
  FormResult getVendorList();

  // tim tuyen theo laneCode
  FormResult findLaneListByCode(String laneCode);

  // check trung ma mang xong
  public List<InfraSleevesBO> findListSleeveBySleeveCode(String sleeveCode, Long sleeveId);

  // get list mang xong voi quyen user
  public List<InfraSleevesBO> getListSleeveBySleeveCode(String sleeveCode, Long userId);

  //get list mang xong
  FormResult listSleeve(Long userId);

  ViewInfraSleevesBO findViewSleeveById(Long id);

  public List<InfraSleevesBO> findSleeveByLaneCode(String laneCode);

  public InfraSleevesBO findSleeveBySleeveCode(String sleeveCode);
}
