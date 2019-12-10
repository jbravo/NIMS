package com.viettel.nims.commons.util;

import java.util.ArrayList;
import java.util.List;


public class FormResult extends PaginationDTO {
	List<?> listData = new ArrayList<>();

	public List<?> getListData() {
		return listData;
	}

	public void setListData(List<?> list) {
		this.listData = list;
	}

}
