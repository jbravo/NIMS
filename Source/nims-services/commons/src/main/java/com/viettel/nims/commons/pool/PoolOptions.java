package com.viettel.nims.commons.pool;

import org.springframework.aop.target.CommonsPool2TargetSource;

public class PoolOptions {

  public static void setOptions(CommonsPool2TargetSource pool, int maxSize, int minIdle,
      int maxIdle, long maxWait, long minEvictableIdleTimeMillis) {
    pool.setMaxSize(maxSize);
    pool.setMinIdle(minIdle);
    pool.setMaxIdle(maxIdle);
    pool.setMaxWait(maxWait);
    pool.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
  }
}
