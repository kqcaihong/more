package com.learn.more.httpclient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ApacheHttpClient {

  public static void main(String[] args) {
    String result = doGet("http://localhost:8010/user/queryAll");
    System.out.println(result);

    String param = "{\"name\":\"Andy\",\"age\":18}";
    result = doPost("http://localhost:8010/user/add", param);
    System.out.println(result);
  }

  public static String doGet(String url) {
    // 创建一个httpClient实例
    CloseableHttpClient httpClient = HttpClients.createDefault();
    // 创建httpGet连接实例
    HttpGet httpGet = new HttpGet(url);
    httpGet.setHeader("Accept-Type", "application/json");
    RequestConfig requestConfig = RequestConfig.custom()
        // 连接超时时间
        .setConnectTimeout(10000)
        // 请求超时时间
        .setConnectionRequestTimeout(30000)
        // 读取超时时间
        .setSocketTimeout(60000)
        .build();
    // 为请求设置配置
    httpGet.setConfig(requestConfig);
    CloseableHttpResponse response = null;
    String result = "";
    try {
      response = httpClient.execute(httpGet);
      // 通过返回对象获取返回数据
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (Objects.nonNull(response)) {
          response.close();
        }
        if (Objects.nonNull(httpClient)) {
          httpClient.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  public static String doPost(String url, String param) {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    // 创建httpPost远程连接实例
    HttpPost httpPost = new HttpPost(url);
    // 配置请求参数实例
    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(10000)
        .setConnectionRequestTimeout(30000)
        .setSocketTimeout(60000)
        .build();
    httpPost.setConfig(requestConfig);
    httpPost.addHeader("Content-Type", "application/json");
    httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8));
    CloseableHttpResponse response = null;
    String result = "";
    try {
      response = httpClient.execute(httpPost);
      // 从响应对象中获取响应内容
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (Objects.nonNull(response)) {
          response.close();
        }
        if (Objects.nonNull(httpClient)) {
          httpClient.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }
}