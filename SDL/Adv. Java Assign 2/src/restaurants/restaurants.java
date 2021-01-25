package restaurants;

import java.util.*;
import users.*;

public class restaurants {
	
	class Categories {
		
		public Categories(ArrayList<String> catAL) {
			catAL.add("Food");
			catAL.add("Drinks");
			catAL.add("Desserts");
		}
	}

	public class Items {
		
		public String itemName;
		public int itemPrice;
		
		public Items(String itemName, int itemPrice) {
			this.itemName = itemName;
			this.itemPrice = itemPrice;
		}
		
		public Items(LinkedHashMap<String, ArrayList<Items>> itemsLHM) {
			ArrayList<Items> Food = new ArrayList<Items>(Arrays.asList(
					new Items("Palak Paneer", 160), 
					new Items("Paneer Butter Masala", 180), 
					new Items("Rice", 80), 
					new Items("Dal", 120), 
					new Items("Roti", 10)));
			
			ArrayList <Items> Drinks = new ArrayList<Items>(Arrays.asList(
					new Items("Coca Cola", 30), 
					new Items("Sprite", 25), 
					new Items("Pepsi", 30)));
			
			ArrayList<Items> Desserts = new ArrayList<Items>(Arrays.asList(
					new Items("Gulab Jamun", 20)));
			
			itemsLHM.put("Food", Food);
			itemsLHM.put("Drinks", Drinks);
			itemsLHM.put("Desserts", Desserts);
		}
		
	}
	
	ArrayList<String> catAL = new ArrayList<String>(10);	
	LinkedHashMap<String, ArrayList<Items>> itemsLHM = new LinkedHashMap<String, ArrayList<Items>>();
	ArrayList<user> allOrder = new ArrayList<user>();
	PriorityQueue<user> allOrderPrio = new PriorityQueue<user>(new orderComparator());
}