package buyers;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

import pojos.Fish;
import buyers.behaviours.SearchBehaviour;
import buyers.behaviours.SendBidBehaviour;
import buyers.behaviours.UpdateBuyerBehaviour;



public class FishBuyerAgent extends Agent{
    private FishBuyerGui _myGui;
    private boolean _autoMode;
    private boolean _endSearch;
    private Fish _fish;
    
    private List<AID> _sellers;
    private boolean _untrackingPrices;
    
    public boolean isUntrackingPrices() {
		return _untrackingPrices;
	}

	public void setUntrackingPrices(boolean trackingPrices) {
		this._untrackingPrices = trackingPrices;
	}

	public Fish getFish() {
		return _fish;
	}

	public void setFish(Fish fish) {
		this._fish = fish;
	}
	
	public void setNewFish(String type, double price){
		this._fish = new Fish(type, price);
	}
	
	public void stopUpdate(){
		this.setUntrackingPrices(true);
		ACLMessage stopUpdate = new ACLMessage(ACLMessage.REQUEST);
		List<AID> senders = this.getSellers();
		for(int i = 0; i < senders.size(); i++){
			stopUpdate.addReceiver(senders.get(i));
			stopUpdate.setContent("stopUpdate");
		}
		this.send(stopUpdate);
	}

	public void setMode(boolean auto){
    	this._autoMode = auto;
    }
    
    public boolean isAuto() {
		return _autoMode;
	}

	public boolean isEndSearch() {
		return _endSearch;
	}

	public FishBuyerGui getGUI() {
		return _myGui;
	}

	protected void setup(){
        _myGui = new FishBuyerGui(this);
        _myGui.setVisible(true);
        this._sellers = new ArrayList<AID>();
        _endSearch = false;
        
    	if(this.isAuto()){
    		this.automatic();
    	}else{
    		this.manual();
    	}
    }
    
    public void setSellers(AID[] agents){
    	_endSearch = true;
    	_untrackingPrices = false;
    	this._sellers.clear();
    	for(int i = 0; i < agents.length; i++){
    		this._sellers.add(agents[i]);
    	}
    	this.findAnnounces();
    	this.addBehaviour(new UpdateBuyerBehaviour(this));
    }
    
    public List<AID> getSellers() {
		return _sellers;
	}
    
    public AID getASeller(int index){
    	return this._sellers.get(index);
    }

	private void findAnnounces(){
		List<String> prices = new ArrayList<String>();
		List<String> types = new ArrayList<String>();
    	for(int i = 0; i < _sellers.size(); i++){		
			AID seller = _sellers.get(i);
			types.add(this.findType(seller));
			prices.add(findPrice(seller));
		}
    	this._myGui.setSellers(_sellers, types, prices);
    }
    
    private String findType(AID agent){
    	ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(agent);
		msg.setContent("type");
		System.out.println("demande type");
		send(msg);
		
		ACLMessage reply = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		System.out.println("retour type");
		return reply.getContent();
    }
    
    private String findPrice(AID agent){
    	ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(agent);
		msg.setContent("price");
		System.out.println("demande prix");
		send(msg);
		
		ACLMessage reply = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		System.out.println("retour prix");
		return reply.getContent();
    }
    
    public void automatic(){
    	// TODO comportement automatique
    }
    
    public void bid(AID seller){
    	this.addBehaviour(new SendBidBehaviour(this, seller));
    }
    
    public void buyFish(AID seller){
    	ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
    	msg.addReceiver(seller);
    	msg.setContent("Money Received");
    	send(msg);
    	
    	msg = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
    	if(msg.getContent().equals("Fish Received")){
    		System.out.println("Fish Received");
    	}
    	else{
    		System.out.println("Error! Fish has not been received!!!");
    	}
    }
    
    public void manual(){
		addBehaviour(new SearchBehaviour(this));
    }
}
