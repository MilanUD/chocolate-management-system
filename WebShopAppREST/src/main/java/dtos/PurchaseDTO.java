package dtos;

import beans.Chocolate;
import beans.Purchase.Status;

import java.util.ArrayList;
import java.util.List;

public class PurchaseDTO {

	private String id;
	private Status status;
	private String userId;
	private List<String> chocolateIds;
	private double price;
	private String declineReason;
	
	
	
	
	
	public PurchaseDTO(String id, Status status, String userId, List<String> chocolateIds, double price,
			String declineReason) {
		super();
		this.id = id;
		this.status = status;
		this.userId = userId;
		this.chocolateIds = chocolateIds;
		this.price = price;
		this.declineReason = declineReason;
	}





	public PurchaseDTO(String id, Status status, String userId, List<String> chocolateIds, double price) {
		super();
		this.id = id;
		this.status = status;
		this.userId = userId;
		this.chocolateIds = chocolateIds;
		this.price = price;
	}
	
	



	public String getDeclineReason() {
		return declineReason;
	}





	public void setDeclineReason(String declineReason) {
		this.declineReason = declineReason;
	}





	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public PurchaseDTO(String id, Status status, String userId, List<String> chocolateIds) {
		super();
		this.id = id;
		this.status = status;
		this.userId = userId;
		this.chocolateIds = chocolateIds;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Status getStatus() {
		return status;
	}



	public void setStatus(Status status) {
		this.status = status;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public List<String> getChocolateIds() {
		return chocolateIds;
	}



	public void setChocolateIds(List<String> chocolateIds) {
		this.chocolateIds = chocolateIds;
	}



	public PurchaseDTO() {
		// TODO Auto-generated constructor stub
		chocolateIds = new ArrayList<String>();
	}

}
