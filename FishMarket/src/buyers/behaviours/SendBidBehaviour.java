package buyers.behaviours;

import buyers.FishBuyerAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SendBidBehaviour extends Behaviour {
	private FishBuyerAgent _myAgent;
	private AID _seller;

	public SendBidBehaviour(FishBuyerAgent agent) {
		this._myAgent = agent;
	}

	@Override
	public void action() {
		ACLMessage bid = new ACLMessage(ACLMessage.REQUEST);
		bid.addReceiver(this._seller);
		bid.setContent("bid");
		this._myAgent.send(bid);
		
		ACLMessage reply = this._myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		String response = reply.getContent();
		if(response.equals("yes")){
			// TODO acheter le poisson
		}

	}

	@Override
	public boolean done() {
		return true; // one shot
	}

}
