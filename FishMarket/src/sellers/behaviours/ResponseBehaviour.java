package sellers.behaviours;

import sellers.FishSellerAgent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ResponseBehaviour extends Behaviour {
	private FishSellerAgent _myAgent;
	
	public ResponseBehaviour(FishSellerAgent agent){
		System.out.println("response");
		this._myAgent = agent;
		System.out.println(_myAgent.getName().toString());
	}

	@Override
	public void action() {
		ACLMessage request = null;
		request = this._myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		System.out.println("ask receive");
		String demande = request.getContent();
		ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
		reply.addReceiver(request.getSender());
		if (demande.equals("price")){
			System.out.println("prix");
			reply.setContent(String.valueOf(this._myAgent.getFish().getPrice()));
		}else if (demande.equals("type")){
			System.out.println("type");
			reply.setContent(this._myAgent.getFish().getType());
		}else if (demande.equals("bid")){
			_myAgent.setABidder(request.getSender());
			System.out.println("bid");
		}else{
			System.out.println("other");
			reply.setContent("NAN");
		}
		if (!demande.equals("bid")){
			this._myAgent.send(reply);
			System.out.println("send answer");
		}
	}

	@Override
	public boolean done() {
		return _myAgent.isSell();
	}

}
