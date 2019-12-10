package com.viettel.nims.transmission.dao;

import com.viettel.nims.commons.util.FormResult;
import com.viettel.nims.transmission.model.CntCouplerToCouplerBO;
import com.viettel.nims.transmission.model.InfraOdfBO;
import com.viettel.nims.transmission.model.InfraStationsBO;

import com.viettel.nims.transmission.model.view.ViewInfraOdfBO;
import com.viettel.nims.transmission.model.view.ViewODFCableBO;
import com.viettel.nims.transmission.model.view.ViewOdfLaneBO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

/**
 * InfraOdfDao
 * Version 1.0
 * Date: 08-23-2019
 * Copyright
 * Modification Logs:
 * DATE						AUTHOR				DESCRIPTION
 * -----------------------------------------------------------------------
 * 08-23-2019				HungNV				Create
 */
public interface InfraOdfDao {

	// Search Advance
	public FormResult findAdvanceOdf(ViewInfraOdfBO viewInfraOdfBO, Long userId);

	// Add Odf
	public Long saveOrUpdateOdf(InfraOdfBO infraOdfBO);

	// Get Max ODF_ID
	public Object getMaxOdfId();

	// Search Odf with Id
	public ViewInfraOdfBO findOdfById(Long id);

	// Get data form search
	public FormResult getDataFormSearchAdvance();

	// Delete odf
	public int deleteOdf(Long odfId);

	// check odf id reference to coupler to line
	public String countRecordRefCouplerToLine(Long odfId);

	// check odf id reference to coupler to line
	public String countRecordRefCouplerToCoupler(Long odfId);

	// get all odf
	public List<ViewInfraOdfBO> getAllOdf(Long stationId,Long userId);

  public List getStationByOdfCodes(String odfCode, String destOdfCode);

	// get all stations
	public List<InfraStationsBO> getAllStations();

	// find odf by station id
	public List<ViewInfraOdfBO> findOdfByStationId(Long id);

	// Get Max ODF Index
	public List<ViewInfraOdfBO> getMaxOdfIndexByStationId(Long stationId);

	// get coupler no ref
	public ArrayList<CntCouplerToCouplerBO> getCouplerNoRef(Long odfId);

	// get cable code ref
	public ArrayList<ViewODFCableBO> getCableCodeRef(Long odfId);

	// get cable code ref
	public String getOdfCodeById(Long odfId);

	// get odf row status
	public Integer getOdfRowStatus(Long odfId);

	// get ODf by code
	public ViewInfraOdfBO findOdfByCode(String code);

	// get ODf by code
	public InfraOdfBO selectOdfByCode(String code);

	// get ODf lane by odf code
	public List<ViewOdfLaneBO> getOdfLaneByOdfId(Long odfId);

	/** */
	public void setStatusCoupler(Long odf, Long coupler, int statuz);

	/** */
	public void setStatusCableLine(Long cable, Long line, int statuz);

	// search ODF Lane
	public List<ViewOdfLaneBO> getOdfLaneBySearch(ViewOdfLaneBO viewOdfLaneBO);
}
