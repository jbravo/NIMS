package com.viettel.nims.transmission.service;

import com.viettel.nims.transmission.model.view.ViewSysGridBO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SysGridViewService {

  ResponseEntity<?> getGridView(ViewSysGridBO viewSysGridBO);

  ResponseEntity<?> setGridView(List<ViewSysGridBO> viewSysGridBO, HttpServletRequest request);
}
