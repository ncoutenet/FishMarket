package buyers.behaviours;

import java.util.Iterator;

import buyers.FishBuyerAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class SearchBehaviour extends Behaviour {
	private boolean _endSearch;
	private FishBuyerAgent _myAgent;
	
	public SearchBehaviour(FishBuyerAgent a){
		this._endSearch = false;
		this._myAgent = a;
	}
	@Override
	public void action() {
		// TODO chercher les vendeurs sur le marché
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
	  		template.addServices(templateSd);
	  		
	  		SearchConstraints sc = new SearchConstraints();
	  		// We want to receive 10 results at most
//	  		sc.setMaxResults(new Long(10));
	  		
	  		DFAgentDescription[] results = DFService.search(this._myAgent, market, template, sc);
	  		if (results.length > 0) {
	  			System.out.println("Agent "+_myAgent.getLocalName()+" found the following Monger services:");
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
	  						// TODO récupérer l'agent concerné
	  					}
	  				}
	  			}
	  			this.setEndSearch(true); // TODO enlever ce point d'arret
	  			
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
		return this._endSearch;
	}
	public boolean isEndSearch() {
		return _endSearch;
	}
	public void setEndSearch(boolean endSearch) {
		this._endSearch = endSearch;
	}

}
