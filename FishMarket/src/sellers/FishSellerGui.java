package sellers;

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
import java.util.Locale;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import pojos.Fish;



public class FishSellerGui extends JFrame{
	private FishSellerAgent _myAgent;
	
	private JFormattedTextField _priceField;
	private JTextField _nameFish;
	private JFormattedTextField _timeOut;
	
	private Vector<Vector<String>> _buyers;
	private JTable _buyTabler;
	
	public FishSellerGui(FishSellerAgent agent){
		NumberFormat myFormat;
		myFormat = NumberFormat.getNumberInstance();
		myFormat.setMinimumFractionDigits(3);
		_myAgent = agent;
		_priceField = new JFormattedTextField(myFormat);
		_nameFish = new JTextField();
		_timeOut =  new JFormattedTextField(myFormat);
		
		_buyers = new Vector<Vector<String>>();
		Vector<String> header = new Vector<String>();
		header.add("Buyers");
		_buyTabler = new JTable(_buyers, header);
		JButton btnAnnonce = new JButton("Annonce");
		
		JPanel p = new JPanel();
		
		p.setLayout(new GridLayout(3, 2));
		p.add(new JLabel("Type of fish: "));
		p.add(_nameFish);
		p.add(new JLabel("Initial fish price: "));
		p.add(_priceField);
		p.add(new JLabel("Time out: "));
		p.add(_timeOut);
		this.getContentPane().add(p, BorderLayout.NORTH);
		
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
		
		getContentPane().add(p, BorderLayout.CENTER);
		
		p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(_buyTabler.getTableHeader(), BorderLayout.NORTH);
		p.add(_buyTabler, BorderLayout.CENTER);
		getContentPane().add(p, BorderLayout.SOUTH);
		
		this.pack();
		
		//Quitte l'agent à la fermeture de la fenêtre
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				_myAgent.doDelete();
			}
		});
	}
	
	public void addBuyer(String buyer){
		Vector<String> vect = new Vector<String>();
		vect.add(buyer);
		this._buyers.add(vect);
	}
	
	public void initBuyers(){
		this._buyers.clear();
	}
	
	public void removeBuyer(String buyer){
		int i = 0;
		while( i < this._buyers.size()){
			if(this._buyers.get(i).get(0).equals(buyer)){
				this._buyers.remove(i);
			}
			i++;
		}
	}
	
	public void showGui(){
		this.setVisible(true);
	}
}