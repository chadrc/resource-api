package com.chadrc.resourceapi.basic.crud.read;

import com.chadrc.resourceapi.core.ResourceService;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(basePackages = {"com.chadrc.resourceapi"})
@EnableWebMvc
@WebAppConfiguration
@DataMongoTest
@EnableMongoRepositories("com.chadrc.resourceapi")
public class RepositoryGetResourceServiceTestsConfig {

    @Bean
    public HttpMessageConverter getHttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public ResourceService getRepositoryGetResourceService() {
        return new RepositoryGetResourceService();
    }
}
