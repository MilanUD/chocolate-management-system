package beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

	private String id;
	@JsonProperty("longitude")
	private double longitude;
	@JsonProperty("latitude")
	private double latitude;
	@JsonProperty("address")
	private Address address;
	public Location() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Location(String id, double longitude, double latitude, Address address) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
	

}
