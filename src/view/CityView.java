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
import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import controller.Controller;
import engine.City;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Unit;
import javax.swing.JComboBox;

public class CityView extends JPanel{

	JList ArmyList;
	JList UnitList;
	JButton marketBtn;
	JButton farmBtn;
	JButton archeryRangeBtn;
	JButton stableBtn;
	JButton barracksBtn;
	JTextArea buildingInfo;
	JButton BuildBtn;
	JButton RecruitBtn;
	JButton UpgradeBuildingBtn;
	ArrayList <Unit> ArmyUnits;
	ArrayList <Army> CA;
	ArrayList <Army> CityArmies;
	ArrayList <Army> CityArmiesReloc;
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
	JButton BackCityView;
	JPanel armyPanel;
	JButton EndTurnBtn;
	
	
	public CityView(Controller c, City city) {
		
		setBounds(0,0,1387,763);
		setLayout(null);

		setVisualization();
		
		revalidate();
		repaint();
		
		marketBtn.setActionCommand("marketBtn");
		marketBtn.addActionListener(c);
		
		farmBtn.setActionCommand("farmBtn");
		farmBtn.addActionListener(c);

		archeryRangeBtn.setActionCommand("archeryRangeBtn");
		archeryRangeBtn.addActionListener(c);
		
		stableBtn.setActionCommand("stableBtn");
		stableBtn.addActionListener(c);
		
		barracksBtn.setActionCommand("barracksBtn");
		barracksBtn.addActionListener(c);
		
		RelocateBtn.setActionCommand("relocateBtn");
		RelocateBtn.addActionListener(c);
		
		
		EndTurnBtn.setActionCommand("endTurnBtn");
		EndTurnBtn.addActionListener(c);
		
		BackCityView.setActionCommand("backCityViewBtn");
		BackCityView.addActionListener(c);
			
		BuildBtn.setActionCommand("buildBtn");
		BuildBtn.addActionListener(c);

	    initiateArmyBtn.setActionCommand("initiateBtn");
	    initiateArmyBtn.addActionListener(c);		
		
		UpgradeBuildingBtn.setActionCommand("upgradeBuildingBtn");
		UpgradeBuildingBtn.addActionListener(c);

		
	    RecruitBtn.setActionCommand("recruitBtn");
	    RecruitBtn.addActionListener(c);
	    
	    //relocateToArmyCombo.setActionCommand("relocateCombo");
	    //relocateToArmyCombo.addActionListener(c);

		ArmyList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				DefaultListModel<String> model = new DefaultListModel<>();

				
				JList list = (JList)e.getSource();
				int index = list.getSelectedIndex();
				
				if(index == -1) 
					return;
				
				
				
				CityArmiesReloc = new ArrayList<Army>();
				
				ArmyUnits = CityArmies.get(index).getUnits();
				for(int i=0;i<ArmyUnits.size();i++) {
					model.addElement("Unit "+(i+1));
				}

				
				UnitList.removeAll();

				UnitList.setModel(model);
				
				if(model.getSize()> 0)
					UnitList.setSelectedIndex(0);
				
				
				relocateToArmyCombo.removeAllItems();
				
				
				CityArmiesReloc.clear();
				
				relocateToArmyCombo.addItem("");
				
				if(index > 0)
				{
					relocateToArmyCombo.addItem("Defending Army");
					CityArmiesReloc.add(city.getDefendingArmy());
				}
				
				for(int i=0;i<CA.size();i++) {
					if(CA.get(i).getCurrentLocation().equals(city.getName()) && CA.get(i) != CityArmies.get(index)) {
						CityArmiesReloc.add(CA.get(i));
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
				UnitInfoTextArea.setText("");
				
				JList list = (JList)e.getSource();
				if(list.getSelectedIndex() == -1)
					return;
				
				if(ArmyList.getSelectedIndex() == 0)
					initiateArmyBtn.setVisible(true);
				
				if(relocateToArmyCombo.getSelectedIndex() > 0)
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
			
					
					if(relocateToArmyCombo.getSelectedIndex() > 0)
						RelocateBtn.setVisible(true);
					else
						RelocateBtn.setVisible(false);
						
				}
				
		});


