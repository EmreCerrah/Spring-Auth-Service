package com.emrecerrah.springauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SpringAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthServiceApplication.class, args);
    }
//TODO: Proje ilk calisirken -p ile root password almali yada konsolde calisince sormali?? Yada en kotu app.yml de tanimlanabilir
}
