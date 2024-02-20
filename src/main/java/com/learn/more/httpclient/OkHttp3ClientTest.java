package com.learn.more.httpclient;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OkHttp3ClientTest {

  public static void main(String[] args) throws IOException {
    String param = "{\"name\":\"Andy\",\"age\":18}";
    String result = doPostByOkhttp3("http://localhost:8010/user/add", param);
    System.out.println(result);
  }

  private static String doPostByOkhttp3(String url, String param) throws IOException {
    OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build();
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), param);
    Request request = new Request.Builder()
        .url(url)
        .post(requestBody)
        .addHeader("Accept-Type", "application/json")
        .build();
    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful()) {
        ResponseBody body = response.body();
        return Objects.nonNull(body) ? body.string() : "";
      }
      return "";
    }
  }
}