		refresh(city, c);
		
		
	}
	
	
	private void setVisualization() {
		setLayout(null);

		
		
		JScrollPane JScrollArmy = new JScrollPane();
		JScrollArmy.setBounds(172, 424, 232, 104);
		add(JScrollArmy);
		
		 ArmyList = new JList();
		JScrollArmy.setViewportView(ArmyList);
		

		ArmyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
		
		JScrollPane JScrollUnit = new JScrollPane();
		JScrollUnit.setBounds(438, 424, 145, 95);
		
		add(JScrollUnit);
		
		 UnitList = new JList();
		
		JScrollUnit.setViewportView(UnitList);
		
		
		
	    buildingInfo = new JTextArea();
		buildingInfo.setBounds(172, 189, 761, 196);
		buildingInfo.setEditable(false);
		add(buildingInfo);
		
		marketBtn = new JButton("Market");
		marketBtn.setBounds(193, 157, 80, 21);
		add(marketBtn);
		
		
		farmBtn = new JButton("Farm");
		farmBtn.setBounds(294, 157, 80, 21);
		add(farmBtn);
		
		archeryRangeBtn = new JButton("ArcheryRange");
		archeryRangeBtn.setBounds(598, 157, 115, 21);
		add(archeryRangeBtn);
		
		stableBtn = new JButton("Stable");
		stableBtn.setBounds(733, 157, 80, 21);
		add(stableBtn);
		
		barracksBtn = new JButton("Barracks");
		barracksBtn.setBounds(835, 157, 90, 21);
		add(barracksBtn);
		
		UnitInfoTextArea = new JTextArea();
		UnitInfoTextArea.setBounds(614, 424, 323, 90);
		add(UnitInfoTextArea);
		
		JLabel ArmyLabel = new JLabel("Armies:");
		ArmyLabel.setBounds(172, 405, 45, 13);
		add(ArmyLabel);
		
		UnitsLabel = new JLabel("Units :");
		UnitsLabel.setBounds(438, 401, 45, 13);
		add(UnitsLabel);
		
		UnitInfoLabel = new JLabel("Unit Info:");
		UnitInfoLabel.setBounds(614, 401, 90, 13);
		add(UnitInfoLabel);

		
		LabelRelocateTo = new JLabel("Relocate to:");
		LabelRelocateTo.setBounds(614, 541, 69, 13);
		add(LabelRelocateTo);
		
		relocateToArmyCombo = new JComboBox();
		relocateToArmyCombo.setBounds(686, 534, 163, 27);
		add(relocateToArmyCombo);
		
		RelocateBtn = new JButton("Relocate");
		RelocateBtn.setBounds(859, 537, 115, 21);
		add(RelocateBtn);
		
		ArmyInfoTextArea = new JTextArea();
		ArmyInfoTextArea.setBounds(172, 561, 236, 72);
		add(ArmyInfoTextArea);
		
		ArmyInfoLabel = new JLabel("Army Info");
		ArmyInfoLabel.setBounds(172, 538, 101, 13);
		add(ArmyInfoLabel);
		
		armyPanel = new JPanel();
		armyPanel.setLayout(null);
		armyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		armyPanel.setBackground(new Color(170, 154, 109));
		armyPanel.setBounds(1116, 0, 168, 763);
		add(armyPanel);
		
		EndTurnBtn = new JButton("End Turn");
		EndTurnBtn.setActionCommand("endTurnBtn");
		EndTurnBtn.setBounds(10, 11, 148, 90);
		armyPanel.add(EndTurnBtn);
		
		JLabel mapImage = new JLabel(new ImageIcon("option2.jpg"));
		 mapImage.setSize(1387,763);
	     mapImage.setLayout(new FlowLayout());
		 add(mapImage);
		
		 
		BackCityView = new JButton("Back to World Map");
		BackCityView.setBounds(10, 128, 148, 38);
		armyPanel.add(BackCityView);
		
		BuildBtn = new JButton("Build");
		BuildBtn.setBounds(10, 215, 148, 30);
		armyPanel.add(BuildBtn);
	
	    UpgradeBuildingBtn = new JButton("Upgrade building");
	    UpgradeBuildingBtn.setBounds(10, 256, 148, 30);
	    armyPanel.add(UpgradeBuildingBtn);
	    
	    
	    RecruitBtn = new JButton("Recruit");
	    RecruitBtn.setBounds(10, 302, 148, 30);
	    armyPanel.add(RecruitBtn);

        initiateArmyBtn = new JButton("Initiate Army");
        initiateArmyBtn.setBounds(10, 345, 148, 27);
        armyPanel.add(initiateArmyBtn);


	}

	public void refresh(City city,Controller c) {
	
		
		Army defArmy = city.getDefendingArmy();

		
		if(defArmy == null)
			return;
		
		int index = ArmyList.getSelectedIndex();
		
		ArmyList.removeAll();
		UnitList.removeAll();
		UnitInfoTextArea.setText("");
		
		model = new DefaultListModel<>();
		
		CA =c.getModel().getPlayer().getControlledArmies();
		
		CityArmies = new ArrayList<Army>();
		ArmyUnits = new ArrayList<Unit>();
		
		CityArmies.add(defArmy);
		
		model.addElement("Defending Army");
		
		initiateArmyBtn.setVisible(false);
		RelocateBtn.setVisible(false);
		
		
		for(int i=0;i<CA.size();i++) {
			if(CA.get(i).getCurrentLocation().equals(city.getName())) {
				CityArmies.add(CA.get(i));
				model.addElement("Controlled Army " + (i+1));
				
			}
		}
		
		
		ArmyList.setModel(model);
		
		if(index > -1)
			ArmyList.setSelectedIndex(index);
		else
			ArmyList.setSelectedIndex(0);
	
		
			
	}
	public JTextArea getBuildingInfo() {
		return buildingInfo;
	}

	public void setBuildingInfo(JTextArea buildingInfo) {
		this.buildingInfo = buildingInfo;
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
			return CityArmiesReloc.get(index - 1);
		
		return null;
		
	}
