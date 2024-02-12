package com.learn.more.httpclient;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class OkHttpClientTest {

  public static void main(String[] args) throws IOException {
    doAsyncGet("http://localhost:8010/user/queryAll");

    String param = "{\"name\":\"Andy\",\"age\":18}";
    String result = doPostByOkhttp3("http://localhost:8010/user/add", param);
    System.out.println(result);
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

  private static String doPostByOkhttp3(String url, String param) throws IOException {
    okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build();
    RequestBody requestBody = RequestBody.create(param, MediaType.parse("application/json"));
    okhttp3.Request request = new okhttp3.Request.Builder()
        .url(url)
        .post(requestBody)
        .addHeader("Accept-Type", "application/json")
        .build();
    okhttp3.Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      ResponseBody body = response.body();
      return Objects.nonNull(body) ? body.string() : "";
    }
    return "";
  }
}
