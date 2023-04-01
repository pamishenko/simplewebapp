package com.school21.simplewebstudio.services;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Data
@Service
@PropertySource("classpath:application-dev.yml")
public class Engine {

    List<String> buffer;
    @Getter @Setter
    List<String> allHtmlTagTextList;

    List<String> variant;

    String currentWord;

    public ArrayList<String> getHtmlTags() {
        return htmlTags;
    }

    @Value("#{'${config.html.tags}'.split(',')}")
    private ArrayList<String> htmlTags;

    @Autowired
    public Engine(

    ) {

        this.buffer = new ArrayList<>();
        this.allHtmlTagTextList = new ArrayList<>();
        this.variant = null;
        currentWord = "";
    }

    ArrayList<String> getVariant(String str) {

        ArrayList<String> ret = new ArrayList<>();
        Stream<String> stringStream = htmlTags.stream().filter(it -> it.contains(str));
        stringStream.forEach(it -> {
            ret.add(it);
            System.out.println(it);
        });
        return ret;

    }

    public ArrayList<String> parse(String word){
        if (!checkNewWord(word)) {
            currentWord = currentWord + word;
            return getVariant(currentWord);
        }
        else currentWord = "";
        return null;
    }

    private boolean checkNewWord(String s){
        return  (s.equals("\n") || s.equals(" ") || s.equals("\t"));
    }
}
