package com.viettel.dialect;

import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

/**
 * Created by SangNV1 on 5/25/2019.
 */
public class PostgreSQL95JsonDialect
    extends PostgreSQL94Dialect {

  public PostgreSQL95JsonDialect() {
    super();
    this.registerHibernateType(
        Types.OTHER, JsonNodeBinaryType.class.getName()
    );
  }
}
