package com.example.bootdemojava;

import java.time.LocalTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BootDemoJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootDemoJavaApplication.class, args);
	}

	// http :8080
    @GetMapping("/")
    public String hello() {
        return "Hi, it's " + LocalTime.now();
    }
}
