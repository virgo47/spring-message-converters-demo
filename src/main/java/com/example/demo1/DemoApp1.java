package com.example.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Very simple auto-magical application with Spring MVC auto-configuration
 * triggered by dependency spring-boot-starter-web.
 * <p>
 * Example commands (using HTTPie):
 * <pre>
 * http :8080 # returns String with current time
 * http :8080/list # returns list as JSON
 * http :8080/config # returns some converters-related info
 * </pre>
 */
@SpringBootApplication
public class DemoApp1 {
  public static void main(String[] args) {
    SpringApplication.run(DemoApp1.class, args);
  }
}
