package view;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controller.Controller;

public class View extends JFrame {
	private JTextArea playerInput;
	private JComboBox cityInput;
	private JLabel BGImage;
	private JButton cairoBtn;
	private JButton romeBtn;
	private JButton spartaBtn;
	private JLabel selectText;
	private JTextArea status;
	public View() {
		setTitle("Clash Of Clan (El ghalaba Edition)");
		setBounds(0,0,1387,763);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		ImageIcon img = new ImageIcon("icon2.jpg");
		setIconImage(img.getImage());
		BGImage = new JLabel(new ImageIcon("option2.jpg"));
		BGImage.setSize(1387,763);
		BGImage.setLayout(new FlowLayout());;
		playerInput = new JTextArea();
		cityInput = new JComboBox<String>(new String[]{"Cairo","Rome","Sparta"});
		playerInput.setBounds(450,620,500,40);
		playerInput.setVisible(true);
		//add(playerInput);
		cityInput.setBounds(170,100,60,30);
		cityInput.setVisible(true);
		status = new JTextArea();
		status.setSize(240,100);
	//	status.setBackground(Color.decode("#cfbc88"));
		status.setBackground(Color.decode("#aa9a6d"));
		status.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		status.setVisible(false);
		status.setEditable(false);
		add(status);
		revalidate();
		repaint();
	}
	public JLabel getSelectText() {
		return selectText;
	}
	
	public void setBG() {
		add(BGImage);
	}
	public JTextArea getPlayerInput() {
		return playerInput;
	}

	public void setPlayerInput(JTextArea playerInput) {
		this.playerInput = playerInput;
	}

	public JComboBox getCityInput() {
		return cityInput;
	}

	

	public JTextArea getStatus() {
		return status;
	}
	public void setStatus(JTextArea status) {
		this.status = status;
	}
	public static void main(String [] args) {
		//new View();
		
	}
	
}

