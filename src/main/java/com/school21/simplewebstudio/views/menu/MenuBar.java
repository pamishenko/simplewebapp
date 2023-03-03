package com.school21.simplewebstudio.views.menu;

import com.school21.simplewebstudio.services.Engine;
import com.school21.simplewebstudio.services.impl.FileManagerImpl;
import com.school21.simplewebstudio.views.ParentFrame;
import com.school21.simplewebstudio.views.mainframe.MainTextPanel;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@PropertySource("classpath:application-dev.yml")
public class MenuBar extends JMenuBar {

    @Getter
    public File selectedFile;

    public

    Engine engine;

    ArrayList<String> buffer = new ArrayList<>();

    FileManagerImpl fileManager;

    @Autowired
    public MenuBar(MainTextPanel mainTextPanel, ParentFrame parentFrame) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openFileMenuItem = new JMenuItem("Open");
        openFileMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    mainTextPanel.clean();
                    parentFrame.updateTitle(selectedFile.getName());
                    while (reader.ready()) {
                        String tmp = reader.readLine() + "\n";
                        buffer.add(tmp);
                        mainTextPanel.mainTextArea.append(tmp);
                    }
                    fileManager = new FileManagerImpl();
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
                selectedFile = fileChooser.getSelectedFile();
                label.setText("File Saved as: " + selectedFile.getName());
            } else {
                label.setText("Save command canceled");
            }
        });
        JMenuItem saveFileMenuItem = new JMenuItem("Save");
        saveFileMenuItem.addActionListener(e -> {
                    selectedFile.setWritable(true);
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                        buffer.clear();
                        buffer.addAll(Arrays.asList(mainTextPanel.mainTextArea.getText().split("\n")));
                        buffer.forEach(str -> {
                            try {
                                writer.write(str);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        writer.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        );
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

        JMenu previewMenu = new JMenu("Preview");

        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);
        this.add(previewMenu);
    }
}
