package dtos;

public class ChocolateDTO {

	private String id;
	private int stockQuantity;
	
	
	
	public ChocolateDTO(String id, int stockQuantity) {
		super();
		this.id = id;
		this.stockQuantity = stockQuantity;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public int getStockQuantity() {
		return stockQuantity;
	}



	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}



	public ChocolateDTO() {
		// TODO Auto-generated constructor stub
	}

}
