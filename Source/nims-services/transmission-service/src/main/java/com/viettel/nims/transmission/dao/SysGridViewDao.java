package com.viettel.nims.transmission.dao;

import com.viettel.nims.transmission.model.SysGridViewColumnBO;
import com.viettel.nims.transmission.model.SysGridViewUserBO;
import com.viettel.nims.transmission.model.view.ViewSysGridBO;

import java.util.List;

public interface SysGridViewDao {

  List<ViewSysGridBO> getGridView(ViewSysGridBO viewSysGridBO);

  List<SysGridViewUserBO> getAllColumsByUser(Long userId, Long gridId);

  List<SysGridViewColumnBO> getAllColumns(Long gridId);

  void saveSysGridUser(SysGridViewUserBO sysGridViewUserBO);

  Long getGridIdByName(String gridName);

}
