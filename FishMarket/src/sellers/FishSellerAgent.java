package sellers;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

import pojos.Fish;
import sellers.behaviours.RegisterBehaviour;
import sellers.behaviours.ResponseBehaviour;
import sellers.behaviours.TreatBidBehaviour;

/*
 * Faire les annonces sur le marché
 * Les Acheteurs disent aux marché quel est le vendeur qu'ils écoutent
 * Le marché ne sert que d'interface 
 */

public class FishSellerAgent extends Agent{
	private FishSellerGui _myGui;
	private String _myName;
	private Fish _fish;
	private boolean _sell;
	private boolean _timeOut;
	private List<AID> _bidders;
	private List<AID> _potential;
	private AID _market;
	private TreatBidBehaviour _treat;
	
	public FishSellerGui getMyGui() {
		return _myGui;
	}

	public void setMyGui(FishSellerGui myGui) {
		this._myGui = myGui;
	}

	public AID getMarket() {
		return _market;
	}

	public void setMarket(AID market) {
		this._market = market;
	}

	public List<AID> getBidders() {
		return _bidders;
	}

	public void setBidders(List<AID> bidders) {
		this._bidders = bidders;
	}
	
	public AID getABidder(int i) {
		return _bidders.get(i);
	}

	public void setABidder(AID bidders) {
		this._bidders.add(bidders);
	}

	public boolean isTimeOut() {
		return _timeOut;
	}

	public void setTimeOut(boolean timeOut) {
		this._timeOut = timeOut;
	}

	public boolean isSell() {
		return _sell;
	}

	public void setSell(boolean sell) {
		this._sell = sell;
	}

	public String getMyName() {
		return _myName;
	}

	public void setMyName(String name) {
		this._myName = name;
	}

	public Fish getFish() {
		return _fish;
	}

	public double getPrice(){
		return this._fish.getPrice();
	}

	public void increasePrice(){
		_fish.setPrice(_fish.getPrice() * 6/5);
		System.out.println("Increase price");
	}

	public void decreasePrice(){
		_fish.setPrice(_fish.getPrice() * 4/5);
		System.out.println("Decrease price");
	}

	protected void setup(){
		_myGui = new FishSellerGui(this);
		_myGui.showGui();
		_myName = this.getAID().getName();
		_sell = false;
		_timeOut = false;
		_bidders = new ArrayList<AID>();
		_potential = new ArrayList<AID>();
	}

	public List<AID> getPotential() {
		return _potential;
	}

	public void setPotential(List<AID> potential) {
		this._potential = potential;
	}

	public AID getAPotential(int i) {
		return _potential.get(i);
	}

	public void setAPotential(AID potential) {
		this._potential.add(potential);
	}

	public void registerToMarket(Fish f){
		this._fish = f;
		SequentialBehaviour seq = new SequentialBehaviour(this);
		seq.addSubBehaviour(new RegisterBehaviour(this));
		ParallelBehaviour para = new ParallelBehaviour();
		para.addSubBehaviour(new ResponseBehaviour(this));
		_treat = new TreatBidBehaviour(this, 5000);
		para.addSubBehaviour(_treat);
		seq.addSubBehaviour(para);
		addBehaviour(seq);
	}
	
	public void stop(){
		_treat.stop();
	}
	
	public void sell(){
		ACLMessage fish = new ACLMessage(ACLMessage.INFORM);
		fish.addReceiver(_bidders.get(0));
		fish.setContent("Fish Received");
		this.send(fish);
		System.out.println("Fish sent");
		ACLMessage money = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		if (money.getContent().equals("Money Received")){
			System.out.println(money.getContent());
		} else{
			System.out.println("Error receiving money");
		}
	}
	
	public void dePotential(AID pot){
		int i=0;
		while (i<_potential.size() && !getAPotential(i).equals(pot)){
			i++;
		}
		if (i == _potential.size()){
			System.out.println("Buyer not recognized");
		}else{
			_potential.remove(i);
		}
	}
}
