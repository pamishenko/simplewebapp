package com.school21.simplewebstudio.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration

@ComponentScan("com.school21.simplewebstudio")
@PropertySource("classpath:application-dev.yml")
public class Config {


}
