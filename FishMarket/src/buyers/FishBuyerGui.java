package buyers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import javax.swing.*;

import pojos.Fish;

import listeners.MyBuyerListener;

import sellers.FishSellerAgent;

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
		// TODO récupérer les infos des vendeurs pour affichage
		
		for(int i = 0; i < sellers.size(); i++){ 
			Vector<String> agents = new Vector<String>();
			agents.add(sellers.get(i).getName());
			if(this.isAuto()){
				agents.add(_fishType.getText());
			} else{
				agents.add(types.get(i));
			}
			agents.add(prices.get(i));
		}
	}
	
	public void constructManualMode(){
		this.getContentPane().removeAll();
		
		this.getContentPane().add(_auto, BorderLayout.NORTH);
		JButton btnBuy = new JButton("Buy");
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
	
	public void setLabelSellerSelected(String seller){
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