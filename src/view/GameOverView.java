package view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Controller;

public class GameOverView extends JPanel {
	JLabel GameOverText;
	JButton NewGameBtn;
	public GameOverView(Controller c) {
		setBounds(0,0,1387,763);
		this.setLayout(null);
		NewGameBtn = new JButton("New Game");
		NewGameBtn.setBounds(589, 550, 239, 45);
		NewGameBtn.setActionCommand("newGameBtn");
		NewGameBtn.setVisible(true);
		NewGameBtn.addActionListener(c);
		add(NewGameBtn);
		GameOverText= new JLabel();
		GameOverText.setBounds(411, 140, 893, 358);
		GameOverText.setAlignmentX(CENTER_ALIGNMENT);
		Font fPN = new Font("Serif",Font.BOLD, 60);
		GameOverText.setFont(fPN);
		add(GameOverText);
	}
	public JLabel getGameOverText() {
		return GameOverText;
	}
	
	
}
