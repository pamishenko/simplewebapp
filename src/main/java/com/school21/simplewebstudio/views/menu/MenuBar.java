package com.school21.simplewebstudio.views.menu;

import com.school21.simplewebstudio.services.Engine;
import com.school21.simplewebstudio.views.ParentFrame;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        JMenu viewMenu = new JMenu("View");
        JMenuItem lightTheme = new JMenuItem("Light");
        JMenuItem darkTheme = new JMenuItem("Dark");
        viewMenu.add(lightTheme);
        viewMenu.add(darkTheme);
        lightTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Установка темы Light
                    parentFrame.mainTextPanel.mainTextArea.setBackground(java.awt.Color.WHITE);
                    parentFrame.mainTextPanel.mainTextArea.setForeground(java.awt.Color.BLACK);
                    parentFrame.mainTextPanel.mainTextArea.setCurrentLineHighlightColor(Color.YELLOW);
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    UIManager.put("nimbusBase", Color.LIGHT_GRAY);
                    UIManager.put("nimbusBlueGrey", Color.LIGHT_GRAY);
                    UIManager.put("control", Color.LIGHT_GRAY);
                    SwingUtilities.updateComponentTreeUI(parentFrame);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        darkTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Установка темы Dark
                    parentFrame.mainTextPanel.mainTextArea.setBackground(java.awt.Color.BLACK);
                    parentFrame.mainTextPanel.mainTextArea.setForeground(java.awt.Color.WHITE);
                    parentFrame.mainTextPanel.mainTextArea.setCurrentLineHighlightColor(Color.DARK_GRAY);
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    UIManager.put("nimbusBase", java.awt.Color.darkGray);
                    UIManager.put("nimbusBlueGrey", java.awt.Color.darkGray);
                    UIManager.put("control", java.awt.Color.darkGray);
                    SwingUtilities.updateComponentTreeUI(parentFrame);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutHelpMenuItem = new JMenuItem("About");
        helpMenu.add(aboutHelpMenuItem);
        aboutHelpMenuItem.addActionListener(
                e -> JOptionPane.showMessageDialog(this, "Simple Web Studio v1.0.0\n" +
                        "© 2023 School 21.\n" +  "Developed by: \n" + "student ttanja"
                )
        );

        JMenu previewMenu = new JMenu("Preview");
        JMenuItem previewMenuItem = new JMenuItem("Preview");
        previewMenuItem.addActionListener(e -> {
            try {
                JEditorPane previewPane = new JEditorPane();
                previewPane.setEditable(false);
                previewPane.setContentType("text/html");
                previewPane.setText(parentFrame.mainTextPanel.mainTextArea.getText());

                JFrame previewFrame = new JFrame("Preview");
                previewFrame.add(new JScrollPane(previewPane));
                previewFrame.pack();
                previewFrame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        previewMenu.add(previewMenuItem);

        this.add(fileMenu);
        this.add(editMenu);
        this.add(viewMenu);
        this.add(helpMenu);
        this.add(previewMenu);
    }
}
