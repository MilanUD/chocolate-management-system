package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.CustomerType;
import dao.ChocolateDAO;
import dao.CustomerTypeDAO;
import dtos.PurchaseDTO;

@Path("/customerTypes")
public class CustomerTypeService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("customerTypeDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("customerTypeDAO", new CustomerTypeDAO(contextPath));
		}
	}

	public CustomerTypeService() {
		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerType getCustomerTypeByUserId(@PathParam("id") String userId) {
		CustomerTypeDAO dao = (CustomerTypeDAO) ctx.getAttribute("customerTypeDAO");
		return dao.getByUserId(userId);
		
	}
	
	@PATCH
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateCustomerPoints(PurchaseDTO purchase) {
		CustomerTypeDAO dao = (CustomerTypeDAO) ctx.getAttribute("customerTypeDAO");
		dao.subtractPoints(purchase);
	}

}
