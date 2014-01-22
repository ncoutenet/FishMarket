package Seller;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Pojo.Fish;


public class FishSellerGui extends JFrame{
	private FishSellerAgent _myAgent;
	
	private JTextField _priceField;
	private JTextField _nameFish;
	
	public FishSellerGui(FishSellerAgent agent){
		_myAgent = agent;
		_priceField = new JTextField(15);
		_nameFish = new JTextField(15);
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		p.add(new JLabel("Type of fish: "));
		p.add(_nameFish);
		p.add(new JLabel("Initial fish price: "));
		p.add(_priceField);
		
		this.getContentPane().add(p, BorderLayout.CENTER);
		
		JButton btnAnnonce = new JButton("Annonce");
		btnAnnonce.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String type = _nameFish.getText();
					float price = Float.valueOf(_priceField.getText());
					
					//mise à jour de l'agent
					_myAgent.setFish(new Fish(type, price));
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(FishSellerGui.this, "Invalid values: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		p = new JPanel();
		p.add(btnAnnonce);
		
		getContentPane().add(p, BorderLayout.SOUTH);
		
		this.pack();
		
		//Quitte l'agent à la fermeture de la fenêtre
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				_myAgent.doDelete();
			}
		});
	}
	
	public void showGui(){
		this.pack();
		this.setVisible(true);
	}
}