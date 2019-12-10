package com.viettel.nims.transmission.dao;
import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.ViewHangCableBO;
import com.viettel.nims.transmission.model.view.ViewInfraSleevesBO;

import java.util.List;

/**
 * Created by VAN-BA 8/23/2019.
 */
public interface PillarDao {

  // Tim kiem co ban
//  public FormResult findBasicPillar(PillarsBO pillarsBO);

  // Tim kiem nang cao
  public FormResult findAdvancePillar(PillarsBO pillarsBO, Long userId);

  // Lay danh sach cot cho mang xong
  public FormResult getPillarList(PillarsBO pillarsBO, Long userId) throws NoSuchFieldException;

  // Them moi cot
  public Long savePillar(PillarsBO pillarsBO);

  // Tim id cot theo ma cot
  public PillarsBO findPillarById(Long id);

  // get list pillar type code
  public FormResult getPillarTypeCodeList();

  // get list owner
//  public FormResult getOwnerName();

  //Get list laneCode
  public FormResult getLaneCodeList(InfraCableLanesBO entity);

  //Get list laneCode cho popup
  public FormResult getListLaneCode(InfraCableLanesBO entity, Long userId);

  //Get list danh sach tuyen cap vat qua cot
  public FormResult getListLaneCodeHangPillar(PillarsBO entity, Long userId);


  //Get tuyen cap cho cot/be chua mang xong
//  public FormResult getListLaneCodeForSleeve(InfraCableLanesBO entity, Long userId);

  // Lay chi so cot max
  public String getPillarIndex(String laneCode);
  //Kiem tra trung lap ma cot
  public Boolean isExitByCode(PillarsBO pillarsBO);
  //Kiem tra cot chua mang xong
  public List<ViewInfraSleevesBO> listSleevesInPillar(Long pillarId);
  //Xoa cot
  public int deletePillar(Long id);
  //Kiem tra cot da vat cap
  public List<ViewHangCableBO> listPillarHangCable(Long pillarId);
  //Kiem tra trung lap cot
  public boolean checkExitPillarCode(String pillarCode, Long pillarId);
  //----------kienNT-----------
  //tim cot theo code
  PillarsBO findPillarByCode(String code);

  //---------KienNt------------

  // HopTQ phan quyen Cot start
  public boolean checkRolePillarWithPillarCode(String pillarCode, Long userId);
  // HopTQ end

}
