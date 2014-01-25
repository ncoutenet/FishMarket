package sellers;

import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import pojos.Fish;
import sellers.behaviours.ReceptBidBehaviour;
import sellers.behaviours.RegisterBehaviour;
import sellers.behaviours.ResponseBehaviour;

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
	}

	public void decreasePrice(){
		_fish.setPrice(_fish.getPrice() * 4/5);
	}

	protected void setup(){
		_myGui = new FishSellerGui(this);
		_myGui.showGui();
		_myName = this.getAID().getName();
		_sell = false;
		_timeOut = false;
		_bidders = new ArrayList<AID>();
	}

	public void registerToMarket(Fish f){
		this._fish = f;
		//creation behaviour sequentiel
		SequentialBehaviour seq = new SequentialBehaviour(this);
		//ajout register a seq
		seq.addSubBehaviour(new RegisterBehaviour(this));
		//creation behaviour parallele
		ParallelBehaviour para = new ParallelBehaviour();
		//ajout behaviours a para
		para.addSubBehaviour(new ResponseBehaviour(this));
		para.addSubBehaviour(new ReceptBidBehaviour(this));
		//ajout para a seq
		seq.addSubBehaviour(para);
		//lancement seq
		addBehaviour(seq);
	}
}
