package buyers.behaviours;

import jade.core.behaviours.Behaviour;

public class SearchBehaviour extends Behaviour {
	private boolean _endSearch;
	
	public SearchBehaviour(){
		this._endSearch = false;
	}
	@Override
	public void action() {
		// TODO chercher les vendeurs sur le marché

	}

	@Override
	public boolean done() {
		return this._endSearch;
	}

}
