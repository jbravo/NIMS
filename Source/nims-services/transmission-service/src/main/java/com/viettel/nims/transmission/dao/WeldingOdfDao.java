package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.*;
import com.viettel.nims.transmission.model.view.ViewWeldingOdfBO;

import java.util.List;

/**
 * Created by BINHNV on 7/31/2019.
 */
public interface WeldingOdfDao {

  List<ViewWeldingOdfBO> selectAllWeldingOdf(Long id);

  List<ViewWeldingOdfBO> selectWeldingOdfs(Long id, List<Long> couplers);

  ViewWeldingOdfBO findSelectedWeldingOdf(Long id, Long couplerNo, Integer odfConnectType);

  String selectOdfCodeById(Long id);

  List selectCouplerNos(Long id);

  List selectLineNos(Long id);

  List<CableBO> selectCableCodes(Long id);

  List<DestOdfBO> selectDestOdfs(Long id);

  List<JointCouplersBO> selectJointCouplerNos(Long id);

  int deleteWeldingOdfs(List<WeldingOdfBO> weldingOdfs);

  void insertWeldingOdf(WeldingOdfBO weldingOdf);

  void updateWeldingOdf(WeldingOdfBO updatingDto);

  void executeSaveWeldingODf(CntCouplerToLineBO cntCouplerToLineBO);

  void executeSaveJointODf(CntCouplerToCouplerBO cntCouplerToCouplerBO);

  CntCouplerToLineBO selectWeldingOdfByCode(Long odfId, Integer couplerNo, Long cableId, Integer lineNo);
}
