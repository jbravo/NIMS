package com.viettel.nims.transmission.dao;

import java.util.List;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.CableInSleeveResponDto;
import com.viettel.nims.transmission.model.view.ViewWeldSleeveBO;

public interface WeldSleeveDao {

	// tim kiem co ban
	public FormResult findBasicWeld(ViewWeldSleeveBO viewWeldSleeveBO);

	// Save moi han
	public String saveWeldSleeve(WeldSleeveBO weldSleeveBO);

	// Load danh sach soi trong cap
	public List<CableInSleeveResponDto> getListCableId(long cableTypeId, long sleeveId, long cableId);

	// Load cap den, cap di

	public FormResult getListYarnCable(long cableTypeId, long sleeveId, long cableId);

	// Get SleeveCode By Id
	public InfraSleevesBO findSleeveCodeById(Long id);

	// xoa nha tram
	public int delete(WeldSleeveBO.PK id);

	public WeldSleeveBO findById(WeldSleeveBO.PK id);

	public List<WeldSleeveBO> findAll();

	public int deleteByFiveField(List<WeldSleeveBO.PK> weldSleeveBOS);

	public FormResult findWeldSleeveBO();

	public FormResult findWeldSleeveTestBO();

	/**
	 * @author ThieuNV Check Han noi mang xong theo id.
	 */
	public boolean checkweldingSleeveById(Long id);

	public boolean checkSleeveIdLaneId(ViewWeldSleeveBO viewWeldSleeveBO);

	//lay thong tin cap theo Id infraCableBO
	public InfraCablesBO getCableById(Long cableId);

	//update thong tin moi han
	public String updateWeldSleeve(WeldSleeveBO weldSleeveBO);

	public String updateInfraCableLane(Long landeId);

	//lay laneId theo cableId
  public CntCableLaneBO findLaneIdByCableId(Long cableId);
}
