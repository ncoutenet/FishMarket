package pojos;

import sellers.FishSellerAgent;

public class Announcement {
	// TODO ajouter une référence au vendeur
	private FishSellerAgent _myAgent;
	private String _myPrice;

	public Announcement(FishSellerAgent a) {
		this._myAgent = a;
		this._myPrice = String.valueOf(this._myAgent.getPrice());
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
