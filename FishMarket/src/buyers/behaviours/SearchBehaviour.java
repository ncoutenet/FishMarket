package buyers.behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.Iterator;

import buyers.FishBuyerAgent;

public class SearchBehaviour extends Behaviour {
	private FishBuyerAgent _myAgent;

	public SearchBehaviour(FishBuyerAgent a){
		this._myAgent = a;
	}
	@Override
	public void action() {
		// Search for services of type "Monger"
		System.out.println("Agent "+_myAgent.getLocalName()+" searching for services of type \"Monger\"");
		try {
			//searching the market
			DFAgentDescription marketDesc = new DFAgentDescription();
			ServiceDescription sdMarket = new ServiceDescription();
			sdMarket.setType("fish-market");
			marketDesc.addServices(sdMarket);
			AID market = null;
			DFAgentDescription[] result = null;
			try{
				result = DFService.search(_myAgent, marketDesc);
				while(result.length < 1){
					result = DFService.search(_myAgent, marketDesc);
				}
			}
			catch(FIPAException e){
				e.printStackTrace();
			}
			market = result[0].getName();

			// Build the description used as template for the search
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription templateSd = new ServiceDescription();
			templateSd.setType("Monger");
			if(_myAgent.isAuto()){
				templateSd.addProperties(new Property("fish", _myAgent.getGUI().getFishType()));
			}
			template.addServices(templateSd);

			SearchConstraints sc = new SearchConstraints();
			// We want to receive 10 results at most
			sc.setMaxResults(new Long(10));

			DFAgentDescription[] results = DFService.search(this._myAgent, market, template, sc);
			if (results.length > 0) {
				System.out.println("Agent "+_myAgent.getLocalName()+" found the following Monger services:");
				AID[] agents = new AID[results.length];
				for (int i = 0; i < results.length; ++i) {
					DFAgentDescription dfd = results[i];
					AID provider = dfd.getName();
					// The same agent may provide several services; we are only interested
					// in the monger one
					Iterator it = dfd.getAllServices();
					while (it.hasNext()) {
						ServiceDescription sd = (ServiceDescription) it.next();
						if (sd.getType().equals("Monger")) {
							System.out.println("- Service \""+sd.getName()+"\" provided by agent "+provider.getName());
						}
					}
					agents[i] = provider;
				}
				this._myAgent.setSellers(agents);

			}	
			else {
				System.out.println("Agent "+_myAgent.getLocalName()+" did not find any monger service");
			}
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

	@Override
	public boolean done() {
		return _myAgent.isEndSearch();
	}
}
