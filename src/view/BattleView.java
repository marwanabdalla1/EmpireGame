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
import javax.swing.JOptionPane;
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
import javax.swing.SwingConstants;

public class BattleView extends JPanel{

	JList ArmyList;
	JList UnitList;
	JList AttackedCityUnitList;
	
	ArrayList <Unit> ArmyUnits;
	ArrayList <Army> CA;
	ArrayList <Army> Armies;
	Army attackingArmy;
	DefaultListModel<String> model;
	JLabel UnitsLabel;
	JLabel UnitInfoLabel;
    JTextArea UnitInfoTextArea;
	JButton BackArmyViewBtn;
	JButton ResolveBtn;
	JButton AttackBtn;
	JLabel lblAttackedCityArmy;
    JLabel AttackedCityUnitInfoLabel;
	JTextArea AttackedCityUnitInfoTextArea;
	JScrollPane AttackedCityJScrollUnit;
	City attackedCity;
	Unit attackingUnit;
    JButton LaySiegeBtn;
	JTextArea SiegeStatusTextArea;
	JTextArea BattleLogTextArea;
	JScrollPane BattleLogscrollPane;
	
	public Unit getAttackingUnit() {
		return attackingUnit;
	}



	public void setSiegeStatus(String siegeStatus) {
		SiegeStatusTextArea.setText(siegeStatus);
	}



	public Unit getAttackedUnit() {
		return attackedUnit;
	}




	private Unit attackedUnit;
	private JPanel armyPanel;
	private JButton EndTurnBtn;

    
    
	public City getAttackedCity() {
		return attackedCity;
	}


