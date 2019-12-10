package com.viettel.nims.transmission.commom;

import java.util.ArrayList;
import java.util.List;

import com.viettel.nims.transmission.model.InfraPoolsBO;
import com.viettel.nims.transmission.model.PillarsBO;

public class Constants {

	public static class EXCEL {
		public static final String REPORT_OUT = "report.out";
	}

	public static class DATE {
		public static final String YYYYMMDDHHMMSS = "YYYYMMDDHHMMSS";
		public static final String DEFAULT_FORMAT = "dd/MM/yyyy";
		public static final String YYYYMMDD = "yyyyMMdd";

	}

	public static class TEMPLATE_FILE {
		public static final String TEAMPLATE_INFRASTATION = "/templates/teamplate_import_nha_tram.xlsx";
		public static final String TEAMPLATE_PILLAR = "/templates/Temp_IMP_Cot.xlsx";
    public static final String TEAMPLATE_POOL = "/templates/temp_imp_be.xlsx";
		public static final String TEAMPLATE_CABLEINSTATION = "/templates/teamplate_import_doan_cap_trong_nha_tram.xlsx";
    public static final String TEAMPLATE_SLEEVE = "/templates/temp_imp_mangxong.xlsx";
    public static final String TEAMPLATE_SLEEVE_INSERT = "/templates/temp_imp_mangxong_insert.xlsx";
	}


}