public void refreshBuildingInfo(City cityC, String type) {
	ArrayList <MilitaryBuilding> Military=cityC.getMilitaryBuildings();
	ArrayList <EconomicBuilding> EcoBuildings=cityC.getEconomicalBuildings();
	MilitaryBuilding Building =null;
	
	//ArcheryRange
	if(type.equals("ArcheryRange")) {
	for(int i=0; i<Military.size();i++) {
		if(Military.get(i) instanceof ArcheryRange) {
			Building=Military.get(i);
		}
	}
	
	if(Building==null) {
		ArcheryRange dummyBuilding = new ArcheryRange();
		int cost = dummyBuilding.getCost();
		getBuildingInfo().setText("You need to Build an Archery Range first.\n"+"Price: "+cost);
	}
	else {
		String s = "Archery Range: \n";
		s+="Level: "+Building.getLevel() +"\n";
		s+="Cost of building: "+Building.getCost() +"\n";
		s+="Upgrade cost: "+Building.getUpgradeCost() +"\n";
		s+="Recruitment cost: "+Building.getRecruitmentCost()+"\n";
		getBuildingInfo().setText(s);
	}
	}
	//Barracks
	
	if(type.equals("Barracks")) {
	 Building =null;

	for(int i=0; i<Military.size();i++) {
		if(Military.get(i) instanceof Barracks) {
			Building=Military.get(i);
		}
	}
	
	if(Building==null) {
		Barracks dummyBuilding = new Barracks();
		dummyBuilding.setCoolDown(false);
		int cost = dummyBuilding.getCost();
		getBuildingInfo().setText("You need to Build a Barracks first.\n"+"Price: "+cost);
	}
	else {
		String s = "Barracks: \n";
		s+="Level: "+Building.getLevel() +"\n";
		s+="Cost of building: "+Building.getCost() +"\n";
		s+="Upgrade cost: "+Building.getUpgradeCost() +"\n";
		s+="Recruitment cost: "+Building.getRecruitmentCost()+"\n";
		getBuildingInfo().setText(s);
	}
	}
	//Stable
	
	if(type.equals("Stable")) {
	 Building =null;

		for(int i=0; i<Military.size();i++) {
			if(Military.get(i) instanceof Stable) {
				Building=Military.get(i);
			}
		}
		
		if(Building==null) {
			Stable dummyBuilding = new Stable();
			dummyBuilding.setCoolDown(false);
			int cost = dummyBuilding.getCost();
			getBuildingInfo().setText("You need to Build a Stable first.\n"+"Price: "+cost);
		}
		else {
			String s = "Stable: \n";
			s+="Level: "+Building.getLevel() +"\n";
			s+="Cost of building: "+Building.getCost() +"\n";
			s+="Upgrade cost: "+Building.getUpgradeCost() +"\n";
			s+="Recruitment cost: "+Building.getRecruitmentCost()+"\n";
			getBuildingInfo().setText(s);
		}
	}
		
		//market
	
		if(type.equals("Market")) {
		EconomicBuilding market = null;
		type="Market";
		for(int i=0; i<EcoBuildings.size();i++) {
			if(EcoBuildings.get(i) instanceof Market) {
				market=EcoBuildings.get(i);
			}
		}
		
		if(market==null) {
			Market dummyMarket= new Market();
			dummyMarket.setCoolDown(false);
			int cost = dummyMarket.getCost();
			getBuildingInfo().setText("You need to Build a Market first.\n"+"Price: "+cost);
		}
		else {
			String s = "Market: \n";
			s+="Level: "+market.getLevel() +"\n";
			s+="Cost of building: "+market.getCost() +"\n";
			s+="Upgrade cost: "+market.getUpgradeCost() +"\n";
			getBuildingInfo().setText(s);
		}
		}
		
		//farm
		
		if(type.equals("Farm")) {
		EconomicBuilding farm = null;
		type="Farm";
		for(int i=0; i<EcoBuildings.size();i++) {
			if(EcoBuildings.get(i) instanceof Farm) {
				farm=EcoBuildings.get(i);
			}
		}
		
		if(farm==null) {
			Farm dummyfarm = new Farm();
			dummyfarm.setCoolDown(false);
			int cost = dummyfarm.getCost();
			getBuildingInfo().setText("You need to Build a Farm first.\n"+"Price: "+cost);
		}
		else {
			String s = "Farm: \n";
			s+="Level: "+farm.getLevel() +"\n";
			s+="Cost of building: "+farm.getCost() +"\n";
			s+="Upgrade cost: "+farm.getUpgradeCost() +"\n";
			getBuildingInfo().setText(s);
		}
		}
}


}

	
