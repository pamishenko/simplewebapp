package com.school21.simplewebstudio;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
@SpringBootApplication
public class SimpleWebStudioApplication extends JFrame{

	public SimpleWebStudioApplication(){
		initUI();
	}

	private void initUI() {

		JButton quitButton = new JButton("Quit");

		quitButton.addActionListener((ActionEvent event) -> {
			System.exit(0);
		});

		createLayout(quitButton);

		setTitle("Quit button");
		setSize(300, 200);
		setLocationRelativeTo(null);
		Image image = Toolkit.getDefaultToolkit().getImage("images/favicon.ico");
		setIconImage(image);
		Font defaultFont = new Font(Font.MONOSPACED, Font.PLAIN, 10);
		setFont(defaultFont);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void createLayout(JComponent... arg) {

		Container pane = getContentPane();
		GroupLayout gl = new GroupLayout(pane);
		pane.setLayout(gl);

		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addComponent(arg[0])
		);

		gl.setVerticalGroup(gl.createSequentialGroup()
				.addComponent(arg[0])
		);
	}

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(SimpleWebStudioApplication.class)
				.headless(false).run(args);

		EventQueue.invokeLater(() -> {

			ctx.getBean(SimpleWebStudioApplication.class).setVisible(true);
		});
	}

	}
