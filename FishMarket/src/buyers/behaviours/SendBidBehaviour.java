package buyers.behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import buyers.FishBuyerAgent;

public class SendBidBehaviour extends Behaviour {
	private FishBuyerAgent _myAgent;
	private AID _seller;

	public SendBidBehaviour(FishBuyerAgent agent, AID seller) {
		this._myAgent = agent;
		this._seller = seller;
	}

	@Override
	public void action() {
		ACLMessage bid = new ACLMessage(ACLMessage.REQUEST);
		bid.addReceiver(this._seller);
		bid.setContent("bid");
		this._myAgent.send(bid);
		
		ACLMessage reply = this._myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		String response = reply.getContent();
		if(response.equals("YES")){
			this._myAgent.buyFish(this._seller);
		}
		else if(response.equals("NO")){
			reply = this._myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			double price = Double.parseDouble(reply.getContent());
			this._myAgent.updatePrice(price);
		}

	}

	@Override
	public boolean done() {
		return true; // one shot
	}

}
