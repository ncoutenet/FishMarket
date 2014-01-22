package Buyer;

import jade.core.Agent;

import javax.swing.*;

public class FishBuyerGui extends JFrame{
	private FishBuyerAgent _myAgent;
	
	public FishBuyerGui(FishBuyerAgent agent){
		this._myAgent = agent;
	}
}