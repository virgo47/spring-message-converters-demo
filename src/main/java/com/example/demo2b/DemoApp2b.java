package com.example.demo2b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class DemoApp2b {
  public static void main(String[] args) {
    SpringApplication.run(DemoApp2b.class, args);
  }
}
