package pojos;

public class Announcement {
	// TODO ajouter une référence au vendeur
	private String _myPrice;

	public Announcement(String price) {
		// TODO modifier pour prendre le prix du vendeur
		this._myPrice = price;
	}

	/**
	 * @return the _myPrice
	 */
	public String getMyPrice() {
		return _myPrice;
	}

	/**
	 * @param price the _myPrice to set
	 */
	public void setMyPrice(String price) {
		this._myPrice = price;
	}
	
}
