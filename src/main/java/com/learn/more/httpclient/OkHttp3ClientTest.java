package com.learn.more.httpclient;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttp3ClientTest {

  public static final File CACHE_DIRECTORY = new File("D:/okhttp/cache");
  public static final long MAX_SIZE = 50L * 1024L * 1024L;
  public static final OkHttpClient client = new OkHttpClient.Builder()
      .connectTimeout(3L, TimeUnit.SECONDS)
      .readTimeout(5L, TimeUnit.SECONDS)
      .cache(new Cache(CACHE_DIRECTORY, MAX_SIZE))
      .build();

  public static void main(String[] args) throws IOException {
//    doAsyncGet("http://localhost:8010/user/queryAll");

        String param = "{\"name\":\"Andy\",\"age\":18}";
        String result = doPost("http://localhost:8010/user/add", param);
        System.out.println(result);
    //
    //    doAsyncGet("http://localhost:8010/user/queryAll");

    //    Request request = new Request.Builder()
    //        .cacheControl(new CacheControl.Builder().noCache().build())
    //        .url("http://publicobject.com/helloworld.txt")
    //        .build();
    //    Request request2 = new Request.Builder()
    //        .cacheControl(new CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build())
    //        .url("http://publicobject.com/helloworld.txt")
    //        .build();
    //    Request request3 = new Request.Builder()
    //        .cacheControl(new CacheControl.Builder().noCache().build())
    //        .url("http://publicobject.com/helloworld.txt")
    //        .build();
    //
        Request request4 = new Request.Builder()
            .cacheControl(new CacheControl.Builder().noStore().build())
            .url("http://publicobject.com/helloworld.txt")
            .build();
    //    Response forceCacheResponse = client.newCall(request).execute();
    //    if (forceCacheResponse.code() != 504) {
    //      // 命中缓存，使用它
    //    } else {
    //      // 资源未被缓存
    //    }
    //    Request request5 = new Request.Builder()
    //        .cacheControl(new CacheControl.Builder().maxStale(10, TimeUnit.DAYS).build())
    //        .url("http://publicobject.com/helloworld.txt")
    //        .build();
  }

  private static String doPost(String url, String param) throws IOException {
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
    Request request = new Request.Builder()
//        .cacheControl(new CacheControl.Builder().onlyIfCached().build())
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
        if (response.code() != 504) {
          assert response.body() != null;
          System.out.println(response.body().string());
        } else {
          System.out.println("资源未被缓存");
        }
      }
    });
  }
}
