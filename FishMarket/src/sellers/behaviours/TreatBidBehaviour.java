package sellers.behaviours;

import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import sellers.FishSellerAgent;

@SuppressWarnings("serial")
public class TreatBidBehaviour extends TickerBehaviour {
	private FishSellerAgent _seller;

	public TreatBidBehaviour(FishSellerAgent a, long period) {
		super(a, period);
		_seller = a;
		System.out.println("Miaou");
	}

	@Override
	protected void onTick() {
		System.out.println(_seller.getBidders().size());
		switch (_seller.getBidders().size()){
		case 0:
			_seller.decreasePrice();
			System.out.println("0 bid");
			break;
		case 1:
			System.out.println("1 bid");
			_seller.setSell(true);
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(_seller.getABidder(0));
			msg.setContent("YES");
			_seller.send(msg);
			deregister();
			_seller.stop();
			break;
		default:
			_seller.increasePrice();
			sendMessage();
			_seller.getBidders().clear();
			System.out.println("2+ bids");
			break;
		}

	}

	private void sendMessage(){
		for (int i=0; i<_seller.getBidders().size();i++){
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(_seller.getABidder(i));
			msg.setContent("NO");
			_seller.send(msg);
		}
	}

	private void deregister(){
		try {
			DFService.deregister(_seller, _seller.getMarket());
		} catch (FIPAException e) {
			e.printStackTrace();
		}		
	}
}
