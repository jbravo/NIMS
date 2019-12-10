package com.viettel.nims.commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CommonsServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CommonsServiceApplication.class, args);
  }
}
