package beans;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;


public class User {
	
	 @JsonProperty("id")
	    private String id;
	    
	    @JsonProperty("username")
	    private String username;
	    
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
	
	

	public User(String id, String username, String name, String lastName, String gender, LocalDate birthDate,
			String userType) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.userType = userType;
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
