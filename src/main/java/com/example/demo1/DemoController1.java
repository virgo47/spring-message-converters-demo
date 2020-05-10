package com.example.demo1;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@RestController
// @RequestMapping / is implied
public class DemoController1 {

  // http -v :8080
  @GetMapping
  public String hello() {
    return "Hi, it's " + LocalTime.now();
  }

  // http -v :8080/list
  @GetMapping("/list")
  public Collection<?> list() {
    return List.of(
      "String1",
      List.of("Substring1", "Substring2")
    );
  }

  // DEBUG area
  @Autowired
  private List<WebMvcConfigurer> configurers;

  @Autowired
  private List<HttpMessageConverter<?>> converters;

  @Autowired
  private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

  @GetMapping("config")
  public Map<?, ?> config() {
    return Map.of(
      "configurers",
      toStrings(configurers),
      "converters",
      toStrings(converters),
      "requestMappingHandlerAdapter-converters",
      toStrings(requestMappingHandlerAdapter.getMessageConverters()));
  }

  private String[] toStrings(Collection<?> collection) {
    return collection.stream().map(Object::toString).toArray(String[]::new);
  }
}