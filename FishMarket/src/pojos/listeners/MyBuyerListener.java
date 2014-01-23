package pojos.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyBuyerListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("autobuy")){
			// TODO mettre a jour l'interface de l'acheteur
		}

	}

}
