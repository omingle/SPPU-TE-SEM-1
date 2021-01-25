package restaurants;

import java.util.Comparator;
import users.*;

public class orderComparator implements Comparator<user> {
	
	public int compare(user u1,user u2)
	{
		int totalPrice1 = 0, totalPrice2 = 0;
		
		for(order o1:u1.userOrder){
		    totalPrice1 = totalPrice1 + (o1.itemPrice*o1.qty);
		}
		
		for(order o2:u2.userOrder){
		    totalPrice2 = totalPrice2 + (o2.itemPrice*o2.qty);
		}
		
		if(totalPrice1 < totalPrice2)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}