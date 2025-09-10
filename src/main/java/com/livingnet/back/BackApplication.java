package com.livingnet.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@RestController
public class BackApplication {
public static void main(String[] args) {
      SpringApplication.run(BackApplication.class, args);
    }

@GetMapping("")
public String getMethodName() {
    return "Hello Test";
}

}

