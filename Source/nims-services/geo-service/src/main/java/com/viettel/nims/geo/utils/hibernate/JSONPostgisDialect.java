package com.viettel.nims.geo.utils.hibernate;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.spatial.dialect.postgis.PostgisDialect;
import org.hibernate.type.StandardBasicTypes;

public class JSONPostgisDialect extends PostgisDialect {

  @Override
  protected void registerTypesAndFunctions() {
    super.registerTypesAndFunctions();
    registerColumnType(JSONTypeDescriptor.INSTANCE.getSqlType(), "jsonb");
    registerFunction("extract",
        new StandardSQLFunction("jsonb_extract_path_text", StandardBasicTypes.STRING));
  }
}
