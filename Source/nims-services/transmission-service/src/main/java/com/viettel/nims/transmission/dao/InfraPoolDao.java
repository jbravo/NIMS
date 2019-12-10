package com.viettel.nims.transmission.dao;

import java.util.List;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.InfraPoolsBO;
import com.viettel.nims.transmission.model.view.ViewInfraPoolsBO;
import com.viettel.nims.transmission.model.view.ViewInfraSleevesBO;

/**
 * Created by ThieuNV on 08/23/2019.
 */
public interface InfraPoolDao {

	// Tim kiem co ban
	public FormResult findBasicPool(InfraPoolsBO infraPoolsBO);

	// Tim kiem nang cao
	public FormResult findAdvancePool(List<InfraPoolsBO> infraPoolsBO, Long userId);

  // Them moi
  Long savePool(InfraPoolsBO infraPoolsBO);

	// Them moi
	public Long getMaxNumberPool(Long path, Long userId);

	//
	public String checkNumberAndGetZ(String dept_TTT, Long number_YYYY);

	// Tim theo id
	public ViewInfraPoolsBO findPoolById(Long id);

	// Tim theo poolCode
	public ViewInfraPoolsBO findPoolByPoolCode(String poolCode);

	// Delete
	public int deletePool(Long id);

	// Count Sleeves in pool
	public List<ViewInfraSleevesBO> listSleevesInPool(Long poolId);

	// Get list laneCode cho popup
	public FormResult getLaneCodeHang(InfraPoolsBO infraPoolsBO);

	// Tim bo theo poolCode
	public InfraPoolsBO findPoolBOByPoolCode(String poolCode);

//  check be thuoc quyen cua user quan by
	public boolean checkPoolPermission(InfraPoolsBO bo, Long userId);

//	HopTQ check role be start
  public boolean checkRolePoolWithPoolCode(String poolCode, Long userId);
//HopTQ end
}

