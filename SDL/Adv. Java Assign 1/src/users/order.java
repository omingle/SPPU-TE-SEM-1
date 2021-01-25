package users;

public class order {
	public String itemName;
	public int qty, itemPrice;
	public order(String item, int qty, int itemPrice) {
		this.itemName = item;
		this.qty = qty;
		this.itemPrice = itemPrice;
	}
	
	// To store unique values in set
	public boolean equals(Object obj){
		order or = (order)obj;
		if(or.itemName.equals(this.itemName)){
			return true;
		}
		return false;
	}
	
	// To store unique values in set
	public int hashCode() {
		return this.itemName.hashCode();
	}
}
