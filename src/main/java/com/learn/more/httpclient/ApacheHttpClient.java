package com.learn.more.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Future;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.util.Timeout;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ApacheHttpClient {

  public static final String url =
      "https://www.baidu.com/sugrec?&prod=pc_his&from=pc_web&json=1&sid=40156_39996_40201_39662_40207_40212_40216_40222&hisdata=&_t=1707296757667"
          + "&req=2&csor=0";

  public static void main(String[] args) {
    //    String result = doGet(url);
    //    System.out.println(result);
    //    http5Get(url);
    http5Post("https://is.pkfare.com/pf-direct/order-manage/get-is-order-list",
        "{\"createdDate\":[\"2024-02-07\",\"2024-02-07\"],\"orderItemNumber\":\"\",\"pnr\":\"\",\"airlinePnr\":\"\",\"tktNumber\":\"\","
            + "\"passengerName\":\"\",\"postingDate\":[],\"lockedBy\":\"\",\"departureDate\":null,\"productCode\":\"\",\"supplierOrder\":\"\","
            + "\"buyerOrder\":\"\",\"ancillaryTypeList\":[],\"ancillaryOrderNumber\":\"\",\"saleStage\":null,\"currentPage\":1,\"dataCount\":10,"
            + "\"dataFrom\":0}");
  }

  public static String doGet(String url) {
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;
    String result = "";
    try {
      // 通过址默认配置创建一个httpClient实例
      httpClient = HttpClients.createDefault();
      // 创建httpGet远程连接实例
      HttpGet httpGet = new HttpGet(url);
      // 设置请求头信息，鉴权
      httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
      // 设置配置请求参数
      RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
          .setConnectionRequestTimeout(35000)// 请求超时时间
          .setSocketTimeout(60000)// 数据读取超时时间
          .build();
      // 为httpGet实例设置配置
      httpGet.setConfig(requestConfig);
      // 执行get请求得到返回对象
      response = httpClient.execute(httpGet);
      // 通过返回对象获取返回数据
      HttpEntity entity = response.getEntity();
      // 通过EntityUtils中的toString方法将结果转换为字符串
      result = EntityUtils.toString(entity);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 关闭资源
      if (null != response) {
        try {
          response.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != httpClient) {
        try {
          httpClient.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }

  public static String doPost(String url, Map<String, Object> paramMap) {
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse httpResponse = null;
    String result = "";
    // 创建httpClient实例
    httpClient = HttpClients.createDefault();
    // 创建httpPost远程连接实例
    HttpPost httpPost = new HttpPost(url);
    // 配置请求参数实例
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
        .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
        .setSocketTimeout(60000)// 设置读取数据连接超时时间
        .build();
    // 为httpPost实例设置配置
    httpPost.setConfig(requestConfig);
    // 设置请求头
    httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
    // 封装post请求参数
    if (null != paramMap && paramMap.size() > 0) {
      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
      // 通过map集成entrySet方法获取entity
      Set<Entry<String, Object>> entrySet = paramMap.entrySet();
      // 循环遍历，获取迭代器
      Iterator<Entry<String, Object>> iterator = entrySet.iterator();
      while (iterator.hasNext()) {
        Entry<String, Object> mapEntry = iterator.next();
        nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
      }
      // 为httpPost设置封装好的请求参数
      try {
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    try {
      // httpClient对象执行post请求,并返回响应参数对象
      httpResponse = httpClient.execute(httpPost);
      // 从响应对象中获取响应内容
      HttpEntity entity = httpResponse.getEntity();
      result = EntityUtils.toString(entity);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 关闭资源
      if (null != httpResponse) {
        try {
          httpResponse.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != httpClient) {
        try {
          httpClient.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }

  // 异步get
  public static void http5Get(String url) {
    try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {
      client.start();
      // 支持request设置超时
      org.apache.hc.client5.http.config.RequestConfig config = org.apache.hc.client5.http.config.RequestConfig.custom()
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
          System.out.println(simpleHttpResponse.getContentType());
          System.out.println(simpleHttpResponse.getReasonPhrase());
          System.out.println(simpleHttpResponse.getCode());
        }

        @Override
        public void failed(Exception e) {

        }

        @Override
        public void cancelled() {

        }
      });

      SimpleHttpResponse response = future.get();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void http5Post(String url, String param) {
    HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(new StringEntity(param, ContentType.APPLICATION_JSON));
    httpPost.setHeader("Accept", "application/json");
    httpPost.setHeader("Content-Type", "application/json");
    httpPost.setHeader("Cookie",
        "__PKDFP=982fc318c57a859883e38a967eef8341; gr_user_id=fab4d325-d4cb-4259-a2ca-10ea167a71f2; _ga=GA1.3.572078408.1681123988; "
            + "mp_2200c364fb9bf96f6533578821d4c943_mixpanel=%7B%22distinct_id%22%3A%20%221890c8667f741e-0575110efdcd7c-26031d51-1d4839"
            + "-1890c8667f812e0%22%2C%22%24device_id%22%3A%20%221890c8667f741e-0575110efdcd7c-26031d51-1d4839-1890c8667f812e0%22%2C%22"
            + "%24initial_referrer%22%3A%20%22http%3A%2F%2Fis-t1.pkfare"
            + ".com%2Fapp-finances%2Ffinance%2FairAPSettlement%2Fto-be-paid%22%2C%22%24initial_referring_domain%22%3A%20%22is-t1.pkfare.com%22%7D; "
            + "gr_user_id=fab4d325-d4cb-4259-a2ca-10ea167a71f2; 8ed50f333bf1b541_gr_last_sent_cs1=734; b7b06b7a164c97e0_gr_last_sent_cs1=17658; "
            + "pkfare_fe_runtime_env=T1; pkNewLang=CN; pkHomeLang=zh; lang=CN; language=cn; _ga=GA1.2.1006587011.1704956071; "
            + "82e571228a1d467b_gr_last_sent_cs1=45746; 82e571228a1d467b_gr_cs1=45746; currency=HKD; 8ed50f333bf1b541_gr_last_sent_cs1=734; "
            + "pkHomeLang=en; pkNewLang=UK; lang=UK; language=uk; _gid=GA1.3.795036365.1707104442; __PKDFP=71c470896fea12c63f4653955c63e12e; "
            + "IS-PKFARE-SESSION-NEW=8540f781-d39c-4fbb-95ae-8c9a1b62127e; 8ed50f333bf1b541_gr_session_id=d527ff93-3362-482a-80a2-72f849ca00d7; "
            + "8ed50f333bf1b541_gr_last_sent_sid_with_cs1=d527ff93-3362-482a-80a2-72f849ca00d7; "
            + "8ed50f333bf1b541_gr_session_id_sent_vst=d527ff93-3362-482a-80a2-72f849ca00d7; _gat=1; _ga_8YVNRQ47CT=GS1.3.1707300418.33.0"
            + ".1707300418.0.0.0; mp_b7361f0007d416df727be2de29682ae3_mixpanel=%7B%22distinct_id%22%3A%20%2218c949174450-0384104eda694e-26001951"
            + "-124d32-18c94917446d0f%22%2C%22%24device_id%22%3A%20%2218c949174450-0384104eda694e-26001951-124d32-18c94917446d0f%22%2C%22"
            + "%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%7D; 8ed50f333bf1b541_gr_cs1=734");
    try (CloseableHttpClient client = HttpClients.createDefault()) {
      CloseableHttpResponse response = client.execute(httpPost);
      HttpEntity entity = response.getEntity();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      entity.writeTo(outputStream);
      String body = outputStream.toString();
      System.out.println("body: " + body);
      response.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}