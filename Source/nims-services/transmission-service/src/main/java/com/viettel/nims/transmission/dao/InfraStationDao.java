package com.viettel.nims.transmission.dao;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.DocumentBO;
import com.viettel.nims.transmission.model.InfraStationsBO;
import com.viettel.nims.transmission.model.view.ViewInfraStationsBO;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by VTN-PTPM-NV64 on 7/31/2019.
 */
@Repository
public interface InfraStationDao {

	// Tim kiem co ban
	FormResult findBasicStation(InfraStationsBO infraStationsBO);

	// Tim kiem nang cao
	FormResult findAdvanceStation(InfraStationsBO infraStationsBO, Long userId);

	// Them moi tram
	Long saveStation(InfraStationsBO infraStationsBO);

	// Tim tram theo id
	ViewInfraStationsBO findStationById(Long id);

	// Xoa nha tram
	int delete(Long id);

	// Tim tram theo code
	InfraStationsBO findStationByCode(String code, Long userId);

	//
	boolean checkStationByUserPermission(String code, Long userId);

	// Them moi document
	Long saveDocument(DocumentBO documentBO);

	// get document
	DocumentBO getDocument(Long stationId , int type);

}
