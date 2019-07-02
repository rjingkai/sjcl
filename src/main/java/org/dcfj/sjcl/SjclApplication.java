package org.dcfj.sjcl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SjclApplication {

    public static void main(String[] args) {
        SpringApplication.run(SjclApplication.class, args);
    }

}
