package buyers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jade.core.Agent;

import javax.swing.*;

import pojos.listeners.MyBuyerListener;

public class FishBuyerGui extends JFrame{
	private FishBuyerAgent _myAgent;
	private JTextField _maxPrice;
	private JCheckBox _auto;
	
	public FishBuyerGui(FishBuyerAgent agent){
		this._myAgent = agent;
		
		_auto = new JCheckBox("Automatic");
		_auto.addActionListener(new MyBuyerListener(this));
		_auto.setActionCommand("autobuy");
		_auto.setSelected(true);
		
		this.getContentPane().add(_auto, BorderLayout.NORTH);
		
		if(_auto.isSelected()){
			this.constructAutomaticMode();
		} else{
			this.contructManualMode();
		}
		
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
		// TODO vider la fenetre
		this._maxPrice = new JTextField();
		JLabel labPrice = new JLabel("Maximum price: ");
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					float price = Float.valueOf(_maxPrice.getText());
					
					// TODO mettre à jour l'agent
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(FishBuyerGui.this, "Invalid values: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
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
		// TODO vider la fenetre
		this.pack();
	}

	public JTextField getMaxPrice() {
		return _maxPrice;
	}

	public void setMaxPrice(JTextField maxPrice) {
		this._maxPrice = maxPrice;
	}
}