package pojos.listeners;

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
				
				// TODO mettre à jour l'agent
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
