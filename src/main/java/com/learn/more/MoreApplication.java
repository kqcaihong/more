package com.learn.more;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(MoreApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

//  @Bean
//  public RestTemplate restTemplate(@Qualifier("simpleClientHttpRequestFactory") ClientHttpRequestFactory factory) {
//    return new RestTemplate(factory);
//  }

  @Bean
  public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    // HttpURLConnection只支持设置这两个超时时间
    factory.setConnectTimeout(10000);
    factory.setReadTimeout(30000);
    return factory;
  }

  // 当使用okHttp3作为RestTemplate底层Http Client时
  @Bean
  public ClientHttpRequestFactory okHttp3ClientHttpRequestFactory() {
    OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
    factory.setConnectTimeout(10000);
    factory.setReadTimeout(30000);
    factory.setWriteTimeout(30000);
    return factory;
  }
}
