package com.school21.simplewebstudio.services.impl;

import com.school21.simplewebstudio.services.Engine;
import com.school21.simplewebstudio.services.FileManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class FileManagerImpl implements FileManager {

    @Getter @Setter
    String currentFile;

    @Getter @Setter
    Engine engine;
    @Override
    public void create() {

    }

    @Override
    public void open() {

    }

    @Override
    public void save() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))){
//            engine.getAllTextList().forEach(str -> {
//                try {
//                    writer.write(str);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }catch (IOException ex){
//            ex.printStackTrace();
//        }

    }
}
