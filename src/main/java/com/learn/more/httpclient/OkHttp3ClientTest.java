package com.learn.more.httpclient;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttp3ClientTest {

  public static void main(String[] args) throws IOException {
    doAsyncGet("http://localhost:8010/user/queryAll");

    String param = "{\"name\":\"Andy\",\"age\":18}";
    String result = doPost("http://localhost:8010/user/add", param);
    System.out.println(result);
  }

  private static String doPost(String url, String param) throws IOException {
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

  private static void doAsyncGet(String url) {
    OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build();
    Request request = new Request.Builder()
        .url(url)
        .get()
        .addHeader("Accept-Type", "application/json")
        .build();

    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {

      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        assert response.body() != null;
        System.out.println(response.body().string());
      }
    });
  }
}
