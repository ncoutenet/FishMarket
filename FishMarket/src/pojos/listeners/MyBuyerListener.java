package pojos.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import buyers.FishBuyerGui;

public class MyBuyerListener implements ActionListener {
	private FishBuyerGui _myGUI;

	public MyBuyerListener(FishBuyerGui v) {
		this._myGUI = v;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("autobuy")){
			if(_myGUI.isAuto()){
				_myGUI.constructAutomaticMode();
			}else{
				_myGUI.contructManualMode();
			}
		}

	}

}
