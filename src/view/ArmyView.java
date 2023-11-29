package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import buildings.Barracks;
import controller.Controller;
import engine.City;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;
import javax.swing.JComboBox;

public class ArmyView extends JPanel{

	JList ArmyList;
	JList UnitList;
	ArrayList <Unit> ArmyUnits;
	ArrayList <Army> CA;
	ArrayList <Army> Armies;
	ArrayList <Army> ArmiesReloc;
	DefaultListModel<String> model;
	JLabel UnitsLabel;
	JLabel UnitInfoLabel;
	JTextArea UnitInfoTextArea;
	JLabel LabelRelocateTo;
	JComboBox relocateToArmyCombo;
	JButton RelocateBtn;
	JButton initiateArmyBtn;
	JLabel ArmyInfoLabel;
	JTextArea ArmyInfoTextArea;
	JButton BackArmyViewBtn;
	JComboBox TargetCitiesCombo;
	JButton SetTargetBtn;
	ArrayList <City> TrgtCities;
    JButton AttackBtn;
    JButton EndTurnBtn;
    
    private boolean currentArmyinOrigin=false;
    
    
	public ArmyView(Controller c) {
		
		setBounds(0,0,1387,763);
		setLayout(null);

		setVisualization();
		
		revalidate();
		repaint();
		
		//relocateToArmyCombo.setActionCommand("relocateCombo");
		//relocateToArmyCombo.addActionListener(c);
		
		initiateArmyBtn.setActionCommand("initiateBtn");
		initiateArmyBtn.addActionListener(c);
		
		RelocateBtn.setActionCommand("relocateArmyViewBtn");
		RelocateBtn.addActionListener(c);
		
		BackArmyViewBtn.addActionListener(c);
		BackArmyViewBtn.setActionCommand("backArmyViewBtn");
		
		SetTargetBtn.addActionListener(c);
		SetTargetBtn.setActionCommand("setTargetBtn");
		
		AttackBtn.addActionListener(c);
		AttackBtn.setActionCommand("startAttackBtn");
		
		EndTurnBtn.setActionCommand("endTurnBtn");
		EndTurnBtn.addActionListener(c);
		
		

		
		ArmyList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				DefaultListModel<String> model = new DefaultListModel<>();

				
				JList list = (JList)e.getSource();
				int index = list.getSelectedIndex();
				
				AttackBtn.setVisible(false);
				currentArmyinOrigin = false;
				if(index == -1) 
					return;
				
				
				ArmyInfoTextArea.setText("");
				if(Armies.get(index).getCurrentStatus()==Status.IDLE){
					ArmyInfoTextArea.setText("Current location: "+Armies.get(index).getCurrentLocation());
					
					//NA
					if(Armies.get(index).getTarget() != "")
					{
						ArmyInfoTextArea.setText(ArmyInfoTextArea.getText() + "\n Target Location: " + Armies.get(index).getTarget() + "\n Distance left to reach Target: "+ Armies.get(index).getDistancetoTarget());
							
					}
					
					for(int i=0;i<c.getModel().getPlayer().getControlledCities().size();i++) {
						if((Armies.get(index).getCurrentLocation().equals(c.getModel().getPlayer().getControlledCities().get(i).getName()))){
							currentArmyinOrigin=true;
							break;
						}
				 	}
					
					if(!currentArmyinOrigin && Armies.get(index).getDistancetoTarget() == 0)
					{
						AttackBtn.setVisible(true);
						ArmyInfoTextArea.setForeground(Color.red);
					}
					else
						ArmyInfoTextArea.setForeground(Color.black);

					
				}
				if(Armies.get(index).getCurrentStatus()==Status.MARCHING) {
					ArmyInfoTextArea.setText("Marching to City: "+Armies.get(index).getTarget()+
							"\n Distance left to reach Target: "+Armies.get(index).getDistancetoTarget());
				}
				if(Armies.get(index).getCurrentStatus()==Status.BESIEGING) {
					City besieged=null;
					for(int i=0;i<c.getModel().getAvailableCities().size();i++){
						if(c.getModel().getAvailableCities().get(i).getName().equals(Armies.get(index).getCurrentLocation())) {
							 besieged = c.getModel().getAvailableCities().get(i);
						}
					}	
					if(besieged!=null) {
						ArmyInfoTextArea.setText("Besieged city: "+Armies.get(index).getCurrentLocation()+
							"\n Turns under siege: "+ besieged.getTurnsUnderSiege());
			}
				}
				
				ArmiesReloc = new ArrayList<Army>();
				
				ArmyUnits = Armies.get(index).getUnits();
				for(int i=0;i<ArmyUnits.size();i++) {
					model.addElement("Unit "+(i+1));
				}

				
				UnitList.removeAll();

				UnitList.setModel(model);
				
				
				if(model.getSize() > 0)
					UnitList.setSelectedIndex(0);
				else
				{
					AttackBtn.setVisible(false);

				}
				
				
				relocateToArmyCombo.removeAllItems();
				
				
				ArmiesReloc.clear();
				
				relocateToArmyCombo.addItem("");
				

				for(int i=0;i<CA.size();i++) {
					if(CA.get(i) != Armies.get(index) && CA.get(i).getTarget() == "" && Armies.get(index).getTarget() == "") {
						ArmiesReloc.add(CA.get(i));
						relocateToArmyCombo.addItem("Controlled Army " + (i+1));
						
					}
				}
	
			}
			
			});
		
		UnitList.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				DefaultListModel<String> model = new DefaultListModel<>();
				String type="";
				RelocateBtn.setVisible(false);
				initiateArmyBtn.setVisible(false);
				
				JList list = (JList)e.getSource();
				if(list.getSelectedIndex() == -1)
					return;
				
				if(ArmyList.getSelectedIndex() == 0)
					initiateArmyBtn.setVisible(true);
				
				if(relocateToArmyCombo.getSelectedIndex() > 0 && currentArmyinOrigin )
					RelocateBtn.setVisible(true);
				else
					RelocateBtn.setVisible(false);
				
				
				Unit a = ArmyUnits.get(list.getSelectedIndex());
				if(a instanceof Archer)
				    type="Archer";
				if(a instanceof Cavalry)
					type="Cavalry";
				if(a instanceof Infantry)
					type="Infantry";
				
				
				UnitInfoTextArea.setText("Type: "+type+
						"\n level: "+a.getLevel()+
						"\n Current soldier Count: "+a.getCurrentSoldierCount()+
						"\n Max soldier count: "+a.getMaxSoldierCount());
				
				

			}
			
			});
		
		relocateToArmyCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
					
					if(relocateToArmyCombo.getSelectedIndex() > 0 && currentArmyinOrigin)
						RelocateBtn.setVisible(true);
					else
						RelocateBtn.setVisible(false);
						
				}
				
		});


		refresh(c.getSelectedArmyStatus(), c);
		
		
	}
	
	
	private void setVisualization() {
		setLayout(null);
		
		TargetCitiesCombo = new JComboBox();
		TargetCitiesCombo.setBounds(434, 425, 159, 27);
		add(TargetCitiesCombo);

		
		
		JScrollPane JScrollArmy = new JScrollPane();
		JScrollArmy.setBounds(61, 247, 232, 104);
		add(JScrollArmy);
		
		 ArmyList = new JList();
		JScrollArmy.setViewportView(ArmyList);
		

		ArmyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
		
		JScrollPane JScrollUnit = new JScrollPane();
		JScrollUnit.setBounds(327, 247, 145, 104);
		
		add(JScrollUnit);
		
		 UnitList = new JList();
		
		JScrollUnit.setViewportView(UnitList);
		
		UnitInfoTextArea = new JTextArea();
		UnitInfoTextArea.setBounds(503, 247, 360, 90);
		add(UnitInfoTextArea);
		
		JLabel ArmyLabel = new JLabel("Armies:");
		ArmyLabel.setBounds(61, 228, 45, 13);
		add(ArmyLabel);
		
		UnitsLabel = new JLabel("Units :");
		UnitsLabel.setBounds(327, 224, 45, 13);
		add(UnitsLabel);
		
		UnitInfoLabel = new JLabel("Unit Info:");
		UnitInfoLabel.setBounds(503, 224, 90, 13);
		add(UnitInfoLabel);

		
		LabelRelocateTo = new JLabel("Relocate to:");
		LabelRelocateTo.setBounds(354, 394, 69, 13);
		add(LabelRelocateTo);
		
		relocateToArmyCombo = new JComboBox();
		relocateToArmyCombo.setBounds(430, 387, 163, 27);
		add(relocateToArmyCombo);
		
		RelocateBtn = new JButton("Relocate");
		RelocateBtn.setBounds(615, 390, 115, 27);
		add(RelocateBtn);
		
        initiateArmyBtn = new JButton("Initiate Army");
		initiateArmyBtn.setBounds(503, 394, 336, 27);
		
		
		ArmyInfoTextArea = new JTextArea();
		ArmyInfoTextArea.setBounds(61, 384, 236, 72);
		ArmyInfoTextArea.setEditable(false);
		add(ArmyInfoTextArea);
		
		ArmyInfoLabel = new JLabel("Army Info");
		ArmyInfoLabel.setBounds(61, 361, 101, 13);
		add(ArmyInfoLabel);
		
		BackArmyViewBtn = new JButton("Back to World Map");
		BackArmyViewBtn.setBounds(304, 42, 392, 51);
		add(BackArmyViewBtn);
		
		SetTargetBtn = new JButton("Set target");
		SetTargetBtn.setBounds(615, 425, 115, 27);
		add(SetTargetBtn);
		
		AttackBtn = new JButton("Go to Battle");
		AttackBtn.setBounds(313, 128, 383, 51);
		add(AttackBtn);
		AttackBtn.setVisible(false);
		
		JLabel TargetLabel = new JLabel("Choose a target:");
		TargetLabel.setBounds(337, 425, 94, 26);
		add(TargetLabel);
		
		JPanel armyPanel = new JPanel();
		armyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		armyPanel.setBackground(new Color(170, 154, 109));
		armyPanel.setBounds(1116, 0, 168, 763);
		add(armyPanel);
		armyPanel.setLayout(null);
		
		EndTurnBtn = new JButton("End Turn");
		EndTurnBtn.setBounds(10, 11, 148, 90);
		armyPanel.add(EndTurnBtn);
		
		JLabel mapImage = new JLabel(new ImageIcon("option2.jpg"));
		 mapImage.setSize(1387,763);
	     mapImage.setLayout(new FlowLayout());
		 add(mapImage);

	}

	public void refresh(Status status,Controller c) {
	
		
		int currentArmyIndex;
		
		currentArmyIndex = ArmyList.getSelectedIndex();
		
		ArmyList.removeAll();
		UnitList.removeAll();
		UnitInfoTextArea.setText("");
		ArmyInfoTextArea.setText("");
		TargetCitiesCombo.removeAllItems();
		
		AttackBtn.setVisible(false);
		
		model = new DefaultListModel<>();
		
		CA =c.getModel().getPlayer().getControlledArmies();
		
		Armies = new ArrayList<Army>();
		
		ArmyUnits = new ArrayList<Unit>();
		

		
		initiateArmyBtn.setVisible(false);
		RelocateBtn.setVisible(false);
		
		
		for(int i=0;i<CA.size();i++) {
			//if(CA.get(i).getCurrentStatus() == status) {
				Armies.add(CA.get(i));
				model.addElement("Controlled Army " + (i+1));
				
			//}
		}
		
		
		ArmyList.setModel(model);
		
		if(currentArmyIndex > -1)
			ArmyList.setSelectedIndex(currentArmyIndex);
		else
			ArmyList.setSelectedIndex(0);
		
		ArrayList <City>AVCities = new ArrayList<City>();
		ArrayList <City>CTRCities = new ArrayList<City>();
	AVCities = c.getModel().getAvailableCities();
	CTRCities = c.getModel().getPlayer().getControlledCities();
	TrgtCities =  new ArrayList<City>();
	TargetCitiesCombo.addItem("");
	
	boolean flag=false;
	
	for(int i=0;i<AVCities.size();i++) {
		flag=false;
		for(int j=0;j<CTRCities.size();j++) {
			if((AVCities.get(i).equals(CTRCities.get(j)))){
				flag=true;
				}
			}
		if(!flag) {
			TrgtCities.add(AVCities.get(i));
			TargetCitiesCombo.addItem(AVCities.get(i).getName());
			}
		}
		
		
		
	}

	
	public Unit getCurrentUnit() {
		
		int index = UnitList.getSelectedIndex();
		
		if(index !=-1)
			return ArmyUnits.get(index);
		
		return null;
		
	}
public Army getRelocateToArmy() {
		
		int index = relocateToArmyCombo.getSelectedIndex();
		
		if(index > 0)
			return ArmiesReloc.get(index - 1);
		
		return null;
		
	}


public String getTargetName() {
	int index = TargetCitiesCombo.getSelectedIndex();
	if(index > 0)
		return TrgtCities.get(index-1).getName();
	return null;
	
}

public Army getCurrentArmy() {
	
	int index = ArmyList.getSelectedIndex();
	
	if(index !=-1)
		return Armies.get(index);
	
	return null;
	
}
}

	
