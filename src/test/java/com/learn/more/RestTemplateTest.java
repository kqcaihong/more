package com.learn.more;

import com.learn.more.entiry.User;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoreApplication.class)
class RestTemplateTest {

  public static final String BASE_URL = "http://localhost:8010";

  @Autowired
  private RestTemplate restTemplate;

  @Test
  void get() {
    String url = BASE_URL + "/user/queryAll";
    // restTemplate 会把复杂的对象转换成 LinkedHashMap
    List allUser = restTemplate.getForObject(url, List.class);
    System.out.println(allUser);
  }

  @Test
  void getAsArray() {
    String url = BASE_URL + "/user/queryAll";
    User[] allUser = restTemplate.getForObject(url, User[].class);
    System.out.println(allUser);
  }

  @Test
  void getAsList() {
    String url = BASE_URL + "/user/queryAll";
    MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
    header.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
    header.put(HttpHeaders.ACCEPT, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
    HttpEntity<User> entity = new HttpEntity<>(header);
    // 匿名子类
    ParameterizedTypeReference<List<User>> type = new ParameterizedTypeReference<List<User>>() {
    };
    ResponseEntity<List<User>> allUser = restTemplate.exchange(url, HttpMethod.GET, entity, type);
    System.out.println(allUser.getBody());
  }

  @Test
  void postByParam() {
    String url = BASE_URL + "/user/addByParam";
    // 设置请求的 Content-Type
    MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
    header.add(HttpHeaders.CONTENT_TYPE, (MediaType.APPLICATION_FORM_URLENCODED_VALUE));
    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("name", "Cathy");
    map.add("age", 23);
    HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, header);
    ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, User.class);
    System.out.println(response.getBody());
  }

  @Test
  void postByParam2() {
    String url = BASE_URL + "/user/addByParam";
    // 设置请求的 Content-Type
    MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
    header.add(HttpHeaders.CONTENT_TYPE, (MediaType.APPLICATION_FORM_URLENCODED_VALUE));
    String param = new StringBuilder().append("name=Judy").append("&").append("age=18").toString();
    HttpEntity<String> httpEntity = new HttpEntity<>(param, header);
    ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, User.class);
    System.out.println(response.getBody());
  }

  @Test
  void postByBody() {
    String url = BASE_URL + "/user/add";
    User tom = new User("Cathy", 23);
    // body体传参
    User user = restTemplate.postForObject(url, tom, User.class);
    System.out.println(user);
  }

  @Test
  void exchange() {
    String url = BASE_URL + "/user/add";
    User tom = new User("Alan", 35);
    MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
    header.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
    header.put(HttpHeaders.ACCEPT, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
    HttpEntity<User> entity = new HttpEntity<>(tom, header);
    ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, entity, User.class);
    System.out.println(response.getBody());
  }
}
