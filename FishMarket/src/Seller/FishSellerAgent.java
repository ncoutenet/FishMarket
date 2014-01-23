package Seller;
import pojos.Fish;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/*
 * Faire les annonces sur le marché
 * Les Acheteurs disent aux marché quel est le vendeur qu'ils écoutent
 * Le marché ne sert que d'interface 
 */

public class FishSellerAgent extends Agent{
    private FishSellerGui _myGui;
    private String _myName;
    
    public String getMyName() {
		return _myName;
	}

	public void setMyName(String name) {
		this._myName = name;
	}

	private Fish _fish;

	public Fish getFish() {
		return _fish;
	}

	public void setFish(Fish fish) {
		this._fish = fish;
	}

	public void increasePrice(){
		// TODO augmenter le prix
	}
	
	public void decreasePrice(){
		// TODO baisser le prix
	}
	
	public void sendToMarket(){
		// TODO envoyer le type et le prix du poisson au marché
	}
	
	protected void setup(){
        _myGui = new FishSellerGui(this);
        _myGui.showGui();
        _myName = this.getAID().getName();
        String serviceName = "Fishmonger";
      	
      	// Read the name of the service to register as an argument
      	Object[] args = getArguments();
      	/*if (args != null && args.length > 0) {
      		serviceName = (String) args[0]; 
      	}*/
      	
      	// Register the service
      	System.out.println("Agent "+getLocalName()+" registering service \""+serviceName+"\" of type \"weather-forecast\"");
      	try {
      		DFAgentDescription dfd = new DFAgentDescription();
      		dfd.setName(getAID());
      		ServiceDescription sd = new ServiceDescription();
      		sd.setName(serviceName);
      		sd.setType("fish-market");
      		// Agents that want to use this service need to "know" the weather-forecast-ontology
      		sd.addOntologies("fish-market-ontology");
      		// Agents that want to use this service need to "speak" the FIPA-SL language
      		sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
      		sd.addProperties(new Property("fish", _fish));
      		dfd.addServices(sd);
      		
      		DFService.register(this, dfd);
      	}
      	catch (FIPAException fe) {
      		fe.printStackTrace();
      	}
    }
}
