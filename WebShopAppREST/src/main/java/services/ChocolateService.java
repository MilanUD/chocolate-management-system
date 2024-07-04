package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Cart;
import beans.Chocolate;
import beans.ChocolateFactory;
import dao.ChocolateDAO;
import dao.ChocolateFactoryDAO;
import dtos.ChocolateDTO;
import dtos.PurchaseDTO;

@Path("/chocolates")
public class ChocolateService {

	@Context
	ServletContext ctx;
	public ChocolateService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("chocolatesDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("chocolatesDAO", new ChocolateDAO());
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Chocolate> getChocolates(@PathParam("id") String fabricId) {
		ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("chocolatesDAO");
		return dao.getByFabricId(fabricId);
	}
	
	@GET
	@Path("/getChocolate{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Chocolate getChocolate(@PathParam("id") String id) {
		ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("chocolatesDAO");
		return dao.getById(id);
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public void createChocolate(Chocolate chocolate) {
		ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("chocolatesDAO");
		dao.addChocolate(chocolate);
	}
	
	@PUT
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Chocolate editChocolate(Chocolate chocolate) {
		ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("chocolatesDAO");
		return dao.editChocolate(chocolate);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Chocolate deleteChocolate(@PathParam("id") String id) {
		ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("chocolatesDAO");
		return dao.deleteChocolate(id);
	}
	
	@POST
	@Path("/updateQuantity")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateChocolateQuantity(Cart cart) {
		ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("chocolatesDAO");
		dao.updateChocolatesQuantity(cart.getChocolatesInCart());
	}
	
	@PATCH
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public void revertQuantity(PurchaseDTO dto) {
		ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("chocolatesDAO");
		dao.revertStockQuantities(dto);
	}
	
	@PATCH
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void changeQuantityByWorker(@PathParam("id") String id, ChocolateDTO chocolateDTO) {
		ChocolateDAO dao = (ChocolateDAO) ctx.getAttribute("chocolatesDAO");
		dao.changeQuantityByWorker(id, chocolateDTO);
	}
}
