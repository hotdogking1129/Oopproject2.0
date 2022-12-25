package part1;

public class Product {
	//Declaration
	private String productName;
	private double detail;
	private double price;
	private int quantityInOrder;
	
	//Constructor of Product
	Product(String productName, double detail, double price){
		setProductName(productName);
		setDetail(detail);
		setPrice(price);
	}
	
	//Setters
	public void setProductName(String productName){
		this.productName = productName;
	}
	
	public void setDetail(double detail){
		this.detail = detail;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public void setQuantityInOrder(int quantityInOrder){
		this.quantityInOrder = quantityInOrder;
	}
	
	//Getters
	public String getProductName() {
		return productName;
	}
	
	public double getDetail() {
		return detail;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getQuantityInOrder(){
		return quantityInOrder;
	}
	
	//Return String of productname, calories and price
	public String toFile() {
		return getProductName() + "/" + getDetail() + "/" + getPrice() ;
	}
	
}