package com.royeen.smartpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartParkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartParkApplication.class, args);
    }

}
