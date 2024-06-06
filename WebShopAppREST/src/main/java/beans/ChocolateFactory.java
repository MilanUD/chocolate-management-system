package beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChocolateFactory {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	private List<Chocolate> chocolates;
		
	@JsonProperty("businessHours")
	private String businessHours;
	
	@JsonProperty("isOpen")
	private boolean isOpen;
	
	@JsonProperty("location")
	private Location location;
	
	@JsonProperty("picture")
	private String picture;
	
	@JsonProperty("rating")
	private double rating;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public List<Chocolate> getChocolates() {
		return chocolates;
	}


	public void setChocolates(List<Chocolate> chocolates) {
		this.chocolates = chocolates;
	}


	public String getBusinessHours() {
		return businessHours;
	}


	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}


	public boolean getIsOpen() {
		return isOpen;
	}


	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public ChocolateFactory() {
		// TODO Auto-generated constructor stub
		chocolates = new ArrayList<Chocolate>();
	}

}
