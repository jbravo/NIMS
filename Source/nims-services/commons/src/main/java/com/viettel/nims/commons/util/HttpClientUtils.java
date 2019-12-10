package com.viettel.nims.commons.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HttpClientUtils {


  public RequestConfig getRequestConfig() {
    return RequestConfig.custom()
        .setSocketTimeout(30000)
        .setConnectTimeout(30000)
        .setConnectionRequestTimeout(60000)
        .build();
  }

  public PoolingHttpClientConnectionManager getConnectionManager() {
    PlainConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory
        .getSocketFactory();
    Registry<ConnectionSocketFactory> connSocketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
        .register("http", plainSocketFactory)
        .build();
    PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
        connSocketFactoryRegistry);
    connManager.setMaxTotal(20);
    connManager.setDefaultMaxPerRoute(20);
    return connManager;
  }
}
