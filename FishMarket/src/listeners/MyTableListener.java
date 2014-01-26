package listeners;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MyTableListener implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent lse) {
		if(lse.getValueIsAdjusting()){
			return;
		}
		ListSelectionModel lsm = (ListSelectionModel)lse.getSource();
		if(!lsm.isSelectionEmpty()){
			int selectedRow = lsm.getMinSelectionIndex();
			// TODO mettre ˆ jour l'affichage
		}
	}

}