	public BattleView(Controller c, Army attackingArmy, City attackedCity) {
		
		setBounds(0,0,1387,763);
		setLayout(null);

		setVisualization();
		
		revalidate();
		repaint();
		
		this.attackingArmy = attackingArmy;
		this.attackedCity = attackedCity;
		
		BackArmyViewBtn.addActionListener(c);
		BackArmyViewBtn.setActionCommand("battleBackArmyViewBtn");
		
		ResolveBtn.addActionListener(c);
		ResolveBtn.setActionCommand("battleResolveBtn");
		
		AttackBtn.addActionListener(c);
		AttackBtn.setActionCommand("battleAttackBtn");
				
		LaySiegeBtn.addActionListener(c);
		LaySiegeBtn.setActionCommand("laySiegeBtn");
		
		EndTurnBtn.addActionListener(c);
		EndTurnBtn.setActionCommand("endTurnBtn");
		
		
		ArmyList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				DefaultListModel<String> model = new DefaultListModel<>();
				DefaultListModel<String> attackedUnitsModel = new DefaultListModel<>();
				
				JList list = (JList)e.getSource();
				int index = list.getSelectedIndex();
				
				if(index == -1) 
					return;					
				
				ArmyUnits = Armies.get(index).getUnits();
				
				for(int i=0;i<ArmyUnits.size();i++) {
					model.addElement("Unit "+(i+1));
				}

				
				UnitList.setModel(model);
				
				
				if(model.getSize() > 0)
					UnitList.setSelectedIndex(0);
				else
				{
					UnitInfoTextArea.setBackground(Color.red);
					
					UnitInfoTextArea.setForeground(Color.white);
					
					UnitInfoTextArea.setText("All units have died! Unfortunatly you have lost the battle.");
			
					SiegeStatusTextArea.setText("");
				}
				
				
				if(getAttackedCity() != null)
				{
					for(int i=0;i<getAttackedCity().getDefendingArmy().getUnits().size();i++) {
						attackedUnitsModel.addElement("Unit "+(i+1));
					}

				
					AttackedCityUnitList.setModel(attackedUnitsModel);
				}
				
				if(attackedUnitsModel.getSize() > 0)
					AttackedCityUnitList.setSelectedIndex(0);
				else
				{
					AttackedCityUnitInfoTextArea.setBackground(Color.green);
					
					AttackedCityUnitInfoTextArea.setForeground(Color.black);
					
					AttackedCityUnitInfoTextArea.setText("All units of attacked city have died!\nCongratulations you have won the battle.");

					SiegeStatusTextArea.setText("");
					
				}
	
			}
			
			});
		
		UnitList.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				DefaultListModel<String> model = new DefaultListModel<>();
				String type="";

				ResolveBtn.setVisible(false);
				AttackBtn.setVisible(false);
				
				JList list = (JList)e.getSource();
				if(list.getSelectedIndex() == -1)
					return;
				
				
				Unit a = ArmyUnits.get(list.getSelectedIndex());
				
				attackingUnit = a;
				
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
				
				if(attackedCity.getDefendingArmy().getUnits().size() > 0)
				{
					ResolveBtn.setVisible(true);
					AttackBtn.setVisible(true);
				}

			}
			
			});

		AttackedCityUnitList.addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent e) {
				DefaultListModel<String> model = new DefaultListModel<>();
				String type="";

				LaySiegeBtn.setVisible(false);
				ResolveBtn.setVisible(false);
				AttackBtn.setVisible(false);
				
				JList list = (JList)e.getSource();
				
				if(list.getSelectedIndex() == -1)
					return;

				
				Unit a = attackedCity.getDefendingArmy().getUnits().get(list.getSelectedIndex());
				
				attackedUnit = a;
				
				if(a instanceof Archer)
				    type="Archer";
				if(a instanceof Cavalry)
					type="Cavalry";
				if(a instanceof Infantry)
					type="Infantry";
				
				
				AttackedCityUnitInfoTextArea.setText("Type: "+type+
						"\n level: "+a.getLevel()+
						"\n Current soldier Count: "+a.getCurrentSoldierCount()+
						"\n Max soldier count: "+a.getMaxSoldierCount());
				
				
				if(ArmyUnits.size() > 0)
				{
					if(!attackedCity.isUnderSiege()) LaySiegeBtn.setVisible(true);
					ResolveBtn.setVisible(true);
					AttackBtn.setVisible(true);
				}
			}
			
			});

		
		
		refresh(c);
		
		
	}
	
	
	private void setVisualization() {
		setLayout(null);

		
		
		JScrollPane JScrollArmy = new JScrollPane();
		JScrollArmy.setBounds(36, 169, 273, 51);
		add(JScrollArmy);
		
		 ArmyList = new JList();
		JScrollArmy.setViewportView(ArmyList);
		

		ArmyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
		
		JScrollPane JScrollUnit = new JScrollPane();
		JScrollUnit.setBounds(43, 264, 145, 87);
		
		add(JScrollUnit);
		
		 UnitList = new JList();
		
		JScrollUnit.setViewportView(UnitList);
		
		UnitInfoTextArea = new JTextArea();
		UnitInfoTextArea.setBounds(211, 264, 360, 87);
		UnitInfoTextArea.setEditable(false);
		add(UnitInfoTextArea);
		
		JLabel ArmyLabel = new JLabel("Attacking Army:");
		ArmyLabel.setBounds(36, 152, 130, 13);
		add(ArmyLabel);
		
		UnitsLabel = new JLabel("Attacking Army Units :");
		UnitsLabel.setBounds(43, 248, 145, 13);
		add(UnitsLabel);
		
		UnitInfoLabel = new JLabel("Unit Status:");
		UnitInfoLabel.setBounds(211, 248, 90, 13);
		add(UnitInfoLabel);
		
		BackArmyViewBtn = new JButton("Back to Army");
		BackArmyViewBtn.setBounds(382, 46, 392, 51);
		add(BackArmyViewBtn);
		
		ResolveBtn = new JButton("Resolve");
		ResolveBtn.setBounds(739, 398, 115, 31);
		add(ResolveBtn);
		ResolveBtn.setVisible(false);
		
		AttackBtn = new JButton("Attack");
		AttackBtn.setBounds(878, 398, 115, 31);
		add(AttackBtn);
		
		
		lblAttackedCityArmy = new JLabel("Attacked City Army Units :");
		lblAttackedCityArmy.setBounds(43, 385, 155, 13);
		add(lblAttackedCityArmy);
		
		AttackedCityUnitInfoLabel = new JLabel("Unit Status:");
		AttackedCityUnitInfoLabel.setBounds(211, 385, 90, 13);
		add(AttackedCityUnitInfoLabel);
		
		AttackedCityUnitInfoTextArea = new JTextArea();
		AttackedCityUnitInfoTextArea.setText("");
		AttackedCityUnitInfoTextArea.setBounds(211, 401, 360, 87);
		AttackedCityUnitInfoTextArea.setEditable(false);
		add(AttackedCityUnitInfoTextArea);
		
		AttackedCityJScrollUnit = new JScrollPane();
		AttackedCityJScrollUnit.setBounds(43, 401, 145, 87);
		add(AttackedCityJScrollUnit);
		
		AttackedCityUnitList = new JList();
		AttackedCityJScrollUnit.setViewportView(AttackedCityUnitList);
		
		LaySiegeBtn = new JButton("lay Siege");
		LaySiegeBtn.setBounds(584, 398, 125, 31);
		add(LaySiegeBtn);
		
		SiegeStatusTextArea = new JTextArea();
		SiegeStatusTextArea.setBounds(581, 440, 392, 51);
		SiegeStatusTextArea.setEditable(false);
		add(SiegeStatusTextArea);
		
		armyPanel = new JPanel();
		armyPanel.setLayout(null);
		armyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		armyPanel.setBackground(new Color(170, 154, 109));
		armyPanel.setBounds(1116, 0, 168, 763);
		add(armyPanel);
		
		EndTurnBtn = new JButton("End Turn");
		EndTurnBtn.setBounds(10, 11, 148, 90);
		armyPanel.add(EndTurnBtn);
		 
		BattleLogscrollPane = new JScrollPane();
	    BattleLogTextArea = new JTextArea();
	    BattleLogTextArea.setEditable(false);
		BattleLogscrollPane.setBounds(51, 528, 296, 192);
		add(BattleLogscrollPane);
		
		BattleLogscrollPane.setViewportView(BattleLogTextArea);
		
		JLabel mapImage = new JLabel(new ImageIcon("option2.jpg"));
		 mapImage.setSize(1387,763);
	     mapImage.setLayout(new FlowLayout());
		 add(mapImage);
		
		



	}

	public void refresh(Controller c) {
	
		
		int currentArmyIndex;
		int srcUnit;
		int trgtUnit;
		DefaultListModel<String> model;
		DefaultListModel<String> UnitsModel = new DefaultListModel<>();
		DefaultListModel<String> attackedUnitsModel = new DefaultListModel<>();
		
		
		currentArmyIndex = ArmyList.getSelectedIndex();
		
		srcUnit = UnitList.getSelectedIndex();
		trgtUnit = AttackedCityUnitList.getSelectedIndex();
		
		
		attackingUnit = null;
		attackedUnit = null;
		
		ResolveBtn.setVisible(false);
		AttackBtn.setVisible(false);
		
//		ArmyList.removeAll();
	
		
		UnitsModel.clear();
		attackedUnitsModel.clear();
		
		UnitList.setModel(UnitsModel);
		AttackedCityUnitList.setModel(attackedUnitsModel);
		
		
		UnitInfoTextArea.setText("");
		

		model = new DefaultListModel<>();
		
		Armies = new ArrayList<Army>();
		
		ArmyUnits = new ArrayList<Unit>();
		
		CA =c.getModel().getPlayer().getControlledArmies();
		
		
		ArrayList <City> CC = c.getModel().getPlayer().getControlledCities();
		City occupiedCity = null;
		
		boolean isCityOccupied = false;
		
		for(int i=0;i<CC.size();i++) {
			if(CC.get(i).equals(this.getAttackedCity())) {
				isCityOccupied = true;
				occupiedCity=CC.get(i);
				break;
			}
		}
		
		
		if(isCityOccupied)
		{
			this.attackedCity = null;
			Armies.add(occupiedCity.getDefendingArmy());
			model.addElement("Your defending Army");
		}
			
		else
		{
		
			for(int i=0;i<CA.size();i++) {
				if(CA.get(i) == attackingArmy) {
					Armies.add(CA.get(i));
					model.addElement("Controlled Army " + (i+1) + ", attacking city: " + attackingArmy.getCurrentLocation());				
				}
				
			}

		}
		
		ArmyList.setModel(model);
		
		
		
		if(currentArmyIndex > -1)
			ArmyList.setSelectedIndex(currentArmyIndex);
		else
			ArmyList.setSelectedIndex(0);
	
		
		if(srcUnit < UnitList.getModel().getSize())
			UnitList.setSelectedIndex(srcUnit);
		
		if(trgtUnit < AttackedCityUnitList.getModel().getSize())
			AttackedCityUnitList.setSelectedIndex(trgtUnit);
		
		if(attackedCity != null)
		{
			if(attackedCity.isUnderSiege())
			{
				SiegeStatusTextArea.setForeground(Color.blue);
				SiegeStatusTextArea.setText("City under Siege, turn " + (attackedCity.getTurnsUnderSiege() + 1) + " out of " + 3 );
				LaySiegeBtn.setVisible(false);	
			
			}
			
			if(attackedCity.getTurnsUnderSiege()  > 2)
			{
				SiegeStatusTextArea.setForeground(Color.red);
				SiegeStatusTextArea.setText("Run out of maximum Siege turns, your only choice is to attack!" );
				LaySiegeBtn.setVisible(false);
			}
			else if(!attackedCity.isUnderSiege())
			{
				SiegeStatusTextArea.setForeground(Color.red);
				SiegeStatusTextArea.setText("City not under Siege" );
			}
		}
	}

	
	public Unit getCurrentUnit() {
		
		int index = UnitList.getSelectedIndex();
		
		if(index !=-1)
			return ArmyUnits.get(index);
		
		return null;
		
	}



