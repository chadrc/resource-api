package com.chadrc.resourceapi.core;

import com.chadrc.resourceapi.core.mocks.MockGetResourceService;
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
public class CustomBasePathTestConfig {

    @Bean
    public HttpMessageConverter getHttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public List<ResourceService> getResourceServices() throws Throwable {
        return new ArrayList<ResourceService>() {{
            add(new MockGetResourceService());
        }};
    }
}
