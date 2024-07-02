package dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import beans.Chocolate;
import beans.ChocolateFactory;
import beans.Location;

public class ChocolateFactoryDTO {
	
	private String id;
	
	private String name;
	
	private List<Chocolate> chocolates;
		
	private String businessHours;
	
	private boolean isOpen;
	
	private Location location;
	
	private String picture;
	
	private double rating;
	
	public ChocolateFactoryDTO(ChocolateFactory factory) {
        this.id = factory.getId();
        this.name = factory.getName();
        this.chocolates = factory.getChocolates();
        this.businessHours = factory.getBusinessHours();
        this.isOpen = factory.getIsOpen();
        this.location = factory.getLocation();
        this.picture = factory.getPicture();
        this.rating = factory.getRating();
    }

	public ChocolateFactoryDTO() {
		// TODO Auto-generated constructor stub
	}
	
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


}
