package com.viettel.nims.commons.pool;

import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PoolInfoLogging {

  private List<CommonsPool2TargetSource> pools;

  @Autowired
  public PoolInfoLogging(List<CommonsPool2TargetSource> pools) {
    this.pools = pools;
  }

  // Periodic logging pools information each 5 minutes
  @Scheduled(fixedRate = 300000)
  public void logPoolSchedule() {
    pools.forEach(this::logPoolInfo);
  }

  private void logPoolInfo(CommonsPool2TargetSource pool) {
    log.info(
        "Pool of `{}` status: Active count = {} | Idle count = {} | "
            + "Max size = {} | Max wait = {} | Max idle = {} | Min idle = {} | "
            + "Min evictable idle time = {} ms | Time between eviction runs = {} ms",
        Objects.requireNonNull(pool.getTargetClass()).getCanonicalName(),
        pool.getActiveCount(),
        pool.getIdleCount(),
        pool.getMaxSize(),
        pool.getMaxWait(),
        pool.getMaxIdle(),
        pool.getMinIdle(),
        pool.getMinEvictableIdleTimeMillis(),
        pool.getTimeBetweenEvictionRunsMillis());
  }
}
