package com.school21.simplewebstudio.views.mainframe;

import com.school21.simplewebstudio.controllers.InputListener;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class MainTextPanel extends JPanel {

    InputListener inputListener = new InputListener();


    @Getter @Setter
    public JTextArea mainTextArea = new JTextArea(63, 158);

    @Autowired
    public MainTextPanel() {
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setCaretColor(Color.RED);
        mainTextArea.setSelectionColor(Color.ORANGE);
        mainTextArea.getDocument().addDocumentListener(new MyDocumentListener());
        mainTextArea.getDocument().putProperty("name", "Text Area");

        this.add(new JScrollPane(mainTextArea));
    }

    public void clean() {
        mainTextArea.removeAll();
    }


    class MyDocumentListener implements DocumentListener {
        String newline = "\n";

        public void insertUpdate(DocumentEvent e) {
            updateLog(e, "inserted into");
        }

        public void removeUpdate(DocumentEvent e) {
            updateLog(e, "removed from");
        }

        public void changedUpdate(DocumentEvent e) {
            //Plain text components do not fire these events
        }

        public void updateLog(DocumentEvent e, String action) {
//            inputListener.setList(Arrays.stream(mainTextArea.getText().split("\n")).collect(Collectors.toList()));
        }
    }
}