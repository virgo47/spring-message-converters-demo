package com.example.demo1b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

/**
 * With Spring MVC auto-configuration disabled and nothing in its place options are limited.
 * REST still works, conversion to String works, but many basic things, including JSON, do not.
 * <p>
 * Example commands (using HTTPie):
 * <pre>
 * http :8080 # returns String with current time
 * http :8080/list # FAILS
 * http :8080/config # does not return anything, but dumps information on stdout
 * </pre>
 */
@SpringBootApplication(exclude = WebMvcAutoConfiguration.class)
public class DemoApp1b {
  public static void main(String[] args) {
    SpringApplication.run(DemoApp1b.class, args);
  }
}
