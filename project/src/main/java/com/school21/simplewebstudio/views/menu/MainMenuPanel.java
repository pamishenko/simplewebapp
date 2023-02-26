package com.school21.simplewebstudio.views.menu;

import com.school21.simplewebstudio.views.mainframe.MainTextPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class MainMenuPanel extends JPanel {


    @Autowired
    public MainMenuPanel(MainTextPanel mainTextPanel) {
        this.add(new MenuBar(mainTextPanel));
    }
}
