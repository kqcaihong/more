package com.learn.more.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class HttpURLClient {

  public static void main(String[] args) {
    String result = doGet("http://localhost:8010/user/queryAll");
    System.out.println(result);

    String param = "{\"name\":\"Andy\",\"age\":18}";
    result = doPost("http://localhost:8010/user/add", param);
    System.out.println(result);
  }

  public static String doGet(String httpUrl) {
    HttpURLConnection connection = null;
    InputStream is = null;
    BufferedReader br = null;
    String result = null;
    try {
      // 创建连接对象
      connection = (HttpURLConnection) new URL(httpUrl).openConnection();
      connection.setRequestMethod("GET");
      // 设置连接超时时间：15秒
      connection.setConnectTimeout(15000);
      // 设置读取超时时间：30秒
      connection.setReadTimeout(30000);
      // 建立连接，发送请求
      connection.connect();
      // 获取输入流
      if (connection.getResponseCode() == 200) {
        is = connection.getInputStream();
        br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sbf = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
          sbf.append(line);
          sbf.append(System.lineSeparator());
        }
        result = sbf.toString();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (Objects.nonNull(br)) {
          br.close();
        }
        if (Objects.nonNull(is)) {
          is.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (Objects.nonNull(connection)) {
        connection.disconnect();
      }
    }
    return result;
  }

  public static String doPost(String httpUrl, String param) {
    HttpURLConnection connection = null;
    InputStream is = null;
    OutputStream os = null;
    BufferedReader br = null;
    String result = null;
    try {
      URL url = new URL(httpUrl);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setConnectTimeout(15000);
      connection.setReadTimeout(30000);
      // 默认值为：false，当向远程服务器写数据时，需要设置为true
      connection.setDoOutput(true);
      // 默认值为：true，当前向远程服务读取数据时，设置为true
      connection.setDoInput(true);
      // 设置入参、响应格式
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Accept-Type", "application/json");
      connection.connect();
      // 向输出流写出入参
      os = connection.getOutputStream();
      os.write(param.getBytes());
      // 通过输入流读取响应
      if (connection.getResponseCode() == 200) {
        is = connection.getInputStream();
        br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sbf = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
          sbf.append(line);
        }
        result = sbf.toString();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (Objects.nonNull(br)) {
          br.close();
        }
        if (Objects.nonNull(is)) {
          is.close();
        }
        if (Objects.nonNull(os)) {
          os.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (Objects.nonNull(connection)) {
        connection.disconnect();
      }
    }
    return result;
  }
}