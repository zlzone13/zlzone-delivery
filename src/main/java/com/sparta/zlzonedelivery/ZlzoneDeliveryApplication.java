package com.sparta.zlzonedelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ZlzoneDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZlzoneDeliveryApplication.class, args);
    }

}
