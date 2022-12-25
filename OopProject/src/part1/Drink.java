package part1;

public class Drink extends Product{
	//Declaration
	private Boolean large = false;
	private Boolean hot = true;
	private final double ADD_ICE = 0.50;
	private final double GO_LARGE = 1.50;
	
	//Constructor of Drink
	Drink(String productName, double detail, double price){
		super(productName, detail, price);
	}
	
	//Constructor of Drink after ordered
	Drink(String productName, double detail, double price,int quantity, Boolean large, Boolean hot){
		super(productName, detail, price);
		setQuantityInOrder(quantity);
		this.large = large;
		this.hot = hot;
	}
	
	//Calculate price of Drink after goLarge
	public void goLarge() {
		if(this.large) {
			System.out.println("Your drink is already a large size .");
		}else {
			double x = getPrice()+ GO_LARGE;
			setPrice(x);
			this.large = true;
			System.out.println("Large size of "+ getProductName() +" have been successfully set up.");
		}
	}
	
	//Calculate price of Drink after addIce
	public void addIce() {
		if(this.hot) {
			double x = getPrice() + ADD_ICE;
			setPrice(x);
			this.hot = false;
			System.out.println("Cold drinks of "+ getProductName() +" have been successfully set up."   );
		}else {
			System.out.println("Your drink is already a cold drink.");
		}
	}
	
	
	//Return String of drinkname, boolean of large and hot, drink calories and price
	public String toString() {
		return getProductName() + " (" + (large == true?"Large":"Small") + ", " + (hot == true?"Hot":"Cold") + " )" + " (" + getDetail() + " kcal)"+"-RM" + String.format("%.2f",getPrice());
	}
	
	//Getters
	public Boolean getLarge() {
		return large;
	}
	
	public Boolean getHot() {
		return hot;
	}

	
}