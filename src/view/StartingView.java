package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Controller;

public class StartingView extends JPanel{
	
	private JButton cairoBtn;
	private JButton spartaBtn;
	private JButton romeBtn;
	private Controller c;
	private JLabel selectText;
	private JTextArea playerInput;
	JButton start;
	private  JLabel BGImage;
	public StartingView(Controller c) {
		this.c = c;	
		setBounds(0,0,1387,763);
		this.setLayout(null);
		
		start = new JButton("Start");
		start.setBounds(650,680,120,40);
		start.addActionListener(c);
		add(start);
		
		cairoBtn = new JButton(new ImageIcon("X-mark.png"));
        cairoBtn.setBounds(230, 590, 45, 45);
        cairoBtn.setVisible(true);
        cairoBtn.setContentAreaFilled(false);
        cairoBtn.setActionCommand("cairoBtn");
        cairoBtn.addActionListener(c);
    	add(cairoBtn);
        
        spartaBtn = new JButton(new ImageIcon("X-mark.png"));
		spartaBtn.setBounds(460,143,45,45);
		spartaBtn.setContentAreaFilled(false);
		spartaBtn.setActionCommand("spartaBtn");
		spartaBtn.setVisible(true);
		spartaBtn.addActionListener(c);
		add(spartaBtn);
        
		romeBtn = new JButton(new ImageIcon("X-mark.png"));
		romeBtn.addActionListener(c);
		romeBtn.setBounds(906, 395, 45, 45);
		romeBtn.setContentAreaFilled(false);
		romeBtn.setActionCommand("romeBtn");
		romeBtn.setVisible(true);
		add(romeBtn);
		
		
		Font f = new Font("Serif",Font.BOLD, 50);
		
		JLabel cairoText = new JLabel("Cairo");
		cairoText.setForeground(Color.BLACK);
		cairoText.setFont(f);
		cairoText.setBounds(150, 630, 250, 100);
		cairoText.setVisible(true);
		add(cairoText);
		
		JLabel spartaText = new JLabel("Sparta");
		spartaText.setForeground(Color.BLACK);
		spartaText.setFont(f);
		spartaText.setBounds(290, 103, 250, 100);
		spartaText.setVisible(true);
		add(spartaText);
		
		JLabel romeText = new JLabel("Rome");
		romeText.setForeground(Color.BLACK);
		romeText.setFont(f);
		romeText.setBounds(980, 365, 250, 100);
		romeText.setVisible(true);
		add(romeText);
		
		Font fS = new Font("Serif",Font.BOLD, 50);
		
	    selectText = new JLabel("Select a City");
		selectText.setForeground(Color.BLACK);
		selectText.setFont(f);
		selectText.setBounds(550, 528, 1000, 100);
		selectText.setVisible(true);
		add(selectText);
		
		Font fPN = new Font("Serif",Font.BOLD, 30);
		playerInput = new JTextArea();
		playerInput.setBounds(450,620,500,40);
		playerInput.setFont(fPN);
		playerInput.setVisible(false);
		add(playerInput);
		
		    BGImage = new JLabel(new ImageIcon("option2.jpg"));
			BGImage.setSize(1387,763);
			BGImage.setLayout(new FlowLayout());
			add(BGImage);
			
		
	}
	public void selectCityBorder(String selectedCity) {
		if(selectedCity.equals("Cairo")) {
			cairoBtn.setBorder(BorderFactory.createLineBorder(Color.RED,3));
			cairoBtn.setBorderPainted(true);
			spartaBtn.setBorderPainted(false);
			romeBtn.setBorderPainted(false);
		}
		if(selectedCity.equals("Rome")) {
			cairoBtn.setBorderPainted(false);
			spartaBtn.setBorderPainted(false);
			romeBtn.setBorder(BorderFactory.createLineBorder(Color.RED,3));
			romeBtn.setBorderPainted(true);
		}
		if(selectedCity.equals("Sparta")) {
			cairoBtn.setBorderPainted(false);
			spartaBtn.setBorderPainted(true);
			spartaBtn.setBorder(BorderFactory.createLineBorder(Color.RED,3));
			romeBtn.setBorderPainted(false);
		}
		remove(BGImage);
		add(playerInput);
		add(BGImage);
	}
	public JButton getCairoBtn() {
		return cairoBtn;
	}
	public void setCairoBtn(JButton cairoBtn) {
		this.cairoBtn = cairoBtn;
	}
	public JButton getSpartaBtn() {
		return spartaBtn;
	}
	public void setSpartaBtn(JButton spartaBtn) {
		this.spartaBtn = spartaBtn;
	}
	public JButton getRomeBtn() {
		return romeBtn;
	}
	public void setRomeBtn(JButton romeBtn) {
		this.romeBtn = romeBtn;
	}
	public Controller getC() {
		return c;
	}
	public void setC(Controller c) {
		this.c = c;
	}
	public JLabel getSelectText() {
		return selectText;
	}
	public void setSelectText(JLabel selectText) {
		this.selectText = selectText;
	}
	public JTextArea getPlayerInput() {
		return playerInput;
	}
	public void setPlayerInput(JTextArea playerInput) {
		this.playerInput = playerInput;
	}
	
}
