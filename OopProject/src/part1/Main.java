package part1;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Main {
		
	public static void main(String[] args) throws InterruptedException, IOException {
		//Declaration
		List<User>user = new ArrayList<>();
		List<Food>food = new ArrayList<>();
		List<Drink>drink = new ArrayList<>();
		List<OrderList>orderList = new ArrayList<>();
		
		//Read product from the file product.txt
		Scanner readFile = new Scanner(new File("product.txt"));
		int fileloop = 0;
		while (readFile.hasNextLine()) {//when file is not empty
			String a = readFile.nextLine();
			String[] b = a.split("#",2);
			String[] c = b[0].split("/", 3);
			String[] d = b[1].split("/",3);
			if(c[0].equalsIgnoreCase("!")) {
				//Food is empty. Therefore, do nothing
			}else {
				//Read and input food into arrayList
				food.add(new Food ("",0,0));
				food.get(fileloop).setProductName(c[0]);
				food.get(fileloop).setDetail(Double.valueOf(c[1]));
				food.get(fileloop).setPrice(Double.valueOf(c[2]));
			}
			if(d[0].equalsIgnoreCase("!")) {
				//Drink is empty. Therefore, do nothing
			}else {
				//Read and input drink into arrayList
				drink.add(new Drink("",0,0));
				drink.get(fileloop).setProductName(d[0]);
				drink.get(fileloop).setDetail(Double.valueOf(d[1]));
				drink.get(fileloop).setPrice(Double.valueOf(d[2]));
			}
			fileloop++;
		}
		
		//Read user from file
		Scanner readUserFile = new Scanner(new File("user.txt"));
		fileloop = 0;
		while (readUserFile.hasNextLine()) {
			String a = readUserFile.nextLine();
			String[] b = a.split("/", 3);
			
			//Read and input user into arrayList
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
            }catch (Exception e) { //print error message if there is an error
            	e.printStackTrace();
            }
			//read password into user arrayList
			user.get(fileloop).setPassword(password);
			
			fileloop++;
		}
		
		//Read orderList from file
		Scanner readOrderListFile = new Scanner(new File("orderList.txt"));
		fileloop = 0;
		//when the orderList is not empty
		while (readOrderListFile.hasNextLine()) {

			orderList.add(new OrderList(null,null,null,null));
			String a = readOrderListFile.nextLine();
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
			List<Food> foodReadFile = new ArrayList<>();
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
		
		
		//Main of food order system
		Scanner scanner = new Scanner(System.in);
		String noMatrics,password = "";
		User userLogin;
		
		Boolean valid = true;
		do {
				userLogin = null;
			//Login
			do{
				System.out.println("------Login page------");
				
				//Check validity
				do {
					noMatrics = "";
					try {
						Scanner input = new Scanner(System.in);
						System.out.println("Enter your No.Matrics:");
						noMatrics = input.nextLine();
					}catch (InputMismatchException e) {
						System.out.println("!!! Please enter a nombor matircs !!!");
					}
				}while(noMatrics == "");
				
				int i;
				password = "";
				
				for(i=0; i<user.size(); i++){
					//if noMatric input is same as the user.txt
					if(user.get(i).getNoMatrics().equalsIgnoreCase(noMatrics)){
						
						//Check validity
						do {
							try {
								password = "";
								System.out.println("Enter your password:");
								password = scanner.nextLine();
							}catch(InputMismatchException e) {
								System.out.println("!!! Please enter valid password !!!");
							}
						//Login into the system if the password is same as user.txt
						if(user.get(i).getPassword().equalsIgnoreCase(password)){
							userLogin = user.get(i);
							System.out.println("Succesful login...");
							break;
						}else{
							System.out.println("!!! Password Incorrect !!!");
							Thread.sleep(1000); //stop the program for 1 second
							for (int j = 0; j < 50; ++j) System.out.println(); //print blank lines to make the output look simple
						}
						}while(!(user.get(i).getPassword().equalsIgnoreCase(password)));//if password input is not same as user.txt
					}
				}
				if(password == "") {
					System.out.println("The user is not found");
					Thread.sleep(1000); //stop the program for 1 second
					for (int j = 0; j < 50; ++j) System.out.println(); //print blank lines
				}
			}while(userLogin == null);//repeat when there is no user login into system
			
			System.out.println("Welcome to Food Ordering System for Tun Fatimah Residential College, " + userLogin.getName().toUpperCase() + "...");
			Thread.sleep(1500);//stop the program for 1.5 second
			for (int i = 0; i < 50; ++i) System.out.println(); //print blank lines
			//Menu
			int option = 0;
			do {
				
				do {
					//print menu
					System.out.println("------------Food Ordering System-------------");
					System.out.println("|1)Order Food                               |");
					System.out.println("|2)Order Drink                              |");
					System.out.println("|3)View Order                               |");
					System.out.println("|4)Cancel Order                             |");
					System.out.println("|5)Exit                                     |");
					System.out.println("---------------------------------------------");
					
					option = 0;
					//Check validity
					try {
						Scanner input = new Scanner(System.in);
						System.out.println("Enter the number of menu you want:");
						option = input.nextInt();
						valid = true;
					}catch (InputMismatchException e) {
						System.out.println("!!! Please enter a number !!!");
						valid = false;
					}
				}while(!valid);
				
				switch(option) {
				
				
				case 1: 
					//order food
					int num = 0;
					do {
						Scanner input = new Scanner(System.in);
						System.out.println("--------------------FOOD LIST--------------------");
						for(int i = 0; i<food.size();i++) {
							System.out.println(i+1 + ") " + food.get(i).toString()); //list of food available
						}
						System.out.println("-------------------------------------------------");
						
							num = 0;
						try {
							System.out.println("Enter the type of food you want.");
							num = input.nextInt();
						}catch (InputMismatchException e) {//if user input other than integer
							System.out.println("!!! Please enter a number !!!");
							Thread.sleep(1000); //stop program for 1 second
							for (int j = 0; j < 50; ++j) System.out.println(); //print blank lines
						}
						if(num<0 || num>food.size()+1) { //if user input other than the food list
							System.out.println("The choice was no found.");
							Thread.sleep(1000); //stop program 1 second
						}
					}while(num<=0 || num>food.size()+1); //repeat if user input other than food list
					
					Food foodOrder = new Food(food.get(num-1).getProductName(),food.get(num-1).getDetail(),food.get(num-1).getPrice());
					
					//Add Sauce
					int sauce = 3;
					do {
						// print sauce menu
						Scanner input = new Scanner(System.in);
						System.out.println("--------------------Sauce--------------------");
						System.out.println("1) Chili ");
						System.out.println("2) Tomato ");
						System.out.println("3) No Sauce ");
						do {
							System.out.println("Enter the sauce that your want to add:");
							try {
								Scanner input2 = new Scanner(System.in);
								sauce = input2.nextInt();
								valid = true;
							}catch (InputMismatchException e) { //if user input other than integer
								System.out.println("Please enter a number of the sauce that you want !!!");
								valid = false;
							}
							if(sauce <1 || sauce >3) { //if user input other than 1,2,3
								System.out.println("Your should input number from 1-3");
							}
						}while(sauce<1 || sauce >3);//repeat when user input other than 1,2,3
					}while (!valid);
					foodOrder.setSauce(sauce);
					
					//Add quantity
					do {
						System.out.println("Enter the quantity of food you want");
						try {
							Scanner input2 = new Scanner(System.in);
							int numberOfFood = input2.nextInt();
							foodOrder.setQuantityInOrder(numberOfFood);
							valid = true;
						}catch (Exception e) { //if user input other than integer
							valid = false;
						}
					}while(!valid); // repeat when valid = false
					//Set date
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
				    Date date = new Date(); 
				    String strdate = formatter.format(date); 
				    
				    //Input ordered food in to orderList
					if(orderList.size() == 0) { //if orderList is empty
						orderList.add(new OrderList(userLogin, foodOrder, strdate));
					}else { // if orderList is not empty
						orderList.get(0).setOrderFood(orderList, userLogin, foodOrder);
					}
					break;
					
					
				case 2:
					
					//order drink
					do {
						Scanner input = new Scanner(System.in);
						System.out.println("--------------------Drink LIST--------------------");
						for(int i = 0; i<drink.size();i++) {
							System.out.println(i+1 + ") " + drink.get(i).toString()); // list of drink available
						}
						System.out.println("-------------------------------------------------");
						
							num = 0;
						try {
							System.out.println("Enter the type of drink you want");
							num = input.nextInt();
						}catch (InputMismatchException e) { // if user input other than integer
							System.out.println("!!! Please enter a number !!!");
							Thread.sleep(1000);// stop program 1 second
							for (int j = 0; j < 50; ++j) System.out.println();//print blank lines
						}
						if(num<0 || num>drink.size()+1) { //if user input other than the drink list
							System.out.println("The choice was no found.");
							Thread.sleep(1000);//stop program 1s
						}
					}while(num<=0 || num>drink.size()+1); //repeat when user input other than drink list
					Drink drinkOrder = new Drink(drink.get(num-1).getProductName(),drink.get(num-1).getDetail(),drink.get(num-1).getPrice());
					
					//Set size for drink
					String size = "";
					do {
						valid = true;
						Scanner input = new Scanner(System.in);
						System.out.println("Did you want to go-Large the drink? (Y/N)");
						size = input.nextLine();
						if(size.equalsIgnoreCase("y")) {
							drinkOrder.goLarge(); //the size of drink become large and charge extra RM1.50
							drinkOrder.setDetail(drinkOrder.getDetail()*2);//calories*2
						}else if(size.equalsIgnoreCase("n")) {
							
						}else {//user input other than y or n
							System.out.println("Please enter y or n.");
							valid = false;
						}
					}while(!valid);//repeat when no valid
					
					//Set status for drink
					String hot = "";
					do {
						valid = true;
						Scanner input = new Scanner(System.in);
						System.out.println("Did you want to order a cold drink? (Y/N)");
						hot = input.nextLine();
						if(hot.equalsIgnoreCase("y")) {
							drinkOrder.addIce();//the drink become cold drink and charge extra RM0.50
						}else if(hot.equalsIgnoreCase("n")) {
							
						}else {//user input other than y or n
							System.out.println("Please enter y or n.");
							valid = false;
						}
					}while(!valid);//repeat when no valid
					
					System.out.println("Enter the quantity of drink you want");
					int numberOfdrink = scanner.nextInt();
					drinkOrder.setQuantityInOrder(numberOfdrink);
					
					formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
				    date = new Date(); 
				    strdate = formatter.format(date); 
					//if orderList is empty
					if(orderList.size() == 0) {
						orderList.add(new OrderList(userLogin, drinkOrder, strdate));
					}else {//orderList is not empty
						orderList.get(0).setOrderDrink(orderList, userLogin, drinkOrder);
					}
					break;
					
					
				case 3:
					//view order
					//if the user does not exist in orderList
					if(orderList.get(0).findUser(orderList, userLogin) == -1) {
						System.out.println("You have no order anything.");
					}else { //if user exist in the orderList
						double calories = 0, price = 0;
						int numArrayListUser = orderList.get(0).findUser(orderList, userLogin);
						if(orderList.get(numArrayListUser).getFood().size() != 0) {//if user's food orderList is not empty
							System.out.println("---------------Food---------------");
						}
						for(int i = 0 ; i < orderList.get(numArrayListUser).getFood().size() ; i++) {
							//Print food ArrayList
							System.out.println( i+1+ ")" + orderList.get(numArrayListUser).getFood().get(i) + " --- " + orderList.get(numArrayListUser).getFood().get(i).getQuantityInOrder() );
							//calculate total calories
							calories += orderList.get(numArrayListUser).getFood().get(i).getDetail() * orderList.get(numArrayListUser).getFood().get(i).getQuantityInOrder();
							//calculate total price
							price += orderList.get(numArrayListUser).getFood().get(i).getPrice() * orderList.get(numArrayListUser).getFood().get(i).getQuantityInOrder();
						}
						if(orderList.get(numArrayListUser).getDrink().size() != 0) {//if user's drink orderList is not empty
							System.out.println("---------------Drink--------------");
						}
						for(int i = 0; i<orderList.get(numArrayListUser).getDrink().size();i++) {
							//Print Drink ArrayList
							System.out.println( i+1+ ")" + orderList.get(numArrayListUser).getDrink().get(i) + " --- " + orderList.get(numArrayListUser).getDrink().get(i).getQuantityInOrder());
							//calculate total calories
							calories += orderList.get(numArrayListUser).getDrink().get(i).getDetail() * orderList.get(numArrayListUser).getDrink().get(i).getQuantityInOrder();
							//calculate total price
							price += orderList.get(numArrayListUser).getDrink().get(i).getPrice() * orderList.get(numArrayListUser).getDrink().get(i).getQuantityInOrder();
						}
						System.out.println("------------------------------");
						System.out.println("Total calories: " + calories + "kcal");
						System.out.println("Total price: RM" + String.format("%.2f", price));
						System.out.println("\nEnter any key to continue...");
						scanner.next();
					}
					break;
					
					
				case 4:
					//Cancel order
					////if the user does not exist in orderList
					if(orderList.get(0).findUser(orderList, userLogin) == -1) {
						System.out.println("You have no order anything.");
					}else {
						int cancel = 0;
						do {
							//print cancel menu
							System.out.println("---------------Cancel order---------------");
							System.out.println("1) Cancel all order");
							System.out.println("2) Cancel a order");
							System.out.println("3) Exit");
							System.out.println("------------------------------------------");
							do {
								try {
									Scanner input = new Scanner(System.in);
									System.out.println("Enter the number of option you want:");
									cancel = input.nextInt();
									valid = true;
								}catch (InputMismatchException e) {//if user didn't input integer
									System.out.println("!!! Please enter a number !!!");
									valid = false;
								}
							}while(!valid);//repeat when user didn't input integer
							
							if(cancel == 1) {
								String comfirm = "";
								do {
									try {
									System.out.println("Are you sure to remove all order.(Y/N)");
									Scanner input = new Scanner(System.in);
									comfirm = input.nextLine();
									if(comfirm.equalsIgnoreCase("y")) {
										//remove all orderList of the user
										orderList.remove(orderList.get(0).findUser(orderList, userLogin));
										valid = true;
									}else if(comfirm.equalsIgnoreCase("n")) {
										valid = true;
									}else {//if user input other than y or n
										System.out.println("Please enter y or n.");
										valid = false;
									}
									}catch (InputMismatchException e) {
										System.out.println("!!! Please enter a y or n !!!");
										valid = false;
									}
								}while(!valid);//repeat if the input is not valid
								break;
								
							}else if(cancel == 2) {
							
								int numArrayListUser = orderList.get(0).findUser(orderList, userLogin);
								System.out.println("---------------OrderList---------------");
								for(int i = 0 ; i < orderList.get(numArrayListUser).getFood().size() ; i++) {
									//display food user ordered and its quantity
									System.out.println( i+1+ ")" + orderList.get(numArrayListUser).getFood().get(i) + " --- " + orderList.get(numArrayListUser).getFood().get(i).getQuantityInOrder());
								}
								for(int i = orderList.get(numArrayListUser).getFood().size(); i<orderList.get(numArrayListUser).getFood().size() + orderList.get(numArrayListUser).getDrink().size(); i++) {
									//display drink user ordered and its quantity
									System.out.println( i+1+ ")" + orderList.get(numArrayListUser).getDrink().get(i - orderList.get(numArrayListUser).getFood().size()) + " --- " + orderList.get(numArrayListUser).getDrink().get(i - orderList.get(numArrayListUser).getFood().size()).getQuantityInOrder() );
								}
								System.out.println("---------------------------------------");
								int cancelOrder = 0, numberOfItem = 0;
								do {
									try {
										Scanner input = new Scanner(System.in);
										System.out.println("Enter the list that you want to delete:");
										cancelOrder = input.nextInt();
										//if user input other than the cancel order list
										if(cancelOrder < 0 || cancelOrder > orderList.get(numArrayListUser).getFood().size() + orderList.get(numArrayListUser).getDrink().size() ) {
											System.out.println("The cancel list input not found !!!");
										}else {
											System.out.println("Enter the number of item that you want to delete:");
											numberOfItem = input.nextInt();
											valid = true;
										}
										break;
									}catch (InputMismatchException e) {//if user didn't input integer
										System.out.println("!!! Please enter a number !!!");
										valid = false;
									}
								}while(!valid);//repeat if the input is not valid
								//cancel order for food
								if(cancelOrder > 0 && cancelOrder<= orderList.get(numArrayListUser).getFood().size() && orderList.get(numArrayListUser).getFood().size() != 0) {
									int restFood = orderList.get(numArrayListUser).getFood().get(cancelOrder -1).getQuantityInOrder() - numberOfItem;
									//if the quantity of food = 0
									if(restFood == 0) {
										orderList.get(numArrayListUser).getFood().remove(cancelOrder -1);
										System.out.println("The food has been removed");
									}else if(restFood > 0 && numberOfItem > 0) { 
										//if the remaining food > 0
										orderList.get(numArrayListUser).getFood().get(cancelOrder -1).setQuantityInOrder(restFood);
										System.out.println("The food has been removed, still remaining " + restFood);
									}else if(restFood < 0) {
										//if the remaining food < 0
										System.out.println("The number of cancel order is bigger than the quantity in order.");
									}else if(numberOfItem < 0) {
										//if user enter a negative value
										System.out.println("You enter a negative number in cancel order !!!");
									}
								}else if(cancelOrder > 0 && cancelOrder <= orderList.get(numArrayListUser).getFood().size() + orderList.get(numArrayListUser).getDrink().size()) {
								//cancel order for drink
									int drinkArray = cancelOrder - orderList.get(numArrayListUser).getFood().size() -1;
									int restDrink = orderList.get(numArrayListUser).getDrink().get(drinkArray).getQuantityInOrder() - numberOfItem;
									//if the quantity of drink = 0
									if(restDrink == 0) {
										orderList.get(numArrayListUser).getDrink().remove(drinkArray);
										System.out.println("The drink has been removed");
									}else if(restDrink > 0 && numberOfItem > 0) {
										//if the remaining food > 0
										orderList.get(numArrayListUser).getDrink().get(drinkArray).setQuantityInOrder(restDrink);
										System.out.println("The drink has been removed, still remaining " + restDrink);
									}else if(restDrink < 0) {
										//if the remaining food < 0
										System.out.println("The number of cancel order is bigger than the quantity in order.");
									}else if(numberOfItem < 0) {
										//if user enter a negative value
										System.out.println("You enter a negative number in cancel order !!!");
									}
								}
								// if users' food and drink = 0, remove the orderList
								if(orderList.get(numArrayListUser).getFood().size() == 0 && orderList.get(numArrayListUser).getDrink().size() == 0 ){
									orderList.remove(orderList.get(0).findUser(orderList, userLogin));
								}
								break;
								
							}else if(cancel == 3){
							}else {
								System.out.println("Enter your should input number from 1-3");
							}
						}while(cancel != 3);//repeat until the user input 3
					}
					
					break;
					
					
				case 5:
					System.out.println("--------------------End-------------------------");
					
					//Write orderList into orderList.txt
					File file = null;
					try {
						file = new File("orderList.txt");
						file.createNewFile();
					}catch (IOException e) {
					      System.out.println("An error occurred.");
					      e.printStackTrace();
					}
					
					FileWriter orderFile = new FileWriter("orderList.txt");
					for(int i = 0 ; i < orderList.size() ; i++) {
						orderFile.write(orderList.get(i).toFile() + "\n");
					}
					orderFile.close();
					Thread.sleep(2000);
					
					break;
					
					
				default: 
					System.out.println("Your should input number from 1-7.");
				}
				
			}while(option != 5);//repeat until the user input 5
			
		}while(true);//Let the program execute infinitely until we terminate the program
		
	}
	
}
