package buyers;

import jade.core.AID;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import listeners.MyBuyerListener;
import listeners.MyTableListener;

public class FishBuyerGui extends JFrame{
	private FishBuyerAgent _myAgent;
	private JFormattedTextField _maxPrice;
	private JTextField _fishType;
	private JCheckBox _auto;
	private JLabel _labSellerSelected;
	
	private JTable _sellTable;
	private Vector<Vector<String>> _sellers; 
	
	public FishBuyerGui(FishBuyerAgent agent){
		this._myAgent = agent;
		this.setTitle(_myAgent.getName().toString().split("@")[0]);
		
		_auto = new JCheckBox("Automatic");
		_auto.addActionListener(new MyBuyerListener(this));
		_auto.setActionCommand("autobuy");
		_auto.setSelected(false);
		
		this._sellers = new Vector<Vector<String>>();
		Vector<String> header = new Vector<String>();
		header.add("Seller");
		header.add("Fish");
		header.add("Price");
		
		this._sellTable = new JTable(this._sellers, header);

		if(_auto.isSelected()){
			this.constructAutomaticMode();
		} else{
			this.constructManualMode();
		}
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				_myAgent.doDelete();
			}
		});
	}

	public FishBuyerAgent getMyAgent() {
		return _myAgent;
	}
	
	public int getSelectedSeller(){
		return this._sellTable.getSelectedRow();
	}
	
	public void deleteASeller(int index){
		this._sellers.remove(index);
		this._sellTable.updateUI();
		this.pack();
	}

	public boolean isAuto(){
		return this._auto.isSelected();
	}
	
	public void constructAutomaticMode(){
		this.getContentPane().removeAll();
		
		NumberFormat myFormat;
		myFormat = NumberFormat.getNumberInstance();
		myFormat.setMaximumFractionDigits(2);
		this._maxPrice = new JFormattedTextField(myFormat);
		this._fishType = new JTextField();
		JLabel labPrice = new JLabel("Maximum price: ");
		JLabel labType = new JLabel("Type of fish");
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new MyBuyerListener(this));
		btnSubmit.setActionCommand("btnsubmitbuyer");

		this.getContentPane().add(_auto, BorderLayout.NORTH);
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel p = new JPanel(new GridLayout(2, 2));
		p.add(labType);
		p.add(_fishType);
		p.add(labPrice);
		p.add(_maxPrice);
		panel.add(p, BorderLayout.CENTER);
		panel.add(btnSubmit, BorderLayout.SOUTH);
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
		p = new JPanel(new BorderLayout());
		p.add(_sellTable.getTableHeader(), BorderLayout.NORTH);
		p.add(_sellTable, BorderLayout.CENTER);
		this.getContentPane().add(p, BorderLayout.SOUTH);
		
		this.pack();
	}
	
	public void setSellers(List<AID> sellers,List<String> types, List<String> prices){
		this._sellers.clear();
		
		for(int i = 0; i < sellers.size(); i++){
			String name = sellers.get(i).getName().toString();
			name = name.split("@")[0];
			Vector<String> data = new Vector<String>(3);
			data.add(0, name);
			if (isAuto()){
				data.add(1, _fishType.getText());
			} else{
				data.add(1, types.get(i));
			}
			data.add(2, prices.get(i));
			
			_sellers.add(data);
			_sellTable.updateUI();
			this.pack();
		}
	}
	
	public void constructManualMode(){
		this.getContentPane().removeAll();
		
		this.getContentPane().add(_auto, BorderLayout.NORTH);
		ListSelectionModel lsm = this._sellTable.getSelectionModel();
		lsm.addListSelectionListener(new MyTableListener(this));
		JButton btnBuy = new JButton("Bid");
		btnBuy.addActionListener(new MyBuyerListener(this));
		btnBuy.setActionCommand("btnmanualbuy");
		this._labSellerSelected = new JLabel();
		
		this.setLabelSellerSelected(null);
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(_sellTable.getTableHeader(), BorderLayout.NORTH);
		p.add(_sellTable, BorderLayout.CENTER);
		p.add(_labSellerSelected, BorderLayout.SOUTH);
		this.getContentPane().add(p, BorderLayout.CENTER);
		this.getContentPane().add(btnBuy, BorderLayout.SOUTH);
		
		this.pack();
	}
	
	private void setLabelSellerSelected(String seller){
		String text;
		if(seller != null){
			text = new String(seller);
		}
		else{
			text = new String("No seller");
		}
		text += " selected!";
		
		this._labSellerSelected.setText(text);
	}

	public void updatePrice(double price, int index){
		Vector<String> vect = this._sellers.get(index);
		vect.set(2, String.valueOf(price));
		this._sellers.set(index, vect);
		this._sellTable.updateUI();
	}
	
	public void updateSellerSelected(int index){
		if(index == -1){
			this.setLabelSellerSelected(null);
		}else{
			this.setLabelSellerSelected(this._sellers.get(index).firstElement());
		}
	}

	public String getFishType() {
		return _fishType.getText();
	}

	public Double getMaxPrice() {
		return Double.parseDouble(_maxPrice.getValue().toString());
	}

	public void setMaxPrice(String maxPrice) {
		this._maxPrice.setText(maxPrice);
	}
}