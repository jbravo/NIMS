package com.viettel.nims.commons.service;


import com.viettel.nims.commons.model.SysUserBO;
import com.viettel.nims.commons.service.base.GenericDaoImpl;
import com.viettel.nims.commons.service.base.GenericDaoService;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Service
public class SysUserServiceImpl extends GenericDaoImpl<SysUserBO, Long>
    implements GenericDaoService<SysUserBO, Long>, Serializable {

}