public Army getCurrentArmy() {
	
	int index = ArmyList.getSelectedIndex();
	
	if(index !=-1)
		return Armies.get(index);
	
	return null;
	
}
public Army getCurrentAttackedArmy(Controller c) {
	if(getCurrentArmy()==null)
	return null;
	for(int i=0; i< c.getModel().getAvailableCities().size();i++) {
		if(c.getModel().getAvailableCities().get(i).getName().equals(getCurrentArmy().getCurrentLocation())){
			return c.getModel().getAvailableCities().get(i).getDefendingArmy();
		
		}
		}
	return null;
	}

public void AddToLog(Unit A, String who ,int scBefore) {
	 int currSoldierCount = scBefore;
	currSoldierCount -=  A.getCurrentSoldierCount();
	if(A.getCurrentSoldierCount()<=0)
		getBattleLogTextArea().setText(getBattleLogTextArea().getText()+"\n"+who+": Lost a Unit of "+"lvl "+A.getLevel()+" "+getUnitType(A));
		else {
			getBattleLogTextArea().setText(getBattleLogTextArea().getText()+"\n"+who+": -"+currSoldierCount+" lvl "+A.getLevel()+" "+getUnitType(A)+"\n");
		}
}



public JTextArea getBattleLogTextArea() {
	return BattleLogTextArea;
}



public void setBattleLogTextArea(JTextArea battleLogTextArea) {
	BattleLogTextArea = battleLogTextArea;
}
public String getUnitType(Unit unit) {
	if(unit instanceof Archer)
	return "archers";
	if(unit instanceof Cavalry)
		return "cavalries";
	if(unit instanceof Infantry)
		return "infantries";
	return "";
	
}

}

	
