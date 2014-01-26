package buyers.behaviours;

import java.util.List;

import buyers.FishBuyerAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class UpdateBuyerBehaviour extends Behaviour {
	private FishBuyerAgent _myAgent;

	public UpdateBuyerBehaviour(FishBuyerAgent agent) {
		super();
		this._myAgent = agent;
	}

	@Override
	public void action() {
		ACLMessage msg = this._myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		if(msg != null){
			String content = msg.getContent();
			int index = 0;
			while((index < this._myAgent.getSellers().size()) && (!this._myAgent.getSellers().get(index).equals(msg.getSender()))){
				index++;
			}
			
			if(content.split("#").length > 1){
				if(content.split("#")[0].equals("newPrice")){
					double price = Double.parseDouble(content.split("#")[1]);
					if(index < _myAgent.getSellers().size()){
						this._myAgent.getGUI().updatePrice(price, index);
					}
				}
			}
			else{
				if(content.equals("stopTracking")){
					if(index < this._myAgent.getSellers().size()){
						this._myAgent.getGUI().deleteASeller(msg.getSender());
					}
				}
			}
		}

	}

	@Override
	public boolean done() {
		return this._myAgent.isUntrackingPrices();
	}

}
