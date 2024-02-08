package com.learn.more.httpclient;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient.Builder;
import okhttp3.RequestBody;

public class OkHttpClientTest {

  //创建单个OkHttpClient实例可以将进行重复使用，支持异步、同步请求
  public static void main(String[] args) {
    get();
  }

  // client复用，对客户端设置超时
  private static void get() {
    try {
      OkHttpClient client = new OkHttpClient();
      client.setConnectTimeout(30L, TimeUnit.SECONDS);
      client.setReadTimeout(30L, TimeUnit.SECONDS);
      client.setWriteTimeout(30L, TimeUnit.SECONDS);

      Request request = new Request.Builder()
          .get()
          .url("http://www.baidu.com")
          .build();

      //      Response response = client.newCall(request).execute();
      //      if (response.isSuccessful()) {
      //        Headers responseHeaders = response.headers();
      //        for (int i = 0; i < responseHeaders.size(); i++) {
      //          System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
      //        }
      //        System.out.println(response.body().string());
      //      }
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

  private static void http5Post(String url) throws IOException {
    okhttp3.OkHttpClient client = new Builder()
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build();
    RequestBody body = RequestBody.create("", MediaType.parse("application/json"));
    okhttp3.Request request = new okhttp3.Request.Builder()
        .url(url)
        .post(body)
        .build();
    okhttp3.Response response = client.newCall(request).execute();
    System.out.println(response);
  }
}
