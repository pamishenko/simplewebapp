package com.school21.simplewebstudio.views;

import com.school21.simplewebstudio.configs.AppConfig;
import com.school21.simplewebstudio.views.mainframe.MainTextPanel;
import com.school21.simplewebstudio.views.menu.MainMenuPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Component
@PropertySource("classpath:application-dev.yml")
public class ParentFrame extends JFrame {

    private final int startLocationX;
    private final int startLocationY;
    private final int width;
    private final int height;
    private final String fileIcon;
    private final String title;

    public ArrayList<String> buffer;

    public MainTextPanel mainTextPanel;
    public MainMenuPanel menuPanel;

@Autowired
    public ParentFrame(
        AppConfig config, @Value("${config.window.startLocationX}") int startLocationX,
        @Value("${config.window.startLocationY}") int startLocationY,
        @Value("${config.window.width}") int width,
        @Value("${config.window.height}") int height,
        @Value("${config.images.icon}") String fileIcon,
        @Value("${config.application.name}") String title){
    this.startLocationY = startLocationY;
        this.startLocationX = startLocationX;
        this.width = width;
        this.height = height;
        this.fileIcon = fileIcon;
        this.title = title;
        this.buffer = new ArrayList<>();
        this.menuPanel  = new MainMenuPanel( this);
        this.mainTextPanel  = new MainTextPanel(config, this);
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocation(startLocationX, startLocationY);
        setTitle(title);
        setIconImage(new ImageIcon(fileIcon).getImage());
        setResizable(false);
        add(menuPanel, BorderLayout.LINE_START);
        add(mainTextPanel, BorderLayout.SOUTH);

        setSize(width, height);
        setVisible(true);
    }

    public void updateTitle(String fileName){
    if (!fileName.isEmpty())
        setTitle(title + " (" + fileName + ")");
    }

    public void clean(){
    mainTextPanel.mainTextArea.setText("");
    }
}
