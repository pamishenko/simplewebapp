package com.school21.simplewebstudio.views.menu;

import com.school21.simplewebstudio.services.Engine;
import com.school21.simplewebstudio.views.ParentFrame;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;

@Component
@PropertySource("classpath:application-dev.yml")
public class MenuBar extends JMenuBar {

    @Getter
    public File selectedFile;

    public

    Engine engine;

    @Autowired
    public MenuBar(ParentFrame parentFrame) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openFileMenuItem = new JMenuItem("Open");
        openFileMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    parentFrame.buffer.clear();
                    parentFrame.clean();
                    parentFrame.updateTitle(selectedFile.getName());
                    while (reader.ready()) {
                        String tmp = reader.readLine() + "\n";
                        parentFrame.buffer.add(tmp);
                        parentFrame.mainTextPanel.mainTextArea.append(tmp);
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
                        parentFrame.buffer.clear();
                        parentFrame.buffer.addAll(Arrays.asList(parentFrame.mainTextPanel.mainTextArea.getText().split("\n")));
                        parentFrame.buffer.forEach(str -> {
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
        aboutHelpMenuItem.addActionListener(
                e -> JOptionPane.showMessageDialog(this, "Simple Web Studio v1.0.0\n" +
                        "© 2023 School 21.\n" +  "Developed by: \n" + "student ttanja"
                )
        );

        JMenu previewMenu = new JMenu("Preview");

        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);
        this.add(previewMenu);
    }
}
