package services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Cart;
import beans.Chocolate;
import beans.Purchase;
import beans.Purchase.Status;
import dao.ChocolateDAO;
import dao.ChocolateFactoryDAO;
import dao.CustomerTypeDAO;
import dao.PurchaseDAO;
import dao.UserDAO;
import dtos.PurchaseDTO;

@Path("/purchases")
public class PurchaseService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {

		if (ctx.getAttribute("purchaseDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("purchaseDAO", new PurchaseDAO());
		}
	}
	
	public PurchaseService() {
		// TODO Auto-generated constructor stub
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public void addPurchase(Cart cart) {
		PurchaseDAO dao = (PurchaseDAO) ctx.getAttribute("purchaseDAO");
		Purchase newPurchase = new Purchase();
		//newPurchase.setChocolates(cart.getChocolatesInCart());
		newPurchase.setChocolateIds(cart.getChocolatesInCart().stream().map(Chocolate::getId).collect(Collectors.toList()));
		newPurchase.setFactoryId(cart.getChocolatesInCart().get(0).getFabricId());
		//newPurchase.setFactoryId(newPurchase.getFactory().getId());
		newPurchase.setDate(LocalDateTime.now());
		newPurchase.setPrice(cart.getPrice());
		newPurchase.setCustomer(cart.getUser().getName() + " " + cart.getUser().getLastName());
		newPurchase.setUserId(cart.getUser().getId());
		newPurchase.setStatus(Status.InProgress);
		dao.addPurchase(newPurchase);
		CustomerTypeDAO customerTypeDAO = new CustomerTypeDAO();
		customerTypeDAO.updateCustomerType(cart);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Purchase> getByUserId(@PathParam("id") String userId){
		PurchaseDAO dao = (PurchaseDAO) ctx.getAttribute("purchaseDAO");
		Collection<Purchase> purchases = dao.getByUserId(userId);
		ChocolateFactoryDAO factoryDAO = new ChocolateFactoryDAO();
		ChocolateDAO chocolateDAO = new ChocolateDAO();
		for(Purchase purchase : purchases) { 
			purchase.setFactory(factoryDAO.getFactoryDetails(purchase.getFactoryId()));
			Set<String> chocolateIds = purchase.getChocolateIds().stream().collect(Collectors.toSet());
			purchase.setChocolates(chocolateDAO.getByIds(chocolateIds));
		}
		return purchases;
	}
	
	@PATCH
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Purchase updateStatus(@PathParam("id") String id, PurchaseDTO purchaseDTO) {
		PurchaseDAO dao = (PurchaseDAO) ctx.getAttribute("purchaseDAO");
		return dao.updateStatus(purchaseDTO, id);
	}
}
