package part1;

public class Food extends Product{
	//Declaration
	final static int spicy = 1;
	final static int tomato = 2;
	final static int  normal = 3;
	private int sauce;
	
	//Constructor of Food
	Food(String foodName, double detail, double price){
		super(foodName, detail, price);
		this.sauce = normal;
	}
	
	//Constructor of Food after ordered
	Food(String foodName, double detail, double price, int quantity , int sauce){
		super(foodName, detail, price);
		this.setQuantityInOrder(quantity);
		this.sauce = sauce;
	}
	
	//Setters
	public void setSauce(int sauce) {
		this.sauce = sauce;
	}
	
	//Getters
	public String getSauce() {
		if(this.sauce == spicy) {
			return "spicy";
		}else if(this.sauce == tomato) {
			return "tomato";
		}else if(this.sauce == normal) {
			return "normal";
		}else {
			return "";
		}
	}
	
	//Return String of Foodname, sauce, calories and price
	public String toString() {
		return getProductName() + " (" + getSauce() + ")" + " (" + getDetail() + " kcal)"+"-RM" + String.format("%.2f",getPrice());
	}
	
}	
