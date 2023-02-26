package com.school21.simplewebstudio.views.menu;

import com.school21.simplewebstudio.views.mainframe.MainTextPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import javax.swing.JFileChooser;

@Component
public class MenuBar extends JMenuBar {

    File selectedFile;


    public MenuBar(MainTextPanel mainTextPanel) {

        JMenu fileMenu = new JMenu("File");
        JMenuItem openFileMenuItem = new JMenuItem("Open");
        openFileMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();

                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    while (reader.ready()) {
                        mainTextPanel.mainTextArea.append(reader.readLine() + "\n");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JMenuItem createFileMenuItem = new JMenuItem("Create");
        createFileMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            final JLabel label = new JLabel();
            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                label.setText("File Saved as: " + file.getName());
            } else {
                label.setText("Save command canceled");
            }
        });
        JMenuItem saveFileMenuItem = new JMenuItem("Save");
        JMenuItem exitFileMenuItem = new JMenuItem("Exit");
        exitFileMenuItem.addActionListener(e ->
                System.exit(0)
        );
        fileMenu.add(openFileMenuItem);
        fileMenu.add(createFileMenuItem);
        fileMenu.add(saveFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitFileMenuItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem undoEditMenuItem = new JMenuItem("Undo");
        JMenuItem redoEditMenuItem = new JMenuItem("Redo");
        JMenuItem cutEditMenuItem = new JMenuItem("Cut");
        JMenuItem copyEditMenuItem = new JMenuItem("Copy");
        JMenuItem pasteEditMenuItem = new JMenuItem("Paste");
        editMenu.add(undoEditMenuItem);
        editMenu.add(redoEditMenuItem);
        editMenu.addSeparator();
        editMenu.add(cutEditMenuItem);
        editMenu.add(copyEditMenuItem);
        editMenu.add(pasteEditMenuItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutHelpMenuItem = new JMenuItem("About");
        helpMenu.add(aboutHelpMenuItem);

        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);
    }
}
