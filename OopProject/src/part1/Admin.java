package part1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Admin {

	public static void main(String[] args) throws IOException, ParseException {
		//Declaration
		List<User>user = new ArrayList<>();
		List<Food> food = new ArrayList<>();
		List<Drink>drink = new ArrayList<>();
		List<OrderList>orderList = new ArrayList<>();
		//Create file name orderList.txt
		File file1 = null;
		try {
			file1 = new File("orderList.txt");
			file1.createNewFile();
		}catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		//Create file name product.txt
		try {
			file1 = new File("product.txt");
			file1.createNewFile();
		}catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		//Create file name user.txt
		try {
			file1 = new File("user.txt");
			file1.createNewFile();
		}catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		//Create file name HistoryOrderList.txt
		try {
			file1 = new File("HistoryOrderList.txt");
			file1.createNewFile();
		}catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		//Read product from the file product.txt
		Scanner readFile = new Scanner(new File("product.txt"));
		int fileloop = 0;
		while (readFile.hasNextLine()) {//when file is not empty
			String a = readFile.nextLine();
			String[] b = a.split("#",2);
			String[] c = b[0].split("/", 3);
			String[] d = b[1].split("/",3);
			if(c[0].equalsIgnoreCase("!")) {
				//Do nothing
			}else {
				//Input food arrayList
				food.add(new Food ("",0,0));
				food.get(fileloop).setProductName(c[0]);
				food.get(fileloop).setDetail(Double.valueOf(c[1]));
				food.get(fileloop).setPrice(Double.valueOf(c[2]));
			}
			if(d[0].equalsIgnoreCase("!")) {
				//Do nothing
			}else {
				//Input food arrayList
				drink.add(new Drink("",0,0));
				drink.get(fileloop).setProductName(d[0]);
				drink.get(fileloop).setDetail(Double.valueOf(d[1]));
				drink.get(fileloop).setPrice(Double.valueOf(d[2]));
			}
			fileloop++;
		}
				
		//Read user from user.txt
		Scanner readUserFile = new Scanner(new File("user.txt"));
		fileloop = 0;
		while (readUserFile.hasNextLine()) {//when file is not empty
			String a = readUserFile.nextLine();
			String[] b = a.split("/", 3);
			
			//Input food arrayList
			user.add(new User("","",""));
			user.get(fileloop).setName(b[0]);
			user.get(fileloop).setNoMatrics(b[1]);
			
			// now convert the string to byte array
            // for decryption
            byte[] bb = new byte[b[2].length()];
            for (int i=0; i<b[2].length(); i++) {
                bb[i] = (byte) b[2].charAt(i);
            }
            String password = "";
            try {
	            // decrypt the text
	            String key = "Bar12345Bar12345";
	            SecretKeySpec aesKey = new SecretKeySpec(key.getBytes(), "AES");
	            Cipher cipher = Cipher.getInstance("AES");
	            cipher.init(Cipher.DECRYPT_MODE, aesKey);
	            password = new String(cipher.doFinal(bb));
            }catch (Exception e) {
            	e.printStackTrace();
            }
            //read password into user arrayList
			user.get(fileloop).setPassword(password);
			
			fileloop++;
		}
		
		//Read orderList from orderList.txt
		Scanner readOrderLisrFile = new Scanner(new File("orderList.txt"));
		fileloop = 0;
		while (readOrderLisrFile.hasNextLine()) {//when file is not empty

			orderList.add(new OrderList(null,null,null,null));
			List<Food> foodReadFile = new ArrayList<>();
			String a = readOrderLisrFile.nextLine();
			String[] b = a.split("#", 0);
			
			//User
			for(int i = 0; i<user.size() ; i++) {
				//compare the string b[0] with the NoMatrics in the user arrayList
				if(b[0].equalsIgnoreCase(user.get(i).getNoMatrics())) {
					//set user to orderList
					orderList.get(fileloop).setUser(user.get(i));
					break;
				}
			}
			
			//Food
			String[] c1 = b[1].split("@",0);
			for(int i=1 ; i < c1.length ;i++) {
				String[] d1 = c1[i].split("/",0);
				int sauce = 0;
				if(d1[4].equalsIgnoreCase("spicy")) {
					sauce = 1;
				}else if(d1[4].equalsIgnoreCase("tomato")) {
					sauce = 2;
				}else if(d1[4].equalsIgnoreCase("normal")) {
					sauce = 3;
				}
				//set foodname, calories, price, quantity and sauce into foodReadFile arrayList
				foodReadFile.add(new Food(d1[0], Double.valueOf(d1[1]), Double.valueOf(d1[2]), Integer.valueOf(d1[3]), sauce));
				//Set food into orderList arrayList
				orderList.get(fileloop).setFood(foodReadFile);
			}
			
			//Drink
			List<Drink>drinkReadFile = new ArrayList<>();
			String[] c2 = b[2].split("@",0);
			for(int i=1 ; i < c2.length ;i++) {
				String[] d2 = c2[i].split("/",0);
				//set DrinkName, calories, price, quantity, goLarge status and addIce status into drinkReadFile arrayList
				drinkReadFile.add(new Drink(d2[0], Double.valueOf(d2[1]), Double.valueOf(d2[2]), Integer.valueOf(d2[3]) , Boolean.valueOf(d2[4]), Boolean.valueOf(d2[5])));
				//Set drink into orderList arrayList
				orderList.get(fileloop).setDrink(drinkReadFile);
			}
			
			//Date
			//set date into orderList
			orderList.get(fileloop).setOrderTime(b[3]);
			
			fileloop++;
		}
		
		int option = 0;
		Boolean valid = false;
		
		do {
			do {
				//Print menu
				System.out.println("--------Admin mode---------");
				System.out.println("|1)Order List             |");
				System.out.println("|2)Product                |");
				System.out.println("|3)User setting           |");
				System.out.println("|4)Exit                   |");
				System.out.println("---------------------------");
				
				//Check validity
				try {
					Scanner input = new Scanner(System.in);
					System.out.println("Enter the number of menu you want:");
					option = input.nextInt();
					valid = true;
				}catch (InputMismatchException e) {//if user not input an integer
					System.out.println("!!! Please enter a number !!!");
					valid = false;
				}
			}while(!valid);//Repeat when no valid
			
			switch(option) {
			
			case 1:
				int optionOrder = 0;
				
				do {
					do {
						//Print menu for orderList
						System.out.println("\n\n------OrderList mode-------");
						System.out.println("|1)View Order List        |");
						System.out.println("|2)Complete an Order      |");
						System.out.println("|3)Exit                   |");
						System.out.println("---------------------------");
						
						//Check validity
						try {
							Scanner input = new Scanner(System.in);
							System.out.println("Enter the number of menu you want:");
							optionOrder = input.nextInt();
							valid = true;
						}catch (InputMismatchException e) {//if user not input integer
							System.out.println("!!! Please enter a number !!!");
							valid = false;
						}
					}while(!valid);//Repeat when no valid
					
					switch(optionOrder) {
					case 1:
						for(int i=0 ; i<orderList.size() ; i++) {
							//Display orderList for each customer
							System.out.println("\n--------------------------Customer" + (i+1) + "--------------------------" );
							System.out.println("Customer Name: " + orderList.get(i).getUser().getName());
							System.out.println("Time order: " + orderList.get(i).getOrderTime());
							if(orderList.get(i).getFood().size() != 0) {//if food ArrayList is not empty
								System.out.println("----------------------------Food----------------------------");
								for(int j = 0 ; j < orderList.get(i).getFood().size() ; j++) {
									//Display Food ordered by the customer
									System.out.println( j+1+ ")" + orderList.get(i).getFood().get(j) + " --- " + orderList.get(i).getFood().get(j).getQuantityInOrder() );
								}
							}
							
							if(orderList.get(i).getDrink().size() != 0) {//if drink ArrayList is not empty
								System.out.println("----------------------------Drink---------------------------");
								for(int j = 0; j<orderList.get(i).getDrink().size() ; j++) {
									//Display drink ordered by the customer
									System.out.println( j+1+ ")" + orderList.get(i).getDrink().get(j) + " --- " + orderList.get(i).getDrink().get(j).getQuantityInOrder());
								}
							}
							
						}
						if(orderList.size() == 0) {//If the orderList is empty
							System.out.println("The order list is empty");			
						}
						break;
					case 2:
						//Complete order
						//Remove orderList for specific customer when complete order
						for(int i=0 ; i<orderList.size() ; i++) {
							//Display customer name and time order
							System.out.println("\n--------------------------Customer" + (i+1) + "--------------------------" );
							System.out.println("Customer Name: " + orderList.get(i).getUser().getName());
							System.out.println("Time order: " + orderList.get(i).getOrderTime());
							if(orderList.get(i).getFood().size() != 0) {//if food arrayList is not empty
								System.out.println("----------------------------Food----------------------------");
								for(int j = 0 ; j < orderList.get(i).getFood().size() ; j++) {
									//Display food ordered by the customer
									System.out.println( j+1+ ")" + orderList.get(i).getFood().get(j) + " --- " + orderList.get(i).getFood().get(j).getQuantityInOrder() );
								}
							}
							
							if(orderList.get(i).getDrink().size() != 0) {//if drink ArrayList is not empty
								System.out.println("----------------------------Drink---------------------------");
								for(int j = 0; j<orderList.get(i).getDrink().size() ; j++) {
									//Display drink ordered by the customer
									System.out.println( j+1+ ")" + orderList.get(i).getDrink().get(j) + " --- " + orderList.get(i).getDrink().get(j).getQuantityInOrder());
								}
							}
						}
						
						do {
							//Check validity
							try {
								Scanner input = new Scanner(System.in);
								System.out.println("Enter the customer number that order completed:");
								int optionRemove = input.nextInt();
								
								//Remove the orderList
								if(optionRemove >0 && optionRemove <= orderList.size()) {
									appendStrToFile("HistoryOrderList.txt",orderList.get(optionRemove-1).toHistoryFile() + "\n");
									orderList.remove(optionRemove -1);
								}else {
									System.out.println("The list no inside the order list !!!");
								}
								valid = true;
							}catch (InputMismatchException e) {
								System.out.println("!!! Please enter a number !!!");
								valid = false;
							}
						}while(!valid);
						
						break;
					case 3:
						//exit from orderList mode
						break;
					default: 
						//if user input other than 1,2,3
						System.out.println("Please enter the number from 1-3.");
					}
					
				}while(optionOrder != 3);
				
				//write orderList into orderList.txt
				FileWriter orderFile = new FileWriter("orderList.txt");
				for(int i = 0 ; i < orderList.size() ; i++) {
					orderFile.write(orderList.get(i).toFile() + "\n");
				}
				orderFile.close();
				
				break;
			
			case 2:
				//Add or remove menu
				int optionProduct = 0;
				do {
					do {
						//print menu
						System.out.println("\n\n------Product mode-------");
						System.out.println("|1)Add Food                 |");
						System.out.println("|2)Add Drink                |");
						System.out.println("|3)Remove Food              |");
						System.out.println("|4)Remove Drink             |");
						System.out.println("|5)Exit                     |");
						System.out.println("---------------------------");
						
						//Check validity
						try {
							Scanner input = new Scanner(System.in);
							System.out.println("Enter the number of menu you want:");
							optionProduct = input.nextInt();
							valid = true;
						}catch (InputMismatchException e) {
							System.out.println("!!! Please enter a number !!!");
							valid = false;
						}
					}while(!valid);
					
					switch(optionProduct) {
					case 1:
						//Add new Food
						try {
							Scanner input = new Scanner(System.in);
							System.out.println("Enter the name of the food:");
							String foodName = input.nextLine();
							System.out.println("Enter the calories of the food:");
							double foodDetail = input.nextDouble();
							System.out.println("Enter the price of the food:");
							double foodPrice = input.nextDouble();
							food.add(new Food(foodName, foodDetail, foodPrice));
						}catch (InputMismatchException e) {
							System.out.println("Error Input !!!");
						}
						
						break;
					case 2:
						//Add new Drink
						try {
							Scanner input = new Scanner(System.in);
							System.out.println("Enter the name of the drink:");
							String drinkName = input.nextLine();
							System.out.println("Enter the calories of the drink:");
							double drinkDetail = input.nextDouble();
							System.out.println("Enter the price of the drink:");
							double drinkPrice = input.nextDouble();
							
							drink.add(new Drink(drinkName, drinkDetail, drinkPrice));
						}catch (InputMismatchException e) {
							System.out.println("Error Input !!!");
						}
						
						break;
					case 3:
						//Remove food from the menu
						System.out.println("--------------------FOOD LIST--------------------");
						for(int i = 0; i<food.size();i++) {
							//Display all food in the menu
							System.out.println(i+1 + ") " + food.get(i).toString());
						}
						System.out.println("-------------------------------------------------");
						
						try {
							Scanner input = new Scanner(System.in);
							System.out.println("Enter the number of food you want to remove.");
							int optionRemove = input.nextInt();
							//Remove the selected food
							if(optionRemove<0 || optionRemove>food.size()) {
								System.out.println("The choice was no found.");
							}else if(optionRemove>0 || optionRemove<food.size()){
								food.remove(optionRemove-1);
							}
						}catch (InputMismatchException e) {
							System.out.println("!!! Please enter a number !!!");
						}
						
						break;
					case 4:
						//Remove Drink from the menu
						System.out.println("--------------------Drink LIST--------------------");
						for(int i = 0; i<drink.size();i++) {
							//Display all drink from the menu
							System.out.println(i+1 + ") " + drink.get(i).toString());
						}
						System.out.println("-------------------------------------------------");
						
						try {
							Scanner input = new Scanner(System.in);
							System.out.println("Enter the number of drink you want to remove.");
							int optionRemove = input.nextInt();
							//Remove the selected drink
							if(optionRemove<0 || optionRemove>drink.size()) {
								System.out.println("The choice was no found.");
							}else if(optionRemove>0 || optionRemove<drink.size()){
								drink.remove(optionRemove-1);
							}
						}catch (InputMismatchException e) {
							System.out.println("!!! Please enter a number !!!");
						}
						
						break;
					case 5:
						//Exit from product mode
						break;
					default: 
						System.out.println("Please enter the number from 1-3.");
						break;
					}
					
					//Write the menu into product.txt
					FileWriter foodFile = new FileWriter("product.txt");
					int size = food.size()<drink.size() ? drink.size():food.size() ;
					for(int i = 0 ; i < size ; i++) {
						//Write ! for empty food or drink
						foodFile.write( (food.size()<=i ? "!":food.get(i).toFile()) + "#" + (drink.size()<= i ? "!":drink.get(i).toFile()) + "\n");
					}
					foodFile.close();
					
				}while(optionProduct != 5);//Repeat until the user input 5
				break;
				
			case 3:
				//User setting
				int optionUser =0;
				do {
					do {
						//Display menu
						System.out.println("------User Setting mode-------");
						System.out.println("|1)Add New User              |");
						System.out.println("|2)Remove User               |");
						System.out.println("|3)Change password           |");
						System.out.println("|4)Exit                      |");
						System.out.println("------------------------------");
						
						try {
							Scanner input = new Scanner(System.in);
							System.out.println("Enter the number of menu you want:");
							optionUser = input.nextInt();
							valid = true;
						}catch (InputMismatchException e) {
							System.out.println("!!! Please enter a number !!!");
							valid = false;
						}
					}while(!valid);
					
					switch(optionUser) {
					case 1:
						//Add new user
						//check validity
						try {
							Scanner input = new Scanner(System.in);
							System.out.println("Enter the user's name:");
							String userName = input.nextLine();
							System.out.println("Enter the user's No.matrics:");
							String userNoMatrics = input.nextLine();
							System.out.println("Enter the user's password:");
							String userPassword = input.nextLine();
							
							user.add(new User(userName, userNoMatrics, userPassword));
						}catch (InputMismatchException e) {
							System.out.println("Error Input !!!");
						}
						break;
					case 2:
						//Remove User
						System.out.println("--------------------User Detail--------------------");
						for(int i = 0; i<user.size();i++) {
							//Display all user
							System.out.println("\n" + (i+1) + ")User Name: " + user.get(i).getName() + "\n No.Matircs: " + user.get(i).getNoMatrics() );
						}
						System.out.println("---------------------------------------------------");
						
						do {
							try {
								Scanner input = new Scanner(System.in);
								System.out.println("Enter the user you want to remove:");
								int optionBlacklist = input.nextInt();
								if(optionBlacklist > 0 && optionBlacklist <= user.size()) {
									do {
										try {
											//Double confirm
											System.out.println("Are you sure to remove the user.(Y/N)");
											Scanner input1 = new Scanner(System.in);
											String comfirm = input1.nextLine();
											if(comfirm.equalsIgnoreCase("y")) {
												//Remove the selected user
												user.remove(optionBlacklist-1);
												System.out.println("The user has been removed");
												valid = true;
											}else if(comfirm.equalsIgnoreCase("n")) {
												//Return to previous menu
											}else {
												System.out.print("Please enter y or n.");
												valid = false;
											}
										}catch (InputMismatchException e) {
											System.out.println("!!! Please enter a y or n !!!");
											valid = false;
										}
									}while(!valid);	
								}else {
									//If user selected others from the user List
									System.out.println("The number your enter is not available !!!");
								}
							}catch (InputMismatchException e) {
								System.out.println("!!! Please enter a number !!!");
								valid = false;
							}
						}while(!valid);//Repeat when not valid

						
						break;
						
					case 3:
						//Change password
						System.out.println("--------------------User Detail--------------------");
						for(int i = 0; i<user.size();i++) {
							//Display all user
							System.out.println("\n" + (i+1) + ")User Name: " + user.get(i).getName() + "\n No.Matircs: " + user.get(i).getNoMatrics() + "\n Password: " + user.get(i).getPassword());
						}
						System.out.println("---------------------------------------------------");
						
						do {
							try {
								//Change password
								Scanner input = new Scanner(System.in);
								System.out.println("Enter the user you want to change password:");
								int optionPassword = input.nextInt();
								if(optionPassword > 0 && optionPassword <= user.size()) {
									do {
										try {
											//change password for selected user
											System.out.println("Are you sure to change the password.(Y/N)");
											Scanner input1 = new Scanner(System.in);
											String comfirm = input1.nextLine();
											if(comfirm.equalsIgnoreCase("y")) {
												//Change new password
												String password = "",password1 = "";
												do {
													try {
														password = "";
														System.out.println("Enter new password:");
														password = input1.nextLine();
														System.out.println("Enter comfirm password:");
														password1 = input1.nextLine();
														//if password and confirm password are same and not equal to the old password
														if(password.equals(password1) && !(password.equals(user.get(optionPassword-1).getPassword()))) {
															user.get(optionPassword-1).setPassword(password1);
															System.out.println("The password had been changed");
															password1 = "";
														}
														//if password and confirm password are same but equal to the old password
														else if(password.equals(password1) && password.equals(user.get(optionPassword-1).getPassword())) {
															System.out.println("The new password should not be same with the old password!!!");
														}else {//if the password and confirm password are not same
															System.out.println("The comfirm password should be same.");
														}
													}catch(InputMismatchException e) {
														System.out.println("!!! Please enter valid password !!!");
													}
												}while(password1 != "");
												
												valid = true;
											}else if(comfirm.equalsIgnoreCase("n")) {
												valid = true;
											}else {
												System.out.print("Please enter y or n.");
												valid = false;
											}
										}catch (InputMismatchException e) {
											System.out.println("!!! Please enter a y or n !!!");
											valid = false;
										}
									}while(!valid);	
								}else {//if user select other than the userList
									System.out.println("The number your enter is not available !!!");
								}
							}catch (InputMismatchException e) {
								System.out.println("!!! Please enter a number !!!");
								valid = false;
							}
						}while(!valid);
						
						break;
						
					case 4:
						break;
						//return to previous page
					default: 
						//if user didn't input 1,2,3,4
						System.out.println("Please enter the number from 1-4.");
						break;
					}
						
					//Write user details into user.txt
					FileWriter userFile = new FileWriter("user.txt");
					for(int i = 0 ; i < user.size() ; i++) {
						userFile.write(user.get(i).toFile() + "\n");
					}
					userFile.close();
				
				}while(optionUser != 4);
				
				break;
				
			case 4:
				System.out.println("-------End Program-------");
				break;
				//End program
			
			default:
				System.out.println("Please enter the number from 1-4.");
				break;
			}
		}while(option != 4);//Repeat until user input 4
	}
	
	//Append a string in an existing file
	public static void appendStrToFile(String fileName, String str) {

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
			out.write(str);
			out.close();
		}catch (IOException e) {
			System.out.println("exception occurred" + e);
		}
	}
}
