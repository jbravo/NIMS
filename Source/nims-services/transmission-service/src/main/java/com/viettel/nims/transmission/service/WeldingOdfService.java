package com.viettel.nims.transmission.service;

import com.viettel.nims.transmission.model.WeldingOdfBO;
import net.sf.json.JSONObject;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by VTN-PTPM-NV64 on 8/16/2019.
 */
public interface WeldingOdfService {

  ResponseEntity<?> getWeldingOdfList(Long id);

  ResponseEntity<?> getOdfCode(Long id);

  ResponseEntity<?> getCouplers(Long id);

  ResponseEntity<?> getLines(Long id);

  ResponseEntity<?> getCableCodes(Long id);

  ResponseEntity<?> getDestOdfs(Long id);

  ResponseEntity<?> getJointCouplers(Long id);

  @Transactional
  JSONObject deleteWeldingOdfs(List<WeldingOdfBO> weldingOdfs);

  @Transactional
  JSONObject saveWeldingOdf(WeldingOdfBO weldingOdf);

  @Transactional
  JSONObject updateWeldingOdf(WeldingOdfBO updatingDto);

  ByteArrayInputStream exportData(HashMap<String, Object> paramsMap, String langCode);
}
