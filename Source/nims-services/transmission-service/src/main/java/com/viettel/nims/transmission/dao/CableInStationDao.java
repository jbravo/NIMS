package com.viettel.nims.transmission.dao;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.CableInStationBO;
import com.viettel.nims.transmission.model.CatOpticalCableTypeBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewCableInStationBO;

/**
 * Created by HieuDT on 08/26/2019.
 */
public interface CableInStationDao {
	// Tim kiem co ban
	FormResult findBasicCable(CableInStationBO cableInStationBO);

	// Tim kiem nang cao
	FormResult findAdvanceCable(CableInStationBO cableInStationBO, Long userId);

	// Them moi doan cap
	void saveCable(CableInStationBO cableInStationBO);

	// Tim cap theo id
	ViewCableInStationBO findCableById(Long id);

	// Xoa cap trong nha tram
	int delete(Long id);

	// List loai cap
	FormResult findListCableType();

	// List ODF dau
	FormResult findListODFFist(Long stationId, Long userId);

	// List ODF cuoi
	FormResult findListODFEnd(Long stationId, Long userId);

	// get stationcode
	FormResult getDataFormSearchAdvance(InfraStationsBO infraStationsBO);

	// Lay chi so doan cap max
	public String getCableIndex(Long sourceId, Long destId);

	// check isexitcode
	Boolean isExitByCode(CableInStationBO cableInStationBO);

	// check ODF da duoc dau noi
	Boolean checkWeldODFByCable(CableInStationBO cableInStationBO);

	// Tim loai cap theo code
	CatOpticalCableTypeBO findCableTypeByCode(String code);

	// Tim cap theo code
	CableInStationBO findCableByCode(String code);

	// get all ODF
	FormResult getAllODF(Long stationId, Long userId);

}
