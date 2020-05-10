package com.example.demo1b;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@RestController
public class DemoController1b {

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
  @Autowired(required = false)
  private List<WebMvcConfigurer> configurers;

  @Autowired(required = false)
  private List<HttpMessageConverter<?>> converters;

  @Autowired(required = false)
  private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

  @Autowired(required = false)
  private ApplicationContext applicationContext;

  // NPE protection is needed here, applicationContext can be used in breakpoint
  @GetMapping("config")
  public void config() {
    System.out.println(Map.of(
      "configurers",
      toStrings(configurers),
      "converters",
      toStrings(converters),
      "requestMappingHandlerAdapter-converters",
      toStrings(requestMappingHandlerAdapter != null
        ? requestMappingHandlerAdapter.getMessageConverters()
        : null)));
  }

  private Object toStrings(Collection<?> collection) {
    return collection != null
      ? collection.stream().map(Object::toString).collect(Collectors.toList())
      : "null";
  }
}
