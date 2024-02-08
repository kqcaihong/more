package com.learn.more.httpclient;

import org.springframework.web.reactive.function.client.WebClient;

public class SpringWebClient {

  public static void main(String[] args) {
    get("http://www.baidu.com");
  }

  public static void get(String url) {
    WebClient webClient = WebClient.create();
    webClient.get().uri(url).retrieve().bodyToMono(String.class).subscribe(System.out::println);
  }
}
