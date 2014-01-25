package buyers;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

import pojos.Fish;

import buyers.behaviours.SearchBehaviour;



public class FishBuyerAgent extends Agent{
    private FishBuyerGui _myGui;
    private boolean _autoMode;
    private boolean _endSearch;
    private Fish _fish;
    
    private List<AID> _sellers;
    
    public Fish getFish() {
		return _fish;
	}

	public void setFish(Fish fish) {
		this._fish = fish;
	}
	
	public void setNewFish(String type, double price){
		this._fish = new Fish(type, price);
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
        
        // TODO completer
    }
    
	// TODO arreter la recherche dès qu'on a trouvé un vendeur
    public void setSellers(AID[] agents){
    	_endSearch = true;
    	this._sellers.clear();
    	for(int i = 0; i < agents.length; i++){
    		this._sellers.add(agents[i]);
    	}
    	this.findAnnounces();
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
		send(msg);
		
		ACLMessage reply = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		return reply.getContent();
    }
    
    private String findPrice(AID agent){
    	ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(agent);
		msg.setContent("price");
		send(msg);
		
		ACLMessage reply = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		return reply.getContent();
    }
    
    public void automatic(){
    	// TODO comportement automatique
    }
    
    public void manual(){
    	// TODO comportement manuel
		addBehaviour(new SearchBehaviour(this));
    }
}
