package beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cart {
	
	@JsonProperty("chocolatesInCart")
	private List<Chocolate> chocolatesInCart;

	@JsonProperty("user")
	private User user;
	
	@JsonProperty("price")
	private double price;


	public Cart(List<Chocolate> chocolatesInCart, User user, double price) {
		super();
		this.chocolatesInCart = chocolatesInCart;
		this.user = user;
		this.price = price;
	}
	

	public List<Chocolate> getChocolatesInCart() {
		return chocolatesInCart;
	}

	public void setChocolatesInCart(List<Chocolate> chocolatesInCart) {
		this.chocolatesInCart = chocolatesInCart;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Cart() {
		// TODO Auto-generated constructor stub
	}

}
