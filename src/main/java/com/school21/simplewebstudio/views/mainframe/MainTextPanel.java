package com.school21.simplewebstudio.views.mainframe;

import com.school21.simplewebstudio.configs.AppConfig;
import com.school21.simplewebstudio.views.ParentFrame;
import lombok.Getter;
import lombok.Setter;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MainTextPanel extends JPanel {
    static public ParentFrame parentFrame;
    private final AppConfig config;
    @Getter
    @Setter
    public RSyntaxTextArea mainTextArea = new RSyntaxTextArea(67, 270);



    @Autowired
    public MainTextPanel(
            AppConfig config, ParentFrame parentFrame
    ) {

        ArrayList<String> buffer = config.getHtmlTags();
        mainTextArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // Вызывается при изменении текста
            }
            public void insertUpdate(DocumentEvent e) {
                String tex = mainTextArea.getText();
                int offset = e.getOffset();
                if (Character.isWhitespace(tex.charAt(offset)))
                    return;
                try {
                    String text = mainTextArea.getText(0, e.getOffset() + 1);
                    String[] words = text.split("\\s"); // Разбиваем текст на слова
                    String lastWord = words[words.length - 1]; // Получаем последнее слово
                    List<String> suggestions = config.getHtmlTags(); // Список подсказок

                    JPopupMenu popupMenu = new JPopupMenu(); // Создаем всплывающее меню
                    List<String> temp = suggestions.stream().filter(suggestion -> suggestion.startsWith(lastWord)).collect(Collectors.toList());
                    // Фильтруем подсказки по введенному тексту
                    temp.forEach(
                            suggestion -> {
                                JMenuItem menuItem = new JMenuItem(suggestion);
                                menuItem.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent act) {
                                        JMenuItem selectedMenuItem = (JMenuItem) act.getSource();
                                        String selectedText = selectedMenuItem.getText();
                                        mainTextArea.replaceRange(selectedText, e.getOffset(), e.getOffset() + lastWord.length());
                                    }
                                });
                                popupMenu.add(menuItem);
                            });
                    // Добавляем подсказки в меню

                    Rectangle rectangle = mainTextArea.modelToView(e.getOffset()); // Получаем позицию курсора
                    Point point = new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height); // Создаем точку для отображения меню
                    SwingUtilities.convertPointToScreen(point, mainTextArea); // Преобразуем координаты точки в экранные координаты
                    popupMenu.show(mainTextArea, point.x, point.y); // Отображаем меню

                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                mainTextArea.requestFocusInWindow();
            }
            public void removeUpdate(DocumentEvent e) {
                // Вызывается при удалении текста
            }
        });

        this.config = config;
        DefaultCompletionProvider provider = new DefaultCompletionProvider();
        config.getHtmlTags().forEach(it -> provider.addCompletion(new BasicCompletion(provider, it)));
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(mainTextArea);
        mainTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
        mainTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
        mainTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        mainTextArea.setCodeFoldingEnabled(true);
        mainTextArea.setAntiAliasingEnabled(true);
        mainTextArea.setAutoIndentEnabled(true);




        MainTextPanel.parentFrame = parentFrame;
        JScrollPane jScrollPane = new JScrollPane(mainTextArea);
        BasicScrollBarUI basicScrollBarUI = new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.GRAY;
            }
        };
        jScrollPane.getVerticalScrollBar().setUI(basicScrollBarUI);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(jScrollPane);

    }
}