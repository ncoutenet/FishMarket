package buyers;

import jade.core.AID;
import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

import buyers.behaviours.SearchBehaviour;



public class FishBuyerAgent extends Agent{
    private FishBuyerGui _myGui;
    private boolean _autoMode;
    private boolean _endSearch;
    
    private List<AID> _sellers;
    
    public void setMode(boolean auto){
    	this._autoMode = auto;
    }
    
    public boolean isEndSearch() {
		return _endSearch;
	}

	protected void setup(){
        _myGui = new FishBuyerGui(this);
        _myGui.setVisible(true);
        this._sellers = new ArrayList<AID>();
        _endSearch = false;
        
    	addBehaviour(new SearchBehaviour(this));
        
        // TODO completer
    }
    
    public void setSellers(AID[] agents){
    	this._sellers.clear();
    	for(int i = 0; i < agents.length; i++){
    		this._sellers.add(agents[i]);
    	}
    }
    
    private void findAnnounces(){
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
