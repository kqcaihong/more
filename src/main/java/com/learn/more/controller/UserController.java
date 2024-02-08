package com.learn.more.controller;

import com.learn.more.entiry.User;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

  private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
  // 模拟数据库保存
  private static final Map<Long, User> USER_MAP = new ConcurrentHashMap<>();

  @GetMapping("/queryById")
  public User queryById(@RequestParam Long id) {
    return USER_MAP.get(id);
  }

  @GetMapping("/queryAll")
  public List<User> queryAll() {
    return USER_MAP.values().stream().sorted(Comparator.comparingLong(User::getId)).collect(Collectors.toList());
  }

  @PostMapping("/add")
  public User add(@RequestBody User user) {
    user.setId(ID_GENERATOR.incrementAndGet());
    USER_MAP.put(user.getId(), user);
    return user;
  }
}