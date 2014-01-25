package sellers.behaviours;

import sellers.FishSellerAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceptBidBehaviour extends Behaviour {
	private FishSellerAgent _myAgent;
	
	public ReceptBidBehaviour(FishSellerAgent myAgent){
		_myAgent = myAgent;
	}

	@Override
	public void action() {
		ACLMessage request = null;
		while (request == null){
			request = this._myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		}
		if (request.getContent().equals("bid")){
			_myAgent.setABidder(request.getSender());
		}
		request = null;
	}

	@Override
	public boolean done() {
		return _myAgent.isTimeOut();
	}

}
