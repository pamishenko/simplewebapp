package com.school21.simplewebstudio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import javax.swing.*;

@SpringBootApplication
@PropertySource("classpath:application-dev.yml")
public class SimpleWebStudioApplication extends JFrame {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SimpleWebStudioApplication.class)
                .headless(false).run(args);
    }
}
