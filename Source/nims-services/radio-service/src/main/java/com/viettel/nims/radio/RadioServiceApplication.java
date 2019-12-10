package com.viettel.nims.radio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RadioServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(RadioServiceApplication.class, args);
  }
}
