package beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comments {
	
	public enum CommentStatus {Accepted, Declined, Pending}
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("userId")
	private String userId;
	
	private User user;
	
	@JsonProperty("factoryId")
	private String factoryId;
	
	@JsonProperty("commentText")
	private String commentText;
	
	@JsonProperty("rating")
	private Integer rating;
	
	@JsonProperty("status")
	private CommentStatus status;
	

	public Comments(String id, String userId, String factoryId, String commentText, Integer rating,
			CommentStatus status) {
		super();
		this.id = id;
		this.userId = userId;
		this.factoryId = factoryId;
		this.commentText = commentText;
		this.rating = rating;
		this.status = status;
	}
	
	
	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}




	public Comments(String id, String userId, String factoryId, String commentText, Integer rating) {
		super();
		this.id = id;
		this.userId = userId;
		this.factoryId = factoryId;
		this.commentText = commentText;
		this.rating = rating;
	}


	public CommentStatus getStatus() {
		return status;
	}





	public void setStatus(CommentStatus status) {
		this.status = status;
	}





	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getFactoryId() {
		return factoryId;
	}



	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}



	public String getCommentText() {
		return commentText;
	}



	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}



	public Integer getRating() {
		return rating;
	}



	public void setRating(Integer rating) {
		this.rating = rating;
	}



	public Comments() {
		// TODO Auto-generated constructor stub
	}

}
