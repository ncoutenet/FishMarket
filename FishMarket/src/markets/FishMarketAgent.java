package markets;

import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class FishMarketAgent extends jade.domain.df{

	protected void setup(){

		try {

			AID parentName = getDefaultDF(); 

			super.setup();
 
			setDescriptionOfThisDF(getDescription());

			super.showGui();

			DFService.register(this,parentName,getDescription());
			addParent(parentName,getDescription());
			System.out.println("Agent: " + getName() + " federated with default df.");

		}catch(FIPAException fe){fe.printStackTrace();}
	}

	private DFAgentDescription getDescription()
	{
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("fish-market");
		sd.setName("FishMarket");
		sd.addOntologies("fish-market-ontology");
		sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
		sd.addProperties(new Property("Seller", "Fish"));
		dfd.addServices(sd);
		return dfd;
	}
}
