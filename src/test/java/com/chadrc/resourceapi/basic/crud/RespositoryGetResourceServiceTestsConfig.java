package com.chadrc.resourceapi.basic.crud;

import com.chadrc.resourceapi.core.ResourceService;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@ComponentScan(basePackages = "com.chadrc.resourceapi")
@EnableWebMvc
@WebAppConfiguration
@AutoConfigureDataMongo
public class RespositoryGetResourceServiceTestsConfig {

    @Bean
    public HttpMessageConverter getHttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public List<ResourceService> getResourceServices() {
        return new ArrayList<ResourceService>() {{
            add(new RepositoryGetResourceService());
        }};
    }
}
