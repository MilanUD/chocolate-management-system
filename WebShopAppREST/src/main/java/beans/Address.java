package beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

	@JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("postalCode")
    private String postalCode;
    
    public Address() {
    	
    }

	    public Address(String street, String city, String postalCode) {
	        this.street = street;
	        this.city = city;
	        this.postalCode = postalCode;
	    }

	    // Getters and setters
	    public String getStreet() {
	        return street;
	    }

	    public void setStreet(String street) {
	        this.street = street;
	    }

	    public String getCity() {
	        return city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }

	    public String getPostalCode() {
	        return postalCode;
	    }

	    public void setPostalCode(String postalCode) {
	        this.postalCode = postalCode;
	    }

	    @Override
	    public String toString() {
	        return street + ", " + city + ", " + postalCode;
	    }

}
