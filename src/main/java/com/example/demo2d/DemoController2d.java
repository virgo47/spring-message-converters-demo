package com.example.demo2d;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@RestController
@RequestMapping
public class DemoController2d {

  @GetMapping
  public String hello() {
    return "Hi, it's " + LocalTime.now();
  }

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

  @GetMapping("/config")
  public Map<?, ?> config() {
    return Map.of(
      "configurers",
      toStrings(configurers),
      "converters",
      toStrings(converters),
      "requestMappingHandlerAdapter-converters",
      toStrings(requestMappingHandlerAdapter.getMessageConverters()));
  }

  // for HTTP 406 demo: http -v :8080/config2 "Accept: application/json"
  // note that "http -j" means "Accept: application/json, */*" and would return XML thanks to */*
  @GetMapping(value = "/config2", produces = "text/xml")
  public Map<?, ?> configXmlOnly() {
    return Map.of(
      "configurers",
      toStrings(configurers),
      "converters",
      toStrings(converters),
      "requestMappingHandlerAdapter-converters",
      toStrings(requestMappingHandlerAdapter.getMessageConverters()));
  }

  private Object toStrings(Collection<?> collection) {
    return collection != null
      ? collection.stream().map(Object::toString).collect(Collectors.toList())
      : "N/A";
  }
}
