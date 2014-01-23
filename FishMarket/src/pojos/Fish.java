package pojos;

public class Fish {
	private float _price;
	private String _type;
	
	public Fish(String type, float price){
		this._price = price;
		this._type = type;
	}
	
	public String getType() {
		return _type;
	}
	public void setType(String type) {
		this._type = type;
	}
	public float getPrice() {
		return _price;
	}
	public void setPrice(float price) {
		this._price = price;
	}
}
