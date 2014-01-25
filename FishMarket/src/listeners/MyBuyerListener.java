package listeners;

import jade.core.AID;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import buyers.FishBuyerGui;

public class MyBuyerListener implements ActionListener {
	private FishBuyerGui _myGUI;

	public MyBuyerListener(FishBuyerGui v) {
		this._myGUI = v;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("autobuy")){
			_myGUI.getMyAgent().setMode(_myGUI.isAuto());
			if(_myGUI.isAuto()){
				_myGUI.constructAutomaticMode();
			}else{
				_myGUI.constructManualMode();
			}
		}
		if(arg0.getActionCommand().equals("btnsubmitbuyer")){
			try{
				double price = this._myGUI.getMaxPrice();
				String type = this._myGUI.getFishType();
				int indexAgent = _myGUI.getSelectedSeller();
				
				if(indexAgent == -1){
					JOptionPane.showMessageDialog(this._myGUI, "No seller selected!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					AID seller = this._myGUI.getMyAgent().getASeller(indexAgent);
					// TODO envoyer un message de bid au vendeur
				}
				
				this._myGUI.getMyAgent().setNewFish(type, price);
				this._myGUI.getMyAgent().automatic();
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(this._myGUI, "Invalid values: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(arg0.getActionCommand().equals("btnmanualbuy")){
			// TODO achat manuel
		}

	}

}
