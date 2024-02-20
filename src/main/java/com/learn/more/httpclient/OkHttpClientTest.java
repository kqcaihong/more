package com.learn.more.httpclient;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpClientTest {

  public static void main(String[] args) {
    doAsyncGet("http://localhost:8010/user/queryAll");
  }

  private static void doAsyncGet(String url) {
    try {
      // client复用，对客户端设置超时
      OkHttpClient client = new OkHttpClient();
      client.setConnectTimeout(30L, TimeUnit.SECONDS);
      client.setReadTimeout(30L, TimeUnit.SECONDS);
      client.setWriteTimeout(30L, TimeUnit.SECONDS);

      Request request = new Request.Builder()
          .get()
          .url(url)
          .build();
      client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {

        }

        @Override
        public void onResponse(Response response) throws IOException {
          System.out.println(response.body().string());
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
