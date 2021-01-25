package FOSAssign3;

/**
 * @author Om Ingle
 *
 */

import java.util.*;
import admin.*;
import users.*;
import restaurants.*;

public class FOSA3 {

	public static void main(String[] args)throws Exception {

		int userAdmin;
		Scanner scan = new Scanner(System.in);

		//user
		user u = new user();
				
		//restaurant manager
		restaurantManager rm = new restaurantManager();

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
							
							if(u.login(username, password)) {
								System.out.println("--------------------------");
								System.out.println("Login Successful...");
								System.out.println("--------------------------");
								
								int cartID = u.getCartId(username);
								
								int userRestID;

								u.displayCityRest(username);
								
								System.out.println("Enter 0 to Go Back...");
								System.out.println("--------------------------");
								userRestID = u.getUserRest();
								System.out.println("--------------------------");
								
								
								if(userRestID != 0) {
									int paid = 0;
									int userCatID, userItemID; 
									int totalPrice = 0;
									
									do {
										System.out.println("1. Order Items");
										System.out.println("2. Your Cart");
										System.out.println("3. Proceed to Pay");
										System.out.println("4. Ask Queries/Make Complaints");
										System.out.println("5. Logout");
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
												rm.showCat(userRestID);
												System.out.println("--------------------------");
												System.out.print("Enter Your Category ID : ");
												userCatID = scan.nextInt();
												System.out.println("--------------------------");
												rm.showItems(userCatID);
												System.out.println("0. Go Back");
												System.out.println("------------------------------");
												System.out.print("Enter Your Item ID : ");
												userItemID = scan.nextInt();
												
												if(userItemID!=0) {
													System.out.print("Enter Quantity : ");
													qty = scan.nextInt();
													System.out.println("--------------------------");	
													
													u.addToCart(cartID, userItemID, qty);
												}
												System.out.println("--------------------------");
												System.out.print("Order More [Y/N] : ");
												orderCont = scan.next();
												System.out.println("--------------------------");
											}while(orderCont.equals("Y") || orderCont.equals("y"));
											break;
											
										case 2:
											int cartAct;
											int rcItem;
											int i=1;
											totalPrice = 0;
											
											u.showCart(cartID);
											
											System.out.println("----------------------------------------------------------------");
											System.out.println("1. Remove Item   2. Remove All   3. Change Quantity   4. Go Back");
											System.out.println("----------------------------------------------------------------");
											System.out.print("Enter Your Choice : ");
											cartAct = scan.nextInt();
											System.out.println("--------------------------");
											switch(cartAct) {
											case 1:
												System.out.print("Enter Item ID to be Removed : ");
												rcItem = scan.nextInt();
												u.removeItem(cartID, rcItem);
												break;
											case 2:
												u.removeAllItems(cartID);
												break;
											case 3:
												System.out.print("Enter Item ID to be Changed : ");
												rcItem = scan.nextInt();
												
												int nQty;
												System.out.print("Enter New Quantity : ");
												nQty = scan.nextInt();
												u.changeItem(cartID, rcItem, nQty);
												
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
												System.out.println("3. Cash");
												System.out.println("4. Go Back");
												System.out.println("--------------------------");
												System.out.print("Enter Payment Method : ");
												payWay = scan.nextInt();
												System.out.println("--------------------------");
												switch(payWay) {
												case 1:
												case 2:
													int cnf;
													System.out.print("Card No. : ");
													cn = scan.nextLong();
													System.out.print("Expiry : ");
													ex = scan.nextInt();
													System.out.print("CVV : ");
													cvv = scan.nextInt();
													
													System.out.println("--------------------------");
													
													System.out.println("1. Pay and Order");
													System.out.println("2. Cancel");
													System.out.println("--------------------------");
													System.out.print("Enter Confirmation : ");
													cnf = scan.nextInt();
													System.out.println("--------------------------");
													if(cnf==1) {
														paid = 1;
														u.setOrders(username, cartID, userRestID, payWay);
													}
													else
														System.out.println("Order Canceled");
													
													System.out.println("--------------------------");
													break;
												case 3:
													System.out.println("1. Order");
													System.out.println("2. Cancel");
													System.out.println("--------------------------");
													System.out.print("Enter Confirmation : ");
													cnf = scan.nextInt();
													System.out.println("--------------------------");
													if(cnf==1) {
														paid = 1;
														u.setOrders(username, cartID, userRestID, payWay);
													}
													else
														System.out.println("Order Canceled");
													
													System.out.println("--------------------------");
													break;
												}
											}
											break;
											
										case 4:
											u.chat2rm(username);
											break;
										
										case 5:
											break;
											
										default:
											System.out.println("Please Enter Valid Choice...");
											System.out.println("----------------------------");
											break;
										}
									}while(userAct != 5);
								}
							}
							else {
								System.out.println("------------------------------");
								System.out.println("Incorrect Username or Password");
								System.out.println("------------------------------");
							}
						}
						else if(userLogReg ==2){
							String username, password, name, address, city;
							long mobNo;
							
							System.out.print("Name	: ");
							name = readString();
							System.out.print("Mobile No. : ");
							mobNo = scan.nextLong();
							System.out.print("Address : ");
							address = readString();
							System.out.print("City : ");
							city = readString();
							System.out.print("Username : ");
							username = scan.next();
							System.out.print("Password : ");
							password = scan.next();
							
							if(u.register(name, mobNo, address, city, username, password)) {
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
							
							if(rm.loginRM(rmUName, rmPass)) {
								System.out.println("Login Successful...");
								System.out.println("--------------------------");
								int adminAct;
								String addCat, addItem;
								int delCatID, editItemID;
								int delItemID;
								
								int restId = rm.getRestId(rmUName);
								
								int priceItem;
								do {
									System.out.println("1. Category");
									System.out.println("2. Items");
									System.out.println("3. View Orders");
									System.out.println("4. View User's Complaints");
									System.out.println("5. Ask Admin");
									System.out.println("6. Logout");
									System.out.println("--------------------------");
									System.out.print("Enter Choice : ");
									adminAct = scan.nextInt();
									
									System.out.println("--------------------------");
									
									switch(adminAct) {
									case 1:
										int catCh;
										System.out.println("------- Categories -------");
										rm.showCat(restId);
										System.out.println("-------------------------------------------------");
										System.out.println("1. Add Category   2. Delete Category   3. Go back");
										System.out.println("-------------------------------------------------");
										System.out.print("Enter Choice : ");
										catCh = scan.nextInt();
										System.out.println("--------------------------");
										
										if(catCh==1) {
											System.out.print("Enter Category Name : ");
											addCat = readString();
											rm.addCategory(addCat, restId);
										}
										else if(catCh==2) {
											System.out.print("Enter Category ID : ");
											delCatID = scan.nextInt();
											System.out.println("--------------------------");
											rm.delCategory(delCatID, restId);
											
										}
										System.out.println("--------------------------");
										break;
									
									case 2:
										int itemCh;
										String newEditItem;
										int newEditPrice;
										int catId;
										
										System.out.print("Enter Category ID : ");
										catId = scan.nextInt();
										System.out.println("--------------------------");
										rm.showItems(catId);
										System.out.println("--------------------------------------------------------");
										System.out.println("1. Add Item   2. Edit Item   3. Delete Item   4. Go back");
										System.out.println("--------------------------------------------------------");
										System.out.print("Enter Choice : ");
										itemCh = scan.nextInt();
										System.out.println("--------------------------");
										
										if(itemCh==1) {
											
											System.out.print("Enter Item Name : ");
											addItem = readString();
											System.out.print("Enter Price of " + addItem + " : ");
											priceItem = scan.nextInt();
											
											rm.addItems(restId, catId, addItem, priceItem);										
										}
										else if(itemCh==2) { 
											System.out.print("Enter Item ID : ");
											editItemID = scan.nextInt();
											
											System.out.print("Enter new Name : ");
											newEditItem = readString();
											
											System.out.print("Enter new Price for : ");
											newEditPrice = scan.nextInt();
											rm.editItems(editItemID, newEditItem, newEditPrice);
										}
										else if(itemCh==3) {
											System.out.print("Enter Item ID : ");
											delItemID = scan.nextInt();
											System.out.println("--------------------------");
											rm.delItems(delItemID);
										}
										System.out.println("--------------------------");					
										break;
									
									case 3:
										int ordAR;
										int ordId;
										System.out.println("-------------- Orders --------------");
										rm.showAllOrders(restId, "N");
										System.out.println("-----------------------------------------------------------------------------------");
										System.out.println("1. Change Status to Delivered  2. Go back");
										System.out.println("-----------------------------------------------------------------------------------");
										System.out.print("Enter Choice : ");
										ordAR = scan.nextInt();
										System.out.println("--------------------------");
										String uName;
										
										if(ordAR == 1) {
											System.out.print("Enter Order ID : ");
											ordId = scan.nextInt();
											System.out.println("--------------------------");
											rm.changeDeliveryStatus(ordId);
										}
										
										System.out.println("--------------------------");
										break;
									case 4:
										rm.chat2user();
										break;
									case 5:
										rm.chat2admin();										
										break;
										
									case 6:
										break;
										
									default:
										System.out.println("Please Enter Valid Choice...");
										System.out.println("----------------------------");
										break;
									}
														
								}while(adminAct != 6);
							}
							else {
								System.out.println("------------------------------");
								System.out.println("Incorrect Username or Password");
								System.out.println("------------------------------");
							}
						}
						else if(managerLog == 2) {
							rm = new restaurantManager();
							
							String usernameRM;
							String passwordRM, nameRM, addressR, cityR;
							long mobNoRM;
							int restId;
							
							System.out.print("Name	: ");
							nameRM = readString();
							System.out.print("Mobile No. : ");
							mobNoRM = scan.nextLong();
							System.out.print("Address : ");
							addressR = readString();
							System.out.print("Restaurant ID : ");
							restId = scan.nextInt();
							System.out.print("Username : ");
							usernameRM = scan.next();
							System.out.print("Password : ");
							passwordRM = scan.next();
							
							int x = rm.registerRM(nameRM, mobNoRM, addressR, restId, usernameRM, passwordRM); 
							
							System.out.println("--------------------------");
							if(x == 1) {
								System.out.println("Registered Successfully");
							}
							else if(x == 0){
								System.out.println("Please Select Another Username");
							}
							else if(x == 2)
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
									System.out.println("4. Messages from Restaurant Manager");
									System.out.println("5. Logout");
									System.out.println("--------------------------");
									System.out.print("Enter Choice : ");
									adminAct = scan.nextInt();
									
									System.out.println("--------------------------");
									
									switch(adminAct) {
									case 1:
										ad.addRest();
										break;
									case 2:
										ad.delRest();
										break;
									case 3:
										ad.displayRest();
										break;
									case 4:
										ad.chat2rm();
										break;
									case 5:
										break;
									default:
										System.out.println("Please Enter Valid Choice...");
										System.out.println("----------------------------");
										break;
									}
								}while(adminAct != 5);
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

