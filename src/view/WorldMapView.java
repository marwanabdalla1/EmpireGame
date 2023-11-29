package view;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.Controller;
import engine.Game;
import units.Army;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import engine.City;
public class WorldMapView extends JPanel{
	JLabel mapImage;
	JPanel armyPanel;
	JComboBox<String> idleDropDown;
	JComboBox OutDropDown;
    ArrayList <String> idleArmies;
	ItemListener DDM;
	JButton cairoCityBtn;
	JButton spartaCityBtn;
	JButton romeCityBtn;
	JButton EndTurnBtn;
	
	public WorldMapView(Controller c){
		
		setBounds(0,0,1387,763);
		this.setLayout(null);
			
		
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
			romeText.setBounds(971, 365, 250, 100);
			romeText.setVisible(true);
			add(romeText);
			
			armyPanel = new JPanel();
			armyPanel.setBounds(1106,0,168,763);
			armyPanel.setBackground(Color.decode("#aa9a6d"));
			armyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
			add(armyPanel);
			armyPanel.setLayout(null);
			
			JButton IdleArmyBtn = new JButton("Controlled Armies");
			
			IdleArmyBtn.setBounds(10, 129, 148, 33);
			IdleArmyBtn.setActionCommand("idleArmyBtn");
			IdleArmyBtn.addActionListener(c);
			
			armyPanel.add(IdleArmyBtn);
			
			JButton MarchingArmyBtn = new JButton("Marching Armies");
			MarchingArmyBtn.setActionCommand("marchingArmyBtn");
			MarchingArmyBtn.addActionListener(c);
			MarchingArmyBtn.setBounds(21, 409, 137, 21);
			//armyPanel.add(MarchingArmyBtn);
			
			JButton BesiegingArmyBtn = new JButton("Besieging Armies");
			BesiegingArmyBtn.setActionCommand("besiegingArmyBtn");
			BesiegingArmyBtn.addActionListener(c);
			BesiegingArmyBtn.setBounds(21, 441, 137, 21);
			
			//armyPanel.add(BesiegingArmyBtn);
			
			
			ArrayList <String> ControlledArmies = new ArrayList<String>()/*c.getModel().getPlayer().getControlledArmies()*/;
		
	
			
			cairoCityBtn = new JButton(new ImageIcon("X-mark.png"));
			cairoCityBtn.setBounds(230, 590, 45, 45);
			cairoCityBtn.setContentAreaFilled(false);
	    	cairoCityBtn.setActionCommand("cairoCity");
	    	cairoCityBtn.setVisible(false);
	    	cairoCityBtn.addActionListener(c);
	    	add(cairoCityBtn);
	    
	    	spartaCityBtn = new JButton(new ImageIcon("X-mark.png"));
	    	spartaCityBtn.setBounds(460,143,45,45);
	    	spartaCityBtn.setContentAreaFilled(false);
	    	spartaCityBtn.setActionCommand("spartaCity");
	    	spartaCityBtn.setVisible(false);
	    	spartaCityBtn.addActionListener(c);
	    	add(spartaCityBtn);

	    
	    	romeCityBtn = new JButton(new ImageIcon("X-mark.png"));
	    	romeCityBtn.setBounds(906, 395, 45, 45);
	    	romeCityBtn.setContentAreaFilled(false);
	    	romeCityBtn.setActionCommand("romeCity");
	    	romeCityBtn.setVisible(false);
	    	romeCityBtn.addActionListener(c);
	    	add(romeCityBtn);
	    	
			 EndTurnBtn = new JButton("End Turn");
			 EndTurnBtn.setBounds(10, 11, 148, 90);
			 EndTurnBtn.addActionListener(c);
			 EndTurnBtn.setActionCommand("endTurnBtn");
		     armyPanel.add(EndTurnBtn);
		     
		     
		     mapImage = new JLabel(new ImageIcon("option2.jpg"));
			 mapImage.setSize(1387,763);
		     mapImage.setLayout(new FlowLayout());
			 add(mapImage);
			 

	}
	public void showControlledCities(Controller c) {
		ArrayList <City> controlledCities = c.getModel().getPlayer().getControlledCities();
		for(int i=0;i<controlledCities.size();i++) {
			if(controlledCities.get(i).getName().equals("Cairo")) {
				getCairoCityBtn().setVisible(true);
			}
			if(controlledCities.get(i).getName().equals("Sparta")) {
				getSpartaCityBtn().setVisible(true);
				
			}
			if(controlledCities.get(i).getName().equals("Rome")) {
				getRomeCityBtn().setVisible(true);
				
			}
	}
	}
	public JPanel getArmyPanel() {
		return armyPanel;
	}

	public void setArmyPanel(JPanel armyPanel) {
		this.armyPanel = armyPanel;
	}

	public JButton getSpartaCityBtn() {
		return spartaCityBtn;
	}

	public void setSpartaCityBtn(JButton spartaCityBtn) {
		this.spartaCityBtn = spartaCityBtn;
	}

	public JButton getRomeCityBtn() {
		return romeCityBtn;
	}

	public void setRomeCityBtn(JButton romeCityBtn) {
		this.romeCityBtn = romeCityBtn;
	}

	public JButton getCairoCityBtn() {
		return cairoCityBtn;
	}

	public void setCairoCityBtn(JButton cairoCityBtn) {
		this.cairoCityBtn = cairoCityBtn;
	}

	public JLabel getMapImage() {
		return mapImage;
	}

	public void setMapImage(JLabel mapImage) {
		this.mapImage = mapImage;
	}
	
	
}
