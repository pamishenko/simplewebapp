package com.school21.simplewebstudio.views.mainframe;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.util.List;

public class MyPopMenu extends JFrame {

    private final JPopupMenu popupMenu;

    public MyPopMenu(List<String> options, RSyntaxTextArea textArea) {
        super();

        popupMenu = new JPopupMenu();

        options.forEach(
                option -> {
                    JMenuItem menuItem = new JMenuItem(option);
                    menuItem.addActionListener(e -> {
                        textArea.replaceSelection(option);
});
                    popupMenu.add(menuItem);
                }
        );

        textArea.setComponentPopupMenu(popupMenu);
        add(textArea);
        pack();
        setVisible(true);
    }
}
