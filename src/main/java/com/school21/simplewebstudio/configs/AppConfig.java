package com.school21.simplewebstudio.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.Objects;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.school21.simplewebstudio"})
@PropertySource("classpath:application-dev.yml")
public class AppConfig {

    @Value("#{'${config.html.tags}'.split(',')}")
    private ArrayList<String> htmlTags;

    public ArrayList<String> getHtmlTags() {
        return htmlTags;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        final YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application-dev.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        return propertySourcesPlaceholderConfigurer;
    }
}
