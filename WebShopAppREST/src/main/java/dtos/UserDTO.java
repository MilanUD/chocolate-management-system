package dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import beans.CustomerType;
import beans.User;

public class UserDTO {
	
    private String id;
    
    private String username;
    
    private String password;
    
    private String name;
    
    private String lastName;
    
    private String gender;
    
    private LocalDate birthDate;
    
    private String userType;
    
    private String factoryId;
    
    private CustomerType type;
    
    private boolean isSuspicious;
    
    private boolean isBlocked;
    
    
    
    
    public UserDTO(User user) {
        super();
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.gender = user.getGender();
        this.birthDate = user.getBirthDate();
        this.userType = user.getUserType();
        this.factoryId = user.getFactoryId();
        this.type = user.getCustomerType();
        this.cart = user.getCart();
        this.isSuspicious = user.getIsSuspicious();
        this.isBlocked = user.getIsBlocked();
    }

    

	public boolean getIsSuspicious() {
		return isSuspicious;
	}



	public void setIsSuspicious(boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}



	public boolean getIsBlocked() {
		return isBlocked;
	}



	public void setSetBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}



	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public LocalDate getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public String getFactoryId() {
		return factoryId;
	}


	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}


	public CustomerType getType() {
		return type;
	}


	public void setType(CustomerType type) {
		this.type = type;
	}


	public String getCart() {
		return cart;
	}


	public void setCart(String cart) {
		this.cart = cart;
	}


	@JsonIgnore
    private String cart;
    

	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

}
