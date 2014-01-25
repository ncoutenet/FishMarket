package sellers.behaviours;

import sellers.FishSellerAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ResponseBehaviour extends Behaviour {
	private FishSellerAgent _myAgent;
	
	public ResponseBehaviour(FishSellerAgent agent){
		this._myAgent = agent;
	}

	@Override
	public void action() {
		ACLMessage request = null;
		while (request == null){
			request = this._myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		}
		String demande = request.getContent();
		ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
		reply.addReceiver(request.getSender());
		if (demande.equals("price")){
			reply.setContent(String.valueOf(this._myAgent.getFish().getPrice()));
		}else if (demande.equals("type")){
			reply.setContent(this._myAgent.getFish().getType());
		}else{
			reply.setContent("NAN");
		}
		this._myAgent.send(reply);
	}

	@Override
	public boolean done() {
		return false; //cyclic behaviour
	}

}
