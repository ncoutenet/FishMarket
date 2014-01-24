package sellers.behaviours;

import sellers.FishSellerAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class RegisterBehaviour extends Behaviour {
	private FishSellerAgent _seller;

	public RegisterBehaviour (FishSellerAgent seller){
		_seller = seller;
	}

	@Override
	public void action() {
		//creation dfd et sd pour recherche dfMarket
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("fish-market");
		sd.setName("FishMarket");
		sd.addOntologies("fish-market-ontology");
		sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
		sd.addProperties(new Property("Seller", "Fish"));
		dfd.addServices(sd);

		//recherche du dfMarket
		AID agent = null;

		DFAgentDescription[] result = null;
		try {
			result = DFService.search(_seller, dfd);
		} catch (FIPAException e1) {
			e1.printStackTrace();
		}
		while (result.length < 1){
			try {
				result = DFService.search(_seller, dfd);
			} catch (FIPAException e) {
				e.printStackTrace();
			}
		}
		agent = result[0].getName();

		//creation du dfd et sd pour enregistrement service
		DFAgentDescription dfd2 = new DFAgentDescription();
		ServiceDescription sd2 = new ServiceDescription();
		sd2.setType("Monger");
		sd2.setName("FishMonger");
		sd2.addOntologies("fish-market-ontology");
		sd2.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
		sd2.addProperties(new Property("fish", _seller.getFish().getType()));
		dfd2.addServices(sd2);

		//enregistrement service
		try {
			DFService.register(_seller, agent, dfd2);
		} catch (FIPAException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean done() {
		return true;
	}

}
