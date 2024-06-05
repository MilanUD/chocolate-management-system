package beans;


public class Chocolate {

	private String id;
	private String name;
	private double price;
	private String type;
	private String flavor;
	private double grams;
	private String description;
	private String picture;
	private int stockQuantity;
	private boolean isInStock;
	private String fabricId;
	public String getFabricId() {
		return fabricId;
	}


	public void setFabricId(String fabricId) {
		this.fabricId = fabricId;
	}


	private ChocolateFactory factory;
	
	
	public Chocolate() {
		// TODO Auto-generated constructor stub
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getFlavor() {
		return flavor;
	}


	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}


	public double getGrams() {
		return grams;
	}


	public void setGrams(double grams) {
		this.grams = grams;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public int getStockQuantity() {
		return stockQuantity;
	}


	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}


	public boolean getIsInStock() {
		return isInStock;
	}


	public void setIsInStock(boolean isInStock) {
		this.isInStock = isInStock;
	}


	public ChocolateFactory getFactory() {
		return factory;
	}


	public void setFactory(ChocolateFactory factory) {
		this.factory = factory;
	}
	
	
	

}
