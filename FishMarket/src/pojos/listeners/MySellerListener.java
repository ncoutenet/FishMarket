package pojos.listeners;

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
			// TODO completer listener vendeur
			try{
				String type = _myGui.getNameFish().getText();
				float price = Float.valueOf(_myGui.getPriceField().getText());
				
				//mise à jour de l'agent
				_myGui.getMyAgent().setFish(new Fish(type, price));
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(_myGui, "Invalid values: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
