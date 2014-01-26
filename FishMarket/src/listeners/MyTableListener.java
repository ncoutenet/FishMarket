package listeners;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import buyers.FishBuyerGui;

public class MyTableListener implements ListSelectionListener {
	private FishBuyerGui _myGUI;

	public MyTableListener(FishBuyerGui gui) {
		super();
		this._myGUI = gui;
	}

	public FishBuyerGui getMyGUI() {
		return _myGUI;
	}

	public void set_myGUI(FishBuyerGui gui) {
		this._myGUI = gui;
	}

	@Override
	public void valueChanged(ListSelectionEvent lse) {
		if(lse.getValueIsAdjusting()){
			return;
		}
		ListSelectionModel lsm = (ListSelectionModel)lse.getSource();
		int selectedRow;
		if(lsm.isSelectionEmpty()){
			selectedRow = -1;
		}else{
			selectedRow = lsm.getMinSelectionIndex();
		}
		this._myGUI.updateSellerSelected(selectedRow);
	}

}
