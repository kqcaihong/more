package com.learn.more.httpclient;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;

public class ApacheHttpClient5 {

  public static void main(String[] args) {
    doGet("http://localhost:8010/user/queryAll");

    String param = "{\"name\":\"Andy\",\"age\":18}";
    doPost("http://localhost:8010/user/add", param);
  }

  // 异步get
  public static void doGet(String url) {
    try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
      client.start();
      // 支持request设置超时
      RequestConfig config = RequestConfig.custom()
          .setConnectTimeout(Timeout.ofSeconds(30))
          .setResponseTimeout(Timeout.ofSeconds(30))
          .build();
      SimpleHttpRequest request = SimpleRequestBuilder.get(url)
          .setRequestConfig(config)
          .build();
      Future<SimpleHttpResponse> future = client.execute(request, new FutureCallback<SimpleHttpResponse>() {
        @Override
        public void completed(SimpleHttpResponse simpleHttpResponse) {
          System.out.println(simpleHttpResponse.getBodyText());
        }

        @Override
        public void failed(Exception e) {

        }

        @Override
        public void cancelled() {

        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 同步post
  public static void doPost(String url, String param) {
    HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8));
    httpPost.setHeader("Accept", ContentType.APPLICATION_JSON.toString());
    httpPost.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
    try (CloseableHttpClient client = HttpClients.createDefault()) {
      CloseableHttpResponse response = client.execute(httpPost);
      HttpEntity entity = response.getEntity();
      System.out.println(EntityUtils.toString(entity));
      response.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}