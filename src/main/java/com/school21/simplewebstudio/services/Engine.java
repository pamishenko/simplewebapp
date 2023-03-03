package com.school21.simplewebstudio.services;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Service
@PropertySource("classpath:application-dev.yml")
public class Engine {

    @Getter @Setter
    ArrayList<String> allTextList;

    @Getter
    ArrayList<String> htmlTags;

    @Autowired
    public Engine(
            @Value("${config.htmlTags}") ArrayList<String> htmlTags) {
        this.allTextList = new ArrayList<>();
        this.htmlTags = htmlTags;
    }


}
