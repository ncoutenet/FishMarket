package listeners;

import jade.core.AID;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import buyers.FishBuyerGui;
import buyers.behaviours.StopUpdateBehaviour;

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
			// TODO mode automatique buyer
		}
		if(arg0.getActionCommand().equals("btnmanualbuy")){
			try{
				int indexAgent = _myGUI.getSelectedSeller();
				
				if(indexAgent == -1){
					JOptionPane.showMessageDialog(this._myGUI, "No seller selected!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					AID seller = this._myGUI.getMyAgent().getASeller(indexAgent);
					this._myGUI.getMyAgent().addBehaviour(new StopUpdateBehaviour(this._myGUI.getMyAgent()));
					this._myGUI.getMyAgent().bid(seller);
				}
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(this._myGUI, "Invalid values: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

}
