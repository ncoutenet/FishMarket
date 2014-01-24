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
    }
	
	public void registerToMarket(Fish f){
		this._fish = f;
		this.addBehaviour(new RegisterBehaviour(this));
		response();
		response();
	}
	
	public void response(){
		// TODO a mettre en cyclic behaviour (ou pas)
		ACLMessage request = null;
		while (request == null){
			request = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		}
		String demande = request.getContent();
		ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
		reply.addReceiver(request.getSender());
		if (demande.equals("price")){
			reply.setContent(String.valueOf(_fish.getPrice()));
		}else if (demande.equals("type")){
			reply.setContent(_fish.getType());
		}else{
			reply.setContent("NAN");
		}
		send(reply);
	}
}
