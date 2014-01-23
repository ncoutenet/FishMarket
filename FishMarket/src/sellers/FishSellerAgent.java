package sellers;

import pojos.Fish;
import jade.core.AID;
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
    private double _price;
	private Fish _fish;
    
    public String getMyName() {
		return _myName;
	}

	public void setMyName(String name) {
		this._myName = name;
	}

	public Fish getFish() {
		return _fish;
	}

	public void setFish(Fish fish) {
		this._fish = fish;
	}

	public void setPrice(double p){
		this._price = p;
	}
	
	public double getPrice(){
		return this._price;
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
        String serviceName = "FishMonger";
      	
      	// Read the name of the service to register as an argument
      	Object[] args = getArguments();
      	/*if (args != null && args.length > 0) {
      		serviceName = (String) args[0]; 
      	}*/
      	
      	// Register the service
      	System.out.println("Agent "+getLocalName()+" registering service \""+serviceName+"\"");
      	try {
      		DFAgentDescription dfd = new DFAgentDescription();
      		ServiceDescription sd = new ServiceDescription();
      		sd.setType("fish-market");
      		sd.setName("FishMarket");
      		// Agents that want to use this service need to "know" the weather-forecast-ontology
      		sd.addOntologies("fish-market-ontology");
      		// Agents that want to use this service need to "speak" the FIPA-SL language
      		sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
      		sd.addProperties(new Property("fish", _fish));
      		dfd.addServices(sd);
      		
      		AID agent = null;

      		DFAgentDescription[] result = null;
      		result = DFService.search(this, dfd);
      		while (result.length < 1){
          		result = DFService.search(this, dfd);
      		}
  			agent = result[0].getName();
      		sd.setName(serviceName);
  			System.out.println("DF trouvé: " + result[0].getName());
      		DFService.register(this, agent, dfd);
      		
      	}
      	catch (FIPAException fe) {
      		fe.printStackTrace();
      	}
    }
}
