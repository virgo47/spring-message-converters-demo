package com.example.demo2c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class DemoApp2c {
  public static void main(String[] args) {
    SpringApplication.run(DemoApp2c.class, args);
  }
}
