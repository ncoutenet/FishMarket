package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import pojos.Fish;
import sellers.FishSellerGui;

public class MySellerListener implements ActionListener {
	private FishSellerGui _myGui;

	public MySellerListener (FishSellerGui myGui){
		_myGui = myGui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("btnPost")){ 
			try{
				Fish f = new Fish(_myGui.getNameFish().getText(), Double.parseDouble(_myGui.getPriceField().getValue().toString()));

				//mise à jour de l'agent et enregistrement au marché
				_myGui.getMyAgent().registerToMarket(f);
				_myGui.getBtnAnnonce().setEnabled(false);
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(_myGui, "Invalid values: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
