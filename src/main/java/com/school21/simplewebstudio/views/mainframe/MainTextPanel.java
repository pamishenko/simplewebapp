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

@Component
public class MainTextPanel extends JPanel {
    static public ParentFrame parentFrame;
    private final AppConfig config;
    @Getter
    @Setter
    public RSyntaxTextArea mainTextArea = new RSyntaxTextArea(67, 272);


    @Autowired
    public MainTextPanel(
            AppConfig config, ParentFrame parentFrame
    ) {
        this.config = config;
        DefaultCompletionProvider provider = new DefaultCompletionProvider();
        config.getHtmlTags().forEach(it -> provider.addCompletion(new BasicCompletion(provider, it)));
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(mainTextArea);
        mainTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
        mainTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
        mainTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        mainTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        mainTextArea.setCodeFoldingEnabled(true);
        mainTextArea.setAntiAliasingEnabled(true);
        mainTextArea.setAutoIndentEnabled(true);

        MainTextPanel.parentFrame = parentFrame;
        this.add(new JScrollPane(mainTextArea));
    }

}