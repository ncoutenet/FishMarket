package pojos.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		}
	}

}
