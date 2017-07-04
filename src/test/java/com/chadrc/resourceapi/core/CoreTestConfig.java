package com.chadrc.resourceapi.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(basePackages = "com.chadrc.resourceapi")
@EnableWebMvc
@WebAppConfiguration
public class CoreTestConfig {
    @Bean
    public HttpMessageConverter getHttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public ResourceService getResourceService() {
        return new MockResourceService();
    }
}
