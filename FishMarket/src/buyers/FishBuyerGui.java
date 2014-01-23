package buyers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Vector;

import jade.core.Agent;

import javax.swing.*;

import pojos.listeners.MyBuyerListener;

public class FishBuyerGui extends JFrame{
	private FishBuyerAgent _myAgent;
	private JFormattedTextField _maxPrice;
	private JCheckBox _auto;
	private JLabel _labSellerSelected;
	
	private JTable _sellTable;
	private Vector<Vector<String>> _sellers; 
	
	public FishBuyerGui(FishBuyerAgent agent){
		this._myAgent = agent;
		
		_auto = new JCheckBox("Automatic");
		_auto.addActionListener(new MyBuyerListener(this));
		_auto.setActionCommand("autobuy");
		_auto.setSelected(true);
		
		if(_auto.isSelected()){
			this.constructAutomaticMode();
		} else{
			this.contructManualMode();
		}
		
		this._sellers = new Vector<Vector<String>>();
		Vector<String> header = new Vector<String>();
		header.add("Seller");
		header.add("Price");
		
		this._sellTable = new JTable(this._sellers, header);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				_myAgent.doDelete();
			}
		});
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
		JLabel labPrice = new JLabel("Maximum price: ");
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new MyBuyerListener(this));
		btnSubmit.setActionCommand("btnsubmitbuyer");

		this.getContentPane().add(_auto, BorderLayout.NORTH);
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		p.add(labPrice);
		p.add(_maxPrice);
		this.getContentPane().add(p, BorderLayout.CENTER);
		
		p = new JPanel();
		p.add(btnSubmit);
		this.getContentPane().add(p, BorderLayout.SOUTH);
		
		this.pack();
	}
	
	public void contructManualMode(){
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

	public Double getMaxPrice() {
		return Double.parseDouble(_maxPrice.getValue().toString());
	}

	public void setMaxPrice(String maxPrice) {
		this._maxPrice.setText(maxPrice);
	}
}