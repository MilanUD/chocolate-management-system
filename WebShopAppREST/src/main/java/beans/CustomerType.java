package beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerType {
	
	@JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("discount")
    private double discount;

    @JsonProperty("pointsUntilNextRank")
    private double pointsUntilNextRank;

    @JsonProperty("userId")
    private String userId;
    
    @JsonProperty("points")
    private double points;
	
    public CustomerType(String userId) {
		super();
		this.type = "Bronze";
		this.discount = 0;
		this.pointsUntilNextRank = 100;
		this.userId = userId;
		this.points = 0;
	}
    
    

	public CustomerType(String id, String type, double discount, double pointsUntilNextRank, String userId,
			double points) {
		super();
		this.id = id;
		this.type = type;
		this.discount = discount;
		this.pointsUntilNextRank = pointsUntilNextRank;
		this.userId = userId;
		this.points = points;
	}



	public CustomerType(String id, String type, double discount, double pointsUntilNextRank, String userId) {
		super();
		this.id = id;
		this.type = type;
		this.discount = discount;
		this.pointsUntilNextRank = pointsUntilNextRank;
		this.userId = userId;
	}


	

	public double getPoints() {
		return points;
	}


	public void setPoints(double points) {
		this.points = points;
	}


	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public double getDiscount() {
		return discount;
	}



	public void setDiscount(double discount) {
		this.discount = discount;
	}



	public double getPointsUntilNextRank() {
		return pointsUntilNextRank;
	}



	public void setPointsUntilNextRank(double pointsUntilNextRank) {
		this.pointsUntilNextRank = pointsUntilNextRank;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public CustomerType() {
		// TODO Auto-generated constructor stub
	}

}
