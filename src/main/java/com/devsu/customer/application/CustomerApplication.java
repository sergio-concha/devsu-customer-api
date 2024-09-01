package com.devsu.customer.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages= {"com.devsu.customer."})
@EnableJpaRepositories(basePackages= {"com.devsu.customer.domain.repository"})
@EntityScan(basePackages= {"com.devsu.customer.domain.dto"})
@EnableWebMvc
@EnableSpringDataWebSupport
public class CustomerApplication implements ApplicationRunner {

    private static final Logger LOG = LogManager.getLogger(CustomerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("************************************************");
        LOG.info("********       Start Customer Api      *********");
        LOG.info("************************************************");
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/devsu/api/**").allowedOrigins("*");
            }
        };
    }
}
