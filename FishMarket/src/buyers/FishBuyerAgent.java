package buyers;

import java.util.ArrayList;
import java.util.List;

import buyers.behaviours.SearchBehaviour;

import sellers.FishSellerAgent;

import jade.core.AID;
import jade.core.Agent;
/*import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;*/


public class FishBuyerAgent extends Agent{
    private FishBuyerGui _myGui;
    private boolean _autoMode;
    private List<AID> _sellers;
    
    public void setMode(boolean auto){
    	this._autoMode = auto;
    }
    
    protected void setup(){
        _myGui = new FishBuyerGui(this);
        _myGui.setVisible(true);
        this._sellers = new ArrayList<AID>();
        
        if(!this._autoMode){
        	addBehaviour(new SearchBehaviour(this));
        }
        
        // TODO completer
    }
    
    public void setSellers(AID[] agents){
    	this._sellers.clear();
    	for(int i = 0; i < agents.length; i++){
    		this._sellers.add(agents[i]);
    	}
    }
    
    private void findAnnounces(){
		// TODO récupérer les infos de chaque seller
    	this._myGui.setSellers(_sellers);
    }
    
    public void setBuyMode(){
    	if(this._autoMode){
    		// TODO comportement automatique
    	} else{
    		// TODO comportement manuel
    	}
    }
}
