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
			List<AID> sellers = this._myAgent.getSellers();
			int index = 0;
			while(!sellers.get(index).equals(msg.getSender())){
				index++;
			}
			
			if(content.split("#").length > 1){
				if(content.split("#")[0].equals("newPrice")){
					double price = Double.parseDouble(content.split("#")[1]);
					this._myAgent.getGUI().updatePrice(price, index);
				}
			}
			else{
				if(content.equals("stopTracking")){
					this._myAgent.getGUI().deleteASeller(index);
				}
			}
		}

	}

	@Override
	public boolean done() {
		return this._myAgent.isUntrackingPrices();
	}

}
