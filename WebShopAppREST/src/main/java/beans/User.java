package beans;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class User {
	
	 	@JsonProperty("id")
	    private String id;
	    
	    @JsonProperty("username")
	    private String username;
	    
	    @JsonProperty("password")
	    private String password;
	    
	    @JsonProperty("name")
	    private String name;
	    
	    @JsonProperty("lastName")
	    private String lastName;
	    
	    @JsonProperty("gender")
	    private String gender;
	    
	    @JsonProperty("birthDate")
	    private LocalDate birthDate;
	    
	    @JsonProperty("userType")
	    private String userType;
	    
	    @JsonProperty("factoryId")
	    private String factoryId;
	    
	    @JsonIgnore
	    private String cart;
	    
	    @JsonIgnore
	    private CustomerType customerType;

	    @JsonProperty("isSuspicious")
	    private boolean isSuspicious;
	    
	    @JsonProperty("isBlocked")
	    private boolean isBlocked;
	    
	    

	public User(String id, String username, String password, String name, String lastName, String gender,
				LocalDate birthDate, String userType, String factoryId, String cart, CustomerType customerType,
				boolean isSuspicious, boolean isBlocked) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
			this.name = name;
			this.lastName = lastName;
			this.gender = gender;
			this.birthDate = birthDate;
			this.userType = userType;
			this.factoryId = factoryId;
			this.cart = cart;
			this.customerType = customerType;
			this.isSuspicious = isSuspicious;
			this.isBlocked = isBlocked;
		}




	public User(String id, String username, String password, String name, String lastName, String gender,
				LocalDate birthDate, String userType, String factoryId, String cart, CustomerType customerType) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
			this.name = name;
			this.lastName = lastName;
			this.gender = gender;
			this.birthDate = birthDate;
			this.userType = userType;
			this.factoryId = factoryId;
			this.cart = cart;
			this.customerType = customerType;
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




	public void setIsBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}



	public CustomerType getCustomerType() {
			return customerType;
		}


		public void setCustomerType(CustomerType customerType) {
			this.customerType = customerType;
		}


	public User(String id, String username, String password, String name, String lastName, String gender,
				LocalDate birthDate, String userType, String factoryId, String cart) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
			this.name = name;
			this.lastName = lastName;
			this.gender = gender;
			this.birthDate = birthDate;
			this.userType = userType;
			this.factoryId = factoryId;
			this.cart = cart;
		}


	public User(String id, String username, String password, String name, String lastName, String gender,
				LocalDate birthDate, String userType, String cart) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
			this.name = name;
			this.lastName = lastName;
			this.gender = gender;
			this.birthDate = birthDate;
			this.userType = userType;
			this.cart = cart;
		}


	public User(String id, String username, String password, String name, String lastName, String gender,
				LocalDate birthDate, String userType) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
			this.name = name;
			this.lastName = lastName;
			this.gender = gender;
			this.birthDate = birthDate;
			this.userType = userType;
		}
	
	
	

	public String getFactoryId() {
		return factoryId;
	}


	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}


	public String getCart() {
		return cart;
	}

	public void setCart(String cart) {
		this.cart = cart;
	}





	public String getPassword() {
			return password;
		}



		public void setPassword(String password) {
			this.password = password;
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



	public User() {
		// TODO Auto-generated constructor stub
	}

}
