package pojos;

public class Fish {
	private double _price;
	private String _type;
	
	public Fish(String type, double price){
		this._price = price;
		this._type = type;
	}
	
	public String getType() {
		return _type;
	}
	public void setType(String type) {
		this._type = type;
	}
	public double getPrice() {
		return _price;
	}
	public void setPrice(double price) {
		this._price = price;
	}
}
