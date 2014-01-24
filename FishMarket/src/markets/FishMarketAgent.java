package markets;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.*;

import pojos.Fish;

public class FishMarketAgent extends jade.domain.df{
	private Fish _fish;

	protected void setup(){
		// Input df name
		int len = 0;
		byte[] buffer = new byte[1024];

		try {

			AID parentName = getDefaultDF(); 

			//Execute the setup of jade.domain.df which includes all the default behaviours of a df 
			//(i.e. register, unregister,modify, and search).
			super.setup();

			//Use this method to modify the current description of this df. 
			setDescriptionOfThisDF(getDescription());

			//Show the default Gui of a df.
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
		// Agents that want to use this service need to "know" the weather-forecast-ontology
		sd.addOntologies("fish-market-ontology");
		// Agents that want to use this service need to "speak" the FIPA-SL language
		sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
		sd.addProperties(new Property("Seller", "Fish"));
		dfd.addServices(sd);
		return dfd;
	}
}
