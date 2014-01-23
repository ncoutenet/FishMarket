package sellers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import pojos.listeners.MySellerListener;



public class FishSellerGui extends JFrame{
	private FishSellerAgent _myAgent;
	
	private JFormattedTextField _priceField;
	private JTextField _nameFish;
	private JFormattedTextField _timeOut;
	
	private Vector<Vector<String>> _buyers;
	private JTable _buyTabler;
	private MySellerListener _listener;
	
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
		_listener = new MySellerListener(this);
		JButton btnAnnonce = new JButton("Annonce");
		btnAnnonce.addActionListener(_listener);
		
		JPanel p = new JPanel();
		
		p.setLayout(new GridLayout(3, 2));
		p.add(new JLabel("Type of fish: "));
		p.add(_nameFish);
		p.add(new JLabel("Initial fish price: "));
		p.add(_priceField);
		p.add(new JLabel("Time out: "));
		p.add(_timeOut);
		this.getContentPane().add(p, BorderLayout.NORTH);
		
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
	
	public FishSellerAgent getMyAgent() {
		return _myAgent;
	}

	public void setMyAgent(FishSellerAgent myAgent) {
		this._myAgent = myAgent;
	}

	public JFormattedTextField getPriceField() {
		return _priceField;
	}

	public void setPriceField(JFormattedTextField priceField) {
		this._priceField = priceField;
	}

	public JTextField getNameFish() {
		return _nameFish;
	}

	public void setNameFish(JTextField nameFish) {
		this._nameFish = nameFish;
	}

	public JFormattedTextField getTimeOut() {
		return _timeOut;
	}

	public void setTimeOut(JFormattedTextField timeOut) {
		this._timeOut = timeOut;
	}

	public Vector<Vector<String>> getBuyers() {
		return _buyers;
	}

	public void setBuyers(Vector<Vector<String>> buyers) {
		this._buyers = buyers;
	}

	public JTable getBuyTabler() {
		return _buyTabler;
	}

	public void setBuyTabler(JTable buyTabler) {
		this._buyTabler = buyTabler;
	}

	public MySellerListener getListener() {
		return _listener;
	}

	public void setListener(MySellerListener listener) {
		this._listener = listener;
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