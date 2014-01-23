package buyers;

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
    
    protected void setup(){
        _myGui = new FishBuyerGui(this);
        _myGui.setVisible(true);
        
        // TODO completer
    }
}
