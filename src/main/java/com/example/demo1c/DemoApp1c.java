package com.example.demo1c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Now we're declaring to {@code @EnableWebMvc} explicitly.
 * MVC is configured, but without using the auto-configuration.
 * <p>
 * Example commands (using HTTPie):
 * <pre>
 * http :8080 # returns String with current time
 * http :8080/list # returns collection as XML, add -j for JSON
 * http :8080/config # returns config as XML, add -j for JSON
 * </pre>
 */
@SpringBootApplication
@EnableWebMvc
public class DemoApp1c {
  public static void main(String[] args) {
    SpringApplication.run(DemoApp1c.class, args);
  }
}
