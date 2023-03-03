package com.school21.simplewebstudio.views.menu;

import com.school21.simplewebstudio.views.ParentFrame;
import com.school21.simplewebstudio.views.mainframe.MainTextPanel;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class MainMenuPanel extends JPanel {

    public ParentFrame parentFrame;

    @Getter
    public MenuBar menuBar = null;

    @Autowired
    public MainMenuPanel(MainTextPanel mainTextPanel, ParentFrame parentFrame) {
        this.parentFrame = parentFrame;
        menuBar = new MenuBar(mainTextPanel, parentFrame);
        this.add(menuBar);
    }
}
