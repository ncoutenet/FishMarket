package sellers;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import pojos.Fish;
import sellers.behaviours.RegisterBehaviour;

/*
 * Faire les annonces sur le marché
 * Les Acheteurs disent aux marché quel est le vendeur qu'ils écoutent
 * Le marché ne sert que d'interface 
 */

public class FishSellerAgent extends Agent{
    private FishSellerGui _myGui;
    private String _myName;
    private double _price;
	private Fish _fish;
    
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
		return this._price;
	}
	
	public void increasePrice(){
		_price = _price * 6/5;
	}
	
	public void decreasePrice(){
		_price = _price * 4/5;
	}
	
	protected void setup(){
        _myGui = new FishSellerGui(this);
        _myGui.showGui();
        _myName = this.getAID().getName();
    }
	
	public void registerToMarket(Fish f){
		this._fish = f;
		this._price = this._fish.getPrice();
		this.addBehaviour(new RegisterBehaviour(this));
		response();
	}
	
	public void response(){
		ACLMessage request = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		String demande = request.getContent();
		ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
		reply.addReceiver(request.getSender());
		if (demande.equals("price")){
			reply.setContent(String.valueOf(_price));
		} else{
			reply.setContent("NAN");
		}
		send(reply);
	}
}
