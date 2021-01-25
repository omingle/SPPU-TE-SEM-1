package FOSAssign1;

/**
 * @author Om Ingle
 *
 */

import java.util.*;
import admin.*;
import users.*;
import restaurants.*;

public class FOSA1 {

	public static void main(String[] args) {

		int userAdmin;
		Scanner scan = new Scanner(System.in);

		//user
		LinkedHashSet<order> cartLHS = null;
		HashSet<user> userHS = new HashSet<user>();
		user u = null;
		
		//restaurant
		LinkedHashMap<String, restaurants> restLHM = new LinkedHashMap<String, restaurants>();
	
		//manager
		HashSet<restaurantManager> rmHS = new HashSet<restaurantManager>();
		restaurantManager rm = null;

		//admin
		admin ad = new admin();

		do {
			System.out.println("==========================");
			System.out.println("== Food Ordering System ==");
			System.out.println("==========================");
			System.out.println("1. User");
			System.out.println("2. Restaurant Manager");
			System.out.println("3. Admin");
			System.out.println("4. Exit");
			System.out.println("==========================");
			System.out.print("Enter Choice : ");
			userAdmin = scan.nextInt();
			System.out.println("==========================");
			
			try {
				switch(userAdmin) {
				case 1:
					int userAct;
					int userCat, userItem; 
					int totalPrice = 0;
					int userLogReg;
					
					do {
						System.out.println("--------- Users ----------");
						System.out.println("1. Login");
						System.out.println("2. Register");
						System.out.println("3. Go back");
						System.out.println("--------------------------");
						System.out.print("Enter Choice : ");
						userLogReg = scan.nextInt();
						System.out.println("--------------------------");
											
						if(userLogReg==1) {
							
							String username, password;
							System.out.println("------| Users Login |-----");
							System.out.println("--------------------------");
							System.out.print("Username : ");
							username = scan.next();
							System.out.print("Password : ");
							password = scan.next();
							
							if(!userHS.isEmpty()) {
								for(user ui:userHS) {
									if(ui.username.equals(username))
									{
										u = ui;
										cartLHS = u.userOrder;
										break;
									}							
								}
							}
							
							if(u.login(userHS, username, password)) {
								System.out.println("--------------------------");
								System.out.println("Login Successful...");
								System.out.println("--------------------------");
								if(u.userOrder==null) {
									cartLHS = new LinkedHashSet<order>();
									u.addOrder(cartLHS);
								}
								
								do {
									String userRest = null, userCity;
									rm = new restaurantManager();
									System.out.println("--------------------------");
									System.out.print("Enter Your City : ");
									userCity = readString();
									if(rm.checkCity(rmHS, userCity)) {
										System.out.println("--------------------------");
										System.out.println("--- Select Restaurant ----");
										rm.displayCityRest(rmHS, userCity);
										System.out.println("--------------------------");
										System.out.print("Enter Restaurant Name : ");
										userRest = readString();
										System.out.println("--------------------------");
										
									}
									else {
										System.out.println("------------------------------------------------------");
										System.out.println("Sorry.... Our Service is not available in your city...");
										System.out.println("------------------------------------------------------");
									}
									
									rm = rm.getRest(restLHM, rmHS, userRest);
									
								}while(rm==null);
								
								int paid=0;
								do {
									System.out.println("1. Order Items");
									System.out.println("2. Your Cart");
									System.out.println("3. Proceed to Pay");
									System.out.println("4. Logout");
									System.out.println("--------------------------");
									System.out.print("Enter Choice : ");
									userAct = scan.nextInt();
									
									switch(userAct) {
									case 1:
										String orderCont;
										int qty;
										do {
											System.out.println("--------------------------");
											System.out.println("------- Categories -------");
											rm.showCat();
											System.out.println("--------------------------");
											System.out.print("Enter Your Category : ");
											userCat = scan.nextInt();
											System.out.println("--------------------------");
											System.out.println("------------ " + rm.getCat(userCat) + " ------------");
											System.out.println("-- Item Name --------- Price -");
											System.out.println("------------------------------");
											rm.showItems(rm.getCat(userCat));
											System.out.println("0. Go Back");
											System.out.println("------------------------------");
											System.out.print("Enter Your Item : ");
											userItem = scan.nextInt();
											if(userItem!=0) {
												System.out.print("Enter Quantity : ");
												qty = scan.nextInt();
												System.out.println("--------------------------");	
												order or = new order(rm.getItemName(rm.getCat(userCat), userItem-1), qty, rm.getItemPrice( rm.getCat(userCat), userItem-1));
												if(cartLHS.add(or)) {
													System.out.println("Item added to cart");
												}
												else {
													System.out.println("Item is already present in cart");
												}
											}
											System.out.println("--------------------------");
											System.out.print("Order More [Y/N] : ");
											orderCont = scan.next();
											System.out.println("--------------------------");
										}while(orderCont.equals("Y") || orderCont.equals("y"));
										break;
										
									case 2:
										int cartAct;
										String rcItem;
										int i=1;
										totalPrice = 0;
										System.out.println("--------------------------------------");
										System.out.println("SN.----Items-------------Price---Qty--");
										System.out.println("--------------------------------------");
	
										for(order io:cartLHS){
										    System.out.printf(i + ". %-20s %5d %5d \n", io.itemName, (io.itemPrice*io.qty), io.qty);
										    totalPrice = totalPrice + (io.itemPrice*io.qty);
										    i++;
										}
										System.out.println("--------------------------------------");
										System.out.println("Total Amount :        Rs. " + totalPrice);
										System.out.println("--------------------------------------");
										System.out.println("----------------------------------------------------------------");
										System.out.println("1. Remove Item   2. Remove All   3. Change Quantity   4. Go Back");
										System.out.println("----------------------------------------------------------------");
										System.out.print("Enter Your Choice : ");
										cartAct = scan.nextInt();
										System.out.println("--------------------------");
										switch(cartAct) {
										case 1:
											System.out.print("Item Name to be Removed : ");
											rcItem = readString();
											order roi = new order(rcItem, 0, 0);
											cartLHS.remove(roi);
											break;
										case 2:
											cartLHS.clear();
											break;
										case 3:
											System.out.print("Item Name to be Changed : ");
											rcItem = readString();
											order coi = new order(rcItem,0,0);
											int nQty;
											if(cartLHS.contains(coi)) {
												for (order obj : cartLHS) {
											        if (obj.equals(coi)) {
											        	System.out.print("Enter New Quantity : ");
											        	nQty = scan.nextInt();
											        	obj.qty = nQty;
											        }
												}
											}
										    else
										       	System.out.println("Please Select item that is present in the cart.");
											
											System.out.println("--------------------------");
											break;
										case 4:
											
											break;
										default:
											System.out.println("Please Enter Valid Choice...");
											break;
										}
										System.out.println("--------------------------");
										break;
										
									case 3:
										int payWay;
										long cn;
										int ex, cvv;
										if(paid==1) {
											System.out.println("----------------------------------------------------------");
											System.out.println("You cannot order until your previous order gets delivered.");
											System.out.println("----------------------------------------------------------");
										}else {
											System.out.println("--------------------------");
											System.out.println("Select the way of payment");
											System.out.println("--------------------------");
											System.out.println("1. Credit Card");
											System.out.println("2. Debit Card");
											System.out.println("3. Go Back");
											System.out.println("--------------------------");
											System.out.print("Enter Payment Method : ");
											payWay = scan.nextInt();
											System.out.println("--------------------------");
											switch(payWay) {
											case 1:
											case 2:
												int cnf;
												System.out.println("Amount to Pay : Rs." + totalPrice);
												System.out.println("--------------------------");
												System.out.print("Card No. : ");
												cn = scan.nextLong();
												System.out.print("Expiry : ");
												ex = scan.nextInt();
												System.out.print("CVV : ");
												cvv = scan.nextInt();
												
												System.out.println("--------------------------");
												
												System.out.println("1. Pay");
												System.out.println("2. Cancel");
												System.out.println("--------------------------");
												System.out.print("Enter Confirmation : ");
												cnf = scan.nextInt();
												System.out.println("--------------------------");
												if(cnf==1) {
													System.out.println("Ordered Succesfully...");
													paid=1;
													rm.setOrders(u);
												}
												else
													System.out.println("Order Canceled");
												
												System.out.println("--------------------------");
												break;
											}
										}
										break;
										
									case 4:
										break;
										
									default:
										System.out.println("Please Enter Valid Choice...");
										System.out.println("----------------------------");
										break;
									}
								}while(userAct != 4);
							}
							else {
								System.out.println("------------------------------");
								System.out.println("Incorrect Username or Password");
								System.out.println("------------------------------");
							}
						}
						else if(userLogReg ==2){
							u = new user();
							if(u.register(userHS)) {
								System.out.println("--------------------------");
								System.out.println("Registered Successfully");
							}
							else {
								System.out.println("--------------------------");
								System.out.println("Please Select Another Username");
							}
							System.out.println("--------------------------");
						}
					}while(userLogReg!=3);
					break;
				
				case 2:
					int managerLog;
					String rmUName, rmPass;
					
					do {
						System.out.println("--- Restaurant Manager ---");
						System.out.println("1. Login");
						System.out.println("2. Register");
						System.out.println("3. Go back");
						System.out.println("--------------------------");
						System.out.print("Enter Choice : ");
						managerLog = scan.nextInt();
						
						System.out.println("--------------------------");
						
						if(managerLog==1) {
							
							System.out.println("-----| Manager Login |----");
							System.out.println("--------------------------");
							System.out.print("Enter Username : ");
							rmUName = scan.next();
							System.out.print("Enter Password : ");
							rmPass = scan.next();
						
							System.out.println("--------------------------");
							
							if(!rmHS.isEmpty()) {
								for(restaurantManager rmg:rmHS) {
									if(rmg.usernameRM.equals(rmUName))
									{
										rm = rmg;
										break;
									}							
								}
							}
							
							if(rm.loginRM(rmHS, rmUName, rmPass)) {
								System.out.println("Login Successful...");
								System.out.println("--------------------------");
								int adminAct;
								String addDelCat, addEditItem;
								int delItem;
								int priceItem;
								do {
									System.out.println("1. Category");
									System.out.println("2. Items");
									System.out.println("3. View Orders");
									System.out.println("4. Logout");
									System.out.println("--------------------------");
									System.out.print("Enter Choice : ");
									adminAct = scan.nextInt();
									
									System.out.println("--------------------------");
									
									switch(adminAct) {
									case 1:
										int catCh;
										System.out.println("------- Categories -------");
										rm.showCat();
										System.out.println("-------------------------------------------------");
										System.out.println("1. Add Category   2. Delete Category   3. Go back");
										System.out.println("-------------------------------------------------");
										System.out.print("Enter Choice : ");
										catCh = scan.nextInt();
										System.out.println("--------------------------");
										
										if(catCh==1) {
											System.out.print("Enter Category Name : ");
											addDelCat = readString();
											rm.addCategory(addDelCat);
										}
										else if(catCh==2) {
											System.out.print("Enter Category Name : ");
											addDelCat = readString();
											rm.delCategory(addDelCat);
											
										}
										System.out.println("--------------------------");
										break;
									
									case 2:
										int itemCh;
										String newEditItem;
										int newEditPrice;
										
										System.out.print("Enter Category Name : ");
										addDelCat = readString();
										System.out.println("--------------------------");
										System.out.println("------------ " + addDelCat + " ------------");
										System.out.println("-- Item Name --------- Price -");
										System.out.println("------------------------------");
										rm.showItems(addDelCat);
										System.out.println("--------------------------------------------------------");
										System.out.println("1. Add Item   2. Edit Item   3. Delete Item   4. Go back");
										System.out.println("--------------------------------------------------------");
										System.out.print("Enter Choice : ");
										itemCh = scan.nextInt();
										System.out.println("--------------------------");
										
										if(itemCh==1) {
											
											System.out.print("Enter Item Name : ");
											addEditItem = readString();
											System.out.print("Enter Price of " + addEditItem + " : ");
											priceItem = scan.nextInt();
											
											
											
											rm.addItems(addDelCat, addEditItem, priceItem);
	
	//										System.out.println("--------------------------");							
	//										itemObj.showItems(itemsLHM, addDelCat);
	//										System.out.println("------------------------------");
											
										}
										else if(itemCh==2) { 
											System.out.print("Enter Item Name : ");
											addEditItem = readString();
											
											System.out.print("Enter new Name for " + addEditItem + " : ");
											newEditItem = readString();
											
											System.out.print("Enter new Price for " + addEditItem + " : ");
											newEditPrice = scan.nextInt();
											rm.editItems(addDelCat, addEditItem, newEditItem, newEditPrice);
										}
										else if(itemCh==3) {
											System.out.print("Enter Item Number : ");
											delItem = scan.nextInt();
											rm.delItems(addDelCat, delItem-1);
	//										itemObj.showItems(itemsLHM, addDelCat);
										}
										System.out.println("--------------------------");					
										break;
									
									case 3:
										int ordAR;
										System.out.println("--------- Orders ---------");
										rm.showAllOrders();
										System.out.println("---------------------------------------------------------");
										System.out.println("1. Approve Order   2. Remove Delivered Order   3. Go back");
										System.out.println("---------------------------------------------------------");
										System.out.print("Enter Choice : ");
										ordAR = scan.nextInt();
										System.out.println("--------------------------");
										String uName;
										if(ordAR==1) {
											System.out.print("Enter the Username : ");
											uName = scan.next();
											System.out.println("--------------------------");
											rm.approveOrder(uName);
										}
										else if(ordAR==2) { 
											System.out.print("Enter the Username : ");
											uName = scan.next();
											System.out.println("--------------------------");
											rm.removeCompletedOrder(uName);
										}
										
										System.out.println("--------------------------");
										break;
									case 4:
										break;
									default:
										System.out.println("Please Enter Valid Choice...");
										System.out.println("----------------------------");
										break;
									}
														
								}while(adminAct != 4);
							}
							else {
								System.out.println("------------------------------");
								System.out.println("Incorrect Username or Password");
								System.out.println("------------------------------");
							}
						}
						else if(managerLog == 2) {
							rm = new restaurantManager();
							int x = rm.registerRM(rmHS, restLHM); 
							
							System.out.println("--------------------------");
							if(x==1) {
								System.out.println("Registered Successfully");
							}
							else if(x==0){
								System.out.println("Please Select Another Username");
							}
							else if(x==2)
							{
								System.out.println("Restaurant is not added by Admin");
							}
							System.out.println("--------------------------");
						}
					}while(managerLog!=3);
					break;
					
				case 3:
					int adminLog;
					String adUName, adPass;
					
					do {
						System.out.println("--------- Admin ----------");
						System.out.println("1. Login");
						System.out.println("2. Go back");
						System.out.println("--------------------------");
						System.out.print("Enter Choice : ");
						adminLog = scan.nextInt();
						
						System.out.println("--------------------------");
						
						if(adminLog==1) {
							
							System.out.println("------| Admin Login |-----");
							System.out.println("--------------------------");
							System.out.print("Enter Username : ");
							adUName = scan.next();
							System.out.print("Enter Password : ");
							adPass = scan.next();
						
							System.out.println("--------------------------");
							
							if(ad.adminLogin(adUName, adPass)) {
								System.out.println("Login Successful...");
								System.out.println("--------------------------");
								int adminAct;
								String addRest, delRest;
								do {
									System.out.println("1. Add Restaurant");
									System.out.println("2. Delete Restaurant");
									System.out.println("3. Display Restaurants");
									System.out.println("4. Logout");
									System.out.println("--------------------------");
									System.out.print("Enter Choice : ");
									adminAct = scan.nextInt();
									
									System.out.println("--------------------------");
									
									switch(adminAct) {
									case 1:
										ad.addRest(restLHM);
										break;
									case 2:
										ad.delRest(restLHM);
										break;
									case 3:
										ad.displayRest(restLHM);
										break;
									case 4:
										break;
									default:
										System.out.println("Please Enter Valid Choice...");
										System.out.println("----------------------------");
										break;
									}
								}while(adminAct != 4);
							}
							else {
								System.out.println("------------------------------");
								System.out.println("Incorrect Username or Password");
								System.out.println("------------------------------");
							}
						}
					}while(adminLog!=2);
					break;
				
				case 4:
					break;
				
				default:
					System.out.println("Please Enter Valid Choice...");
					System.out.println("----------------------------");
					break;
				}
			}
			catch(Exception e) {
				System.out.println("Exception : " + e.getMessage());
				System.out.println("Exception : " + e.getLocalizedMessage());
			}
		} while(userAdmin!=4);
	}
	public static String readString()
	{
	    Scanner scanner = new Scanner(System.in);
	    return scanner.nextLine();
	} 
}

