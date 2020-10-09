package com.sc.attachment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableSwagger2
@EnableJpaAuditing
@EnableJpaRepositories
public class AttachmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttachmentApplication.class, args);
    }

}
