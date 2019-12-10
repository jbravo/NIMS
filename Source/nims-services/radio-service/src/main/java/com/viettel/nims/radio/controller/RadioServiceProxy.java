package com.viettel.nims.radio.controller;

import com.viettel.nims.radio.model.ActionLogBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "commons-service")
public interface RadioServiceProxy {

  @PostMapping(value = "/commons/actionLog/saveOrUpdate")
  public String saveOrUpdate(@RequestBody ActionLogBO actionLog);

  @GetMapping(value = "/commons/department/{id}")
  public String findByDeptId(@PathVariable Long id);
}
