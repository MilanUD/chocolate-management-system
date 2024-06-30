package beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Purchase {
	public enum Status {Accepted, Declined, Cancelled, InProgress}

	@JsonProperty("id")
    private String id;

    @JsonProperty("chocolateIds")
    private List<String> chocolateIds;
    
    @JsonIgnore
    private List<Chocolate> chocolates;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("factoryId")
    private String factoryId;
    
    @JsonIgnore
    private ChocolateFactory factory;

    @JsonProperty("price")
    private double price;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("status")
    private Status status;
    
    @JsonProperty("userId")
    private String userId;
    
    

	public Purchase(String id, List<String> chocolateIds, List<Chocolate> chocolates, LocalDateTime date,
			String factoryId, ChocolateFactory factory, double price, String customer, Status status) {
		super();
		this.id = id;
		this.chocolateIds = chocolateIds;
		this.chocolates = chocolates;
		this.date = date;
		this.factoryId = factoryId;
		this.factory = factory;
		this.price = price;
		this.customer = customer;
		this.status = status;
	}
	
	



	public Purchase(String id, List<String> chocolateIds, List<Chocolate> chocolates, LocalDateTime date,
			String factoryId, ChocolateFactory factory, double price, String customer, Status status, String userId) {
		super();
		this.id = id;
		this.chocolateIds = chocolateIds;
		this.chocolates = chocolates;
		this.date = date;
		this.factoryId = factoryId;
		this.factory = factory;
		this.price = price;
		this.customer = customer;
		this.status = status;
		this.userId = userId;
	}





	public String getUserId() {
		return userId;
	}





	public void setUserId(String userId) {
		this.userId = userId;
	}





	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public List<String> getChocolateIds() {
		return chocolateIds;
	}



	public void setChocolateIds(List<String> chocolateIds) {
		this.chocolateIds = chocolateIds;
	}



	public List<Chocolate> getChocolates() {
		return chocolates;
	}



	public void setChocolates(List<Chocolate> chocolates) {
		this.chocolates = chocolates;
	}



	public LocalDateTime getDate() {
		return date;
	}



	public void setDate(LocalDateTime date) {
		this.date = date;
	}



	public String getFactoryId() {
		return factoryId;
	}



	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}



	public ChocolateFactory getFactory() {
		return factory;
	}



	public void setFactory(ChocolateFactory factory) {
		this.factory = factory;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public String getCustomer() {
		return customer;
	}



	public void setCustomer(String customer) {
		this.customer = customer;
	}



	public Status getStatus() {
		return status;
	}



	public void setStatus(Status status) {
		this.status = status;
	}



	public Purchase() {
		// TODO Auto-generated constructor stub
	    chocolates = new ArrayList<>();
	}

}
