package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import engine.Player;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyFireException;
import exceptions.MaxCapacityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import units.Army;
import units.Status;
import units.Unit;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import engine.Game;
import view.ArmyView;
import view.BattleView;
import view.CityView;
import view.GameOverView;
import view.StartingView;
import view.View;
import view.WorldMapView;
import engine.City;

public class Controller implements ActionListener{
	View view;
	Game Model;
	
	String city="";
	StartingView startingview;
	WorldMapView worldmapview;
	ArmyView armyview=null;
	CityView cityview=null;
	BattleView battleview=null;
	GameOverView gameoverview;
	City cityC;
	String type="";
	String type1="";
	Status selectedArmyStatus;
	
	public Controller() {
		view = new View();
		startingview = new StartingView(this);
		view.add(startingview);
		view.revalidate();
		view.repaint();
		view.getStatus().setText("HI Nayer");
		Font fS = new Font("Serif",Font.BOLD, 17);
		view.getStatus().setFont(fS);
		view.getStatus().setForeground(Color.WHITE);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
		JButton b= (JButton) e.getSource();
			
		if(b.getActionCommand().equals("cairoBtn")) {
			city= "Cairo";
			b.setBorderPainted(true);
			startingview.selectCityBorder(city);
		    startingview.getSelectText().setText("Player name");
		    startingview.getPlayerInput().setVisible(true);
		}
		if(b.getActionCommand().equals("spartaBtn")) {
			city= "Sparta";
			b.setBorderPainted(true);
			startingview.selectCityBorder(city);
			startingview.getSelectText().setText("Player name");
			startingview.getPlayerInput().setVisible(true);
		}
		if(b.getActionCommand().equals("romeBtn")) {
			city= "Rome";
			b.setBorderPainted(true);
			startingview.selectCityBorder(city);
			startingview.getSelectText().setText("Player name");
			startingview.getPlayerInput().setVisible(true);
		}
		if(b.getActionCommand().equals("Start")) {
			String player= startingview.getPlayerInput().getText();
			if(player.contentEquals("") && city.contentEquals("") ) {
				JOptionPane.showMessageDialog(null, "Please choose a name for your player and a city.");
			}
			else {
				if(player.contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Please choose a name for your player.");
				}
				else {
					if(city.contentEquals("")) {
						JOptionPane.showMessageDialog(null, "Please choose a city by clicking on one of the Xs above.");
					}
					else {
						try {
							Model=new Game(player,city);
							Model.getPlayer().setTreasury(5000);
							view.getStatus().setVisible(true);
							view.getStatus().setText("Name: "+Model.getPlayer().getName()+"\n Turn Count: "+Model.getCurrentTurnCount()+"\n Treasury: "+Model.getPlayer().getTreasury()+"\n Food: "+Model.getPlayer().getFood());;
							view.remove(startingview);
							worldmapview = new WorldMapView(this);
							ArrayList <City> controlledCities = Model.getPlayer().getControlledCities();
							worldmapview.remove(worldmapview.getMapImage());
							for(int i=0;i<controlledCities.size();i++) {
								if(controlledCities.get(i).getName().equals("Cairo")) {
									worldmapview.getCairoCityBtn().setVisible(true);;
									cityC = controlledCities.get(i);
								}
								if(controlledCities.get(i).getName().equals("Sparta")) {
									worldmapview.getSpartaCityBtn().setVisible(true);;
									cityC = controlledCities.get(i);
								}
								if(controlledCities.get(i).getName().equals("Rome")) {
									worldmapview.getRomeCityBtn().setVisible(true);;
									cityC = controlledCities.get(i);
								}
								
							}
							worldmapview.add(worldmapview.getMapImage());
							
							view.add(worldmapview);
	
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		
			
		}
		if(b.getActionCommand().equals("cairoCity")) {
			ArrayList <City> controlledCities = Model.getPlayer().getControlledCities();
			for(int i=0;i<controlledCities.size();i++) {
				if(controlledCities.get(i).getName().equals("Cairo")) {
					cityC = controlledCities.get(i);
				}
			}
			cityview = new CityView(this,cityC);
			view.remove(worldmapview);
			view.add(cityview);
		}

		if(b.getActionCommand().equals("spartaCity")) {
			ArrayList <City> controlledCities = Model.getPlayer().getControlledCities();
			for(int i=0;i<controlledCities.size();i++) {
				if(controlledCities.get(i).getName().equals("Sparta")) {
					cityC = controlledCities.get(i);
				}
			}

			cityview = new CityView(this,cityC);
			view.remove(worldmapview);
			view.add(cityview);
		}
		
		if(b.getActionCommand().equals("romeCity")) {
			ArrayList <City> controlledCities = Model.getPlayer().getControlledCities();
			for(int i=0;i<controlledCities.size();i++) {
				if(controlledCities.get(i).getName().equals("Rome")) {
					cityC = controlledCities.get(i);
				}
			}
			cityview = new CityView(this,cityC);
			view.remove(worldmapview);
			view.add(cityview);
		}
		
		if(b.getActionCommand().equals("marketBtn")) {
			ArrayList <EconomicBuilding> EcoBuildings=cityC.getEconomicalBuildings();
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
				cityview.getBuildingInfo().setText("You need to Build a Market first.\n"+"Price: "+cost);
			}
			else {
				String s = "Market: \n";
				s+="Level: "+market.getLevel() +"\n";
				s+="Cost of building: "+market.getCost() +"\n";
				s+="Upgrade cost: "+market.getUpgradeCost() +"\n";
				cityview.getBuildingInfo().setText(s);
			}
			
		}
		
		
		if(b.getActionCommand().equals("farmBtn")) {
			ArrayList <EconomicBuilding> EcoBuildings=cityC.getEconomicalBuildings();
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
				cityview.getBuildingInfo().setText("You need to Build a Farm first.\n"+"Price: "+cost);
			}
			else {
				String s = "Farm: \n";
				s+="Level: "+farm.getLevel() +"\n";
				s+="Cost of building: "+farm.getCost() +"\n";
				s+="Upgrade cost: "+farm.getUpgradeCost() +"\n";
				cityview.getBuildingInfo().setText(s);
			}
			
		}
		
		if(b.getActionCommand().equals("archeryRangeBtn")) {
			ArrayList <MilitaryBuilding> Military=cityC.getMilitaryBuildings();
			MilitaryBuilding Building =null;

			type="ArcheryRange";
			type1="archer";
			for(int i=0; i<Military.size();i++) {
				if(Military.get(i) instanceof ArcheryRange) {
					Building=Military.get(i);
				}
			}
			
			if(Building==null) {
				ArcheryRange dummyBuilding = new ArcheryRange();
				dummyBuilding.setCoolDown(false);
				int cost = dummyBuilding.getCost();
				cityview.getBuildingInfo().setText("You need to Build an Archery Range first.\n"+"Price: "+cost);
			}
			else {
				String s = "Archery Range: \n";
				s+="Level: "+Building.getLevel() +"\n";
				s+="Cost of building: "+Building.getCost() +"\n";
				s+="Upgrade cost: "+Building.getUpgradeCost() +"\n";
				s+="Recruitment cost: "+Building.getRecruitmentCost()+"\n";
				cityview.getBuildingInfo().setText(s);
			}
			
		}
		
		if(b.getActionCommand().equals("barracksBtn")) {
			ArrayList <MilitaryBuilding> Military=cityC.getMilitaryBuildings();
			MilitaryBuilding Building =null;

			type="Barracks";
			type1="infantry";
			for(int i=0; i<Military.size();i++) {
				if(Military.get(i) instanceof Barracks) {
					Building=Military.get(i);
				}
			}
			
			if(Building==null) {
				Barracks dummyBuilding = new Barracks();
				dummyBuilding.setCoolDown(false);
				int cost = dummyBuilding.getCost();
				cityview.getBuildingInfo().setText("You need to Build a Barracks first.\n"+"Price: "+cost);
			}
			else {
				String s = "Barracks: \n";
				s+="Level: "+Building.getLevel() +"\n";
				s+="Cost of building: "+Building.getCost() +"\n";
				s+="Upgrade cost: "+Building.getUpgradeCost() +"\n";
				s+="Recruitment cost: "+Building.getRecruitmentCost()+"\n";
				cityview.getBuildingInfo().setText(s);
			}
			
		}
		
		if(b.getActionCommand().equals("stableBtn")) {
			ArrayList <MilitaryBuilding> Military=cityC.getMilitaryBuildings();
			MilitaryBuilding Building =null;

			type="Stable";
			type1="cavalry";
			for(int i=0; i<Military.size();i++) {
				if(Military.get(i) instanceof Stable) {
					Building=Military.get(i);
				}
			}
			
			if(Building==null) {
				Stable dummyBuilding = new Stable();
				dummyBuilding.setCoolDown(false);
				int cost = dummyBuilding.getCost();
				cityview.getBuildingInfo().setText("You need to Build a Stable first.\n"+"Price: "+cost);
			}
			else {
				String s = "Stable: \n";
				s+="Level: "+Building.getLevel() +"\n";
				s+="Cost of building: "+Building.getCost() +"\n";
				s+="Upgrade cost: "+Building.getUpgradeCost() +"\n";
				s+="Recruitment cost: "+Building.getRecruitmentCost()+"\n";
				cityview.getBuildingInfo().setText(s);
			}
			
		}
			
			
			
			
			if(b.getActionCommand().equals("buildBtn")) {
				try {
					Model.getPlayer().build(type, cityC.getName());
					this.updateStatus();
				    cityview.refreshBuildingInfo(cityC, type);
					
				
					
				} catch (NotEnoughGoldException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "You don't have enough gold.");
				}
			}
			
			if(b.getActionCommand().equals("recruitBtn")) {
				try {
					if (type.equals("Stable")|| type.equals("Barracks")|| type.equals("ArcheryRange")) {
					Model.getPlayer().recruitUnit(type1, cityC.getName());
					this.updateStatus();
					cityview.refresh(cityC, this);
					cityview.refreshBuildingInfo(cityC, type);
					}
					else {
						JOptionPane.showMessageDialog(null, type+" can not recruit units.");
					}
				} catch (BuildingInCoolDownException e1) {
					JOptionPane.showMessageDialog(null, "Building Is in CoolDown.");
				} catch (MaxRecruitedException e1) {
					JOptionPane.showMessageDialog(null, "Max amount of recruits have been reached.");
				} catch (NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(null, "You don't have enough gold.");
				}
			}
			
			if(b.getActionCommand().equals("upgradeBuildingBtn")) {
				try {
					ArrayList <EconomicBuilding > Economic=cityC.getEconomicalBuildings();
					for (int i =0; i<Economic.size();i++) {
						if (Economic.get(i) instanceof Farm && type.equals("Farm")) {
							Model.getPlayer().upgradeBuilding(Economic.get(i));
							this.updateStatus();
							cityview.refreshBuildingInfo(cityC, type);
						}
						if (Economic.get(i) instanceof Market && type.equals("Market")) {
							Model.getPlayer().upgradeBuilding(Economic.get(i));
							this.updateStatus();
							cityview.refreshBuildingInfo(cityC, type);
						}
						
					}
					ArrayList <MilitaryBuilding> Military=cityC.getMilitaryBuildings();
					
				
					for (int i=0;i<Military.size();i++) {
						if (Military.get(i) instanceof ArcheryRange && type.equals("ArcheryRange")) {
							Model.getPlayer().upgradeBuilding(Military.get(i));
							cityview.refreshBuildingInfo(cityC, type);
							this.updateStatus();
						}
						if (Military.get(i) instanceof Barracks && type.equals("Barracks")) {
							Model.getPlayer().upgradeBuilding(Military.get(i));
							cityview.refreshBuildingInfo(cityC, type);
							this.updateStatus();
						}
						if (Military.get(i) instanceof Stable  && type.equals("Stable")) {
							Model.getPlayer().upgradeBuilding(Military.get(i));
							cityview.refreshBuildingInfo(cityC, type);
							this.updateStatus();
						}
						
						
                  }


					}
			
					
				
				catch (BuildingInCoolDownException e1) {
						JOptionPane.showMessageDialog(null, "Building Is in CoolDown.");
				} catch (MaxLevelException e1) {
					JOptionPane.showMessageDialog(null, "Building is at Max Level.");				} 
				catch (NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(null, "Not Enough Gold.");				} 
				}
			if(b.getActionCommand().equals("initiateBtn")) {
				if(cityview.getCurrentUnit()==null)
					return;
				Model.getPlayer().initiateArmy(cityC,cityview.getCurrentUnit());
				cityview.refresh(cityC, this);
			}
			
			if(b.getActionCommand().equals("relocateBtn")) {
				if(cityview.getRelocateToArmy()==null)
					return;
				try {
					cityview.getRelocateToArmy().relocateUnit(cityview.getCurrentUnit());
				} catch (MaxCapacityException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Army max capaicty reached.");
				}
				
				cityview.refresh(cityC, this);
			}
			if(b.getActionCommand().equals("relocateArmyViewBtn")) {
				if(armyview.getRelocateToArmy()==null)
					return;
				try {
					armyview.getRelocateToArmy().relocateUnit(armyview.getCurrentUnit());
				} catch (MaxCapacityException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Army max capaicty reached.");
				}
				
				armyview.refresh( this.selectedArmyStatus,this);
			}
			
			if(b.getActionCommand().equals("idleArmyBtn"))
			{
				selectedArmyStatus = Status.IDLE;
				armyview = new ArmyView(this);
				view.remove(worldmapview);
				view.add(armyview);
			}
		    if(b.getActionCommand().equals("marchingArmyBtn")) {
				selectedArmyStatus = Status.MARCHING;
				armyview = new ArmyView(this);
				view.remove(worldmapview);
				view.add(armyview);
		    }
		    
		     if(b.getActionCommand().equals("besiegingArmyBtn") ) {
					selectedArmyStatus = Status.BESIEGING;
					armyview = new ArmyView(this);
					view.remove(worldmapview);
					view.add(armyview);
					
		     }
		     if(b.getActionCommand().equals("backCityViewBtn")) {
		    	 view.remove(cityview);
		    	 worldmapview.showControlledCities(this);
		    	 view.add(worldmapview);
		     }
		     if(b.getActionCommand().equals("backArmyViewBtn")) {
		    	 view.remove(armyview);
		    	 worldmapview.showControlledCities(this);
		    	 view.add(worldmapview);
		     }
			if(b.getActionCommand().equals("setTargetBtn")) {
				if(armyview.getCurrentArmy().getUnits().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Cant set a target for an empty army, please recruit units to the selected army.");
				    return;
				}
				Model.targetCity(armyview.getCurrentArmy(), armyview.getTargetName());
				//NA
				armyview.refresh(this.getSelectedArmyStatus(), this);
			}
			if(b.getActionCommand().equals("endTurnBtn")){
				Model.endTurn();
				Boolean won =false;
				if(Model.isGameOver()) {
					view.remove(worldmapview);
					view.remove(view.getStatus());
					gameoverview = new GameOverView(this);
					view.add(gameoverview);
					if(Model.getAvailableCities().size()==Model.getPlayer().getControlledCities().size()) {
						gameoverview.getGameOverText().setText((Model.getPlayer().getName()+" has won the game"));
				
					}
					else {
						gameoverview.getGameOverText().setText((Model.getPlayer().getName()+" has lost the game"));
						
					}
					view.revalidate();
					view.repaint();
					
				}
				else {
					updateStatus();
				}
				
				if(cityview != null)
					cityview.refresh(cityC, this);
				
				if(armyview != null)
					armyview.refresh(selectedArmyStatus, this);			
				
				if(battleview != null)
					battleview.refresh(this);
			}
			
			if(b.getActionCommand().equals("newGameBtn")){
				view.remove(gameoverview);
				startingview = new StartingView(this);
				view.remove(view.getStatus());
				view.add(startingview);
				view.add(view.getStatus());
			}
			
			if(b.getActionCommand().equals("startAttackBtn")){
				
				Army attackingArmy = armyview.getCurrentArmy();
				
				ArrayList<City> availableCities = Model.getAvailableCities();
				
				
				for(int i=0; i< availableCities.size();i++)
					if(availableCities.get(i).getName().equals(attackingArmy.getCurrentLocation()))
					{
						battleview = new BattleView(this,attackingArmy, availableCities.get(i));
						view.remove(armyview);
						view.add(battleview);
						break;
					}
								

			}
			
			
			 if(b.getActionCommand().equals("battleBackArmyViewBtn")) {
				 armyview.refresh(Status.IDLE,this);
				 view.remove(battleview);
		    	 view.add(armyview);
		     }
			
			 
			 if(b.getActionCommand().equals("battleResolveBtn")) {

					 try {
						Model.autoResolve(battleview.getCurrentArmy(),
						battleview.getAttackedCity().getDefendingArmy());
					} catch (FriendlyFireException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage());
					}
					 battleview.refresh(this);
					

			}
			
			 
			 if(b.getActionCommand().equals("battleAttackBtn")) {

				 try {
					 int currSoldierCount = battleview.getAttackedUnit().getCurrentSoldierCount();
					 
					 battleview.getAttackingUnit().attack(battleview.getAttackedUnit());
					 battleview.AddToLog(battleview.getAttackedUnit(), "Defender", currSoldierCount);
					
					 if(battleview.getAttackedUnit().getParentArmy().getUnits().size()==0)
						 Model.occupy(battleview.getAttackingUnit().getParentArmy(),battleview.getAttackedCity().getName());
					 
					 Unit unit1 = battleview.getCurrentArmy().getUnits().get((int) (Math.random() * battleview.getCurrentArmy().getUnits().size()));
					 Unit unit2 = battleview.getCurrentAttackedArmy(this).getUnits().get((int)Math.random() * battleview.getCurrentAttackedArmy(this).getUnits().size());
					 if(unit2.getCurrentSoldierCount() > 0) {
						 currSoldierCount = unit1.getCurrentSoldierCount();
						
						 unit2.attack(unit1);
						 battleview.AddToLog(unit1, Model.getPlayer().getName(), currSoldierCount);
					 }

				} catch (FriendlyFireException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage());
				}
				 battleview.refresh(this);
			

			 }
			 
			 if(b.getActionCommand().equals("laySiegeBtn")) {

				 	battleview.getAttackedCity().setUnderSiege(true);
					battleview.refresh(this);
			
			 }			 
			 
			
		}
	
		
		view.revalidate();
		view.repaint();
		}
		
	
	public void updateStatus() {
		view.getStatus().setText("Name: "+Model.getPlayer().getName()+"\n Turn Count: "+Model.getCurrentTurnCount()+"\n Treasury: "+Model.getPlayer().getTreasury()+"\n Food: "+Model.getPlayer().getFood());;
		
	}
	public Game getModel() {
		return Model;
	}
	public void setModel(Game model) {
		Model = model;
	}
	public static void main(String [] args) {
		new Controller();
	}
	public Status getSelectedArmyStatus() {
		return selectedArmyStatus;
	}
	public void setSelectedArmyStatus(Status selectedArmyStatus) {
		this.selectedArmyStatus = selectedArmyStatus;
	}

}
