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
		request = this._myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (request != null){
			System.out.println("Ask receive");
			String demande = request.getContent();
			ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
			reply.addReceiver(request.getSender());
			if (demande.equals("price")){
				System.out.println("Price");
				_myAgent.setAPotential(request.getSender());
				reply.setContent(String.valueOf(this._myAgent.getFish().getPrice()));
			}else if (demande.equals("type")){
				System.out.println("Type");
				reply.setContent(this._myAgent.getFish().getType());
			}else if (demande.equals("bid")){
				_myAgent.setABidder(request.getSender());
				_myAgent.getMyGui().updateGui();
				System.out.println("Bid");
			}else if (demande.equals("stopUpdate")){
				_myAgent.dePotential(request.getSender());
				System.out.println("DeregisterUpdate");
			}else{
				System.out.println("Other");
				reply.setContent("NAN");
			}
			if (!demande.equals("bid") && !demande.equals("stopUpdate")){
				this._myAgent.send(reply);
				System.out.println("Send answer");
			}
		}
	}

	@Override
	public boolean done() {
		return _myAgent.isSell();
	}

}
