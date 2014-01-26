package buyers.behaviours;

import java.util.List;

import buyers.FishBuyerAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class StopUpdateBehaviour extends Behaviour {
	private FishBuyerAgent _myAgent;

	public StopUpdateBehaviour(FishBuyerAgent agent) {
		super();
		this._myAgent = agent;
	}

	@Override
	public void action() {
		// TODO demander aux vendeur d'arreter les updates
		ACLMessage stopUpdate = new ACLMessage(ACLMessage.REQUEST);
		List<AID> senders = this._myAgent.getSellers();
		for(int i = 0; i < senders.size(); i++){
			stopUpdate.addReceiver(senders.get(i));
			stopUpdate.setContent("stopUpdate");
		}
		this._myAgent.send(stopUpdate);
		this._myAgent.setTrackingPrices(false);
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return true; // one shot
	}

}
